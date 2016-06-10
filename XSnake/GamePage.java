package XSnake;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GamePage extends JFrame {
	
	SnakeGame game;
	SoundEffect se;
	public GamePage(SnakeGame gamemap, int x, int y)
	{
		game = gamemap;
		game.par = this;
		this.setBounds(x,y,805,610);
		InitComponent();
	}
	
	public void InitComponent()
	{
		se = new SoundEffect("XSnake/loveinbox.wav",true);
		se.start();
		this.add(game);
		this.setTitle("Greedy Snake");
		this.addKeyListener(game);
		game.neti.StartGameThread();
		this.setVisible(true);
	}
}
