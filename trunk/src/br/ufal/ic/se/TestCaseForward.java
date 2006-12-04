package br.ufal.ic.se;
import br.ufal.ic.se.models.*;
import br.ufal.ic.se.node.TLetter;


public class TestCaseForward {
	public static void main(String[] args) {
		try {		    
		      
		    /*base de conhecimento*/
			KB kb = new KB();
			
			/*regras*/
			kb.tell("(P => Q)","");
			kb.tell("((L AND A) => P)","");
			kb.tell("((K AND L) => M)","");
			kb.tell("((A AND K) => L)","");
			kb.tell("((A AND B) => K)","");
			
			/*fatos*/			
			kb.tell("A","");
			kb.tell("B","");			 
			
			
			
			//cria uma memória de trabalho
			WMemory<TLetter, Boolean> workingMemory = 
			    new WMemory<TLetter, Boolean>();
			
			//aplica o algoritmo de busca para a frente
			ForwardChainingEngine fce = 
			    new ForwardChainingEngine(kb, workingMemory);
			
			//consulta
			boolean result = (fce.query("Q"));
			System.out.println(result);
					
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}



