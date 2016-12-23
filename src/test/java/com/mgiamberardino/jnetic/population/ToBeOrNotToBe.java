package com.mgiamberardino.jnetic.population;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import com.mgiamberardino.jnetic.population.Evolution;
import com.mgiamberardino.jnetic.population.Population;
import com.mgiamberardino.jnetic.population.Population.Parents;

public class ToBeOrNotToBe {

	private static final String SEARCHED_STRING = "To be or not to be";
	
	public static void main(String[] args) {
		System.out.println(
			Evolution.of(
					Population.generate(ToBeOrNotToBe::generatePhrase, 10),
					ToBeOrNotToBe::aptitudeMeter)
				.selector(ToBeOrNotToBe::select)
				.crosser(ToBeOrNotToBe::crosser)
				.mutator(ToBeOrNotToBe::mutator)
				.evolveUntil(ToBeOrNotToBe::stopCondition)
				.best()
		);
	}
	
	private static String generatePhrase(){
		return generateChars(SEARCHED_STRING.length());
	}
	
	private static String generateChars(int size){
		return RandomStringUtils.random(size,"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !;':.,");
	}
	
	private static Integer aptitudeMeter(String phrase){
		return SEARCHED_STRING.length() - compareByPosition(SEARCHED_STRING, phrase);
	}
	
	private static int compareByPosition(String s1, String s2){
		int counter = 0;
		for(int j=0; j < SEARCHED_STRING.length(); j++) {
		  String w1 = s1.substring(j,j+1);
		  String w2 = s2.substring(j,j+1);
	
		  if (!w1.equals(w2) ){
		    counter++;    
		  }
		}
		return counter;
	}

	private static List<String> select(Population<String> population, Function<String, Integer> aptitudeFunction){
		Integer sum = population.stream()
								.map(aptitudeFunction)
								.mapToInt(Integer::intValue)
								.sum();
		Integer floor = sum / population.size();
		List<String> result = population.stream()
			.filter(ph -> aptitudeFunction.apply(ph) >= floor)
			.collect(Collectors.toList());
		result.sort((s1, s2) -> aptitudeFunction.apply(s2).compareTo(aptitudeFunction.apply(s1)));
		return result.stream().limit(population.size() / 4 * 3).collect(Collectors.toList());
	}
	
	private static List<String> crosser(Parents<String> parents){
		Integer splitIndex = parents.first.length() / 2;
		String childA = parents.first.substring(0, splitIndex+1) + parents.second.substring(splitIndex+1, parents.second.length());
		String childB = parents.second.substring(0, splitIndex+1) + parents.first.substring(splitIndex+1, parents.first.length());
		return Stream.of(childA, childB).collect(Collectors.toList());
	}
	
	private static String mutator(String phrase){
		int index = RandomUtils.nextInt(0, SEARCHED_STRING.length());
		return phrase.substring(0, index) + generateChars(1) + phrase.substring(index+1, phrase.length());
	}
	
	private static Boolean stopCondition(Population<String> population, Function<String, Integer> aptitudeFunction){
		return population.stream().anyMatch(SEARCHED_STRING::equals);
	}
}
