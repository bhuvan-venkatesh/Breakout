import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class AbstractGameField {
	public static final int BRICK_COLUMNS = 10, BRICK_ROWS = 10, NUM_COLORS = Brick.colours.length, 
			BRICK_X_PADDING = (GameField.FIELD_WIDTH - Brick.BRICK_WIDTH*BRICK_COLUMNS)/(BRICK_COLUMNS+1), //It fills the screen
			BRICK_Y_PADDING = 5,
			BRICK_BLOCK_OFFSET = (int) (GameField.FIELD_HEIGHT*.15),
			PIXEL_PADDING = 2;
	private Brick[][] bricks;
	private Paddle pad;
	private Ball ball;
	private ArrayList<Integer> pressed;
	private byte repaintPaddle;
	
	public AbstractGameField()
	{
		pad = (Paddle) new Paddle()
				.initX(GameField.FIELD_WIDTH/2 - Paddle.PADDLE_WIDTH/2) //Center the paddle up
				.initY((int) (GameField.FIELD_HEIGHT*.9)) //At a predesignated height
				.initColor(Ball.BALL_COLOR);
		ball = (Ball) new Ball()
				.initRight(true)
				.initDown(false)
				.initX(GameField.FIELD_WIDTH/2-Ball.BALL_RADIUS)
				.initY(pad.getY()-Ball.BALL_RADIUS*2-5)
				.initColor(Ball.BALL_COLOR);
		bricks = new Brick[BRICK_ROWS][BRICK_COLUMNS];
		
		for(int i = NUM_COLORS-1; i >= 0; --i)
		{
			for(int k = 0; k < BRICK_ROWS/NUM_COLORS; ++k)
			{
				for(int j = 0; j < BRICK_COLUMNS; ++j)
				{
					bricks[i*2+k][j] = (Brick) new Brick()
					.initLevel(5-i)
					.initColor(Brick.colours[5-(i+1)])
					.initX(BRICK_X_PADDING*(j+1) + Brick.BRICK_WIDTH*j)
					.initY(BRICK_Y_PADDING*(i*2+k) + Brick.BRICK_HEIGHT*(i*2+k) +BRICK_BLOCK_OFFSET);
				}
			}
		}
		pressed = new ArrayList<Integer>();
		repaintPaddle = 0;
	}
	
	public HitEdge checkBall(GameComponent a2)
	{
		if(inside(a2, ball.getX()+ball.getWidth()/2, ball.getY() + ball.getHeight()))
			return HitEdge.BOTTOM;
		else if(inside(a2, ball.getX() +ball.getWidth()/2, ball.getY()))
			return HitEdge.TOP;
		else if(inside(a2, ball.getX(), ball.getY()+ball.getHeight()/2))
			return HitEdge.LEFT;
		else if(inside(a2, ball.getX()+ball.getWidth(), ball.getY()+ball.getHeight()/2))
			return HitEdge.RIGHT;
		else
			return HitEdge.NIL;
		
		
	}
	
	private static final boolean inside(GameComponent sprite, int x, int y)
	{
		if(sprite == null)
			return false;
		return x >= sprite.getX()-PIXEL_PADDING 
				&& x <= sprite.getX() + sprite.getWidth()+PIXEL_PADDING 
				&& y >= sprite.getY() -PIXEL_PADDING
				&& y <= sprite.getY() + sprite.getHeight()+PIXEL_PADDING; 
	}
	
	public boolean changeBallPath(HitEdge res)
	{
		if(res == HitEdge.NIL)
			return false;
		else if(res == HitEdge.TOP || res == HitEdge.BOTTOM)
			ball.toggleDown();
		else if(res == HitEdge.LEFT || res == HitEdge.RIGHT)
			ball.toggleRight();
		return true;
	}
	
	public Brick[][] getBricks()
	{
		return bricks;
	}
	
	public Paddle getPaddle()
	{
		return pad;
	}
	
	public Ball getBall()
	{
		return ball;
	}
	
	public void addKey(KeyEvent ev)
	{
		if(pressed.size() == 0)
			pressed.add(ev.getKeyCode());
	}
	
	public void removeKey(KeyEvent ev)
	{
		try{
			pressed.remove(pressed.indexOf(ev.getKeyCode()));
		}
		catch(Exception e)
		{
			
		}
	}
	
	public Brick gameloop()
	{
		Brick run = null;
		ball.move();
		for(int ev: pressed)
		{
			switch(ev)
			{
			case KeyEvent.VK_LEFT:
				pad.moveLeft();
				repaintPaddle = -1;
				break;
			case KeyEvent.VK_RIGHT:
				pad.moveRight();
				repaintPaddle = 1;
				break;
			}
		}
		if(ball.isLowerHalf())
			changeBallPath(checkBall(pad));
		else
		{
			int start = ball.isRightHalf() ? BRICK_COLUMNS/2 : 0, stop = ball.isRightHalf() ? BRICK_COLUMNS: BRICK_COLUMNS/2;
				
				
			for(Brick[] a: bricks)
			{
				for(int i = start; i < stop; ++i)
					if(changeBallPath(checkBall(a[i])))
					{
						a[i].hit();
						run = a[i];
						if(a[i].getLevel() == 0)
							a[i] = null;
						break;
					}
				if(run != null)
					break;
			}
		}
		
		return run;
		
	}

	public byte getRepaint() {
		return repaintPaddle;
	}

	public void setRepaintPaddle(byte repaintPaddle) {
		this.repaintPaddle = repaintPaddle;
	}
}
