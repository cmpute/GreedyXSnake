package XSnake;

import java.util.Random;

/**
 * ��ͼ�е�ʵ���࣬����ʳ����ߵ�
 *
 */
public abstract class MapEntity extends MapObject {
	SnakeMap map;
	public MapEntity(SnakeMap parentMap, int x, int y)
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
	public MapEntity(SnakeMap parentMap)
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
			locx = r.nextInt(map.MapMaxX);
			locy = r.nextInt(map.MapMaxY);
		}
		while(map.GridState[locx][locy]);//TODO:�������ѭ��
	}
	
	public abstract void TakeEffect(Snake target);
}
