import java.awt.Color;
import javax.swing.JFrame;


public class RunningWindow extends JFrame {

	private static final long serialVersionUID = -2445985173657810844L;
	public static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 700;
	private GameField panel;
	public RunningWindow(){
		// TODO Auto-generated constructor stub
		panel = new GameField(this);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setBackground(Color.black);
        setTitle("Breakout!");
		add(panel);
		setVisible(true);
	}

	public static void main(String[] args) {
		Thread gameThread = new Thread(new Runnable()
		{
			public void run() {
				new RunningWindow();
			}
		
		});
		gameThread.start();
		try {
			synchronized(gameThread)
			{
				gameThread.wait();
				gameThread.interrupt();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println(true);
		}

	}
}
