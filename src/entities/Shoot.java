package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Game_main.Game;
import World.Camera;

public class Shoot extends Entity{
	
	private double dx, dy;
	private int speed = 2, life = 100;

	public Shoot(int x, int y, int width, int height, BufferedImage sprite,double dx2,double dy2) {
		super(x, y, width, height, sprite);
		this.dx = dx2;
		this.dy = dy2;
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		this.setX((int) (getX() + dx * speed));
		this.setY((int) (getY() + dy * speed));
		
		life --;
		if(life < 0) {
			Game.shoots.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillOval(this.getX() - Camera.getX(), this.getY() - Camera.getY(), this.getWIDTH(), this.getHEIGHT());
	}
}
