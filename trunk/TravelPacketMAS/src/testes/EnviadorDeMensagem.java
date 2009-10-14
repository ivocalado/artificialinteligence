package testes;

import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class EnviadorDeMensagem extends TickerBehaviour{
	private static final long serialVersionUID = 4049078233091748916L;
	//private Rete maquina;
	private int contador;
	private AgenteEnviadorDeMensagem enviador;
	private static int count=0;
	

	public EnviadorDeMensagem(AgenteEnviadorDeMensagem enviador) {
		super(enviador,3000);
		this.enviador = enviador;
	}

	public void onTick(){
		try {
			MessageTemplate template = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage message = new ACLMessage(ACLMessage.INFORM);
			message.setContent((count++==3)?"TERMINATE":"Conteudo qualquer! =p");
//			message.setContent("conteudo");
			message.addReceiver(this.enviador.getRecebedor());
			this.myAgent.send(message);	
			System.out.println("Enviando............");
			
			ACLMessage response = this.myAgent.receive();
			if (response!=null) {
				System.out.println("Mensagem recebida: " + response);
				//if (response.getContent().endsWith("terminated")) {
				//	System.out.println("So, I will end too :P");
					this.myAgent.doDelete();
					
				//}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("**" + e.getMessage());
		}
		
	}
	

}
