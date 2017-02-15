package com.mgiamberardino.jnetic.population;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Population<T> {

	private List<T> members;
	private int generation;

	public static class Parents<T> {
		public T first;
		public T second;
		
		public Parents(T first, T second){
			this.first = first;
			this.second = second;
		}
	}

	public static <T> Population<T> of(List<T> members) {
		return new Population<T>(members, 0);
	}

	public static <T> Population<T> generate(Supplier<T> generator, Integer size) {
		return new Population<T>(
				Stream.generate(generator)
					  .limit(size)
					  .collect(Collectors.toList()), 0);
	}
	
	/**
	 * This method assumes that is evolving and the new population will have
	 * its generation number increased.
	 * @param population
	 * @return new population with the generation number increased
	 */
	public static <T, U> Population<T> of(Population<T> population) {
		return new Population<T>(population.members(), population.generation + 1);
	}
	
	public static <T,U extends Comparable<U>> Comparator<T> comparator(Function<T, U> aptitudeFunction){
		return (o1, o2) -> aptitudeFunction.apply(o1).compareTo(aptitudeFunction.apply(o2));
	}

	Population(List<T> members, int generation){
		this.generation = generation;
		this.members = new ArrayList<T>(members);
	}

	public Integer size() {
		return members.size();
	}

	public Stream<T> stream() {
		return members.stream();
	}

	public <U extends Comparable<U>> Evolution<T, U> evolution(Function<T, U> aptitudeFunction, double elitePortion) {
		return new Evolution<T, U>(this, aptitudeFunction, elitePortion);
	}

	private List<T> members() {
		return members;
	}

	public int getGeneration() {
		return generation;
	}
	

}
