package XSnake;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * �Ǽ���������
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
		tp.add(new JLabel("���������մ���~(�����ո�)"));
		namet = new JTextField(20);
		tp.add(namet);
		this.add(tp);

		JPanel bp = new JPanel();
		bp.setLayout(new FlowLayout());
		set = new JButton("��������");
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
		JButton leave = new JButton("�͵�����");
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
