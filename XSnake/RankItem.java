package XSnake;

/**
 * ���������Ϣ�Ľṹ��
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