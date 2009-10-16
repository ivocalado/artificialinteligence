package br.edu.ufcg.embedded.mas.behaviors;

import java.io.IOException;

import br.edu.ufcg.embedded.mas.objects.Travel;
import br.edu.ufcg.embedded.mas.yellowpages.YellowPagesManager;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

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
		ACLMessage message = myAgent.receive(MessageTemplate
				.MatchPerformative(ACLMessage.REQUEST));

		if (message != null) {

			AID transportationConversationAgent = null;

			try {
				if (((Travel) message.getContentObject()).getQuality().length() > 0) {
					transportationConversationAgent = YellowPagesManager
							.findBrokers(myAgent, "transportation-quality");
				} else
					transportationConversationAgent = YellowPagesManager
							.findBrokers(myAgent, "transportation-price");

				if (transportationConversationAgent != null) {
					ACLMessage messageToTransportationConversation = new ACLMessage(
							ACLMessage.REQUEST);
					messageToTransportationConversation
							.setConversationId(message.getConversationId());
					messageToTransportationConversation
							.setContentObject(message.getContentObject());
					myAgent
							.addBehaviour(new TransportationBrokerConversationBehavior(
									myAgent, message));
					myAgent.send(messageToTransportationConversation);
				}

			} catch (UnreadableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
