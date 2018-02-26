import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy extends Object {

	String name;
	int hp;
	int attack;
	int magResist;
	int physResist;

	BufferedImage sprite;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}

	void draw(Graphics g) {
		super.draw(g);
		g.drawImage(sprite, x, y, null);
	}

	void giveStats(int hp, int attack, int magResist, int physResist,String name) {
		this.hp = hp;
		this.attack = attack;
		this.magResist = magResist;
		this.physResist = physResist;
		this.name = name;
	}

	public String returnStats(){
		return "HP: " + hp + " \nAttack: " + attack + " \nMagic Resistance: " + magResist + " \nPhysical Resistance: " + physResist;
	}
	BufferedImage getImage() {
		return sprite;
	}
}
