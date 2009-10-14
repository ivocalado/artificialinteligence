package testes;

import jade.Boot;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class AgenteRecebendoMensagem extends Agent{
	private static final long serialVersionUID = 3257284742721124402L;
	//private Rete maquina;

	public void setup(){
		this.cadastroNasPaginasAmarelas("recebedor");
                System.out.println("Cadastro OK");
		this.addBehaviour(new RecebedorDeMensagensTeste(this));
	}

	public static void main(String[] args) {
		Boot.main(new String[]{"-container","Eduardo:testes.AgenteRecebendoMensagem"});
	}

	public boolean cadastroNasPaginasAmarelas(String nome){
		ServiceDescription descricaoDeUmServico = new ServiceDescription();
		descricaoDeUmServico.setName(nome);
		descricaoDeUmServico.setType(nome);

		DFAgentDescription descritorDeServicosDeUmAgente = new DFAgentDescription();
		descritorDeServicosDeUmAgente.addServices(descricaoDeUmServico);
		
		try{
			DFService.register(this,descritorDeServicosDeUmAgente);
			return true;
		}catch(FIPAException e){
			e.printStackTrace();
			return false;
		}
	}
}
