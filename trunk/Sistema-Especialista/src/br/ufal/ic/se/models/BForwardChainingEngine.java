package br.ufal.ic.se.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import br.ufal.ic.se.node.AAtomSentence;
import br.ufal.ic.se.node.ASymbolAtomicsentence;
import br.ufal.ic.se.node.PSentence;
import br.ufal.ic.se.node.TLetter;
import br.ufal.ic.se.util.SentenceCreator;

public class BForwardChainingEngine {
    private HornClauseKnowledgeBase hornClauseKnowledgeBase;
    private WMemory<TLetter,Boolean> workMemory;
    private List<HornClause> hornClauses;
    private ConflictsResolutionStrategy conflictStrategy;

    public BForwardChainingEngine(KB knowledgeBase) {
	initEngine(knowledgeBase);		
    }

    public void initEngine(KB knowledgeBase) {		
	hornClauseKnowledgeBase = new HornClauseKnowledgeBase(knowledgeBase);
	setUpEngine(hornClauseKnowledgeBase);	
    }

    public boolean query(String query) throws RuntimeException {
	try {
	    PSentence ps = SentenceCreator.getSentence(query);
	    if (ps instanceof AAtomSentence) {
		AAtomSentence aas = (AAtomSentence) ps;
		ASymbolAtomicsentence asas = (ASymbolAtomicsentence) aas.getAtomicsentence();
		TLetter q = asas.getLetter();
		return forwardChainingLogicalConsequence(q);
	    }
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	throw new RuntimeException("Query string must be an atom");        
    }

    public void setKnowledgeBase(KB knowledgeBase) {
	this.initEngine(knowledgeBase);		
    }

    private void setUpEngine(HornClauseKnowledgeBase hckb) {
	workMemory = new WMemory<TLetter,Boolean>();	
	hornClauses = hckb.getHornClauses();

	for (HornClause hc : hornClauses) {

	    List<TLetter> l = new ArrayList<TLetter>(hc.getBody());
	    l.add(hc.getHead());
	    for (TLetter t : l)	workMemory.put(t, false);

	}

	loadWorkMemory();
	workMemory.setPrintable(true);
	workMemory.print();
    }

    private boolean inferred(TLetter p) {	
	Object v = workMemory.get(p);
	if (v==null) return false;
	return (v.equals(true));
    } 

    public boolean forwardChainingLogicalConsequence(TLetter query) {
	
	// Busca na memoria de trabalho, se existe retorna seu valor	
	if(inferred(query)) return true;	
	
	do {
	    ///Cria lista de listas, para armazenar as listas de premissas
	    Map<HornClause,Object> hornList = new HashMap<HornClause,Object>();

	    ///busca as listas de premissas
	    for(HornClause hc : hornClauses) {
		if (!workMemory.get(hc.getHead()))
		    //checa se alguma condição coincide com um fato na memória de trabalho
		    for (TLetter t : hc.getBody()) {		    
			if (inferred(t)) {			
			    hornList.put(hc, null);			
			}
		    }	    
	    }    

	    //Se nao existir nenhuma premissa, pergunta ao usuario e retorna
	    if (hornList.isEmpty()) {
		workMemory.put(query, askUser(query));
		return inferred(query);
	    }	    

	    for (HornClause clause : hornList.keySet()) {
		System.out.println("Clause " + clause);
		for (TLetter t : clause.getBody()) {
		    if (!inferred(t)) {
			if (!t.equals(query)) {
			    System.out.println("perguntando t = " + t + " query = " + query);
			    
			    boolean inferred = askUser(t);
			    if (inferred == false)
				return false;
			    workMemory.put(t, true);
			}

		    }
		}

		workMemory.put(clause.getHead(), true);
	    }
	} while (!workMemory.get(query));
	return workMemory.get(query);

    }

    private boolean askUser(TLetter query)  {

	return(JOptionPane.showConfirmDialog(null, "O fato '" + query + "' é verdadeiro?","Encadeamento para frente",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION);		

    }

    private void loadWorkMemory() {
	for (HornClause hc : hornClauses) {	   		
	    if (hc.getBody().size() == 0) 		
		workMemory.put(hc.getHead(), true);	    
	}
    }
}

