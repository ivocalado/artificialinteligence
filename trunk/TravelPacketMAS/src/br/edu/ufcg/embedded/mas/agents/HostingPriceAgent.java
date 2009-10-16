/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.HostingPriceBehavior;
import jade.Boot;

/**
 * @author thiagobrunoms
 * 
 */
public class HostingPriceAgent extends YellowPagesAgents {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4441398153872004985L;

	/**
	 * @param name
	 * @param type
	 * @param owner
	 */
	public HostingPriceAgent() {
		super("HostingPriceAgent", "hosting-price", "");
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new HostingPriceBehavior(this, 2800));
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boot
				.main(new String[] { "-container",
						"hosting-price:br.edu.ufcg.embedded.mas.agents.HostingPriceAgent" });
	}

}
