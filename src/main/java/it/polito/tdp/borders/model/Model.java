package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO dao; 
	private Map<Integer, Country> idMapCountries; 
	private Graph<Country, DefaultEdge> graph; 

	public Model() {
	
		this.dao= new BordersDAO();
		this.idMapCountries= new HashMap<>(); 
		this.dao.loadAllCountries(idMapCountries);
	}
	
	public void creaGrafo(int anno) {
		this.graph= new SimpleGraph<>(DefaultEdge.class);
		
		//vertex and edges 
		for (CoupleCountries cc : this.dao.getCountryPairs(anno, idMapCountries)) {
			
			if (!this.graph.containsVertex(cc.getC1())) {
				this.graph.addVertex(cc.getC1()); 
			}
			if (!this.graph.containsVertex(cc.getC2())) {
				this.graph.addVertex(cc.getC2()); 
			}
			
			this.graph.addEdge(cc.getC1(), cc.getC2()); 
		}
		
	}
	
	public int nVertex() {
		return this.graph.vertexSet().size(); 
	}
	public int nArchi() {
		return this.graph.edgeSet().size(); 
	}
	
	//Ogni Country con il suo numero di stati confinanti
	public List<CountryNum> getCountryConConfini(){
		List<CountryNum> lista= new ArrayList<>(); 
		
		for (Country c: this.graph.vertexSet()) {
			int confini= this.graph.degreeOf(c); 
			lista.add(new CountryNum(c, confini)); 
			
		}
		Collections.sort(lista);
		return lista; 
	}
	
	public int componentiConnesse() {
		ConnectivityInspector<Country, DefaultEdge> insp= new ConnectivityInspector<Country, DefaultEdge>(this.graph); 
		
		return insp.connectedSets().size(); 
		
		
	}

	public List<Country> getCountries() {
		List<Country> lista= new ArrayList<>(this.graph.vertexSet()); 
		Collections.sort(lista);
		return lista ; 
	}

	//visita in ampiezza
	public List<Country> viciniAmpiezza(Country partenza){
		List<Country> lista= new ArrayList<>();
		BreadthFirstIterator <Country, DefaultEdge>iter= new BreadthFirstIterator<>(this.graph, partenza); 
		
		while(iter.hasNext()) {
			lista.add(iter.next()); 
		}
		return lista; 
	}
	

	//visita in profondita
	public List<Country> viciniProfondita(Country partenza){
		List<Country> lista= new ArrayList<>();
		DepthFirstIterator <Country, DefaultEdge>iter= new DepthFirstIterator<>(this.graph, partenza); 
		
		while(iter.hasNext()) {
			lista.add(iter.next()); 
		}
		return lista; 
	}
	
	//visita ricorsiva
	public List<Country> viciniRicorsione(Country partenza){
		List<Country> vicini= new ArrayList<>(); 
		
		vicini.add(partenza); 
		ricorsione(vicini); 
		return vicini; 
	}

	private void ricorsione(List<Country> vicini) {
		
		
		List<Country> confinanti= Graphs.neighborListOf(this.graph, vicini.get(vicini.size()-1)); 
		for (Country c : confinanti) {
			if (!vicini.contains(c)) {
				vicini.add(c); 
				ricorsione(vicini); 
				//no backtracking!!!!!!!!
			}
			
		}
		
	}
	
	//visita iterativa (come in videoSoluzione)
	public List<Country> viciniIterativa(Country partenza){
		List<Country> visitati= new ArrayList<>(); 
		List<Country> daVisitare= new ArrayList<>();
		
		visitati.add(partenza); 
		daVisitare.addAll(Graphs.neighborListOf(this.graph, partenza)); 
		
		while(!daVisitare.isEmpty()) {
			Country temp= daVisitare.remove(0); //ne tolgo sempre il primo
			visitati.add(temp); 
			List<Country> vicini= Graphs.neighborListOf(this.graph, temp); //ne prendo i suoi vicini
			vicini.removeAll(visitati);  //dai suoi vicini tolgo quelli gia' visitati
			vicini.removeAll(daVisitare); //ma anche quelli da visitare 
			
			daVisitare.addAll(vicini); 
		}
		
		return visitati; 
		
		
	}
}
