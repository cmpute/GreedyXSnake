package XSnake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SettingPage extends JPanel {

	public SettingPage(XSnake_GUI parent)
	{
		InitComponent();
		par = parent;
	}
	private XSnake_GUI par;
	
	JTextField textx,texty;
	JCheckBox isWall,canTurnBack,colli;
	
	public void InitComponent()
	{
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		JPanel mapsetting = new JPanel();
		mapsetting.setLayout(new BoxLayout(mapsetting,BoxLayout.Y_AXIS));
		mapsetting.add(new JLabel("地图参数                                                         "));
		JPanel x = new JPanel();
		x.add(new JLabel("地图宽度"));
		textx = new JTextField(3);
		textx.setText(SnakeGame.MapMaxX+"");
		x.add(textx);
		mapsetting.add(x);
		JPanel y = new JPanel();
		y.add(new JLabel("地图高度"));
		texty = new JTextField(3);
		texty.setText(SnakeGame.MapMaxY+"");
		y.add(texty);
		mapsetting.add(y);
		JPanel tw = new JPanel();
		isWall = new JCheckBox("地图边界可以通过", SnakeGame.NoSideWall);
		tw.add(isWall);
		mapsetting.add(tw);
		this.add(mapsetting);
		this.add(new JSeparator(JSeparator.HORIZONTAL));
		JPanel snakesetting = new JPanel();
		snakesetting.setLayout(new BoxLayout(snakesetting,BoxLayout.Y_AXIS));
		snakesetting.add(new JLabel("贪吃蛇设置                                                         "));
		JPanel sw = new JPanel();
		canTurnBack = new JCheckBox("蛇可以掉头", Snake.CanTurnBack);
		sw.add(canTurnBack);
		snakesetting.add(sw);
		JPanel sw2 = new JPanel();
		colli = new JCheckBox("蛇身视为障碍物", Snake.Collidable);
		sw2.add(colli);
		snakesetting.add(sw2);
		this.add(snakesetting);
		this.add(new JSeparator(JSeparator.HORIZONTAL));
		JPanel bp = new JPanel();
		bp.setLayout(new FlowLayout());
		JButton Save = new JButton("保存设置");
		Save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					SnakeGame.MapMaxX = Integer.parseInt(textx.getText());
					SnakeGame.MapMaxY = Integer.parseInt(texty.getText());
				}
				catch(Exception ex){JOptionPane.showMessageDialog(null, "请输入正确的地图尺寸", "输入错误", JOptionPane.ERROR_MESSAGE);}
				Snake.CanTurnBack = canTurnBack.isSelected();
				Snake.Collidable = colli.isSelected();
				SnakeGame.NoSideWall = isWall.isSelected();
			}
		});
		bp.add(Save);
		JButton Back = new JButton("返回");
		Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				par.getContentPane().removeAll();
				par.setContentPane(par.init);
				par.revalidate();
				par.pack();
				par.repaint();
			}
		});
		bp.add(Back);
		this.add(bp);
	}
}
