package br.ufal.ic.se.models;

import br.ufal.ic.se.node.*;
import br.ufal.ic.se.util.SentenceCreator;
import br.ufal.ic.se.models.KB;
import br.ufal.ic.se.models.WMemory;


import java.util.List;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class AForwardChainingEngine  {
    private HornClauseKnowledgeBase hornClauseKnowledgeBase;
    //private Map<TLetter, Boolean> deducedSymbols;
    private WMemory<TLetter,Boolean> workMemory;
    private Map<HornClause,Integer> counter;
    private Stack<TLetter> schedule;
    private List<HornClause> hornClauses;

    public AForwardChainingEngine(KB knowledgeBase) {
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
	//deducedSymbols = new HashMap<TLetter,Boolean>();
	workMemory = new WMemory<TLetter,Boolean>();

	counter = new HashMap<HornClause, Integer>();
	hornClauses = hckb.getHornClauses();

	for (HornClause hc : hornClauses) {
	    //pushes the head onto stack (all pushed symbols are assumed to be true)			
	    if (hc.getBody().size() == 0) { 			
		
		workMemory.put(hc.getHead(), true);
	    }
	    else schedule.push(hc.getHead());

	    //for each clause, stores the premisses associated to its body			
	    counter.put(hc, hc.getBody().size());

	} 
	System.out.println("------init results-----");
	workMemory.setPrintable(true);
	workMemory.print();
	System.out.println("Schedule: " + schedule);
	System.out.println("----------------");
    }

    private boolean inferred(TLetter p) {	
	Object v = workMemory.get(p);
	System.out.println("inferiu");
	if (v!=null)
	    return (true);
	return false;
    }

    public boolean forwardChainingLogicalConsequence(TLetter query) {

	if (inferred(query))
	    return true;

	while (!schedule.isEmpty()) {	

	    TLetter currentSymbol =  schedule.pop();

	    while (!inferred(currentSymbol)) 
	    {	

		askUser(currentSymbol);
		//workMemory.put(currentSymbol, true);		

		for (HornClause hc : hornClauses) {
		    //checks if currentSymbol appears in the hornclause's body part
		    if (hc.getBody().contains(currentSymbol)) {
			//if so, decrements the counter

			int value = counter.get(hc) - 1;
			counter.put(hc, value);

			if (counter.get(hc) == 0) {
			    if (workMemory.get(hc.getHead()) != null && workMemory.get(query) != null)
				if (hc.getHead().equals(query) 
					&& workMemory.get(hc.getHead()) 
					&& workMemory.get(query)) 
				    return true;			    
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

    private void askUser(TLetter query) {
	String jop = JOptionPane.showInputDialog(null, "Insira o valor verdade para o atomo "  + query.getText());
	if (jop.equalsIgnoreCase("verdadeiro"))
	    workMemory.put(query, true);
	else
	    workMemory.put(query, false);
    }
}