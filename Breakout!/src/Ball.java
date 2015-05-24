import java.awt.Color;


public class Ball extends GameComponent {
	public static final int BALL_RADIUS = 7, BALL_STEP = 1;
	private boolean isMovingRight, isMovingDown, isLowerHalf, isRightHalf;
	private final byte stepDenom = 10;
	private byte stepNum;
	public static final Color BALL_COLOR = Color.black;
	private int prevX, prevY;
	
	public Ball()
	{
		width = BALL_RADIUS*2;
		height = BALL_RADIUS*2;
	}
	
	public Ball initRight(boolean slo)
	{
		isMovingRight = slo;
		return this;
	}
	
	public Ball initDown(boolean down)
	{
		isMovingDown = down;
		return this;
	}
	
	public Ball initNumerator(byte n)
	{
		stepNum = n;
		return this;
	}
	
	public void move()
	{
		prevX = x;
		prevY = y;
		if(isMovingDown)
		{
			if(y < GameField.FIELD_HEIGHT) y+=BALL_STEP;
			else GameField.gameOver();
		}
		else
		{
			if(y>0) y-=BALL_STEP;
			else toggleDown();
		}
		
		if(isMovingRight)
		{
			if(x+BALL_RADIUS*2<GameField.FIELD_WIDTH) x+=BALL_STEP;
			else toggleRight();
		}
		else
		{
			if(x>0) x-=BALL_STEP;
			else toggleRight();
		}
		if(x <GameField.FIELD_WIDTH/2) isRightHalf = false;
		else isRightHalf = true;
		
		if(y < GameField.FIELD_HEIGHT/2) isLowerHalf = false;
		else isLowerHalf = true;
	}
	
	public void toggleDown()
	{
		isMovingDown ^= true;
	}
	
	public void toggleRight()
	{
		isMovingRight ^= true;
	}

	public int getPrevX() {
		return prevX;
	}

	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}

	public int getPrevY() {
		return prevY;
	}

	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}
	
	public boolean isMovingDown() {
		return isMovingDown;
	}

	public void setMovingDown(boolean isMovingDown) {
		this.isMovingDown = isMovingDown;
	}

	public boolean isMovingRight() {
		return isMovingRight;
	}

	public void setMovingRight(boolean isMovingRight) {
		this.isMovingRight = isMovingRight;
	}

	public boolean isLowerHalf() {
		return isLowerHalf;
	}

	public void setLowerHalf(boolean isLowerHalf) {
		this.isLowerHalf = isLowerHalf;
	}

	public boolean isRightHalf() {
		return isRightHalf;
	}

	public void setRightHalf(boolean isRightHalf) {
		this.isRightHalf = isRightHalf;
	}

}
