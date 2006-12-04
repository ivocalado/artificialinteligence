//import models.ForwardChainingEngine;

import br.ufal.ic.se.models.*;

import br.ufal.ic.se.node.*;
import br.ufal.ic.se.util.SentenceCreator;
//import models.KB;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;

public class MainBackward {

	public static void main(String[] args) {

		try {

			KB kb = new KB();

			
			kb.tell("((A AND B)=>C)","");
			kb.tell("((C AND A)=>E)","");
			kb.tell("((E AND B)=>F)","");
			kb.tell("((C AND B)=>F)","");
			kb.tell("(F=>G)","");
			BackwardChainingEngine bce =new BackwardChainingEngine(kb);
			//ForwardChainingEngine fce = new ForwardChainingEngine(kb);

			System.out.println(bce.query("F"));
			/*kb.printFacts();
			System.out.println("Cláusulas Horns a patir daqui\n\n\n");
			
			HornClauseKnowledgeBase hkb=new HornClauseKnowledgeBase(kb);
			
			List<HornClause> hornClauses=hkb.getHornClauses();
			
			for (HornClause hc : hornClauses) 
				System.out.println(hc.toString());
			*/

		}

		catch (Exception e) {

			e.printStackTrace();

		}

	}

}