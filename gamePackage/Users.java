package gamePackage;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.*;

public class Users implements Serializable{
	private transient static final long serialVersionUID = 1L;
	private String userName;
	private int score;
	private transient JButton confirm;
	private transient JButton cancel;
	private transient JTextField t1;
	private transient static String scoreFileName;
	public transient static String errorFileName;
	private transient JFrame f1;
	public static Logger logger;
	public static FileHandler fh;
	
	public static void main(String[] args)
	{
		OpeningWindow o1 = new OpeningWindow();
		MainGame obj = new MainGame();
		o1.createOpeningForm();
		while(true)
		{
			if(o1.getVal() == true)
			{
				//remove the previous JFrame
				Users.logger.info("Opening frame set to invisible");  
				o1.setFrame(false);
				//frame1.dispose();
				Users.logger.info("Start main game"); 
			
				while((o1.getVal() == true) || (obj.getB2() == true))
				{
					try
					{
						o1.setVal(false);						
						obj.createForm();					
						obj.startGame();
					}
					catch(Exception ex)
					{
						try
						{
							
							ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					                new FileOutputStream(Users.errorFileName));
							objectOutputStream.writeObject(ex);
							objectOutputStream.close();
						}
						catch(Exception excp) {
							excp.printStackTrace();
						}
					}					
				}
				if(obj.getValue() == true)
				{
					obj.setValue(false);
					o1.setFrame(true);
				}
			}
		}
	}
	
	public String toString() {
		return "Name:" + userName + "\tScore: " + score ;
	}
	
	private class Listener implements ActionListener
	{		
		public void actionPerformed(ActionEvent e)
	    {  
			//What to do when button is pressed 	
			if(e.getSource() == confirm)
			{
				/*if(t1.getText().toUpperCase().contentEquals("ADMIN") == true)
				{
					//can load input file
					loadFile.setEnabled(true);
					
				} */
				boolean res = addUser(t1.getText().toUpperCase());
				String m = "";
				OpeningWindow.currentUser.userName = t1.getText().toUpperCase();
				OpeningWindow.currentUser.score = 0;
				if(res == true)
				{
					m = "New Username added, Welcome " + OpeningWindow.currentUser.userName; 
					JOptionPane.showMessageDialog(null, m, "Login", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					m = "Welcome " + OpeningWindow.currentUser.userName; 
					JOptionPane.showMessageDialog(null, m, "Login", JOptionPane.INFORMATION_MESSAGE);
				}
			}			
			else if (e.getSource() == cancel)
			{
				f1.setVisible(false);
				f1.dispose();
			}
	    }
	}
	
	public Users(boolean b)
	{
		this.userName = "ADMIN";
		this.score = 0;
		scoreFileName = "files\\Scores.txt";
	}
	
	public Users()
	{				
		userName = "";
		score = 0;
		scoreFileName = "files\\Scores.txt";
		errorFileName = "files\\Error.txt";	  
		try
		{
			File tempFile = new File(scoreFileName);
			tempFile.createNewFile();
		}
		catch(Exception e)
		{
			try
			{
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			}
			catch(Exception excp) {
				excp.printStackTrace();
			}
		}
	}
	
	public void loadFile()
	{	
		//UI window
		f1 = new JFrame();
		JPanel p1 = new JPanel();
		
		JLabel l1 = new JLabel("Enter User Name: ");
		t1 = new JTextField("ADMIN");
		confirm = new JButton("OK");
		confirm.addActionListener(new Listener());
		cancel = new JButton("CLOSE");
		cancel.addActionListener(new Listener());
		/*loadFile = new JButton("Load Input File");
		loadFile.setPreferredSize(new Dimension(150, 50));
		loadFile.addActionListener(new Listener());
		loadFile.setEnabled(false); */
		
		p1.add(l1);
		p1.add(t1);
		p1.add(confirm);
		//p1.add(loadFile);
		p1.add(cancel);
	
		Image icon = Toolkit.getDefaultToolkit().getImage("files\\icon.jpg");  
		f1.setIconImage(icon);
		f1.add(p1);
		
		f1.setSize(500, 500);
		f1.pack();
		f1.setLocationRelativeTo(null);
		f1.setResizable(false);
		f1.setVisible(true);		
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setTitle("GUESSING GAME");
	}
	
	public boolean addUser(String name)
	{
		Users[] listUsers = new Users[10];
		int i = 0;
		Users.logger.info("Add user to list");  
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
			        new FileInputStream(scoreFileName));
			
			// get the object
			while(i != 10)
			{
				listUsers[i] = (Users) objectInputStream.readObject();
				if(listUsers[i].userName.compareTo(name) == 0)
				{
					objectInputStream.close();	
					return false;
				}
				i++;
			}
			objectInputStream.close();			
	        
		}
		 catch (FileNotFoundException ex) {
			 try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(ex);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
        } catch (IOException ex) {
            if(i < 10)
            {
            	listUsers[i] = new Users();
            	listUsers[i].userName = name;
            	listUsers[i].score = 0;
            	i++;
            }
            try
			{
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(scoreFileName));
				for(int j = 0; j < i; j++)
				{
					objectOutputStream.writeObject(listUsers[j]);
					objectOutputStream.flush();
				}
				objectOutputStream.close();
			}
            catch(Exception exe)
			{
            	try
	   			 {
	   				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
	   		                new FileOutputStream(Users.errorFileName));
	   				objectOutputStream.writeObject(exe);
	   				objectOutputStream.close();
	   			 }
	   			 catch(Exception excp)
	   			 {
	   				 excp.printStackTrace();
	   			 }
			}
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
		} 
		return true;
	}
	
	public void updateScore(Users user)
	{
		Users[] players = new Users[10];
		Users temp = new Users();
		int i = 0, j = 0, k = 0;
		Users.logger.info("Upate score");  
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
			        new FileInputStream(scoreFileName));
            while(i < 11)
            {
	            players[i] = (Users) objectInputStream.readObject();
	            i++;
            }
            objectInputStream.close();
        } catch (FileNotFoundException e) {
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
        } catch (IOException e) {
        	; //will be handled below
        } catch (ClassNotFoundException e) {
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
        } 
		for(j = 0; j < i; j++)
		{
			if(players[j].userName.compareTo(user.userName) == 0)
				break;
		}
		if (j < i)
		{
			players[j].score++;
		}
		//sort the players in descending order
		for(j = 0; j < i; j++)
		{
			for(k = j+1; k < i; k++)
			{
				if(players[j].score < players[k].score)
				{
					temp = players[j];
					players[j] = players[k];
					players[k] = temp;
				}
			}
		}
		try
		{
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
	                new FileOutputStream(scoreFileName));
			for(j = 0; j < i; j++)
			{
				objectOutputStream.writeObject(players[j]);
				objectOutputStream.flush();
			}
			objectOutputStream.close();
		}
        catch(Exception exe)
		{
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(exe);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
		}
	}
	
	public int readScoreboard()
	{
		/* At any given point, only details of 10 highest scorers 
		 * will be saved to file
		 */
		Users[] players = new Users[10];
		int i = 0;
		Users.logger.info("Read scoreboard");  
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
			        new FileInputStream(scoreFileName));
            while(i < 11)
            {
	            players[i] = (Users) objectInputStream.readObject();
	            i++;
            }
            objectInputStream.close();
        } catch (FileNotFoundException e) {
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
        } catch (IOException e) {
        	return 0;
        } catch (ClassNotFoundException e) {
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
        } 
        return i;
	}
	
	public void Display()
	{
		/* At any given point, only details of 10 highest scorers 
		 * will be saved to file
		 */
		Users[] players = new Users[10];
		int i = 0;
		logger.info("Display user list");  
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
			        new FileInputStream(scoreFileName));
			 while(i < 11)
            {
	            players[i] = (Users) objectInputStream.readObject();
	            i++;
            }                     
			 objectInputStream.close();
        } catch (FileNotFoundException e) {
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
        } catch (IOException e) {
        	if(i == 0)
        	{
        		JOptionPane.showMessageDialog(null, "No Users registered yet", "Highest Scores", JOptionPane.INFORMATION_MESSAGE);
        	}
        	else
        	{
        		String msg = "USER     SCORE\n";
        		for(int num = 0; num < i; num++)
        		{
        			msg += players[num].userName +"     "+players[num].score + "\n"; 
        		}
        		JOptionPane.showMessageDialog(null, msg, "Highest Scores", JOptionPane.INFORMATION_MESSAGE);
        	}
        } catch (ClassNotFoundException e) {
        	try
			 {
				 ObjectOutputStream objectOutputStream = new ObjectOutputStream(
		                new FileOutputStream(Users.errorFileName));
				objectOutputStream.writeObject(e);
				objectOutputStream.close();
			 }
			 catch(Exception excp)
			 {
				 excp.printStackTrace();
			 }
        } 
	} 	
	
}