package commissionElectorale;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class AgentElectoralContainer {	
	/*
	 * CREATION DE L'AGENT ELECTORAL
	 */
	public static void main(String[] args) {	
		try 
		{
			Runtime runtime=Runtime.instance();
			ProfileImpl profileImpl=new ProfileImpl(false);
			profileImpl.setParameter(Profile.MAIN_HOST, "localhost");
			
			 AgentContainer agentContainer;
			 agentContainer = runtime.createAgentContainer(profileImpl);
			 
			AgentController agentController=agentContainer.createNewAgent("Agent_Electoral",AgentElectoral.class.getName() ,new Object []{});	
			agentController.start();
		  } 
		catch (ControllerException  ex) {
			ex.printStackTrace();
		}	
	}
}
