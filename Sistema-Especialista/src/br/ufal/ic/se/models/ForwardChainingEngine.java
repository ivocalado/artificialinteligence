package br.ufal.ic.se.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import br.ufal.ic.se.node.AAtomSentence;
import br.ufal.ic.se.node.ASymbolAtomicsentence;
import br.ufal.ic.se.node.PSentence;
import br.ufal.ic.se.node.TLetter;
import br.ufal.ic.se.util.SentenceCreator;

/**
 * @author Anderson Brandao
 *
 */

public class ForwardChainingEngine {
    private HornClauseKnowledgeBase hornClauseKnowledgeBase;
    private WMemory<TLetter,Boolean> workingMemory;
    private Map<HornClause,Integer> counter;
    private Stack<TLetter> schedule;
    private List<HornClause> hornClauses;
    private ConflictsResolutionStrategy conflictStrategy;

    public ForwardChainingEngine(KB knowledgeBase, WMemory<TLetter, Boolean> workingMemory) {
	this.workingMemory = workingMemory;
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
	schedule = new Stack<TLetter>();
	counter = new HashMap<HornClause, Integer>();
	hornClauses = hckb.getHornClauses();
	
	for (HornClause hc : hornClauses) {
	    //pushes the head onto stack (all pushed symbols are assumed to be true)			
	    if (hc.getBody().size() == 0) 			
		schedule.push(hc.getHead());			

	    List<TLetter> l = new ArrayList<TLetter>(hc.getBody());
	    l.add(hc.getHead());			

	    for (TLetter t : l)	workingMemory.put(t, false);

	    //for each clause, stores the premisses associated to its body			
	    counter.put(hc, hc.getBody().size());
	    
	}
	workingMemory.setPrintable(true);
    }    

    private boolean forwardChainingLogicalConsequence(TLetter query) {
	while (!schedule.isEmpty()) {	

	    TLetter currentSymbol =  schedule.pop();

	    while (!inferred(currentSymbol)) 
	    {		
		workingMemory.put(currentSymbol, true);
		for (HornClause hc : hornClauses) {
		    //checks if currentSymbol appears in the hornclause's body part
		    if (hc.getBody().contains(currentSymbol)) {
			//if so, decrements the counter
			int value = counter.get(hc) - 1;
			counter.put(hc, value);
			if (counter.get(hc) == 0) {
			    if (hc.getHead().equals(query)) {
				workingMemory.put(query, true);
				return true;
			    }	
			    else {
				TLetter symbol = hc.getHead();
				schedule.push(symbol);
			    }	
			}
		    }
		}
	    }
	}
	return false;
    }
    
    private boolean inferred(TLetter p) {	
	Object v = workingMemory.get(p);
	return ((v == null) || v.equals(true));
    }
}