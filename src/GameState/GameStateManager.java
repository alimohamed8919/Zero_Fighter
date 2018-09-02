package GameState;

import java.awt.*;

public class GameStateManager {

	private GameState[] gameStates;
	private int currentState;
	private int lastState;
	
	public static final int MENUSTATE = 0;
	public static final int LEVEL1STATE = 1;
	public static final int LEVEL2STATE = 2;
	public static final int ENDSTATE = 3;
	public static final int GAMEOVERSTATE = 4;

	public GameStateManager() {
		
		gameStates = new GameState[5];
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	public int getLastState() {
		
		return lastState;
	}

	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
		if(state == ENDSTATE)
			gameStates[state] = new EndState(this);
		if(state == GAMEOVERSTATE)
			gameStates[state] = new GameOverState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		lastState = currentState;
		unloadState(currentState);
		currentState = state;
		loadState(currentState);

	}

	public void update() {
		
		if(gameStates[currentState] != null) 
			
			gameStates[currentState].update();
		
	}

	public void draw(Graphics g) {
		
		if(gameStates[currentState] != null) 
			
			gameStates[currentState].draw(g);
		
	}
	
	public void keyPressed(int k) {
		
		gameStates[currentState].keyPressed(k);
	}
	
	public void keyReleased(int k) {
		
		gameStates[currentState].keyReleased(k);
	}

}
