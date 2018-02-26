import java.awt.Dimension;

import javax.swing.JFrame;

public class MonsterGame {
	JFrame frame;
	GamePanel panel;
	public static final int width1 = 1250;
	public static final int height1 = 850;
	
	MonsterGame(){
		frame = new JFrame();
		panel = new GamePanel();
		frame.addKeyListener(panel);
		setup();
	}
	public static void main(String[] args) {
		MonsterGame m = new MonsterGame();
	}
	void setup() {
		frame.add(panel);
		frame.getContentPane().setPreferredSize(new Dimension(width1,height1));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}











