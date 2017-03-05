package com.mgiamberardino.jnetic.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.mgiamberardino.jnetic.population.Population.Parents;

public class PairStream {

	public static <T> Stream<Parents<T>> buildPairStream(List<T> list){
		int streamSize = list.size() % 2 == 0 ? list.size() / 2 : list.size() / 2 + 1; 
		return Stream.iterate(0, (i) -> i + 1).map( // natural numbers
			    new Function<Integer, Parents<T>>() {
			        Integer previous;

			        @Override
			        public Parents<T> apply(Integer index) {
			            if (list.size() > 1){
			            	T first = list.get(index * 2);
			            	T second = index * 2 == list.size() - 1 ? list.get(0) : list.get( (index * 2) + 1);
			            	return new Parents<>(first, second);
			            }
			            return null;
			        }
			    }).limit(streamSize); // drop first null
	}
	
}
