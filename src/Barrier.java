import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Barrier extends Object {
	boolean canInteract;

	Barrier(int x, int y, int width, int height, BufferedImage img) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	void draw(Graphics g) {
		g.drawImage(img, x, y, width, height, null);
		if (GamePanel.drawCollisionBoxes) {
			g.drawRect(collisionBox.x, collisionBox.y, collisionBox.width, collisionBox.height);
		}
	}

	boolean canInteract() {
		return this.canInteract;
	}
}
