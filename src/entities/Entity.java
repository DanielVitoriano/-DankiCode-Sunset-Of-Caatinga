package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Game_main.Game;
import World.Camera;

public class Entity {

	public static BufferedImage money_bag = Game.atlas.getSprite(432, 368, 16, 16);
	public static BufferedImage enemy = Game.enemy_sheet.getSprite(0, 96, 32,32);
	
	private int x, y, WIDTH, HEIGHT;
	private BufferedImage sprite;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.sprite = sprite;
	}

	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, getX() - Camera.getX(), getY() - Camera.getY(), null);
	}
	
	//gets e sets
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}

	public int getHEIGHT() {
		return HEIGHT;
	}

	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}


	public BufferedImage getSprite() {
		return sprite;
	}


	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
}
