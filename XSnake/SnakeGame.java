package XSnake;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import XSnake.Snake.BodyDirection;

public class SnakeGame extends JPanel implements KeyListener{
	public static int MapMaxX = 49,MapMaxY = 35;								//��ͼ��С����
	public final static int MaxSleep = 1000,MinSleep = 20;						//�����Сˢ��ʱ��
	public final static int MaxEntityNum = 16;									//���entity��
	int Sleeptime = 80;															//ˢ��ʱ�䣬������Ϸ�ٶ�
	public static boolean NoSideWall = true;									//��ͼ��Ե�Ƿ����ͨ��
	public boolean[][] GridBlocked = new boolean[MapMaxX][MapMaxY];				//�ϰ����־
	public boolean[][] GridState = new boolean[MapMaxX][MapMaxY];				//�Ƿ��������־
	public Snake s_self = new Snake(this), snake1;								//�������
	public MapEntity[] entities = new MapEntity[]{new Food(this), new Food(this)};		//ʳ����ߵȵȣ����ڴ������ƣ����ΪMaxEntityNum��entity
	public Obstacle[] obstacles;
	public WebInterface neti;
	public int timestamp = 0;

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
        	//��ͣ��Ϸ
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
		//super();
		
	}

	public void paint(Graphics g)
	{
		//TODO: ���ӱ����Ժ����ȥ��super��repaint
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
		timestamp++;
		if(s_self.CheckHit())s_self.Pause();//TODO: ����ײǽ�Ժ�Ĵ���
		else s_self.MoveStep();
		if(snake1!=null)
		if(snake1.CheckHit())snake1.Pause();//TODO: ����ײǽ�Ժ�Ĵ���
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
	 * ��ȡ��Ϸ�ٶ�
	 * @return
	 * һ���߳�˯�ߵ�ʱ��
	 */
	public int GetSpeed()
	{
		return Sleeptime;
	}
	public void SetStatusText(WebInterface.ConnectStatus status)
	{
		
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
