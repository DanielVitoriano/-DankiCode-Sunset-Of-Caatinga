package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

import Game_main.Game;
import World.Camera;
import World.Node;
import World.Vector2i;

public class Entity {

	//public static BufferedImagem ammo = Game.atlas.getSprite(0, 0, 0, 0);
	public static BufferedImage money_bag = Game.atlas.getSprite(432, 368, 16, 16);
	public static BufferedImage enemy = Game.enemy_sheet.getSprite(0, 96, 32,32);
	
	public int x, y, WIDTH, HEIGHT, depht;
	private BufferedImage sprite;
	
	private int maskx, masky, mwidth, mheight;
	protected List<Node> path;
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mheight = height;
		this.mwidth = width;
	}
	
	public static Comparator<Entity> nodeSortert = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0, Entity n1) {
			if(n1.depht < n0.depht) return +1;
			if(n1.depht > n0.depht) return -1;
			return 0;
		}};

	public void tick() {
		
	}
	
	public void followPath(List<Node> path, double speed) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				//xprev = x;
				//yprev = y;
				if(x < target.x * 16) x+= speed;
				else if(x > target.x * 16) x-= speed;

				if(y < target.y * 16) y+= speed;
				else if(y > target.y * 16) y-= speed;
				
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size() - 1);
				}
			}
		}
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public static boolean isColliding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
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
