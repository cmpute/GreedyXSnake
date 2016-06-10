package XSnake;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import XSnake.Snake.BodyDirection;

public class SnakeGame extends JPanel implements KeyListener{
	public static int MapMaxX = 49,MapMaxY = 35;								//地图大小参数
	public final static int MaxSleep = 1000,MinSleep = 20;						//最大最小刷新时间
	public final static int MaxEntityNum = 16;									//最多entity数
	int Sleeptime = 80;															//刷新时间，控制游戏速度
	public static boolean NoSideWall = true;									//地图边缘是否可以通过
	public static boolean HitToDeath = false;									//撞上障碍物以后是暂停还是死亡
	public boolean[][] GridBlocked = new boolean[MapMaxX][MapMaxY];				//障碍物标志
	public boolean[][] GridState = new boolean[MapMaxX][MapMaxY];				//是否有物体标志
	public Snake s_self = new Snake(this), snake1;								//两名玩家
	public MapEntity[] entities = new MapEntity[]{new Food(this), new Food(this)};		//食物、道具等等，由于传输限制，最大为MaxEntityNum个entity
	public Obstacle[] obstacles;
	public WebInterface neti;
	public int timestamp = 0;
	public Image background;

	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			s_self.SetDirection(BodyDirection.Left);
			neti.SendDirection();
			break;
		case KeyEvent.VK_RIGHT:
			s_self.SetDirection(BodyDirection.Right);
			neti.SendDirection();
			break;
		case KeyEvent.VK_UP:
			s_self.SetDirection(BodyDirection.Up);
			neti.SendDirection();
			break;
		case KeyEvent.VK_DOWN:
			s_self.SetDirection(BodyDirection.Down);
			neti.SendDirection();
			break;
        case KeyEvent.VK_SPACE:
        	s_self.ChangePauseState();
        	neti.PlayerPause();
            break;
        case KeyEvent.VK_P:
        	neti.ChangeGameState();
        	break;
        case KeyEvent.VK_ADD:
        case KeyEvent.VK_PAGE_UP:
        	neti.SpeedUp();
        	SpeedUp();
            break;
        case KeyEvent.VK_SUBTRACT:
        case KeyEvent.VK_PAGE_DOWN:
        	neti.SpeedDown();
        	SpeedDown();
            break;
		}
	}
	
	public SnakeGame()
	{
		MapSets.GenerateMap1(this);
		background = PaintBackground();
		//super();
		
	}

	public Image PaintBackground()
	{
		BufferedImage bg = new BufferedImage((MapMaxX+1) * MapObject.AreaSize, (MapMaxY+1) * MapObject.AreaSize, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bg.createGraphics();
				try {
					g.drawImage(ImageIO.read(new FileInputStream("XSnake/grass.jpg")), 0 , 0, bg.getWidth(), bg.getHeight()  , this);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		bg.flush();
		return bg;
	}
	
	public void paint(Graphics g)
	{
		//super.paint(g);
		g.drawImage(background, 0, 0, this);
		for(MapEntity e: entities)
			e.DrawObject(g);
		if(obstacles!=null)
			for(Obstacle o: obstacles)
				o.DrawObject(g);
		if(snake1!=null)
			snake1.DrawBody(g);
		s_self.DrawBody(g);
	}
	public void ProcessStep()
	{
		timestamp++;
		if(s_self.CheckHit())
			if(HitToDeath)
				;//TODO: 增加死亡以后的处理
			else
				s_self.Pause();
		else s_self.MoveStep();
		if(snake1!=null)
		if(snake1.CheckHit())
			if(HitToDeath)
				;//TODO: 增加死亡以后的处理
			else
				snake1.Pause();
		else snake1.MoveStep();
	}
	public void SetStepTime(int steptime)
	{
		Sleeptime = steptime;
	}
	public synchronized void SpeedUp()
	{
		if(Sleeptime>MinSleep)
			Sleeptime -= 10;
	}
	public synchronized void SpeedDown()
	{
		if(Sleeptime<MaxSleep)
			Sleeptime += 10;
	}
	/**
	 * 获取游戏速度
	 * @return
	 * 一次线程睡眠的时间
	 */
	public int GetSpeed()
	{
		return Sleeptime;
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
