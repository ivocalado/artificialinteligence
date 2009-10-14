package br.edu.ufcg.embedded.mas.agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public abstract class YellowPagesAgents extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3966631940700293180L;
	private String name;
	private String type;
	private String owner;

	public YellowPagesAgents(String name, String type, String owner) {
		this.name = name;
		this.type = type;
		this.owner = owner;
	}
	
	
	
	/* (non-Javadoc)
	 * @see jade.core.Agent#setup()
	 */
	@Override
	protected void setup() {
		registerInYellowPages(name, type, owner);	
	}



	private boolean registerInYellowPages(String name, String type, String owner){
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setName(name);
		serviceDescription.setType(type);
		serviceDescription.setOwnership(owner);

		System.out.println("name: " + name);
		System.out.println("type: " + type);
		System.out.println("owner: " + owner);
		
		DFAgentDescription serviceDesciptionAgent = new DFAgentDescription();
		serviceDesciptionAgent.setName(this.getAID());
		serviceDesciptionAgent.addServices(serviceDescription);
		
		try{
			DFService.register(this, serviceDesciptionAgent);
			return true;
		}catch(FIPAException e){
			e.printStackTrace();
			return false;
		}
	}
}
