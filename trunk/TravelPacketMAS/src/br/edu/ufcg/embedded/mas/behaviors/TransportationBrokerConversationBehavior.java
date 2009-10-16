/**
 * 
 */
package br.edu.ufcg.embedded.mas.behaviors;

import java.io.IOException;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

/**
 * @author thiagobrunoms
 * 
 */
public class TransportationBrokerConversationBehavior extends
		ConversationBehavior {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7780062526718940615L;

	/**
	 * @param a
	 * @param message
	 */
	public TransportationBrokerConversationBehavior(Agent a, ACLMessage message) {
		super(a, message);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jade.core.behaviours.Behaviour#action()
	 */
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

		if (message != null) {
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
			completed = true;
		}

	}

}
