package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Enemy3 {

	 private BufferedImage enemy;
	 private boolean dead = false;
	 private int health = 5;

	 public double x, y;

	 public Enemy3(double x, double y) {

		 try {
			 
			 enemy = ImageIO.read(getClass().getResourceAsStream("/Enemies/alien2.png"));

		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }

		 this.x = x;
		 this.y = y;

	 }

	 public Rectangle getBounds() {

		 return new Rectangle((int)x, (int)y, 50, 40);
	 }

	 public int getHealth() {
		 return health;
	 }

	 public void hit(int damage) {

		 health -= damage;
		 if(health < 0) health = 0;
		 if(health == 0) dead = true;

	 }


	 public boolean isDead() { return dead; }


	 public void update() {


		y += 3;


	 }

	 public void draw(Graphics g) {

		 //g.drawRect((int)x, (int)y, 50, 80);
		 g.drawImage(enemy, (int)x, (int)y, 50, 60, null);

		 g.setColor(Color.BLACK);
		 g.fillRect((int)x + (enemy.getWidth()/4), (int)y + (enemy.getHeight()/4), 20, 3);
		 g.setColor(Color.RED);
		 g.fillRect((int)x + (enemy.getWidth()/4), (int)y + (enemy.getHeight()/4), health * 4, 3);
		 g.setColor(Color.BLACK);
		 g.drawRect((int)x + (enemy.getWidth()/4), (int)y + (enemy.getHeight()/4), 20, 3);

	 }
}
