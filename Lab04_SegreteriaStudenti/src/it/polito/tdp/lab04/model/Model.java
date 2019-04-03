package it.polito.tdp.lab04.model;

import java.util.*;

import it.polito.tdp.lab04.DAO.*;

/* il model comunica con la base di dati per farsi dare i nomi dei corsi
 * ma non direttamente, ma attraverso una classe DAO (Pattern DAO)*/

public class Model {
	
	private Map <String, Corso> mappaCorsi = new HashMap <String, Corso>();
	

	/**
	 * Tale metodo riceve da una classe DAO, 
	 * secondo il pattern DAO, la lista di tutti i corsi presenti sul DB.
	 * 
	 * @return lista di corsi, rappresentati da stringhe. Una stringa è formata ne seguente modo
	 * <nomeCorso> tree space <codins>.
	 */
	
	public List <String> getCorsi() {
		
		CorsoDAO dao = new CorsoDAO();
		
		List<Corso> corsi = dao.getTuttiICorsi();
		
		List<String> result = new LinkedList<String>();
		
		for(Corso c : corsi) {
			result.add(c.getNome()+"   "+c.getCodins());
		    mappaCorsi.put(c.getCodins(), c);
		}
		result.add("");
		
		return result;
	}

	/**
	 * Data una matricola si occupa di restituire un array di stringhe dato dal 
	 * nome e dal cognome (nelle rispettive posizioni)
	 * Ciò avviene mediante la classe StudenteDAO che comunica direttamente con il DB
	 * 
	 * @param matricola
	 * @return array di stringhe di dimensione 2, avente, nel rispettivo ordine,
	 * nome e cognome.
	 */
	
	public String [] cercaStudente(int matricola) {
	
		StudenteDAO dao = new StudenteDAO();
		
		String studente[] = {null,null};
		
		Studente s = dao.getStudente(matricola);
		
		if(s!=null) {
			
			studente[0] = s.getNome();
			studente[1] = s.getCognome();
		}
			
	
		return studente;
	}
	
	/**
	 * Dato il codice di un insegnamento restituisce un Set di {@link Studente} che partecipano a tale {@link Corso}.
	 * Si serve di una classe DAO
	 * @param codice
	 * @return Set di {@link Studente} iscritti al corso che ha per codice la stringa passata come parametro
	 */

	public Set<Studente> getIscrittiCorso(String codice) {
		
		CorsoDAO dao = new CorsoDAO();
		
		Corso corso = null;
		
		for(String cod : this.mappaCorsi.keySet()) {
			
			if(codice.equals(cod)) {
				corso=this.mappaCorsi.get(cod);
				break;
			}
			
		}
		
		Set<Studente> iscritti = dao.getStudentiIscrittiAlCorso(corso);
		
		return iscritti;
	}
	
	/**
	 * Restituisce un Set di {@link Corso} che sono seguiti dallo {@link Studente} la cui
	 * matricola è passata come parametro
	 * 
	 * @param matricola
	 * @return un Set di {@link Corso} che sono seguiti dallo {@link Studente} 
	 */

	public Set<Corso> cercaCorsi(int matricola) {
		
		CorsoDAO dao = new CorsoDAO();
		
	    Set <String> codCorsi = dao.cercaCorsi(matricola);
		
	    Set <Corso> corsiIscritto = new HashSet<Corso>();
	    
	    
	    for(String cod : this.mappaCorsi.keySet()) {
	    	if(codCorsi.contains(cod))
	    		corsiIscritto.add(mappaCorsi.get(cod));
	    }
	    	
		return corsiIscritto;
	}
	/**
	 * Tale metodo si occupa di verificare se uno {@link Studente}, avente la matricola
	 * passata come parametro, risulti essere iscritto ad un {@link Corso}, il cui codice 
	 * è passato come parametro. Usa il metodo {@link #cercaCorsi(int matricola)}
	 * @param matricola
	 * @param codins
	 * @return {@code true} se lo {@link Studente} risulta essere iscritto al {@link Corso}, {@code false} altrimenti.
	 */

	public boolean cercaCorso(int matricola, String codins) {
		
		Set <Corso> corsiIscritti = this.cercaCorsi(matricola);
		
		for(Corso corso: corsiIscritti) {
			if(codins.equals(corso.getCodins()))
				return true;
		}
		
		return false;
	}

	/**
	 * Si occupa dell'inserimento, nel DB iscrizione della matricola e del codice del corso passati come parametro.
	 * @param matricola
	 * @param codins
	 * @return {@code true} se lo {@link Studente} risulta essere stato iscritto al {@link Corso}
	 * con successo, {@code false} altrimenti.
	 */
	
	public boolean iscriviStudente(int matricola, String codins) {
		
		CorsoDAO dao = new CorsoDAO();
		
		return dao.inscriviStudenteACorso(matricola, codins);
	}	
	
}
