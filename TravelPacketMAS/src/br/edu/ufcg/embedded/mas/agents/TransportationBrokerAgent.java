/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.TransportationBrokerBehavior;
import jade.Boot;

/**
 * @author thiagobrunoms
 *
 */
public class TransportationBrokerAgent extends YellowPagesAgents {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8247666244574407309L;

	/**
	 * @param name
	 * @param type
	 * @param owner
	 */
	public TransportationBrokerAgent() {
		super("TransportationBroker", "transportation-broker", "");

	}
	
	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new TransportationBrokerBehavior(this, 3500));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boot
		.main(new String[] { "-container",
				"transportation-broker:br.edu.ufcg.embedded.mas.agents.TransportationBrokerAgent" });
	}

}
