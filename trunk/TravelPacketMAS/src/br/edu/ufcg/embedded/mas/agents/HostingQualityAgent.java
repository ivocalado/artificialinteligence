/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.HostingQualityBehavior;
import jade.Boot;

/**
 * @author thiagobrunoms
 *
 */
public class HostingQualityAgent extends YellowPagesAgents {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8101207963922458376L;

	/**
	 * @param name
	 * @param type
	 * @param owner
	 */
	public HostingQualityAgent() {
		super("HostingQualityAgent", "hosting-quality", "");
	}
	
	

	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new HostingQualityBehavior(this, 2354));
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boot
		.main(new String[] { "-container",
				"hosting-quality:br.edu.ufcg.embedded.mas.agents.HostingQualityAgent" });
	}

}
