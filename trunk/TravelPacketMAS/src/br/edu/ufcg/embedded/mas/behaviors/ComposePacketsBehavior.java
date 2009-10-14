/**
 * 
 */
package br.edu.ufcg.embedded.mas.behaviors;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.edu.ufcg.embedded.mas.agents.TravelBrokerAgent;
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
public class ComposePacketsBehavior extends TickerBehaviour {

	private int steps;
	private List<ACLMessage> messages;
	
	public ComposePacketsBehavior(Agent a, long period) {
		super(a, period);
		steps = 0;
		messages = new LinkedList<ACLMessage>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8399549322407959513L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jade.core.behaviours.TickerBehaviour#onTick()
	 */
	@Override
	protected void onTick() {
		//System.out.println("ComposePacketsBehavior: onTick");
		ACLMessage message = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
		//System.out.println("message from client: " + message);

		if(message != null) {
			try {
				System.out.println("Client travel request: " + message.getContentObject());
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myAgent.addBehaviour(new HandleRequestBehavior(myAgent, message));			
		}

	}

}
