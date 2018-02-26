import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

public class Player extends Object{
	
	int playerHeight = (GamePanel.playerRightImg.getHeight())/2;
	int playerWidth = (GamePanel.playerRightImg.getWidth())/2;
	BufferedImage currentImg = GamePanel.playerRightImg;
	
	double speed;
	double dx;
	double dy;
	
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	boolean collide;
	
	

	
	public Player(int x, int y, int width, int height)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	
		dx = x;
		dy = y;
		speed = 0.05;
		
		up = false;
		down = false;
		left = false;
		right = false;
		
		
	}
	
	void update()
	{
		if(!collide)
		{
		x = (int) dx;
		y = (int) dy;
		} else {
			dy = y;
			dx = x;
			collide = false;
		}
		if(up)
		{
		dy-=speed;
//		System.out.println("y: " + dy);
		}
		if(down)
		{
		dy+=speed;
		}
		if(left)
		{
		currentImg = GamePanel.playerLeftImg;
		dx-=speed;
		}
		if(right)
		{
		currentImg = GamePanel.playerRightImg;
		dx+=speed;
		}
		collisionBox.setBounds((int) dx + playerWidth/4, (int) dy, playerWidth/2, playerHeight);
	}
	void center()
	{
		dx = 605;
		dy = 425;
	}
	void draw(Graphics g)
	{
		g.drawImage(currentImg,x,y,playerWidth,playerHeight,null);
		if(GamePanel.drawCollisionBoxes)
		{
		g.drawRect(collisionBox.x, collisionBox.y, collisionBox.width ,collisionBox.height);
		}
		//		g.drawRect(x, y, playerWidth , playerHeight);
	}
}
