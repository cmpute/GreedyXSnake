package XSnake;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

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
	public void DrawTo(Graphics g, int x, int y)
	{
		g.drawImage(image, x-offsetx, y-offsety, null);
	}
	public static OffsetImage InitImage(float ratio, String imagepath, int OffsetX, int OffsetY) throws FileNotFoundException, IOException
	{
		BufferedImage t = new BufferedImage((int)(ratio*MapObject.AreaSize), (int)(ratio*MapObject.AreaSize), BufferedImage.TYPE_4BYTE_ABGR);
		t.createGraphics().drawImage(ImageIO.read(new FileInputStream(imagepath)), 0, 0, t.getWidth(), t.getHeight(), null);
		return new OffsetImage(t, OffsetX, OffsetY);
	}
	public static OffsetImage InitCenterImage(float ratio, String imagepath) throws FileNotFoundException, IOException
	{
		BufferedImage t = new BufferedImage((int)(ratio*MapObject.AreaSize), (int)(ratio*MapObject.AreaSize), BufferedImage.TYPE_4BYTE_ABGR);
		t.createGraphics().drawImage(ImageIO.read(new FileInputStream(imagepath)), 0, 0, t.getWidth(), t.getHeight(), null);
		return new OffsetImage(t, (int)((ratio-1)/2*MapObject.AreaSize), (int)((ratio-1)/2*MapObject.AreaSize));
	}
}
