package com.mgiamberardino.jnetic.operators;

import java.util.function.Function;

import com.mgiamberardino.jnetic.population.Population;

@FunctionalInterface
public interface Condition<T> extends Function<Population<T>, Boolean> {

	default Condition<T> or(Condition<T> condition){
		return (t) -> (apply(t) || condition.apply(t));
	}
	
}
