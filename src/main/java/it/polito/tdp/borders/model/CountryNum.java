package it.polito.tdp.borders.model;

public class CountryNum implements Comparable<CountryNum>{

	
	private Country c; 
	private int confini;
	
	
	/**
	 * @param c
	 * @param confini
	 */
	public CountryNum(Country c, int confini) {
		super();
		this.c = c;
		this.confini = confini;
	}


	public Country getC() {
		return c;
	}


	public void setC(Country c) {
		this.c = c;
	}


	public int getConfini() {
		return confini;
	}


	public void setConfini(int confini) {
		this.confini = confini;
	}


	@Override
	public String toString() {
		return this.c.toString()+" con "+this.confini+" confini";
	}


	//nome alfabetico
	@Override
	public int compareTo(CountryNum o) {
		return this.c.getNome().compareTo(o.c.getNome());
	} 
	
	
	
	
}
