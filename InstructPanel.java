//Andy Xu
//Date: 4-23-19
//InstructPanel.java
//This class holds the Instructions Panel. It prints out the instructions into a JTextArea.

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InstructPanel extends JPanel
{
	public InstructPanel()
	{
		//Setting color and layout
		Color ipBack = new Color(51, 204, 255);
		setBackground(ipBack);
		setLayout(null);
		
		//Writing and Displaying instructions to JTextArea
		JTextArea instr = new JTextArea("Instructions: \nWelcome to Science Typing Bash!"
			+ " Once you start the game, you will first enter your name in order to keep "
			+ "track of your score. Once you click \"submit\", the game will start. You "
			+ "see your time and typing speed on the top right. You will also see "
			+ "a text box in the middle with the prompt that you have to type in the "
			+ "text box at the bottom. If you type characters correctly, they will be "
			+ "highlighted green. If they are wrong, they will be highlighted pink. "
			+ "Remember that you will not be able to navigate to wrong letters with your "
			+ "mouse so make sure to fix them immediately. When you type everything "
			+ "correctly, you will have the option to try typing again or to finish."
			+ "You can then follow the directions that will be shown to move on. If "
			+ "you would like to review all the science terms, simply press \"Home\" "
			+ "on the top left and and then press the \"All Science Terms\" button on "
			+ "the bottom. Good luck and have fun!", 40, 50);
		instr.setLineWrap(true);
		instr.setWrapStyleWord(true);
		instr.setEditable(false);
		Font instFont = new Font("Serif", Font.PLAIN, 20);
		instr.setFont(instFont);
		instr.setBounds(250, 10, 500, 500);
		add(instr);
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}
