package com.mgiamberardino.jnetic.operators.crossers;

import java.util.List;
import java.util.function.Function;

import com.mgiamberardino.jnetic.operators.Operators;
import com.mgiamberardino.jnetic.population.Population.Parents;

public class Crossers<T,U> {
	
	private Function<T, List<U>> gensGetter;
	private Function<List<U>, T> fromGensBuilder;
	
	public Crossers(Operators.Factory<T, U> factory){
		this.gensGetter = factory.gensGetter();
		this.fromGensBuilder = factory.fromGensBuilder();
	}

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
	 */
	public Function<Parents<T>, List<T>> uniformCrosserBuilder(){
		return new UniformCrosserBuilder<T,U>()
				.gensGetter(gensGetter)
				.fromGensBuilder(fromGensBuilder)
				.build();
	}
	
	public Function<Parents<T>, List<T>> uniformCrosserBuilder(double probability){
		return new UniformCrosserBuilder<T,U>()
				.ratio(probability)
				.gensGetter(gensGetter)
				.fromGensBuilder(fromGensBuilder)
				.build();
	}
	
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
	 */
	public Function<Parents<T>, List<T>> onePointCrosserBuilder(int point){
		return new OnePointCrossoverBuilder<T,U>(point)
				.gensGetter(gensGetter)
				.fromGensBuilder(fromGensBuilder)
				.build();
	}

}
