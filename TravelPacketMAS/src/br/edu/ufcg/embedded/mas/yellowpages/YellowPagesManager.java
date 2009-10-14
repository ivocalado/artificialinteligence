package br.edu.ufcg.embedded.mas.yellowpages;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class YellowPagesManager {
	
	public static AID findBrokers(Agent a, String type) {
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType(type);

		DFAgentDescription serviceDescriptionAgent = new DFAgentDescription();
		serviceDescriptionAgent.addServices(serviceDescription);
		
		try {
			DFAgentDescription results[] = DFService.search(a,
					serviceDescriptionAgent);
			if (results == null || results.length == 0) {
				return null;
			} else
				return results[0].getName();
		} catch (FIPAException e) {
			return null;
		}
	}

}
