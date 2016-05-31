package XSnake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import XSnake.Snake.BodyDirection;

public class XSnake_GUI extends JFrame {

	SnakeMap gamemap;
	
	public XSnake_GUI()
	{
		InitComponent();
	}
	public void InitComponent()
	{
		gamemap = new SnakeMap();
		this.add(gamemap);
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				switch (arg0.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					gamemap.P1.SetDirection(BodyDirection.Left);
					break;
				case KeyEvent.VK_RIGHT:
					gamemap.P1.SetDirection(BodyDirection.Right);
					break;
				case KeyEvent.VK_UP:
					gamemap.P1.SetDirection(BodyDirection.Up);
					break;
				case KeyEvent.VK_DOWN:
					gamemap.P1.SetDirection(BodyDirection.Down);
					break;
	            case KeyEvent.VK_SPACE:
	            case KeyEvent.VK_P:
	                gamemap.ChangePauseState();
	                break;
	            case KeyEvent.VK_ADD:
	            case KeyEvent.VK_PAGE_UP:
	             	gamemap.SpeedUp();
	                break;
	            case KeyEvent.VK_SUBTRACT:
	            case KeyEvent.VK_PAGE_DOWN:
	               	gamemap.SpeedDown();
	                break;
				}
			}
		});
		this.setBounds(100, 100, 800, 600);
		this.setTitle("Greedy Snake By ÖÓÔªöÎ");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new XSnake_GUI().setVisible(true);
	}

}
