package Game_main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import World.World;
import entities.Enemy;
import entities.Entity;
import entities.Player;
import grafics.SpriteSheet;
import grafics.UI;

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	
	public static int WIDTH = 320, HEIGHT = 240, SCALE = 2;
	private boolean isRunning;
	
	public static World world;
	public static SpriteSheet sheet, atlas, enemy_sheet;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public BufferedImage image;
	public static JFrame frame;
	private Thread thread;
	public static Player player;
	public static Enemy enemy;
	public static UI ui;
	
	//construtor da classe
	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); //dimensoes da janela
		initFrame();

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		sheet = new SpriteSheet("/player_sheet.png");
		atlas = new SpriteSheet("/atlas.png");
		enemy_sheet = new SpriteSheet("/enemy_sheet.png");
		player = new Player(0, 0 ,32, 32, sheet.getSprite(15, 9, 32, 32));
		ui = new UI();
		
		entities.add(player);
		world = new World("/map.png");
		
	}
	//janela
	public void initFrame() {
		frame = new JFrame("RPG");
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
		for(int i = 0; i < entities.size(); i ++) {
			Entity e = entities.get(i);
			e.tick();
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
		
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
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
				System.out.println("FPS: " + frames);
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
		}
		//baixo
		else if(e.getKeyCode() == KeyEvent.VK_S) {
			player.setDown(true);
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
