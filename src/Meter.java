import java.awt.Color;
import java.awt.Graphics;

public class Meter extends Object {

	int var;
	Color c;
	public Meter(int x, int y, int width, int height, int var,Color c) {
		super();
		this.c = c;
		this.var = var;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	void update() {
		collisionBox.setBounds(x, y, width, height);
		
	}
	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(x - 2, y + 2, width + 4, -height -4);
		g.setColor(c);
		g.fillRect(x, y, width, -height);
		
		
	}
}
