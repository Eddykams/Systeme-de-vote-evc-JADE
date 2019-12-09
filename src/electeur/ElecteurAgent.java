package electeur;

import java.util.ArrayList;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class ElecteurAgent extends Agent{
	
	private ArrayList<String> listecandidatx = new ArrayList<String>();
	 private String ResulatAfficher="";
	 private int compteur=0;

	@Override
	protected void setup() {
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		/* 
		 * Demande de la liste des candidats a l agent Electoral
		 */
		parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				// TODO Auto-generated method stub
				DFAgentDescription dfadcandidat = new DFAgentDescription();
				dfadcandidat.setName(getAID());
				ServiceDescription sdcandidat = new ServiceDescription();
				sdcandidat.setType("Electeur");
				sdcandidat.setName("Electeur-Candidat");
				dfadcandidat.addServices(sdcandidat);			
				try {
					DFService.register(myAgent, dfadcandidat);
					System.out.println("Enregistrement de l'Agent Electeur...");
					} catch (FIPAException e) {
					e.printStackTrace();
				}
				/*
				 * MESSAGE AVEC L'AGENT ELECTORAL POUR RECUPERER LA LISTE DES CANDIDATS
				 */
				String contentMsg = "demande Liste candidat";
				
				ACLMessage msgListeCandidat = new ACLMessage(ACLMessage.REQUEST);
				msgListeCandidat.setContent(contentMsg);
				msgListeCandidat.addReceiver(new AID("Agent_Electoral", AID.ISLOCALNAME));
				send(msgListeCandidat);
			}
		});
		/*
		 * RECEPTION DU MESSAGE ENVOYE A L'AGENT ELECTORAL
		 * @Eddy
		 */
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {		
			@Override
			public void action() {
				ACLMessage aclMessage = receive();
				
				if(aclMessage!=null) {
					String msgContent=aclMessage.getContent();
					System.out.println("****************************************************");
					//System.out.println("La liste des candidats demandé est"+" "+ msgContent);
					System.out.println(" "+ResulatAfficher);
					System.out.println("****************************************************");
					/*
					 * test one ----test vote Aleatoire d'un candidat de La Liste
					 * @Eddy
					 */		
					  DFAgentDescription dfadCanditature = new DFAgentDescription();
	                  ServiceDescription sdCanditature = new ServiceDescription();
	                  sdCanditature.setType("Candidature");
	                  dfadCanditature.addServices(sdCanditature);   
	                  try {
	                	  DFAgentDescription[] dfdcandidat =DFService.search(myAgent, dfadCanditature);
	                	 
	                	  for(int i=0; i< dfdcandidat.length; i++) {
	                		  /*
	                		   * VOTE ALEATOIRE
	                		   */         		  
	                		listecandidatx.add(dfdcandidat[i].getName().getLocalName().toLowerCase());			          	
	                	  }
	                		Random rand = new Random();
	  						int RandI= rand.nextInt(listecandidatx.size());
	  						String choix = listecandidatx.get(RandI);
	  						System.out.println("****************************************************");
	                        System.out.println("Le choix de l electeur est"+ " "+choix);    
	                        
	                        ACLMessage msgListeCandidat = new ACLMessage(ACLMessage.INFORM);
	    					msgListeCandidat.setContent(choix);
	    					msgListeCandidat.addReceiver(new AID("Agent_Electoral", AID.ISLOCALNAME));
	    					send(msgListeCandidat);  
	                	  
					} catch (FIPAException e) {
						e.printStackTrace();
					}			
				}			
			}
		});
		
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				  
				  DFAgentDescription dfadCanditature = new DFAgentDescription();
                  ServiceDescription sdCanditature = new ServiceDescription();
                  sdCanditature.setType("Candidature");
                  dfadCanditature.addServices(sdCanditature);   
                    
                  try {
                	  DFAgentDescription[] dfdcandidat =DFService.search(myAgent, dfadCanditature);
                	 
                	  for(int i=0; i<dfdcandidat.length; i++) {            		  
                		  if(compteur<2)
                		  ResulatAfficher+=(i+1)+"-"+dfdcandidat[i].getName().getLocalName().toUpperCase()+"\n";
                          compteur+=1;                
                	  }	  
				} catch (FIPAException e) {
					e.printStackTrace();
				}				
			}
		});		
	}
	@Override
	protected void takeDown() {
		System.out.println("DESTRUCTION DE L AGENT ELECTEUR");
	}
}
