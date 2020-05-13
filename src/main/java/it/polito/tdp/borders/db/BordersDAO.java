package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMapCountries) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		//List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if (!idMapCountries.containsKey(rs.getInt("Ccode"))) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
					Country c= new Country(rs.getInt("Ccode"), rs.getString("StateAbb"), rs.getString("StateNme")); 
					idMapCountries.put(c.getCode(), c); 
			}
			}
			conn.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	/**
	 * Coppie di {@code Country} fra i quali c'e' confine di tipo terra (=1)
	 * @param anno anno in questione
	 * @return lista di {@code Border}
	 */
	public List<Border> getCountryPairs(int anno, Map<Integer, Country> idMap) {

		String sql="SELECT state1no, state2no " + 
				"FROM contiguity " + 
				"WHERE conttype=? AND YEAR<=? AND state1no != state2no " + 
				"GROUP BY state1no, state2no "; 
		
		List<Border> confini= new ArrayList<>(); 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,  1); //per il tipo di confine
			st.setInt(2,  anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				//li recupero dalla mappa
				Country c1= idMap.get(rs.getInt("state1no")); 
				Country c2= idMap.get(rs.getInt("state2no")); 
				
				Country confine1= new Country(c1.getCode(), c1.getSigla(), c1.getName()); 
				Country confine2= new Country(c2.getCode(), c2.getSigla(), c2.getName());
				Border b= new Border(confine1, confine2); 
				confini.add(b); 
			}
				
			conn.close();
			return confini; 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
		
	}
	
	/**
	 * Dato un anno, trova tutti i {@code Country} con confini di terra ( = di tipo 1) a quel tempo
	 * @param anno anno in questione
	 * @param idMap
	 * @return lista di {@code Country}
	 */
	public List<Country> getCountries(int anno, Map<Integer, Country> idMap){
		String sql="SELECT  distinct state1no " + 
				"FROM contiguity " + 
				"WHERE conttype=? AND YEAR<= ? "; 
		List<Country> countries= new ArrayList<>(); 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,  1); //per il tipo di confine
			st.setInt(2,  anno);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Country c= idMap.get(rs.getInt("state1no")); 
				Country nuovo= new Country(c.getCode(),c.getSigla(), c.getName()); 
				countries.add(nuovo); 
				
			}
				
			conn.close();
			return countries; 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
			}

	
}

