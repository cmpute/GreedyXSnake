package XSnake;

import java.util.*;

/**
 * 地图中的实体类，包括食物、道具等
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
	 * 随机生成一个实体
	 * @param parentMap
	 * 关联的地图
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
		while(map.GridState[locx][locy]);//TODO:会进入死循环
	}
	
	public abstract void TakeEffect(Snake target);
}
