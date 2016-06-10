package XSnake;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

/**
 * 排名页面
 */
@SuppressWarnings("serial")
public class RankingPage extends JPanel
{
	public static RankItem[] ranks = new RankItem[5];
	public static String path = System.getProperty("user.dir")+"\\ranking.ini";
	
	XSnake_GUI par;
	public RankingPage(XSnake_GUI parent)
	{
		par=parent;
		InitComponent();
	}
	public static JScrollPane GetRankTable()
	{
		Object[] titles = new Object[]{"排名","名字","分数","模式"};
		Object[][] vals = new Object[5][4];
		for(int i = 0;i<5;i++)
		{
			vals[i][0]=i+1;
			if(ranks[i]==null)
			{
				vals[i][1]="";
				vals[i][2]="N/A";
				vals[i][3]="N/A";
				continue;
			}
			vals[i][1]=ranks[i].name;
			vals[i][2]=ranks[i].score;
			switch(ranks[i].mode)
			{
			case 0:
				vals[i][3]="正常模式";
				break;
			case 1:
				vals[i][3]="无墙模式";
				break;
			case 2:
				vals[i][3]="无敌蛇模式";
				break;
			case 3:
				vals[i][3]="无墙无敌蛇模式";
				break;
			}
		}
		JTable rankst = new JTable(vals,titles);
		return new JScrollPane(rankst);
	}
	public void InitComponent()
	{
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		LoadRank();
		this.add(GetRankTable());
		//TODO:增加排行榜的读取
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
	
	/**
	 * 加载排行榜
	 * @return
	 * 存在排行榜文件则加载并返回true，不存在则直接返回false
	 */
	public static boolean LoadRank()
	{
		File f = new File(path);
		if(!f.exists())
			return false;
		try {
			Scanner sin = new Scanner(new FileInputStream(f));
			if(sin.nextLine().indexOf("[SnakeRanking]")<0)
			{
				sin.close();
				return false;
			}
			int pointer = 0;
			while(sin.hasNext()&&(pointer<5))
			{
				String n = sin.next();
				int s = sin.nextInt();
				int m = sin.nextInt();
				ranks[pointer++] = new RankItem(n,s,m);
			}
			sin.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 在排行榜中插入新值
	 * @param value
	 * 插入的新值
	 */
	public static void InsertRank(RankItem value)
	{
		if(value.score>((ranks[4]==null)?0:(ranks[4].score)))
		{
			ranks[4]=value;
			for(int i=3;i>=0;i--)
			{
				if(ranks[i+1].score > ((ranks[i]==null)?0:(ranks[i].score)))
				{
					RankItem t = ranks[i];
					ranks[i] = ranks[i+1];
					ranks[i+1] = t;
				}
				else
					break;
			}
		}
	}
	/**
	 * 保存排行榜到文件
	 */
	public static void SaveRank()
	{
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(path));
			ps.println("[SnakeRanking]");
			for(int i=0;i<5;i++)
				if(ranks[i]!=null)
					ps.println(ranks[i].name + " " + ranks[i].score + " " + ranks[i].mode);
			ps.flush();
			ps.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}
