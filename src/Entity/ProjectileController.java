package Entity;

import java.awt.Graphics;
import java.util.ArrayList;

public class ProjectileController {

	public ArrayList<Projectiles> b = new ArrayList<Projectiles>();
	
	Projectiles TempProjectile;
	
	
	public void update() {
		
		for(int i = 0; i < b.size(); i++) {
			TempProjectile = b.get(i);
			
			if(TempProjectile.getY() <= 0) {
				removeProjectile(TempProjectile);
			}
			
			TempProjectile.update();
		}
	}
	
	public void update2() {
		
		for(int i = 0; i < b.size(); i++) {
			TempProjectile = b.get(i);
			
			if(TempProjectile.getY() >= 300) {
				removeProjectile(TempProjectile);
			}
			
			TempProjectile.update2();
		}
	}
	
	public void update3(int A) {
		
		for(int i = 0; i < b.size(); i++) {
			TempProjectile = b.get(i);
			
			if(TempProjectile.getY() >= 300) {
				removeProjectile(TempProjectile);
			}
			
			TempProjectile.update3(A);
		}
	}
	
	public void update4(Player player) {
		
		for(int i = 0; i < b.size(); i++) {
			TempProjectile = b.get(i);
			
			if(TempProjectile.getY() >= 300) {
				removeProjectile(TempProjectile);
			}
			
			TempProjectile.update4(player);
		}
	}
	
	public void addProjectile(Projectiles projectile) { b.add(projectile); }
	public void removeProjectile(Projectiles projectile) { b.remove(projectile); }
	
	public void draw(Graphics g) {
		
		for(int i = 0; i < b.size(); i++) {
			TempProjectile = b.get(i);
			if(TempProjectile != null)
			TempProjectile.draw(g);
		}
		
	}
}
