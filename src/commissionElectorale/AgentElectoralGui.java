package commissionElectorale;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AgentElectoralGui extends JFrame{

	private JTextArea jTextAreaMess =new JTextArea();
	private AgentElectoral agentelectoral;
	
	public AgentElectoralGui() {
		
		 jTextAreaMess.setEditable(false);
    	 jTextAreaMess.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));
    	 JPanel jpanelN=new JPanel();
    	 this.setLayout(new BorderLayout());
    	 this.add(jpanelN,BorderLayout.NORTH);
    	 this.add(new JScrollPane(jTextAreaMess),BorderLayout.CENTER);
    	 this.setSize(600,400);
    	 this.setVisible(true); 
	}
	//AFFICHER MESSAGE SUR TEXT AREA
	public void AfficherMessageCENI(String msg,boolean append) 
	{
		if(append=true) {
			jTextAreaMess.append(msg+"\n");
		}
		else 
		{
			jTextAreaMess.setText(msg);
		}
	}

	public AgentElectoral getAgentelectoral() {
		return agentelectoral;
	}
	public void setAgentelectoral(AgentElectoral agentelectoral) {
		this.agentelectoral = agentelectoral;
	}
}
