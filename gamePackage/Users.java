package gamePackage;

import java.io.*;
import javax.swing.*;

public class Users implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private int score;
	public transient String scoreFileName;
	public transient String errorFileName;
	
	public static void main(String[] args)
	{
		new OpeningWindow();
	}
	
	public String toString() {
		return "Name:" + userName + "\tScore: " + score ;
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
	
	public void addUser(String name)
	{
		Users newUser = new Users();
        
		try {
			FileOutputStream f = new FileOutputStream(new File(scoreFileName), true);
			ObjectOutputStream o = new ObjectOutputStream(f);
	        newUser.userName = name.toString();
	        newUser.score = 0;
	        o.writeObject(newUser);
	        o.close();
			f.close();
		}
		 catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
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
			FileInputStream fi = new FileInputStream(new File(scoreFileName));
			ObjectInputStream oi = new ObjectInputStream(fi);
            while(i < 11)
            {
	            players[i] = (Users) oi.readObject();
	            i++;
            }
            oi.close();
			fi.close();
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
			FileInputStream fi = new FileInputStream(new File(scoreFileName));
			ObjectInputStream oi = new ObjectInputStream(fi);
			 while(i < 11)
            {
	            players[i] = (Users) oi.readObject();
	            i++;
            }                     
            oi.close();
			fi.close();
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
