package XSnake;
import java.awt.*;

/*���е�ͼ������Ļ���*/

public abstract class MapObject {
	public static final int AreaSize=16; //�ж�����Ĵ�С(����)
	public int locx, locy;
	
	public abstract void DrawObject(Graphics g);
	public boolean IsHit(MapObject target)
	{
		return (locx==target.locx)&&(locy==target.locy);
	}
}
