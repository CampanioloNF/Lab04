package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	/*
	 *Ottengo lo studente cercato
	 * */
	public Set<Studente> getStudenti() {

		 
		  
		  Set <Studente> studenti = new HashSet <Studente>();
		 
		  final String sql = "SELECT * FROM studente";

		try {
		 
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");

				// Crea un nuovo JAVA Bean Studente
				Studente s = new Studente(matricola, cognome, nome, cds);
                // Lo aggiungo al set
				studenti.add(s);
			}
			
				conn.close();
				
				return studenti;
				
				
		} catch (SQLException e) {
			
			//e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
}
