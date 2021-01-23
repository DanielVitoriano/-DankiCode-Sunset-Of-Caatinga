package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Game_main.Game;
import World.Camera;

public class Player extends Entity{
	
	private int speed = 1;
	private double life = 100, maxLife = 100;
	
	private boolean right, left, up, down;
	public int dir, right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	
	private int frames = 0, frames_idle = 0, index_idle = 0, maxFrames = 5, index = 0, maxIndex = 7;
	private boolean moved = false;
	
	private BufferedImage[] idle_player;
	private BufferedImage[] right_Player;
	private BufferedImage[] left_Player;
	private BufferedImage[] up_Player;
	private BufferedImage[] down_Player;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		idle_player = new BufferedImage[5];
		right_Player = new BufferedImage[8];
		left_Player = new BufferedImage[8];
		up_Player = new BufferedImage[8];
		down_Player = new BufferedImage[8];
		
		for(int i = 0; i < 8; i ++) {
			right_Player[i] = Game.sheet.getSprite(0 + (i * 32), 256, 32, 32);
		}
		for(int i = 0; i < 8; i ++) {
			left_Player[i] = Game.sheet.getSprite(0 + (i * 32), 352, 32, 32);
		}
		for(int i = 0; i < 8; i ++) {
			up_Player[i] = Game.sheet.getSprite(0 + (i * 32), 160, 32, 32);
		}
		for(int i = 0; i < 8; i ++) {
			down_Player[i] = Game.sheet.getSprite(0 + (i * 32), 32, 32, 32);
		}
	}
	@Override
	public void tick() {

		render_idle();
		moved = false;
		if(right && World.World.isFree(this.getX() + speed, this.getY())) {
			moved = true;
			dir = right_dir;
			setX(getX() + speed);
		}
		else if (left && World.World.isFree(this.getX() - speed, this.getY())) {
			moved = true;
			dir = left_dir;
			setX(getX() - speed);
		}
		if(up && World.World.isFree(this.getX(), this.getY() - speed)) {
			moved = true;
			dir = up_dir;
			setY(getY() - speed);
		}
		if(down && World.World.isFree(this.getX(), this.getY() + speed)) {
			moved = true;
			dir = down_dir;
			setY(getY() + speed);
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		else {
			frames_idle++;
			if(frames_idle == maxFrames + 6) {
				frames_idle = 0;
				index_idle++;
				if(index_idle >= 5) {
					index_idle = 0;
				}
			}
		}
		Camera.setX(Camera.clamp(getY() -(Game.WIDTH/2), 0, World.World.WIDTH * 16 - Game.WIDTH));
		Camera.setY(Camera.clamp(getY() -( Game.HEIGHT/2), 0, World.World.HEIGHT * 16- Game.HEIGHT));
		
	}
	@Override
	public void render(Graphics g) {
		if(moved == false) {
			g.drawImage(idle_player[index_idle], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == up_dir) {
			g.drawImage(up_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == down_dir) {
			g.drawImage(down_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == right_dir) {
			g.drawImage(right_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == left_dir) {
			g.drawImage(left_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
	}

	public void render_idle() {
		for(int i = 0; i < 5; i++) {
			if(dir == up_dir) {
				idle_player[i] = Game.sheet.getSprite(0 + (i * 32), 128, 32, 32);
			}
			else if(dir == down_dir) {
				idle_player[i] = Game.sheet.getSprite(0 + (i * 32), 0, 32, 32);
			}
			else if(dir == right_dir) {
				idle_player[i] = Game.sheet.getSprite(0 + (i * 32), 224, 32, 32);
			}
			else if(dir == left_dir) {
				idle_player[i] = Game.sheet.getSprite(0 + (i * 32), 320, 32, 32);
			}
			else {
				idle_player[i] = Game.sheet.getSprite(0 + (i * 32), 0, 32, 32);
			}
		}
	}
	
	public void setRight(boolean right) {
		this.right = right;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	public double getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public double getMaxLife() {
		return maxLife;
	}
	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
	}
	
}
