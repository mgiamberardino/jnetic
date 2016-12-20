package com.mgiamberardino.jnetic.population;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Population<T, U> {

	private List<T> members;
	private Function<T, U> aptitudeFunction;
	
	public static class Parents<T> {
		public T first;
		public T second;
		
		public Parents(T first, T second){
			this.first = first;
			this.second = second;
		}
	}
	
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
		return new Population<T, U>(population.members());
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

	public Evolution<T, U> evolution() {
		return new Evolution<T, U>(this);
	}
	
	private List<T> members() {
		return members;
	}
}
