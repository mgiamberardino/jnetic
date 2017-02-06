package com.mgiamberardino.jnetic.util;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.mgiamberardino.jnetic.operators.Condition;
import com.mgiamberardino.jnetic.population.Population;

public class Conditions {
	
	public static <T,U> Condition<T,U> after(int iterations){
		return (population, aptitudeFunction) -> population.getGeneration() == iterations;
	}
	
	public static <T,U> Condition<T,U> converge(Double difference, BiFunction<Population<T>, Function<T,U>, Double> avgFunction){
		return new Condition<T, U>() {

			private Double lastAvg = 0.0;
			private int times = 0;
			@Override
			public Boolean apply(Population<T> population, Function<T, U> aptitudeFunction) {
				Double avg = avgFunction.apply(population, aptitudeFunction);
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
	
}
