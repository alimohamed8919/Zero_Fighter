package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Enemy1 {

    private BufferedImage enemy;
    private boolean dead = false;
    private int health = 5;
    public ProjectileController projectiles;
	
	public double x, y;
	public int A;
	private long Timer = -1;

	public Enemy1(double x, double y) {
		
		try {
			enemy = ImageIO.read(getClass().getResourceAsStream("/Enemies/alien1.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		projectiles = new ProjectileController();
		
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
	
	public void moveTowards(Player player) {
	    
		turnTowards(player);
			
		double ux = Lookup.cos[A];
		double uy = Lookup.sin[A];
	
		double vx = player.x - x;
		double vy = player.y - y;
	
		double d = vx*ux + vy*uy;
	
	
		if ( d > 0)
			moveForwardBy(5);
		
	}
	
	public void moveForwardBy(double distance) {
		x += distance * Math.cos(A*Math.PI/180);
	    //y += distance * Math.sin(A*Math.PI/180);
		
	}
	
	public void turnTowards(Player player) {
		
	      double ux = Lookup.cos[A];
	      double uy = Lookup.sin[A];

	      double nx = -uy;
	      double ny = ux;

	      double vx = player.x - x;
	      double vy = player.y - y;

	      double d = vx*nx + vy*ny;

	      if(d > 0)
	      {
	          A += 3;
	          if (A >= 360)  A -= 360;
	      }
	      if(d < -20)
	      {
	         A -= 3;
	         if(A < 0)  A += 360;
	      }

	}
	
	public boolean isDead() { return dead; }
	
	public boolean isShooting() {
		
		if(y > -100) {
			if(Timer <= -1) {
				Timer = System.nanoTime();
			}else {
				long elapsed = (System.nanoTime() - Timer) / 1000000;
				if(elapsed > 1000) {
					Timer = -1;
					return true;
				}
			}
		}
		
		return false;
	}
	
	
	public void update() {
		
		if(isShooting()) {
			projectiles.addProjectile(new Projectiles(x+20, y+25, 2));
		}
		
		projectiles.update2();

		if(y <= 50)
			y += 1;
		
		
	}
	 
	public void draw(Graphics g) {
		
		//g.drawRect((int)x, (int)y, 50, 80);
		g.drawImage(enemy, (int)x, (int)y, 50, 50, null);
		projectiles.draw(g);
		
		g.setColor(Color.BLACK);
		g.fillRect((int)x + (enemy.getWidth()/4), (int)y + (enemy.getHeight()/5), 20, 3);
		g.setColor(Color.RED);
		g.fillRect((int)x + (enemy.getWidth()/4), (int)y + (enemy.getHeight()/5), health * 4, 3);
		g.setColor(Color.BLACK);
		g.drawRect((int)x + (enemy.getWidth()/4), (int)y + (enemy.getHeight()/5), 20, 3);
		

	}
	
	
	

}
