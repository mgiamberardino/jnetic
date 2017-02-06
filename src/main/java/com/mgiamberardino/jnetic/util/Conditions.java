package com.mgiamberardino.jnetic.util;

import java.util.function.Function;

import com.mgiamberardino.jnetic.operators.Condition;
import com.mgiamberardino.jnetic.population.Population;

public class Conditions {
	
	public static <T> Condition<T> after(int iterations){
		return (population) -> population.getGeneration() == iterations;
	}
	
	public static <T> Condition<T> converge(Double difference, Function<Population<T>, Double> avgFunction){
		return new Condition<T>() {

			private Double lastAvg = 0.0;
			private int times = 0;
			@Override
			public Boolean apply(Population<T> population) {
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
	
	public static <T> Condition<T> contains(T value){
		return new Condition<T>() {

			@Override
			public Boolean apply(Population<T> population) {
				return population.stream().anyMatch(t -> t.equals(value));
			}
		};
	}
	
}
