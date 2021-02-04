package Game_main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import World.World;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import entities.Shoot;
import grafics.SpriteSheet;
import grafics.UI;

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 320, HEIGHT = 240, SCALE = 2, fps_show = 0;
	private boolean isRunning, showMessengerGameOver = true, restartGame = false, saveGame = false;
	public static int money_bags_left = 0;
	private int cur_level = 1, max_level = 2, framesOver = 0;
	public static String gameState = "menu";
	public Random rand = new Random();
	
	public InputStream stream1 = ClassLoader.getSystemClassLoader().getResourceAsStream("old.ttf");
	public static Font  oldFont;
	
	public InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("sunset.ttf");
	public static Font  sunsetFont;
	
	public static World world;
	public static SpriteSheet sheet, atlas, enemy_sheet, sprite_menu;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public BufferedImage image;
	public static JFrame frame;
	private Thread thread;
	public static Player player;
	public static Enemy enemy;
	public static UI ui;
	public static List<Shoot> shoots;
	public static Menu menu;
	
	//construtor da classe
	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); //dimensoes da janela
		initFrame();

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		shoots = new ArrayList<Shoot>();
		sheet = new SpriteSheet("/player_sheet.png");
		atlas = new SpriteSheet("/atlas.png");
		enemy_sheet = new SpriteSheet("/enemy_sheet.png");
		sprite_menu  = new SpriteSheet("/back_menu.png");
		player = new Player(0, 0 ,32, 32, sheet.getSprite(15, 9, 32, 32));
		ui = new UI();
		
		entities.add(player);
		world = new World("/level" + cur_level + ".png");
		
		menu = new Menu();
		try {
			oldFont = Font.createFont(Font.TRUETYPE_FONT, stream1).deriveFont(40f);
			sunsetFont = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(65f);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//janela
	public void initFrame() {
		frame = new JFrame("SUNSEET OF CAATINGA");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	//main
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	//inicio do jogo
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	//fim do jogo
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//update/ atualizações na tela do jogo/ chamadas
	public void tick() {
		if(gameState == "normal") {
			this.restartGame = false;
			for(int i = 0; i < entities.size(); i ++) {
				Entity e = entities.get(i);
				e.tick();
			}
			for(int i = 0; i < shoots.size(); i++) {
				shoots.get(i).tick();
			}
			
			if(money_bags_left <= 0) {
				cur_level ++;
				if(cur_level > max_level) {
					cur_level = 1;
				}
				String newWorld = "level"  + cur_level + ".png";
				World.restart_game(newWorld);
			}
		}
		else if(gameState == "over"){
			this.framesOver ++;
			if(this.framesOver == 15) {
				this.framesOver = 0;
				if(this.showMessengerGameOver) this.showMessengerGameOver = false;
				else this.showMessengerGameOver = true;
			}
			if(restartGame) {
				this.restartGame = false;
				money_bags_left = 0;
				gameState = "normal";
				cur_level = 1;
				String newWorld = "level"  + cur_level + ".png";
				World.restart_game(newWorld);
				gameState = "menu";
				}
			}
		else if (gameState == "menu" || gameState == "pause") {
				menu.tick();
			}
		if(gameState == "pause") {
			if(this.saveGame) {
				this.saveGame = false;
				String[] opt1 = {"level"};
				int[] opt2 = {this.cur_level};
				Save.saveGame(opt1, opt2, 1);
			}
		}
		}
		
	//renderização das imagens
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0xFFeeca84));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		
		for(int i = 0; i < entities.size(); i ++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < shoots.size(); i++) {
			shoots.get(i).render(g);
		}
		
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		Graphics2D g3 = (Graphics2D) g;
		g3.setColor(new Color(230,30,5, 35));
		g3.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		if(gameState == "over") {
			
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0, 100));
			g2.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
			
			g.setFont(new Font ("Arial", Font.BOLD, 28));
			g.setColor(Color.white);
			g.drawString("GAME OVER", WIDTH - 95, HEIGHT + 5);
			
			g.setFont(new Font ("Arial", Font.BOLD, 14));
			g.setColor(Color.WHITE);
			if(showMessengerGameOver) g.drawString("PRESS ENTER", WIDTH - 55, HEIGHT + 20);
		}
		else if(gameState == "menu" || gameState == "pause") {
			menu.render(g);
		}
		
		g.setFont(new Font ("Arial", Font.BOLD, 14));
		g.setColor(Color.white);
		g.drawString("FPS: " + fps_show, WIDTH + 260, 15);
		
		bs.show();
	}
	@Override
	public void run() {
		//frames
		long lastTime = System.nanoTime();
		double amoutOfTicks = 60.0;
		double ns = 1000000000 / amoutOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		//looping do jogo
		while(isRunning == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				
				tick();
				render();
				
				frames++;
				delta--;
				}
			if(System.currentTimeMillis() - timer >= 1000) {
				fps_show = frames;
				//System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
				
			}
			stop();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//direita
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.setRight(true);
		}
		//esquerda
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.setLeft(true);
		}
		//cima
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.setUp(true);
			
			if(gameState == "menu") menu.up = true;
		}
		//baixo
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.setDown(true);
			
			if(gameState == "menu") menu.down = true;
		}
		//tiro
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.setShoot(true);
			//player.fire();
		}
		if(e.getKeyCode() == KeyEvent.VK_R) {
			player.reloadAmmo();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			restartGame = true;
			if(gameState == "menu" || gameState == "pause") menu.enter = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Menu.pause = true;
			gameState = "pause";
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			saveGame = true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		//direita
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.setRight(false);
		}
		//esquerda
		else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.setLeft(false);
		}
		//cima
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.setUp(false);
		}
		//baixo
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.setDown(false);
		}
		
	}

	
}
