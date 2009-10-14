package br.edu.ufcg.embedded.mas.behaviors;

import java.io.IOException;

import br.edu.ufcg.embedded.mas.objects.Travel;
import br.edu.ufcg.embedded.mas.yellowpages.YellowPagesManager;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class FlightBrokerConversationBehavior extends Behaviour {

	private boolean completed;
	private ACLMessage message;
	private String conversationId;

	public FlightBrokerConversationBehavior() {
	}

	public FlightBrokerConversationBehavior(Agent a, ACLMessage message) {
		super(a);
		completed = false;
		this.message = message;
	}

	@Override
	public void action() {
		ACLMessage message = myAgent
				.receive(MessageTemplate
						.and(
								MessageTemplate
										.MatchConversationId(this.message
												.getConversationId()),
								MessageTemplate
										.or(
												MessageTemplate
														.MatchPerformative(ACLMessage.FAILURE),
												MessageTemplate
														.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL))));
		
		if(message != null) {
			ACLMessage msg = this.message.createReply();
			msg.setContent(message.getContent());
			msg.setPerformative(message.getPerformative());
			
			try {
				msg.setContentObject(message.getContentObject());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UnreadableException e) {
				e.printStackTrace();
			}
			
			myAgent.send(msg);			
		}
		
	}

	@Override
	public boolean done() {
		return completed;
	}

}
