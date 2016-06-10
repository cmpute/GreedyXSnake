package XSnake;

import java.io.*;

import javax.sound.sampled.*;;

public class SoundEffect extends Thread{
	String file;
	boolean isloop;
	boolean state = true;
	
	public static boolean muteall = true;		//�Ƿ���
	public SoundEffect(String musicFile, boolean loop)
	{
		file = musicFile;
		isloop = loop;
	}
	public void StopSound()
	{
		state = false;
	}
	public void run()
	{
		try
		{
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(file));
			// ��ȡ��Ƶ�������
			AudioFormat audioFormat = audioInputStream.getFormat();
			// ������������
			DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat, AudioSystem.NOT_SPECIFIED);
			SourceDataLine sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();
			//���������ж�ȡ���ݷ��͵�������
			int count;
			byte tempBuffer[] = new byte[1024];
			while (state&&((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1)) {
				if (muteall)
					continue;
				if (count > 0) {
					sourceDataLine.write(tempBuffer, 0, count);
				}
			}
			// ������ݻ���,���ر�����
			sourceDataLine.drain();
			sourceDataLine.close();
			if(isloop)
				run();
		}catch(Exception e){e.printStackTrace();}
	}	
}
