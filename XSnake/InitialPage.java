package XSnake;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
		start = addButton("开始游戏");
		setting = addButton("游戏设置");
		ranking = addButton("排行榜");
		help = addButton("游戏帮助");
		exit = addButton("退出游戏");
	}
	
	private JButton addButton(String text) 
	{
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(150, 75));
        button.setMargin(new Insets(10,15,10,15));
        JPanel bp = new JPanel();
        bp.setPreferredSize(new Dimension(400,100));
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
