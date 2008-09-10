package br.edu.ufcg.ia.algorithms.examples;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.ia.algorithms.graph.Grafo;
import br.edu.ufcg.ia.algorithms.graph.GrafoNaoDirigido;
import br.edu.ufcg.ia.algorithms.graph.Vertice;
import br.edu.ufcg.ia.algorithms.search.Aleatorio;
import br.edu.ufcg.ia.algorithms.search.Antecessor;
import br.edu.ufcg.ia.algorithms.search.BuscaLargura;
import br.edu.ufcg.ia.algorithms.search.Estado;
import br.edu.ufcg.ia.algorithms.search.Heuristica;
import br.edu.ufcg.ia.algorithms.search.MostraStatusConsole;
import br.edu.ufcg.ia.algorithms.search.Nodo;



/**
 * Representa um estado do mundo para o problema do caixeiro viajante.
 * 
 * No caso o estado e uma das cidades.
 */
public class EstadoCaixeiroViajante implements Estado, Antecessor, Heuristica, Aleatorio {

	public String getDescricao() {
		StringBuffer ds = new StringBuffer("Encontra a melhor rota que visite todos os nodos no mapa abaixo (o custo dos caminhos esta entre parenteses):\n");
		for (int i=0; i<nomes.length; i++) {
			ds.append("  saindo de "+nomes[i]+" para ");
			Map<Vertice,Integer> custos = mapa.getVertice(i).getCustoAdjacentes();
			for (Vertice v: custos.keySet()) {
				ds.append(nomes[v.getId()]+"("+custos.get(v)+"), ");
			}
			ds.append("\n");
		}
		return ds.toString();
	}

	/** estado: a cidade corrente */
	public int cidade;
	public int custo;
	private String caminho;

	public EstadoCaixeiroViajante(int c)  {
		this.cidade = c;
		caminho = ""+c;
	}
	
	public EstadoCaixeiroViajante(int c, int custo, String pai)  {
		this.cidade = c;
		this.custo = custo;
		this.caminho = pai + " -> " + c;
	}

	/**
	 * verifica se um estado e igual a outro
	 */
	public boolean equals(Object o) {
		if (o instanceof EstadoCaixeiroViajante) {
			EstadoCaixeiroViajante e = (EstadoCaixeiroViajante)o;
			return e.cidade == this.cidade;
		}
		return false;
	}

	public int hashCode() {
		return new Integer(cidade).hashCode();
	}

	/**
	 * verifica se o estado e meta
	 */
	public boolean ehMeta(Nodo n) {
//		System.out.println("\n");
//		for (int i=0; i<nomes.length; i++) {
//		System.out.println(nomes[i]);
//		System.out.println(mapa.getVertice(i).isFoiVisitado());
//		}
//		System.out.println("\n");
//		System.out.println("Profundidade Nó atual: " + n.getProfundidade());
		return n.getProfundidade() == (nomes.length-1);
	}

	/**
	 * Custo para geracao de um estado
	 */
	public int custo() {
		return custo;
	}

	/**
	 * gera uma lista de sucessores do nodo.
	 */
	public List<Estado> sucessores() {
		//mapa.getVertice(cidade).setFoiVisitado(true);		
		System.out.println(caminho);
		List<Estado> suc = new LinkedList<Estado>(); // a lista de sucessores
		Map<Vertice,Integer> custos = mapa.getVertice(cidade).getCustoAdjacentes();
		for (Vertice v: custos.keySet()) {
			suc.add(new EstadoCaixeiroViajante(v.getId(), custos.get(v), caminho));            	            
		}
		return suc;
	}

	public List<Estado> antecessores() {
		return sucessores();
	}

	public int getCidade() {
		return cidade;
	}

	public String toString() {
		String saida = nomes[cidade];
		return saida + "\n";
	}

	public String toStringAll() {
		String saida = "";
		System.out.println(percurso.size());
		for (Iterator iterator = percurso.iterator(); iterator.hasNext();) {
			saida += nomes[((EstadoCaixeiroViajante)iterator.next()).cidade];
			if (iterator.hasNext())
				saida += ", ";
		}
		return saida + "\n";
	}


