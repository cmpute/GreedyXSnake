package XSnake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HelpPage extends JPanel
{
	public HelpPage(XSnake_GUI parent)
	{
		InitComponent();
		par = parent;
	}
	private XSnake_GUI par;	
	public void InitComponent()
	{
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		//TODO:���Ӱ�������
		JButton Back = new JButton("����");
		Back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				par.setContentPane(par.init);
				par.revalidate();
				par.pack();
				par.repaint();
			}
		});
		this.add(Back);
	}
}
