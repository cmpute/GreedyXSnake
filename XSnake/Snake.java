package XSnake;

import java.util.*;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.*;

public class Snake {

	/**
	 * 蛇的身体类
	 */
	public class SnakeBody extends MapObject{
		BodyDirection dir;
		public SnakeBody(int x, int y, BodyDirection direction) {
			locx = x;
			locy = y;
			dir = direction;
		}

		 @ Override
		public void DrawObject(Graphics g) {
			//g.drawRect(locx*AreaSize, locy*AreaSize, AreaSize, AreaSize);
			DrawObject(g, dir);
		}
		public void DrawObject(Graphics g, BodyDirection lastdir) {
			if ((dir.ordinal() - lastdir.ordinal()) % 2 == 0) // 同向
				switch (dir) {
				case Left:
				case Right:
					SnakeBodyPatterns.horizental.DrawTo(g, locx * AreaSize, locy * AreaSize);
					break;
				case Up:
				case Down:
					SnakeBodyPatterns.vertical.DrawTo(g, locx * AreaSize, locy * AreaSize);
					break;
				default:
					break;
				}
			else //转弯
				switch (dir.ordinal() * 4 + lastdir.ordinal()) {
				case 3:
				case 6:
					SnakeBodyPatterns.UL.DrawTo(g, locx * AreaSize, locy * AreaSize);
					break;
				case 1:
				case 14:
					SnakeBodyPatterns.DL.DrawTo(g, locx * AreaSize, locy * AreaSize);
					break;
				case 4:
				case 11:
					SnakeBodyPatterns.UR.DrawTo(g, locx * AreaSize, locy * AreaSize);
					break;
				case 9:
				case 12:
					SnakeBodyPatterns.DR.DrawTo(g, locx * AreaSize, locy * AreaSize);
					break;
				}
		}
		public void DrawHead(Graphics g) {
			//g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
			switch (dir) {
			case Left:
				SnakeBodyPatterns.headLeft.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			case Right:
				SnakeBodyPatterns.headRight.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			case Up:
				SnakeBodyPatterns.headUp.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			case Down:
				SnakeBodyPatterns.headDown.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			default:
				break;
			}
		}
		public void DrawTail(Graphics g) {
			//g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
			switch (dir) {
			case Left:
				SnakeBodyPatterns.tailLeft.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			case Right:
				SnakeBodyPatterns.tailRight.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			case Up:
				SnakeBodyPatterns.tailUp.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			case Down:
				SnakeBodyPatterns.tailDown.DrawTo(g, locx * AreaSize, locy * AreaSize);
				break;
			default:
				break;
			}
		}
	}
	public static class SnakeBodyPatterns {
		public static OffsetImage horizental;
		public static OffsetImage vertical;
		public static OffsetImage UR;
		public static OffsetImage UL;
		public static OffsetImage DR;
		public static OffsetImage DL;
		public static OffsetImage headUp;
		public static OffsetImage headDown;
		public static OffsetImage headLeft;
		public static OffsetImage headRight;
		public static OffsetImage tailUp;
		public static OffsetImage tailDown;
		public static OffsetImage tailLeft;
		public static OffsetImage tailRight;
		static {
			try {
				SnakeBodyPatterns.horizental = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "horizental.png");
				SnakeBodyPatterns.vertical = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "vertical.png");
				SnakeBodyPatterns.UR = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "ur.png");
				SnakeBodyPatterns.UL = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "ul.png");
				SnakeBodyPatterns.DR = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "dr.png");
				SnakeBodyPatterns.DL = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "dl.png");
				SnakeBodyPatterns.tailUp = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "t_up.png");
				SnakeBodyPatterns.tailDown = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "t_down.png");
				SnakeBodyPatterns.tailLeft = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "t_left.png");
				SnakeBodyPatterns.tailRight = OffsetImage.InitCenterImage(1, XSnake_GUI.pathhead + "t_right.png");
				SnakeBodyPatterns.headUp = OffsetImage.InitCenterImage(4, XSnake_GUI.pathhead + "h_up.png");
				SnakeBodyPatterns.headDown = OffsetImage.InitCenterImage(4, XSnake_GUI.pathhead + "h_down.png");
				SnakeBodyPatterns.headLeft = OffsetImage.InitCenterImage(4, XSnake_GUI.pathhead + "h_left.png");
				SnakeBodyPatterns.headRight = OffsetImage.InitCenterImage(4, XSnake_GUI.pathhead + "h_right.png");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 蛇身的方向枚举，用于绘制身体
	 */
	public enum BodyDirection {
		Left,
		Up,
		Right,
		Down,
		UL, //左上
		UR, //右上
		DL, //左下
		DR; //右下

		public static BodyDirection valueOf(int val) {
			switch (val) {
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

	public static final int InitLength = 20;			//蛇身的初始长度
	List<SnakeBody>body = new ArrayList<SnakeBody>(); 	//储存身体的List
	SnakeGame map; 										//与蛇关联的地图实例
	int score = 0; 										//该玩家所获分数
	public static boolean CanTurnBack = true; 			//蛇是否可以掉头
	public static boolean Collidable = false;			//蛇身是否算障碍物
	boolean CrossWall = false; 							//存储穿边墙信息
	SnakeBody nextpart; 								//预测下一个body出现的位置
	int drawStyle = 0; 									//蛇身的样式，用于区别玩家
	int bodytoadd = 0; 									//存储长长的部分

	/**
	 * 新建蛇的实例，起始位置为默认起始位置20, 20
	 * @param parentMap
	 */
	public Snake(SnakeGame parentMap) {
		map = parentMap;
		body.add(new SnakeBody(20, 20, BodyDirection.Right));
		GenerateNext();
		AddBody(InitLength - 1);
	}
	/**
	 * 新建蛇的实例
	 * @param parentMap
	 * 所属的地图
	 * @param startx
	 * 起点的x坐标
	 * @param starty
	 * 起点的y坐标
	 */
	public Snake(SnakeGame parentMap, int startx, int starty) {
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
	public void DrawBody(Graphics g) {
		//TODO 换头
		if (drawStyle > 0)
			g.setColor(Color.BLACK);
		else
			g.setColor(Color.BLUE);
		body.get(body.size() - 1).DrawTail(g);
		for (int i = body.size() - 2; i > 0; i--) {
			body.get(i).DrawObject(g, body.get(i + 1).dir);
		}
		body.get(0).DrawHead(g);
		//((Graphics2D)g).drawString("x:"+body.get(0).locx+"y:"+body.get(0).locy, 10, 10); //Debug用
		//((Graphics2D)g).drawString(this.score+"", body.get(0).locx*MapObject.AreaSize, body.get(0).locy*MapObject.AreaSize);
	}
	/**
	 * 判断是否撞上
	 */
	public boolean CheckHit() {
		if (CrossWall) {
			CrossWall = false;
			return true;
		}
		//synchronized(map.entities){
		for (int i = 0; i < map.entities.length; i++)
			if (nextpart.IsHit(map.entities[i])) {
				map.entities[i].TakeEffect(this);
				map.neti.SyncEntity(i);
			}
		//}
		return map.GridBlocked[nextpart.locx][nextpart.locy];
	}
	/**
	 * 移动蛇身
	 */
	public void MoveStep() {
		if (isNmoving)
			return;
		CutTail();
		AddBody();
	}
	/**
	 * 增加长度
	 */
	public synchronized void AddBody() {
		if (Collidable)
			map.GridBlocked[nextpart.locx][nextpart.locy] = true;
		map.GridState[nextpart.locx][nextpart.locy] = true;
		body.add(0, nextpart);
		GenerateNext();
	}
	/**
	 * 增加一定长度，并且不立即增加
	 * @param num
	 * 需要增加的长度
	 */
	public void AddBody(int num) {
		bodytoadd = num;
	}
	/**
	 * 从尾部减去一段
	 */
	public synchronized void CutTail() {
		if (bodytoadd >= 0) {
			bodytoadd--;
			return;
		}
		SnakeBody tail = body.get(body.size() - 1);
		map.GridState[tail.locx][tail.locy] = false;
		if (Collidable)
			map.GridBlocked[tail.locx][tail.locy] = false;
		body.remove(body.size() - 1);
	}

	/**
	 * 获取蛇的身长
	 */
	public int GetLength() {
		return body.size();
	}
	/**
	 * 更改蛇前进的方向
	 * @param direction
	 * 前进的方向
	 */
	public void SetDirection(BodyDirection direction) {
		if (!CanTurnBack) {
			if ((direction.ordinal() % 2) == (body.get(0).dir.ordinal() % 2))
				return;
			if (Math.abs(direction.ordinal() - body.get(1).dir.ordinal()) == 2)
				return;
		}
		body.get(0).dir = direction;
		GenerateNext();
	}

	private void GenerateNext() {
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
		default:
			break;
		}
	}

	boolean isNmoving = false; //暂停用的变量
	public synchronized void Pause() {
		isNmoving = true;
	}
	public synchronized void Continue() {
		isNmoving = false;
	}
	public synchronized void ChangePauseState() {
		isNmoving = !isNmoving;
	}
}
