package br.edu.ufcg.ia.algorithms.graph;

public class ArestaNaoDirigidaValorada extends ArestaValorada {
	
	public ArestaNaoDirigidaValorada(Vertice vi, Vertice vj, int custo) {
		super(vi, vj, custo);
		vi.addAresta(this);
		vj.addAresta(this);
	}
}
