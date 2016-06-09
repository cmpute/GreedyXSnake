package XSnake;

import java.awt.*;

public class Obstacle extends MapObject {

	public static OffsetImage wall;
	
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
		//g.setColor(Color.BLACK);
		//g.fillRect(locx*AreaSize, locy*AreaSize, AreaSize+1, AreaSize+1);
		wall.DrawTo(g, locx*AreaSize, locy*AreaSize);
	}

}
