package br.edu.ufcg.ia.algorithms.graph;

public class ArestaNaoDirigida extends Aresta {
	
	public ArestaNaoDirigida(Vertice vi, Vertice vj) {
		super(vi,vj);
		vi.addAresta(this);
		vj.addAresta(this);
	}
}
