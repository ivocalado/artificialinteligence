package br.ufal.ic.se.models;

import br.ufal.ic.se.node.*;
import br.ufal.ic.se.parser.*;
import br.ufal.ic.se.lexer.*;
import br.ufal.ic.se.util.SentenceCreator;


import java.util.*;
import java.io.IOException;



public class KB {
	private Map<PSentence, String> facts;

	public KB() {
		facts = new HashMap<PSentence, String>();
	}

	public void tell(String fact, String description) throws ParserException, LexerException, IOException {		
		PSentence astStartNode = SentenceCreator.getSentence(fact);
		
		//stores the description associated to each atomic symbol
		if ((Node)astStartNode instanceof ASymbolAtomicsentence)
			tell(astStartNode, description);
		
		//stores the sentence passed as argument, e.g., ((P AND Q) => R)
		else tell(astStartNode, fact);
	}
	
	public void tell(TLetter fact, String description) throws ParserException, LexerException, IOException {
	    this.tell(fact.getText(), description);
	}

	public void tell(PSentence fact, String description) {
		facts.put(fact, description);
	}
	
	public List<PSentence> askAll() {
		return new ArrayList<PSentence>(facts.keySet());
	}
	
	public void clear() {
	    facts.clear();
	}

	public void printFacts() {
		LinkedList<String> formmaterList = new LinkedList<String>();
		
		//sorts atoms first, sentences last 
		
		for (PSentence fact : askAll()) { 
			if (fact instanceof AAtomSentence)
				formmaterList.addFirst(((ASymbolAtomicsentence)
						((AAtomSentence)fact).getAtomicsentence()).getLetter() 
						+ " : " + facts.get(fact));
			else
				formmaterList.addLast(facts.get(fact));
		}
		
		int count = 1;
		
		//prints the facts
		for (String fact : formmaterList) System.out.println("(" + count++ + ")" + "  " + fact); 			
		
	}
}
