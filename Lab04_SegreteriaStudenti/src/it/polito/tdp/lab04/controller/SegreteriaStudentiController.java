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
    private ChoiceBox <Corso> btnCorsi;

    @FXML
    private Button btnCercaIscrittiCorso;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnCompleta;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorsi;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

	private Model model;

	ObservableList<Corso> comboItems;
	
		
	/**
	 * Questo metodo servce a caricare 
	 * il Box Choice
	 */
	public void caricaBox() {
	comboItems = FXCollections.observableList(model.getCorsi());
	this.btnCorsi.setItems(comboItems);
	
		}
	
	/**
	 * effettua il controllo sulla matricola inserita in input
	 * @return un array avente, rispettimente, nome e cognome o, in caso di errore un array avente come valori solo {@code null} 
	 */
	public Studente controlloMatricola() {
		
		Studente studente = null;
		
	 	try {
    	
    	studente = model.cercaStudente(Integer.parseInt(this.txtMatricola.getText().trim()));
    	
    	if(studente==null) {
    		this.txtMatricola.setText("Matricola NON valida");
    		return studente;
    	}
		

    	}catch(NumberFormatException nfe) {
    		this.txtMatricola.setText("Matricola NON valida");
    		return studente;
    	}
		
		return studente;
		
	}
	
	/**
	 * Pulisce la schermata e setta il bottone iscrivi per evitare 
	 * di iscrivere erroneamente studenti
	 */
	public void setButton() {
		
		this.btnIscrivi.setDisable(true);
    	this.txtResult.clear();
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
    	
    	this.setButton();
    	
    	if(this.controlloMatricola() != null) {
    	
    	Corso questoCorso = this.btnCorsi.getValue();

    	if(questoCorso==null) { 
    			
    		
        Set <Corso> corsi = model.cercaCorsi(Integer.parseInt(this.txtMatricola.getText()));
      

        if(corsi.isEmpty()) {
    		this.txtResult.setText("Mi dispiace, questo studente non risulta iscritto a nessun corso!");
    	    return;
        }
        
        for(Corso corso : corsi) {
        	this.txtResult.appendText(corso.getCodins()+"   "+corso.getCrediti()
        	     +"    "+corso.getNome()+"    "+corso.getPd()+"\n");
        }
      }
    
    		
    	else {
    		
    		
    		boolean result = model.cercaCorso(Integer.parseInt(this.txtMatricola.getText().trim()), questoCorso.getCodins());
    		
    		if(result) {
    			this.txtResult.setText("Lo studente risulta essere iscritto al corso selezionato.");
    		}
    		else {
    			this.txtResult.setText("Lo studente NON risulta essere iscritto al corso selezionato.\n"
    					+ "Se si desidera iscrivere lo studente, premere il tasto Iscrivi, altrimenti si prema il tasto Reset");
    			
    			this.btnIscrivi.setDisable(false);
    			this.btnCorsi.setDisable(true);
    			this.btnCercaIscrittiCorso.setDisable(true);
    			this.txtMatricola.setEditable(false);
    			
    		}
    	
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


    	this.setButton();
    	
    	Corso corso = this.btnCorsi.getValue();
    	
    	//
    	
    	if(corso == null ) {
    		this.txtResult.setText("ERRORE, si prega di inserire un corso");
    	    return;
    	}
    	
    	
    	
    	Set <Studente> studenti = model.getIscrittiCorso(corso.getCodins());
    	
    	if(studenti.isEmpty()) {
    		this.txtResult.setText("Non ci sono studenti iscritti a questo corso");
    	    return;
    	}
    	for(Studente st: studenti) {
    		this.txtResult.appendText(st.getMatricola()+"   "+
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

    	this.setButton();
    	
        Corso corso  = this.btnCorsi.getValue();
        
        
        int matricola = Integer.parseInt(this.txtMatricola.getText().trim());
    	
        boolean result = model.iscriviStudente(matricola, corso.getCodins());
        
        if(result) {
        	this.txtResult.setText("Operazione riuscita con successo! Lo studente fa ora parte del corso.\n"
        			+ "Si prega di premere il pulsante Reset per una nuova operazione.");
        }
        else {
        	this.txtResult.setText("Operazione NON riuscita! Sono stati riscontrati dei problemi riguardo i dati o la connessione rispetto a questi.\n"
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
    	

    	Studente studente = this.controlloMatricola();
    	
    	if(studente!=null) {
    	
    	this.txtNome.setText(studente.getNome());
        this.txtCognome.setText(studente.getCognome());
    	
    	}
    	
    }

    /**
     * Riporta l'applicazione allo stato iniziale
     * @param event
     */
    
    @FXML
    void doReset(ActionEvent event) {

    	this.txtResult.clear();
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtMatricola.clear();
    	
    	this.btnIscrivi.setDisable(true);
    	
    	this.btnCorsi.setDisable(false);
		this.btnCercaIscrittiCorso.setDisable(false);
		this.txtMatricola.setEditable(true);
    	
    }

    @FXML
    void initialize() {
    	 
    	assert btnCorsi != null : "fx:id=\"btnCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert txtMatricola != null : "fx:id=\"btnMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnCompleta != null : "fx:id=\"btnCompleta\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert txtNome != null : "fx:id=\"btnNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert txtCognome != null : "fx:id=\"btnCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert txtResult != null : "fx:id=\"btnResult\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
         assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

         //this.btnCorsi.setItems(comboItems);
       
    }

	public void setModel(Model model) {
	
		this.model=model;
		
	}
}
