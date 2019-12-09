 package candidat;


import java.util.Scanner;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class CandidatContainer extends CandidatGui{

	public static void main(String[] args) {  	
		try 
		{	 
			  Runtime runtime= Runtime.instance();
			  ProfileImpl profileImpl = new ProfileImpl(false);
			  profileImpl.setParameter(profileImpl.MAIN_HOST,"localhost");
					
			  AgentContainer agentContainer;
			  agentContainer = runtime.createAgentContainer(profileImpl);
				
			  System.out.println("Entrer Le Nom Du Candidat");
			  Scanner sc = new Scanner(System.in);
			  String candidat_nom = sc.next();
			  
			  AgentController agentController = agentContainer.createNewAgent(candidat_nom,CandidatAgent.class.getName(), new Object[] {});	  
   		      agentController.start();	
			 	
		} 
		catch (ControllerException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}		   
	}
}
