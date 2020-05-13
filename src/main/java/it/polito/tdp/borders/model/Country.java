package it.polito.tdp.borders.model;

public class Country {

	private int code; //id
	private String sigla; //nome abbreviato
	private String name;  //nome completo 
	
	
	/**
	 * @param code
	 * @param sigla
	 * @param name
	 */
	public Country(int code, String sigla, String name) {
		super();
		this.code = code;
		this.sigla = sigla;
		this.name = name;
	}


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getSigla() {
		return sigla;
	}


	public void setSigla(String sigla) {
		this.sigla = sigla;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Country other = (Country) obj;
		if (code != other.code)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Country " + code + ", " + sigla + " " + name;
	}
	
	
	
	
}
