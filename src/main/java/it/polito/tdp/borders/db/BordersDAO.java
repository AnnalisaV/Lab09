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
import it.polito.tdp.borders.model.CoupleCountries;

public class BordersDAO {

	public void loadAllCountries(Map<Integer, Country> idMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		//List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				//System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			
				if (!idMap.containsKey(rs.getInt("ccode"))) {
					Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), 
							rs.getString("StateNme"));
					idMap.put(c.getId(), c); 
				}
			}
			
			conn.close();
			//return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<CoupleCountries> getCountryPairs(int anno, Map<Integer, Country> idMap) {

		String sql="SELECT state1no, state2no " + 
				"FROM contiguity " + 
				"WHERE state1no>state2no AND " + 
				"conttype=? AND YEAR<=? ";
		List<CoupleCountries> lista= new ArrayList<>(); 
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, 1);
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c1= idMap.get(rs.getInt("state1no")); 
				Country c2= idMap.get(rs.getInt("state2no"));
				
				CoupleCountries cc= new CoupleCountries(c1, c2);
				lista.add(cc); 
			}
			
			conn.close();
			return lista; 

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	
}
