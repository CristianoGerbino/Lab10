package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	private Graph<Author, DefaultEdge> grafo;
	private Map<Integer, Author> idMap;
	private List<Author> listaAutori;
	private List<Adiacenza> listaAdiacenze;

	public Model() {
		idMap = new HashMap<Integer, Author>();
		PortoDAO dao = new PortoDAO();
		listaAutori = dao.getAllAutori(idMap);
		this.creaGrafo();
	}

public void creaGrafo () {
	grafo = new SimpleGraph<Author, DefaultEdge>(DefaultEdge.class);
	
	//Aggiungiamo i vertici
	Graphs.addAllVertices(grafo, listaAutori);
	
	//Aggiungiamo gli archi
	PortoDAO dao = new PortoDAO();
	listaAdiacenze = new ArrayList<Adiacenza>();
	listaAdiacenze = dao.getAdiacenze(idMap);
	
	for (Adiacenza a : listaAdiacenze) {
		grafo.addEdge(a.getA1(), a.getA2());
	}
}

public List<Author> trovaVicini (Author a) {
	List<Author> res = new ArrayList<Author>();
	res = Graphs.neighborListOf(grafo, a);
	Collections.sort(res);
	return res;
}

public List<Author> trovaNonVicini(Author a) {
	Set<Author> set = new HashSet<Author>(grafo.vertexSet());
	set.remove(a);
	
	for (Author temp : Graphs.neighborSetOf(grafo, a)) {
		set.remove(temp);
	}
	List<Author> res = new ArrayList<Author>(set);
	Collections.sort(res);
	return res;
}

public List<Paper> trovaCamminoMinimo (Author partenza, Author arrivo) {
	DijkstraShortestPath<Author, DefaultEdge> djikstra = new DijkstraShortestPath<Author, DefaultEdge>(grafo);
	GraphPath<Author, DefaultEdge> path = djikstra.getPath(partenza, arrivo);
	
	if (path == null) {
		return null;
	}
	
	List<Author> vertici = path.getVertexList();
	
	System.out.format("Lunghezza del percorso: %d\n", vertici.size());
	System.out.println("Autori :");
	for (Author aut : vertici) {
		System.out.println(aut);
	}
	
	List<Paper> papers = new LinkedList<Paper>();
	PortoDAO dao = new PortoDAO();
	
	for (int i = 0; i< vertici.size()-1; i++) {
		Paper temp = dao.getPaper(vertici.get(i), vertici.get(i+1));
		papers.add(temp);
	}
	return papers;
}



public Graph<Author, DefaultEdge> getGrafo() {
	return grafo;
}

public Map<Integer, Author> getIdMap() {
	return idMap;
}

public List<Author> getListaAutori() {
	Collections.sort(this.listaAutori);
	return listaAutori;
}






}