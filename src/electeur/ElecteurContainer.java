package electeur;

import java.util.Scanner;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class ElecteurContainer {
	
	public static void main(String[] args) {	
		try {
			Runtime runtime=Runtime.instance();
			ProfileImpl profileImpl=new ProfileImpl(false);
			profileImpl.setParameter(profileImpl.MAIN_HOST, "localhost");
			AgentContainer agentContainer=runtime.createAgentContainer(profileImpl);
			
			System.out.println("Entrer Le Nom Du Votant:");
			Scanner sc = new Scanner(System.in);
			String votant_nom = sc.next();
			
			AgentController agentController=agentContainer.createNewAgent(votant_nom, ElecteurAgent.class.getName() ,new Object []{}); 		
			agentController.start();
		} catch (StaleProxyException e)
		
		{ 
			e.printStackTrace();
		}
	}

}
