package XSnake;

import java.util.*;
import java.util.List;
import java.io.IOException;
import java.io.Serializable;
import java.awt.*;

public class Snake implements Serializable{

	/**
	 * �ߵ�������
	 */
	public class SnakeBody extends MapObject implements Serializable
	{
		BodyDirection dir;
		public SnakeBody(int x, int y, BodyDirection direction)
		{
			locx = x;
			locy = y;
			dir = direction;
		}
		
		@Override
		public void DrawObject(Graphics g)
		{
			g.drawRect(locx*AreaSize, locy*AreaSize, AreaSize, AreaSize);
			//TODO: ���Ӳ�ͬ��������廭��
		}
		public void DrawHead(Graphics g)
		{
			g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
		}
	}
	/**
	 * ����ķ���ö�٣����ڻ�������
	 */
	public enum BodyDirection{
		Left,Up,Right,Down;
		
		public static BodyDirection valueOf(int val)
		{
			switch(val)
			{
			case 0:
				return BodyDirection.Left;
			case 1:
				return BodyDirection.Up;
			case 2:
				return BodyDirection.Right;
			case 3:
				return BodyDirection.Down;
			default:
				return null;
			}
		}
	}
	
	public static final int InitLength = 20;
	List<SnakeBody> body = new ArrayList<SnakeBody>();	//���������List
	SnakeGame map;			//���߹����ĵ�ͼʵ��
	int score = 0;			//������������
	public static boolean CanTurnBack = true;	//���Ƿ���Ե�ͷ
	public static boolean Collidable = false;      //�����Ƿ����ϰ���
	boolean CrossWall = false;		//�洢����ǽ��Ϣ
	SnakeBody nextpart; //Ԥ����һ��body���ֵ�λ��
	int drawStyle = 0;  //�������ʽ�������������
	int bodytoadd = 0;  //�洢�����Ĳ���
	
	public Snake(SnakeGame parentMap)
	{
		map = parentMap;
		body.add(new SnakeBody(20, 20, BodyDirection.Right));
		GenerateNext();
		AddBody(InitLength - 1);
	}
	public Snake(SnakeGame parentMap,int startx,int starty)
	{
		map = parentMap;
		body.add(new SnakeBody(startx, starty, BodyDirection.Right));
		GenerateNext();
		AddBody(InitLength - 1);
	}
	
	/**
	 *  ��������
	 *  @param g
	 *  �滭���
	 */
	public void DrawBody(Graphics g)
	{
		if(drawStyle>0)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.BLUE);
		for (int i = body.size()-1; i > 0 ; i--) {
			body.get(i).DrawObject(g);
		}
		body.get(0).DrawHead(g);
		((Graphics2D)g).drawString("x:"+body.get(0).locx+"y:"+body.get(0).locy, 10, 10); //Debug��
		((Graphics2D)g).drawString(this.score+"", body.get(0).locx*MapObject.AreaSize, body.get(0).locy*MapObject.AreaSize);
	}
	/**
	 * �ж��Ƿ�ײ��
	 */
	public boolean CheckHit()
	{
		if(CrossWall)
		{
			CrossWall = false;
			return true;
		}
		//synchronized(map.entities){
		for(int i = 0;i<map.entities.length;i++)
			if(nextpart.IsHit(map.entities[i]))
			{
				map.entities[i].TakeEffect(this);
				map.neti.SyncEntity(i);
			}
		//}
		return map.GridBlocked[nextpart.locx][nextpart.locy];
	}
	/**
	 * �ƶ�����
	 */
	public void MoveStep()
	{
		if(isNmoving) return;
		CutTail();
		AddBody();
	}
	/**
	 * ���ӳ���
	 */
	public synchronized void AddBody()
	{
		if(Collidable) map.GridBlocked[nextpart.locx][nextpart.locy] = true;
		map.GridState[nextpart.locx][nextpart.locy] = true;
		body.add(0, nextpart);
		GenerateNext();
	}
	/**
	 * ����һ�����ȣ����Ҳ���������
	 * @param num
	 * ��Ҫ���ӵĳ���
	 */
	public void AddBody(int num)
	{
		bodytoadd = num;
	}
	/**
	 * ��β����ȥһ��
	 */
	public synchronized void CutTail()
	{
		if(bodytoadd>=0)
		{
			bodytoadd--;
			return;
		}
		SnakeBody tail = body.get(body.size() - 1);
		map.GridState[tail.locx][tail.locy] = false;
		if(Collidable) map.GridBlocked[tail.locx][tail.locy] = false;
		body.remove(body.size() - 1);
	}
	
	/**
	 * ��ȡ�ߵ���
	 */
	public int GetLength()
	{
		return body.size();
	}
	/**
	 * ������ǰ���ķ���
	 * @param direction
	 * ǰ���ķ���
	 */
	public void SetDirection(BodyDirection direction)
	{
		if(!CanTurnBack)
		{
			if((direction.ordinal()%2)==(body.get(0).dir.ordinal()%2))
				return;
			if(Math.abs(direction.ordinal()-body.get(1).dir.ordinal())==2)
				return;
		}
		body.get(0).dir = direction;
		GenerateNext();
		//TODO: Socket���ͷ�����Ϣ
	}
	
	private void GenerateNext()
	{
		SnakeBody head = body.get(0);
		CrossWall = false;
		switch (head.dir) {
		case Left:
			if (head.locx <= 0) {
				if (SnakeGame.NoSideWall)
					head = new SnakeBody(SnakeGame.MapMaxX, head.locy, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx - 1, head.locy, head.dir);
			break;
		case Right:
			if (head.locx >= SnakeGame.MapMaxX - 1) {
				if (SnakeGame.NoSideWall)
					head = new SnakeBody(-1, head.locy, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx + 1, head.locy, head.dir);
			break;
		case Up:
			if (head.locy <= 0) {
				if (SnakeGame.NoSideWall)
					head = new SnakeBody(head.locx, SnakeGame.MapMaxY, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx, head.locy - 1, head.dir);
			break;
		case Down:
			if (head.locy >= SnakeGame.MapMaxY - 1) {
				if (SnakeGame.NoSideWall)
					head = new SnakeBody(head.locx, -1, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx, head.locy + 1, head.dir);
			break;
		}
	}
	
	boolean isNmoving = false;
	public synchronized void Pause()
	{
		isNmoving = true;
	}
	public synchronized void Continue()
	{
		isNmoving = false;
	}
	public synchronized void ChangePauseState()
	{
		isNmoving = !isNmoving;
	}
}

