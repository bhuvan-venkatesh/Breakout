import java.awt.Color;


public class Paddle extends GameComponent {
	public static final int PADDLE_WIDTH = 40, PADDLE_HEIGHT = 15, PADDLE_STEP = 2;
	public static final Color PADDLE_COLOR = Color.gray;
	public Paddle()
	{
		height = PADDLE_HEIGHT;
		width = PADDLE_WIDTH;
	}
	
	public void moveLeft()
	{
		if(x > 3)
			x-= PADDLE_STEP;
	}
	
	public void moveRight()
	{
		if(x + PADDLE_WIDTH < GameField.FIELD_WIDTH - 1)
			x += PADDLE_STEP;
	}

}
