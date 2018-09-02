package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import Audio.AudioPlayer;
import Main.Background;
import Entity.Enemy1;
import Entity.Enemy2;
import Entity.Enemy3;
import Entity.Projectiles;
import Entity.ProjectileController;
import Entity.Player;


public class Level1State extends GameState {

	private Background bg;
	private Background bg1;
	
	private AudioPlayer pShot;
	private AudioPlayer pHit;
	private AudioPlayer eHit;
	//private AudioPlayer bgMusic;

	private Player player;
	private ArrayList<Enemy1> enemy1 = new ArrayList<Enemy1>();
	private ArrayList<Enemy2> enemy2 = new ArrayList<Enemy2>();
	private ArrayList<Enemy3> enemy3 = new ArrayList<Enemy3>();
	private long Timer1;
	private long Timer2;
	private long Timer3;
	private long Timer4 = -1;
	private long Timer5 = -1;
	private long gameTimer;
	private long gameElapsed = 30;
	private int converter = 1000000000;
	private String timeLeft = "";

	ProjectileController leftProjectiles;
	ProjectileController rightProjectiles;

	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
		
		gameTimer = System.nanoTime();
	}

	@Override
	public void init() {

		bg = new Background("/Background/mapbg1.jpg", 1);
		bg1 = new Background("/Background/clouds.png", 1);
		
		//bgMusic = new AudioPlayer("/BGMusic/bgmusic.wav");
		pHit = new AudioPlayer("/SFX/playerhit1.wav");
		pShot = new AudioPlayer("/SFX/plasmashot.wav");
		eHit = new AudioPlayer("/SFX/enemyhit.wav");

		//bgMusic.play();
		
		bg.setPosition(0, -1215);
		bg.setVector(0, 0.3);
		bg1.setPosition(0, -1377);
		bg1.setVector(0, 1.5);

		leftProjectiles = new ProjectileController();
		rightProjectiles = new ProjectileController();
		
		player = new Player(140, 259);
		enemy1 = new ArrayList<Enemy1>();
		enemy2 = new ArrayList<Enemy2>();
		enemy3 = new ArrayList<Enemy3>();
		
		//bgMusic.stop();
	}

	@Override
	public void update() {

		if(!timeLeft.equals("0 : 00")) {
			if(player.x <= -5) {
				player.setPosition(-5, player.y);
			}if(player.x >= 292) {
				player.setPosition(292, player.y);
			}if(player.y >= 260) {
				player.setPosition(player.x, 260);
			}if(player.y <= 0) {
				player.setPosition(player.x, 0);
			}

			enemySpawn1();
			enemySpawn2();
			enemySpawn3();


			isEnemyCollidingWithProjectile();
			isPlayerCollidingWithProjectiles();
			isCollidingWithPlayer();


			if(!player.isDead()) {
				player.update();
			}
			// crahs here
			leftProjectiles.update();
			rightProjectiles.update();


			bg.update();
			if(bg1.y >= 300) bg1.setPosition(0, -1500);
			bg1.update();


			for(int i = 0; i < enemy1.size(); i++) {
				enemy1.get(i).moveTowards(player);
				enemy1.get(i).update();
			}
			for(int i = 0; i < enemy2.size(); i++) {
				enemy2.get(i).moveTowards(player);
				enemy2.get(i).update();
			}
			for(int i = 0; i < enemy3.size(); i++) {
				enemy3.get(i).update();
			}




			if(player.isDead()) {
				if(Timer5 <= -1) {
					Timer5 = System.nanoTime();
				}else {
					long elapsed2 = (System.nanoTime() - Timer5) / 1000000;
					if(elapsed2 > 5000) {
						gsm.setState(GameStateManager.GAMEOVERSTATE);
					}
				}
			}
				


			gameTimerFormater();
			
		} else {
			
			
			enemy1 = new ArrayList<Enemy1>();
			enemy2 = new ArrayList<Enemy2>();
			enemy3 = new ArrayList<Enemy3>(); 
			leftProjectiles = new ProjectileController();
			rightProjectiles = new ProjectileController();
			
			
			bg.update();
			bg1.update();
			
			if(Timer4 <= -1) {
				Timer4 = System.nanoTime();
			}else {
				long elapsed = (System.nanoTime() - Timer4) / 1000000;
				if(elapsed > 2000) {
					player.update2();
				}
			}
			
			if(Timer5 <= -1) {
				Timer5 = System.nanoTime();
			}else {
				long elapsed2 = (System.nanoTime() - Timer5) / 1000000;
				if(elapsed2 > 8000) {
					gsm.setState(GameStateManager.LEVEL2STATE);
				}
			}
			 
			
			
			
		}
		
	}


	@Override
	public void draw(Graphics g) {
		bg.draw(g);
		bg1.draw(g);
		
		if(!player.isDead()) {
			player.draw(g);
		}

		leftProjectiles.draw(g);
		rightProjectiles.draw(g);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 10));
		g.drawString(timeLeft, 270, 290);

		for(int i = 0; i < enemy1.size(); i++) {
			enemy1.get(i).draw(g);
		}

		for(int i = 0; i < enemy2.size(); i++) {
			enemy2.get(i).draw(g);
		}

		for(int i = 0; i < enemy3.size(); i++) {
			enemy3.get(i).draw(g);
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

	
	private String gameTimerFormater() {
		if(!timeLeft.equals("0 : 00")) {
			timeLeft = "";

			timeLeft += (gameElapsed - ((System.nanoTime() - gameTimer) / converter)) / 60 + " : ";

			if(((((gameElapsed - ((System.nanoTime() - gameTimer) / converter)))) % 60) < 10)
				timeLeft += "0" + (((gameElapsed - ((System.nanoTime() - gameTimer) / converter)))) % 60;
			else
				timeLeft += (((gameElapsed - ((System.nanoTime() - gameTimer) / converter)))) % 60;

			//System.out.println(timeLeft);

		}
		return timeLeft;
		
	}
	
	public void enemySpawn1() {

		if(enemy1.size() == 0) {

			if(Timer1 <= -1) {
				Timer1 = System.nanoTime();
			}else {
				long elapsed = (System.nanoTime() - Timer1) / 1000000;
				if(elapsed > 5000) {
					enemy1.add(new Enemy1(70, -200));
					Timer1 = -1;
				}
			}
		}

	}

	public void enemySpawn2() {
		
		if(enemy2.size() == 0) {

			if(Timer2 <= -1) {
				Timer2 = System.nanoTime();
			}else {
				long elapsed = (System.nanoTime() - Timer2) / 1000000;
				if(elapsed > 5000) {
					for(int i = 0; i < 3; i++) {
						enemy2.add(new Enemy2(i * 130, -200));
					}
					Timer2 = -1;
				}
			}
		}

	}

	public void enemySpawn3() {

		for(int i = 0; i < enemy3.size(); i++) {
			if(enemy3.get(i).y > 400)
				enemy3.remove(i);

		}

		if(enemy3.size() == 0) {

			if(Timer3 <= -1) {
				Timer3 = System.nanoTime();
			}else {
				long elapsed = (System.nanoTime() - Timer3) / 1000000;
				if(elapsed > 5000) {
					for(int i = 0; i < 6; i++) {
						enemy3.add(new Enemy3(i * 54, -200));
					}
					Timer3 = -1;
				}
			}
		}

	}

	public void isCollidingWithPlayer() {

		if(!player.isFlinching()) {
			
			// enemy1
			for(int i = 0; i < enemy1.size(); i++) {
				if(enemy1.get(i).getBounds().intersects(player.getBounds())) {
					if(!player.isFlinching() && !player.isDead()) {
						System.out.println("player is hit");
						player.hit(1);
						pHit.play();
					}
				}
			}

			// enemy2
			for(int i = 0; i < enemy2.size(); i++) {
				if(enemy2.get(i).getBounds().intersects(player.getBounds())) {
					if(!player.isFlinching() && !player.isDead()) {
						System.out.println("player is hit");
						player.hit(1);
						pHit.play();
					}
				}
			}


			// enemy3
			for(int i = 0; i < enemy3.size(); i++) {
				if(enemy3.get(i).getBounds().intersects(player.getBounds())) {
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
			
			
			for(int i = 0; i < enemy1.size(); i++) {
				for(int j = 0; j < enemy1.get(i).projectiles.b.size(); j++) {
					if(enemy1.get(i).projectiles.b.get(j).getBounds().intersects(player.getBounds())) {
						enemy1.get(i).projectiles.b.remove(j);
						player.hit(1);
						pHit.play();
						System.out.println("player is hit with Projectiles");

					}
					
				}
			}


			for(int i = 0; i < enemy2.size(); i++) {
				for(int j = 0; j < enemy2.get(i).projectiles.b.size(); j++) {
					if(enemy2.get(i).projectiles.b.get(j).getBounds().intersects(player.getBounds())) {
						enemy2.get(i).projectiles.b.remove(j);
						player.hit(1);
						pHit.play();
						System.out.println("player is hit with Projectiles");

					}
					
				}
			}
			
		}


	}


	public void isEnemyCollidingWithProjectile() {


		// enemy1
		for(int i = 0; i < enemy1.size(); i++) {
			for(int j = 0; j < leftProjectiles.b.size(); j++) {
				if(leftProjectiles.b.get(j).getBounds().intersects(enemy1.get(i).getBounds())) {
					enemy1.get(i).hit(1);
					leftProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");

				}
			}
			for(int j = 0; j < rightProjectiles.b.size(); j++) {
				if(rightProjectiles.b.get(j).getBounds().intersects(enemy1.get(i).getBounds())) {
					enemy1.get(i).hit(1);
					rightProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");
				}
			}
			if(enemy1.get(i).isDead()) {
				enemy1.remove(i);
			}
		}


		// enemy2
		for(int i = 0; i < enemy2.size(); i++) {
			for(int j = 0; j < leftProjectiles.b.size(); j++) {
				if(leftProjectiles.b.get(j).getBounds().intersects(enemy2.get(i).getBounds())) {
					enemy2.get(i).hit(1);
					leftProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");

				}
			}
			for(int j = 0; j < rightProjectiles.b.size(); j++) {
				if(rightProjectiles.b.get(j).getBounds().intersects(enemy2.get(i).getBounds())) {
					enemy2.get(i).hit(1);
					rightProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");
				}
			}
			if(enemy2.get(i).isDead()) {
				enemy2.remove(i);
			}
		}

		// enemy3
		for(int i = 0; i < enemy3.size(); i++) {
			for(int j = 0; j < leftProjectiles.b.size(); j++) {
				if(leftProjectiles.b.get(j).getBounds().intersects(enemy3.get(i).getBounds())) {
					enemy3.get(i).hit(1);
					leftProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");

				}
			}
			for(int j = 0; j < rightProjectiles.b.size(); j++) {
				if(rightProjectiles.b.get(j).getBounds().intersects(enemy3.get(i).getBounds())) {
					enemy3.get(i).hit(1);
					rightProjectiles.b.remove(j);
					eHit.play();
					System.out.println("Enemy is hit with projectile");
				}
			}
			if(enemy3.get(i).isDead()) {
				enemy3.remove(i);
			}
		}
	}


	@Override
	public void keyPressed(int k) {

		if(!timeLeft.equals("0 : 00") && !player.isDead()) {
			
			if(k == KeyEvent.VK_LEFT) player.moveLeft(true);
			if(k == KeyEvent.VK_RIGHT) player.moveRight(true);
			if(k == KeyEvent.VK_UP) player.moveUp(true);
			if(k == KeyEvent.VK_DOWN) player.moveDown(true);
		}
	}

	@Override
	public void keyReleased(int k) {

		if(!timeLeft.equals("0 : 00") && !player.isDead()) {
			
			if(k == KeyEvent.VK_LEFT) player.moveLeft(false);
			if(k == KeyEvent.VK_RIGHT) player.moveRight(false);
			if(k == KeyEvent.VK_UP) player.moveUp(false);
			if(k == KeyEvent.VK_DOWN) player.moveDown(false);

			if(k == KeyEvent.VK_SPACE) {

				pShot.stop();
				leftProjectiles.addProjectile(new Projectiles(player.x + 2, player.y, 1));
				rightProjectiles.addProjectile(new Projectiles(player.x + 24, player.y, 1));
				pShot.play();

			}
		}

	}









}
