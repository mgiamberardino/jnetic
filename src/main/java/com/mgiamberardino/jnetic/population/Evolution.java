package com.mgiamberardino.jnetic.population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mgiamberardino.jnetic.Individual;
import com.mgiamberardino.jnetic.operators.Operators;
import com.mgiamberardino.jnetic.operators.selectors.Selector;
import com.mgiamberardino.jnetic.operators.selectors.SelectorFactory;
import com.mgiamberardino.jnetic.population.Population.Parents;

public class Evolution<T, U extends Comparable<U>> {
	
	private Population<T> population;
	private Function<Parents<T>, List<T>> crosser;
	private Selector<T, U> selector;
	private Function<T, T> mutator;
	private Function<T, U> aptitudeFunction;
	private Function<Map<T,U>, Supplier<Parents<T>>> parentSupplierBuilder = Evolution::defaultSupplier;
	private Predicate<T> validator = o -> true;
	
	private Double elitePortion;
	
	public static <T, U extends Comparable<U>> Evolution<T, U> of(Population<T> population, Function<T, U> aptitudeFunction, double elitePortion) {
		return new Evolution<T, U>(population, aptitudeFunction,elitePortion);
	}
	
	public static <T extends Individual<R>, R> Evolution<T, Double> create(Individual.Factory<T, R> factory, int populationSize, double elitePortion) {
		return new Evolution<T, Double>(Population.generate(factory::buildRandom, populationSize), i -> i.getAptitude(), elitePortion)
					.operators(Operators.factory(factory))
					.validator(i -> i.isValid());
	}
	
	Evolution(Population<T> population, Function<T, U> aptitudeFunction, Double elitePortion){
		this.population = population;
		this.aptitudeFunction = aptitudeFunction;
		this.elitePortion = elitePortion;
		this.selector = Operators.SELECTORS.binaryTournament(this ,0.5, 0.75);
	}
	
	public Evolution<T,U> evolve() {
		List<T> parents = selector.select(population, aptitudeFunction, elitePortion);
		Map<T,U> aptitudes = parents.stream()
			.collect(Collectors.toMap(Function.identity(), aptitudeFunction, (s1, s2) -> s1));
		List<T> sons =	Stream.generate(parentSupplierBuilder.apply(aptitudes))
				.parallel()
			    .map((parentsPair) -> crosser.apply(parentsPair))
			    .flatMap(List::stream)
			    .map(t -> mutator.apply(t))
			    .filter(validator::test)
			    .limit(population.size() - parents.size())
			    .collect(Collectors.toList());
		parents.addAll(sons);
		population = new Population<>(parents, population.getGeneration()+1);
		return this;
	}

	public Evolution<T, U> evolveUntil(Predicate<Population<T>> condition){
		Integer i = 0;
		while(! condition.test(population)){
			evolve();
			//System.out.println("Generacion " + i + ":");
			//System.out.println(population.stream().collect(Collectors.toList()));
			i++;
		}
		System.out.println("Stopped at generation " + i);
		return this;
	}
	
	public Evolution<T, U> crosser(Function<Parents<T>, List<T>> crosser) {
		this.crosser = crosser;
		return this;
	}
	
	public <R> Evolution<T, U> operators(Operators.Factory<T, R> operators){
		this.crosser = operators.crossers().uniformCrosserBuilder();
		this.mutator = operators.mutators().basicMutatorBuilder();
		return this;
	}

	public Function<Parents<T>, List<T>> crosser() {
		return crosser;
	}

	public Selector<T, U> selector() {
		return selector;
	}

	public Evolution<T, U> mutator(Function<T, T> mutator) {
		this.mutator = mutator;
		return this;
	}

	public Function<T,T> mutator() {
		return mutator;
	}

	public Evolution<T, U> selector(Selector<T,U> selector) {
		this.selector = selector;
		return this;
	}
	
	private static <T,U> Supplier<Parents<T>> defaultSupplier(Map<T,U> unmodifiableAptitudes){
		return new Supplier<Parents<T>>() {
			
			List<T> list;
			{
				if (0 == unmodifiableAptitudes.size()){
					throw new IllegalStateException("The size of the selected parents must be bigger than 0.");
				}
				list = new ArrayList<T>(unmodifiableAptitudes.keySet());
			}
			
			@Override
			public Parents<T> get() {
				Collections.shuffle(list);
				return new Parents<T>(list.get(0), list.size() > 1 ? list.get(1) : list.get(0));
			}
			
		};
	}
	
	public T best(){
		return population.stream()
			.reduce((t1, t2) -> aptitudeFunction.apply(t1).compareTo(aptitudeFunction.apply(t2)) >= 0 ? t1 : t2)
			.orElse(null);
	}
	
	public Predicate<T> validator() {
		return validator;
	}

	public Evolution<T, U> validator(Predicate<T> validator) {
		this.validator = validator;
		return this;
	}
	
	public int currentGeneration(){
		return population.getGeneration();
	}

}
