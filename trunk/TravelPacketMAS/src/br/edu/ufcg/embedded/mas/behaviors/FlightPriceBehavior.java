/**
 * 
 */
package br.edu.ufcg.embedded.mas.behaviors;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author thiagobrunoms
 *
 */
public class FlightPriceBehavior extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 879633509434631654L;

	/**
	 * @param a
	 * @param period
	 */
	public FlightPriceBehavior(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see jade.core.behaviours.TickerBehaviour#onTick()
	 */
	@Override
	protected void onTick() {
		ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		if(msg != null) {
			ACLMessage reply = msg.createReply();
			reply.setConversationId(msg.getConversationId());
			reply.setContent("FlighPriceBehavior response!!!");
			reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			myAgent.send(reply);
		}
	}

}
