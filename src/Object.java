import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Object {
	Rectangle collisionBox;
	BufferedImage img;
	int x;
	int y;
	int width;
	int height;
	public boolean isAlive;
	
	public Object()
	{
		isAlive = true;
		collisionBox = new Rectangle(x,y,width,height);
	}
	void update(){
		collisionBox.setBounds(x,y,width,height);
	}
	void draw(Graphics g) {
		
	}
	
	
}
