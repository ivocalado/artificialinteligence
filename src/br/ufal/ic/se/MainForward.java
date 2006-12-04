package br.ufal.ic.se;
import br.ufal.ic.se.models.*;
import br.ufal.ic.se.node.TLetter;


public class MainForward {
	public static void main(String[] args) {
		try {		    
		       /******* busca orientada a dados ****************/
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
			
			System.out.println("***********************************************************");
			System.out.println("Definindo a base de conhecimento para a busca para a frente");
			System.out.println();
			System.out.println("Adicionando as regras");
			System.out.println();
			System.out.println("(P => Q)");
			System.out.println("((L AND A) => P)");
			System.out.println("((K AND L) => M)");
			System.out.println("((A AND P) => L)");
			System.out.println("(A AND B) => K)");
			System.out.println();
			System.out.println("Adicionando os fatos");
			System.out.println("A");
			System.out.println("B");
			
			//cria uma memória de trabalho
			WMemory<TLetter, Boolean> workingMemory = 
			    new WMemory<TLetter, Boolean>();
			
			//aplica o algoritmo de busca para a frente
			ForwardChainingEngine fce = 
			    new ForwardChainingEngine(kb, workingMemory);
			
			//consulta
			System.out.println("============ consultando ==================");
			System.out.println();
			System.out.println("perguntando por Q...");
			System.out.println();
			boolean result = (fce.query("Q"));
			System.out.println();
			System.out.print("Q eh " +  (result ? " verdadeiro " : " falso ") + " (ver acima ciclos da memoria de trabalho)");
			System.out.println();
			System.out.println();
			System.out.println("perguntando por J...");
			System.out.println();
			result = (fce.query("J"));
			System.out.println();
			System.out.println("J eh " +  (result ? " verdadeiro " : " falso ") + " (ver acima ciclos da memoria de trabalho)");
			System.out.println();
			System.out.println("*******************fim da busca para a frente **************************");
			
			/******* busca orientada a objetivos **************/
			/*base de conhecimento*/
			KB kbback = new KB();
			
			/*regras*/

			kbback.tell("((M AND N) => L)","");
			kbback.tell("((V AND Z) => L)","");
			kbback.tell("(B => Z)","");
			kbback.tell("((J AND B) => V)","");
			kbback.tell("(A  => J)","");
			kbback.tell("((C AND K) => B)","");
			
			System.out.println("***********************************************************");
			System.out.println("Definindo a base de conhecimento para a busca para tras");
			System.out.println();
			System.out.println("Adicionando as regras");
			System.out.println();
			System.out.println("((M AND N) => L)");
			System.out.println("((V AND Z) => L)");
			System.out.println("(B => Z)");
			System.out.println("((J AND B) => V)");
			System.out.println("(A  => J)");
			System.out.println("((C AND K) => B)");
			 
			//reseta a memória de trabalho
			workingMemory.reset();
			
			BackwardChainingEngine bce = new BackwardChainingEngine(kbback, workingMemory);
			
			
			// consulta
			System.out.println("============ consultando ==================");
			System.out.println();
			System.out.println("perguntando por L...");
			System.out.println();
			boolean resultBackChaining = (bce.query("L"));
			System.out.println();
			System.out.print("L eh " +  (resultBackChaining ? " verdadeiro " : " falso ") + " (ver acima ciclos da memoria de trabalho)");
			System.out.println();
			System.out.println();
			System.out.println("perguntando por V...");
			System.out.println();
			result = (bce.query("J"));
			System.out.println();
			System.out.println("J eh " +  (result ? " verdadeiro " : " falso ") + " (ver acima ciclos da memoria de trabalho)");
			System.out.println();
			System.out.println("*******************fim da busca para tras **************************");
			
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}



