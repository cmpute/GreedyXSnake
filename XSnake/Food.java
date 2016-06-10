package XSnake;

import java.awt.*;

public class Food extends MapEntity {

	public static final int FoodScore = 10;
	public static OffsetImage egg;

	public Food(SnakeGame parentMap, int x, int y)
	{
		super(parentMap,x,y);
	}
	/**
	 * 随机生成一个食物
	 * @param parentMap
	 * 关联的地图
	 */
	public Food(SnakeGame parentMap)
	{
		super(parentMap);
	}
	
	@Override
	public void DrawObject(Graphics g) {
		// TODO Auto-generated method stub
		//g.setColor(Color.GREEN);
		//g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
		egg.DrawTo(g, locx*AreaSize, locy*AreaSize);
	}
	
	/**
	 * 被吃掉时调用
	 * @param eator
	 * 吃掉食物的蛇
	 */
	@Override
	public synchronized void TakeEffect(Snake eator)
	{
		eator.AddBody(1); //改成eator.AddBody()就是在蛇头增加
		eator.score += FoodScore;
		new SoundEffect("XSnake/goldenegg.wav",false).start();
		this.GenerateLocation();
	}
}
