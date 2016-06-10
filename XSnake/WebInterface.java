package XSnake;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class WebInterface
{
	SnakeGame host;
	Socket connect;
	boolean isserver,gamestarted=false,gamepaused=false;
	ConnectStatus stat = ConnectStatus.NoTask;
	public enum ConnectStatus
	{
		NoTask, WaitingForConnect, SyncParams, Startin2Seconds, Started
	}
	
	/*Constant Fields*/
	static final int MAP_PARAMS=0x0020;
	static final int GAME_START=0x0021;
	static final int DIRECT_CHANGE=0x0000;
	static final int PLAYER_PAUSE=0x0022;
	static final int SPEED_UP=0x0023;
	static final int SPEED_DOWN=0x0024;
	static final int GAME_PAUSE=0x0025;
	static final int GAME_BREAK=0x0026;
	static final int GAME_TICK=0x0030;
	static final int SYNC_ENTITY=0x0031;
	public WebInterface(Socket connect, boolean isServer)
	{
		this.connect = connect;
		this.isserver = isServer;
	}
	/**
	 * 关联游戏实例，进行控制
	 * @param hostmap
	 * 需要控制的游戏进程
	 */
	public void AssociateGame(SnakeGame hostmap)
	{
		host = hostmap;
		host.neti = this;
	}
	/**服务器端开始等待加入
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
	 * 客户端加入游戏
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	public void JoinGame(InetAddress host, int port) throws IOException
	{
		connect = new Socket(host, port);
	}
	
	/**
	 * 服务器向客户端发送开局信息，同步两端的地图信息
	 * @throws IOException
	 * 如果标志检查不正确还会抛出IOException异常
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
	    oos.writeBoolean(SnakeGame.HitToDeath);
	    oos.flush();
	    //TODO: 同步地图信息（障碍物位置)
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
		SnakeGame.HitToDeath = ois.readBoolean();
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
	
	public boolean CheckStart() throws IOException
	{
		if(isserver)
		{
			connect.getOutputStream().write(GAME_START);
			if(connect.getInputStream().read()==GAME_START)
				return true;
			return false;
		}
		else
		{
			if(connect.getInputStream().read()!=GAME_START)
				return false;
			connect.getOutputStream().write(GAME_START);
			return true;
		}
	}
	
	public void StartGameThread()
	{
		gamestarted = true;
		
		//刷新线程
		if(isserver)
		new Thread(new Runnable(){
			public void run() {
				while (true) {
					System.out.print(""); //这行没有意义，但是不加入这一行暂停以后无法继续，尚未弄清原因
					if(gamepaused)
						continue;
					try {
						Thread.sleep(host.Sleeptime);
						connect.getOutputStream().write(GAME_TICK);
						//host.neti.SyncMap();
						host.ProcessStep();
						host.repaint();
					} catch (InterruptedException e) {	e.printStackTrace();} 
					  catch (IOException e) {	e.printStackTrace();}
				}
			}
		}).start();
		//更新监听线程
		new Thread(new Runnable(){
			public void run() {
				InputStream ins;
				try {
					ins = connect.getInputStream();
					while (gamestarted){
						System.out.print("");
						int temp = ins.read();
						//判断信息
						if(temp<0x0010){
							host.snake1.SetDirection(Snake.BodyDirection.valueOf(temp&0x000f));
							continue;
						}
						if((temp>=SYNC_ENTITY)&&(temp<SYNC_ENTITY+SnakeGame.MaxEntityNum))
						{
							int index = temp - SYNC_ENTITY;
							host.entities[index].locx = ins.read();
							host.entities[index].locy = ins.read();
							continue;
						}
						switch(temp)
						{
						case PLAYER_PAUSE:
							host.snake1.ChangePauseState();
							break;
						case SPEED_UP:
							host.SpeedUp();
							break;
						case SPEED_DOWN:
							host.SpeedDown();
							break;
						case GAME_TICK:
							host.ProcessStep();
							host.repaint();
							break;
						case GAME_PAUSE:
							gamepaused = !gamepaused;
							break;
						case GAME_BREAK:
							gamestarted=false;
							gamepaused=true;
							connect.close();
							host.GameEnd(0);
							break;
						}
					}
				} catch (IOException ie) {ie.printStackTrace();}
			}
		}).start();
	}
	
	/**
	 * 使游戏停止或继续
	 */
	public void ChangeGameState()
	{
		gamepaused = !gamepaused;
		new ParamThread(GAME_PAUSE).start();
	}
	/*************************     以下函数为对通信过程的封装        **************************/
	public void SendDirection()
	{
		new ParamThread(DIRECT_CHANGE + host.s_self.body.get(0).dir.ordinal()).start();
	}
	public void PlayerPause()
	{
		new ParamThread(PLAYER_PAUSE).start();
	}
	public void GametoEnd()
	{
		new ParamThread(GAME_BREAK).start();
		gamestarted = false;
	}
	public void SpeedUp()
	{
		new ParamThread(SPEED_UP).start();
	}
	public void SpeedDown()
	{
		new ParamThread(SPEED_DOWN).start();
	}
	public void SyncMap()
	{
		//TODO:难点，要检查双方地图内容是否一致
		for(int i =0;i<host.entities.length;i++)
			SyncEntity(i);
	}
	public void SyncEntity(int index)
	{
		if(isserver)
			new ParamValueThread(SYNC_ENTITY+index, host.entities[index].locx, host.entities[index].locy).start();;
	}
	class ParamThread extends Thread
	{
		int param;
		public ParamThread(int parameter)
		{
			param = parameter;
		}
		public void run()
		{
			new Thread(new Runnable(){
				public void run() {
					try {
						connect.getOutputStream().write(param);
						if(param==GAME_BREAK) 
							connect.close();
					} catch (IOException ie) {JOptionPane.showMessageDialog(null, "连接错误:"+ie.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);ie.printStackTrace();}
				}
			}).start();
		}
	}
	class ParamValueThread extends Thread
	{
		int param;
		int[] vals;
		public ParamValueThread(int parameter, int... values)
		{
			param = parameter;
			vals = values;
		}
		public void run()
		{
			new Thread(new Runnable(){
				public void run() {
					try {
						connect.getOutputStream().write(param);
						for(int i =0;i<vals.length;i++)
							connect.getOutputStream().write(vals[i]);
						connect.getOutputStream().flush();
					} catch (IOException ie) {JOptionPane.showMessageDialog(null, "连接错误:"+ie.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);ie.printStackTrace();}
				}
			}).start();
		}
	}
}
