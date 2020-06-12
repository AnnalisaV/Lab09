
package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryNum;
import it.polito.tdp.borders.model.CoupleCountries;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox<Country> boxCountries;

    @FXML
    private Button btnVicini;
    
    @FXML
    void doCalcolaConfini(ActionEvent event) {

    	txtResult.clear();
    	int anno=0; 
    	if (txtAnno.getText().length()==0) {
    		txtResult.appendText("ERRORE : Inserire un anno\n");
    		return; 
    	}
    	
    	try {
    		anno= Integer.parseInt(this.txtAnno.getText());
    	}catch(NumberFormatException nfe) {
    		txtResult.appendText("ERRORE : Inserire un anno in numeri\n");
    		return;
    		
    	}
    	if (anno<1816 || anno>2006) {
    		txtResult.appendText("ERRORE : Inserire anno compreso fra 1816-2006\n");
    		return;
    		
    	}
    	
    	model.creaGrafo(anno);
    	txtResult.appendText("Grafo con "+model.nVertex()+" vertici e "+model.nArchi()+" archi\n");
    	txtResult.appendText("\nStati con confini : \n");
    	for (CountryNum cc : this.model.getCountryConConfini()) {
    		txtResult.appendText(cc.toString()+"\n");
    	}
    	txtResult.appendText("\n\nStati connessi: "+model.componentiConnesse()+"\n");
    	
    	this.boxCountries.getItems().removeAll(this.boxCountries.getItems()); 
    	this.boxCountries.getItems().addAll(model.getCountries()); 
    	this.btnVicini.setDisable(false);
    	
    	
    }
    
    @FXML
    void doTrovaVicini(ActionEvent event) {

    	txtResult.clear();
    	if(this.boxCountries.getValue()==null) {
    		txtResult.appendText("ERRORE : Selezionare un Country di partenza! \n");
    		return; 
    	}
    	
    	txtResult.appendText(""+this.model.viciniRicorsione(this.boxCountries.getValue()).size()+"\n");
    	for (Country c : this.model.viciniRicorsione(this.boxCountries.getValue())) {
    		txtResult.appendText(c.toString()+"\n");
    	}
    }


    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnVicini.setDisable(true);
    }
}
