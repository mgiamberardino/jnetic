package com.mgiamberardino.jnetic.util;

import java.util.function.Function;
import java.util.function.Predicate;

import com.mgiamberardino.jnetic.population.Population;

public class Conditions {
	
	public static <T> Predicate<Population<T>> after(int iterations){
		return (population) -> population.getGeneration() == iterations;
	}
	
	public static <T> Predicate<Population<T>> converge(Double difference, Function<Population<T>, Double> avgFunction){
		return new Predicate<Population<T>>() {

			private Double lastAvg = 0.0;
			private int times = 0;
			@Override
			public boolean test(Population<T> population) {
				Double avg = avgFunction.apply(population);
				Double diff = Math.abs(lastAvg - avg);
				System.out.println("Last: " + lastAvg + " New: " + avg + "Dif: " + diff);
				lastAvg = avg;
				if (diff < difference){
					times++;
					return times == 5;
				}
				times=0;
				return false;
			}
			
		};
	}
	
	public static <T> Predicate<Population<T>> contains(T value){
		return (Population<T> population) -> {
				return population.stream().anyMatch(t -> t.equals(value));
			};
	}
	
}
