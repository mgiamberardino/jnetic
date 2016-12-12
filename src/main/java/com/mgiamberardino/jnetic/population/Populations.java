package com.mgiamberardino.jnetic.population;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Populations {

	public static <T> Population<T> of(List<T> members) {
		return new Population<T>(members);
	}

	public static <T> Population<T> generate(Supplier<T> generator, Integer size) {
		return new Population<>(IntStream.range(0, size)
				 .boxed()
				 .map( position -> generator.get())
				 .collect(Collectors.toList()));
	}
	
}
