package it.polito.tdp.lab04.controller;

import java.net.URL;

import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.lab04.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;

public class SegreteriaStudentiController {

	
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox <String> btnCorsi;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField btnMatricola;

    @FXML
    private Button btnCompleta;

    @FXML
    private TextField btnNome;

    @FXML
    private TextField btnCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea btnResult;

    @FXML
    private Button btnReset;

	private Model model;

	ObservableList<String> comboItems;
	
	/**
	 * Questo metodo servce a caricare 
	 * il Box Choice
	 */
	public void caricaBox() {
	comboItems = FXCollections.observableList(model.getCorsi());
	this.btnCorsi.setItems(comboItems);
	
		}
	
	/**
	 * 
	 * Questo metodo si occupa di ricevere da un input esterno la matricola 
	 * e restituire i corsi al quale tale studente risulta essere iscritto
	 * o, nel caso di selezione di uno specifico corso, se tale studente risulta 
	 * essere iscritto o meno, a quello specifico corso.
	 * 
	 * Nel caso in cui non sia iscritto sblocca il tasto 'Iscrivi' attraverso il 
	 * quale verrà aggiunta la coppia (matricola, codins) al database 'iscrizione'.
	 * In quest'ultimo caso vengono diabilitati gli altri pulsanti, al fine di evitare 
	 * di inserire dati errati all'interno del DB. Viene chiaramente data l'opportunità 
	 * di utilizzare il pulsante 'Reset'.
	 * 
	 * @param event
	 */
	
    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	this.btnIscrivi.setDisable(true);
    	
    	this.btnResult.clear();
    	
    	String stringa = this.btnCorsi.getValue();
    	
        try {
    	
    	this.doNome(event);
    	
        }catch(NumberFormatException nfe) {
        	this.btnMatricola.setText("Matricola NON valida");
        }
    	
        String matr = this.btnMatricola.getText();
        
        if(matr.equals("Matricolaa NON valida"))
        	return;
    	

    	if(stringa==null || stringa.trim().equals("")) {
        
        Set <Corso> corsi = model.cercaCorsi(Integer.parseInt(this.btnMatricola.getText().trim()));

        if(corsi.isEmpty()) {
    		this.btnResult.setText("Mi dispiace, questo studente non risulta iscritto a nessun corso!");
    	    return;
        }
        
        for(Corso corso : corsi) {
        	this.btnResult.appendText(corso.getCodins()+"   "+corso.getCrediti()
        	     +"    "+corso.getNome()+"    "+corso.getPd()+"\n");
        }
      }
    
    	else {
    		
    		String elenco[] = stringa.split("   ");
    		
    		boolean result = model.cercaCorso(Integer.parseInt(this.btnMatricola.getText().trim()), elenco[1].trim());
    		
    		if(result) {
    			this.btnResult.setText("Lo studente risulta essere iscritto al corso selezionato.");
    		}
    		else {
    			this.btnResult.setText("Lo studente NON risulta essere iscritto al corso selezionato.\n"
    					+ "Se si desidera iscrivere lo studente, premere il tasto Iscrivi, altrimenti si prema il tasto Reset");
    			
    			this.btnIscrivi.setDisable(false);
    			this.btnCorsi.setDisable(true);
    			this.btnCercaIscrittiCorso.setDisable(true);
    			this.btnMatricola.setEditable(false);
    			
    		}
    	}
    }
   
    /**
     * Tale metodo si occupa di resituire, nell'area di testo, un elenco di 
     * studenti che sono iscritti al corso selezionato. Nel caso non venga selezionato alcun corso
     * viene stampato, sempre nella medesima area, un messaggio di errore.
     * @param event
     */

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {

    	this.btnResult.clear();
    	this.btnIscrivi.setDisable(true);
    	
    	String s = this.btnCorsi.getValue();
    	
    	//
    	
    	if(s==null || s.trim().equals("")) {
    		this.btnResult.setText("ERRORE, si prega di inserire un corso");
    	    return;
    	}
    	
    	String elenco[] = s.split("   ");
    	
    	Set <Studente> studenti = model.getIscrittiCorso(elenco[1].trim());
    	
    	if(studenti.isEmpty()) {
    		this.btnResult.setText("Non ci sono studenti iscritti a questo corso");
    	    return;
    	}
    	for(Studente st: studenti) {
    		this.btnResult.appendText(st.getMatricola()+"   "+
    	                 st.getNome()+"   "+st.getCognome()+"   "+st.getCds()+"\n");
    	}
    	
    	}
    	  
    	
    /**
     * Si occupa di iscrivere uno studente ad un nuovo corso.
     * Il controllo sulla presenza dello studente nel DB, o dell'iscrizione duplicata viene effettuato 
     * da metodi precedenti. A iscrizione completata viene stampato un messaggio, dove si indica il successo
     * o il fallimento di tale operazione. Successivamente sarà possibile esclusivamente l'uso del tasto 'Reset'
     * @param event
     */

    @FXML
    void doIscrivi(ActionEvent event) {

    	this.btnIscrivi.setDisable(true);
    	
        String stringa  = this.btnCorsi.getValue();
        String elenco[] = stringa.split("   ");
        
        int matricola = Integer.parseInt(this.btnMatricola.getText().trim());
    	
        boolean result = model.iscriviStudente(matricola, elenco[1]);
        
        if(result) {
        	this.btnResult.setText("Operazione riuscita con successo! Lo studente fa ora parte del corso.\n"
        			+ "Si prega di premere il pulsante Reset per una nuova operazione.");
        }
        else {
        	this.btnResult.setText("Operazione NON riuscita! Sono stati riscontrati dei problemi riguardo i dati o la connessione rispetto a questi.\n"
        			+ "Si prega di premere il pulsante Reset per una nuova operazione.");
        }
        
    }

    /**
     * questo metodo restituisce il nome ed il cognome a partire dalla matricola e verifica la presenza
     * di tale studente all'interno del DB
     * 
     * @param event
     */
    @FXML
    void doNome(ActionEvent event) {
    	

    	try {
    	
    	int matricola = Integer.parseInt(this.btnMatricola.getText().trim());
    	
    	String studente[] = model.cercaStudente(matricola);
    	
    	if(studente[0]==null) {
    		this.btnMatricola.setText("Matricolaa NON valida");
    		return;
    	}		
    		
    		this.btnNome.setText(studente[0]);
    		this.btnCognome.setText(studente[1]);
    		
    	
    	
    	}catch(NumberFormatException nfe) {
    		this.btnMatricola.setText("Matricola NON valida");
    	}
    	
    }

    /**
     * Riporta l'applicazione allo stato iniziale
     * @param event
     */
    
    @FXML
    void doReset(ActionEvent event) {

    	this.btnResult.clear();
    	this.btnCognome.clear();
    	this.btnNome.clear();
    	this.btnMatricola.clear();
    	
    	this.btnIscrivi.setDisable(true);
    	
    	this.btnCorsi.setDisable(false);
		this.btnCercaIscrittiCorso.setDisable(false);
		this.btnMatricola.setEditable(true);
    	
    }

    @FXML
    void initialize() {
    	 
    	assert btnCorsi != null : "fx:id=\"btnCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnMatricola != null : "fx:id=\"btnMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnCompleta != null : "fx:id=\"btnCompleta\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnNome != null : "fx:id=\"btnNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnCognome != null : "fx:id=\"btnCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnResult != null : "fx:id=\"btnResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

         //this.btnCorsi.setItems(comboItems);
       
    }

	public void setModel(Model model) {
	
		this.model=model;
		
	}
}
