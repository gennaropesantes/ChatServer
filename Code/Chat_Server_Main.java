package Chat_Server_4;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat_Server_Main extends JApplet implements ActionListener{

	TextField messageTextField = new TextField("Type your message");
	JButton sendButton = new JButton("Send");
	JButton disconnectButton = new JButton("Disconnect");
	JButton privateButton = new JButton("Send Private Message");
	JTextArea displayMessageArea = new JTextArea(); // set up JTextArea to display messages
	JTextArea displayUserArea = new JTextArea();
	JPanel centerPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel rightPanel = new JPanel();

	public Chat_Server_Main(){
		
	}
	
	public void init(){
		
		//Container container = getContentPane();
		
		add(bottomPanel, BorderLayout.SOUTH);
		

		bottomPanel.add(disconnectButton);
		disconnectButton.addActionListener(this);
		
		bottomPanel.add(privateButton);
		privateButton.addActionListener(this);
		
		add(bottomPanel, BorderLayout.SOUTH);
		bottomPanel.add(messageTextField);
		this.setSize(20, 20);
		
		bottomPanel.add(sendButton);
		sendButton.addActionListener(this);
		
		add(rightPanel, BorderLayout.CENTER);
		rightPanel.setBackground(Color.WHITE);
		rightPanel.add(displayUserArea);
		displayUserArea.setEditable(false);
		this.setSize(100, 100);
        
		add(centerPanel, BorderLayout.CENTER);
		centerPanel.setBackground(Color.WHITE);
		centerPanel.add(displayMessageArea);
        displayMessageArea.setEditable(false);
      //  container.add(new JScrollPane(displayMessageArea), BorderLayout.NORTH );
    	this.setSize(100, 100);
        
    	
    	setSize(710,400);
   
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand()==("Send")) {
			String sentMessage = messageTextField.getText();
			ChatServerThread.send(sentMessage);
			displayMessageArea.append(sentMessage);	
		}
		
		if(e.getActionCommand()==("Disconnect")) {
			
		}
		
		if(e.getActionCommand()==("Send Private Message")){
			
		}
	}
	
	
}
