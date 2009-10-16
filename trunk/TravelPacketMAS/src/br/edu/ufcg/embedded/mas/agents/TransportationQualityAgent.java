/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import jade.Boot;

/**
 * @author thiagobrunoms
 *
 */
public class TransportationQualityAgent extends YellowPagesAgents {

	/**
	 * @param name
	 * @param type
	 * @param owner
	 */
	public TransportationQualityAgent(String name, String type, String owner) {
		super("TransportationQualityAgent", "transportation-quality", "");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Boot
		.main(new String[] { "-container",
				"transportation-quality:br.edu.ufcg.embedded.mas.agents.TransportationQualityAgent" });
	}

}
