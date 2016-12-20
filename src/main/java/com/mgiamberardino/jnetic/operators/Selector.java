package com.mgiamberardino.jnetic.operators;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@FunctionalInterface
public interface Selector<T,U> {

	public List<T> select(Stream<T> members, Function<T, U> aptitudeFunction, int size);
	
}
