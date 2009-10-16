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
public class HostingPriceBehavior extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2458741633506125153L;

	/**
	 * @param a
	 * @param period
	 */
	public HostingPriceBehavior(Agent a, long period) {
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
			reply.setContent("HostingPriceBehavior response!!!");
			reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			myAgent.send(reply);
		}
	}

}
