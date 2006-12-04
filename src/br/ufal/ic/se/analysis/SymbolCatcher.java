package br.ufal.ic.se.analysis;
import br.ufal.ic.se.node.*;
import java.util.*;

/**
 * @author Fritz
 *
 */
public class SymbolCatcher extends DepthFirstAdapter {

	Set<TLetter> symbolSet;

	public SymbolCatcher() 
	{
		symbolSet = new HashSet<TLetter>();		
	}	

	public void outASymbolAtomicsentence(ASymbolAtomicsentence node)
	{		    
		TLetter tkClass = node.getLetter();
		if (!symbolSet.contains(tkClass)) {			
			symbolSet.add(tkClass);
		}
	}	 

	public List<TLetter> getCatchedSymbols() 
	{			
		return new ArrayList<TLetter>(symbolSet);
	} 
}
