package br.ufal.ic.se.models;

import java.util.HashSet;
import java.util.Iterator;
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

	private Set<TLetter> localInferredMemory; // to hold inferred symbols
	
	private double bound = 0.0;

	// during runtime

	private ConflictsResolutionStrategy conflictsResStrategy;

	public BackwardChainingEngine(KB knowledgeBase,
			WMemory<TLetter, Boolean> workingMemory) {
		this.workingMemory = workingMemory;
		initEngine(knowledgeBase);
	}

	public void initEngine(KB knowledgeBase) {
		hornClauseKnowledgeBase = new HornClauseKnowledgeBase(knowledgeBase);
		initializeMaps(hornClauseKnowledgeBase);
		if (!workingMemory.isLoaded())
			loadWorkingMemory();
	}

	public TLetter query(String query) throws RuntimeException {
		try {
			PSentence ps = SentenceCreator.getSentence(query);
			if (ps instanceof AAtomSentence) {
				AAtomSentence aas = (AAtomSentence) ps;
				ASymbolAtomicsentence asas = (ASymbolAtomicsentence) aas
						.getAtomicsentence();
				TLetter q = asas.getLetter();
				return backwardChaining(q);
			}
		} catch (Exception e) {
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

	// TODO A naive implementation of backward chaining - improve later
	public TLetter backwardChaining(TLetter symbol) throws Exception {

		// asks the working memory for the query, returning true
		// if the value is already present, proceeding the query otherwise
		if (workingMemory.get(symbol))
			return symbol;

		// Creates a schedule (stack) of rules whose consequents match the query
		Stack<HornClause> schedule = new Stack<HornClause>();

		// pushes the rules onto stack (see above)
		for (HornClause hc : hornClauses)
			if (hc.getHead().equals(symbol))
				schedule.push(hc);

		// if schedule is empty it means that the query didn't match
		// any rule's consequent and so asks the user
		if (schedule.isEmpty()) {
			// checks if the symbol has been inferred before
			// to avoid querying user more than once
			if (!localInferredMemory.contains(symbol)) {

				symbol = askUser(symbol);
				workingMemory.put(symbol, symbol.getValue());
				localInferredMemory.add(symbol);
			}
			return symbol;
		}

		// runs through all the rules
		// exits when all the rule's antecedents are matched
		// returning true for the first satisfied rule
		// when there are no matches for any rule stored on the stack
		// returns false
		while (!schedule.isEmpty()) {

			HornClause clause = schedule.pop();

			// a sentinel, to check if all the antecendents of a given
			// rule are true
			Double certaintyDegree = null;
			TLetter tletter = null;
			for (TLetter t : clause.getBody()) {

				// for each antecedent, checks if it's in the working memory
				if (!workingMemory.get(t)) {
					// if not present, do a recursive search
					// nestedQuery = backwardChaining(t);
					tletter = backwardChaining(t);
					if (certaintyDegree == null) {
						certaintyDegree = tletter.getCertaintyDegree();
					} else {
						certaintyDegree *= tletter.getCertaintyDegree();
					}
					// if actual antecedent is false
					// breaks the loop and looks for another rule on the stack
					if (!tletter.getValue()) {
						localInferredMemory.add(t);
						break;
					}

					localInferredMemory.add(tletter);
					workingMemory.put(tletter, true);
				}
			}
			// if all the antecedents are true,
			// stores the consequent in the
			// working memory and returns true
			if (tletter.getValue()) {
				symbol.setCertaintyDegree(certaintyDegree>=getBound()?certaintyDegree:1.0);
				symbol.setValue(certaintyDegree>=getBound()?true:false);
				workingMemory.put(symbol, symbol.getValue());
				return symbol;
			}
		}
		// if there are any rules, returns false
		return symbol;
	}

	private TLetter askUser(TLetter query) throws Exception {
		boolean valor = JOptionPane.showConfirmDialog(null, "O fato '"
				+ (query.getText()) + "' é verdadeiro?",
				"Encadeamento para trás", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
		double certaintyDegree = Double.parseDouble(JOptionPane.showInputDialog(null,
				"Qual o grau de certeza do fato '" + query.getText() + "'?\n"
						+ "Digite um valor entre 0 e 1",
				"Encadeamento para trás", JOptionPane.QUESTION_MESSAGE));
		if (certaintyDegree < 0.0 || certaintyDegree > 1.0) {
			throw new Exception("Grau de certeza definido fora dos limites");
		}
		query.setValue(valor);
		query.setCertaintyDegree(certaintyDegree);
		return query;
	}

	public double getBound() {
		return bound;
	}

	public void setBound(double bound) throws RuntimeException{
		if (bound<0.0||bound>1.0) {
			throw new RuntimeException("Limiar inválido");
		}
		this.bound = bound;
	}


}
