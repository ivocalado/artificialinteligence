/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import jade.Boot;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import br.edu.ufcg.embedded.mas.behaviors.ComposePacketsBehavior;

/**
 * @author ivocalado
 *
 */
public class TravelBrokerAgent extends YellowPagesAgents {

	public TravelBrokerAgent() {
		super("TravelBrokerAgent", "travel-broker", "");
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 5388488220109289382L;

	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	
	
	@Override
	protected void setup() {
		
		super.setup();		
		addBehaviour(new ComposePacketsBehavior(this, 3000l));
	}
	
	
	public void completeTravelPacket(ACLMessage replyMessage) {
		//TODO Cria mensagem de resposta
		
		//replyMessage.setContent("Respostiando");
		send(replyMessage);
		
		
	}
	
	public static void main(String[] args) {
		Boot
				.main(new String[] { "-container",
						"travel-broker:br.edu.ufcg.embedded.mas.agents.TravelBrokerAgent" });
	}

	
	
}
