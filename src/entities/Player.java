package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import Game_main.Game;
import World.Camera;
import World.World;
import grafics.SpriteSheet;
import grafics.UI;

public class Player extends Entity{
	
	private int speed = 1;
	private double life = 100, maxLife = 100;
	private float money;
	
	private boolean right, left, up, down;
	public int dir, right_dir = 1, left_dir = 2, up_dir = 3, down_dir = 4;
	
	private int frames = 0, frames_idle = 0, index_idle = 0, maxFrames = 5, index = 0, maxIndex = 7;
	private boolean moved = false;
	private boolean shoot = false, isShooting = false;
	
	private int shoots = 6, maxShoots = 6, ammo = 30;
	
	Random rand = new Random();
	
	private BufferedImage[] idle_player, shooting;
	private BufferedImage[] right_Player ,left_Player,up_Player,down_Player;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		//parado
		idle_player = new BufferedImage[5];
		//andando
		right_Player = new BufferedImage[8];
		left_Player = new BufferedImage[8];
		up_Player = new BufferedImage[8];
		down_Player = new BufferedImage[8];
		//atirando
		shooting = new BufferedImage[6];
		
		//andando
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
		//render_shoot();
		render_idle();
		moved = false;
		if(right && World.isFree(this.getX() + speed, this.getY())) {
			moved = true;
			dir = right_dir;
			setX(getX() + speed);
		}
		else if (left && World.isFree(this.getX() - speed, this.getY())) {
			moved = true;
			dir = left_dir;
			setX(getX() - speed);
		}
		if(up && World.isFree(this.getX(), this.getY() - speed)) {
			moved = true;
			dir = up_dir;
			setY(getY() - speed);
		}
		if(down && World.isFree(this.getX(), this.getY() + speed)) {
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
		
		this.checkMoneyBag();
		
		Camera.setX(Camera.clamp(getY() -(Game.WIDTH/2), 0, World.WIDTH * 16 - Game.WIDTH));
		Camera.setY(Camera.clamp(getY() -( Game.HEIGHT/2), 0, World.HEIGHT * 16- Game.HEIGHT));
		
		if(life <= 0 ) {
			Game.entities.clear();
			Game.enemies.clear();
			
			Game.entities = new ArrayList<Entity>();
			Game.enemies = new ArrayList<Enemy>();
			Game.sheet = new SpriteSheet("/player_sheet.png");
			Game.atlas = new SpriteSheet("/atlas.png");
			Game.enemy_sheet = new SpriteSheet("/enemy_sheet.png");
			Game.player = new Player(0, 0 ,32, 32, Game.sheet.getSprite(15, 9, 32, 32));
			Game.ui = new UI();
			
			Game.entities.add(Game.player);
			Game.world = new World("/map.png");
			
			return;
		}
	}
	
	public void checkMoneyBag() {
		for(int i = 0; i < Game.entities.size(); i ++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof MoneyBag) {
				if(Entity.isColidding(this, atual)) {
					money += rand.nextInt(55) + 20;
					Game.entities.remove(atual);
				}
			}
		}
	}
	
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	@Override
	public void render(Graphics g) {
		if(isShooting) {
			g.drawImage(shooting[index_idle], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
			if(index_idle >= 4) isShooting = false;
		}
		else if(moved == false && isShooting == false) { //parado
			g.drawImage(idle_player[index_idle], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == up_dir && isShooting == false) { //cima
			g.drawImage(up_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == down_dir && isShooting == false) {//baixo
			g.drawImage(down_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == right_dir && isShooting == false) { // direita
			g.drawImage(right_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
		else if(dir == left_dir && isShooting == false) {//esquerda
			g.drawImage(left_Player[index], this.getX() - Camera.getX(), this.getY() - Camera.getY(), null);
		}
	}
	public void render_shoot() {
		//atirando
		if(isShooting) {
			for(int i = 0; i < 6; i ++) {
				if(dir == right_dir) { //direita
					shooting[i] = Game.sheet.getSprite(0 + (i * 32), 288, 32, 32);
				}
				else if(dir == left_dir) { //esquerda
					shooting[i] = Game.sheet.getSprite(0 + (i * 32), 384, 32, 32);
				}
				else if(dir == up_dir) { //cima
					shooting[i] = Game.sheet.getSprite(0 + (i * 32), 192, 32, 32);
				}
				else if(dir == down_dir) { //baixo
					shooting[i] = Game.sheet.getSprite(0 + (i * 32), 64, 32, 32);
				}
			}
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
	
	public void fire() {

		if(shoot && shoots > 0 && isShooting == false) {
			isShooting = true;
			render_shoot();
			shoots --;
			
			int dx = 0, dy = 0, px = 0, py = 0;
			if(dir == right_dir) {
				dx = 1;
				px = 32;
				py = 16;
			}
			else if(dir == left_dir) {
				dx = -1;
				px = -8;
				py = 16;
			}
			else if(dir == up_dir) {
				dy = -1;
				px = 20;
			}
			else if(dir == down_dir) {
				dy = 1;
				py = 32;
				px = 6;
			}
			
			Shoot disparo = new Shoot(this.getX() + px, this.getY() + py, 5, 3, null, dx, dy);
			Game.shoots.add(disparo);
		}
	}
	
	public void reloadAmmo() {
		int dif = maxShoots - shoots;
		if(ammo > 0) {
			if(ammo - dif >= 0) {
				ammo -= dif;
				shoots += dif;
			}
			else {
				shoots += ammo;
				ammo = 0;
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
	public boolean isShoot() {
		return shoot;
	}
	public void setShoot(boolean shoot) {
		this.shoot = shoot;
	}
	public int getShoots() {
		return shoots;
	}
	public void setShoots(int shoots) {
		this.shoots = shoots;
	}
	public int getAmmo() {
		return ammo;
	}
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}
	
}
