package XSnake;

import java.awt.Color;
import java.awt.Graphics;

public class Obstacle extends MapObject {

	public Obstacle(int x, int y, SnakeGame host)
	{
		locx = x;
		locy = y;
		host.GridBlocked[x][y] = true;
		host.GridState[x][y] = true;
	}
	
	@Override
	public void DrawObject(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLACK);
		g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
	}

}
