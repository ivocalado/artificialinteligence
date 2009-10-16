/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boot
		.main(new String[] { "-container",
				"flight-quality:br.edu.ufcg.embedded.mas.agents.HostingtQualityAgent" });
	}

}
