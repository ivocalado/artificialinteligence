package br.edu.ufcg.ia.algorithms.examples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import br.edu.ufcg.ia.algorithms.search.AEstrela;
import br.edu.ufcg.ia.algorithms.search.Aleatorio;
import br.edu.ufcg.ia.algorithms.search.Antecessor;
import br.edu.ufcg.ia.algorithms.search.BuscaProfundidade;
import br.edu.ufcg.ia.algorithms.search.Estado;
import br.edu.ufcg.ia.algorithms.search.Heuristica;
import br.edu.ufcg.ia.algorithms.search.Nodo;

/**
 * Representa um estado do mundo para o problema do caixeiro viajante.
 * 
 * No caso o estado e uma das cidades.
 */
public class EstadoCaixeiroViajante implements Estado, Antecessor, Heuristica,
		Aleatorio {
	private SimpleWeightedGraph<String, DefaultWeightedEdge> g;
	public List<EstadoCaixeiroViajante> percurso;
	private String estado;
	public double custo;
	private String caminho;

	public String getDescricao() {
		StringBuffer ds = new StringBuffer(
				"Encontra o caminho de custo mais baixo visitando todos os nós do grafo (Problema do Caixeiro Viajante - PCV)" +
				" Obs.: O valor do custo está entre parênteses:\n");
		for (String estado : g.vertexSet()) {
			ds.append("  saindo de " + estado + " para ");
			for (DefaultWeightedEdge edge : g.edgesOf(estado)) {

				ds.append(g.getEdgeTarget(edge).equals(estado) ? g
						.getEdgeSource(edge) : g.getEdgeTarget(edge) + "("
						+ g.getEdgeWeight(edge) + "), ");
			}
			ds.append("\n");
		}

		return ds.toString();
	}

	public EstadoCaixeiroViajante(String c,
			SimpleWeightedGraph<String, DefaultWeightedEdge> g) {
		this.g = g;
		this.estado = c;
		caminho = "" + c;
		percurso = new ArrayList<EstadoCaixeiroViajante>();
	}

	public EstadoCaixeiroViajante(String c, double custo, String pai,
			SimpleWeightedGraph<String, DefaultWeightedEdge> g) {
		this.g = g;
		this.estado = c;
		this.custo = custo;
		this.caminho = pai + " -> " + c;
		percurso = new ArrayList<EstadoCaixeiroViajante>();
	}

	public boolean equals(Object o) {
		if (o instanceof EstadoCaixeiroViajante) {
			EstadoCaixeiroViajante e = (EstadoCaixeiroViajante) o;
			return e.getEstado().equals(this.getEstado());
		}
		return false;
	}

	public int hashCode() {
		return estado.hashCode();
	}

	public boolean ehMeta(Nodo n) {
		return n.getProfundidade() == (g.vertexSet().size() - 1);
	}

	/**
	 * Custo para geracao de um estado
	 */
	public int custo() {
		return (int) custo;
	}

	/**
	 * gera uma lista de sucessores do nodo.
	 */
	public List<Estado> sucessores() {
		// System.out.println(caminho);
		List<Estado> suc = new LinkedList<Estado>(); // a lista de sucessores

		for (DefaultWeightedEdge edge : g.edgesOf(estado)) {
			suc.add(new EstadoCaixeiroViajante(g.getEdgeSource(edge).equals(
					estado) ? g.getEdgeTarget(edge) : g.getEdgeSource(edge), g
					.getEdgeWeight(edge), caminho, g));

		}
		return suc;
	}

	public List<Estado> antecessores() {
		return sucessores();
	}

	public String toString() {

		return estado + "\n";
	}

	public String toStringAll() {
		String saida = "";
		System.out.println(percurso.size());
		for (Iterator<EstadoCaixeiroViajante> iterator = percurso.iterator(); iterator
				.hasNext();) {
			saida += ((EstadoCaixeiroViajante) iterator.next()).getEstado();
			if (iterator.hasNext())
				saida += ", ";
		}
		return saida + "\n";
	}

	public int h(Nodo n) {
		double value = 0.0;
		for (DefaultWeightedEdge edge : g.edgeSet())
			value += g.getEdgeWeight(edge);
		value /= (double) g.edgeSet().size();
		return (int) ((g.edgeSet().size() - n.getProfundidade()) * value);
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Estado geraAleatorio() {
		return new EstadoCaixeiroViajante(
				(String) g.vertexSet().toArray()[(int) Math.ceil(Math.random()
						* g.vertexSet().size() -1)], 0, "", g);
	}

	public static void main(String[] a) throws Exception {

/*		SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
				new ClassBasedEdgeFactory<String, DefaultWeightedEdge>(
						DefaultWeightedEdge.class));

		Properties p = new Properties();
		p.load(new FileInputStream("estados.properties"));
		String t = p.getProperty("est1");
		for (Object estado : p.keySet()) {
			g.addVertex(p.getProperty((String) estado));
		}

		g.setEdgeWeight(
				g.addEdge(p.getProperty("est1"), p.getProperty("est2")), 11.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est1"), p.getProperty("est5")), 20.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est1"), p.getProperty("est8")), 82.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est2"), p.getProperty("est7")), 60.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est2"), p.getProperty("est8")), 68.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est2"), p.getProperty("est4")), 44.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est5"), p.getProperty("est4")), 77.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est4"), p.getProperty("est3")), 45.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est3"), p.getProperty("est7")), 40.0);

		g.setEdgeWeight(
				g.addEdge(p.getProperty("est3"), p.getProperty("est9")), 60.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est9"), p.getProperty("est6")), 38.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est6"), p.getProperty("est7")), 47.0);
		g.setEdgeWeight(
				g.addEdge(p.getProperty("est7"), p.getProperty("est8")), 20.0);

		*/

		SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
				new ClassBasedEdgeFactory<String, DefaultWeightedEdge>(
						DefaultWeightedEdge.class));
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addVertex("d");
		g.addVertex("e");
		g.addVertex("f");
		g.addVertex("g");
		g.addVertex("h");
		g.addVertex("i");
		g.addVertex("j");
		
		g.addVertex("l");
		g.addVertex("m");
		g.addVertex("n");
		g.addVertex("o");
		g.addVertex("p");
		
		
		g.setEdgeWeight(g.addEdge("a", "b"), 42.);
		g.setEdgeWeight(g.addEdge("a", "c"), 61.);
		g.setEdgeWeight(g.addEdge("a", "d"), 30.);
		g.setEdgeWeight(g.addEdge("a", "e"), 17.);
		g.setEdgeWeight(g.addEdge("a", "f"), 82.);
		g.setEdgeWeight(g.addEdge("a", "g"), 31.);
		g.setEdgeWeight(g.addEdge("a", "h"), 11.);		
		g.setEdgeWeight(g.addEdge("a", "i"), 1.);
		g.setEdgeWeight(g.addEdge("a", "j"), 81.);
		
		g.setEdgeWeight(g.addEdge("a", "l"), 2.);
		g.setEdgeWeight(g.addEdge("a", "m"), 631.);
		g.setEdgeWeight(g.addEdge("a", "n"), 4.);		
		g.setEdgeWeight(g.addEdge("a", "o"), 9.);
		g.setEdgeWeight(g.addEdge("a", "p"), 8.);
		
		g.setEdgeWeight(g.addEdge("b", "c"), 14.);
		g.setEdgeWeight(g.addEdge("b", "d"), 87.);
		g.setEdgeWeight(g.addEdge("b", "e"), 28.);
		g.setEdgeWeight(g.addEdge("b", "f"), 70.);
		g.setEdgeWeight(g.addEdge("b", "g"), 19.);
		g.setEdgeWeight(g.addEdge("b", "h"), 33.);
		g.setEdgeWeight(g.addEdge("b", "i"), 3.);
		g.setEdgeWeight(g.addEdge("b", "j"), 2.);
		
		g.setEdgeWeight(g.addEdge("b", "l"), 7.);
		g.setEdgeWeight(g.addEdge("b", "m"), 159.);
		g.setEdgeWeight(g.addEdge("b", "n"), 383.);
		g.setEdgeWeight(g.addEdge("b", "o"), 13.);
		g.setEdgeWeight(g.addEdge("b", "p"), 2.);
		
		g.setEdgeWeight(g.addEdge("c", "d"), 20.);
		g.setEdgeWeight(g.addEdge("c", "e"), 81.);
		g.setEdgeWeight(g.addEdge("c", "f"), 21.);
		g.setEdgeWeight(g.addEdge("c", "g"), 8.);
		g.setEdgeWeight(g.addEdge("c", "h"), 29.);		
		g.setEdgeWeight(g.addEdge("c", "i"), 9.);
		g.setEdgeWeight(g.addEdge("c", "j"), 2.);
		
		g.setEdgeWeight(g.addEdge("c", "l"), 231.);
		g.setEdgeWeight(g.addEdge("c", "m"), 8.);
		g.setEdgeWeight(g.addEdge("c", "n"), 9.);		
		g.setEdgeWeight(g.addEdge("c", "o"), 1.);
		g.setEdgeWeight(g.addEdge("c", "p"), 92.);
		
		
		g.setEdgeWeight(g.addEdge("d", "e"), 34.);
		g.setEdgeWeight(g.addEdge("d", "f"), 33.);
		g.setEdgeWeight(g.addEdge("d", "g"), 91.);
		g.setEdgeWeight(g.addEdge("d", "h"), 10.);
		g.setEdgeWeight(g.addEdge("d", "i"), 1.);
		g.setEdgeWeight(g.addEdge("d", "j"), 18.);
		
		g.setEdgeWeight(g.addEdge("d", "l"), 3.);
		g.setEdgeWeight(g.addEdge("d", "m"), 491.);
		g.setEdgeWeight(g.addEdge("d", "n"), 1.);
		g.setEdgeWeight(g.addEdge("d", "o"), 6.);
		g.setEdgeWeight(g.addEdge("d", "p"), 1.);
//		g.
		
		g.setEdgeWeight(g.addEdge("e", "f"), 41.);
		g.setEdgeWeight(g.addEdge("e", "g"), 34.);
		g.setEdgeWeight(g.addEdge("e", "h"), 82.);
		g.setEdgeWeight(g.addEdge("e", "i"), 8.);
		g.setEdgeWeight(g.addEdge("e", "j"), 2.);

		g.setEdgeWeight(g.addEdge("e", "l"), 4.);
		g.setEdgeWeight(g.addEdge("e", "m"), 374.);
		g.setEdgeWeight(g.addEdge("e", "n"), 8.);
		g.setEdgeWeight(g.addEdge("e", "o"), 18.);
		g.setEdgeWeight(g.addEdge("e", "p"), 2.);
		
		g.setEdgeWeight(g.addEdge("f", "g"), 19.);
		g.setEdgeWeight(g.addEdge("f", "h"), 32.);
		g.setEdgeWeight(g.addEdge("f", "i"), 2.);
		g.setEdgeWeight(g.addEdge("f", "j"), 3.);
		
		g.setEdgeWeight(g.addEdge("f", "l"), 1.);
		g.setEdgeWeight(g.addEdge("f", "m"), 3.);
		g.setEdgeWeight(g.addEdge("f", "n"), 25.);
		g.setEdgeWeight(g.addEdge("f", "o"), 38.);
		g.setEdgeWeight(g.addEdge("f", "p"), 31.);

		g.setEdgeWeight(g.addEdge("g", "h"), 59.);
		g.setEdgeWeight(g.addEdge("g", "i"), 5.);
		g.setEdgeWeight(g.addEdge("g", "j"), 9.);
		
		g.setEdgeWeight(g.addEdge("g", "l"), 5.);
		g.setEdgeWeight(g.addEdge("g", "m"), 545.);
		g.setEdgeWeight(g.addEdge("g", "n"), 96.);
		g.setEdgeWeight(g.addEdge("g", "o"), 50.);
		g.setEdgeWeight(g.addEdge("g", "p"), 91.);
		
		g.setEdgeWeight(g.addEdge("h", "i"), 7.);
		g.setEdgeWeight(g.addEdge("h", "j"), 9.);
		g.setEdgeWeight(g.addEdge("h", "l"), 72.);
		g.setEdgeWeight(g.addEdge("h", "m"), 956.);
		g.setEdgeWeight(g.addEdge("h", "n"), 71.);
		g.setEdgeWeight(g.addEdge("h", "o"), 29.);
		g.setEdgeWeight(g.addEdge("h", "p"), 79.);

		g.setEdgeWeight(g.addEdge("i", "j"), 4.);
		g.setEdgeWeight(g.addEdge("i", "l"), 14.);
		g.setEdgeWeight(g.addEdge("i", "m"), 43.);
		g.setEdgeWeight(g.addEdge("i", "n"), 7.);
		g.setEdgeWeight(g.addEdge("i", "o"), 9.);
		g.setEdgeWeight(g.addEdge("i", "p"), 3.);
		
		g.setEdgeWeight(g.addEdge("j", "l"), 34.);
		g.setEdgeWeight(g.addEdge("j", "m"), 8.);
		g.setEdgeWeight(g.addEdge("j", "n"), 654.);
		g.setEdgeWeight(g.addEdge("j", "o"), 9.);
		g.setEdgeWeight(g.addEdge("j", "p"), 2.);
		
		g.setEdgeWeight(g.addEdge("l", "m"), 1.);
		g.setEdgeWeight(g.addEdge("l", "n"), 434.);
		g.setEdgeWeight(g.addEdge("l", "o"), 89.);
		g.setEdgeWeight(g.addEdge("l", "p"), 9.);
		
		g.setEdgeWeight(g.addEdge("m", "n"), 5.);
		g.setEdgeWeight(g.addEdge("m", "o"), 498.);
		g.setEdgeWeight(g.addEdge("m", "p"), 244.);
		
		g.setEdgeWeight(g.addEdge("n", "o"), 87.);
		g.setEdgeWeight(g.addEdge("n", "p"), 34.);
		
		g.setEdgeWeight(g.addEdge("o", "p"), 44.);
		
		EstadoCaixeiroViajante inicial = new EstadoCaixeiroViajante(
				"a", g);
		
		
		System.out.println("estado inicial= " + inicial);

//		AEstrela busca = new AEstrela();
		BuscaProfundidade busca = new BuscaProfundidade();



		busca.usarFechados(false);
		
		long before = System.currentTimeMillis();
		Nodo s = busca.busca(inicial);
		long after = System.currentTimeMillis();
		
		if (s != null) {
			System.out.println("solucao = " + s.montaCaminho());
			System.out.println("\toperacoes = " + s.getProfundidade());
			System.out.println("\tcusto = " + s.g());
		}
		
		System.out.println(after-before);

	}
}
