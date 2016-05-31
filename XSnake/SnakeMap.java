package XSnake;
import java.awt.*;
import javax.swing.*;

public class SnakeMap extends JPanel{
	public final int MapMaxX = 49,MapMaxY = 35;				//��ͼ��С����
	public final static int MaxSleep = 1000,MinSleep = 20;			//�����Сˢ��ʱ��
	int Sleeptime = 80;												//ˢ��ʱ�䣬������Ϸ�ٶ�
	public boolean NoSideWall = false;								//��ͼ��Ե�Ƿ����ͨ��
	public boolean[][] GridBlocked = new boolean[MapMaxX][MapMaxY];	//�ϰ����־
	public boolean[][] GridState = new boolean[MapMaxX][MapMaxY];	//�Ƿ��������־
	public Snake P1 = new Snake(this), P2;							//�������
	public MapEntity[] entities = new MapEntity[]{new Food(this), new Food(this)};		//ʳ����ߵȵ�
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
		//TODO: ���ӱ����Ժ����ȥ��super��repaint
		super.paint(g);
		for(MapEntity e: entities)
			e.DrawObject(g);
		P1.DrawBody(g);
	}
	public void ProcessStep()
	{
		if(IsPause)
			return;
		if(P1.CheckHit())Pause();//TODO: ����ײǽ�Ժ�Ĵ���
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
	 * ��ȡ��Ϸ�ٶ�
	 * @return
	 * һ���߳�˯�ߵ�ʱ��
	 */
	public int GetSpeed()
	{
		return Sleeptime;
	}
}
