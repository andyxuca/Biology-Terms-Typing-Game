//Andy Xu
//Date: 4-23-19
//HomePanel.java
//This class holds the Home Panel (Greeting Screen). It contains both BorderLayout
//and GridLayout, BorderLayout for the big image panel in center and buttons panel
//in south, and GridLayout for the buttons. The buttons also incorporate CardLayout
//and will switch to the defined panel when pressed.

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;


public class HomePanel extends JPanel implements ActionListener
{
	//CardLayout for buttons to switch panels
	private CardLayout cl; 
	private JButton play; //JButton that switches to GamePanel
	private JButton inst; //JButton that switches to InstructPanel
	private JButton hs; //JButton that switches to HigScoPanel
	private JButton tePan; //JButton that switches to TermsPanel
	private JPanel base; //JPanel that holds everything
	private InstructPanel ip; //Instance of InstructPanel
	private HigScoPanel hsp; //Instance of HigScoPanel
	private GamePanel gp; //Instance of GamePanel
	private InfoPanel fp; //Instance of InfoPanel
	private TermsPanel tp; //Instance of panel that holds review terms
	//JLabel that will contain image to allow it to be added to BorderLayout
	private JLabel sciIm; 
	private Color bCol; //Color theme for background of game;

	public HomePanel()
	{
		bCol = new Color(51, 204, 255);
		cl = new CardLayout();
		
		//Initiating sciIm JLabel and adding image onto it
		sciIm = new JLabel();
		sciIm.setHorizontalAlignment(JLabel.CENTER);
		sciIm.setVerticalAlignment(JLabel.CENTER);
		ImageIcon sci = new ImageIcon("Science.jpg");
		sciIm.setIcon(sci);

		//Initiating JButtons, adding ActionListener, setting colors, setting Fonts
		Font jbFont = new Font("Serif", Font.PLAIN, 20);

		play = new JButton("Play");
		play.setOpaque(true);
		play.setBackground(bCol);
		play.setFont(jbFont);
		play.addActionListener(this);
		
		inst = new JButton("Instructions");
		inst.setBackground(bCol);
		inst.setOpaque(true);
		inst.setFont(jbFont);
		inst.addActionListener(this);

		hs = new JButton("High Scores");
		hs.setBackground(bCol);
		hs.setOpaque(true);
		hs.setFont(jbFont);
		hs.addActionListener(this);
		
		tePan = new JButton("All Science Terms");
		tePan.setBackground(bCol);
		tePan.setOpaque(true);
		tePan.setFont(jbFont);
		tePan.addActionListener(this);
		
		//Initiating JPanel, setting color and BorderLayout,
		//and adding JLabel with image and buttons panel
		base = new JPanel();
		base.setBackground(bCol);
		base.setLayout(new BorderLayout());
		base.add(sciIm, BorderLayout.CENTER);
		BasePanel bp = new BasePanel();
		base.add(bp, BorderLayout.SOUTH);

		//Setting cardLayout to entire panel and adding instances
		//of other panels to cardLayout.
		setLayout(cl);
		ip = new InstructPanel();
		hsp = new HigScoPanel();
		//gp = new GamePanel();
		fp = new InfoPanel();
		tp = new TermsPanel();
		add("Info", fp);
		add("Base", base);
		add("Instructions", ip);
		add("High Scores", hsp);
		//add("Game", gp);
		add("Terms", tp);
		cl.show(this, "Base");
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	//ActionListener that switches cards in cardLayout
	//depending on button that is pressed.
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == play)
		{
			cl.show(this, "Info");
		}
		else if(e.getSource() == inst)
		{
			cl.show(this, "Instructions");
		}
		else if(e.getSource() == hs)
		{
			hsp = new HigScoPanel();
			add("High Scores", hsp);
			cl.show(this, "High Scores");
		}
		else if(e.getSource() == tePan)
		{
			cl.show(this, "Terms");
		}
		else
		{
			cl.show(this, "Base");
		}

	}

	//Panel that contains JButtons, GridLayout with 3 rows of 1 
	//button each.
	class BasePanel extends JPanel
	{
		public BasePanel()
		{
			setLayout(new GridLayout(4, 1));
			add(play);
			add(inst);
			add(hs);
			add(tePan);

		}
	}

	//Panel that contains JTextfield that asks for player name
	class InfoPanel extends JPanel implements ActionListener
	{
		private JTextField nameJF; //JTextField for name
		private JButton sub; //JButton to submit name
		private String name; //String to hold inputed name

		public InfoPanel()
		{
			//setting color and instantiating textfield and button
			setBackground(bCol);
			nameJF = new JTextField("Type Your Name", 20);
			Font naFont = new Font("Serif", Font.PLAIN, 25);
			nameJF.setFont(naFont);
			sub = new JButton("Submit");
			sub.addActionListener(this);
			sub.setFont(naFont);
			name = new String("");
			add(nameJF);
			add(sub);
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
		}

		public void actionPerformed(ActionEvent evt)
		{
			//If submit button is pressed, inputed name is stored and written to
			//high scores file and game starts.
			if(evt.getSource() == sub)
			{
				name = nameJF.getText();
				gp = new GamePanel();
				gp.setName(name);
				HomePanel.this.add("Game", gp);
				cl.show(HomePanel.this, "Game");
				//gp.ti.start();
			}
		}
	}	

	//TermsPanel holds all the terms for the player to review and study
	class TermsPanel extends JPanel implements ActionListener
	{
		private JLabel pic; //JLabel that holds picture
		private JTextArea sci; //JTextArea that displays term
		private String term; //term that is displayed
		private String picTerm; //name of image displayed
		private int curTerm; //number of displayed term
		private Scanner spIn; //Scanner to read term
		private JButton nex; //JButton to move to next term
		private JButton prev;//JButton to move to previous term
		private ImageIcon img; //ImageIcon to hold image displayed
		
		public TermsPanel()
		{
			setLayout(new BorderLayout());
			curTerm = 2;
			term = new String("");
			picTerm = new String("");
			getTerm();
			
			//Initializing JTextArea sci with term
			sci = new JTextArea(term);
			Font sciFont = new Font("Serif", Font.PLAIN, 30);
			sci.setFont(sciFont);
			sci.setLineWrap(true);
			sci.setWrapStyleWord(true);
			sci.setBackground(bCol);
			sci.setEditable(false);
			
			//Initializing JButtons next and previous
			nex = new JButton("NEXT");
			prev = new JButton("PREVIOUS");
			nex.addActionListener(this);
			prev.addActionListener(this);
			
			//Initializing JLabel pic with image
			pic = new JLabel();
			pic.setBackground(bCol);
			pic.setOpaque(true);
			pic.setHorizontalAlignment(JLabel.CENTER);
			pic.setVerticalAlignment(JLabel.CENTER);
			img = new ImageIcon(picTerm);
			pic.setIcon(img);
	
			add(sci, BorderLayout.SOUTH);
			add(nex, BorderLayout.EAST);
			add(prev, BorderLayout.WEST);
			add(pic, BorderLayout.CENTER);
		}
		
		//getTerm() opens SciencePhrase.txt and stores the science phrase 
		//at number curTerm in the variables term and picTerm
		public void getTerm()
		{
			//Open SciencePhrases.txt
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

			//Storing term at curTerm in the variables term and picTerm
			String cur = new String("");
			for(int i = 0; i<curTerm; i++)
			{
				term = "";
				while(!cur.equals("-"))
					cur = spIn.nextLine();
				cur = spIn.nextLine();
				picTerm = cur;
				cur = spIn.nextLine();
				while(!cur.equals("-"))
				{
					term += cur;
					cur = spIn.nextLine();
				}
			}	
		}
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
		}
		
		//Adds one to curTerm if next is pressed and subtracts one if
		//prev is pressed. If curTerm reaches the max value and the user clicks
		//next, then it starts back at one again and the same occurs for
		//it if it reaches the min value but it is sent to the max value instead.
		//It repaints the frame after.
		public void actionPerformed(ActionEvent evt)
		{
			if(evt.getSource() == nex)
			{
				if(curTerm == 20)
					curTerm = 1;
				else
					curTerm++;
			}
			else if(evt.getSource() == prev)
			{
				if(curTerm == 1)
					curTerm = 20;
				else
					curTerm--;
			}
			getTerm();
			sci.setText(term);
			img = new ImageIcon(picTerm);
			pic.setIcon(img);
			repaint();
			
		}
		
	}
}

