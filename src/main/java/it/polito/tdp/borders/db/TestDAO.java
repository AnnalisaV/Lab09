package it.polito.tdp.borders.db;

import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();

		System.out.println("Lista di tutte le nazioni:");
		//List<Country> countries = dao.loadAllCountries();
		Model m= new Model(); 
		List<Country>  lista= dao.getCountries(1920, m.getIdMapCountries()); 
		for (Country c : lista) {
			System.out.println(c.toString()+"\n"); 
		}
		
		 
		List<Border>  lista2= dao.getCountryPairs(1920, m.getIdMapCountries()); 
		for (Border b : lista2) {
			System.out.println(b.toString()+"\n"); 
		}
	}
}
