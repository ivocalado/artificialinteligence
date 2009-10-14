package br.edu.ufcg.embedded.mas.behaviors;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class TransportationBrokerBehavior extends TickerBehaviour {

	
	
	public TransportationBrokerBehavior(Agent a, long period) {
		super(a, period);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -412159981448525482L;

	@Override
	protected void onTick() {
		System.out.println("Transportation Broker: onTick");
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		
		if(message != null) {
			System.out.println("receiveing request from ");
			ACLMessage reply = message.createReply();
			
			reply.setContent("TRANSPORTATION ERROR!");
//			reply.setPerformative(ACLMessage.FAILURE);
			reply.setPerformative(ACLMessage.INFORM);
			myAgent.send(reply);
		}
	}

}
