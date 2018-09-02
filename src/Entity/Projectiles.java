package Entity;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Projectiles {

    private BufferedImage projectile;
    private Player player;
    
    private double x;
    private double y;
    private int A;

    public Projectiles(double x, double y, int proj){

        try {
        	
        	if(proj == 1)
        		projectile = ImageIO.read(getClass().getResourceAsStream("/Projectiles/playerS.png"));
        	else if(proj == 2)
        		projectile = ImageIO.read(getClass().getResourceAsStream("/Projectiles/enemyS1.png"));
        	else if(proj == 3)
        		projectile = ImageIO.read(getClass().getResourceAsStream("/Projectiles/enemyS2.png"));
        	else if(proj == 4)
        		projectile = ImageIO.read(getClass().getResourceAsStream("/Projectiles/bossS.png"));
        		

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        this.x = x;
        this.y = y;
    }

    public double getX() { return this.x; }
	public double getY() { return this.y; }
	
	public Rectangle getBounds() {

		return new Rectangle((int)x, (int)y, (int) (projectile.getWidth() * 0.4), (int) (projectile.getHeight() * 0.2));
	}
	
	public void setPosition(double x, double y) {
		
		this.x = x;
		this.y = y;
	}
    
    public void draw(Graphics g){
    	
    		//g.drawRect((int)x, (int)y, (int) (missile.getWidth() * 0.4), (int) (missile.getHeight() * 0.2));
    	
        g.drawImage(projectile, (int)x, (int)y, (int) (projectile.getWidth() * 0.6), (int) (projectile.getHeight() * 0.6), null);
    }

    public void update(){

        
        y -= 5;
    }
    
    public void update2() {

    		y += 5;
    }

    public void update3(int A) {

    	double unit_vx = Math.cos(A*Math.PI/180);
    	double unit_vy = Math.sin(A*Math.PI/180);

    	setPosition(x + 8 * unit_vx, y + 8 * unit_vy) ;

    }

    public void update4(Player player) {
    	this.player = player;
    	moveTowards();
    	
    }
    
    public void moveTowards() {
	    
		turnTowards(player);
			
		double ux = Lookup.cos[A];
		double uy = Lookup.sin[A];
	
		double vx = player.x - x;
		double vy = player.y - y;
	
		double d = vx*ux + vy*uy;
	
	
		if ( d > 20)
			moveForwardBy(3);
		
	}
	
	public void moveForwardBy(double distance) {
		x += distance * Math.cos(A*Math.PI/180);
	    y += distance * Math.sin(A*Math.PI/180);
		
	}
	
	public void turnTowards(Player player) {
		
	      double ux = Lookup.cos[A];
	      double uy = Lookup.sin[A];

	      double nx = -uy;
	      double ny = ux;

	      double vx = player.x - x;
	      double vy = player.y - y;

	      double d = vx*nx + vy*ny;

	      if(d > 10)
	      {
	          A += 3;
	          if (A >= 360)  A -= 360;
	      }
	      if(d < -10)
	      {
	         A -= 3;
	         if(A < 0)  A += 360;
	      }

	}
   
}
