import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener{

	final int MENU_STATE = 0;
	final int WORLD_STATE = 1;
	final int COMBAT_STATE = 2;
	final int END_STATE = 3;
	int CURRENT_STATE = WORLD_STATE;
	
	//loading images
	public static BufferedImage player;
	
	
	
	
	
	
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
