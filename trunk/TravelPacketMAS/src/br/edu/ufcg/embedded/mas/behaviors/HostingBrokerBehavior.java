/**
 * 
 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see jade.core.behaviours.TickerBehaviour#onTick()
	 */
	@Override
	protected void onTick() {
		ACLMessage message = myAgent.receive(MessageTemplate
				.MatchPerformative(ACLMessage.REQUEST));

		if (message != null) {

			AID hostingConversationAgent = null;

			try {
				if (((Travel) message.getContentObject()).getQuality().length() > 0) {
					hostingConversationAgent = YellowPagesManager.findBrokers(
							myAgent, "hosting-quality");
				} else
					hostingConversationAgent = YellowPagesManager.findBrokers(
							myAgent, "hosting-price");

				if (hostingConversationAgent != null) {
					ACLMessage messageToHostingConversation = new ACLMessage(
							ACLMessage.REQUEST);
					messageToHostingConversation.setConversationId(message
							.getConversationId());
					messageToHostingConversation.setContentObject(message
							.getContentObject());
					myAgent.addBehaviour(new HostingBrokerConversationBehavior(
							myAgent, message));
					myAgent.send(messageToHostingConversation);
				}

			} catch (UnreadableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
