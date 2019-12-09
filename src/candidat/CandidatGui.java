package candidat;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jade.gui.GuiEvent;


public class CandidatGui extends JFrame{
	private JLabel jLabelAgent = new JLabel("Nom Candidat :");
	private JTextField jTextFieldAgent = new JTextField(20);
	private JButton jButtonEnregistrer = new JButton("ENREGISTRER");
	private JTextArea jTextAreaMess =new JTextArea(); 
	 
	private CandidatAgent candidatagent;
		
    public CandidatGui() {    	
    	 jTextAreaMess.setEditable(false);
    	 jTextAreaMess.setFont(new Font("Arial", Font.CENTER_BASELINE, 14));
    	 JPanel jpanelN=new JPanel();
    	 jpanelN.setLayout(new FlowLayout());
    	 jpanelN.add(jLabelAgent);
    	 jpanelN.add(jTextFieldAgent);
    	 jpanelN.add(jButtonEnregistrer);
    	 this.setLayout(new BorderLayout());
    	 this.add(jpanelN,BorderLayout.NORTH);
    	 this.add(new JScrollPane(jTextAreaMess),BorderLayout.CENTER);
    	 this.setSize(600,400);
    	 this.setVisible(true);
    	 
    	 //action lier au boutton
    	 jButtonEnregistrer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String nom_candidat = jTextFieldAgent.getText();	
				jTextFieldAgent.setText(nom_candidat);
				
				GuiEvent gev=new GuiEvent(this, 1);	
				Map<String, Object> params = new HashMap<>();
				params.put("Nom_candidat", nom_candidat);
				gev.addParameter(params);
				jTextFieldAgent.setText(" ");			
			}
		}); 	 
    }
            
    public CandidatAgent getCandidatagent() {
		return candidatagent;
	}

	public void setCandidatagent(CandidatAgent candidatagent) {
		this.candidatagent = candidatagent;
	}

	//Methode pour afficher dans le TextArea.
    public void AfficherMessage(String msg,boolean append) {
		if(append=true) {
			jTextAreaMess.append(msg+"\n");
		}
		else 
		{
			jTextAreaMess.setText(msg);
		}
	}

	public JTextField getjTextFieldAgent() {
		return jTextFieldAgent;
	}

	public void setjTextFieldAgent(JTextField jTextFieldAgent) {
		this.jTextFieldAgent = jTextFieldAgent;
	}
}
