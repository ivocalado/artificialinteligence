/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.TransportationQualityBehavior;
import jade.Boot;

/**
 * @author thiagobrunoms
 *
 */
public class TransportationQualityAgent extends YellowPagesAgents {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4918946521291834130L;



	/**
	 * @param name
	 * @param type
	 * @param owner
	 */
	public TransportationQualityAgent() {
		super("TransportationQualityAgent", "transportation-quality", "");
	}

	
	
	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new TransportationQualityBehavior(this, 1942));
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boot
		.main(new String[] { "-container",
				"transportation-quality:br.edu.ufcg.embedded.mas.agents.TransportationQualityAgent" });
	}

}
