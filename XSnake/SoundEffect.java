package XSnake;

import javax.sound.sampled.*;

/**
 * ���������ķ�װ�����࣬ ����ʱ�½��߳��첽����
 */
public class SoundEffect extends Thread{
	String file;
	boolean isloop;
	boolean state = true;
	
	public static boolean muteall = false;		//�Ƿ�������Ϊ��̬��������
	public SoundEffect(String musicFile, boolean loop)
	{
		file = musicFile;
		isloop = loop;
	}
	//ֹͣ���ֲ���
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
					{System.out.println("");continue;}
				if (count > 0) {
					sourceDataLine.write(tempBuffer, 0, count);
				}
			}
			// ������ݻ���,���ر�����
			sourceDataLine.drain();
			sourceDataLine.close();
			if(isloop && state)
				run();
		} catch(Exception e) {e.printStackTrace();}
	}	
}
