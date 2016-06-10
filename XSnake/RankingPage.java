package XSnake;

import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

/**
 * ����ҳ��
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
		Object[] titles = new Object[]{"����","����","����","ģʽ"};
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
				vals[i][3]="����ģʽ";
				break;
			case 1:
				vals[i][3]="��ǽģʽ";
				break;
			case 2:
				vals[i][3]="�޵���ģʽ";
				break;
			case 3:
				vals[i][3]="��ǽ�޵���ģʽ";
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
		//TODO:�������а�Ķ�ȡ
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
	
	/**
	 * �������а�
	 * @return
	 * �������а��ļ�����ز�����true����������ֱ�ӷ���false
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
	 * �����а��в�����ֵ
	 * @param value
	 * �������ֵ
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
	 * �������а��ļ�
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
