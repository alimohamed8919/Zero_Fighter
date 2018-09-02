package Entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Boss {

	private BufferedImage enemy;
    private boolean dead = false;
    private int health = 100;
    public ProjectileController projectiles1;
    public ProjectileController projectiles2;
    public ProjectileController projectiles3;
    private Player player;
    
	
	public double x, y;
	public int A;
	private long Timer = -1;
	private long Timer2 = -1;

	public Boss(double x, double y, Player player) {
		
		try {
			enemy = ImageIO.read(getClass().getResourceAsStream("/Enemies/boss.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		projectiles1 = new ProjectileController();
		projectiles2 = new ProjectileController();
		projectiles3 = new ProjectileController();
		this.player = player;
		this.x = x;
		this.y = y;

	}
	
	public Rectangle getBounds() {

		return new Rectangle((int)x, (int)y, (int) (enemy.getWidth() * 0.9), (int) (enemy.getHeight() * 0.7));
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
		//x += distance * Math.cos(A*Math.PI/180);
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
		
		if(Timer <= -1) {
			Timer = System.nanoTime();
		}else {
			long elapsed = (System.nanoTime() - Timer) / 1000000;
			if(elapsed > 1000) {
				Timer = -1;
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isShooting2() {
		
		if(projectiles3.b.size() == 0) {


			if(Timer2 <= -1) {
				Timer2 = System.nanoTime();
			}else {
				long elapsed = (System.nanoTime() - Timer2) / 1000000;
				if(elapsed > 5000) {
					Timer2 = -1;
					return true;
				}
			}

		}
		return false;
	}
	
	public void update() {
		
		if(isShooting()) {
			System.out.println(x);
			projectiles1.addProjectile(new Projectiles(x, y + 30, 3));
			projectiles2.addProjectile(new Projectiles(x + 20, y + 30, 3));
		
		}
		if(isShooting2()) {
			
			projectiles3.addProjectile(new Projectiles(x+45, y+30, 4));
		}
		
		projectiles1.update3(A);
		projectiles2.update3(A);
		projectiles3.update4(this.player);
		
		

		if(y != -40)
			y += 1;
		
		
	}
	 
	public void draw(Graphics g) {
		
		//g.drawRect((int)x, (int)y, 50, 80);
		g.drawImage(enemy, (int)x, (int)y, (int) (enemy.getWidth() * 0.9), (int) (enemy.getHeight() * 0.9), null);
		projectiles1.draw(g);
		projectiles2.draw(g);
		projectiles3.draw(g);
		
		//g.drawRect((int)x, (int)y, (int) (enemy.getWidth() * 0.9), (int) (enemy.getHeight() * 0.7));
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 100, 10);
		g.setColor(Color.RED);
		g.fillRect(0, 0, health, 10);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, 100, 10);
		

	}
	
}
