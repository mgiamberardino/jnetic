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

public class PopulationTest{

	private static Random random = new Random(System.currentTimeMillis());
	
	private static ArrayList<Integer> individualGenerator() {
		return getRandomStream(10,0,100)
						.boxed()
					    .collect(Collectors.toCollection(ArrayList::new));
	}

	private static IntStream getRandomStream(int size, int from, int to) {
		return random.ints(size, from, to);
	}
	
	@Test
	public void testCreateEmptyPopulation() {
		Population<Integer> pop = Population.of(new ArrayList<Integer>());
		assertEquals(Integer.valueOf(0), pop.size());
	}
	
	@Test
	public void testCreatePopulationWithSomeMembers() {
		Population<Integer> pop = Population.of(IntStream.of(1, 2, 3, 4).boxed().collect(Collectors.toList()));
		assertEquals(Integer.valueOf(4), pop.size());
	}
	
	@Test
	public void testPopulationGenerator() {
		Population<Integer> pop = Population.generate(() -> new Integer(0), 10);
		assertEquals(Integer.valueOf(10), pop.size());
	}
	
	@Test
	public void testPopulationGenerateIndivuals() {
		Population<ArrayList<Integer>> pop = Population.generate(PopulationTest::individualGenerator, 10);
		assertEquals(Integer.valueOf(10), pop.size());
		if (! pop.stream().allMatch(i -> null != i)){
			fail();
		}
	}

	@Test
	public void testEvolutionFromPopulation(){
		Evolution<Integer, Integer> ev = Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList())).evolution(Function.identity());
		assertNotNull(ev);
	}

}
