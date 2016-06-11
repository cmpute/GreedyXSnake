package XSnake;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

 @ SuppressWarnings("serial")
public class XSnake_GUI extends JFrame {

	JPanel init;
	public static final String pathhead = "XSnake/";
	public static ClassLoader loader = new LoaderClass().getClass().getClassLoader();

	public XSnake_GUI() {
		InitComponent();
	}
	public void InitComponent() {
		init = new InitialPage(this);
		this.setContentPane(init);
		//this.setContentPane(new StartingPage(this));
		this.setBounds(200, 100, 800, 600);
		this.pack();
		this.setTitle("Greedy Snake By 钟元鑫");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static BufferedImage InitImage(float ratio, String imagepath) throws FileNotFoundException, IOException
	{
		BufferedImage t = new BufferedImage((int)(ratio * MapObject.AreaSize), (int)(ratio * MapObject.AreaSize), BufferedImage.TYPE_4BYTE_ABGR);
		t.createGraphics().drawImage(ImageIO.read(new FileInputStream(imagepath)), 0, 0, t.getWidth(), t.getHeight(), null);
		return t;
	}
	public static void main(String[]args) {

		try {
			//更改UI风格
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Font font = new Font("黑体", Font.BOLD, 14);
			java.util.Enumeration < Object > keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = UIManager.get(key);
				if (value instanceof javax.swing.plaf.FontUIResource) {
					UIManager.put(key, font);
				}
			}
			//初始化图片
			Food.egg = OffsetImage.InitCenterImage(2, pathhead + "egg.png");
			Obstacle.wall = OffsetImage.InitCenterImage(1, pathhead + "wall.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new XSnake_GUI().setVisible(true);
	}

}
/**
 * 这个类仅仅是为了提供getClass()的主体
 *
 */
class LoaderClass {}
