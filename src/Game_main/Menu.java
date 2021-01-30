package Game_main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Menu {
	
	private String[] options = {"New Game", "Load Game", "Exit"};
	public int currentOption = 0, maxOptions = options.length - 1, red = 230, frames = 0,maxFrames = 15;
	public boolean up, down, enter, pause;
	private BufferedImage[] back_menu, gado;
	
	public Menu() {
		back_menu = new BufferedImage[1];
		gado = new BufferedImage[1];
		
		back_menu[0] = Game.sprite_menu.getSprite(0, 0, (Game.WIDTH * Game.SCALE), (Game.HEIGHT * Game.SCALE));
		gado[0] = Game.atlas.getSprite(210, 390, 28, 21);
	}
	
	public void tick() {
		if(up) {
			up = false;
			currentOption --;
			if(currentOption < 0) currentOption = maxOptions;
		}
		if(down) {
			down = false;
			currentOption ++;
			if(currentOption > maxOptions) currentOption = 0;
		}
		frames++;
		if(frames == maxFrames ){
			 red++;
			 if(red == 255) {
				 red = 230;
			 }
		}
		if(enter) {
			enter = false;
			if(options[currentOption] == "New Game") Game.gameState = "normal";
		}
	}
	public void render(Graphics g) {
		if(!pause) {
			g.drawImage(back_menu[0], 0, 0, null);
	
			g.setFont(Game.sunsetFont);
			g.setColor(new Color(red, 30, 30));
			g.drawString("SUNSET OF CAATINGA", Game.HEIGHT -165, Game.WIDTH / 2 + 20);
			
			g.setFont(Game.oldFont);
			if(options[currentOption] == "New Game")g.setColor(new Color(0xfff49e12)); 
			else g.setColor(Color.white);
			g.drawString("NEW GAME", Game.WIDTH - 50, Game.HEIGHT - 5);
			
			g.setFont(Game.oldFont);
			if(options[currentOption] == "Load Game")g.setColor(new Color(0xfff49e12));
			else g.setColor(Color.white);
			g.drawString("LOAD GAME", Game.WIDTH - 52, Game.HEIGHT + 30);
			
			g.setFont(Game.oldFont);
			if(options[currentOption] == "Exit")g.setColor(new Color(0xfff49e12));
			else g.setColor(Color.white);
			g.drawString("EXIT", Game.WIDTH - 15, Game.HEIGHT + 65);
			
			if(options[currentOption] == "New Game") {
				g.drawImage(gado[0], Game.WIDTH - 90, Game.HEIGHT - 30,null); 
				g.drawImage(gado[0], Game.WIDTH + 90 , Game.HEIGHT - 30,null);}
			else if(options[currentOption] == "Load Game") {
				g.drawImage(gado[0],Game.WIDTH - 92, Game.HEIGHT + 5,null);
				g.drawImage(gado[0],Game.WIDTH + 90, Game.HEIGHT + 5,null);}
			else if(options[currentOption] == "Exit") {
				g.drawImage(gado[0], Game.WIDTH - 50, Game.HEIGHT + 45,null);
				g.drawImage(gado[0], Game.WIDTH + 38, Game.HEIGHT + 45,null);
			}
		}
		else {
			
		}
	}
}
