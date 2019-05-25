package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;
    
    @FXML
    void changeBoxSecondo(ActionEvent event) {
    	Author a = this.boxPrimo.getValue();
    	
    	if (a == null) {
    		return;
    	}
    	//Aggiungiamo gli elementi alla seconda combobox
    	this.boxSecondo.getItems().clear();
    	this.boxSecondo.getItems().addAll(model.trovaNonVicini(a));
    }

    @FXML
    void handleCoautori(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Author a = this.boxPrimo.getValue();
    	if (a == null) {
    		this.txtResult.appendText("Seleziona il primo autore!\n");
    		return;
    	}
    	
    	this.txtResult.appendText("Elenco dei co-autori:\n");
    	for (Author temp : model.trovaVicini(a)) {
    		this.txtResult.appendText(temp.toString()+"\n");
    	}
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Author source = this.boxPrimo.getValue();
    	Author dest = this.boxSecondo.getValue();
    	
    	if (source == null) {
    		this.txtResult.appendText("Seleziona il primo autore!\n");
    		return;
    	}
    	
    	if (dest == null) {
    		this.txtResult.appendText("Seleziona il secondo autore!\n");
    		return;
    	}
    	List<Paper> papers = model.trovaCamminoMinimo(source, dest);
    	
    	if (papers == null) {
    		this.txtResult.appendText("Nessun percorso trovato tra "+source+" e "+dest+"\n");
    		return;
    	}
    	
    	this.txtResult.appendText("Sequenza di articoli tra "+source+" e "+dest+":\n");
    	for (Paper p : papers) {
    		this.txtResult.appendText("-"+p+"\n");
    	}
    	
    	
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxPrimo.getItems().addAll(model.getListaAutori());
	}
}
