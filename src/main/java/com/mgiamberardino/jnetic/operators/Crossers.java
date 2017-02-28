package com.mgiamberardino.jnetic.operators;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.mgiamberardino.jnetic.operators.Crossers.BasicBuilder;
import com.mgiamberardino.jnetic.population.Population.Parents;

public class Crossers {

	public static abstract class BasicBuilder<T, U> {
		
		private Function<T, List<U>> gensGetter;
		private Function<List<U>, T> fromGensBuilder;
		
		public BasicBuilder<T,U> gensGetter(Function<T, List<U>> gensGetter){
			this.gensGetter = gensGetter;
			return this;
		}
		
		public Function<T, List<U>> gensGetter(){
			return gensGetter;
		}
		
		public BasicBuilder<T,U> fromGensBuilder(Function<List<U>, T> fromGensBuilder){
			this.fromGensBuilder = fromGensBuilder;
			return this;
		}
		
		public Function<List<U>, T> fromGensBuilder(){
			return fromGensBuilder;
		}
		
		public final Function<Parents<T>, List<T>> build(){
			if (null == gensGetter){
				throw new IllegalArgumentException("The genGetter must be setted before build.");
			}
			if (null == fromGensBuilder){
				throw new IllegalArgumentException("The fromGensBuilder must be setted before build.");
			}
			return createCrosser();
		};
		
		protected abstract Function<Parents<T>, List<T>> createCrosser();
		
	}
	
	public static class OnePointCrossoverBuilder<T,U> extends BasicBuilder<T, U>{

		@Override
		protected Function<Parents<T>, List<T>> createCrosser() {
			return null;
		}
		
	}
	
	public static class UniformCrosserBuilder<T, U> extends BasicBuilder<T, U> {
		
		public static final Double RATIO = 0.5;
		
		protected Double ratio = RATIO;
		
		public UniformCrosserBuilder<T, U> ratio(Double ratio){
			if (ratio > 1 || ratio < 0){
				throw new IllegalArgumentException("The ratio must be between 0 and 1.");
			}
			this.ratio = ratio;
			return this;
		}
		
		@Override
		protected Function<Parents<T>, List<T>> createCrosser(){
			return (parents) -> {
				List<U> gensFirst = gensGetter().apply(parents.first);
				List<U> gensSecond = gensGetter().apply(parents.second);
				List<U> firstChild = Lists.newArrayList();
				List<U> secondChild = Lists.newArrayList();
				Random rand = new Random(System.currentTimeMillis());
				IntStream.range(0, Math.min(gensFirst.size(), gensSecond.size()))
					.forEach(pos -> {
						Double r = rand.nextDouble();
						firstChild.add(r >= ratio ? gensSecond.get(pos) : gensFirst.get(pos));
						secondChild.add(r >= ratio ? gensFirst.get(pos) : gensSecond.get(pos));
					});
				return Stream.of(
								fromGensBuilder().apply(firstChild),
								fromGensBuilder().apply(secondChild)
							)
						.collect(Collectors.toList());
			};
		}

	}
	
}
