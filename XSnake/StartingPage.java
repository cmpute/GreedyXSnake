package XSnake;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class StartingPage extends JPanel implements ActionListener{
	public StartingPage(XSnake_GUI parent)
	{
		InitComponent();
		par = parent;
	}
	private XSnake_GUI par;
	
	JButton startserver,joinserver,back;
	JLabel lbl_address,lbl_port,lbl_status;
	JTextField txt_address,txt_port;
	JPanel gameUI;
	
	int portnum = 2345;
	
	public void InitComponent()
	{
		startserver = new JButton("����������");
		startserver.addActionListener(this);
		joinserver = new JButton("������Ϸ");
		joinserver.addActionListener(this);
		lbl_address = new JLabel("�������������ַ");
		lbl_port = new JLabel("������˿ں�");
		txt_address = new JTextField(9);
		txt_port = new JTextField(3);
		txt_port.setText(String.valueOf(portnum));
		
		JPanel area = new JPanel();
		JPanel joinaddr = new JPanel();
		joinaddr.setLayout(new FlowLayout());
		joinaddr.add(lbl_address);
		joinaddr.add(txt_address);
		JPanel joinport = new JPanel();
		joinport.setLayout(new FlowLayout());
		joinport.add(lbl_port);
		joinport.add(txt_port);
		JPanel joinimport = new JPanel();
		joinimport.setLayout(new BoxLayout(joinimport,BoxLayout.PAGE_AXIS));
		joinimport.add(joinaddr);
		joinimport.add(joinport);
		JPanel btn = new JPanel();
		btn.setLayout(new BoxLayout(btn,BoxLayout.PAGE_AXIS));
		btn.add(joinserver);
		btn.add(startserver);
		area.setLayout(new FlowLayout());
		area.add(joinimport);
		area.add(btn);
		back = new JButton("����");
		back.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				par.setContentPane(par.init);
				par.revalidate();
				par.pack();
				par.repaint();
			}
		});
		area.add(back);
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.add(area);
		this.add(new JSeparator(JSeparator.HORIZONTAL));
		JPanel tp = new JPanel();
		lbl_status = new JLabel("��ѡ�񴴽��������Ϸ");
		tp.add(lbl_status);
		this.add(tp);
	}
	
	public static final int checksleeptime = 1000;
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(startserver))
		{
			try{portnum = Integer.parseInt(txt_port.getText());}
			catch(NumberFormatException nfe){
				JOptionPane.showMessageDialog(null, "�˿������ʽ����", "�������", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(portnum>65535||portnum<0){
				JOptionPane.showMessageDialog(null, "�˿����뷶ΧΪ0~65535", "�������", JOptionPane.ERROR_MESSAGE);
				return;
			}
			back.setEnabled(false);
			new Thread(new Runnable(){
				public void run(){
					try{
						ServerSocket welcome = new ServerSocket(portnum);
						lbl_status.setText("�ȴ���Ҽ��룬����Ϊ" + InetAddress.getLocalHost().toString() + "���˿ں�Ϊ" + portnum);
						Socket connect = welcome.accept();
						welcome.close();
						lbl_status.setText("���"+connect.getInetAddress().toString()+"�����룬ͬ����Ϸ�趨��");
						WebInterface wi = new WebInterface(connect, true);
						wi.SyncParam();
						gamemap = new SnakeGame();
						wi.AssociateGame(gamemap);
						wi.SyncSnake();
						lbl_status.setText("ͬ���ɹ�����Ϸ������ʼ...3");
						gamemap.neti = wi;
						Thread.sleep(checksleeptime);
						lbl_status.setText("ͬ���ɹ�����Ϸ������ʼ...2");
						Thread.sleep(checksleeptime);
						lbl_status.setText("ͬ���ɹ�����Ϸ������ʼ...1");
						Thread.sleep(checksleeptime);
						gamemap.neti.CheckStart();
						//gamemap.neti.SyncMap();
						StartGame();
					}
					catch (IOException ie) {JOptionPane.showMessageDialog(null, "���Ӵ���:"+ie.getMessage(), "����", JOptionPane.ERROR_MESSAGE);ie.printStackTrace();} 
					catch (InterruptedException ine) {ine.printStackTrace();}
				}
			}).start();

		}
		if(e.getSource().equals(joinserver))
		{
			try{portnum = Integer.parseInt(txt_port.getText());}
			catch(NumberFormatException nfe){
				JOptionPane.showMessageDialog(null, "�˿������ʽ����", "�������", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(portnum>65535||portnum<0){
				JOptionPane.showMessageDialog(null, "�˿����뷶ΧΪ0~65535", "�������", JOptionPane.ERROR_MESSAGE);
				return;
			}
			InetAddress tarip;
			try{tarip=InetAddress.getByName(txt_address.getText());	}
			catch(Exception ex){
				JOptionPane.showMessageDialog(null, "IP��ַ���벻��ȷ", "�������", JOptionPane.ERROR_MESSAGE);
				return;
			}
			txt_address.setText(tarip.getHostAddress());

			back.setEnabled(false);
			try
			{
				Socket connect = new Socket(tarip,portnum);
				lbl_status.setText("�����ӷ�����"+connect.getInetAddress().toString()+"���ȴ���ͼ����ͬ��");
				WebInterface wi = new WebInterface(connect, false);
				wi.WaitForParam();
				gamemap = new SnakeGame();
				wi.AssociateGame(gamemap);
				wi.WaitForSnake();
				lbl_status.setText("ͬ���ɹ�����Ϸ������ʼ...3");
				gamemap.neti = wi;
				new Thread(new Runnable(){
					public void run(){
						try{
							Thread.sleep(checksleeptime);
							lbl_status.setText("ͬ���ɹ�����Ϸ������ʼ...2");
							Thread.sleep(checksleeptime);
							lbl_status.setText("ͬ���ɹ�����Ϸ������ʼ...1");
							Thread.sleep(checksleeptime);
							gamemap.neti.CheckStart();
							//gamemap.neti.SyncMap();
							StartGame();
						}catch (InterruptedException ine) {ine.printStackTrace();} 
						catch (IOException e) {e.printStackTrace();}
					}
				}).start();
			}
			catch(IOException ie) {JOptionPane.showMessageDialog(null, "���Ӵ���:"+ie.getMessage(), "����", JOptionPane.ERROR_MESSAGE);ie.printStackTrace();} 
			catch(Exception ex){ex.printStackTrace();}
		}
	}
	
	public SnakeGame gamemap;	
	
	public void StartGame()
	{
		JFrame Game = new JFrame();
		Game.add(gamemap);
		Game.setTitle("Greedy Snake");
		Game.addKeyListener(gamemap);
		gamemap.neti.StartGameThread();
		Game.setBounds(par.getBounds().x+200,par.getBounds().y+100,805,610);
		Game.setVisible(true);
	}
}
