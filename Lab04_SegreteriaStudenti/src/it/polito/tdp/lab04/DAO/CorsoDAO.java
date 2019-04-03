package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {

	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);

				// Crea un nuovo JAVA Bean Corso
				Corso c = new Corso(codins, numeroCrediti, nome, periodoDidattico);
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(c);
				
			}
			
			conn.close();

			return corsi;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 * 
	 * Non è stato implementato perchè essendo pochi ho preferito salvare i corsi
	 * all'interno di una mappa nel model
	 * 
	 */
	public void getCorso(Corso corso) {
	
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public Set<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		String cod = corso.getCodins();
		
		Set <Studente> studentiIscritti = new HashSet<Studente>();
		
		final String sql = "SELECT  studente.matricola, studente.cognome, studente.nome, studente.CDS "
				+ "FROM studente, iscrizione "
				+ "WHERE studente.matricola = iscrizione.matricola AND iscrizione.codins = ?";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, cod);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");
				
				Studente s = new Studente(matricola, cognome, nome, cds);
				studentiIscritti.add(s);
				
			}
			
			conn.close();
			return studentiIscritti;
			
		}catch(SQLException e) {
			throw new RuntimeException("Errore Db");
		}
		
		
	}

	

	public Set<String> cercaCorsi(int matricola) {
		
		
		final String sql = "SELECT iscrizione.codins FROM iscrizione WHERE iscrizione.matricola  = ?";

		Set<String> corsiIscritto = new HashSet<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, matricola);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				
				corsiIscritto.add(codins);
				
			}
			
			conn.close();

			return corsiIscritto;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(int matricola, String codins) {
		// TODO
		// ritorna true se l'iscrizione e' avvenuta con successo
		
		final String sql = "INSERT INTO iscrizione VALUES (?, ?)";
		
		try {
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, matricola);
			st.setString(2, codins);
			
			st.executeUpdate();
			
			conn.close();
			
			return true;
		
		}catch(SQLException e) {
			
			return false;
			
		}
		
		
	}
}
