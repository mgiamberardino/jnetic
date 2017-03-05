package com.mgiamberardino.jnetic.operators.selectors;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.mgiamberardino.jnetic.population.Evolution;
import com.mgiamberardino.jnetic.population.Population;

public class SelectorFactory {

	private Random random = new Random(System.currentTimeMillis());
	
	public SelectorFactory(){
		//avoid public creation
	}
	
	public <T,U extends Comparable<U>> Selector<T, U> truncatedSelection(Double portion){
		return (population, aptitudeFunction, elitePortion) -> {
				List<T> result = population.stream().collect(Collectors.toList());
				Collections.sort(result , Collections.reverseOrder(Population.comparator(aptitudeFunction)));
				Double calculatedPortion = portion > elitePortion
							? portion
							: elitePortion;
				return result.stream().limit((long) (population.size() * calculatedPortion)).collect(Collectors.toList());
			};
	}
	
	public <T> Selector<T, Double> rouletteStochasticAcceptance(Double portion){
		return (population, aptitudeFunction, elitePortion) -> {
				long toSelect = Math.round(population.size() * portion);
				List<T> result = getElite(population, elitePortion, toSelect, aptitudeFunction);
				if (result.size() >= toSelect){
					return result;
				}
				List<T> individuals = population.stream().collect(Collectors.toList());
				Double max = population.stream().map(aptitudeFunction).sorted().reduce((act, last) -> last).orElse(null);
				Collections.shuffle(individuals);
				Stream.generate(() -> individuals.get(random.nextInt(individuals.size())))
						.filter(t -> random.nextDouble() < (aptitudeFunction.apply(t)/max))
						.limit(toSelect - result.size())
						.forEach(result::add);
				return result;
			};
	}
	
	public <T> Selector<T, Double> deterministicBinaryTournament(Double portion){
		return binaryTournament(portion, 1.0);
	}
	
	public <T, U extends Comparable<U>> Selector<T, U> binaryTournament(Evolution<T,U> e, Double portion, Double probability){
		return binaryTournament(portion, probability);
	}
	
	public <T, U extends Comparable<U>> Selector<T,U> binaryTournament(Double portion, Double probability){
		return (population, aptitudeFunction, elitePortion) -> {
			long toSelect = Math.round(population.size() * portion);
			List<T> individuals =  population.stream().collect(Collectors.toList());
			List<T> result = getElite(population, elitePortion, toSelect, aptitudeFunction);
			if (result.size() >= toSelect){
				return result;
			}
			Stream.generate(() -> selectForTournament(individuals, 2, aptitudeFunction, probability))
				.limit(toSelect - result.size())
				.forEach(result::add);
			return result;
		};
	}
	
	private <T, U extends Comparable<U>> T selectForTournament(List<T> population, int size, Function<T, U> aptitudeFunction, double probability){
		return IntStream.generate(() -> random.nextInt(population.size()))
					.limit(size)
					.mapToObj(population::get)
					.sorted(Population.comparator(aptitudeFunction))
					.reduce((i1, i2) -> random.nextDouble() <= probability ? i2 : i1)
					.get();
	}
	
	private <T, U extends Comparable<U>> List<T> getElite(Population<T> population, double elitePortion, long toSelect, Function<T, U> aptitudeFunction) {
		long eliteToSelect = Math.round(population.size() * elitePortion);
		long eliteRealTotal = eliteToSelect < toSelect ? eliteToSelect : toSelect;
		List<T> sorted = population.stream().sorted(Collections.reverseOrder(Population.comparator(aptitudeFunction))).collect(Collectors.toList());
		return sorted.stream().limit(eliteRealTotal).collect(Collectors.toList());
	}
	
}
