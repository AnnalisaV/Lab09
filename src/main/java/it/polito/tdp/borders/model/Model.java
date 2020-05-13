package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Map<Integer, Country> idMapCountries; 
	private BordersDAO dao; 
	private Graph<Country, DefaultEdge> graph; 
	
	
	public Model() {
		this.idMapCountries= new HashMap<>(); 
		this.dao= new BordersDAO(); 
		dao.loadAllCountries(idMapCountries); 
		
	}

	public void calcolaConfine(int anno) {
		 
		
		//creo il grafo
		this.graph= new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		//aggiunta dei vertici
		Graphs.addAllVertices(this.graph, dao.getCountries(anno,this.idMapCountries)); 
		
		//archi 
		for (Border b : dao.getCountryPairs(anno, idMapCountries)) {
			//verifica aggiuntiva per sicurezza
			if(this.graph.containsVertex(b.getCountry1()) && this.graph.containsVertex(b.getCountry2())) {
			DefaultEdge e= this.graph.getEdge(b.getCountry1(), b.getCountry2()); 
			if (e==null) {
				// non c'e' lo aggiungo /sia in un verso che nell'altro
				this.graph.addEdge(b.getCountry1(), b.getCountry2());
				this.graph.addEdge(b.getCountry2(), b.getCountry1());
			}
			else if(this.graph.getEdgeSource(e).equals(b.getCountry1())) {
				this.graph.addEdge(b.getCountry2(), b.getCountry1()); 
			}
			else if(this.graph.getEdgeSource(e).equals(b.getCountry2())) {
				this.graph.addEdge(b.getCountry1(), b.getCountry2()); 
			}
		}
	}
	}
	
	//utile solo per testare il dao
	public Map<Integer, Country> getIdMapCountries() {
		return idMapCountries;
	}
	
	public int vertexNumber() {
		return this.graph.vertexSet().size(); 
	}
	
	public int edgeNumber() {
		return this.graph.edgeSet().size(); 
	}
	
	/**
	 * Elenco dei {@code Country} con numero di {@code Country} confinanti
	 * @return
	 */
	public String elencoCountries() {
		
		String s=""; 
		
		for (Country c : this.graph.vertexSet()) {
			s+= c.toString()+" confina con "+this.graph.degreeOf(c)+" altri Countries\n"; 
		}
		return s; 
	}
	
	public int componentiConnesse() {
		ConnectivityInspector<Country, DefaultEdge> insp= new ConnectivityInspector<>(this.graph);
		return insp.connectedSets().size(); 
	}
}
