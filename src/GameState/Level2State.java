package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Audio.AudioPlayer;
import Entity.Boss;
import Entity.Player;
import Entity.ProjectileController;
import Entity.Projectiles;
import Main.Background;

public class Level2State extends GameState {
	
	private Background bg;
	private Background bg1;
	
	private AudioPlayer pShot;
	private AudioPlayer pHit;
	private AudioPlayer eHit;
	

	private ArrayList<Boss> boss;
	private Player player;

	private long Timer1 = -1;
	private long Timer2 = -1;

	ProjectileController leftProjectiles;
	ProjectileController rightProjectiles;

	public Level2State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {

		bg = new Background("/Background/oceanbg.jpg", 1);
		bg1 = new Background("/Background/clouds.png", 1);
		
		pHit = new AudioPlayer("/SFX/playerhit1.wav");
		pShot = new AudioPlayer("/SFX/plasmashot.wav");
		eHit = new AudioPlayer("/SFX/enemyhit.wav");
		//bgMusic = new AudioPlayer("/BGMusic/bgmusic.wav");
		
		
		//bgMusic.play();

		bg.setPosition(0, -950);
		bg.setVector(0, 0.3);
		bg1.setPosition(0, -1377);
		bg1.setVector(0, 1.5);

		leftProjectiles = new ProjectileController();
		rightProjectiles = new ProjectileController();
	

		player = new Player(140, 259);
		
		boss = new ArrayList<Boss>();
		boss.add(new Boss(80, - 200, player));

		//bgMusic.stop();

	}

	@Override
	public void update() {

		if(player.x <= -5) {
			player.setPosition(-5, player.y);
		}if(player.x >= 292) {
			player.setPosition(292, player.y);
		}if(player.y >= 260) {
			player.setPosition(player.x, 260);
		}if(player.y <= 0) {
			player.setPosition(player.x, 0);
		}

		player.update();
		for(int i = 0; i < boss.size(); i++) {
			boss.get(i).moveTowards(player);
			boss.get(i).update();
		}


		isEnemyCollidingWithProjectile();
		isPlayerCollidingWithProjectiles();
		isCollidingWithPlayer();


		if(player.isDead()) {
			if(Timer2 <= -1) {
				Timer2 = System.nanoTime();
			}else {
				long elapsed2 = (System.nanoTime() - Timer2) / 1000000;
				if(elapsed2 > 4000) {
					gsm.setState(GameStateManager.GAMEOVERSTATE);
					Timer2 = -1;
				}
			}
		}

		leftProjectiles.update();
		rightProjectiles.update();



		bg.update();
		if(bg1.y >= 300) bg1.setPosition(0, -1500);
		bg1.update();

		if(boss.size() == 0) {
			if(Timer1 <= -1) {
				Timer1 = System.nanoTime();
			}else {
				long elapsed2 = (System.nanoTime() - Timer1) / 1000000;
				if(elapsed2 > 5000) {
					gsm.setState(GameStateManager.ENDSTATE);
					Timer1 = -1;
				}
			}
			
		}

		
	}


	@Override
	public void draw(Graphics g) {
		bg.draw(g);
		bg1.draw(g);
		
		if(!player.isDead()) player.draw(g);

		leftProjectiles.draw(g);
		rightProjectiles.draw(g);
		
		for(int i = 0; i < boss.size(); i++) {
			boss.get(i).draw(g);;
		}




		g.setColor(Color.GREEN);
		g.setFont(new Font("Comic Sans", Font.BOLD, 5));
		g.drawString("HP :", 255, 280);

		g.setColor(Color.GRAY);
		g.fillRect(265, 275, 50, 5);
		g.setColor(Color.GREEN);
		g.fillRect(265, 275, player.getHealth() * 10, 5);
		g.setColor(Color.BLACK);
		g.drawRect(265, 275, 50, 5);
	}

	

	public void isCollidingWithPlayer() {

		if(!player.isFlinching()) {
			
			// enemy1
			for(int i = 0; i < boss.size(); i++) {
				
				if(boss.get(i).getBounds().intersects(player.getBounds())) {
					if(!player.isFlinching() && !player.isDead()) {
						System.out.println("player is hit");
						player.hit(1);
						pHit.play();
					}
				}
			}
				
			

		}

	}

	public void isPlayerCollidingWithProjectiles() {

		if(!player.isFlinching()) {
			for(int k = 0; k < boss.size(); k++) {
				
			
			for(int i = 0; i < boss.get(k).projectiles1.b.size(); i++) {
				if(boss.get(k).projectiles1.b.get(i).getBounds().intersects(player.getBounds())) {
					boss.get(k).projectiles1.b.remove(i);
					player.hit(1);
					pHit.play();
					System.out.println("player is hit with Projectiles");

				}
			
		}

			for(int i = 0; i < boss.get(k).projectiles2.b.size(); i++) {
				if(boss.get(k).projectiles2.b.get(i).getBounds().intersects(player.getBounds())) {
					boss.get(k).projectiles2.b.remove(i);
					player.hit(1);
					pHit.play();
					System.out.println("player is hit with Projectiles");

				}
			}
			
			for(int i = 0; i < boss.get(k).projectiles3.b.size(); i++) {
				if(boss.get(k).projectiles3.b.get(i).getBounds().intersects(player.getBounds())) {
					boss.get(k).projectiles3.b.remove(i);
					player.hit(1);
					pHit.play();
					System.out.println("player is hit with Projectiles");

				}else {
					if(Timer1 <= -1) {
						Timer1 = System.nanoTime();
					}else {
						long elapsed2 = (System.nanoTime() - Timer1) / 1000000;
						if(elapsed2 > 5000) {
							boss.get(k).projectiles3.b.remove(i);
							Timer1 = -1;
						}
					}
				}
			}
			}
			
		}


	}


	public void isEnemyCollidingWithProjectile() {


		// boss collision
		
		for(int i = 0; i < boss.size(); i++) {


			for(int j = 0; j < leftProjectiles.b.size(); j++) {
				if(leftProjectiles.b.get(j).getBounds().intersects(boss.get(i).getBounds())) {
					boss.get(i).hit(1);
					leftProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");

				}
			}
			for(int j = 0; j < rightProjectiles.b.size(); j++) {
				if(rightProjectiles.b.get(j).getBounds().intersects(boss.get(i).getBounds())) {
					boss.get(i).hit(1);
					rightProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");
				}
			}

			if(boss.get(i).isDead()) {
				boss.remove(i);
			}
		}




	}


	@Override
	public void keyPressed(int k) {
		
		if(!player.isDead()) {

			if(k == KeyEvent.VK_LEFT) player.moveLeft(true);
			if(k == KeyEvent.VK_RIGHT) player.moveRight(true);
			if(k == KeyEvent.VK_UP) player.moveUp(true);
			if(k == KeyEvent.VK_DOWN) player.moveDown(true);
		}

	}

	@Override
	public void keyReleased(int k) {
		
		if(!player.isDead()) {
			if(k == KeyEvent.VK_LEFT) player.moveLeft(false);
			if(k == KeyEvent.VK_RIGHT) player.moveRight(false);
			if(k == KeyEvent.VK_UP) player.moveUp(false);
			if(k == KeyEvent.VK_DOWN) player.moveDown(false);

			if(k == KeyEvent.VK_SPACE) {

				pShot.stop();
				leftProjectiles.addProjectile(new Projectiles(player.x - 5, player.y, 1));
				rightProjectiles.addProjectile(new Projectiles(player.x + 24, player.y, 1));
				pShot.play();

			}
		}
	}

	


}
