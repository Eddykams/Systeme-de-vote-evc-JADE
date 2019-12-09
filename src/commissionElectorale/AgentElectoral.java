package commissionElectorale;

import java.util.ArrayList;
import java.util.Random;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class AgentElectoral extends GuiAgent{
	
	AgentElectoralGui gui;
	
	private AID[] listecandidatinscrit;
	private ArrayList<String> listecandidat = new ArrayList<String>();
	private int nbrecandidat=0;
	private int nbreelecteur=0;
	private int nbrevotantcandidat1=0;
	private int nbrevotantcandidat2=0;
	private String nomcandidat1;
	private String nomcandidat2;
	
	 
	@Override
	protected void setup() {
		/*
		 * CHARGER L'INTERFACE GRAPHIQUE AU DEMARRAGE DE L'AGENT
		 */
		gui = new AgentElectoralGui();
		gui.setAgentelectoral(this);
		gui.AfficherMessageCENI("DEMARRAGE DE L AGENT ELECTORAL", true);
		
		ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
		addBehaviour(parallelBehaviour);
		
		/*
		 * RECUPERER LA LISTE DES CANDIDATS INSCRITS POUR LES ELECTIONS
		 */
		parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				
				DFAgentDescription dfAgentCandidature = new DFAgentDescription();
				ServiceDescription sdCandidature = new ServiceDescription();
				sdCandidature.setType("candidature");
				dfAgentCandidature.addServices(sdCandidature);
				
				try {
					//Parcourir les services publiE pour recuperer la liste des candidats				
					DFAgentDescription[] dfRechercheCandidature = DFService.search(myAgent, dfAgentCandidature);
					listecandidatinscrit = new AID[dfRechercheCandidature.length];
					String choix;			
					
					for(int i=0; i<dfRechercheCandidature.length;i++) {
						nbrecandidat+=1;
						listecandidat.add(dfRechercheCandidature[i].getName().getLocalName().toLowerCase());				
					}			
				} 
				catch (FIPAException e) {
					e.printStackTrace();
				}				
			}
		});
		
		/*
		 * RECUPERATION DU MSG SUR LA DEMANDE DES LISTES ET TRAITEMENTS
		 */
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage aclMessage=receive();
				if(aclMessage!=null){
					//REPONSE A L'AGENT ELECTEUR
					if (aclMessage.getPerformative() == ACLMessage.REQUEST)
					{
						String contentMsg = String.valueOf(nbrecandidat);			
						ACLMessage msgListeCandidat = new ACLMessage(ACLMessage.INFORM);
						msgListeCandidat.setContent(contentMsg);
						msgListeCandidat.addReceiver(aclMessage.getSender());
						send(msgListeCandidat);
					}
					//CALCUL DES VOTANTS POUR LES ELECTIONS
				
					if(aclMessage.getPerformative()==ACLMessage.INFORM) 
					{
						String contentMsg=aclMessage.getContent();
						if(listecandidat.get(0).equals(contentMsg.toLowerCase()))
						{
							nbrevotantcandidat1+=1;
							nomcandidat1=contentMsg.toLowerCase().toLowerCase();
							gui.AfficherMessageCENI(nbrevotantcandidat1+" "+"vote pour le candidat"+" "+nomcandidat1, true);
						}
						
						if(listecandidat.get(1).equals(contentMsg.toLowerCase()))
						{
							nbrevotantcandidat2+=1;
							nomcandidat2=contentMsg.toLowerCase().toLowerCase();
							gui.AfficherMessageCENI(nbrevotantcandidat2+" "+"vote pour le candidat"+" "+nomcandidat2, true);					
						}
					}				
				}
				else block();
			}
		});		
		/*
		 * TROISIEME COMPORTEMENT RECUPARATION DE LA LISTE DES VOTANTS QUI SE SONT FAITS ENROLES
		 */
		parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {		
			@Override
			public void action() {
				  DFAgentDescription description=new DFAgentDescription();
                  ServiceDescription srv=new ServiceDescription();
                  srv.setType("Electeur");
                  description.addServices(srv);
                  try{
                      DFAgentDescription[] listeElecteur=DFService.search(myAgent, description);
                      nbreelecteur=listeElecteur.length;
                  } catch(FIPAException e){
                	  e.printStackTrace();
                  }			
               }
		});
		/*
		 * AFFICHAGE DES RESULTATS APRES CALCUL DES VOTES
		 */
	   parallelBehaviour.addSubBehaviour(new WakerBehaviour(this,100000) {	   
		   @Override
		protected void onWake() {
			   gui.AfficherMessageCENI("********************************************", true);
               gui.AfficherMessageCENI("***Publication des résultats de l'élection***", true);
               gui.AfficherMessageCENI("*********************************************", true);
               
               if(nbrevotantcandidat1>nbrevotantcandidat2) 
               {
            	   gui.AfficherMessageCENI(nbreelecteur+" "+"votants"+" "+nomcandidat1.toUpperCase()+" "+"est vainqueur avec"+" "+nbrevotantcandidat1+" "+"voies", true);
               }
               if(nbrevotantcandidat1<nbrevotantcandidat2)
               {
            	   gui.AfficherMessageCENI(nbreelecteur+" "+"votants"+" "+nomcandidat2.toUpperCase()+" "+"est vainqueur avec"+" "+nbrevotantcandidat2+" "+"voies", true);
               }
               if(nbrevotantcandidat1==nbrevotantcandidat2)
               {
            	   gui.AfficherMessageCENI("Egalite des voies entre les deux candidats", true);
               }
               gui.AfficherMessageCENI("************************************", true);
               gui.AfficherMessageCENI("************************************", true);
               gui.AfficherMessageCENI("****FIN PUBLICATION DES RESULTATS****", true);
               gui.AfficherMessageCENI("************************************", true);
               doDelete();            
		   }
	});	
	}
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		
	}
	
	@Override
	protected void takeDown() {
		gui.AfficherMessageCENI("DESTRUCTION DE L'AGENT", false);
	}
}
