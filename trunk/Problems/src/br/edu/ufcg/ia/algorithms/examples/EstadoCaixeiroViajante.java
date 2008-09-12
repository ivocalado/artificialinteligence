package br.edu.ufcg.ia.algorithms.examples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import br.edu.ufcg.ia.algorithms.search.AEstrela;
import br.edu.ufcg.ia.algorithms.search.Aleatorio;
import br.edu.ufcg.ia.algorithms.search.Antecessor;
import br.edu.ufcg.ia.algorithms.search.Estado;
import br.edu.ufcg.ia.algorithms.search.Heuristica;
import br.edu.ufcg.ia.algorithms.search.MostraStatusConsole;
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
				"Encontra a melhor rota que visite todos os nodos no mapa abaixo (o custo dos caminhos esta entre parenteses):\n");
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

	// @Override
	public Estado geraAleatorio() {
		return new EstadoCaixeiroViajante(
				(String) g.vertexSet().toArray()[(int) Math.ceil(Math.random()
						* g.vertexSet().size())], 0, "", g);
	}

	public static void main(String[] a) throws FileNotFoundException,
			IOException {
		/** informacao estatica (o mapa) */
		// SimpleWeightedGraph<String, DefaultWeightedEdge> g;
		SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<String, DefaultWeightedEdge>(
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

		EstadoCaixeiroViajante inicial = new EstadoCaixeiroViajante(
				"Pernambuco", g);
		// System.out.println(inicial.getDescricao());
		System.out.println("estado inicial= " + inicial);

		// inicial.ehMeta();

		// BuscaGulosa busca = new BuscaGulosa(new MostraStatusConsole());
		AEstrela busca = new AEstrela(new MostraStatusConsole());
		/*
		 * BuscaProfundidade busca = new BuscaProfundidade(new
		 * MostraStatusConsole());
		 */
		// AEstrela busca = new AEstrela(new MostraStatusConsole());
		// BuscaProfundidade busca = new BuscaProfundidade(new
		// MostraStatusConsole());
		// BuscaLargura busca = new BuscaLargura(new MostraStatusConsole());
		MostraStatusConsole statusConsole = new MostraStatusConsole();

		busca.usarFechados(false);
		Nodo s = busca.busca(inicial);

		if (s != null) {
			System.out.println("solucao = " + s.montaCaminho());
			System.out.println("\toperacoes = " + s.getProfundidade());
			System.out.println("\tcusto = " + s.g());
		}

		// test
		/*
		 * Queue<Nodo> abertos = new PriorityQueue<Nodo>(); abertos.add(new
		 * Nodo(new EstadoMapa(4,50),null)); abertos.add(new Nodo(new
		 * EstadoMapa(1,300),null)); abertos.add(new Nodo(new
		 * EstadoMapa(2,100),null)); abertos.add(new Nodo(new
		 * EstadoMapa(3,200),null)); abertos.add(new Nodo(new
		 * EstadoMapa(5,2),null)); while (!abertos.isEmpty()) {
		 * System.out.println(abertos.remove()); }
		 */

	}
}
