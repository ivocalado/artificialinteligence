/**
 * 
 */
package br.edu.ufcg.embedded.mas.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

import br.edu.ufcg.embedded.mas.objects.Travel;
import br.edu.ufcg.embedded.mas.yellowpages.YellowPagesManager;

/**
 * @author ivocalado
 * @author thiagobruno
 *
 */
public class FlightBrokerBehavior extends TickerBehaviour {

	public FlightBrokerBehavior(Agent a, long period) {
		super(a, period);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8126583925184830540L;

	/* (non-Javadoc)
	 * @see jade.core.behaviours.TickerBehaviour#onTick()
	 */
	@Override
	protected void onTick() {
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		System.out.println("onTick => "+message);
		if(message != null) {
			AID flighConversationAgent = null;
			
			try {
				if(((Travel)message.getContentObject()).getQuality().length() > 0) {
					flighConversationAgent = YellowPagesManager.findBrokers(myAgent, "flight-quality");					
				} else
					flighConversationAgent = YellowPagesManager.findBrokers(myAgent, "flight-price");
				
				
				if(flighConversationAgent != null) {
					ACLMessage messageToFlightConversation = new ACLMessage(ACLMessage.REQUEST);
					messageToFlightConversation.setConversationId(message.getConversationId());
					messageToFlightConversation.setContentObject(message.getContentObject());
					myAgent.addBehaviour(new FlightBrokerConversationBehavior(myAgent, message));
					myAgent.send(messageToFlightConversation);
				}
				
			} catch (UnreadableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}			
	}

}
