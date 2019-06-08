package gamePackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class MainGame {
	
	public void startGame(String fileName)
	{
		Random rand = new Random();
		boolean success = false;
		String temp;
		char readChar = 0;
		int index = 0, correctLet = 0;				
		int numStrings = 0;
		String[] list = new String[100];
		File file = new File(fileName);
		
		try
		{
		BufferedReader buf = new BufferedReader(new FileReader(file)); 
		while ((list[numStrings] = buf.readLine()) != null) 
			numStrings++ ;
		
		int n = rand.nextInt(numStrings);
		char answer[] = new char[list[n].length()];
		Scanner s = new Scanner(System.in);
		System.out.println("Welcome to Guess the Word!");
		for(int i = 0; i < list[n].length(); i++)
		{
			answer[i] = '_';
			System.out.print("_ ");
		}
		
		while(!success)
		{
			System.out.println();
			System.out.print("Guess letter : ");
			index = 0;
			temp = s.nextLine();
			readChar = temp.charAt(0);
			while(index != list[n].length())
			{
				if(((list[n].charAt(index) == ( Character.toLowerCase(readChar))) ||
					(list[n].charAt(index) == ( Character.toUpperCase(readChar)))) &&
						(answer[index] == '_'))
				{
						answer[index] = readChar;
						correctLet++;
				}				
				index++;
			}	
			for(int i = 0; i < list[n].length(); i++)
				System.out.print(answer[i] + " ");	
			if(correctLet == list[n].length())
			{
				success = true;
				System.out.println("Yeah!! Right Answer!");
			}
		}
		}
		catch(FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "Input File missing", "Input File Error", JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
