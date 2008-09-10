package br.edu.ufcg.ia.algorithms.search;

import java.util.List;

/**
 * Algoritmos de Busca (geral, qquer problema)
 * 
 * Busca a solu��o por "subida da montanha" ------------------
 * 
 * @author Jomi Fred H�bner
 */
public class SubidaMontanha extends BuscaHeuristica {

	/** busca sem mostrar status */
	public SubidaMontanha() {
	}

	/** busca mostrando status */
	public SubidaMontanha(MostraStatusConsole ms) {
		super(ms);
	}

	public Nodo busca(Estado corrente) {
		status.inicia();
		initFechados();

		Estado melhor = corrente;
		while (!parar && corrente != null) {

			List<Estado> filhos = corrente.sucessores();
			if (filhos.size() == 0) {
				corrente = ((Aleatorio) corrente).geraAleatorio(); // come�a em
				// outro
				// lugar
				// aleat�rio
				continue;
			}

			// acha o menor h entre os filhos
			Estado melhorFilho = null;
			for (Estado e : filhos) {
				if (melhorFilho == null) {
					melhorFilho = e;
				} else if (((Heuristica) e).h(new Nodo(e, null)) < ((Heuristica) melhorFilho)
						.h(new Nodo(melhorFilho, null))) {
					melhorFilho = e;
				}
			}

			status.nroVisitados++;

			// se n�o tem filho melhor que corrente
			if (((Heuristica) melhorFilho).h(new Nodo(melhorFilho, null)) >= ((Heuristica) corrente)
					.h(new Nodo(corrente, null))) {
				if (corrente.ehMeta(new Nodo(corrente, null))) {
					status.termina(true);
					return new Nodo(corrente, null);
				} else { // m�ximo local
					if (((Heuristica) corrente).h(new Nodo(corrente, null)) < ((Heuristica) melhor)
							.h(new Nodo(melhor, null))) {
						melhor = corrente;
					}
					corrente = ((Aleatorio) corrente).geraAleatorio(); // come�a
					// em
					// outro
					// lugar
					// aleat�rio
				}
			} else { // tem filho melhor que corrente
				corrente = melhorFilho;
			}
		}
		status.termina(false);
		return null;
	}

	public String toString() {
		return "BSM - busca com subida da montanha";
	}
}
