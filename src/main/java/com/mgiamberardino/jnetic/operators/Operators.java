package com.mgiamberardino.jnetic.operators;

import java.util.List;
import java.util.function.Function;

import com.mgiamberardino.jnetic.Individual;
import com.mgiamberardino.jnetic.operators.crossers.Crossers;
import com.mgiamberardino.jnetic.operators.mutators.Mutators;
import com.mgiamberardino.jnetic.operators.selectors.SelectorFactory;

public class Operators {
	
	public static final SelectorFactory SELECTORS = new SelectorFactory();
	
	public static class Factory<T, U> {
		
		private Function<T, List<U>> gensGetter;
		private Function<List<U>, T> fromGensBuilder;
		private Function<Integer, U> randomGenGenerator;
		
		private Crossers<T,U> crossers;
		private Mutators<T,U> mutators;
		
		Factory(Function<T, List<U>> gensGetter,
				Function<List<U>, T> fromGensBuilder,
				Function<Integer, U> randomGenGenerator){
			this.gensGetter = gensGetter;
			this.fromGensBuilder = fromGensBuilder;
			this.randomGenGenerator = randomGenGenerator;
			this.crossers = new Crossers<>(this);
			this.mutators = new Mutators<>(this);
		}

		public Function<T, List<U>> gensGetter() {
			return gensGetter;
		}


		public Function<List<U>, T> fromGensBuilder() {
			return fromGensBuilder;
		}

		public Function<Integer, U> randomGenGenerator() {
			return randomGenGenerator;
		}

		public Crossers<T,U> crossers(){
			return crossers;
		}
		
		public Mutators<T,U> mutators(){
			return this.mutators;
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
