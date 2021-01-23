package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import Game_main.Game;
import World.Camera;

public class Enemy extends Entity {

	private int speed = 1, rage = 70;
	double distance;
	
	public int dir, right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	
	private int frames = 0, frames_idle = 0, index_idle = 0, maxFrames = 5, index = 0, maxIndex = 7;
	private boolean moved = false;
	
	public static Random rand = new Random();
	private BufferedImage[] idle_enemy;
	private BufferedImage[] right_enemy;
	private BufferedImage[] left_enemy;
	private BufferedImage[] up_enemy;
	private BufferedImage[] down_enemy;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
		
		idle_enemy = new BufferedImage[4];
		right_enemy = new BufferedImage[9];
		left_enemy = new BufferedImage[9];
		up_enemy = new BufferedImage[10];
		down_enemy = new BufferedImage[10];
		

		for(int i = 0; i < 9; i ++) {
			right_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 224, 32, 32);
		}
		for(int i = 0; i < 9; i ++) {
			left_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 288, 32, 32);
		}
		for(int i = 0; i < 10; i ++) {
			up_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 32, 32, 32);
		}
		for(int i = 0; i < 10; i ++) {
			down_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 128, 32, 32);
		}
	}
	public void tick() {
		render_idle();
		distance = Math.sqrt(Math.pow(this.getX() - Game.player.getX(), 2) + Math.pow(this.getY() - Game.player.getY(), 2));
		if(distance <= rage && distance >= 16) {
			if(this.getX() > Game.player.getX() && World.World.isFree(this.getX() - speed, this.getY()) && !isColliding(this.getX() - speed, this.getY())) {
				this.setX(getX() - speed); //esquerda
				dir = left_dir;
				moved = true;
			}
			else if(this.getX() < Game.player.getX() && World.World.isFree(this.getX() + speed, this.getY()) && !isColliding(this.getX() + speed, this.getY())) {
				this.setX(getX() + speed); // direita
				dir = right_dir;
				moved = true;
			}
			if(this.getY() > Game.player.getY() && World.World.isFree(this.getX(), this.getY() - speed) && !isColliding(this.getX(), this.getY() - speed)) {
				this.setY(getY() - speed); //cima
				dir = up_dir;
				moved = true;
			}
			else if(this.getY() < Game.player.getY() && World.World.isFree(this.getX(), this.getY() + speed) && !isColliding(this.getX(), this.getY() + speed)) {
				this.setY(getY() + speed); //baixo
				dir = down_dir;
				moved = true;
			}
		}else {
			moved = false;
		}
		
		if(isPlayerColliding()) {
			if(rand.nextInt() < 10) {
				Game.player.setLife((int) (Game.player.getLife() - 1));
			}
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
				if(index_idle >= 4) {
					index_idle = 0;
				}
			}
		}
	}
	
	@Override
	public void render(Graphics g) {
		if(moved == false) {
			g.drawImage(idle_enemy[index_idle], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == up_dir) {
			g.drawImage(up_enemy[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == down_dir) {
			g.drawImage(down_enemy[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == right_dir) {
			g.drawImage(right_enemy[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == left_dir) {
			g.drawImage(left_enemy[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
	}

	public void render_idle() {
		
		for(int i = 0; i < 4; i++) {
			if(dir == up_dir) {
				idle_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 0, 32, 32);
			}
			else if(dir == down_dir) {
				idle_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 96, 32, 32);
			}
			else if(dir == right_dir) {
				idle_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 192, 32, 32);
			}
			else {
				idle_enemy[i] = Game.enemy_sheet.getSprite(0 + (i * 32), 96, 32, 32);
			}
		}
	}
	
	public boolean isPlayerColliding() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + 8, this.getY() + 8, 16, 16);
		Rectangle playerCollider = new Rectangle(Game.player.getX() + 8, Game.player.getY(), 16, 32);
		
		return enemyCurrent.intersects(playerCollider);
	}
	
	public boolean isColliding(int xnext, int ynext) {
		
		Rectangle enemyCurrent = new Rectangle(xnext + 8, ynext + 8, 16, 16);
		
		for(int i = 0; i < Game.enemies.size(); i ++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX(), e.getY(), 16, 16);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
}
