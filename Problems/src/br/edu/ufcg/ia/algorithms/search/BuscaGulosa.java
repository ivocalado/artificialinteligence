package br.edu.ufcg.ia.algorithms.search;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 *   Algoritmos de Busca (geral, qquer problema)
 *   
 *   Busca a solução por "subida da montanha"
 *                        ------------------
 *
 *   @author Jomi Fred Hübner
 */
public class BuscaGulosa extends BuscaHeuristica {
    
    /** busca sem mostrar status */
    public BuscaGulosa() {
    }
    
    /** busca mostrando status */
    public BuscaGulosa(MostraStatusConsole ms) {
        super(ms);
    }
    
    Comparator<Nodo> getNodoComparatorH() {
        return new Comparator<Nodo>() {
            public int compare(Nodo no1, Nodo no2) {
                try {
                    //Heuristica eo1 = (Heuristica)no1.estado;
                    //Heuristica eo2 = (Heuristica)no2.estado;
                    int f1 = ((Heuristica)no1.estado).h(no1);
                    int f2 = ((Heuristica)no2.estado).h(no2);
                    if (f1 > f2) {
                        return 1;
                    } else if (f1 == f2) {
                        return 0; 
                    } else {
                        return -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
    }

    public Nodo busca(Estado inicial) {
        status.inicia();
        initFechados();

        List<Nodo> abertos = new LinkedList<Nodo>();
        
        abertos.add(new Nodo(inicial, null));
        
        while (!parar && abertos.size() > 0) {
            
            Nodo n = abertos.remove(0);
            status.explorando(n,abertos.size());
            if (n.estado.ehMeta(n)) {
                status.termina(true);
                return n;
            }

            Queue<Nodo> abertosFilhos = new PriorityQueue<Nodo>(100, getNodoComparatorH()); // lista ordenada por f()
            abertosFilhos.addAll(sucessores(n));
        
            abertos.addAll( 0, abertosFilhos );
        }
        status.termina(false);
        return null;
    }
    
    public String toString() {
    	return "BSM - busca com subida da montanha";
    }
}
