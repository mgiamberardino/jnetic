package com.mgiamberardino.jnetic.util;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.mgiamberardino.jnetic.operators.Selector;
import com.mgiamberardino.jnetic.population.Population;

public class Selectors {

	public static <T,U extends Comparable<U>> Selector<T, U> truncatedSelection(Double portion){
		return (population, aptitudeFunction) -> {
				List<T> result = population.stream().collect(Collectors.toList());
				Collections.sort(result , Collections.reverseOrder(Population.comparator(aptitudeFunction)));
				return result.stream().limit((long) (population.size() * portion)).collect(Collectors.toList());
			};
	}
	
	public static <T> Selector<T, Double> rouletteStochasticAcceptance(Double portion){
		return (population, aptitudeFunction) -> {
				List<T> result = population.stream().collect(Collectors.toList());
				Double max = result.stream().map(aptitudeFunction).sorted().reduce((act, last) -> last).orElse(null);
				Collections.shuffle(result);
				return result.stream()
						.filter(t -> new Random(System.currentTimeMillis()).nextDouble() < (aptitudeFunction.apply(t)/max))
						.limit((long) (population.size() * portion))
						.collect(Collectors.toList());
			};
	}
	
}
