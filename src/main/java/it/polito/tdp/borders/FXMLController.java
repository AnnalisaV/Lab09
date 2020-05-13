
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {

    	txtResult.clear(); 
    	//controllo sull'input 
    	int anno; 
    	if(txtAnno.getText().length()==0) {
    		txtResult.appendText("ERRORE : Inserire anno \n");
    		return; 
    	}
    	try {
    		anno= Integer.parseInt(txtAnno.getText()); 
    	}catch(Exception e) {
    		txtResult.appendText("ERRORE : Inserire anno (NUMERO) \n");
    		return;
    	}
    	 
    	if (anno <1816 || anno >2006 ) {
    		txtResult.appendText("ERRORE : Anno non valido! Considerare l'intervallo 1816-2006 \n");
    		return; 
    	}
    	
    	//tutto ok coi controlli 
    	this.model.calcolaConfine(anno); 
    	txtResult.appendText("Grafo creato con "+model.vertexNumber()+" vertex and "+model.edgeNumber()+" edges \n");
    	txtResult.appendText("ELENCO : \n");
    	txtResult.appendText(this.model.elencoCountries());
    	txtResult.appendText("Numero di componenti connesse nel grafo : "+this.model.componentiConnesse());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
