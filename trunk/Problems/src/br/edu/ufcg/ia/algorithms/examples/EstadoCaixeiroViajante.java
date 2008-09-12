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
import br.edu.ufcg.ia.algorithms.search.BuscaProfundidade;
import br.edu.ufcg.ia.algorithms.search.Estado;
import br.edu.ufcg.ia.algorithms.search.Heuristica;
import br.edu.ufcg.ia.algorithms.search.MostraStatusConsole;
import br.edu.ufcg.ia.algorithms.search.Nodo;
import br.edu.ufcg.ia.algorithms.search.SubidaMontanha;

/**
 * Representa um estado do mundo para o problema do caixeiro viajante.
 * 
 * No caso o estado e uma das cidades.
 */
public class EstadoCaixeiroViajante implements Estado, Antecessor, Heuristica,
		Aleatorio {
	private SimpleWeightedGraph<String, DefaultWeightedEdge> g;

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

		/*
		 * for (int i = 0; i < nomes.length; i++) { ds.append(" saindo de " +
		 * nomes[i] + " para "); Map<Vertice, Integer> custos =
		 * mapa.getVertice(i) .getCustoAdjacentes(); for (Vertice v :
		 * custos.keySet()) { ds.append(nomes[v.getId()] + "(" + custos.get(v) +
		 * "), "); } ds.append("\n"); }
		 */
		return ds.toString();
	}

	// private/** estado: a cidade corrente */
	private String estado;
	public double custo;
	private String caminho;

	public EstadoCaixeiroViajante(String c,
			SimpleWeightedGraph<String, DefaultWeightedEdge> g) {
		this.g = g;
		this.estado = c;
		caminho = "" + c;
	}

	public EstadoCaixeiroViajante(String c, double custo, String pai,
			SimpleWeightedGraph<String, DefaultWeightedEdge> g) {
		this.g = g;
		this.estado = c;
		this.custo = custo;
		this.caminho = pai + " -> " + c;
	}

	/**
	 * verifica se um estado e igual a outro
	 */
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

	/**
	 * verifica se o estado e meta
	 */
	public boolean ehMeta(Nodo n) {
		// System.out.println("\n");
		// for (int i=0; i<nomes.length; i++) {
		// System.out.println(nomes[i]);
		// System.out.println(mapa.getVertice(i).isFoiVisitado());
		// }
		// System.out.println("\n");
		// System.out.println("Profundidade Nó atual: " + n.getProfundidade());
		return n.getProfundidade() == (nomes.length - 1);
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
		// mapa.getVertice(cidade).setFoiVisitado(true);
		System.out.println(caminho);
		List<Estado> suc = new LinkedList<Estado>(); // a lista de sucessores
		/*
		 * Map<Vertice, Integer> custos = mapa.getVertice(cidade)
		 * .getCustoAdjacentes(); for (Vertice v : custos.keySet()) {
		 * suc.add(new EstadoCaixeiroViajante(v.getId(), custos.get(v),
		 * caminho)); }
		 */

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
		for (Iterator iterator = percurso.iterator(); iterator.hasNext();) {
			saida += ((EstadoCaixeiroViajante) iterator.next()).getEstado();
			if (iterator.hasNext())
				saida += ", ";
		}
		return saida + "\n";
	}

	// cidades
	// a, b, c, d, e, f, g, h, i, k, l, m, n, o, p, x
	// 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
	public final static String nomes[] = { "Joao Pessoa", "Santa Rita",
			"Guarabira", "Mamanguape", "Cabedelo", "Campina Grande",
			"Alagoa Grande", "Itabaiana", "Esperanca" };

	public static List<EstadoCaixeiroViajante> percurso = new ArrayList<EstadoCaixeiroViajante>();

	// @Override
	public int h(Nodo n) {

		/*
		 * //heurística de quinta sem conhecimento prévio //baseado no número de
		 * cidades já visitadas e do número que ainda falta int profundidade =
		 * n.getProfundidade();
		 * 
		 * if(profundidade == 0) return 0;
		 * 
		 * EstadoCaixeiroViajante estadoAtual = (EstadoCaixeiroViajante)
		 * n.getEstado();
		 * 
		 * int custosAtual = 0; int cidades = 0;
		 * 
		 * for (Estado estado : estadoAtual.sucessores()) { if
		 * (!percurso.contains(estado)) { custosAtual += estado.custo();
		 * cidades++; } } custosAtual =
		 * ((cidades*custosAtual)/(cidades-nomes.length)); return
		 * (custosAtual)*estadoAtual.custo(); /* //heuristica de quarta com
		 * conhecimento prévio usando g() int profundidade =
		 * n.getProfundidade(); if(profundidade == 0) return 0; int
		 * custoAcumulado = n.g(); int media = (int)custoAcumulado/profundidade;
		 * int estimativa = media * (nomes.length - profundidade);
		 * 
		 * return estimativa;
		 */

		return (nomes.length - n.getProfundidade()) * 47; // 47 é a média dos
		// custos das
		// arestas
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
		int index = (int) Math.ceil(Math.random() * g.vertexSet().size());
		// Map<Vertice,Integer> custos =
		// mapa.getVertice(cidade).getCustoAdjacentes();
		// for (Vertice v: custos.keySet()) {
		// suc.add(new EstadoCaixeiroViajante(v.getId(), custos.get(v)));
		// }

		return new EstadoCaixeiroViajante(
				(String) g.vertexSet().toArray()[index], 0, "", g);
	}

	public static void main(String[] a) throws FileNotFoundException, IOException {
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

		/*
		 * mapa.criaAresta(0,1,11); mapa.criaAresta(0,4,20);
		 * mapa.criaAresta(0,7,82);
		 * 
		 * mapa.criaAresta(1,6,60); mapa.criaAresta(1,7,68);
		 * mapa.criaAresta(1,3,44);
		 * 
		 * mapa.criaAresta(4,3,77);
		 * 
		 * mapa.criaAresta(3,2,45);
		 * 
		 * mapa.criaAresta(2,6,40); mapa.criaAresta(2,8,60);
		 * 
		 * mapa.criaAresta(8,5,38);
		 * 
		 * mapa.criaAresta(5,6,47);
		 * 
		 * mapa.criaAresta(6,7,20);
		 */
		/*
		 * g.addVertex("a"); g.addVertex("b"); g.addVertex("c");
		 * g.addVertex("d"); g.addVertex("e"); g.addVertex("f");
		 * g.addVertex("g");
		 * 
		 * g.setEdgeWeight(g.addEdge("a", "b"), 2.0);
		 * g.setEdgeWeight(g.addEdge("a", "c"), 3.0);
		 * g.setEdgeWeight(g.addEdge("a", "d"), 4.0);
		 * g.setEdgeWeight(g.addEdge("b", "e"), 1.0);
		 * g.setEdgeWeight(g.addEdge("b", "c"), 2.);
		 * g.setEdgeWeight(g.addEdge("c", "d"), 5.);
		 * g.setEdgeWeight(g.addEdge("c", "e"), 2.);
		 * g.setEdgeWeight(g.addEdge("c", "f"), 3.);
		 * g.setEdgeWeight(g.addEdge("c", "g"), 2.);
		 * g.setEdgeWeight(g.addEdge("e", "g"), 1.);
		 * g.setEdgeWeight(g.addEdge("g", "f"), 3.);
		 * g.setEdgeWeight(g.addEdge("d", "f"), 1.);
		 * 
		 * 
		 * 
		 * public static Grafo mapa = new GrafoNaoDirigido();
		 * 
		 * static { for (int i=0; i<=8; i++) { mapa.criaVertice(i); } }
		 */

		EstadoCaixeiroViajante inicial = new EstadoCaixeiroViajante(
				"Pernambuco", g);
		// System.out.println(inicial.getDescricao());
		System.out.println("estado inicial= " + inicial);

		// inicial.ehMeta();

		// BuscaGulosa busca = new BuscaGulosa(new MostraStatusConsole());
		// AEstrela busca = new AEstrela(new MostraStatusConsole());
		// BuscaProfundidade busca = new BuscaProfundidade(new
		// MostraStatusConsole());
		// BuscaLargura busca = new BuscaLargura(new MostraStatusConsole());
		
		MostraStatusConsole statusConsole = new MostraStatusConsole();
		
		//SubidaMontanha busca = new SubidaMontanha(new MostraStatusConsole());
		BuscaProfundidade busca = new BuscaProfundidade(new MostraStatusConsole());
		
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
