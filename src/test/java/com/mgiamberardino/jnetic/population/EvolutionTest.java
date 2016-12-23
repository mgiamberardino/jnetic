package com.mgiamberardino.jnetic.population;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.mgiamberardino.jnetic.operators.Selector;
import com.mgiamberardino.jnetic.population.Population.Parents;

public class EvolutionTest {
	private static Function<Parents<Integer>, List<Integer>> CROSSER = (parents) -> Arrays.asList(parents.first, parents.second);
	private static Selector<Integer, Integer> SELECTOR = (pop, function) -> pop.stream().collect(Collectors.toList()); 
	private static Function<Integer, Integer> MUTATOR = (i1) -> i1; 
	
	@Test
	public void testEvolutionCreation() {
		Evolution<Integer, Integer> ev = Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList())),Function.identity());
		assertNotNull(ev);
	}
	
	@Test
	public void testSetCrosser(){
		assertEquals(CROSSER, Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList())),Function.identity())
				  .crosser(CROSSER)
				  .crosser());
	}
	
	@Test
	public void testSetSelector(){
		assertEquals(SELECTOR, Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList())),Function.identity())
				  .selector(SELECTOR)
				  .selector());
	}
	
	@Test
	public void testSetMutator(){
		assertEquals(MUTATOR, Evolution.of(Population.of(IntStream.range(0,10).boxed().collect(Collectors.toList())),Function.identity())
				  .mutator(MUTATOR)
				  .mutator());
	}
	
	@Test
	public void testEvolve(){
		Evolution.of(
				Population.of(
					IntStream.range(0,10)
						.boxed()
						.peek(System.out::println)
						.collect(Collectors.toList()))
						,Function.identity())
			.crosser(CROSSER)
			.selector(EvolutionTest::select)
			.mutator(MUTATOR);
			//.evolveUntil((pop, apt, fit) -> );
	}
	
	private static List<Integer> select(Population<Integer> members, Function<Integer, Integer> aptitudeFunction){
		return members.stream()
			.sorted((i1, i2) -> (aptitudeFunction.apply(i1) - aptitudeFunction.apply(i2)))
			.limit(members.size()/2)
			.collect(Collectors.toList());
	}

}
