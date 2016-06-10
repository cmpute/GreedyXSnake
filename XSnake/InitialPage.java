package XSnake;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 主页面
 */
@SuppressWarnings("serial")
public class InitialPage extends JPanel implements ActionListener {
	public InitialPage(XSnake_GUI parent)
	{
		InitComponent();
		par = parent;
	}
	
	private XSnake_GUI par;
	private JButton start, setting, ranking, help, exit;
	
	public void InitComponent()
	{
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		try {
			JPanel head = new JPanel(){
				Image bg = ImageIO.read(XSnake_GUI.loader.getResourceAsStream("XSnake/header.png"));
				public void paint(Graphics g)
				{
					g.drawImage(bg , 0, 0, 400, 300, this);
				}
			};
			head.setPreferredSize(new Dimension(400,300));
			head.setBounds(new Rectangle(400,300));
			this.add(head);
		} catch (IOException e) {e.printStackTrace();}
		start = addButton("开始游戏");
		setting = addButton("游戏设置");
		ranking = addButton("排行榜");
		help = addButton("游戏帮助");
		exit = addButton("退出游戏");
		this.add(new JSeparator(JSeparator.HORIZONTAL));
		this.add(new JLabel("作者：钟元鑫 2014010812 汽42"));
	}
	
	private JButton addButton(String text) 
	{
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(150, 45));
        button.setMargin(new Insets(10,15,10,15));
        JPanel bp = new JPanel();
        bp.setPreferredSize(new Dimension(400,60));
        bp.add(button);
        this.add(bp);
        return button;
    }
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(exit))
		{
			System.exit(0);
			return;
		}
		//par.getContentPane().removeAll();
		if(e.getSource().equals(start))
			par.setContentPane(new StartingPage(par));
		if(e.getSource().equals(setting))
			par.setContentPane(new SettingPage(par));
		if(e.getSource().equals(ranking))
			par.setContentPane(new RankingPage(par));
		if(e.getSource().equals(help))
			par.setContentPane(new HelpPage(par));
		par.revalidate();
		par.pack();
		par.repaint();
	}
}
