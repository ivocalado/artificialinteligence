package br.ufal.ic.se.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.swing.JOptionPane;

import br.ufal.ic.se.node.AAtomSentence;
import br.ufal.ic.se.node.ASymbolAtomicsentence;
import br.ufal.ic.se.node.PSentence;
import br.ufal.ic.se.node.TLetter;
import br.ufal.ic.se.util.SentenceCreator;

/**
 * @author Ivo Augusto
 * @author Anderson Brandao
 * @version 1.00, 02/19/04, rev 2
 */

public class BackwardChainingEngine {

    private HornClauseKnowledgeBase hornClauseKnowledgeBase;    
    private List<HornClause> hornClauses;	
    private WMemory<TLetter, Boolean> workingMemory;
    private Set<TLetter> localInferredMemory; //to hold inferred symbols during runtime
    private ConflictsResolutionStrategy conflictsResStrategy; 

    public BackwardChainingEngine(KB knowledgeBase, WMemory<TLetter, Boolean> workingMemory) {
	this.workingMemory = workingMemory;
	initEngine(knowledgeBase);		
    }

    public void initEngine(KB knowledgeBase) {		
	hornClauseKnowledgeBase = new HornClauseKnowledgeBase(knowledgeBase);
	initializeMaps(hornClauseKnowledgeBase);	
	if (!workingMemory.isLoaded())
	    loadWorkingMemory();
    }

    public boolean query(String query) throws RuntimeException {
	try {
	    PSentence ps = SentenceCreator.getSentence(query);
	    if (ps instanceof AAtomSentence) {
		AAtomSentence aas = (AAtomSentence) ps;
		ASymbolAtomicsentence asas = (ASymbolAtomicsentence) aas.getAtomicsentence();
		TLetter q = asas.getLetter();		
		return backwardChaining(q);				
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

    private void initializeMaps(HornClauseKnowledgeBase hckb) {		
	hornClauses = hckb.getHornClauses();
	localInferredMemory = new HashSet<TLetter>(); 
    }   

    private void loadWorkingMemory() {
	for (HornClause hc : hornClauses) {
	    workingMemory.put(hc.getHead(), false);
	    if (hc.getBody().size() > 0) 
		for (TLetter t : hc.getBody()) 
		    workingMemory.put(t, false);
	}
    }

    //TODO A naive implementation of backward chaining - improve later 
    public boolean backwardChaining(TLetter symbol) throws Exception {

	//asks the working memory for the query, returning true
	//if the value is already present, proceeding the query otherwise
	if(workingMemory.get(symbol))	    
	    return true;

	//Creates a schedule (stack) of rules whose consequents match the query	
	Stack<HornClause> schedule = new Stack<HornClause>();

	//pushes the rules onto stack (see above)
	for(HornClause hc : hornClauses) 
	    if(hc.getHead().equals(symbol))
		schedule.push(hc);

	//if schedule is empty it means that the query didn't match
	//any rule's consequent and so asks the user 
	if (schedule.isEmpty()) {
	    //checks if the symbol has been inferred before
	    //to avoid querying user more than once
	    if (!localInferredMemory.contains(symbol)) {
		boolean q = askUser(symbol.getText());
		workingMemory.put(symbol, q);
		localInferredMemory.add(symbol);
	    }

	    return workingMemory.get(symbol);
	}

	//runs through all the rules
	//exits when all the rule's antecedents are matched
	//returning true for the first satisfied rule
	//when there are no matches for any rule stored on the stack
	//returns false
	while (!schedule.isEmpty()) {

	    HornClause clause = schedule.pop();

	    //a sentinel, to check if all the antecendents of a given
	    //rule are true
	    boolean nestedQuery = true;

	    for(TLetter t : clause.getBody()) {

		//for each antecedent, checks if it's in the working memory
		if(!workingMemory.get(t))	
		{
		    //if not present, do a recursive search
		    nestedQuery = backwardChaining(t); 

		    //if actual antecedent is false
		    //breaks the loop and looks for another rule on the stack
		    if (!nestedQuery) {
			localInferredMemory.add(t);			
			break ;
		    }

		    localInferredMemory.add(t);
		    workingMemory.put(t, true);
		}	
	    }
	    //if all the antecedents are true, 
	    //stores the consequent in the 
	    //working memory and returns true
	    if (nestedQuery) {
		workingMemory.put(symbol, true);
		return true;
	    }
	}
	//if there are any rules, returns false
	return false;
    }

    private boolean askUser(String query) throws Exception {
	return(JOptionPane.showConfirmDialog(null, "O fato '"+ (query)+" é verdadeiro?",
		"Encadeamento para trás",
		JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION);		
    }
}
