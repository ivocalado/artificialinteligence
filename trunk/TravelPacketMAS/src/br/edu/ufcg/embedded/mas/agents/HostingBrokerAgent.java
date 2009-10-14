/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import jade.Boot;
import br.edu.ufcg.embedded.mas.behaviors.FlightBrokerBehavior;
import br.edu.ufcg.embedded.mas.behaviors.HostingBrokerBehavior;

/**
 * @author ivocalado
 *
 */
public class HostingBrokerAgent extends YellowPagesAgents {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5543799734986002622L;

	
	public HostingBrokerAgent() {
		super("HostingBrokerAgent", "hosting-broker", "");
	}


	/* (non-Javadoc)
	 * @see br.edu.ufcg.embedded.mas.agents.YellowPagesAgents#setup()
	 */
	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new HostingBrokerBehavior(this, 4000l));
	}
	
	public static void main(String[] args) {
		Boot
				.main(new String[] { "-container",
						"hosting-broker:br.edu.ufcg.embedded.mas.agents.HostingBrokerAgent" });
	}

}
