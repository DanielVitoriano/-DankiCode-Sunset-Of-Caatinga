package World;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Game_main.Game;

public class Tile {
	public static BufferedImage TILE_FLOOR = Game.atlas.getSprite(112, 576, 16, 16);
	public static BufferedImage TILE_WALL = Game.atlas.getSprite(97, 465, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.getX(), y - Camera.getY(),null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
}
