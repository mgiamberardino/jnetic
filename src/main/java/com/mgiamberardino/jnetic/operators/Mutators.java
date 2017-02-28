package com.mgiamberardino.jnetic.operators;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Mutators {

	public static abstract class BasicBuilder<T, U> {
		
		protected Function<T, List<U>> gensGetter;
		protected Function<List<U>, T> fromGensBuilder;
		protected Function<Integer, U> randomGenGenerator;

		public BasicBuilder<T, U> gensGettter(Function<T, List<U>> gensGetter){
			this.gensGetter = gensGetter;
			return this;
		}
		
		public BasicBuilder<T, U> fromGensBuilder(Function<List<U>, T> fromGensBuilder){
			this.fromGensBuilder = fromGensBuilder;
			return this;
		}
		
		public BasicBuilder<T, U> randomGenGenerator(Function<Integer, U> randomGenGenerator){
			this.randomGenGenerator = randomGenGenerator;
			return this;
		}
		
		public Function<T, T> build(){
			if (null == gensGetter){
				throw new IllegalArgumentException("The genGetter must be setted before build.");
			}
			if (null == fromGensBuilder){
				throw new IllegalArgumentException("The fromGensBuilder must be setted before build.");
			}
			return buildMutator();
		}

		protected abstract Function<T, T> buildMutator();
		
	}
	
	public static class UniformMutatorBuilder<T, U> extends BasicBuilder<T, U>{

		private Double ratio = 0.01;
		
		public UniformMutatorBuilder() {
			//Do Nothing
		}
		
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
	
}
