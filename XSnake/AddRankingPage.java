package XSnake;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * 登记排名界面
 */
 @ SuppressWarnings("serial")
public class AddRankingPage extends JFrame {

	public AddRankingPage(RankItem rankval) {
		rankv = rankval;
		RankingPage.LoadRank();
		InitComponent();
	}
	JTextField namet;
	JButton set;
	RankItem rankv;
	public void InitComponent() {
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		JPanel tp = new JPanel();
		tp.setLayout(new FlowLayout());
		tp.add(new JLabel("请输入尊姓大名~(请避免空格)"));
		namet = new JTextField(20);
		tp.add(namet);
		this.add(tp);

		JPanel bp = new JPanel();
		bp.setLayout(new FlowLayout());
		set = new JButton("保存排名");
		set.addActionListener(new ActionListener() {
			 @ Override
			public void actionPerformed(ActionEvent e) {
				rankv.name = namet.getText().replace(' ', '_');
				RankingPage.InsertRank(rankv);
				RankingPage.SaveRank();
				add(RankingPage.GetRankTable());
				revalidate();
				pack();
				set.setEnabled(false);
			}
		});
		bp.add(set);
		JButton leave = new JButton("低调走人");
		leave.addActionListener(new ActionListener() {
			 @ Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		bp.add(leave);
		this.add(bp);
		this.pack();
		this.setVisible(true);
	}
}
