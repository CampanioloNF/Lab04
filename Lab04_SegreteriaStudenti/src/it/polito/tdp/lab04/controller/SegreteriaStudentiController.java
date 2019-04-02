package it.polito.tdp.lab04.controller;

import java.net.URL;
import java.util.ResourceBundle;

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
	
    @FXML
    void doCercaCorsi(ActionEvent event) {

    }
    

    @FXML
    void doCercaIscrittiCorso(ActionEvent event) {

    }

    @FXML
    void doIscrivi(ActionEvent event) {

    }

    /**
     * questo metodo restituisce il nome ed il cognome a partire dalla matricola
     * @param event
     */
    @FXML
    void doNome(ActionEvent event) {
    	

    	try {
    	
    	int matricola = Integer.parseInt(this.btnMatricola.getText());
    	
    	String ris = model.cercaStudente(matricola);
    	
    	if(ris==null) {
    		this.btnMatricola.setText("Matricolaa NON valida");
    	}

    	else {
    		
    		String nome[] = ris.split(" ");
    		this.btnNome.setText(nome[0]);
    		this.btnCognome.setText(nome[1]);
    		
    	}
    	
    	}catch(NumberFormatException nfe) {
    		this.btnMatricola.setText("Matricola NON valida");
    	}
    	
    }

    @FXML
    void doReset(ActionEvent event) {

    	this.btnResult.clear();
    	this.btnCognome.clear();
    	this.btnNome.clear();
    	this.btnMatricola.clear();
    	
    	
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
