package br.ufal.ic.se.analysis;

import br.ufal.ic.se.node.*;
import java.util.*;


/**
 * @author Fritz
 *
 */
public class Temp extends DepthFirstAdapter {

	boolean current = false, 
	evaluated = false;
	LinkedList<Types> callStack = new LinkedList<Types>();
	enum Types {TRUE, 
		FALSE, 
		AND, 
		OR, 
		ENTAILS, 
		BIDIENTAILS, 
		NOT};

		public Temp() 
		{

		}	

		public void outASymbolAtomicsentence(ASymbolAtomicsentence node) {
			//modelWalker(ASTDescriptors.ATOMIC, modelMap.get(node.getLetter().getText()));
			callStack.addFirst(Types.TRUE);

		}

		public void outAFalsesentenceAtomicsentence(AFalsesentenceAtomicsentence node) {
			//modelWalker(ASTDescriptors.ATOMIC, new Boolean(false));
			callStack.addFirst(Types.FALSE);
		}

		public void outATruesentenceAtomicsentence(ATruesentenceAtomicsentence node) {
			//modelWalker(ASTDescriptors.ATOMIC, new Boolean(true));
			callStack.addFirst(Types.TRUE);
		}

		public void inANegationComplexsentence(ANegationComplexsentence node) {
			callStack.addFirst(Types.NOT);		
		}

		public void outAAndsentenceComplexsentence(AAndsentenceComplexsentence node) {
			callStack.addFirst(Types.AND);	 
		}

		public void outAOrsentenceComplexsentence(AOrsentenceComplexsentence node) {
			callStack.addFirst(Types.OR);	 
		}

		public void outAEntailssentenceComplexsentence(AEntailssentenceComplexsentence node) {
			callStack.addFirst(Types.ENTAILS);
		}

		public void printStack() {
			for (Types s : callStack) {
				System.out.println(s);
			}
			System.out.println(callStack);
		}


		private boolean eval(Types key, Types[] buffer) {			
			switch (key) {
			case AND:  
				current = bindAtomToBool(buffer[0]) 
				&& bindAtomToBool(buffer[1]); 
				break;
			case OR:  
				current = bindAtomToBool(buffer[0]) 
				&& bindAtomToBool(buffer[1]); break;
			case NOT:  current = !bindAtomToBool(buffer[0]); break;
			case ENTAILS:  break;
			case BIDIENTAILS:  break;

			default:

			}
			return true;

		}

		private boolean bindAtomToBool(Types atom) {
			return (atom == Types.TRUE ? true : false);
		}

		public boolean doExp() {
			Types[] valueBuffer = new Types[2];
			int bufferPos = 0;
			current = false;
			for (Types type : callStack) {
				if (type == Types.TRUE || type == Types.FALSE) 
					valueBuffer[bufferPos++] = type;
				else if (bufferPos > 0) {
					eval(type, valueBuffer);
					valueBuffer = new Types[2];
					bufferPos = 0;
				}

			}
			return current;
		}


}
