package com.mgiamberardino.jnetic.operators.mutators;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UniformMutatorBuilder<T, U> extends BasicMutatorBuilder<T, U>{

	private Double ratio = 0.01;
	
	public UniformMutatorBuilder(Double ratio) {
		this.ratio = ratio >= 0.0 && ratio <= 1.0 ? ratio : 0.01;
	}
	
	@Override
	protected Function<T, T> buildMutator() {
		return (individual) -> {
			if (new Random(System.currentTimeMillis()).nextDouble() >= ratio){
				return individual;
			}
			List<U> gens = gensGetter.apply(individual);
			int pos = new Random().nextInt(gens.size());
			U gen = randomGenGenerator.apply(pos);
			return fromGensBuilder.apply(
					IntStream.range(0, gens.size())
						.mapToObj(i -> i == pos ? gen : gens.get(i))
						.collect(Collectors.toList())
					);
		};
	}
	
}
