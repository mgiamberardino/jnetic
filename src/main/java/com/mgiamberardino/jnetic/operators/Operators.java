package com.mgiamberardino.jnetic.operators;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
		
		public Crossers.BasicBuilder<T,U> crosserBuilder(){
			return new Crossers.UniformCrosserBuilder<T,U>()
					.gensGetter(gensGetter)
					.fromGensBuilder(fromGensBuilder);
		}
		
	}
	
	public static <T,U> Factory<T,U> factory(Function<T, List<U>> gensGetter,
			Function<List<U>,T> fromGensBuilder, Function<Integer, U> randomGenGenerator){
		return new Factory<>(gensGetter, fromGensBuilder, randomGenGenerator);
	}

}
