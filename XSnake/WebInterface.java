package XSnake;

import java.io.*;
import java.net.*;
import java.util.List;

public class WebInterface
{
	SnakeGame host;
	Socket connect;
	boolean isserver,gamestarted=false;
	ConnectStatus stat = ConnectStatus.NoTask;
	public enum ConnectStatus
	{
		NoTask, WaitingForConnect, SyncParams, Startin2Seconds, Started
	}
	
	/*Constant Fields*/
	static final int MAP_PARAMS=0x0020;
	static final int GAME_START=0x0021;
	public WebInterface(Socket connect, boolean isServer)
	{
		this.connect = connect;
		this.isserver = isServer;
	}
	/**
	 * ������Ϸʵ�������п���
	 * @param hostmap
	 * ��Ҫ���Ƶ���Ϸ����
	 */
	public void AssociateGame(SnakeGame hostmap)
	{
		host = hostmap;
		host.neti = this;
	}
	/**�������˿�ʼ�ȴ�����
	 * 
	 * @param port
	 * @throws IOException
	 */
	public void WaitForJoin(int port) throws IOException
	{
		ServerSocket welcome = new ServerSocket(port);
		connect = welcome.accept();
		welcome.close();
	}
	/**
	 * �ͻ��˼�����Ϸ
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public void JoinGame(InetAddress host, int port) throws IOException
	{
		connect = new Socket(host, port);
	}
	
	/**
	 * ��������ͻ��˷��Ϳ�����Ϣ��ͬ�����˵ĵ�ͼ��Ϣ
	 * @throws IOException
	 * �����־��鲻��ȷ�����׳�IOException�쳣
	 */
	public boolean SyncParam() throws IOException
	{
	    ObjectOutputStream oos = new ObjectOutputStream(connect.getOutputStream());
	    oos.write(MAP_PARAMS);
	    oos.writeInt(SnakeGame.MapMaxX);
	    oos.writeInt(SnakeGame.MapMaxY);
	    oos.writeBoolean(SnakeGame.NoSideWall);
	    oos.writeBoolean(Snake.CanTurnBack);
	    oos.writeBoolean(Snake.Collidable);
	    oos.flush();
	    //TODO: ͬ����ͼ��Ϣ���ϰ���λ��)
		if(connect.getInputStream().read()!=MAP_PARAMS)
			return false;
		return true;
	}
	public boolean WaitForParam() throws IOException
	{
		ObjectInputStream ois = new ObjectInputStream(connect.getInputStream());
		if(ois.read()!=MAP_PARAMS)
			return false;
		SnakeGame.MapMaxX = ois.readInt();
		SnakeGame.MapMaxY = ois.readInt();
		SnakeGame.NoSideWall = ois.readBoolean();
		Snake.CanTurnBack = ois.readBoolean();
		Snake.Collidable = ois.readBoolean();
		connect.getOutputStream().write(MAP_PARAMS);
		connect.getOutputStream().flush();
		return true;
	}
	
	public boolean SyncSnake() throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(connect.getOutputStream());
		oos.writeInt(host.s_self.body.get(0).locx);
		oos.writeInt(host.s_self.body.get(0).locy);
		oos.flush();
		ObjectInputStream ois = new ObjectInputStream(connect.getInputStream());
		int locx = ois.readInt();
		int locy = ois.readInt();
		host.snake1 = new Snake(host,locx,locy);
		return true;
	}
	public boolean WaitForSnake() throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(connect.getInputStream());
		int locx = ois.readInt();
		int locy = ois.readInt();
		host.snake1 = new Snake(host,locx,locy);
		ObjectOutputStream oos = new ObjectOutputStream(connect.getOutputStream());
		oos.writeInt(host.s_self.body.get(0).locx);
		oos.writeInt(host.s_self.body.get(0).locy);
		oos.flush();
		return true;
	}
	public void SendDirection()
	{
		 
	}
}
