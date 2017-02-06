package com.mgiamberardino.jnetic.operators;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mgiamberardino.jnetic.population.Population;

@FunctionalInterface
public interface Selector<T,U extends Comparable<U>> {

	public List<T> select(Population<T> population, Function<T, U> aptitudeFunction);
	
}
