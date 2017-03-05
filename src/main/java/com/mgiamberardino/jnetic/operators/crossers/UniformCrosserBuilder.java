package com.mgiamberardino.jnetic.operators.crossers;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.mgiamberardino.jnetic.population.Population.Parents;

/**
 * The Uniform crosser or Single-point crosser it's one of the crosser
 * strategies. 
 * <br><br><strong>From Wikipedia:</strong><br><br>
 * 
 * The uniform crossover uses a fixed mixing ratio between two parents. 
 * Unlike single- and two-point crossover, the uniform crossover enables 
 * the parent chromosomes to contribute the gene level rather than the
 * segment level.<br> <br>
 * If the mixing ratio is 0.5, the offspring has approximately half of 
 * the genes from first parent and the other half from second parent,
 * although cross over points can be randomly chosen as seen below:
 * <br> <br>
 * <strong>Ratio:</strong> 0.5<br><br> 
 * <strong>Parent 1:</strong> AAAAAAAAAAAA <br> 
 * <strong>Parent 2:</strong> BBBBBBBBBBBB <br><br>
 * <strong>Child 1:</strong> BABAAABABABB <br>
 * <strong>Child 2:</strong> ABABBBABABAA <br>
 * 
 * 
 * @author Mauro J Giamberardino <mgiamberardino@gmail.com>
 *
 * @param <T>
 *            Type of the individual for the GA
 * @param <U>
 *            The type returned by the fitness function
 */
class UniformCrosserBuilder<T, U> extends BasicCrosserBuilder<T, U> {

	public static final Double RATIO = 0.5;

	protected Double ratio = RATIO;

	public UniformCrosserBuilder<T, U> ratio(Double ratio) {
		if (ratio > 1 || ratio < 0) {
			throw new IllegalArgumentException("The ratio must be between 0 and 1.");
		}
		this.ratio = ratio;
		return this;
	}

	@Override
	protected Function<Parents<T>, List<T>> createCrosser() {
		return (parents) -> {
			List<U> gensFirst = gensGetter().apply(parents.first);
			List<U> gensSecond = gensGetter().apply(parents.second);
			List<U> firstChild = Lists.newArrayList();
			List<U> secondChild = Lists.newArrayList();
			Random rand = new Random(System.currentTimeMillis());
			IntStream.range(0, Math.min(gensFirst.size(), gensSecond.size())).forEach(pos -> {
				Double r = rand.nextDouble();
				firstChild.add(r >= ratio ? gensSecond.get(pos) : gensFirst.get(pos));
				secondChild.add(r >= ratio ? gensFirst.get(pos) : gensSecond.get(pos));
			});
			return Stream.of(fromGensBuilder().apply(firstChild), fromGensBuilder().apply(secondChild))
					.collect(Collectors.toList());
		};
	}

}