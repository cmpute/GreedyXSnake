package XSnake;

/**
 * ��ҵ�ͼ��Ϣ��ͬ��ʱ���������쳣
 *
 */
public class GameAsyncException extends Exception {
	int time;
	public GameAsyncException(String message, int TimeStamp)
	{
		super(message);
		time = TimeStamp;
	}
	public int GetTimeStamp()
	{
		return time;
	}
}
