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
		g.drawString(Game.player.getLife() + "/" + Game.player.getMaxLife(),14, 18);
		
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 8));
		g.drawString("Money: R$" + Game.player.getMoney() + " Bags left: " + Game.money_bags_left,90, 18);
		
		if(Game.player.getAmmo() >= 20) {
			g.setColor(Color.GREEN);
		}
		else if(Game.player.getAmmo() >= 10) {
			g.setColor(Color.ORANGE);
		}
		else {
			g.setColor(Color.RED);
		}
		g.setFont(new Font("Arial", Font.BOLD, 16));
		if(Game.player.getAmmo() <= 0 && Game.player.getShoots() <= 0) {
			g.drawString("EMPTY",255, 235);
		}else {
			g.drawString(Game.player.getShoots() + "/" + Game.player.getAmmo(),275, 235);
		}
		
	}
	
}
