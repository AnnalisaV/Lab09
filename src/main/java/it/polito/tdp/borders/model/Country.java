package it.polito.tdp.borders.model;

public class Country implements Comparable<Country> {
	
	private Integer id; 
	private String abbreviazione; 
	private String nome;
	
	/**
	 * @param id
	 * @param abbreviazione
	 * @param nome
	 */
	public Country(Integer id, String abbreviazione, String nome) {
		super();
		this.id = id;
		this.abbreviazione = abbreviazione;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public String getAbbreviazione() {
		return abbreviazione;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.id+" "+this.nome;
	}

	@Override
	public int compareTo(Country o) {
		
		return this.nome.compareTo(o.nome);
	} 
	
	
	
	

}
