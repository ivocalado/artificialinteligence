/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import jade.Boot;

/**
 * @author ivocalado
 *
 */
public class FlightQualityAgent extends YellowPagesAgents {

	public FlightQualityAgent() {
		super("FlightQualityAgent", "flight-quality", "");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6203892654703539227L;

	
	public static void main(String[] args) {
		Boot
				.main(new String[] { "-container",
						"flight-quality:br.edu.ufcg.embedded.mas.agents.FlightQualityAgent" });
	}
}
