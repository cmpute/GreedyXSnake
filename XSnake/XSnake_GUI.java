package XSnake;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import XSnake.Snake.BodyDirection;

public class XSnake_GUI extends JFrame {

	JPanel init;
	
	public XSnake_GUI()
	{
		InitComponent();
	}
	public void InitComponent()
	{
		init = new InitialPage(this);
		//this.setContentPane(init);
		this.setContentPane(new StartingPage(this));
		this.setBounds(200,100,800,600);
		this.pack();
		this.setTitle("Greedy Snake By ÖÓÔªöÎ");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static BufferedImage InitImage(float ratio, String imagepath) throws FileNotFoundException, IOException
	{
		BufferedImage t = new BufferedImage((int)(ratio*MapObject.AreaSize), (int)(ratio*MapObject.AreaSize), BufferedImage.TYPE_4BYTE_ABGR);
		t.createGraphics().drawImage(ImageIO.read(new FileInputStream(imagepath)), 0, 0, t.getWidth(), t.getHeight(), null);
		return t;
	}
	public static void main(String[] args) {
		
		try {
			Food.egg = OffsetImage.InitCenterImage(2, "XSnake/egg.png");
			Obstacle.wall = OffsetImage.InitCenterImage(1, "XSnake/wall.png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new XSnake_GUI().setVisible(true);
	}

}