	/** informacao estatica (o mapa) */
	public static Grafo mapa = new GrafoNaoDirigido();
	static {
		for (int i=0; i<=8; i++) {
			mapa.criaVertice(i);
		}
		mapa.criaAresta(0,1,11);
		mapa.criaAresta(0,4,20);
		mapa.criaAresta(0,7,82);

		mapa.criaAresta(1,6,60);
		mapa.criaAresta(1,7,68);
		mapa.criaAresta(1,3,44);

		mapa.criaAresta(4,3,77);

		mapa.criaAresta(3,2,45);

		mapa.criaAresta(2,6,40);
		mapa.criaAresta(2,8,60);

		mapa.criaAresta(8,5,38);

		mapa.criaAresta(5,6,47);

		mapa.criaAresta(6,7,20);       
	}

	// cidades
	// a, b, c, d, e, f, g, h, i, k,  l, m,  n,   o,  p,  x
	// 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
	public final static String nomes[] = { "Joao Pessoa", "Santa Rita", "Guarabira", "Mamanguape", "Cabedelo", 
		"Campina Grande", "Alagoa Grande", "Itabaiana", "Esperanca"};    

	public static List<EstadoCaixeiroViajante> percurso = new ArrayList<EstadoCaixeiroViajante>();

	public static void main(String[] a) {
		EstadoCaixeiroViajante inicial = new EstadoCaixeiroViajante(0);
		//System.out.println(inicial.getDescricao());
		System.out.println("estado inicial= "+inicial);

		//inicial.ehMeta();

//		BuscaGulosa busca = new BuscaGulosa(new MostraStatusConsole());
		//AEstrela busca = new AEstrela(new MostraStatusConsole());
//		BuscaProfundidade busca = new BuscaProfundidade(new MostraStatusConsole());
		BuscaLargura busca = new BuscaLargura(new MostraStatusConsole()); 
		
		busca.usarFechados(false);
		Nodo s = busca.busca(inicial);

		if (s != null) {
			System.out.println("solucao = "+s.montaCaminho());
			System.out.println("\toperacoes = "+s.getProfundidade());
			System.out.println("\tcusto = "+s.g());
		}

		// test
		/*
        Queue<Nodo> abertos = new PriorityQueue<Nodo>();
        abertos.add(new Nodo(new EstadoMapa(4,50),null));
        abertos.add(new Nodo(new EstadoMapa(1,300),null));
        abertos.add(new Nodo(new EstadoMapa(2,100),null));
        abertos.add(new Nodo(new EstadoMapa(3,200),null));
        abertos.add(new Nodo(new EstadoMapa(5,2),null));
        while (!abertos.isEmpty()) {
            System.out.println(abertos.remove());
        }
		 */

	}
//@Override
	public int h(Nodo n) {
		
		/*
		//heurística de quinta sem conhecimento prévio
		//baseado no número de cidades já visitadas e do número que ainda falta
		int profundidade = n.getProfundidade();
		
		if(profundidade == 0)
			return 0;
		
		EstadoCaixeiroViajante estadoAtual = (EstadoCaixeiroViajante) n.getEstado();

		int custosAtual = 0;
		int cidades = 0;

		for (Estado estado : estadoAtual.sucessores()) {
			if (!percurso.contains(estado)) {
				custosAtual += estado.custo();		
				cidades++;
			}
		}
		custosAtual = ((cidades*custosAtual)/(cidades-nomes.length));
		return (custosAtual)*estadoAtual.custo();
		
		
		/*
		//heuristica de quarta com conhecimento prévio usando g()
		int profundidade = n.getProfundidade();
		if(profundidade == 0)
			return 0;
		int custoAcumulado = n.g();
		int media = (int)custoAcumulado/profundidade;
		int estimativa = media * (nomes.length - profundidade);
				
		return estimativa;*/
		
		return (nomes.length - n.getProfundidade()) * 47; // 47 é a média dos custos das arestas
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}
	public void setCidade(int cidade) {
		this.cidade = cidade;
	}



//	@Override
	public Estado geraAleatorio() {
		int cidade = (int)Math.ceil(Math.random()*nomes.length);
//		Map<Vertice,Integer> custos = mapa.getVertice(cidade).getCustoAdjacentes();
//		for (Vertice v: custos.keySet()) {
//		suc.add(new EstadoCaixeiroViajante(v.getId(), custos.get(v)));            	            
//		}

		return new EstadoCaixeiroViajante(mapa.getVertice(cidade).getId(),0, "");
	}
}

