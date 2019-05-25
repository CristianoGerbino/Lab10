package it.polito.tdp.porto.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		/*for (Author a : model.getIdMap().values())
			System.out.println(a.getId()+" "+a.getFirstname()+" "+a.getLastname());
	}*/
		model.creaGrafo();
		System.out.format("Creato grafo con %d vertici e %d archi\n", model.getGrafo().vertexSet().size(), model.getGrafo().edgeSet().size());
		Author source = model.getIdMap().get(33949);
		Author dest = model.getIdMap().get(4110);
		System.out.println("Percorso tra "+source+" a "+dest);
		for (Paper p : model.trovaCamminoMinimo(source, dest)) {
			System.out.println(p);
		}
	}
}
