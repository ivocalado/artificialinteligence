package br.edu.ufcg.ia.algorithms.search;
import java.util.Random;
import java.util.Set;

import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class TSP_AG {

	private SimpleWeightedGraph<String, DefaultWeightedEdge> g;
	private int numPopulacoes;
	private boolean mostraEvolucoes;
	private double taxaMortalidade;
	private int numeroEvolucoes;

	public TSP_AG(SimpleWeightedGraph<String, DefaultWeightedEdge> g, boolean mostraEvolucoes, double taxaMortalidade, int numeroEvolucoes) {
		super();
		this.g = g;
		this.numPopulacoes   = g.vertexSet().size()+2;
		this.mostraEvolucoes = mostraEvolucoes;
		this.taxaMortalidade = taxaMortalidade;
		this.numeroEvolucoes = numeroEvolucoes;
	}

	private void ordenar(int[][] cromossomos, double[] resultados) {
		int i, i2;
		for (i = 0; i < resultados.length; i++) {
			for (i2 = i; i2 < resultados.length; i2++) {
				if (resultados[i] > resultados[i2]) {
					double vTmp;
					int[] vvTmp = new int[10];
					vTmp = resultados[i];
					resultados[i] = resultados[i2];
					resultados[i2] = vTmp;

					vvTmp = cromossomos[i];
					cromossomos[i] = cromossomos[i2];
					cromossomos[i2] = vvTmp;
				}
			}
		}
	}

	private void imprimir(int[][] cromossomos, double[] resultados) {
		int i, i2;
		String[] cidades = new String[g.vertexSet().size()];
		g.vertexSet().toArray(cidades);
		for (i = 0; i < numPopulacoes; i++) {
			for (i2 = 0; i2 < g.vertexSet().size(); i2++) {
				System.out.print(cidades[cromossomos[i][i2]] + " => ");
			}
			System.out.print(cidades[cromossomos[i][0]] + " ");
			System.out.println(" Resultados: " + resultados[i]);
		}
	}

	public void renovarCromossomos(int[][] cromossomos, double[] resultados,
			double taxaMortalidade) {

		int inicioExcluidos = (int) (taxaMortalidade * 10);
		int i, i2 = 0;

		for (i = inicioExcluidos; i < numPopulacoes; i++) {

			boolean valido = false;

			while (!valido) {

				int[] c_tmp = resetaCromossomo();

				// pegando 2 pais aleatoriamente
				int pai1, pai2;

				pai1 = new Random().nextInt(inicioExcluidos);
				do {
					pai2 = new Random().nextInt(inicioExcluidos);
				} while ((pai1 == pai2)
						&& (resultados[pai1] != resultados[pai2]));

				// pegando 3 caracteristicas do pai 1 aleatoriamente
				for (i2 = 0; i2 < (int) g.vertexSet().size() / 4; i2++) {
					int pos;
					pos = new Random().nextInt(g.vertexSet().size());
					c_tmp[pos] = cromossomos[pai1][pos];
				}
				// pegando restante do pai 2
				for (i2 = 0; i2 < (int) g.vertexSet().size() / 4; i2++) {
					int pos = new Random().nextInt(g.vertexSet().size());
					if (c_tmp[pos] == -1) {
						if (valorValidoNoCromossomo(cromossomos[pai2][pos],
								c_tmp)) {
							c_tmp[pos] = cromossomos[pai2][pos];
						}
					}
				}

				// preenchendo o restante com aleatorios
				for (i2 = 0; i2 < g.vertexSet().size(); i2++) {
					if (c_tmp[i2] == -1) {
						int crom_temp = valorValidoNoCromossomo(c_tmp);
						c_tmp[i2] = crom_temp;
					}
				}

				valido = cromossomoValido(c_tmp, cromossomos);
				if (valido) {
					cromossomos[i] = c_tmp;
				}
			}
		}

	}

	private void calcularResultado(int[][] cromossomos, double[] resultados) {
		int i, i2;
		// calculando o resultado
//		System.out.println(g.vertexSet().toArray().getClass());
		String[] vertices = new String[g.vertexSet().size()];
		g.vertexSet().toArray(vertices);
//		String[] vertices = (String[]) g.vertexSet().toArray();
		for (i = 0; i < numPopulacoes; i++) {
			double resTmp = 0;
			for (i2 = 0; i2 < g.vertexSet().size() - 1; i2++) {

				resTmp += g.getEdgeWeight(g.getEdge(
						vertices[cromossomos[i][i2]],
						vertices[cromossomos[i][i2 + 1]]));
			}
			resTmp += g.getEdgeWeight(g.getEdge(vertices[cromossomos[i][0]],
					vertices[cromossomos[i][i2]]));
			resultados[i] = resTmp;
		}

	}

	private int[][] gerarCromossomosAleatoriamente(int[][] cromossomos) {

		// inicializando cromossomos aleatoriamente
		int[] c_tmp = new int[g.vertexSet().size()];

		int i, i2;
		for (i = 0; i < numPopulacoes; i++) {
			boolean crom_valido = false;
			while (!crom_valido) {
				crom_valido = true;
				c_tmp = resetaCromossomo();

				// gerando cromossomo - ok
				for (i2 = 0; i2 < g.vertexSet().size(); i2++) {
					c_tmp[i2] = valorValidoNoCromossomo(c_tmp);
				}
				crom_valido = cromossomoValido(c_tmp, cromossomos);
			}
			cromossomos[i] = c_tmp;
		}
		return cromossomos;
	}

	private void resultado(int[][] cromossomos, double[] resultados) {
		int i, i2;
		i = 0;
		//String[] cidades = (String[]) g.vertexSet().toArray();
		String[] cidades = new String[g.vertexSet().size()];
		g.vertexSet().toArray(cidades);
		for (i2 = 0; i2 < g.vertexSet().size(); i2++) {
			System.out.print(cidades[cromossomos[i][i2]] + " => ");
		}
		System.out.print(cidades[cromossomos[i][0]] + " ");
		System.out.println(" Resultado: " + resultados[i]);

	}

	public void start() {
		int[][] cromossomos = new int[numPopulacoes][g.vertexSet().size()];
		double[] resultados = new double[numPopulacoes];

		gerarCromossomosAleatoriamente(cromossomos);
		calcularResultado(cromossomos, resultados);
		ordenar(cromossomos, resultados);
		// g.get
		if (mostraEvolucoes)
			imprimir(cromossomos, resultados);

		int i;
		for (i = 0; i < numeroEvolucoes; i++) {
			renovarCromossomos(cromossomos, resultados, taxaMortalidade);
			calcularResultado(cromossomos, resultados);
			ordenar(cromossomos, resultados);
			if (mostraEvolucoes) {
				System.out.println("Geracao: " + (i + 1));
				imprimir(cromossomos, resultados);
			}
		}

		resultado(cromossomos, resultados);

	}

	private int[] resetaCromossomo() {
		int[] c = new int[g.vertexSet().size()];
		int i;
		for (i = 0; i < g.vertexSet().size(); i++) {
			c[i] = -1;
		}
		return c;
	}

	private int valorValidoNoCromossomo(int[] c_tmp) {
		int crom_temp;
		boolean valido;
		do {
			crom_temp = new Random().nextInt(g.vertexSet().size());
			valido = true;
			for (int ii = 0; ii < g.vertexSet().size(); ii++) {
				if (c_tmp[ii] == crom_temp)
					valido = false;
			}
		} while (!valido);
		return crom_temp;
	}

	private boolean valorValidoNoCromossomo(int valor, int[] c_tmp) {
		int crom_temp = valor;
		boolean valido;

		valido = true;
		for (int ii = 0; ii < g.vertexSet().size(); ii++) {
			if (c_tmp[ii] == crom_temp)
				valido = false;
		}

		return valido;
	}

	private boolean cromossomoValido(int[] c_tmp, int[][] cromossomos) {
		int j, j2;
		boolean crom_valido = true;

		for (j = 0; j < numPopulacoes; j++) {
			int n_iguais = 0;
			for (j2 = 0; j2 < g.vertexSet().size(); j2++) {
				if (c_tmp[j2] == cromossomos[j][j2]) {
					n_iguais++;
				}
			}

			if (n_iguais == g.vertexSet().size())
				crom_valido = false;
		}
		return crom_valido;
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
		g.addVertex("h");

		g.setEdgeWeight(g.addEdge("a", "b"), 42.);
		g.setEdgeWeight(g.addEdge("a", "c"), 61.);
		g.setEdgeWeight(g.addEdge("a", "d"), 30.);
		g.setEdgeWeight(g.addEdge("a", "e"), 17.);
		g.setEdgeWeight(g.addEdge("a", "f"), 82.);
		g.setEdgeWeight(g.addEdge("a", "g"), 31.);
		g.setEdgeWeight(g.addEdge("a", "h"), 11.);		
		g.setEdgeWeight(g.addEdge("b", "c"), 14.);
		g.setEdgeWeight(g.addEdge("b", "d"), 87.);
		g.setEdgeWeight(g.addEdge("b", "e"), 28.);
		g.setEdgeWeight(g.addEdge("b", "f"), 70.);
		g.setEdgeWeight(g.addEdge("b", "g"), 19.);
		g.setEdgeWeight(g.addEdge("b", "h"), 33.);		
		g.setEdgeWeight(g.addEdge("c", "d"), 20.);
		g.setEdgeWeight(g.addEdge("c", "e"), 81.);
		g.setEdgeWeight(g.addEdge("c", "f"), 21.);
		g.setEdgeWeight(g.addEdge("c", "g"), 8.);
		g.setEdgeWeight(g.addEdge("c", "h"), 29.);		
		g.setEdgeWeight(g.addEdge("d", "e"), 34.);
		g.setEdgeWeight(g.addEdge("d", "f"), 33.);
		g.setEdgeWeight(g.addEdge("d", "g"), 91.);
		g.setEdgeWeight(g.addEdge("d", "h"), 10.);
		
//		g.
		
		g.setEdgeWeight(g.addEdge("e", "f"), 41.);
		g.setEdgeWeight(g.addEdge("e", "g"), 34.);
		g.setEdgeWeight(g.addEdge("e", "h"), 82.);
		

		g.setEdgeWeight(g.addEdge("f", "g"), 19.);
		g.setEdgeWeight(g.addEdge("f", "h"), 32.);

		g.setEdgeWeight(g.addEdge("g", "h"), 59.);

	/*	g.setEdgeWeight(g.addEdge("f", "g"), 19.);
		g.setEdgeWeight(g.addEdge("f", "h"), 32.);
		
		g.setEdgeWeight(g.addEdge("g", "h"), 59.);*/
		long before = System.currentTimeMillis();
		new TSP_AG(g, false, 0.5, 3000).start();
		long after = System.currentTimeMillis();
		System.out.println(after-before);

	}

}
