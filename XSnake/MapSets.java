package XSnake;

import java.util.Random;

/**
 * 地图预设类，提供一些设计好的地图
 *
 */
public class MapSets{
	/**
	 * 地图预设1
	 * @param target
	 * 需要生成地图的游戏
	 */
	public static void GenerateMap1(SnakeGame target) {
		target.obstacles = new Obstacle[]{
			new Obstacle(35, 30, target), new Obstacle(35, 29, target),	new Obstacle(35, 28, target), new Obstacle(35, 27, target), new Obstacle(35, 26, target),
			new Obstacle(5, 5, target),	new Obstacle(6, 5, target),	new Obstacle(7, 5, target),	new Obstacle(8, 5, target),	new Obstacle(8, 6, target),	new Obstacle(8, 7, target),
			new Obstacle(14, 10, target), new Obstacle(16, 10, target), new Obstacle(20, 10, target), new Obstacle(31, 17, target),	new Obstacle(32, 17, target), new Obstacle(33, 17, target),	new Obstacle(34, 17, target), new Obstacle(35, 17, target),	new Obstacle(36, 17, target), new Obstacle(37, 17, target)
		};
		target.entities = new MapEntity[]{
			new Food(target, 20, 23),
			new Food(target, 39, 4)
		};
		Random r = new Random();
		target.s_self = new Snake(target, r.nextInt(SnakeGame.MapMaxX), r.nextInt(SnakeGame.MapMaxY));
	}
	/**
	 * 地图预设2
	 * @param target
	 * 需要生成地图的游戏
	 */
	public static void GenerateMap2(SnakeGame target) {
		target.obstacles = new Obstacle[]{
			new Obstacle(35, 30, target), new Obstacle(35, 29, target), new Obstacle(35, 28, target),
			new Obstacle(5, 5, target)
		};
		target.entities = new MapEntity[]{
			new Food(target, 20, 23),
			new Food(target, 39, 4)
		};
		Random r = new Random();
		target.s_self = new Snake(target, r.nextInt(SnakeGame.MapMaxX), r.nextInt(SnakeGame.MapMaxY));
	}
}
