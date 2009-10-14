/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.FlightBrokerBehavior;
import jade.Boot;

/**
 * @author ivocalado
 *
 */
public class FlightBrokerAgent extends YellowPagesAgents {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1346134315934609046L;

	public FlightBrokerAgent() {
		super("FlightBrokerAgent", "flight-broker", "");
	}

	
	
	/* (non-Javadoc)
	 * @see agents.YellowPagesAgents#setup()
	 */
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();
		
		addBehaviour(new FlightBrokerBehavior(this, 2000l));
	}




	public static void main(String[] args) {
		Boot
				.main(new String[] { "-container",
						"flight-broker:br.edu.ufcg.embedded.mas.agents.FlightBrokerAgent" });
	}
}
