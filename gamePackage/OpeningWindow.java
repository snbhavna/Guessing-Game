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
	
	public JLabel label;
	public JPanel panel;
	public JButton option1, option2, option3, option4, option5;
	public String fileName;
	public boolean namesLoaded;
	
	private class Listener implements ActionListener
	{		
		public void actionPerformed(ActionEvent e)
	    {  
			//What to do when button is pressed 	
			if(e.getSource() == option1)
			{
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(null);
				if(result == JFileChooser.OPEN_DIALOG)  
				{ 
					// set the label to the path of the selected file 
					fileName = (fc.getSelectedFile().getAbsolutePath()).toString(); 
					try {
						int numStrings = 0;
						File file = new File(fileName);
						BufferedReader buf = new BufferedReader(new FileReader(file)); 
						String st = ""; 
						while ((st = buf.readLine()) != null) 
							numStrings++ ;
						String msg = "File Loaded successfully, " + numStrings + " strings found";
						JOptionPane.showMessageDialog(null, msg, "Load File", JOptionPane.INFORMATION_MESSAGE);
						if(numStrings > 0)
							namesLoaded = true;
						else
							JOptionPane.showMessageDialog(null, msg, "Load File with valid strings", JOptionPane.INFORMATION_MESSAGE);
						if(namesLoaded == true)
							option5.setEnabled(true);
					}
					catch (FileNotFoundException ex) {
			            ex.printStackTrace();
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        } 
				} 
			}			
			else if(e.getSource() == option2)
			{
				Users temp = new Users();
				temp.Display();
			}
			else if(e.getSource() == option3)
			{
				Users temp = new Users();	
				String userName = "";
		        
	        	int numEntries = temp.readScoreboard();
	        	if(numEntries == 10)
	        	{
	        		int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to replace the last user name?");
					if(r == JOptionPane.YES_OPTION)
					{
						userName = JOptionPane.showInputDialog("Enter User Name");
			            temp.addUser(userName);
					}
	        	}
	        	else
	        	{
	        		userName = JOptionPane.showInputDialog("Enter User Name");
		            temp.addUser(userName);
	        	}		
			}
			else if(e.getSource() == option4)
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
				MainGame obj = new MainGame();
				obj.startGame(fileName);
			}
	    }
	}
	
	public OpeningWindow()
	{		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		panel = new JPanel();
		GridBagLayout layout = new GridBagLayout();  
		GridBagConstraints gbc = new GridBagConstraints();  
		panel.setLayout(layout);
		option1 = new JButton("Load Input File");
		option1.setPreferredSize(new Dimension(150, 50));
		option1.addActionListener(new Listener());
		option2 = new JButton("Highest Scores");
		option2.setPreferredSize(new Dimension(150, 50));
		option2.addActionListener(new Listener());
		option3 = new JButton("Add User");
		option3.setPreferredSize(new Dimension(150, 50));
		option3.addActionListener(new Listener());
		
		option5 = new JButton("Start Game");
		option5.setPreferredSize(new Dimension(150, 50));
		option5.addActionListener(new Listener());
		option5.setEnabled(false);
		
		option4 = new JButton("Exit Game");
		option4.setPreferredSize(new Dimension(150, 50));
		option4.addActionListener(new Listener());
		
         
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
		panel.add(option5, gbc);
		gbc.gridx = 0;  
        gbc.gridy = 16;
		panel.add(option4, gbc);
		
		label = new JLabel();
		label.setIcon(new ImageIcon(new ImageIcon("C:\\Users\\snbha\\Desktop\\Learning\\Java\\icon.jpg").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
		JPanel iconPanel = new JPanel();
		iconPanel.add(label);
		
		mainPanel.add(panel);
		mainPanel.add(iconPanel);
	
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\snbha\\Desktop\\Learning\\Java\\icon.jpg");  
		this.setIconImage(icon);
		this.add(mainPanel);
		
		this.setSize(500, 500);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("GUESSING GAME");		
		//this.setBackground(Color.red);
	}
	
	public void trialmain() 
	{
		new OpeningWindow();
	}
	
}
