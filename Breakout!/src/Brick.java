import java.awt.Color;


public class Brick extends GameComponent {
	public static final int BRICK_WIDTH = 40, BRICK_HEIGHT = 15;
	public static final Color[] colours = {Color.red, Color.orange, Color.yellow,Color.green, Color.blue};
	private int level;
	
	public Brick()
	{
		level = 1;
		height = BRICK_HEIGHT;
		width = BRICK_WIDTH;
	}
	
	//Part of the named constructor idiom
	
	public Brick initLevel(int level)
	{
		this.setLevel(level);
		return this;
	}
	
	public void hit()
	{
		level--;
		if(level == 0)
		{
			color = GameField.back;
			return;
		}
		color = colours[level-1];
			
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
