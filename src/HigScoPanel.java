//Andy Xu
//Date: 4-23-19
//HigScoPanel.java
//This class holds the High Scores Panel. It will open a file with the high scores
//and print them out in a JTextArea

import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import java.awt.Font;
import java.awt.Color;

public class HigScoPanel extends JPanel
{
	private String hs; //String to hold high scores
	private String hsName; //String to hold name of high scores file
	private JTextArea highSco; //JTextArea that displays high scores
	private File hsFile; //File for High Scores
	private Scanner input; //Input that reads hsFile
	
	public HigScoPanel()
	{
		//Setting color of backgroun
		Color hsBack = new Color(51, 204, 255);
		setBackground(hsBack);
		
		//Initiating JTextArea with high scores after getting them from
		//setHS() method
		hs = new String("High Scores:\n");
		hsName = new String("HigScoFile.txt");
		openHS();
		setHS();
		highSco = new JTextArea(hs, 30, 30);
		highSco.setEditable(false);
		Font hsFont = new Font("Serif", Font.PLAIN, 20);
		highSco.setFont(hsFont);
		add(highSco);
	}
	
	public void openHS() //Opening high scores file
	{
		hsFile = new File(hsName);
		
		try
		{
			input = new Scanner(hsFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Cannot create " + hsName + " to be written "
				+ "to.");
			System.exit(1);
		}
	}
	
	public void setHS()
	{
		//reads lines of higScoFile and adds to String hs to be displayed
		while(input.hasNext())
		{
				hs += input.nextLine() + " wpm" +  "\n";
		}
	}
}
