package it.polito.tdp.lab04.model;



import java.util.LinkedList;
import java.util.List;
import it.polito.tdp.lab04.DAO.*;


/* il model comunica con la base di dati per farsi dare i nomi dei corsi
 * ma non direttamente, ma attraverso una classe DAO (Pattern DAO)*/

public class Model {

	public List <String> getCorsi() {
		
		CorsoDAO dao = new CorsoDAO();
		
		List<Corso> corsi = dao.getTuttiICorsi();
		
		List<String> result = new LinkedList<String>();
		
		for(Corso c : corsi)
			result.add(c.getNome());
		
		result.add("");
		
		return result;
	}

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

	

	
	
	
	
}
