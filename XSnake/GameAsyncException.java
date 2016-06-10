package XSnake;

/**
 * 玩家地图信息不同步时将发生的异常
 *
 */
@SuppressWarnings("serial")
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
