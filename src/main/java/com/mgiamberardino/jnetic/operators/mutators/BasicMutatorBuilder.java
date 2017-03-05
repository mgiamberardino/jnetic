package com.mgiamberardino.jnetic.operators.mutators;

import java.util.List;
import java.util.function.Function;

public abstract class BasicMutatorBuilder<T,U> {

	protected Function<T, List<U>> gensGetter;
	protected Function<List<U>, T> fromGensBuilder;
	protected Function<Integer, U> randomGenGenerator;

	public BasicMutatorBuilder<T, U> gensGettter(Function<T, List<U>> gensGetter){
		this.gensGetter = gensGetter;
		return this;
	}
	
	public BasicMutatorBuilder<T, U> fromGensBuilder(Function<List<U>, T> fromGensBuilder){
		this.fromGensBuilder = fromGensBuilder;
		return this;
	}
	
	public BasicMutatorBuilder<T, U> randomGenGenerator(Function<Integer, U> randomGenGenerator){
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
