package br.edu.ufcg.ia.algorithms.examples;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import br.edu.ufcg.ia.algorithms.search.Aleatorio;
import br.edu.ufcg.ia.algorithms.search.Estado;
import br.edu.ufcg.ia.algorithms.search.Heuristica;
import br.edu.ufcg.ia.algorithms.search.MostraStatusConsole;
import br.edu.ufcg.ia.algorithms.search.Nodo;
import br.edu.ufcg.ia.algorithms.search.SubidaMontanha;

public class EstadoTSP implements Estado, Heuristica, Aleatorio {

	//private DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g;
	
	/* (non-Javadoc)
	 * @see busca.Aleatorio#geraAleatorio()
	 */
	
	public Estado geraAleatorio() {
		List<Estado> e = sucessores();
		return e.get(new Random().nextInt()%e.size());
	}

	private SimpleWeightedGraph<String, DefaultWeightedEdge> g;
	private String vertice;
	private double custo;
	private List<String> path;
	private String meta;

	public EstadoTSP(
			SimpleWeightedGraph<String, DefaultWeightedEdge> g,
			String vertice, double custo, List<String> path, String meta) {
		super();
		this.g = g;
		this.vertice = vertice;
		this.custo = custo;
		this.path = path;
		this.meta = meta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Estado#custo()
	 */
	
	public int custo() {
		// TODO Auto-generated method stub
		return (int) custo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Estado#ehMeta()
	 */

	public boolean ehMeta(Nodo n) {
		/*
		 * // TODO Auto-generated method stub
		 * if(vertice.equals(meta)&&path.size()==g.vertexSet().size()+1) return
		 * false;
		 */
		// return vertice.equals(meta) && path.size() == g.vertexSet().size() +
		// 1;
		return h(n) == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Estado#getDescricao()
	 */
	
	public String getDescricao() {
		// TODO Auto-generated method stub
		return "Este problema consiste de encontrar a melhor solução para o problema do caixeiro viajante";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Estado#sucessores()
	 */
	
	public List<Estado> sucessores() {
		Set<DefaultWeightedEdge> edges = g.edgesOf(vertice);
		List<Estado> suc = new LinkedList<Estado>();
		for (DefaultWeightedEdge defaultWeightedEdge : edges) {
			List<String> newPath = new LinkedList<String>(path);
			newPath.add(vertice);

			String source = g.getEdgeSource(defaultWeightedEdge);
			String newVertice = (vertice.equals(source))?g.getEdgeTarget(defaultWeightedEdge):source;
			
			if (newVertice.equals(meta)
					&& (g.vertexSet().size() == path.size()))
				path.add(newVertice);
			else if (!newPath.contains(newVertice)) {
				double custo = g.getEdgeWeight(defaultWeightedEdge);
				// defaultWeightedEdge.
				EstadoTSP newEstado = new EstadoTSP(g, newVertice, custo,
						newPath, meta);
				suc.add(newEstado);
			}
		}
		return suc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Heuristica#h()
	 */
	
	public int h(Nodo n) {
		// TODO Auto-generated method stub
		return g.vertexSet().size() - path.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		/*
		 * // TODO Auto-generated method stub path.add(vertice);
		 * 
		 * String ret =path.toString(); path.remove(vertice);
		 */
		return path.toString();
	}

	public static void main(String[] args) {

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

		g.setEdgeWeight(g.addEdge("a", "b"), 2.0);
		g.setEdgeWeight(g.addEdge("a", "c"), 3.0);
		g.setEdgeWeight(g.addEdge("a", "d"), 4.0);
		g.setEdgeWeight(g.addEdge("b", "e"), 1.0);
		g.setEdgeWeight(g.addEdge("b", "c"), 2.);
		g.setEdgeWeight(g.addEdge("c", "d"), 5.);
		g.setEdgeWeight(g.addEdge("c", "e"), 2.);
		g.setEdgeWeight(g.addEdge("c", "f"), 3.);
		g.setEdgeWeight(g.addEdge("c", "g"), 2.);
		g.setEdgeWeight(g.addEdge("e", "g"), 1.);
		g.setEdgeWeight(g.addEdge("g", "f"), 3.);
		g.setEdgeWeight(g.addEdge("d", "f"), 1.);

		// EstadoRainhas.tam = 8;

		// Estado inicial = new EstadoRainhas(); // um estado aleatï¿½rio
		Estado inicial = new EstadoTSP(g, "a", 0.0, new LinkedList<String>(),
				"a");
		System.out.println("Estado inicial:" + inicial + "\n");

		Nodo n = null;
		// os trï¿½s mï¿½todos seguintes nï¿½o conseguem
		// resolver o problema das raï¿½nhas
		//n = new busca.AEstrela(new MostraStatusConsole()).busca(inicial);
		// n = Busca.buscaProfRec(inicial, null, 10);
		// n = Busca.buscaProfIterativo(inicial, null);
		/*
		 * if (n == null) { System.out.println("sem soluï¿½ï¿½o!"); } else {
		 * System.out.println("soluï¿½ï¿½o:\n" + n.montaCaminho() + "\n\n"); }
		 */

		// a subida da montanha consegue resolver
		 n = new SubidaMontanha(new MostraStatusConsole()).busca(inicial);
		System.out.println("soluï¿½ï¿½o:\n" + n.getEstado() + "\n\n");

	}
}
