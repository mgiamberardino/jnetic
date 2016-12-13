package com.mgiamberardino.jnetic.population;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Evolution<T, U> {

	private Population<T, U> population;
	private BiFunction<T, T, List<T>> crosser;
	private Function<List<T>, List<T>> selector;
	private Function<T, T> mutator;
	
	public static <T, U> Evolution<T, U> of(Population<T,U> population) {
		return new Evolution<T, U>(population);
	}
	
	Evolution(Population<T, U> population){
		this.population = population;
	}
	
	public Population<T, U> evolve() {
		return Population.of(population);
	}

	public Evolution<T, U> crosser(BiFunction<T, T, List<T>> crosser) {
		this.crosser = crosser;
		return this;
	}

	public BiFunction<T, T, List<T>> crosser() {
		return crosser;
	}

	public Evolution<T, U> selector(Function<List<T>, List<T>> selector) {
		this.selector = selector;
		return this;
	}

	public Function<List<T>, List<T>> selector() {
		return selector;
	}

	public Evolution<T, U> mutator(Function<T, T> mutator) {
		this.mutator = mutator;
		return this;
	}

	public Function<T,T> mutator() {
		return mutator;
	}

}
