package XSnake;
import java.awt.*;
import javax.swing.*;

public class SnakeMap extends JPanel{
	public final int MapMaxX = 49,MapMaxY = 35;				//地图大小参数
	public final static int MaxSleep = 1000,MinSleep = 20;			//最大最小刷新时间
	int Sleeptime = 80;												//刷新时间，控制游戏速度
	public boolean NoSideWall = false;								//地图边缘是否可以通过
	public boolean[][] GridBlocked = new boolean[MapMaxX][MapMaxY];	//障碍物标志
	public boolean[][] GridState = new boolean[MapMaxX][MapMaxY];	//是否有物体标志
	public Snake P1 = new Snake(this), P2;							//两名玩家
	public MapEntity[] entities = new MapEntity[]{new Food(this), new Food(this)};		//食物、道具等等
	boolean IsPause;
	
	public SnakeMap()
	{
		//super();
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
		P1.DrawBody(g);
	}
	public void ProcessStep()
	{
		if(IsPause)
			return;
		if(P1.CheckHit())Pause();//TODO: 增加撞墙以后的处理
		else P1.MoveStep();
		//P2.MoveStep();
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
}
