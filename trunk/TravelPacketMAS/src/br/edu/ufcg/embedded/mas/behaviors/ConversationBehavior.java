/**
 * 
 */
package br.edu.ufcg.embedded.mas.behaviors;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

/**
 * @author thiagobrunoms
 *
 */
public abstract class ConversationBehavior extends Behaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6519803318740489919L;
	protected ACLMessage message;
	protected boolean completed;
	/**
	 * 
	 */

	public ConversationBehavior(Agent a, ACLMessage message) {
		super(a);
		// TODO Auto-generated constructor stub
		this.message = message;
		completed = false;
	}


	/* (non-Javadoc)
	 * @see jade.core.behaviours.Behaviour#done()
	 */
	@Override
	public final boolean done() {
		return completed;
	}

}
