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

	boolean myTurn = true;

	int pepperSpray = 5;
	int chugJugs = 1;
	int spam = 0;
	int awardedDice;
	int dice = 100;
	boolean boss1defeat = false;
	boolean boss2defeat = false;
	boolean boss3defeat = false;
	boolean bossUnlock = false;
	static boolean drawCollisionBoxes = false;

	ObjectManager menuManager = new ObjectManager();
	ObjectManager manager = new ObjectManager();
	ObjectManager combatManager = new ObjectManager();
	ObjectManager endManager = new ObjectManager();
	
	BufferedImage finishImage;
	// loading images

	public static BufferedImage happyEnding;
	public static BufferedImage justice;
	public static BufferedImage menuBackground;
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

	public static void main(String[] args) {

	}

	public GamePanel() {

		combatPlayer = new CombatPlayer(50, 100, 10, 10, 10, 10);

		try {
			happyEnding = ImageIO.read(this.getClass().getResource("happyEnding.png"));
			justice = ImageIO.read(this.getClass().getResource("justice.png"));
			menuBackground = ImageIO.read(this.getClass().getResource("menuBackground.png"));
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
		flurpGator = new Enemy(100, 670, flurpGatorImg.getWidth() * 2, flurpGatorImg.getHeight() * 2, flurpGatorImg);
		purpleSlurper = new Enemy(100, 670, purpleSlurperImg.getWidth(), purpleSlurperImg.getHeight(),
				purpleSlurperImg);
		flurpPolitique = new Enemy(100, 350, flurpPolitiqueImg.getWidth(), flurpPolitiqueImg.getHeight(),
				flurpPolitiqueImg);
		bitterPuss = new Enemy(100, 620, bitterPussImg.getWidth(), bitterPussImg.getHeight(), bitterPussImg);
		ambushBlob = new Enemy(100, 670, flurpGooImg.getWidth(), flurpGooImg.getHeight(), flurpGooImg);

		combatManager.addObject(ambushBlob);
		combatManager.addObject(bitterPuss);
		combatManager.addObject(flurpGator);
		combatManager.addObject(purpleSlurper);
		combatManager.addObject(flurpPolitique);
	}

	public void doCombat(Enemy enemy, BufferedImage backdrop) {
		battleBackground = backdrop;
		awardedDice = 100;
		battleEnemy = enemy;
		CURRENT_STATE = COMBAT_STATE;
	}

	int friendCounters = 0;

	public int giveOptions() {
		if(CURRENT_STATE == COMBAT_STATE) {
		int ans = 0;
		int skill = 0;
		String[] skills = { "Magic missle", "Pepper spray", "Can of purple flurp", "Chug jug", "BACK" };
		String[] options = { "Fight", "Friend", "Item/Skill","Flee", "Check Enemy Stats" };
		ans = JOptionPane.showOptionDialog(null, "What will you do?", battleEnemy.name + " attacks!",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		// attack
		if (myTurn) {
			if (ans == 0) {
				battleEnemy.hp -= ((combatPlayer.strength) - battleEnemy.physResist);
				JOptionPane.showMessageDialog(null, "you attacked and did "
						+ ((combatPlayer.dexterity) + (combatPlayer.strength) - battleEnemy.physResist) + " damage!");
			} else if (ans == 1) {
				friendCounters += 1;
				if (friendCounters == battleEnemy.attack) {
					JOptionPane.showMessageDialog(null,
							battleEnemy.name + " has discovered the real name of life, they decide to be your friend");
					player.center();
					player.update();
					CURRENT_STATE = WORLD_STATE;
				}
			} else if (ans == 2) {
				skill = JOptionPane.showOptionDialog(null, "What item/skill will you use", "Items and skills",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, skills, null);
				if (skill == 0) {
					combatPlayer.juice -= 10;
					battleEnemy.hp -= (combatPlayer.intelligence) * 2;
					JOptionPane.showMessageDialog(null,
							"Did " + ((2 * combatPlayer.intelligence) - battleEnemy.magResist) + " damage!");
				} else if (skill == 1) {
					pepperSpray -= 1;
					battleEnemy.hp -= ((combatPlayer.intelligence) + combatPlayer.strength);
					JOptionPane.showMessageDialog(null,
							"Did " + (combatPlayer.intelligence + combatPlayer.strength) + " damage!");
				} else if (skill == 2) {
					if (combatPlayer.hp < 100) {
						combatPlayer.hp += 20;
						JOptionPane.showMessageDialog(null, "HP increased by 20");
					} else {
						JOptionPane.showMessageDialog(null, "You can't use that!");
					}
				} else if (skill == 3) {
					chugJugs -= 1;
					combatPlayer.hp = 200;
					combatPlayer.juice = 200;
					JOptionPane.showMessageDialog(null, "HP and juice set to 200");
				} else {
					giveOptions();
				}

			} else if (ans == 3) {
				JOptionPane.showMessageDialog(null, "You fled FlurpTopia for good, so long sucker");
				System.exit(0);

			} else {
				JOptionPane.showMessageDialog(null,
						"HP: " + battleEnemy.hp + " \nAttack: " + battleEnemy.attack + " \nMagic Resistance: "
								+ battleEnemy.magResist + " \nPhysical Resistance: " + battleEnemy.physResist);
				
			}
		}
		return ans;
		}
		return -1;
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
								"Hey buddy, would you be willing to exchange 100 of those fine dice for a stat upgrade? \nYou currently have "
										+ dice + " dice.");
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
									combatPlayer.hp = 1000;
									combatPlayer.juice = 1000;
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
							// JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
						}
					}
				}
			}
			if (bush2.canInteract()) {
				if (player.collisionBox.intersects(
						new Rectangle((bush2.x - 5), (bush2.y - 5), (bush2.width + 10), (bush2.height + 10)))) {
					if (e.getKeyChar() == 'e') {
						JOptionPane.showMessageDialog(null, "An enraged flurpian jumps from the bush, ready to attack");
						ambushBlob.giveStats(40, 7, 3, 4, "Angered Flurp Blob");
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

			while (battleEnemy.hp > 0) {
				if (myTurn) {
					if (giveOptions() == 4) {
						
					} else if (giveOptions() == 2) {
						player.center();
						player.update();
						CURRENT_STATE = WORLD_STATE;
						myTurn = false;
				}else {
						
						myTurn = false;
					}
				} else {
					if ((int) (Math.random() * 10) % 7 != 0) {
						combatPlayer.hp -= battleEnemy.attack;
						JOptionPane.showMessageDialog(null, "You took " + battleEnemy.attack + " damage!");
						myTurn = true;
						if(combatPlayer.hp<=0) {
							JOptionPane.showMessageDialog(null,"you died lol you suck");
							System.exit(0);
						}
					} else {
						combatPlayer.hp -= battleEnemy.attack * 2;
						JOptionPane.showMessageDialog(null,
								"You took " + battleEnemy.attack * 2 + " damage! Critical hit!");
						if(combatPlayer.hp <= 0) {
							JOptionPane.showMessageDialog(null,"you died lol you suck");
							System.exit(0);
						}
						myTurn = true;
					}
				}
			}
			
			if(battleEnemy.hp <= 0 && battleEnemy == flurpPolitique) {
				String[] options = {"Spare","Kill"};
				int x = JOptionPane.showOptionDialog(null, "Spare him?", "Spare him?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
				if(x == 0) {
					finishImage = happyEnding;
							CURRENT_STATE = END_STATE;
				} else if( x == 1) {
					finishImage = justice;
							CURRENT_STATE = END_STATE;
				} else {
					System.exit(0);
				}
			}
			JOptionPane.showMessageDialog(null, "You WON! you got " + awardedDice + " dice!");
			if (battleEnemy == flurpGator) {
				boss1defeat = true;
			} else if (battleEnemy == bitterPuss) {
				boss2defeat = true;
			} else if (battleEnemy == purpleSlurper) {
				boss3defeat = true;
			}
			dice += awardedDice;
			player.center();
			player.update();
			CURRENT_STATE = WORLD_STATE;
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
			g.drawImage(menuBackground, 0, 0, null);

		}
		if (CURRENT_STATE == WORLD_STATE) {
			repaint();
			manager.update();
			g.drawImage(backgroundImg, 0, 0, 1250, 850, null);
			manager.draw(g);

			if(boss1defeat && boss2defeat && boss3defeat) {
				if(spam<0) {
					spam+=1;
				JOptionPane.showMessageDialog(null, "The first three members of Bitterleaf Co have been disposed of, only the Flurp Politique remains, destroy him");
				}
				CURRENT_STATE = WORLD_STATE;
				bossUnlock = true;
			}
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
				myTurn = true;
				flurpGator.giveStats(50, 15, 3, 3, "Flurp Gator");
			}
			if (player.collisionBox.intersects(boss2.collisionBox)) {
				doCombat(purpleSlurper, secondBossImg);
				myTurn = true;
				purpleSlurper.giveStats(80 , 12, 4,4,"The Purple Slurper");
			}
			if (player.collisionBox.intersects(boss3.collisionBox)) {
				doCombat(bitterPuss, thirdBossImg);
				myTurn = true;
				bitterPuss.giveStats(140, 10, 2, 2, "Bitter Puss");
			}
			if (player.collisionBox.intersects(finalBoss.collisionBox)) {
				player.collide = true;
				if(bossUnlock) {
					doCombat(flurpPolitique, finalBossImg);
					myTurn = true;
					flurpPolitique.giveStats(200, 20, 4, 5, "Flurp Politique");
				}
			}
			if (player.collisionBox.intersects(flurpLake.collisionBox)) {
				if (combatPlayer.hp < 100) {
					combatPlayer.hp += 1;
				}
				if (combatPlayer.juice < 100) {
					combatPlayer.juice += 1;
				}
			}

		}

		if (CURRENT_STATE == COMBAT_STATE) {
			repaint();

			g.drawImage(battleBackground, 0, 0, 1250, 850, null);
			g.drawImage(battleEnemy.getImage(), battleEnemy.x, battleEnemy.y, null);
			g.drawImage(playerLeftImg, 900, 670, playerLeftImg.getWidth(), playerLeftImg.getHeight(), null);

			combatManager.update();

		}

		if (CURRENT_STATE == END_STATE) {
			g.drawImage(finishImage, 0,0, null);
		}
	}
}
