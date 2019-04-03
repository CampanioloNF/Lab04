package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	/*
	 *Ottengo lo studente cercato
	 * */
	public Studente getStudente(int cerco) {

		 
		  final String sql = "SELECT * FROM studente WHERE matricola = ? ";

		try {
		 
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, cerco);
            
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				int matricola = rs.getInt("matricola");
				String cognome = rs.getString("cognome");
				String nome = rs.getString("nome");
				String cds = rs.getString("CDS");

				// Crea un nuovo JAVA Bean Studente
				
				//if(cds == null )
				//	cds="";
				
				Studente s = new Studente(matricola, cognome, nome, cds);
				System.out.println(matricola+" "+nome+" "+cognome+" "+cds );
				conn.close();
				return s;
				
			}
			
				conn.close();
				return null;
				
				
		} catch (SQLException e) {
			
			//e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	
	
}
