package com.mgiamberardino.jnetic.operators.crossers;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.mgiamberardino.jnetic.population.Population.Parents;

/**
 * The One Point crosser or Single-point crosser it's one of the crosser
 * strategies.
 * <br><br><strong>From Wikipedia:</strong><br><br>
 * 
 * A single crossover point on both parents' organism strings is selected.
 * All data beyond that point in either organism string is swapped between
 * the two parent organisms. The resulting organisms are the children.
 * <br> <br>
 * <strong>Point:</strong> 4<br><br> 
 * <strong>Parent 1:</strong> AAAAAAAAAAAAA <br> 
 * <strong>Parent 2:</strong> BBBBBBBBBBBBB <br><br>
 * <strong>Child 1:</strong> BBBBAAAAAAAAA <br>
 * <strong>Child 2:</strong> AAAABBBBBBBBB <br>
 * 
 * 
 * @author Mauro J Giamberardino <mgiamberardino@gmail.com>
 *
 * @param <T>
 *            Type of the individual for the GA
 * @param <U>
 *            The type returned by the fitness function
 */
class OnePointCrossoverBuilder<T, U> extends BasicCrosserBuilder<T, U> {

	private int point;

	public OnePointCrossoverBuilder(int point) {
		super();
		this.point = point;
	}

	@Override
	protected Function<Parents<T>, List<T>> createCrosser() {
		return (parents) -> {
			List<U> gensFirst = gensGetter().apply(parents.first);
			List<U> gensSecond = gensGetter().apply(parents.second);
			List<U> firstChild = Lists.newArrayList();
			List<U> secondChild = Lists.newArrayList();
			IntStream.range(0, Math.max(gensFirst.size(), gensSecond.size()))
					.forEach(pos -> add(gensFirst.get(pos), gensSecond.get(pos), firstChild, secondChild, pos));
			return Stream.of(fromGensBuilder().apply(firstChild), fromGensBuilder().apply(secondChild))
					.collect(Collectors.toList());
		};
	}

	private void add(U first, U second, List<U> resFirst, List<U> resSecond, int pos) {
		boolean cross = pos < point ? true : false;
		U addF = cross ? second : first;
		U addS = cross ? first : second;
		if (null != addF) {
			resFirst.add(addF);
		}
		if (null != addS) {
			resSecond.add(addS);
		}
	}

}