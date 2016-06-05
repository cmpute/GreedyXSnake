package XSnake;

import java.util.Random;

/**
 * 地图预设类，提供一些设计好的地图
 *
 */
public class MapSets 
{
	public static void GenerateMap1(SnakeGame target)
	{
		target.obstacles = new Obstacle[]{new Obstacle(35,30, target), new Obstacle(35,29, target), new Obstacle(35,28, target), new Obstacle(5,5, target)};
		target.entities = new MapEntity[]{new Food(target,20,23), new Food(target,39,4)};
		Random r = new Random();
		target.s_self = new Snake(target, r.nextInt(SnakeGame.MapMaxX),r.nextInt(SnakeGame.MapMaxY));
	}
}
