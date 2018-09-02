package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

//import Audio.AudioPlayer;
import Main.Background;

public class EndState extends GameState {

	private Background bg;
	
	//private AudioPlayer bgMusic;

	private Font font;
	
	private int currentChoice = 0;
	private String[] options = {"REPLAY", "QUIT"};
	
	public EndState(GameStateManager gsm) {
		
		this.gsm = gsm;
		init();
		
	}
	
	
	@Override
	public void init() {
		try {

			bg = new Background("/Background/endbg.jpg", 1);
			
			font = new Font("Arial", Font.BOLD, 12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//bg.setPosition(0, 50);
		
		
	}

	@Override
	public void update() {
		bg.update();
		
	}

	@Override
	public void draw(Graphics g) {
		
		bg.draw(g);
		

		g.setColor(Color.GREEN);
		g.setFont(new Font("Comic Sans", Font.BOLD, 20));
		g.drawString("Earth Is Saved", 75, 50);
		g.setFont(new Font("Comic Sans", Font.BOLD, 15));
		g.drawString("You Saved Earth From", 70, 75);
		g.drawString("The Alien Invasion", 80, 95);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.WHITE);
			}
			g.drawString(options[i], 130 + i * 10, 220 + i * 15);
		}
	}
	
	public void select() {
		
		if(currentChoice == 0) {
			// Retry
			gsm.setState(GameStateManager.LEVEL1STATE);
			
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

