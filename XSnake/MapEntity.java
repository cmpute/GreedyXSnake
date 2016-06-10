package XSnake;

import java.util.*;

/**
 * ��ͼ�е�ʵ���࣬����ʳ����ߵ�
 *
 */
public abstract class MapEntity extends MapObject{
	SnakeGame map;
	public MapEntity(SnakeGame parentMap, int x, int y)
	{
		locx = x;
		locy = y;
		map = parentMap;
		parentMap.GridState[x][y] = true;
	}
	/**
	 * �������һ��ʵ��
	 * @param parentMap
	 * �����ĵ�ͼ
	 */
	public MapEntity(SnakeGame parentMap)
	{
		map = parentMap;
		GenerateLocation();
		parentMap.GridState[locx][locy] = true;
	}
	
	public void GenerateLocation()
	{
		Random r = new Random();
		do
		{
			locx = r.nextInt(SnakeGame.MapMaxX);
			locy = r.nextInt(SnakeGame.MapMaxY);
		}
		while(map.GridState[locx][locy]);//TODO:�������ѭ��
	}
	
	public abstract void TakeEffect(Snake target);
}
