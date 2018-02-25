import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	final int MENU_STATE = 0;
	final int WORLD_STATE = 1;
	final int COMBAT_STATE = 2;
	final int END_STATE = 3;
	int CURRENT_STATE = WORLD_STATE;

	Player player;
	CombatPlayer combatPlayer;
	Meter health;
	Barrier bootGator;
	Barrier bush1;
	Barrier bush2;
	Barrier flurpLake;

	Barrier boss1;
	Barrier boss2;
	Barrier boss3;
	Barrier finalBoss;

	int spam = 0;
	int dice = 100;
	boolean bossUnlock = false;
	static boolean drawCollisionBoxes = false;

	ObjectManager manager = new ObjectManager();
	ObjectManager combatManager = new ObjectManager();
	// loading images
	public static BufferedImage backgroundImg;
	public static BufferedImage playerLeftImg;
	public static BufferedImage playerRightImg;
	public static BufferedImage bootGatorImg;

	public GamePanel() {

		combatPlayer = new CombatPlayer(50, 100, 10, 10, 10, 10);

		String burialGift = JOptionPane.showInputDialog(
				"What is your most respectable attribute? Strength, Intelligence, Dexterity, or Luck?");
		if (burialGift.equalsIgnoreCase("Strength")) {
			combatPlayer.strength = 20;
		} else if (burialGift.equalsIgnoreCase("Intelligence")) {
			combatPlayer.intelligence = 20;
		} else if (burialGift.equalsIgnoreCase("Dexterity")) {
			combatPlayer.dexterity = 20;
		} else if (burialGift.equalsIgnoreCase("Luck")) {
			combatPlayer.luck = 20;
		}
		try {
			backgroundImg = ImageIO.read(this.getClass().getResource("background.png"));
			playerLeftImg = ImageIO.read(this.getClass().getResource("playerLeft.png"));
			playerRightImg = ImageIO.read(this.getClass().getResource("playerRight.png"));
			bootGatorImg = ImageIO.read(this.getClass().getResource("bootGator.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		player = new Player(0, 0, 625, 425);

		bootGator = new Barrier(380, 280, (bootGatorImg.getWidth()) / 2, (bootGatorImg.getHeight()) / 2, bootGatorImg);
		bush1 = new Barrier(960, 110, 130, 100, null);
		bush2 = new Barrier(940, 510, 140, 100, null);
		flurpLake = new Barrier(170, 520, 260, 300, null);
		health = new Meter(50, 1200, 40, 100, combatPlayer.hp);
		boss1 = new Barrier(1230, 300, 20, 200, null);
		boss2 = new Barrier(500, 830, 250, 20, null);
		boss3 = new Barrier(0, 300, 20, 200, null);
		finalBoss = new Barrier(500, 0, 250, 20, null);

		manager.addObject(boss1);
		manager.addObject(boss2);
		manager.addObject(boss3);
		manager.addObject(finalBoss);
		manager.addObject(health);
		manager.addObject(flurpLake);
		manager.addObject(bootGator);
		manager.addObject(player);
		manager.addObject(bush1);
		manager.addObject(bush2);

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = true;
			// System.out.println("up key detected");
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}

		if (bootGator.canInteract()) {
			if (player.collisionBox.intersects(new Rectangle(375, 275, ((bootGatorImg.getWidth()) / 2) + 10,
					((bootGatorImg.getHeight()) / 2) + 10))) {
				if (e.getKeyChar() == 'e') {
					int response = JOptionPane.showConfirmDialog(null,
							"Hey buddy, would you be willing to exchange 100 of those fine dice for a stat upgrade?");
					if (response == JOptionPane.YES_OPTION) {
						if (dice >= 100) {
							dice -= 100;
							String upgrade = JOptionPane.showInputDialog(
									"What skill would you like to upgrade? Strength, Intelligence, Dexterity, or Luck?");
							if (upgrade.equalsIgnoreCase("strength")) {
								combatPlayer.strength += 10;
								JOptionPane.showMessageDialog(null,
										"Strength was upgraded by 10 points to a total of: " + combatPlayer.strength);

							} else if (upgrade.equalsIgnoreCase("dexterity")) {
								combatPlayer.dexterity += 10;
								JOptionPane.showMessageDialog(null,
										"Dexterity was upgraded by 10 points to a total of: " + combatPlayer.dexterity);

							} else if (upgrade.equalsIgnoreCase("intelligence")) {
								combatPlayer.intelligence += 10;
								JOptionPane.showMessageDialog(null,
										"Intelligence was upgraded by 10 points to a total of: "
												+ combatPlayer.intelligence);

							} else if (upgrade.equalsIgnoreCase("luck")) {
								combatPlayer.luck += 10;
								JOptionPane.showMessageDialog(null,
										"Luck was upgraded by 10 points to a total of: " + combatPlayer.luck);
							} else if (upgrade.equalsIgnoreCase("danteisawesome")) {
								combatPlayer.luck += 100;
								combatPlayer.strength += 100;
								combatPlayer.dexterity += 100;
								combatPlayer.intelligence += 100;
								JOptionPane.showMessageDialog(null,
										"Excellent choice, handsome devil, all stats upgraded by 100");
							} else {
								dice += 100;
								JOptionPane.showMessageDialog(null,
										"I don't know quite what that is you typed, but if you plan on learning how to speak, come back some time!");
							}
						} else {
							JOptionPane.showMessageDialog(null,
									"get out of my shop, you don't even have enough dice you HOOLIGAN!");

						}
					}
				}
			}
		}

		if (e.getKeyChar() == 'p') {
			if (drawCollisionBoxes) {
				drawCollisionBoxes = false;
			} else {
				drawCollisionBoxes = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			player.up = false;
			// System.out.println("up key released");
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			player.down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (CURRENT_STATE == WORLD_STATE) {

		}

		System.out.println("action preformed");
	}

	public void paintComponent(Graphics g) {

		if (CURRENT_STATE == WORLD_STATE) {
			repaint();
			manager.update();

			if (player.collisionBox.intersects(bootGator.collisionBox)) {
				player.collide = true;
				bootGator.canInteract = true;
			}
			if (player.collisionBox.intersects(bush1.collisionBox)) {
				player.collide = true;
			}
			if (player.collisionBox.intersects(bush2.collisionBox)) {
				player.collide = true;
			}
			if (player.collisionBox.intersects(boss1.collisionBox)) {

			}
			if (player.collisionBox.intersects(boss2.collisionBox)) {

			}
			if (player.collisionBox.intersects(boss3.collisionBox)) {

			}
			if (player.collisionBox.intersects(finalBoss.collisionBox)) {
				player.collide = true;
				if (!bossUnlock) {
					spam += 1;
					if (spam <= 1) {
						JOptionPane.showMessageDialog(null,
								"Hey buddy, you're not quite ready for that yet, defeat the other members of Bitterleaf Co and Associates in order to enter");
					}
				}
			}
			if (player.collisionBox.intersects(flurpLake.collisionBox)) {
				if (combatPlayer.hp < 100) {
					combatPlayer.hp += 1;
				}
			}
			g.drawImage(backgroundImg, 0, 0, 1250, 850, null);
			manager.draw(g);
		}
		if (CURRENT_STATE == COMBAT_STATE) {
			repaint();
			combatManager.update();
			combatManager.draw(g);
		}
	}
}
