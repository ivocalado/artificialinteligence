/**
 * 
 */
package br.edu.ufcg.embedded.mas.agents;

import br.edu.ufcg.embedded.mas.behaviors.FlightQualityBehavior;
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
	
	@Override
	protected void setup() {
		super.setup();
		addBehaviour(new FlightQualityBehavior(this, 2110));
	}



	public static void main(String[] args) {
		Boot
				.main(new String[] { "-container",
						"flight-quality:br.edu.ufcg.embedded.mas.agents.FlightQualityAgent" });
	}
}
