package com.mgiamberardino.jnetic.operators.crossers;

import java.util.List;
import java.util.function.Function;

import com.mgiamberardino.jnetic.population.Population.Parents;

/**
 * 
 * The Crosser.BasicBuilder is an abstract class that contains the basic
 * data needed to build a Crosser. Extending this class and addind a
 * createCrosser method you can create a new Builder.
 * 
 * @author Mauro J Giamberardino <mgiamberardino@gmail.com>
 *
 * @param <T>
 *            Type of the individual for the GA
 * @param <U>
 *            The type returned by the fitness function
 */
public abstract class BasicCrosserBuilder<T, U> {

	private Function<T, List<U>> gensGetter;
	private Function<List<U>, T> fromGensBuilder;

	public BasicCrosserBuilder<T, U> gensGetter(Function<T, List<U>> gensGetter) {
		this.gensGetter = gensGetter;
		return this;
	}

	public Function<T, List<U>> gensGetter() {
		return gensGetter;
	}

	public BasicCrosserBuilder<T, U> fromGensBuilder(Function<List<U>, T> fromGensBuilder) {
		this.fromGensBuilder = fromGensBuilder;
		return this;
	}

	public Function<List<U>, T> fromGensBuilder() {
		return fromGensBuilder;
	}

	public final Function<Parents<T>, List<T>> build() {
		if (null == gensGetter) {
			throw new IllegalArgumentException("The genGetter must be setted before build.");
		}
		if (null == fromGensBuilder) {
			throw new IllegalArgumentException("The fromGensBuilder must be setted before build.");
		}
		return createCrosser();
	};

	protected abstract Function<Parents<T>, List<T>> createCrosser();

}