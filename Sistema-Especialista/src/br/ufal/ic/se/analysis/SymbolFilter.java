package br.ufal.ic.se.analysis;

import br.ufal.ic.se.node.*;

public class SymbolFilter extends DepthFirstAdapter {

	public void inStart(Start node)
	{
		defaultIn(node);
	}

	public void outStart(Start node)
	{
		defaultOut(node);
	}

	public void defaultIn(Node node)
	{
	}

	public void defaultOut(Node node)
	{
	}

	public void caseStart(Start node)
	{
		inStart(node);
		node.getPSentence().apply(this);
		node.getEOF().apply(this);
		outStart(node);
	}

	public void inAAtomSentence(AAtomSentence node)
	{
		defaultIn(node);
	}

	public void outAAtomSentence(AAtomSentence node)
	{
		defaultOut(node);
	}

	public void caseAAtomSentence(AAtomSentence node)
	{
		inAAtomSentence(node);
		if(node.getAtomicsentence() != null)
		{
			node.getAtomicsentence().apply(this);
		}
		outAAtomSentence(node);
	}

	public void inAComplexSentence(AComplexSentence node)
	{
		defaultIn(node);
	}

	public void outAComplexSentence(AComplexSentence node)
	{
		defaultOut(node);
	}

	public void caseAComplexSentence(AComplexSentence node)
	{
		inAComplexSentence(node);
		if(node.getLpar() != null)
		{
			node.getLpar().apply(this);
		}
		if(node.getComplexsentence() != null)
		{
			node.getComplexsentence().apply(this);
		}
		if(node.getRpar() != null)
		{
			node.getRpar().apply(this);
		}
		outAComplexSentence(node);
	}

	public void inATruesentenceAtomicsentence(ATruesentenceAtomicsentence node)
	{
		defaultIn(node);
	}

	public void outATruesentenceAtomicsentence(ATruesentenceAtomicsentence node)
	{
		defaultOut(node);
	}

	public void caseATruesentenceAtomicsentence(ATruesentenceAtomicsentence node)
	{
		inATruesentenceAtomicsentence(node);
		if(node.getTrue() != null)
		{
			node.getTrue().apply(this);
		}
		outATruesentenceAtomicsentence(node);
	}

	public void inAFalsesentenceAtomicsentence(AFalsesentenceAtomicsentence node)
	{
		defaultIn(node);
	}

	public void outAFalsesentenceAtomicsentence(AFalsesentenceAtomicsentence node)
	{
		defaultOut(node);
	}

	public void caseAFalsesentenceAtomicsentence(AFalsesentenceAtomicsentence node)
	{
		inAFalsesentenceAtomicsentence(node);
		if(node.getFalse() != null)
		{
			node.getFalse().apply(this);
		}
		outAFalsesentenceAtomicsentence(node);
	}

	public void inASymbolAtomicsentence(ASymbolAtomicsentence node)
	{
		defaultIn(node);
	}

	public void outASymbolAtomicsentence(ASymbolAtomicsentence node)
	{
		defaultOut(node);
	}

	public void caseASymbolAtomicsentence(ASymbolAtomicsentence node)
	{
		inASymbolAtomicsentence(node);
		if(node.getLetter() != null)
		{
			node.getLetter().apply(this);
		}
		outASymbolAtomicsentence(node);
	}

	public void inANegationComplexsentence(ANegationComplexsentence node)
	{
		defaultIn(node);
	}

	public void outANegationComplexsentence(ANegationComplexsentence node)
	{
		defaultOut(node);
	}

	public void caseANegationComplexsentence(ANegationComplexsentence node)
	{
		inANegationComplexsentence(node);
		if(node.getNot() != null)
		{
			node.getNot().apply(this);
		}
		if(node.getSentence() != null)
		{
			node.getSentence().apply(this);
		}
		outANegationComplexsentence(node);

	}

	public void inAAndsentenceComplexsentence(AAndsentenceComplexsentence node)
	{
		defaultIn(node);
	}

	public void outAAndsentenceComplexsentence(AAndsentenceComplexsentence node)
	{
		defaultOut(node);
	}

	public void caseAAndsentenceComplexsentence(AAndsentenceComplexsentence node)
	{
		inAAndsentenceComplexsentence(node);
		if(node.getLeft() != null)
		{
			node.getLeft().apply(this);
		}
		if(node.getAnd() != null)
		{
			node.getAnd().apply(this);
		}
		if(node.getRight() != null)
		{
			node.getRight().apply(this);
		}
		outAAndsentenceComplexsentence(node);
	}

	public void inAOrsentenceComplexsentence(AOrsentenceComplexsentence node)
	{
		defaultIn(node);
	}

	public void outAOrsentenceComplexsentence(AOrsentenceComplexsentence node)
	{
		defaultOut(node);
	}

	public void caseAOrsentenceComplexsentence(AOrsentenceComplexsentence node)
	{
		inAOrsentenceComplexsentence(node);
		if(node.getLeft() != null)
		{
			node.getLeft().apply(this);
		}
		if(node.getOr() != null)
		{
			node.getOr().apply(this);
		}
		if(node.getRight() != null)
		{
			node.getRight().apply(this);
		}
		outAOrsentenceComplexsentence(node);
	}

	public void inAEntailssentenceComplexsentence(AEntailssentenceComplexsentence node)
	{
		defaultIn(node);
	}

	public void outAEntailssentenceComplexsentence(AEntailssentenceComplexsentence node)
	{
		defaultOut(node);
	}

	public void caseAEntailssentenceComplexsentence(AEntailssentenceComplexsentence node)
	{
		inAEntailssentenceComplexsentence(node);
		if(node.getLeft() != null)
		{
			node.getLeft().apply(this);
		}
		if(node.getEntails() != null)
		{
			node.getEntails().apply(this);
		}
		if(node.getRight() != null)
		{
			node.getRight().apply(this);
		}
		outAEntailssentenceComplexsentence(node);
	}

	public void inABentailssentenceComplexsentence(ABentailssentenceComplexsentence node)
	{
		defaultIn(node);
	}

	public void outABentailssentenceComplexsentence(ABentailssentenceComplexsentence node)
	{
		 node.clone();
	}

	public void caseABentailssentenceComplexsentence(ABentailssentenceComplexsentence node)
	{
		
	}


}
