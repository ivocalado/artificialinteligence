/**
 * 
 */
package br.edu.ufcg.embedded.mas.behaviors;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * @author ivocalado
 *
 */
public class HostingBrokerBehavior extends TickerBehaviour {

	public HostingBrokerBehavior(Agent a, long period) {
		super(a, period);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 975308162240184685L;

	/* (non-Javadoc)
	 * @see jade.core.behaviours.TickerBehaviour#onTick()
	 */
	@Override
	protected void onTick() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		if(message != null) {
			ACLMessage reply = message.createReply();
			
			reply.setContent("hostingbroker OK!");
			reply.setPerformative(ACLMessage.INFORM);
			myAgent.send(reply);
		}

	}

}
