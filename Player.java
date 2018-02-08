import java.awt.Graphics;

public class Player extends Object{
	
	//controls and conditions
	double speed;
	double xx;
	double yy;
	
	boolean collision;
	boolean up;
	boolean down;
	boolean left;
	boolean right;
	boolean canmove;
	
	public Player(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		xx = x;
		yy = y;
		speed = .3;
		canmove = true;
		
		collision = false;
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	void update() {
		y = (int) yy;
		x = (int) xx;
		
		if(up) {
			yy -= speed;
		}
		if(down) {
			yy += speed;
		}
		if(left) {
			xx -= speed;
		}
		if(right) {
			xx += speed;
		}
		
		collisionBox.setBounds((int) xx, (int) yy, width, height);
	}
	
	void draw(Graphics g) {
		super.draw(g);
		
	}
}


