package gamePackage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*; 

public class OpeningWindow extends JFrame{
	
	public JFrame frame1; 
	public JLabel label;
	public JPanel panel;
	public JButton option1, option2, option3;
	static public JButton option4;
	static public String fileName;
	static public boolean namesLoaded;
	static public Users currentUser;
	static public boolean startGame;
	
	private class Listener implements ActionListener
	{		
		public void actionPerformed(ActionEvent e)
	    {  
			//What to do when button is pressed 	
			if(e.getSource() == option1)
			{
				Users temp = new Users();
				temp.loadFile();
					
			}			
			else if(e.getSource() == option2)
			{
				Users temp = new Users();
				temp.Display();
			}			
			else if(e.getSource() == option3)
			{
				int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to leave?");
				if(r == JOptionPane.YES_OPTION)
				{
					JOptionPane.showMessageDialog(null, "Will miss you! Come back soon!", "Bye Bye", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
			}
			else
			{
				//remove the previous JFrame
				frame1.setVisible(false);
				//frame1.dispose();
				
				startGame = true;   
			}
	    }
	}
	public OpeningWindow(boolean b)
	{
		//just an optional constructor that can be called if the menu is not required 
	}
	public OpeningWindow()
	{	
		
		currentUser = new Users(false);
		startGame = false;
		fileName = "C:\\Program Files\\GuessingGame\\Names.txt";
		
		frame1 = new JFrame();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();  
		GridBagConstraints gbc = new GridBagConstraints();  
		panel.setLayout(layout);
		option1 = new JButton("Login");
		option1.setPreferredSize(new Dimension(150, 50));
		option1.addActionListener(new Listener());
		option2 = new JButton("Highest Scores");
		option2.setPreferredSize(new Dimension(150, 50));
		option2.addActionListener(new Listener());		
		option3 = new JButton("Exit Game");
		option3.setPreferredSize(new Dimension(150, 50));
		option3.addActionListener(new Listener());    
		option4 = new JButton("Start Game");
		option4.setPreferredSize(new Dimension(150, 50));
		option4.addActionListener(new Listener());
		option4.setEnabled(true);
		
        gbc.fill = GridBagConstraints.VERTICAL;  
        gbc.gridx = 0;  
        gbc.gridy = 0;		
		panel.add(option1, gbc);
		gbc.gridx = 0;  
        gbc.gridy = 4;
		panel.add(option2, gbc);
		gbc.gridx = 0;  
        gbc.gridy = 8;
		panel.add(option3, gbc);
		gbc.gridx = 0;  
		gbc.gridy = 12;
		panel.add(option4, gbc);
		
		label = new JLabel();
		label.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\snbha\\Desktop\\Learning\\Java\\icon.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		JPanel iconPanel = new JPanel();
		iconPanel.add(label);
		
		mainPanel.add(panel);
		mainPanel.add(iconPanel);
	
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\snbha\\Desktop\\Learning\\Java\\icon.jpg");  
		frame1.setIconImage(icon);
		frame1.add(mainPanel);
		
		frame1.setSize(500, 500);
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setResizable(false);
		frame1.setVisible(true);		
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setTitle("GUESSING GAME");		
	}
}
