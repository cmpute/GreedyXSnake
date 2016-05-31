package XSnake;

import java.util.*;
import java.util.List;
import java.awt.*;

public class Snake {
	/**
	 * 蛇的身体类
	 */
	public class SnakeBody extends MapObject
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
			//TODO: 增加不同方向的身体画法
		}
		public void DrawHead(Graphics g)
		{
			g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
		}
	}
	/**
	 * 蛇身的方向枚举，用于绘制身体
	 */
	public enum BodyDirection{
		Left,Up,Right,Down
	}
	
	public static final int InitLength = 20;
	List<SnakeBody> body = new ArrayList<SnakeBody>();	//储存身体的List
	SnakeMap map;			//与蛇关联的地图实例
	int score = 0;			//该玩家所获分数
	boolean CanTurnBack = false;	//蛇是否可以掉头
	boolean CrossWall = false;		//存储穿边墙信息
	boolean Collidable = true;      //蛇身是否算障碍物
	SnakeBody nextpart; //预测下一个body出现的位置
	int drawStyle = 0;  //蛇身的样式，用于区别玩家
	int bodytoadd = 0;  //存储长长的部分
	
	public Snake(SnakeMap parentMap)
	{
		map = parentMap;
		body.add(new SnakeBody(20, 20, BodyDirection.Right));
		GenerateNext();
		AddBody(InitLength - 1);
	}
	public Snake(SnakeMap parentMap,int startx,int starty)
	{
		map = parentMap;
		body.add(new SnakeBody(startx, starty, BodyDirection.Right));
		GenerateNext();
		AddBody(InitLength - 1);
	}
	
	/**
	 *  绘制身体
	 *  @param g
	 *  绘画句柄
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
		((Graphics2D)g).drawString("x:"+body.get(0).locx+"y:"+body.get(0).locy, 10, 10); //Debug用
		((Graphics2D)g).drawString(this.score+"", body.get(0).locx*MapObject.AreaSize, body.get(0).locy*MapObject.AreaSize);
	}
	/**
	 * 判断是否撞上
	 */
	public boolean CheckHit()
	{
		if(CrossWall)
		{
			CrossWall = false;
			return true;
		}
		for(MapEntity e : map.entities)
			if(nextpart.IsHit(e))
				e.TakeEffect(this);
		return map.GridBlocked[nextpart.locx][nextpart.locy];
	}
	/**
	 * 移动蛇身
	 */
	public void MoveStep()
	{
		CutTail();
		AddBody();
	}
	/**
	 * 增加长度
	 */
	public synchronized void AddBody()
	{
		if(Collidable) map.GridBlocked[nextpart.locx][nextpart.locy] = true;
		map.GridState[nextpart.locx][nextpart.locy] = true;
		body.add(0, nextpart);
		GenerateNext();
	}
	/**
	 * 增加一定长度，并且不立即增加
	 * @param num
	 * 需要增加的长度
	 */
	public void AddBody(int num)
	{
		bodytoadd = num;
	}
	/**
	 * 从尾部减去一段
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
	 * 获取蛇的身长
	 */
	public int GetLength()
	{
		return body.size();
	}
	/**
	 * 更改蛇前进的方向
	 * @param direction
	 * 前进的方向
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
	}
	
	private void GenerateNext()
	{
		SnakeBody head = body.get(0);
		switch (head.dir) {
		case Left:
			if (head.locx <= 0) {
				if (map.NoSideWall)
					head = new SnakeBody(map.MapMaxX, head.locy, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx - 1, head.locy, head.dir);
			break;
		case Right:
			if (head.locx >= map.MapMaxX - 1) {
				if (map.NoSideWall)
					head = new SnakeBody(-1, head.locy, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx + 1, head.locy, head.dir);
			break;
		case Up:
			if (head.locy <= 0) {
				if (map.NoSideWall)
					head = new SnakeBody(head.locx, map.MapMaxY, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx, head.locy - 1, head.dir);
			break;
		case Down:
			if (head.locy >= map.MapMaxY - 1) {
				if (map.NoSideWall)
					head = new SnakeBody(head.locx, -1, head.dir);
				else
					CrossWall = true;
			}
			nextpart = new SnakeBody(head.locx, head.locy + 1, head.dir);
			break;
		}
	}
}

