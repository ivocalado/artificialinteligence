/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.TransportationPriceBehavior;
import jade.Boot;

/**
 * @author thiagobrunoms
 *
 */
public class TransportationPriceAgent extends YellowPagesAgents {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3141285609380575517L;



	/**
	 * @param name
	 * @param type
	 * @param owner
	 */
	public TransportationPriceAgent() {
		super("TransportationPriceAgent", "transportation-price", "");
	}

	
	
	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new TransportationPriceBehavior(this, 3200));
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boot
		.main(new String[] { "-container",
				"transportation-price:br.edu.ufcg.embedded.mas.agents.TransportationPriceAgent" });
	}

}
