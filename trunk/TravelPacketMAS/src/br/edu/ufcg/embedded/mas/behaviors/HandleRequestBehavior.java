package br.edu.ufcg.embedded.mas.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.edu.ufcg.embedded.mas.agents.TravelBrokerAgent;
import br.edu.ufcg.embedded.mas.yellowpages.YellowPagesManager;

public class HandleRequestBehavior extends Behaviour {

	private ACLMessage clientRequest;
	private int steps;
	List<ACLMessage> responses;
	private int MAX_VALUE = 4;
	private String conversationId;

	public HandleRequestBehavior(Agent agent, ACLMessage clientRequest) {
		super(agent);
		this.clientRequest = clientRequest;
		steps = 0;
		responses = new LinkedList<ACLMessage>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -1805273051708269061L;

	private ACLMessage getMessage() {
		return myAgent.receive(MessageTemplate.and(MessageTemplate.or(
				MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
				MessageTemplate.MatchPerformative(ACLMessage.FAILURE)),
				MessageTemplate.MatchConversationId(conversationId)));
	}

	private void sendMessage(AID aid, Serializable obj) {
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		try {
			message.setContentObject(obj);
			message.addReceiver(aid);
			message.setConversationId(conversationId);
			myAgent.send(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void replyError(String message) {
		ACLMessage reply = clientRequest.createReply();
		reply.setPerformative(ACLMessage.FAILURE);
		reply.setContent(message);
		myAgent.send(reply);
		steps = MAX_VALUE;
	}

	public void action() {
		switch (steps) {
		case 0:
			System.out.println("searching for flightBrokers");
			AID flightBroker = YellowPagesManager.findBrokers(myAgent,
					"flight-broker");

			if (flightBroker != null) {
				ACLMessage messageToFlight = new ACLMessage(ACLMessage.REQUEST);

				// TODO setar parametros

				// TODO considerar varios agentes de um tipo so (prices)

				messageToFlight.addReceiver(flightBroker);
				conversationId = String.valueOf(System.currentTimeMillis());
				messageToFlight.setConversationId(conversationId);
				System.out.println("sending request to flight-broker");
				myAgent.send(messageToFlight);

				steps++;
			} else {
				replyError("No flight broker agent found");
			}

			break;
		case 1:
			ACLMessage messageFromFlightBroker = getMessage();

			if (messageFromFlightBroker != null) {
				System.out.println(messageFromFlightBroker);
				// TODO armazena o servico proposto pelo flight
				if (messageFromFlightBroker.getPerformative() == ACLMessage.INFORM) {

					responses.add(messageFromFlightBroker);

					AID hostingBroker = YellowPagesManager.findBrokers(myAgent,
							"hosting-broker");

					if (hostingBroker != null) {
						sendMessage(hostingBroker, null);
						steps++;
					} else {
						replyError("No Hosting broker agents found!");
					}

				} else {
					replyError(messageFromFlightBroker.getContent());
				}
			}

			break;
		case 2:
			ACLMessage messageFromHostingBroker = getMessage();

			if (messageFromHostingBroker != null) {
				System.out.println(messageFromHostingBroker);
				// TODO armazena o servico proposto pelo hosting
				if (messageFromHostingBroker.getPerformative() == ACLMessage.INFORM) {

					AID transportationBroker = YellowPagesManager.findBrokers(
							myAgent, "transportation-broker");

					if (transportationBroker != null) {
						steps++;
						sendMessage(transportationBroker, null);
					} else {
						replyError("No transportationBroker broker agents found!");
					}

				} else {
					replyError(messageFromHostingBroker.getContent());
				}
			}

			break;
		case 3:
			System.out.println("ENTERING IN STEP 3");
			ACLMessage messageFromTransportationBroker = getMessage();
			System.out.println(messageFromTransportationBroker);
			if (messageFromTransportationBroker != null) {
				System.out.println(messageFromTransportationBroker);
				// TODO armazena o servico proposto pelo hosting
				if (messageFromTransportationBroker.getPerformative() == ACLMessage.INFORM) {
					ACLMessage packet = clientRequest.createReply();
					packet.setPerformative(ACLMessage.CONFIRM);
					packet.setContent("COMPLETED PACKET!");
					((TravelBrokerAgent) myAgent).completeTravelPacket(packet);
					steps = MAX_VALUE;
				} else {
					replyError(messageFromTransportationBroker.getContent());
				}
			}

			break;
		}

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return steps == MAX_VALUE;
	}

}
