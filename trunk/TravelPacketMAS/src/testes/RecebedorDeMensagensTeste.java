package testes;

import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class RecebedorDeMensagensTeste extends TickerBehaviour{
	private static final long serialVersionUID = 3545803190919510326L;
	private static int TEMPO_ENTRE_INVESTIDAS = 4000;
	private AgenteRecebendoMensagem agenteAgencia1;

	public RecebedorDeMensagensTeste(AgenteRecebendoMensagem agenteAgencia1){
		super(agenteAgencia1,TEMPO_ENTRE_INVESTIDAS);
		this.agenteAgencia1 = agenteAgencia1;
	}
	
	public void onTick(){
		ACLMessage message = this.myAgent.receive();
		
                System.out.println("Investindo........");
		if (message != null){
			System.out.println("Sender: "+message.getSender().getName());
			System.out.println("Mensagem recebida: " + message);
			if (message.getContent().equals("TERMINATE")) {
				ACLMessage ret = message.createReply();
				ret.setContent("Agent:" + this.myAgent.getAID().getName()+ " terminated");
				//ACLMessage retMessage = new ACLMessage(ACLMessage.INFORM);
				
				
				this.agenteAgencia1.doDelete();
			}
		}
	}
}

