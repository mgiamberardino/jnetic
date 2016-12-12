package com.mgiamberardino.jnetic.population;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.mgiamberardino.jnetic.population.Population;
import com.mgiamberardino.jnetic.population.Populations;

import junit.framework.TestCase;

public class PopulationsTest extends TestCase {

	public void testCreateEmptyPopulation() {
		Population<Integer> pop = Populations.of(new ArrayList<Integer>());
		assertEquals(Integer.valueOf(0), pop.size());
	}

	public void testCreatePopulationWithSomeMembers() {
		Population<Integer> pop = Populations.of(IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList()));
		assertEquals(Integer.valueOf(4), pop.size());
	}

	public void testPopulationGenerator() {
		Population<Integer> pop = Populations.generate(() -> new Integer(0), 10);
		assertEquals(Integer.valueOf(10), pop.size());
	}

	public void testPopulationGenerateIndivuals() {
		Population<ArrayList<Integer>> pop = Populations.generate(PopulationsTest::individualGenerator, 10);
		assertEquals(Integer.valueOf(10), pop.size());
		if (! pop.stream().allMatch(i -> null != i)){
			fail();
		}
	}

	private static ArrayList<Integer> individualGenerator() {
		return IntStream.range(0, 10)
					    .boxed()
					    .collect(Collectors.toCollection(ArrayList::new));
	}
	
	public void testEvolvingPopulation(){
		Population<ArrayList<Integer>> pop = Populations.generate(PopulationsTest::individualGenerator, 10);
		pop.evolve(1);
	}
}
