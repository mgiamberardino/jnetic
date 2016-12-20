package com.mgiamberardino.jnetic.population;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mgiamberardino.jnetic.operators.Selector;
import com.mgiamberardino.jnetic.population.Population.Parents;

public class Evolution<T, U> {

	private Population<T, U> population;
	private Function<Parents<T>, List<T>> crosser;
	private Selector<T, U> selector;
	private Function<T, T> mutator;
	private Function<T, U> aptitudeFunction = (Function<T, U>) Function.identity();
	private Function<Map<T,U>, Supplier<Parents<T>>>  parentSupplierBuilder;
	private Predicate<T> validator = o -> true;
	public static <T, U> Evolution<T, U> of(Population<T,U> population) {
		return new Evolution<T, U>(population);
	}
	
	Evolution(Population<T, U> population){
		this.population = population;
	}
	
	public Population<T, U> evolve() {
		List<T> parents = selector.select(population.stream(), aptitudeFunction, population.size() / 2);
		Map<T,U> aptitudes = parents.stream()
			.collect(Collectors.toMap(Function.identity(), aptitudeFunction));
		List<T> sons =	Stream.generate(parentSupplierBuilder.apply(aptitudes))
							     .map((parentsPair) -> crosser.apply(parentsPair))
							     .flatMap(List::stream)
							     .map(t -> mutator.apply(t))
							     .filter(validator::test)
							     .limit(population.size() - parents.size())
							     .collect(Collectors.toList());
		parents.addAll(sons);
		return new Population<>(parents);
	}

	public Evolution<T, U> crosser(Function<Parents<T>, List<T>> crosser) {
		this.crosser = crosser;
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

}
