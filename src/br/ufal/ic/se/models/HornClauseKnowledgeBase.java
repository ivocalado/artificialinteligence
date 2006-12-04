package br.ufal.ic.se.models;

import br.ufal.ic.se.node.*;
import br.ufal.ic.se.analysis.SymbolCatcher;

import java.util.List;
import java.util.ArrayList;


/**
 * @author Anderson Brandao
 *
 */
public class HornClauseKnowledgeBase {
	private KB knowledgeBase;
	private List<TLetter> body;  //premisses
	private TLetter head;		//conclusion
	private List<HornClause> hornClauses;

	public HornClauseKnowledgeBase(KB knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
		hornClauses = new ArrayList<HornClause>();
		
	}

	public List<HornClause> getHornClauses() {
		for (PSentence sentence : knowledgeBase.askAll()) {
			HornClause hc = doTransformation(sentence);
			//if (!hornClauses.contains(hc))
				hornClauses.add(hc);
		}
		return hornClauses;
	}	

	private HornClause doTransformation(PSentence sentence) {
		body = new ArrayList<TLetter>();
		if (sentence instanceof AComplexSentence) {			
			AComplexSentence n = (AComplexSentence) sentence;
			AEntailssentenceComplexsentence e = (AEntailssentenceComplexsentence) n.getComplexsentence();			
			assert (e.getRight() instanceof AAtomSentence) : 
				"Can't handle a complex sentence on the rhs";            
			PAtomicsentence pas = ((AAtomSentence)e.getRight()).getAtomicsentence();
			setHead(pas);			
			setBody(e.getLeft());
		}
		else if (sentence instanceof AAtomSentence) {
			setHead(((AAtomSentence) sentence).getAtomicsentence());
		}	
		return new HornClause(head, body);
	}

	private void setHead(PAtomicsentence head) {
		this.head = ((ASymbolAtomicsentence)head).getLetter();
	}

	private void setBody(PSentence body) {
		SymbolCatcher bodySymbolsCatcher = new SymbolCatcher();		
		body.apply(bodySymbolsCatcher);
		this.body = bodySymbolsCatcher.getCatchedSymbols();
	}	
	
}