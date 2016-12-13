package com.mgiamberardino.jnetic.population;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PopulationsTest{

	private static Random random = new Random(System.currentTimeMillis());
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testCreateEmptyPopulation() {
		Population<Integer, Integer> pop = Population.of(new ArrayList<Integer>(), Function.identity());
		assertEquals(Integer.valueOf(0), pop.size());
	}
	
	@Test
	public void testSetAptitudeFunction() {
		Population<Integer, Integer> pop = Population.of(new ArrayList<Integer>(), Function.identity())
											 .aptitudeFunction(Function.identity());
		assertEquals(Function.identity(), pop.aptitudeFunction());
	}
	
	@Test
	public void testCreatePopulationWithSomeMembers() {
		Population<Integer, Integer> pop = Population.of(IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList()), Function.identity());
		assertEquals(Integer.valueOf(4), pop.size());
	}
	
	@Test
	public void testPopulationGenerator() {
		Population<Integer, Integer> pop = Population.generate(() -> new Integer(0), 10);
		assertEquals(Integer.valueOf(10), pop.size());
	}
	
	@Test
	public void testPopulationGenerateIndivuals() {
		Population<ArrayList<Integer>, Integer> pop = Population.generate(PopulationsTest::individualGenerator, 10);
		assertEquals(Integer.valueOf(10), pop.size());
		if (! pop.stream().allMatch(i -> null != i)){
			fail();
		}
	}

	private static ArrayList<Integer> individualGenerator() {
		return getRandomStream(10,0,100)
						.boxed()
					    .collect(Collectors.toCollection(ArrayList::new));
	}

	private static IntStream getRandomStream(int size, int from, int to) {
		return random.ints(size, from, to);
	}

	@Test
	public void testSummarizing(){
		Population<Integer, Integer> pop = Population.of(IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList()), Function.identity());
		Integer result = pop.calculateFitness((members) -> members.mapToInt(Integer::intValue).sum(), Function.identity());
		assertEquals(new Integer(10), result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFitnessParametersFitnessFunctionNull(){
		Population<Integer, Integer> pop = Population.of(IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList()), Function.identity());
		pop.calculateFitness(null, Function.identity());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFitnessParametersAptitudeFunctionNull(){
		Population<Integer, Integer> pop = Population.of(IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList()), Function.identity());
		pop.calculateFitness((members) -> members.mapToInt(Integer::intValue).sum(), null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testSummarizingThrowingError(){
		Population<Integer, Integer> pop = Population.of(IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList()), Function.identity());
		pop.calculateFitness((members) -> members.mapToInt(Integer::intValue).sum());
	}
		
	@Test
	public void testEvolutionFromPopulation(){
		Evolution<Integer, Integer> ev = Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList()), Function.identity()).evolution();
		assertNotNull(ev);
	}

}
