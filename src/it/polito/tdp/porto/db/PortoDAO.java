package it.polito.tdp.porto.db;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import it.polito.tdp.porto.model.Adiacenza;
import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Paper;

public class PortoDAO {

	/*
	 * Dato l'id ottengo l'autore.
	 */
	
	public List<Author> getAllAutori(Map<Integer, Author> idMap) {
		final String sql = "SELECT * FROM author";
		List<Author> res = new ArrayList<Author>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				if (idMap.get(rs.getInt("id")) == null) {
				
				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				idMap.put(autore.getId(), autore);
				res.add(autore);
			} else {
				res.add(idMap.get(rs.getInt("id")));
			}
				
		}
			conn.close();
			return res;
		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	public List<Adiacenza> getAdiacenze(Map<Integer, Author> idMap) {
		final String sql = "SELECT c1.authorid, c2.authorid "+
				"FROM creator AS c1, creator AS c2 "+
				"WHERE c1.eprintid  = c2.eprintid "+
				"AND c1.authorid  > c2.authorid "+
				"GROUP BY c1.authorid, c2.authorid";
		List<Adiacenza> res = new ArrayList<Adiacenza>(); 

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Author a1 = idMap.get(rs.getInt("c1.authorid"));
				Author a2 = idMap.get(rs.getInt("c2.authorid"));
				Adiacenza temp = new Adiacenza(a1, a2);
				res.add(temp);
			}
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	public Author getAutore(int id) {

		final String sql = "SELECT * FROM author where id=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Author autore = new Author(rs.getInt("id"), rs.getString("lastname"), rs.getString("firstname"));
				return autore;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Dato l'id ottengo l'articolo.
	 */
	public Paper getArticolo(int eprintid) {

		final String sql = "SELECT * FROM paper where eprintid=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, eprintid);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				Paper paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				return paper;
			}

			return null;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	
	public Paper getPaper(Author partenza, Author arrivo) {
		final String sql = "SELECT paper.eprintid, title, issn, publication, paper.`type`, types "+
					"FROM creator c1, creator c2, paper "+
					"WHERE c1.eprintid = c2.eprintid AND c1.eprintid = paper.eprintid "+
					"AND c1.authorid = ? AND c2.authorid = ?";
		Paper paper = null;
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getId());
			st.setInt(2, arrivo.getId());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				paper = new Paper(rs.getInt("eprintid"), rs.getString("title"), rs.getString("issn"),
						rs.getString("publication"), rs.getString("type"), rs.getString("types"));
				
			}
			
			conn.close();
			return paper;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		


		
	}
	
	
	
	
}