package com.mgiamberardino.jnetic.population;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Population<T> {

	private List<T> members;

	public static class Parents<T> {
		public T first;
		public T second;
		
		public Parents(T first, T second){
			this.first = first;
			this.second = second;
		}
	}

	public static <T> Population<T> of(List<T> members) {
		return new Population<T>(members);
	}

	public static <T> Population<T> generate(Supplier<T> generator, Integer size) {
		return new Population<T>(
				Stream.generate(generator)
					  .limit(size)
					  .collect(Collectors.toList()));
	}

	public static <T, U> Population<T> of(Population<T> population) {
		return new Population<T>(population.members());
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

	public <U extends Comparable> Evolution<T, U> evolution(Function<T, U> aptitudeFunction) {
		return new Evolution<T, U>(this, aptitudeFunction);
	}

	private List<T> members() {
		return members;
	}

}
