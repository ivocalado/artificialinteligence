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
public class TransportationPriceBehavior extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5560316080071748969L;

	/**
	 * @param a
	 * @param period
	 */
	public TransportationPriceBehavior(Agent a, long period) {
		super(a, period);
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
			reply.setContent("TransportationPriceBehavior response!!!");
			reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			myAgent.send(reply);
		}
	}

}
