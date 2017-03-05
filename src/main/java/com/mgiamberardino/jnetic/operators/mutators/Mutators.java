package com.mgiamberardino.jnetic.operators.mutators;

import java.util.List;
import java.util.function.Function;

import com.mgiamberardino.jnetic.operators.Operators;
import com.mgiamberardino.jnetic.operators.Operators.Factory;

public class Mutators<T,U> {
	
	private Function<Integer, U> randomGenGenerator;
	private Function<T, List<U>> gensGetter;
	private Function<List<U>, T> fromGensBuilder;
	
	public Mutators(Operators.Factory<T, U> factory) {
		this.randomGenGenerator = factory.randomGenGenerator();
		this.gensGetter = factory.gensGetter();
		this.fromGensBuilder = factory.fromGensBuilder();
	}
	
	public Function<T, T> basicMutatorBuilder(Double ratio){
		return new UniformMutatorBuilder<T,U>(ratio)
				.fromGensBuilder(fromGensBuilder)
				.gensGettter(gensGetter)
				.randomGenGenerator(randomGenGenerator)
				.build();
	}
	
	public Function<T, T> basicMutatorBuilder(){
		return basicMutatorBuilder(0.01);
	}
	
}
