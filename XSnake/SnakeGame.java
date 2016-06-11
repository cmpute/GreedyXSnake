package XSnake;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import XSnake.Snake.BodyDirection;

 @ SuppressWarnings("serial")
public class SnakeGame extends JPanel implements KeyListener {
	public static int MapMaxX = 49,	MapMaxY = 35; //地图大小参数
	public final static int MaxSleep = 1000, MinSleep = 20; //最大最小刷新时间
	public final static int MaxEntityNum = 16; //最多entity数
	int Sleeptime = 80; //刷新时间，控制游戏速度
	public static boolean NoSideWall = true; //地图边缘是否可以通过
	public static boolean HitToDeath = true; //撞上障碍物以后是暂停还是死亡
	public boolean[][] GridBlocked = new boolean[MapMaxX][MapMaxY]; //障碍物标志
	public boolean[][] GridState = new boolean[MapMaxX][MapMaxY]; //是否有物体标志
	public Snake s_self = new Snake(this), snake1; //两名玩家
	public MapEntity[] entities = new MapEntity[]{
		new Food(this),
		new Food(this)
	}; //食物、道具等等，由于传输限制，最大为MaxEntityNum个entity
	public Obstacle[] obstacles;
	public WebInterface neti;
	public int timestamp = 0;
	public Image background;

	 @ Override
	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			s_self.SetDirection(BodyDirection.Left);
			neti.SendDirection();
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			s_self.SetDirection(BodyDirection.Right);
			neti.SendDirection();
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			s_self.SetDirection(BodyDirection.Up);
			neti.SendDirection();
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
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
		case KeyEvent.VK_E:
		case KeyEvent.VK_END:
			GameEnd(0);
			neti.GametoEnd();
			break;
		case KeyEvent.VK_M:
			SoundEffect.muteall = !(SoundEffect.muteall);
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
	JFrame par;
	public SnakeGame(JFrame parent) {
		MapSets.GenerateMap1(this);
		background = PaintBackground();
		par = parent;
	}
	/**
	 * 绘制背景图片
	 * @return
	 * 背景图片的Image类型
	 */
	public Image PaintBackground() {
		BufferedImage bg = new BufferedImage((MapMaxX + 1) * MapObject.AreaSize, (MapMaxY + 1) * MapObject.AreaSize, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = bg.createGraphics();
		try {
			g.drawImage(ImageIO.read(XSnake_GUI.loader.getResourceAsStream("XSnake/grass.jpg")), 0, 0, bg.getWidth(), bg.getHeight(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bg.flush();
		return bg;
	}

	public void paint(Graphics g) {
		//super.paint(g);
		g.drawImage(background, 0, 0, this); //绘制背景
		for (MapEntity e : entities) //绘制地图物体
			e.DrawObject(g);
		if (obstacles != null) //绘制地图障碍物
			for (Obstacle o : obstacles)
				o.DrawObject(g);
		if (snake1 != null) //绘制玩家
			snake1.DrawBody(g);
		s_self.DrawBody(g);
		//绘制分数
		//Graphics2D g2d = (Graphics2D)g;
		g.setFont(new Font("Calibri", Font.ITALIC + Font.BOLD, 18));
		g.setColor(new Color(0, 0, 0, 0.9f));
		g.drawString("Your Score: " + s_self.score, 20, 20);
		g.drawString("The Other Player's Score: " + snake1.score, 20, 40);
	}
	/**
	 * 每一个Sleeptime后刷新时调用的游戏逻辑计算
	 */
	public void ProcessStep() {
		timestamp++;
		if (s_self.CheckHit())
			if (HitToDeath)
				GameEnd(2);
			else
				s_self.Pause();
		else
			s_self.MoveStep();
		if (snake1 != null)
			if (snake1.CheckHit())
				if (HitToDeath)
					GameEnd(1);
				else
					snake1.Pause();
			else
				snake1.MoveStep();
	}
	/**
	 * 设置刷新的频率
	 * @param steptime
	 * 每次刷新的时间间隔
	 */
	public void SetStepTime(int steptime) {
		Sleeptime = steptime;
	}
	/**
	 * 减少刷新时间间隔，加快游戏速度
	 */
	public synchronized void SpeedUp() {
		if (Sleeptime > MinSleep)
			Sleeptime -= 10;
	}
	/**
	 * 游戏结束
	 * @param state
	 * 0--非正常结束
	 * 1--本机玩家赢了
	 * 2--对方赢了
	 */
	public void GameEnd(int state) {
		((GamePage)par).se.StopSound();
		neti.gamepaused = true;
		switch (state) {
		case 0:
			JOptionPane.showMessageDialog(null, "游戏被结束啦~", "Game Broken", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 1:
			JOptionPane.showMessageDialog(null, "对方撞墙啦！o(*≧▽≦)ツ", "你赢了", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 2:
			JOptionPane.showMessageDialog(null, "您撞墙啦！TvT", "你输了", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
		int minscore = 0;
		if (RankingPage.ranks[4] != null)
			minscore = RankingPage.ranks[4].score;
		else if (RankingPage.ranks[3] != null)
			minscore = RankingPage.ranks[3].score;
		else if (RankingPage.ranks[2] != null)
			minscore = RankingPage.ranks[2].score;
		else if (RankingPage.ranks[1] != null)
			minscore = RankingPage.ranks[1].score;
		else if (RankingPage.ranks[0] != null)
			minscore = RankingPage.ranks[0].score;
		par.setVisible(false);
		if (s_self.score > minscore) {
			int mode = 0;
			if (NoSideWall)
				mode += 1;
			if (!HitToDeath)
				mode += 2;
			new AddRankingPage(new RankItem("", s_self.score, mode));
		}
		par.dispose();
	}
	public synchronized void SpeedDown() {
		if (Sleeptime < MaxSleep)
			Sleeptime += 10;
	}
	/**
	 * 获取游戏速度
	 * @return
	 * 一次线程睡眠的时间
	 */
	public int GetSpeed() {
		return Sleeptime;
	}

	 @ Override
	public void keyReleased(KeyEvent arg0) {}

	 @ Override
	public void keyTyped(KeyEvent arg0) {}
}
