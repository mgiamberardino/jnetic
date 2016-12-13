package com.mgiamberardino.jnetic.population;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Population<T, U> {

	private List<T> members;
	private Function<T, U> aptitudeFunction;
	
	public static <T, U> Population<T, U> of(List<T> members, Function<T, U> aptitudeFunction) {
		return new Population<T, U>(members);
	}

	public static <T, U> Population<T, U> generate(Supplier<T> generator, Integer size) {
		return new Population<T, U>(
				Stream.generate(generator)
					  .limit(size)
					  .collect(Collectors.toList()));
	}
	
	public static <T, U> Population<T, U> of(Population<T, U> population) {
		return new Population<T, U>(population.members()).aptitudeFunction(population.aptitudeFunction());
	}
		
	Population(List<T> members){
		this.members = new ArrayList<T>(members);
	}
	
	public Integer size() {
		return members.size();
	}

	public Stream<T> stream() {
		return members.stream();
	}

	public void evolve(Integer iterations) {
		
	}

	public U calculateFitness(Function<Stream<U>, U> fitnessFunction, Function<T, U> aptitudeFunction) {
		if (null == aptitudeFunction){
			throw new IllegalArgumentException("Aptitude function can't be null.");
		}
		if (null == fitnessFunction){
			throw new IllegalArgumentException("Fitness function can't be null.");
		}
		return fitnessFunction.apply(
					members.stream()
					  	   .map(aptitudeFunction));
	}
	
	public U calculateFitness(Function<Stream<U>, U> fitnessFunction) {
		if (null == aptitudeFunction){
			throw new IllegalStateException("You need to define the aptitude function to run this method.");
		}
		return fitnessFunction.apply(
					members.stream()
					  	   .map(aptitudeFunction));
	}

	public Evolution<T, U> evolution() {
		return new Evolution<T, U>(this);
	}

	public Population<T,U> aptitudeFunction(Function<T, U> aptitudeFunction) {
		this.aptitudeFunction = aptitudeFunction;
		return this;
	}

	public Function<T,U> aptitudeFunction() {
		return aptitudeFunction;
	}

	private List<T> members() {
		return members;
	}
}
