package com.mgiamberardino.jnetic.operators;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.mgiamberardino.jnetic.population.Population;

@FunctionalInterface
public interface Condition<T, U> extends BiFunction<Population<T>, Function<T, U>, Boolean> {

	default Condition<T,U> or(Condition<T, U> condition){
		return (t, u) -> (apply(t, u) || condition.apply(t, u));
	}
	
}
