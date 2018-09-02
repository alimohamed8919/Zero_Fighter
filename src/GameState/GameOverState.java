package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

//import Audio.AudioPlayer;
import Main.Background;

public class GameOverState extends GameState {
	
	private Background bg;
	
	private Font font;
	
	private int currentChoice = 0;
	private String[] options = {"RETRY", "QUIT"};
	
	public GameOverState(GameStateManager gsm) {
		
		this.gsm = gsm;
		init();
		
	}
	
	
	@Override
	public void init() {
		try {
			
			bg = new Background("/Background/gameoverbg.png", 1);
			
			new Color(255, 0, 0);
			new Font("Century Gothic", Font.BOLD, 28);
			font = new Font("Arial", Font.BOLD, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		bg.draw(g);
		

		g.setColor(Color.RED);
		g.setFont(new Font("Comic Sans", Font.BOLD, 20));
		g.drawString("EARTH IS DOOMED", 60, 55);
		g.drawString("G A M E  O V E R", 75, 85);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 145 + i * 5, 220 + i * 15);
		}
	}
	
	public void select() {
		
		if(currentChoice == 0) {
			// Retry
			gsm.setState(gsm.getLastState());
			
		}
		if(currentChoice == 1) {
			// quit
			System.exit(0);
			
		}
		
	}


	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice <= -1) {
				currentChoice = 0;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice >= 3) {
				currentChoice = 2;
			}
		}
		
	}
	


	@Override
	public void keyReleased(int k) {
		// TODO Auto-generated method stub
		
	}

}
