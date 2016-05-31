package XSnake;
import java.awt.*;

/*所有地图中物体的基类*/

public abstract class MapObject {
	public static final int AreaSize=16; //判定方块的大小(像素)
	public int locx, locy;
	
	public abstract void DrawObject(Graphics g);
	public boolean IsHit(MapObject target)
	{
		return (locx==target.locx)&&(locy==target.locy);
	}
}
