package candidat;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class CandidatAgent extends GuiAgent{
	 
	@Override
	protected void setup() {
	
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
		parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {		
			@Override
			public void action() {
				/*
				 * Enregistrement de l'agent candidat
				 */
				DFAgentDescription dfad = new DFAgentDescription();
				dfad.setName(getAID());
				
				ServiceDescription sd = new ServiceDescription();
				sd.setType("candidature");
				sd.setName("Depot-candidature");
				dfad.addServices(sd);
				
				try {				
					DFService.register(myAgent, dfad);
					System.out.println("Enregistrement de l'agent candidat");		
					} 
				catch (FIPAException ex) {			
					ex.printStackTrace();
				}
				
			}
		});	
		/*
		 * RECEPTION DU MESSAGE VENANT DE LA PART DE L'AGENT ELECTORAL
		 */	
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {		
			@Override
			public void action() {		
				ACLMessage aclMessage = receive();
				if(aclMessage!=null) 
				{	/*
					 * Afficher la reponse dans Notre Text Area
					 */
					System.out.println("Sender : "+aclMessage.getSender() +" "+aclMessage.getContent());
				}				
			}
		});			
	}

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		
	}		
	//DESTRUCTION DES SERVICES DE L'AGENT
	@Override
	protected void takeDown() 
	{
		System.out.println("Destruction de l'agent candidat");;
	}	
}
