package XSnake;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import XSnake.Snake.BodyDirection;

public class SnakeGame extends JPanel{
	public static int MapMaxX = 49,MapMaxY = 35;								//地图大小参数
	public final static int MaxSleep = 1000,MinSleep = 20;						//最大最小刷新时间
	int Sleeptime = 80;															//刷新时间，控制游戏速度
	public static boolean NoSideWall = true;									//地图边缘是否可以通过
	public boolean[][] GridBlocked = new boolean[MapMaxX][MapMaxY];				//障碍物标志
	public boolean[][] GridState = new boolean[MapMaxX][MapMaxY];				//是否有物体标志
	public Snake s_self = new Snake(this), snake1;			//两名玩家
	public MapEntity[] entities = new MapEntity[]{new Food(this), new Food(this)};		//食物、道具等等
	public Obstacle[] obstacles;
	public WebInterface neti;
	boolean IsPause;
	public KeyAdapter gamecontrol = new KeyAdapter() {
		public void keyPressed(KeyEvent arg0) {
			switch (arg0.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				s_self.SetDirection(BodyDirection.Left);
				break;
			case KeyEvent.VK_RIGHT:
				s_self.SetDirection(BodyDirection.Right);
				break;
			case KeyEvent.VK_UP:
				s_self.SetDirection(BodyDirection.Up);
				break;
			case KeyEvent.VK_DOWN:
				s_self.SetDirection(BodyDirection.Down);
				break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_P:
            	ChangePauseState();
                break;
            case KeyEvent.VK_ADD:
            case KeyEvent.VK_PAGE_UP:
            	SpeedUp();
                break;
            case KeyEvent.VK_SUBTRACT:
            case KeyEvent.VK_PAGE_DOWN:
            	SpeedDown();
                break;
			}
		}
	};
	
	public SnakeGame()
	{
		MapSets.GenerateMap1(this);
		//super();
		
	}
	public void StartGame()
	{
		new Thread(new Runnable(){
		public void run() {
			while (true) {
				try {
					Thread.sleep(Sleeptime);
					repaint();
					ProcessStep();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}).start();
	}
	public void paint(Graphics g)
	{
		//TODO: 增加背景以后可以去掉super的repaint
		super.paint(g);
		for(MapEntity e: entities)
			e.DrawObject(g);
		if(obstacles!=null)
			for(Obstacle o: obstacles)
				o.DrawObject(g);
		s_self.DrawBody(g);
		if(snake1!=null)
			snake1.DrawBody(g);
	}
	public void ProcessStep()
	{
		if(IsPause)
			return;
		if(s_self.CheckHit())Pause();//TODO: 增加撞墙以后的处理
		else s_self.MoveStep();
		if(snake1!=null)
		if(snake1.CheckHit())Pause();//TODO: 增加撞墙以后的处理
		else snake1.MoveStep();
	}
	public void SetStepTime(int steptime)
	{
		Sleeptime = steptime;
	}
	public synchronized void Pause()
	{
		IsPause = true;
	}
	public synchronized void Continue()
	{
		IsPause = false;
	}
	public synchronized void ChangePauseState()
	{
		IsPause = !IsPause;
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
	public void SetStatusText(WebInterface.ConnectStatus status)
	{
		
	}
}
