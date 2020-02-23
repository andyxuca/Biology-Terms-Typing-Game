//Andy Xu
//Date: 4-25-19
//GamePanel.java
//This class holds the GamePanel. It is where the actual game happens. It displays
//the prompt the player has to type, an image, the time it takes the user to type
//a textArea for the user to type in, and a submit button.

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class GamePanel extends JPanel implements ActionListener
{
	private Timer ti; //Timer to count time it takes to type
	//JTextAreas that hold what needs to be typed and what the user types
	private JTextArea prompt, input;
	private Scanner in; //Scanner to read in high scores
	private JLabel imageHold; //JLabel that holds image corresponding to term
	private String ans; //String that holds generated prompt
	private String ansImg; //String that holds name of generated image
	private Scanner spIn; //Scanner to read from SciencePhrases.txt
	private PrintWriter pw; //PrintWriter that prints new high scores
	//Booleans to check if either contine or end buttons were pressed
	private boolean cont, endP; 
	private JButton next; //JButton to continue playing
	private JButton end; //JButton to stop playing
	private int speed; //Int variable to hold current speed of typing
	private int endSpeed; //Int variable to hold fastest overall typing speed
	private String name; //String to hold player name
	private Color gbCol; //Color theme for background of game;
	

	public GamePanel()
	{
		gbCol = new Color(51, 204, 255);
		cont = true;
		endP = false;
		endSpeed = 0;
		setupGP();
	}
	
	//Setter to get name from HomePanel class
	public void setName(String name)
	{
		this.name = name;
	}

	//setupGP() sets up the GamePanel depending on the cont and endP booleans.
	//It sets up the image panel, input panel, and timer and incorporates them to 
	//to the GamePanel.
	public void setupGP()
	{
		setBackground(gbCol);
		repaint();
		removeAll();
		
		//The normal game page is displayed in the beginning or after the user
		//presses the continue button.
		if(cont && !endP)
		{
			setLayout(new GridLayout(3, 1));
			ans = new String("");
			ansImg = new String("");
			genPhrase();
			ImageTerm it = new ImageTerm(); //Instance of panel that holds image and time
			ti.start();
			add(it);
			//Setting up and adding prompt JTextArea with color and border
			Font prFont = new Font("Serif", Font.PLAIN, 30);
			prompt = new JTextArea(ans);
			Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
			prompt.setBorder(border);
			prompt.setBackground(gbCol);
			prompt.setFont(prFont);
			prompt.setLineWrap(true);
			prompt.setWrapStyleWord(true);
			prompt.setEditable(false);
			add(prompt);

			//Instance of input panel with input JTextArea and submit button
			InputPanel ip = new InputPanel();
			add(ip);
		}
		else if(!cont && !endP)
		{
			//If user finished typing and got it correct, they are brought to page with
			//correct and end buttons to have them decide what to do next.
			
			//JTextArea that tells user their speed and explains continue and end buttons
			JTextArea cor = new JTextArea("CORRECT! Your speed was: " + speed + " words "
					+ " per minute. Press \"CONTINUE\" to type again or press \"END\" "
					+ "to see if your speed is a high score.");
			Font corFont = new Font("Serif", Font.PLAIN, 30);
			cor.setLineWrap(true);
			cor.setWrapStyleWord(true);
			cor.setFont(corFont);
			add(cor);
			
			//Continue and End buttons
			next = new JButton("CONTINUE");
			end = new JButton("END");
			next.addActionListener(this);
			end.addActionListener(this);
			add(next);
			add(end);
		}
		else
		{
			//If user presses end button, they will be brought to this page with JTextArea
			//that tells them their highest speed and tells them what they can do next.
			JTextArea en = new JTextArea("Congrats! Your high speed was: " + endSpeed + " words "
					+ " per minute. Please check the high scores to see if you beat any. If "
					+ "you would like to play again or review the terms, return to the home page. "
					+ "Thank you for playing!");
			Font enFont = new Font("Serif", Font.PLAIN, 30);
			en.setLineWrap(true);
			en.setWrapStyleWord(true);
			en.setFont(enFont);
			add(en);
		}

	}

	//Here if needed (paintComponent and actionPerformed)
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getSource() == next)
			cont = true;
		else if(evt.getSource() == end)
		{
			cont = false;
			endP = true;
			sortHS();
		}
		setupGP();
		validate();

	}

	//sortHS() opens HigScoFile.txt, gets all the previous high scores and
	//sorts in the new score from the user. It will them print up to a maximum
	//of ten high scores back onto HigScoFile.txt.
	public void sortHS()
	{
		//Opening HigScoFile.txt for Scanner
		File hsFile = new File("HigScoFile.txt");

		try
		{
			in = new Scanner(hsFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Cannot create HigScoFile.txt to be written "
					+ "to.");
			System.exit(1);
		}
		
		//Storing previous high scores
		String[] names = new String[11]; //array for names
		int[] score = new int[11]; //array for scores
		int amo = 0; //current position of array
		
		for(int i = 0; i<11; i++)
			score[i] = 0; //setting all scores to 0 in case there are not 
			//enough high scores to fill array yet.
		
		//adding in the current player's score to the last position of the arrays
		names[10] = name; 
		score[10] = endSpeed;
		
		while(in.hasNext())
		{
			String curName = new String("");
			String nVal = new String("");
			boolean con = true;

			//To separate names and scores, first reads until gets to a number.
			//It stores the first part in the name array and the number in the
			//scores array.
			while(con)
			{
				nVal = in.next();
				boolean ch = check(nVal);
				if(!ch)
					curName += nVal;
				else
					con = false;
			}
			names[amo] = curName;
			score[amo] = Integer.parseInt(nVal);
			amo++;
		}
		
		//Sorting scores array from least to greatest and 
		//changing names array correspondingly.
        for (int i = 0; i < 10; i++)  
        { 
            for (int j = 0; j < 10 - i; j++)  
            { 
                if (score[j] > score[j + 1])  
                { 
                    int temp = score[j]; 
                    score[j] = score[j + 1]; 
                    score[j + 1] = temp; 
                    
                    String temp2 = names[j]; 
                    names[j] = names[j + 1]; 
                    names[j + 1] = temp2; 
                } 
            } 
        }
        
        //Opens HigScoFile.txt again for PrintWriter 
        try
		{
			pw = new PrintWriter(hsFile);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Cannot create HigScoFile.txt to be written "
					+ "to.");
			System.exit(1);
		}
        
        //Printing first ten high scores to HigScoFile.txt
        for(int i = 10; i>0; i--)
        {
        	if(score[i] != 0)
        	{
        		pw.println(names[i] + " " + score[i]);
        	}
        }
        pw.close();
	}
	
	//check() checks if string is number(returns true), otherwise it returns false.
	public boolean check(String str)
	{
		try 
		{  
		    Double.parseDouble(str);  
		    return true;
		 }
		catch(NumberFormatException e)
		{  
		    return false;  
		 }  
	}
	
	//genPhrase() opens SciencePhrases.txt, generates a random number, and goes to the phrase of 
	//that number. It stores the phrase in the variables ans and ansImg(the image) which are used in 
	//other methods and panels.
	public void genPhrase()
	{
		//Open SciencePhrases.txt for Scanner
		File f1 = new File("SciencePhrases.txt");

		try
		{
			spIn = new Scanner(f1);
		}
		catch(FileNotFoundException e)
		{
			System.err.println("Cannot create SciencePhrases.txt to be written "
					+ "to.");
			System.exit(1);
		}

		//Generate random number between 1 and 20
		int numGen = (int)(Math.random()*20+1);
		
		//Reads from file and get phrase at numGen
		String cur = new String("");
		for(int i = 0; i<numGen; i++)
		{
			ans = "Type: ";
			//checking for the "-" which separate each term
			while(!cur.equals("-")) 
				cur = spIn.nextLine();
			cur = spIn.nextLine();
			ansImg = cur;
			cur = spIn.nextLine();
			while(!cur.equals("-"))
			{
				ans += cur;
				cur = spIn.nextLine();
			}
		}
	}

	//Panel that generates random image and phrase from Science.txt,
	//displays image, and also displays time by using a timer.
	class ImageTerm extends JPanel implements ActionListener
	{
		int cur; //Int to hold current time
		JTextArea timeTA; //JTextArea to display time
		
		public ImageTerm()
		{
			setLayout(new GridLayout(1, 2));

			//Starting time at 0 and initializing timer
			cur = 0;
			ti = new Timer(1000, this);

			//Initializing imageHold and adding image
			imageHold = new JLabel();
			imageHold.setBackground(gbCol);
			imageHold.setOpaque(true);
			
			imageHold.setHorizontalAlignment(JLabel.CENTER);
			imageHold.setVerticalAlignment(JLabel.CENTER);
			ImageIcon dia = new ImageIcon(ansImg);
			imageHold.setIcon(dia);
			add(imageHold);

			//Initializing timeTA and displaying time
			Font proFont = new Font("Serif", Font.PLAIN, 50);
			timeTA = new JTextArea("Time: 0 seconds");
			timeTA.setBackground(gbCol);
			timeTA.setLineWrap(true);
			timeTA.setFont(proFont);
			timeTA.setEditable(false);
			add(timeTA);
			setVisible(true);

		}

		public void actionPerformed(ActionEvent evt)
		{
			//Adds one to cur every second and changes timeTA to display
			//accordingly. Also calculate speed of typing by finding out amount of
			//words and dividing it by time then multiplying by 60 to get words
			//per minute.
			cur++;
			speed = (int)(((double)(input.getText().split("\\s").length-1)/cur)*60);
			timeTA.setText(" Time: " + cur + " seconds\n Speed: "+ speed +" wpm");
		}
	}

	//Panel that holds JTextArea for input and submit button to check input
	class InputPanel extends JPanel implements ActionListener
	{
		//Timer that checks input 10 times a second for mistakes
		Timer sub; 
		
		public InputPanel()
		{
			setLayout(new BorderLayout());
			
			//Initializing and starting timer
			sub = new Timer(100, this);
			sub.start();
			
			//Setting up and adding input TextArea with border and color
			input = new JTextArea("Type: ");
			Font inFont = new Font("Serif", Font.PLAIN, 20);
			Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
			input.setBorder(border);
			input.setFont(inFont);
			input.setBackground(gbCol);
			input.setOpaque(true);
			input.setLineWrap(true);
			input.setWrapStyleWord(true);
			input.setTransferHandler(null);
			add(input, BorderLayout.CENTER);
			setVisible(true);

		}

		public void actionPerformed(ActionEvent evt)
		{
			//If submit is pressed, prints correct if input matches
			//prompt and stops timer. Otherwise, wrong is printed and
			//the timer continues
			String inText = input.getText();
			input.setText(inText);
			prompt.setText(ans);
			
			//If the input text is all correct and finished, the timer is stopped
			//and the user is brought to the next page.
			if(inText.equals(ans))
			{
				sub.stop();
				ti.stop();
				cont = false;
				endSpeed = Math.max(endSpeed, speed);
				GamePanel.this.setupGP();
				validate();
			}
			//If the input is not all correct, all the correct parts are highlighted
			//in green and the wrong parts highlighted in pink.
			else
			{

				for(int i = 0; i<ans.length(); i++)//looping through whole String
				{
					if(i<inText.length())
					{
						if(ans.charAt(i) != inText.charAt(i)) //Highlight pink if not matching
						{
							Highlighter highlighter = input.getHighlighter();
							Highlighter pHigh = prompt.getHighlighter();
							HighlightPainter painter  
								= new DefaultHighlighter.DefaultHighlightPainter(Color.pink);

							try 
							{
								highlighter.addHighlight(i, i+1, painter );
								pHigh.addHighlight(i, i+1, painter);
							} 
							catch (BadLocationException e) 
							{
								e.printStackTrace();
							}
						}
						else //highlight green if matching
						{
							Highlighter pHigh = prompt.getHighlighter();
							HighlightPainter prPaint 
								= new DefaultHighlighter.DefaultHighlightPainter(Color.green);
							try 
							{
								pHigh.addHighlight(i, i+1, prPaint );
							} 
							catch (BadLocationException e)
							{
								e.printStackTrace();
							}
						}
					}

				}
			}
			validate(); //validating panel to update
		}
	}
}
