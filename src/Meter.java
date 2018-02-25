import java.awt.Graphics;

public class Meter extends Object{

	int amnt;
	public Meter(int x, int y, int width, int max, int base) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = max;
		amnt = base;
	}
	void draw(Graphics g)
	{
		g.drawRect(x, y, width, height);
		
		g.fillRect(x,y,width,amnt);
		if(amnt < 0)
		{
			amnt = 0;
		}
	}
}
