//Andy Xu
//Date: 4-23-19
//ScienceTypingBash.java
//This class creates the main JFrame and creates a card layout that holds instances
// of HigScoPanel, GamePanel, and HomePanel.
//It also creates a JMenu with JMenuItems that switch the cards in the card layout.

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class ScienceTypingBash extends JPanel implements ActionListener
{
	private JFrame frame; //Main JFrame 
	private JPanel gcp; //JPanel to hold cardLayout
	private JMenuBar mb; //JMenuBar that holds JMenuItems to navigate through card layout
	//JMenuItems that navigate through instances of HigScoPanel, GamePanel, and HomePanel
	private JMenuItem gs, in, hs; 	
	private CardLayout cl = new CardLayout(); //card layout to switch between panels
	private HomePanel ho; //instance of HomePanel to be shown
	
	public static void main(String[] args)
	{ 
		ScienceTypingBash stb = new ScienceTypingBash();
		stb.run();
	}
	
	public void run()
	{
		frame = new JFrame ("Science Typing Bash");
		frame.setSize( 1000, 700);				
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setLocation(100, 0);
		frame.setResizable(true);
		
		setupWindow();
		
		frame.setVisible(true);	
	}	
	
	public void setupWindow()
	{
		//Instantiating JMenuBar and JMenuItems and adding ActionListener to JMenuItems
		//along with setting colors and font.
		mb = new JMenuBar();
		gs = new JMenuItem("Home");
		in = new JMenuItem("Instructions");
		hs = new JMenuItem("High Scores");
		
		Font jmiFont = new Font("Serif", Font.PLAIN, 25);
		gs.setFont(jmiFont);
		in.setFont(jmiFont);
		hs.setFont(jmiFont);

		gs.setOpaque(true);
		gs.setBackground(Color.CYAN);
		in.setOpaque(true);
		in.setBackground(Color.CYAN);
		hs.setOpaque(true);
		hs.setBackground(Color.CYAN);
		
		gs.addActionListener(this);
		in.addActionListener(this);
		hs.addActionListener(this);

		//Adding JMenuItems to JMenu then adding JMenu to frame
		mb.add(gs);
		mb.add(in);
		mb.add(hs);

		frame.add(mb, BorderLayout.NORTH);
		
		gcp = new JPanel(); //Instantiating Panel that will hold card layout
		gcp.setLayout(cl); //setting card layout to gcp
		//creating instances of HigScoPanel, GamePanel, and HomePanel and adding to card layout
		ho = new HomePanel(); 
		InstructPanel ip = new InstructPanel();
		HigScoPanel hsp = new HigScoPanel();
		gcp.add("Home", ho);
		gcp.add("Instructions", ip);
		gcp.add("High Scores", hsp);
		frame.add(gcp, BorderLayout.CENTER); //adding card layout panel to frame
		cl.show(gcp, "Home"); //setting the first panel user sees to the Home panel
	}

	public void actionPerformed(ActionEvent e)
	{
		//Brings user to defined panel depending on which JMenuItem they press
		if(e.getSource() == gs)
		{
			//Creates new instance in order to start a new one
			HomePanel ho = new HomePanel();
			gcp.add("Home", ho);
			cl.show(gcp, "Home");
		}
		else if(e.getSource() == in)
		{
			cl.show(gcp, "Instructions");
		}
		else if(e.getSource() == hs)
		{
			//Creates new instance in order to update scores
			HigScoPanel hsp = new HigScoPanel();
			gcp.add("High Scores", hsp);
			cl.show(gcp, "High Scores");
		}

	}
	
}

