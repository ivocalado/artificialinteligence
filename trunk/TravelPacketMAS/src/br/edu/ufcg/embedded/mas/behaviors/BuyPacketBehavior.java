/**
 * 
 */
package br.edu.ufcg.embedded.mas.behaviors;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

import br.edu.ufcg.embedded.mas.agents.ClientAgent;
import br.edu.ufcg.embedded.mas.objects.Travel;

/**
 * @author ivocalado
 *
 */
public class BuyPacketBehavior extends OneShotBehaviour {

	
	public BuyPacketBehavior(ClientAgent client) {
		super(client);
	} 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5584846569405368954L;

	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#action()
	 */
	@Override
	public void action() {

		System.out.println("BuyPacket=> " + serialVersionUID);
		
	
		AID aidBroker = ((ClientAgent)myAgent).findTravelBroker();
		
		if (aidBroker==null) {
			System.out.println("No Travel broker found");
			myAgent.doDelete();
			return;
		}
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		// TODO adicionar restricoes
		
		Travel t = new Travel("12", "");
		message.addReceiver(aidBroker);
		try {
			message.setContentObject(t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		myAgent.send(message);
		
		
		
		ACLMessage messageReturn = myAgent.blockingReceive();
		System.out.println("ACLMESSAGE: " + messageReturn);
		((ClientAgent)myAgent).newMessagePacket(messageReturn.getContent());
		
		
	}


}
