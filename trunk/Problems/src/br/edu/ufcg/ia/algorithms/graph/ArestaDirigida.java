package br.edu.ufcg.ia.algorithms.graph;

public class ArestaDirigida extends Aresta {
	
	public ArestaDirigida(Vertice vi, Vertice vj) {
		super(vi,vj);
		vi.addAresta(this);
	}
	
}
