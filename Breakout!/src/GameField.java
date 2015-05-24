import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GameField extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AbstractGameField field = new AbstractGameField();
	public static final int FIELD_WIDTH = 480, FIELD_HEIGHT = 640, TIME_DELAY = 10, CUSHION = 5;
	private Timer time;
	private JButton pause;
	private KeyListener list;
	public static final Color back = Color.white;
	private static RunningWindow ref;

	public GameField(RunningWindow a) {
		// TODO Auto-generated constructor stub
		pause = new JButton("PAUSE");
		pause.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				toggleTimer();
			}
			
		});
		ref = a;
		add(pause);
		setSize(FIELD_WIDTH, FIELD_HEIGHT);
		setFocusable(true);
		setDoubleBuffered(true);
		setBackground(back);
		list = new KeyListener()
		{

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				field.addKey(arg0);
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				field.removeKey(arg0);
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		};
		addKeyListener(list);
		time = new Timer(TIME_DELAY, new GameCommand());
		time.start();
	}
	public static void gameOver()
	{
		ref.dispatchEvent(new WindowEvent(ref, WindowEvent.WINDOW_CLOSING));
	}
	
	private void toggleTimer()
	{
		if(time.isRunning()) time.stop();
		else 
		{
			time.start();
			this.removeKeyListener(list);
			this.addKeyListener(list);
		}
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintSprite(g, field.getBall());
		paintSprite(g, field.getPaddle());
		for(Brick[] a: field.getBricks())
			for(Brick b: a)
				paintSprite(g, b);
	}
	
	public void paintSprite(Graphics g, GameComponent c, Color a)
	{
		g.setColor(a);
		if(c instanceof Ball)
		{
			g.drawOval(c.getX(), c.getY(), Ball.BALL_RADIUS*2, Ball.BALL_RADIUS*2);
			g.fillOval(c.getX(), c.getY(), Ball.BALL_RADIUS*2, Ball.BALL_RADIUS*2);
			return;
		}
		
		int width = 0, height = 0;
		if(c instanceof Paddle)
		{
			width = Paddle.PADDLE_WIDTH;
			height = Paddle.PADDLE_HEIGHT;
		}
		else if(c instanceof Brick)
		{
			width = Brick.BRICK_WIDTH;
			height = Brick.BRICK_HEIGHT;
		}
		g.drawRect(c.getX(), c.getY(), width, height);
		g.fillRect(c.getX(), c.getY(), width, height);
	}
	
	public void paintSprite(Graphics g, GameComponent c)
	{
		paintSprite(g, c, c.getColor());
	}
	
	public void repaintPaddle(Paddle p, byte b)
	{
		Graphics g = getGraphics();
		paintSprite(g, p);
		g.setColor(back);
		if(b == 1)
		{
			g.drawRect(p.getX()-Paddle.PADDLE_STEP*b-CUSHION, p.getY(), Paddle.PADDLE_STEP+CUSHION, Paddle.PADDLE_HEIGHT);
			g.fillRect(p.getX()-Paddle.PADDLE_STEP*b-CUSHION, p.getY(), Paddle.PADDLE_STEP+CUSHION, Paddle.PADDLE_HEIGHT);
		}
		else
		{
			g.drawRect(p.getX()+Paddle.PADDLE_WIDTH, p.getY(), Paddle.PADDLE_STEP+CUSHION, Paddle.PADDLE_HEIGHT);
			g.fillRect(p.getX()+Paddle.PADDLE_WIDTH, p.getY(), Paddle.PADDLE_STEP+CUSHION, Paddle.PADDLE_HEIGHT);
		}
	}
	
	public void repaintBall(Ball b)
	{
		Graphics g = getGraphics();
		g.setColor(Color.white);
		g.drawOval(b.getPrevX(), b.getPrevY(), Ball.BALL_RADIUS*2, Ball.BALL_RADIUS*2);
		g.fillOval(b.getPrevX(), b.getPrevY(), Ball.BALL_RADIUS*2, Ball.BALL_RADIUS*2);
		paintSprite(g, b);
		
	}
	
	private class GameCommand implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Brick a = field.gameloop();
			repaintBall(field.getBall());
			byte n = field.getRepaint();
			if(n != 0)
			{
				repaintPaddle(field.getPaddle(), n);
				field.setRepaintPaddle((byte)0);
			}
			if(a != null)
			{
				paintSprite(getGraphics(), a);
				if(a.getLevel() == 0)
					a = null;
			}
		}
	}
}
