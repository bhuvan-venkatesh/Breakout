import java.awt.Color;


public abstract class GameComponent {
	protected int x, y, height, width;
	protected Color color;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public GameComponent initX(int x)
	{
		this.x = x;
		return this;
	}
	
	public GameComponent initY(int y)
	{
		this.y = y;
		return this;
	}
	
	public GameComponent initColor(Color col)
	{
		this.color = col;
		return this;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	

}
