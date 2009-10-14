/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import jade.Boot;

/**
 * @author ivocalado
 *
 */
public class FlightPriceAgent extends YellowPagesAgents {

	public FlightPriceAgent() {
		super("FlightPriceAgent", "flight-price", "");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6482488768351583673L;

	
	public static void main(String[] args) {
		Boot
				.main(new String[] { "-container",
						"flight-price:br.edu.ufcg.embedded.mas.agents.FlightPriceAgent" });
	}
}
