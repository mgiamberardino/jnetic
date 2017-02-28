package com.mgiamberardino.jnetic.operators;

import java.util.List;
import java.util.function.Function;

import com.mgiamberardino.jnetic.Individual;

public class Operators {
	
	public static class Factory<T, U> {
		
		private Function<T, List<U>> gensGetter;
		private Function<List<U>, T> fromGensBuilder;
		private Function<Integer, U> randomGenGenerator;
		
		Factory(Function<T, List<U>> gensGetter,
				Function<List<U>, T> fromGensBuilder,
				Function<Integer, U> randomGenGenerator){
			this.gensGetter = gensGetter;
			this.fromGensBuilder = fromGensBuilder;
			this.randomGenGenerator = randomGenGenerator;
		}
		
		public Crossers.BasicBuilder<T,U> uniformCrosserBuilder(){
			return new Crossers.UniformCrosserBuilder<T,U>()
					.gensGetter(gensGetter)
					.fromGensBuilder(fromGensBuilder);
		}
		
		public Mutators.BasicBuilder<T,U> basicMutatorBuilder(Double ratio){
			return new Mutators.UniformMutatorBuilder<T,U>(ratio)
					.fromGensBuilder(fromGensBuilder)
					.gensGettter(gensGetter)
					.randomGenGenerator(randomGenGenerator);
		}
		
		public Mutators.BasicBuilder<T,U> basicMutatorBuilder(){
			return basicMutatorBuilder(0.01);
		}
		
	}
	
	public static <T,U> Factory<T,U> factory(Function<T, List<U>> gensGetter,
			Function<List<U>,T> fromGensBuilder, Function<Integer, U> randomGenGenerator){
		return new Factory<>(gensGetter, fromGensBuilder, randomGenGenerator);
	}
	
	public static <U, T extends Individual<U>> Factory<T,U> factory(Individual.Factory<T, U> iFactory){
		return new Factory<T, U>(i -> i.gens(), iFactory::build, iFactory::buildGen);
	}

}
