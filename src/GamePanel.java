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
	int CURRENT_STATE = MENU_STATE;

	int menuInt = 0;

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

	BufferedImage battleBackground;
	Enemy flurpGator;
	Enemy purpleSlurper;
	Enemy flurpPolitique;
	Enemy bitterPuss;
	Enemy ambushBlob;
	Enemy battleEnemy;

	int spam = 0;
	int dice = 100;
	boolean bossUnlock = true;
	static boolean drawCollisionBoxes = false;

	ObjectManager menuManager = new ObjectManager();
	ObjectManager manager = new ObjectManager();
	ObjectManager combatManager = new ObjectManager();
	ObjectManager endManager = new ObjectManager();
	// loading images
	
	public static BufferedImage bitterPussImg;
	public static BufferedImage textFieldImg;
	public static BufferedImage firstBossImg;
	public static BufferedImage secondBossImg;
	public static BufferedImage thirdBossImg;
	public static BufferedImage finalBossImg;
	public static BufferedImage backgroundImg;
	public static BufferedImage playerLeftImg;
	public static BufferedImage playerRightImg;
	public static BufferedImage bootGatorImg;
	public static BufferedImage flurpPolitiqueImg;
	public static BufferedImage flurpGatorImg;
	public static BufferedImage flurpGooImg;
	public static BufferedImage purpleSlurperImg;

	public GamePanel() {

		combatPlayer = new CombatPlayer(50, 100, 10, 10, 10, 10);

		try {
			thirdBossImg = ImageIO.read(this.getClass().getResource("thirdBossFight.png"));
			bitterPussImg = ImageIO.read(this.getClass().getResource("bitterPuss.png"));
			purpleSlurperImg = ImageIO.read(this.getClass().getResource("purpleSlurper.png"));
			textFieldImg = ImageIO.read(this.getClass().getResource("rpgBackground.png"));
			backgroundImg = ImageIO.read(this.getClass().getResource("background.png"));
			playerLeftImg = ImageIO.read(this.getClass().getResource("playerLeft.png"));
			playerRightImg = ImageIO.read(this.getClass().getResource("playerRight.png"));
			bootGatorImg = ImageIO.read(this.getClass().getResource("bootGator.png"));
			firstBossImg = ImageIO.read(this.getClass().getResource("firstBossArena.png"));
			secondBossImg = ImageIO.read(this.getClass().getResource("secondBossArena.png"));
			finalBossImg = ImageIO.read(this.getClass().getResource("finalBossArena.png"));
			flurpGatorImg = ImageIO.read(this.getClass().getResource("flurpGator.png"));
			flurpGooImg = ImageIO.read(this.getClass().getResource("flurpGoo.png"));
			flurpPolitiqueImg = ImageIO.read(this.getClass().getResource("flurpPolitique.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Menu objects

		// World objects
		player = new Player(605, 425, 625, 425);
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

		// Combat objects
		flurpGator = new Enemy(100, 500, flurpGatorImg.getWidth() * 2, flurpGatorImg.getHeight() * 2, flurpGatorImg);
		purpleSlurper = new Enemy(100, 500, purpleSlurperImg.getWidth(), purpleSlurperImg.getHeight(),
				purpleSlurperImg);
		flurpPolitique = new Enemy(100, 180, flurpPolitiqueImg.getWidth(), flurpPolitiqueImg.getHeight(),
				flurpPolitiqueImg);
		bitterPuss = new Enemy(100, 450, bitterPussImg.getWidth(), bitterPussImg.getHeight(), bitterPussImg);
		ambushBlob = new Enemy(100, 500, flurpGooImg.getWidth(), flurpGooImg.getHeight(), flurpGooImg);

		combatManager.addObject(ambushBlob);
		combatManager.addObject(bitterPuss);
		combatManager.addObject(flurpGator);
		combatManager.addObject(purpleSlurper);
		combatManager.addObject(flurpPolitique);
	}

	public void doCombat(Enemy enemy, BufferedImage backdrop) {
		battleBackground = backdrop;
		battleEnemy = enemy;
		CURRENT_STATE = COMBAT_STATE;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (CURRENT_STATE == MENU_STATE) {

			if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (menuInt == 0) {
					CURRENT_STATE = WORLD_STATE;
				} else if (menuInt == 1) {

				}
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (menuInt > 0) {
					menuInt -= 1;
					System.out.println(menuInt);
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (menuInt < 1) {
					menuInt += 1;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}

		if (CURRENT_STATE == WORLD_STATE) {

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
											"Strength was upgraded by 10 points to a total of: "
													+ combatPlayer.strength);

								} else if (upgrade.equalsIgnoreCase("dexterity")) {
									combatPlayer.dexterity += 10;
									JOptionPane.showMessageDialog(null,
											"Dexterity was upgraded by 10 points to a total of: "
													+ combatPlayer.dexterity);

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
						} else {
//							JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
						}
					}
				}
			}
			if (bush2.canInteract()) {
				if (player.collisionBox.intersects(
						new Rectangle((bush2.x - 5), (bush2.y - 5), (bush2.width + 10), (bush2.height + 10)))) {
					if (e.getKeyChar() == 'e') {
						JOptionPane.showMessageDialog(null, "An enraged flurpian jumps from the bush, ready to attack");
						doCombat(ambushBlob, firstBossImg);
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

		if (CURRENT_STATE == COMBAT_STATE) {

			if (e.getKeyChar() == 'l') {
				player.center();
				player.update();
				CURRENT_STATE = WORLD_STATE;

			}
		}
		if (CURRENT_STATE == END_STATE) {

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
	}

	public void paintComponent(Graphics g) {

		if (CURRENT_STATE == MENU_STATE) {
			repaint();

		}
		if (CURRENT_STATE == WORLD_STATE) {
			repaint();
			manager.update();
			g.drawImage(backgroundImg, 0, 0, 1250, 850, null);
			manager.draw(g);

			// collision (the code would throw errors if I tried to add this to the
			// objectmanager)
			if (player.collisionBox.intersects(bootGator.collisionBox)) {
				player.collide = true;
				bootGator.canInteract = true;
			}
			if (player.collisionBox.intersects(bush1.collisionBox)) {
				player.collide = true;
			}
			if (player.collisionBox.intersects(bush2.collisionBox)) {
				player.collide = true;
				bush2.canInteract = true;
			}
			if (player.collisionBox.intersects(boss1.collisionBox)) {
				doCombat(flurpGator, firstBossImg);
			}
			if (player.collisionBox.intersects(boss2.collisionBox)) {
				doCombat(purpleSlurper, secondBossImg);
			}
			if (player.collisionBox.intersects(boss3.collisionBox)) {
				doCombat(bitterPuss, thirdBossImg);
			}
			if (player.collisionBox.intersects(finalBoss.collisionBox)) {
				player.collide = true;
				if (!bossUnlock) {
					spam += 1;
					if (spam <= 1) {
						JOptionPane.showMessageDialog(null,
								"Hey buddy, you're not quite ready for that yet, defeat the other members of Bitterleaf Co and Associates in order to enter");
					}
				} else {
					doCombat(flurpPolitique, finalBossImg);
				}
			}
			if (player.collisionBox.intersects(flurpLake.collisionBox)) {
				if (combatPlayer.hp < 100) {
					combatPlayer.hp += 1;
				}
			}

		}

		if (CURRENT_STATE == COMBAT_STATE) {
			repaint();

			g.drawImage(battleBackground, 0, -170, 1250, 850, null);
			g.drawImage(battleEnemy.getImage(), battleEnemy.x, battleEnemy.y, null);
			g.drawImage(playerLeftImg, 900, 500, playerLeftImg.getWidth(), playerLeftImg.getHeight(), null);
			g.drawImage(textFieldImg, 0, 850 - 170, null);
			combatManager.update();

		}

		if (CURRENT_STATE == END_STATE) {

		}
	}
}
