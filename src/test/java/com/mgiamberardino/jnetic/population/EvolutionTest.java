package com.mgiamberardino.jnetic.population;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class EvolutionTest {
	private static BiFunction<Integer, Integer, List<Integer>> CROSSER = (i1, i2) -> Arrays.asList(i1, i2);
	private static Function<List<Integer>, List<Integer>> SELECTOR = (list) -> new ArrayList<>(list); 
	private static Function<Integer, Integer> MUTATOR = (i1) -> i1; 
	
	@Test
	public void testEvolutionCreation() {
		Evolution<Integer, Integer> ev = Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList()),Function.identity()));
		assertNotNull(ev);
	}
	
	@Test
	public void testEvolutionEvolve(){
		Evolution<Integer, Integer> ev = Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList()),Function.identity()));
		assertNotNull(ev.evolve());
	}
	
	@Test
	public void testSetCrosser(){
		assertEquals(CROSSER, Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList()),Function.identity()))
				  .crosser(CROSSER)
				  .crosser());
	}
	
	@Test
	public void testSetSelector(){
		assertEquals(SELECTOR, Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList()),Function.identity()))
				  .selector(SELECTOR)
				  .selector());
	}
	
	@Test
	public void testSetMutator(){
		assertEquals(MUTATOR, Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList()),Function.identity()))
				  .mutator(MUTATOR)
				  .mutator());
	}
	
	@Test
	public void testSetFunctions(){
		Evolution<Integer, Integer> ev = Evolution.of(
										Population.of(
											IntStream.range(0,10)
												.boxed()
												.collect(Collectors.toList())
												,Function.identity()))
									.crosser(CROSSER)
									.selector(SELECTOR)
									.mutator(MUTATOR);
	}

}
