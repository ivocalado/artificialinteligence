/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.BuyPacketBehavior;
import jade.Boot;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * @author ivocalado
 * 
 */
public class ClientAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4085775578963481754L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();

		addBehaviour(new BuyPacketBehavior(this));
	}

	public void newMessagePacket(String message) {
		System.out.println("Result: " + message);
		super.doDelete();
		System.exit(0);
	}

	public AID findTravelBroker() {
		ServiceDescription serviceDescription = new ServiceDescription();
		// serviceDescription.setName("TravelBrokerAgent");
		serviceDescription.setType("travel-broker");

		DFAgentDescription serviceDescriptionAgent = new DFAgentDescription();
		serviceDescriptionAgent.addServices(serviceDescription);
		try {
			DFAgentDescription results[] = DFService.search(this,
					serviceDescriptionAgent);
			if (results == null || results.length == 0) {
				return null;
			} else
				return results[0].getName();
		} catch (FIPAException e) {
			return null;
		}

	}

	public static void main(String[] args) {
		Boot.main(new String[] { "-container",
				"client:br.edu.ufcg.embedded.mas.agents.ClientAgent" });
	}

}
