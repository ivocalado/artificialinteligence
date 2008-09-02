package br.ufal.ic.se;
import br.ufal.ic.se.models.*;
import br.ufal.ic.se.node.TLetter;


public class TestCaseBack {
	public static void main(String[] args) {
		try {		    
		      
			
			/******* busca orientada a objetivos **************/
			/*base de conhecimento*/
			KB kbback = new KB();
			
			/*regras*/

			kbback.tell("((M AND N) => L)","");
			kbback.tell("(((G AND Z) AND V) => L)","");
			kbback.tell("(B => Z)","");
			kbback.tell("((J AND B) => V)","");
			kbback.tell("(A  => J)","");
			kbback.tell("((C AND K) => B)","");
			
			 
			//Cria uma memória de trabalho
			WMemory<TLetter, Boolean> workingMemory = new WMemory<TLetter, Boolean>();
			workingMemory.setPrintable(true);
			
			BackwardChainingEngine bce = new BackwardChainingEngine(kbback, workingMemory);
			
			
			boolean resultBackChaining = (bce.query("L"));
			
			System.out.println(resultBackChaining);
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}



