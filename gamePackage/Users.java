package gamePackage;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class Users implements Serializable {
	private transient static final long serialVersionUID = 1L;
	private String userName;
	private int score;
	public transient JButton confirm;
	public transient JButton cancel;
	public transient JTextField t1;
	public transient String scoreFileName;
	public transient String errorFileName;
	public transient JButton loadFile;
	public transient JFrame f1;
	
	public static void main(String[] args)
	{
		OpeningWindow o1 = new OpeningWindow();
		while(true)
		{
			if(OpeningWindow.startGame == true)
			{
				OpeningWindow.startGame = false;
				MainGame obj = new MainGame();
				obj.startGame(OpeningWindow.fileName);
			}
			if(MainGame.b1 == true)
			{
				MainGame.b1 = false;
				o1.frame1.setVisible(true);
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
			/* else if(e.getSource() == loadFile) //feature not used as of now
			{
				JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(null);
				if(result == JFileChooser.OPEN_DIALOG)  
				{ 
					// set the label to the path of the selected file 
					OpeningWindow.fileName = (fc.getSelectedFile().getAbsolutePath()).toString(); 
					try {
						int numStrings = 0;
						File file = new File(OpeningWindow.fileName);
						BufferedReader buf = new BufferedReader(new FileReader(file)); 
						String st = ""; 
						while ((st = buf.readLine()) != null) 
							numStrings++ ;
						String msg = "File Loaded successfully, " + numStrings + " strings found";
						JOptionPane.showMessageDialog(null, msg, "Load File", JOptionPane.INFORMATION_MESSAGE);
						if(numStrings > 0)
						{
							f1.setVisible(false);
							f1.dispose();
							OpeningWindow.namesLoaded = true;
						}
						else
							JOptionPane.showMessageDialog(null, msg, "Load File with valid strings", JOptionPane.INFORMATION_MESSAGE);
						if(OpeningWindow.namesLoaded == true)
							OpeningWindow.option4.setEnabled(true);
					}
					catch (FileNotFoundException ex) {
			            ex.printStackTrace();
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        } 
				}
			}*/
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
		this.scoreFileName = "C:\\Users\\snbha\\Desktop\\Learning\\Java\\Scores.txt";
	}
	
	public Users()
	{		
		userName = "";
		score = 0;
		scoreFileName = "C:\\Users\\snbha\\Desktop\\Learning\\Java\\Scores.txt";
		try
		{
			File tempFile = new File(scoreFileName);
			tempFile.createNewFile();
		}
		catch(Exception e)
		{
			e.printStackTrace();
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
	
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\snbha\\Desktop\\Learning\\Java\\icon.jpg");  
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
        
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
			        new FileInputStream(scoreFileName));
			
			// get the object
			while(i != 10)
			{
				listUsers[i] = (Users) objectInputStream.readObject();
				if(listUsers[i].userName.compareTo(name) == 0)
					return false;
				i++;
			}
			objectInputStream.close();			
	        
		}
		 catch (FileNotFoundException ex) {
            ex.printStackTrace();
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
            	 exe.printStackTrace();
			}
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return true;
	}
	
	public void updateScore(Users user)
	{
		Users[] players = new Users[10];
		int i = 0, j = 0;
		
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
            e.printStackTrace();
        } catch (IOException e) {
        	; //will be handled below
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        	 exe.printStackTrace();
		}
	}
	
	public int readScoreboard()
	{
		/* At any given point, only details of 10 highest scorers 
		 * will be saved to file
		 */
		Users[] players = new Users[10];
		int i = 0;
		
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
            e.printStackTrace();
        } catch (IOException e) {
        	return 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        } 
	} 	
	
}