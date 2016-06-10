package XSnake;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

/**
 * 储存图像信息和偏移量信息（图片可以不是完美符合AreaSize*AreaSize的)
 */
public class OffsetImage {
	Image image;
	int offsetx;
	int offsety;
	
	public OffsetImage(Image source, int OffsetX, int OffsetY)
	{
		image = source;
		offsetx = OffsetX;
		offsety = OffsetY;
	}
	/**
	 * 在Graphics中绘制图片
	 * @param g
	 * Graphics句柄
	 * @param x
	 * x坐标
	 * @param y
	 * y坐标
	 */
	public void DrawTo(Graphics g, int x, int y)
	{
		g.drawImage(image, x-offsetx, y-offsety, null);
	}
	/**
	 * 加载一个图像
	 * @param ratio
	 * 图像最终大小相对于AreaSize的比率
	 * @param imagepath
	 * 图像路径
	 * @param OffsetX
	 * 相对于左上角的X(负)偏移量
	 * @param OffsetY
	 * 相对于左上角的Y(负)偏移量
	 * @return
	 * 加载好的图像(OffsetImage实例)
	 */
	public static OffsetImage InitImage(float ratio, String imagepath, int OffsetX, int OffsetY) throws FileNotFoundException, IOException
	{
		BufferedImage t = new BufferedImage((int)(ratio*MapObject.AreaSize), (int)(ratio*MapObject.AreaSize), BufferedImage.TYPE_4BYTE_ABGR);
		t.createGraphics().drawImage(ImageIO.read(XSnake_GUI.loader.getResourceAsStream(imagepath)), 0, 0, t.getWidth(), t.getHeight(), null);
		return new OffsetImage(t, OffsetX, OffsetY);
	}
	/**
	 * 加载一个图像，图像位置在方格中居中
	 * @param ratio
	 * 图像最终大小相对于AreaSize的比率
	 * @param imagepath
	 * 图像路径
	 * @return
	 * 加载好的图像(OffsetImage实例)
	 */
	public static OffsetImage InitCenterImage(float ratio, String imagepath) throws FileNotFoundException, IOException
	{
		BufferedImage t = new BufferedImage((int)(ratio*MapObject.AreaSize), (int)(ratio*MapObject.AreaSize), BufferedImage.TYPE_4BYTE_ABGR);
		t.createGraphics().drawImage(ImageIO.read(XSnake_GUI.loader.getResourceAsStream(imagepath)), 0, 0, t.getWidth(), t.getHeight(), null);
		return new OffsetImage(t, (int)((ratio-1)/2*MapObject.AreaSize), (int)((ratio-1)/2*MapObject.AreaSize));
	}
}
