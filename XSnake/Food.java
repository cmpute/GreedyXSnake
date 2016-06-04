package XSnake;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics;

public class Food extends MapEntity {

	public static final int FoodScore = 10;
	public Food(SnakeGame parentMap, int x, int y)
	{
		super(parentMap,x,y);
	}
	/**
	 * �������һ��ʳ��
	 * @param parentMap
	 * �����ĵ�ͼ
	 */
	public Food(SnakeGame parentMap)
	{
		super(parentMap);
	}
	
	@Override
	public void DrawObject(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.GREEN);
		g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
	}
	
	/**
	 * ���Ե�ʱ����
	 * @param eator
	 * �Ե�ʳ�����
	 */
	@Override
	public synchronized void TakeEffect(Snake eator)
	{
		eator.AddBody(1); //�ĳ�eator.AddBody()��������ͷ����
		eator.score += FoodScore;
		this.GenerateLocation();
	}
}
