package br.edu.ufcg.ia.algorithms.search;


/**
 * Interface para estados que implementam a fun��o h()
 *
 * @author  jomi
 */

public interface Heuristica {
    
    /**
     * estimativa de custo
     */
    public int h(Nodo n);

}
