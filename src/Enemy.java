import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends Object{

	BufferedImage sprite;
	public Enemy (int x, int y, int width, int height, BufferedImage sprite)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	void draw(Graphics g)
	{
		super.draw(g);
		g.drawImage(sprite, x, y, null);
	}
}
