package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		/*for (Author a : model.getIdMap().values())
			System.out.println(a.getId()+" "+a.getFirstname()+" "+a.getLastname());
	}*/
		model.creaGrafo();
		System.out.format("Creato grafo con %d vertici e %d archi", model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size());
	}
}
