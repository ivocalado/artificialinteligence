package testes;

import jade.Boot;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteEnviadorDeMensagem  extends Agent{
	private static final long serialVersionUID = 3257009856241284664L;
	private AID recebedor;

	public void setup(){
		this.recebedor = this.getAIDs("recebedor")[0];
		this.addBehaviour(new EnviadorDeMensagem(this));
	}

	protected AID[] getAIDs(String nome){
		ServiceDescription descricaoDeUmServico = new ServiceDescription();
		descricaoDeUmServico.setName(nome);
		descricaoDeUmServico.setType(nome);

		DFAgentDescription descritorDeServicosDeUmAgente = new DFAgentDescription();
		descritorDeServicosDeUmAgente.addServices(descricaoDeUmServico);
		try{
			DFAgentDescription results[] = DFService.search(this,descritorDeServicosDeUmAgente); 
			AID[] aid = new AID[results.length];
			for (int i = 0; i < results.length; i++) {
				aid[i] = results[i].getName();
			}
			return aid;
		}catch(FIPAException e){
			return null;
		}
	}

	protected AID getRecebedor() {
		return recebedor;
	}

	public static void main(String[] args) {
		Boot.main(new String[]{"-container","Alguem:testes.AgenteEnviadorDeMensagem"});
		//Boot.main(new String[]{"Alguem:testes.Enviador"});
	}

}
