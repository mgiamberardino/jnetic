package com.mgiamberardino.jnetic.population;

import java.util.List;
import java.util.stream.Stream;

public class Population<T> {

	private List<T> members;
	
	Population(List<T> members){
		this.members = members;
	}
	
	public Integer size() {
		return members.size();
	}

	public Stream<T> stream() {
		return (Stream<T>) Stream.of(members.toArray());
	}

	public void evolve(Integer iterations) {
		
	}

}
