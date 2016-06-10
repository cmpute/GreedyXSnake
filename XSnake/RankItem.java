package XSnake;

/**
 * 存放排名信息的结构体
 */
public class RankItem
{
	public String name;
	public int score;
	public int mode;
	public RankItem(String Name, int Score, int Mode)
	{
		name = Name.replace(' ', '_');
		score = Score;
		mode = Mode;
	}
}