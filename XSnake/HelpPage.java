package XSnake;

import java.awt.event.*;
import javax.swing.*;

/**
 * ����ҳ��
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
		this.add(new JLabel("̰����XSnake"));
		this.add(new JLabel("���ߣ���Ԫ�� 2014010812 ��42"));
		this.add(new JLabel(" "));
		this.add(new JLabel("�����Լ���С��ȥ��ռ����Ľ𵰰�~~~~"));
		this.add(new JLabel(" "));
		this.add(new JLabel("����������"));
		this.add(new JLabel("		�������ҷ��������ASDW�����ߵ��ƶ�"));
		this.add(new JLabel("		���ո���ͣ�Լ�����P����ͣ��Ϸ����˫����Ч��"));
		this.add(new JLabel("		��E����ֱ�ӽ�����Ϸ"));
		this.add(new JLabel("		��M�����л������Ͳ�����"));
		this.add(new JLabel("		��PageUp��PageDown���Ե�����Ϸ�ٶȣ���˫����Ч"));
		this.add(new JLabel(" "));
		this.add(new JLabel("����˵����"));
		this.add(new JLabel("		����ѡ��'ײǽ����Ϸ����'����ô��ײ���ϰ�����Ϸ��������"));
		this.add(new JLabel("		��δ��ѡ'ײǽ����Ϸ����'����ô��ײ���ϰ���󰴿ո���Ϸ��������"));
		this.add(new JLabel("		����ѡ��'������Ϊ�ϰ���'����ôײ�������Ͼ���ײ�����ϰ���"));
		this.add(new JLabel("		��δ��ѡ'������Ϊ�ϰ���'����ô��ײ������Ҳ������"));
		this.add(new JSeparator(JSeparator.HORIZONTAL));
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
