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
		mapsetting.add(new JLabel("��ͼ����                                                         "));
		JPanel x = new JPanel();
		x.add(new JLabel("��ͼ���"));
		textx = new JTextField(3);
		textx.setText(SnakeGame.MapMaxX+"");
		x.add(textx);
		mapsetting.add(x);
		JPanel y = new JPanel();
		y.add(new JLabel("��ͼ�߶�"));
		texty = new JTextField(3);
		texty.setText(SnakeGame.MapMaxY+"");
		y.add(texty);
		mapsetting.add(y);
		JPanel tw = new JPanel();
		isWall = new JCheckBox("��ͼ�߽����ͨ��", SnakeGame.NoSideWall);
		tw.add(isWall);
		mapsetting.add(tw);
		this.add(mapsetting);
		this.add(new JSeparator(JSeparator.HORIZONTAL));
		JPanel snakesetting = new JPanel();
		snakesetting.setLayout(new BoxLayout(snakesetting,BoxLayout.Y_AXIS));
		snakesetting.add(new JLabel("̰��������                                                         "));
		JPanel sw = new JPanel();
		canTurnBack = new JCheckBox("�߿��Ե�ͷ", Snake.CanTurnBack);
		sw.add(canTurnBack);
		snakesetting.add(sw);
		JPanel sw2 = new JPanel();
		colli = new JCheckBox("������Ϊ�ϰ���", Snake.Collidable);
		sw2.add(colli);
		snakesetting.add(sw2);
		this.add(snakesetting);
		this.add(new JSeparator(JSeparator.HORIZONTAL));
		JPanel bp = new JPanel();
		bp.setLayout(new FlowLayout());
		JButton Save = new JButton("��������");
		Save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					SnakeGame.MapMaxX = Integer.parseInt(textx.getText());
					SnakeGame.MapMaxY = Integer.parseInt(texty.getText());
				}
				catch(Exception ex){JOptionPane.showMessageDialog(null, "��������ȷ�ĵ�ͼ�ߴ�", "�������", JOptionPane.ERROR_MESSAGE);}
				Snake.CanTurnBack = canTurnBack.isSelected();
				Snake.Collidable = colli.isSelected();
				SnakeGame.NoSideWall = isWall.isSelected();
			}
		});
		bp.add(Save);
		JButton Back = new JButton("����");
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
