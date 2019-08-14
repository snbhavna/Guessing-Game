package gamePackage;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.*;
import java.awt.GridBagConstraints;  
import java.awt.GridBagLayout;  

public class MainGame{
	
	private JFrame inputFrame;
	private JPanel mainPanel;
	private JPanel inputPanel;
	private JPanel inputPanel2;
	private JLabel l1, l2;
	volatile private boolean input;
	private char readChar;
	private JButton exit, back, playAgain;
	volatile private boolean b1;
	volatile private int b2;
	private String fileName;
	
	public MainGame()
	{
		setValue(false);
		b2 = 0;
		fileName = "pvt\\Names.txt";
	}
	
	public void setValue(boolean flag) {
	    b1 = flag;
	    }
 
	public boolean getValue() {
	    return b1;
	    }
	
	public boolean getB2() {
		if(b2 == 2)
		{
			b2 = 0;
			return true;
		}
		else
			return false;
	}

	public void createForm()
	{				
		String []inputLetters = new String[]{
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		inputFrame = new JFrame();
		inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		inputPanel2 = new JPanel();
		inputPanel2.setLayout(new GridBagLayout());
		JButton[] letters = new JButton[26];
		int i = 0;
		input = false;
		readChar = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		//c.weightx = 1;
		for(int j = 0; j < 26; j++)
		{
			letters[j] = new JButton(inputLetters[i]);
			letters[j].setSize(80, 80);
			letters[j].addActionListener(new inputListener());			
			inputPanel.add(letters[j], c);		
			i++;
			if(i >= 8)
			{
				c.gridy = i/8;
				c.gridx = i%8;
			}
			else
				c.gridx = i;			
		}
	
		l1 = new JLabel("");			
		l2 = new JLabel ("");
		c.gridx = 0;
		c.gridy = 10;
		inputPanel2.add(l1, c);
		c.gridy = 12;
		inputPanel2.add(l2, c);
		
		c.gridx = 0;
		c.gridy = 14;
		exit = new JButton("Exit");
		inputPanel2.add(exit, c);
		exit.addActionListener(new actionToBeTaken());
		c.gridx = 5;
		c.gridy = 14;
		back = new JButton("Back");
		inputPanel2.add(back, c);
		back.addActionListener(new actionToBeTaken());
		c.gridx = 10;
		c.gridy = 14;
		playAgain = new JButton("Play Again");
		playAgain.setEnabled(false);
		inputPanel2.add(playAgain, c);
		playAgain.addActionListener(new actionToBeTaken());
			
		c.fill = GridBagConstraints.VERTICAL;
		Image icon = Toolkit.getDefaultToolkit().getImage("files\\icon.jpg");  		
		
		mainPanel.add(inputPanel);
		mainPanel.add(inputPanel2);
		inputFrame.add(mainPanel);	
		inputFrame.setIconImage(icon);
		inputFrame.setSize(500, 700);
		//inputFrame.pack();
		inputFrame.setLocationRelativeTo(null);
		inputFrame.setResizable(false);			
		inputFrame.setVisible(true);
		inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		inputFrame.setTitle("GUESSING GAME");	
		//inputFrame.setBackground(Color.red);			
	}
	private class actionToBeTaken implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if((e.getSource() == exit) || (e.getSource() == back))
			{
				inputFrame.setVisible(false);
				inputFrame.dispose();
				//b1 = true;
				b2 = 1;
			}
			else if(e.getSource() == playAgain)
			{
				inputFrame.setVisible(false);
				inputFrame.dispose();
				//OpeningWindow.startGame = true; 
				b2 = 2;
			}
		}
	}
	private class inputListener implements ActionListener
	{		
		public void actionPerformed(ActionEvent e)
	    {  
			ReentrantLock lock = new ReentrantLock();
			//What to do when button is pressed 	
			lock.lock();
			input = true;
			Object o = e.getSource();
			JButton b = null;
			if(o instanceof JButton)
			     b = (JButton)o;
		    if(b != null)
		    {
			   readChar = b.getText().charAt(0);
			   b.setText("");
			   Users.logger.info("2:" + readChar);
		    }
			lock.unlock();
	    }
	}
	public void startGame()
	{
		Random rand = new Random();
		boolean success = false;
		boolean validLetter = false;
		int index = 0, correctLet = 0, j = 0;				
		int numStrings = 0;
		String[] list = new String[100];
		File file = new File(fileName);
		try
		{
		Users.logger.info("User:" + OpeningWindow.currentUser); 		 
		BufferedReader buf = new BufferedReader(new FileReader(file)); 
		while ((list[numStrings] = buf.readLine()) != null) 
			numStrings++ ;
		buf.close();
		int n = rand.nextInt(numStrings);
		Users.logger.info("Str:" + list[n]);
		char answer[] = new char[2* list[n].length()];
		j = 0;
		for(int i = 0; i < list[n].length(); i++)
		{
			answer[j++] = '_';
			answer[j++] = ' ';
		}
		l1.setText(String.valueOf(answer));			
		l2.setText ("Guess a letter!");
		
		while((!success) && (b2 == 0))
		{			
			if(input == true)
			{
				index = 0;
				Users.logger.info("1:" + readChar + "," + String.valueOf(answer)+","+index+","+list[n]);
				input = false;
				validLetter = false;
				while(index != list[n].length())
				{
					if(((list[n].charAt(index) == ( Character.toLowerCase(readChar))) ||
						(list[n].charAt(index) == ( Character.toUpperCase(readChar)))) &&
							(answer[2*index] == '_'))
					{
							answer[2*index] = readChar;
							correctLet++;
							validLetter = true;							
					}		
					index++;
				}	
				//System.out.print(String.valueOf(answer));
				l1.setText(String.valueOf(answer));
				if(correctLet == list[n].length())
				{
					success = true;
					Users tempObj = new Users(false);
					tempObj.updateScore(OpeningWindow.currentUser);
					l2.setText("Yeah!! Right Answer!");
					playAgain.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Score updated", "Score", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					if(validLetter == true)
						l2.setText ("Not yet over! Guess another letter!");
					else
						l2.setText ("Nope! Not that one! Guess another letter!");
				}
			}
		}
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Something is Wrong. Input File missing", "Input File Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e)
		{
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
		while(true)
		{
			if(b2 != 0)
				break;
		}
		if(b2 == 1)
		{
			setValue(true);
			b2 = 0;
		}		
	}
}
