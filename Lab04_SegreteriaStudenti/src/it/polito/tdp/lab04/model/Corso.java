package it.polito.tdp.lab04.model;

public class Corso {

	/* Si tratta di una classe Bean, ovvero contenitore, creata per contenere le istanze
	 * della base di dati corso*/
	
	private String codins;
	private int crediti;
	private String nome;
	private int pd;
	
	public Corso(String codins, int crediti, String nome, int pd) {
		super();
		this.codins = codins;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codins == null) ? 0 : codins.hashCode());
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
		Corso other = (Corso) obj;
		if (codins == null) {
			if (other.codins != null)
				return false;
		} else if (!codins.equals(other.codins))
			return false;
		return true;
	}




	public String getCodins() {
		return codins;
	}




	public int getCrediti() {
		return crediti;
	}




	public String getNome() {
		return nome;
	}




	public int getPd() {
		return pd;
	}
	
	
	
}
