package XSnake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import XSnake.Snake.BodyDirection;

public class XSnake_GUI extends JFrame {

	JPanel init;
	
	public XSnake_GUI()
	{
		InitComponent();
	}
	public void InitComponent()
	{
		init = new InitialPage(this);
		//this.setContentPane(init);
		this.setContentPane(new StartingPage(this));
		this.setBounds(200,100,800,600);
		this.pack();
		this.setTitle("Greedy Snake By ÖÓÔªöÎ");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new XSnake_GUI().setVisible(true);
	}

}
