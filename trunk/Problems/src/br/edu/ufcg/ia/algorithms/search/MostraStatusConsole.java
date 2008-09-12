package br.edu.ufcg.ia.algorithms.search;

import java.util.ArrayList;

import br.edu.ufcg.ia.algorithms.examples.SeachListenner;

/** mostra os dados de um status de busca */
public class MostraStatusConsole extends Thread {

    private Status status;
    private boolean stop = false;
    private ArrayList<SeachListenner> listenners; 
    
    public MostraStatusConsole() {
    	this.listenners = new ArrayList<SeachListenner>();
        start();        
    }
    
    public void addSearchListenner(SeachListenner l) {
    	this.listenners.add(l);
    }
    
    public void removeSearchListenner(SeachListenner l) {
    	this.listenners.remove(l);
    }
    
    public MostraStatusConsole(Status s) {
        setStatus(s);
        start();        
    }

    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status s) {
        this.status = s;
    }
    
    public void para() {
    	if (!stop) {
	        stop = true;
	        interrupt();
    	}
    }
    
    public void run() {
        while (!stop) {
            try {
                sleep(1000);
                if (!stop && status != null) {
                    mostra();
                }
            } catch (Exception e) {  }
        }
        mostraFim();
    }

    protected void mostraFim() {
        println(": Fim da busca. "+status.nroVisitados+" nodos visitados em "+status.getTempoDecorrido()+" mili-seg.\n");        
    }
    protected void mostra() {
    	String str = "Status:" + "\t\n"+status.nroVisitados+" nodos visitados, nodos em aberto="+status.tamAbertos +
    		"\t\nProfundidade atual="+status.profundidadeMax + "\t\nTempo decorrido="+status.getTempoDecorrido();
    	
    	for (SeachListenner l : this.listenners) {
			l.searchUpdated(str);
		}
    	
        println("Status:");
        println("\t"+status.nroVisitados+" nodos visitados, nodos em aberto="+status.tamAbertos);
        println("\tProfundidade atual="+status.profundidadeMax);
        println("\tTempo decorrido="+status.getTempoDecorrido());
        /* 
        print("\nNúmero médio de sucessores="+melhor.nroMedioSucessores());
        print("\nMédia de profundidade="+melhor.getProfundidadeMedia());
        print("\nProfundidade máxima="+melhor.profundidadeMax);
        */
    }

    protected void println(String s) {
        System.out.println(s);
    }
}
