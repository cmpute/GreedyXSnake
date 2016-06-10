package XSnake;

import javax.sound.sampled.*;

/**
 * 播放声音的封装工具类， 播放时新建线程异步播放
 */
public class SoundEffect extends Thread{
	String file;
	boolean isloop;
	boolean state = true;
	
	public static boolean muteall = false;		//是否静音，作为静态环境变量
	public SoundEffect(String musicFile, boolean loop)
	{
		file = musicFile;
		isloop = loop;
	}
	//停止音乐播放
	public void StopSound()
	{
		state = false;
	}
	@Override
	public void run()
	{
		try
		{
			//System.out.println(XSnake_GUI.loader.getResource(file));
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(XSnake_GUI.loader.getResourceAsStream(file));
			// 获取音频编码对象
			AudioFormat audioFormat = audioInputStream.getFormat();
			// 设置数据输入
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat, AudioSystem.NOT_SPECIFIED);
			SourceDataLine sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			//从输入流中读取数据发送到混音器
			int count;
			byte tempBuffer[] = new byte[1024];
			while (state&&((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1)) {
				if (muteall)
					{System.out.println("");continue;}
				if (count > 0) {
					sourceDataLine.write(tempBuffer, 0, count);
				}
			}
			// 清空数据缓冲,并关闭输入
			sourceDataLine.drain();
			sourceDataLine.close();
			if(isloop && state)
				run();
		} catch(Exception e) {e.printStackTrace();}
	}	
}
