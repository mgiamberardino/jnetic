package com.mgiamberardino.jnetic;

import java.util.List;

public interface Individual<U> {
	
	public static interface Factory<T extends Individual<U>, U> {
		
		/**
		 * Interface to build an individual from an ordered list of gens
		 * @return new individual
		 */
		public T build(List<U> gens);
		
		/**
		 * Interface to build a random individual
		 * @return new individual
		 */
		public T buildRandom();

		/**
		 * Interface to generate random gens
		 * @return a new random gen
		 */
		public U buildGen(Integer pos);
		
	}
	
	/**
	 * Interface to retrieve and ordered list of gens from the 
	 * individual
	 * @return list of gens
	 */
	public List<U> gens();

	/**
	 * Interface to determine if the individual is valid
	 * @return true if the individual is valid or false if not
	 */
	public boolean isValid();
	
	/**
	 * Returns the aptitude value
	 * @return aptitude value
	 */
	public Double getAptitude();
}
