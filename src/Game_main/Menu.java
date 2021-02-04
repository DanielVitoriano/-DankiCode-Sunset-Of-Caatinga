package Game_main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class Menu {
	
	private String[] options = {"New Game", "Load Game", "Exit"};
	public int currentOption = 0, maxOptions = options.length - 1, red = 255, frames = 0,maxFrames = 15;
	public boolean up, down, enter, somar = false;
	public static boolean pause;
	private BufferedImage[] back_menu, gado;
	
	public Menu() {
		back_menu = new BufferedImage[1];
		gado = new BufferedImage[1];
		
		back_menu[0] = Game.sprite_menu.getSprite(0, 0, (Game.WIDTH * Game.SCALE), (Game.HEIGHT * Game.SCALE));
		gado[0] = Game.atlas.getSprite(210, 390, 28, 21);
	}
	
	public void tick() {
		File file = new File("save.txt");
		if(file.exists()) Save.saveExists = true;
		else Save.saveExists = false;
		if(Game.gameState == "menu") {
			Sound.bg_menu.loop();
			if(up) {
				Sound.menu_options.play();
				up = false;
				currentOption --;
				if(currentOption < 0) currentOption = maxOptions;
			}
			if(down) {
				Sound.menu_options.play();
				down = false;
				currentOption ++;
				if(currentOption > maxOptions) currentOption = 0;
			}
			frames++;
			if(frames == maxFrames ){
				frames = 0;
				if(somar) red++;
				else if (!somar) red--;
				if(red == 255) somar = false;
				else if(red == 230) somar = true;
			}
		}	
		if(enter) {
			Sound.menu_select.play();
			Sound.bg_menu.stop();
			enter = false;
			if(options[currentOption] == "New Game") {
				Game.gameState = "normal";
				pause = false;
				file = new File("save.txt");
				file.delete();
			}
			else if(options[currentOption] == "Load Game") {
				file = new File("save.txt");
				if(file.exists()) {
					System.out.println("carregando");
					String saver = Save.loadGame(10);
					Save.applySave(saver);
				}
			}
			else if(options[currentOption] == "Exit") System.exit(1);
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
			g.setFont(new Font ("Arial", Font.BOLD, 14));
			g.setColor(Color.WHITE);
			g.drawString("PRESS ENTER", Game.WIDTH - 55, Game.HEIGHT);
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0, 30));
		g2.fillRect(0,0,Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
	}
}
