package XSnake;

import java.awt.event.*;
import javax.swing.*;

/**
 * 帮助页面
 */
@SuppressWarnings("serial")
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
		this.add(new JLabel("贪吃蛇XSnake"));
		this.add(new JLabel("作者：钟元鑫 2014010812 汽42"));
		this.add(new JLabel(" "));
		this.add(new JLabel("控制自己的小蛇去抢占更多的金蛋吧~~~~"));
		this.add(new JLabel(" "));
		this.add(new JLabel("操作方法："));
		this.add(new JLabel("		上下左右方向键或者ASDW控制蛇的移动"));
		this.add(new JLabel("		按空格暂停自己，按P键暂停游戏（对双方有效）"));
		this.add(new JLabel("		按E可以直接结束游戏"));
		this.add(new JLabel("		按M可以切换静音和不静音"));
		this.add(new JLabel("		按PageUp和PageDown可以调节游戏速度，对双方有效"));
		this.add(new JLabel(" "));
		this.add(new JLabel("设置说明："));
		this.add(new JLabel("		若勾选了'撞墙后游戏结束'，那么蛇撞到障碍物游戏立即结束"));
		this.add(new JLabel("		若未勾选'撞墙后游戏结束'，那么蛇撞到障碍物后按空格游戏继续进行"));
		this.add(new JLabel("		若勾选了'蛇身视为障碍物'，那么撞到蛇身上就算撞到了障碍物"));
		this.add(new JLabel("		若未勾选'蛇身视为障碍物'，那么蛇撞到自身也不会死"));
		this.add(new JSeparator(JSeparator.HORIZONTAL));
		//TODO:增加帮助文字
		JButton Back = new JButton("返回");
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
