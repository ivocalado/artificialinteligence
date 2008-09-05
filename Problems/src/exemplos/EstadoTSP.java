package exemplos;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import busca.BuscaLargura;
import busca.Estado;
import busca.Heuristica;
import busca.MostraStatusConsole;
import busca.Nodo;
import busca.SubidaMontanha;

public class EstadoTSP implements Estado, Heuristica {

	private DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g;
	private String vertice;
	private double custo;
	private List<String> path;
	private String meta;

	public EstadoTSP(
			DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g,
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
	@Override
	public int custo() {
		// TODO Auto-generated method stub
		return (int) custo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Estado#ehMeta()
	 */
	@Override
	public boolean ehMeta() {
		/*
		 * // TODO Auto-generated method stub
		 * if(vertice.equals(meta)&&path.size()==g.vertexSet().size()+1) return
		 * false;
		 */
		return vertice.equals(meta) && path.size() == g.vertexSet().size() + 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Estado#getDescricao()
	 */
	@Override
	public String getDescricao() {
		// TODO Auto-generated method stub
		return "Este problema consiste de encontrar a melhor solução para o problema do caixeiro viajante";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Estado#sucessores()
	 */
	@Override
	public List<Estado> sucessores() {
		Set<DefaultWeightedEdge> edges = g.edgesOf(vertice);
		List<Estado> suc = new LinkedList<Estado>();
		for (DefaultWeightedEdge defaultWeightedEdge : edges) {
			List<String> newPath = new LinkedList<String>(path);
			newPath.add(vertice);
			String newVertice = g.getEdgeTarget(defaultWeightedEdge);
			double custo = g.getEdgeWeight(defaultWeightedEdge);
			// defaultWeightedEdge.
			EstadoTSP newEstado = new EstadoTSP(g, newVertice, custo, newPath,
					meta);
			suc.add(newEstado);

		}
		return suc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see busca.Heuristica#h()
	 */
	@Override
	public int h() {
		// TODO Auto-generated method stub
		return (int) custo;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		/*// TODO Auto-generated method stub
		path.add(vertice);
		
		String ret =path.toString();
		path.remove(vertice);*/
		return path.toString();
	}

	public static void main(String[] args) {
		
		DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> g = new DefaultDirectedWeightedGraph<String, DefaultWeightedEdge>(
				new ClassBasedEdgeFactory<String, DefaultWeightedEdge>(DefaultWeightedEdge.class));
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
		
		
//		  EstadoRainhas.tam = 8;
	        
//	        Estado inicial = new EstadoRainhas(); // um estado aleatï¿½rio
		Estado inicial = new EstadoTSP(g, "a", 0.0, new LinkedList<String>(), "a");
	        System.out.println("Estado inicial:"+inicial+"\n");
	        
	        Nodo n = null;
	        // os trï¿½s mï¿½todos seguintes nï¿½o conseguem
	        // resolver o problema das raï¿½nhas
	        n = new busca.AEstrela(new MostraStatusConsole()).busca(inicial);
	        //n = Busca.buscaProfRec(inicial, null, 10);
	        //n = Busca.buscaProfIterativo(inicial, null);
	                /*
	                if (n == null) {
	                System.out.println("sem soluï¿½ï¿½o!");
	                } else {
	                System.out.println("soluï¿½ï¿½o:\n" + n.montaCaminho() + "\n\n");
	                }
	                 */
	        
	        // a subida da montanha consegue resolver
	        //n = new SubidaMontanha(new MostraStatusConsole()).busca(inicial);
	        System.out.println("soluï¿½ï¿½o:\n" + n.getEstado() + "\n\n");
		
		
		
	}
}
