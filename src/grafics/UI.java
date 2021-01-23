package grafics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import Game_main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10, 10, 70, 10);
		
		g.setColor(Color.green);
		g.fillRect(10, 10, (int) ((Game.player.getLife()/Game.player.getMaxLife()) * 70), 10);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 8));
		g.drawString(Game.player.getLife() + "/" + Game.player.getMaxLife(),12, 12);
	}
	
}
