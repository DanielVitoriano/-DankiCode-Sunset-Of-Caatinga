package Game_main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import World.World;

public class Save {
	
	public static boolean saveExists = false, saveGame = false;
	public static int encode = 10;
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null ) {
						String[] trans = singleLine.split(":");
						char[] val = trans[1].toCharArray();
						trans[1] = "";
						for(int i = 0; i < val.length; i++) {
							val[i] -= encode;
							trans[1] += val[i];
						}
						line += trans[0];
						line += ":";
						line += trans[1];
						line += "/";
					}
				}catch(IOException e) {e.printStackTrace();}
			}catch(FileNotFoundException e) { e.printStackTrace(); System.out.println("Arquivo inexistente");}
		}
		
		return line;
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i ++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0]) {
				case "level":
					World.restart_game("level" + spl2[1] + ".png");
					Game.gameState = "normal";
					Game.money_bags_left = 0;
					Menu.pause = false;
					break;
				case "vida":
					Game.player.setLife(Integer.parseInt(spl2[1]));
					break;
			}
		}
	}

	public static void saveGame(String[] val1, int[] val2, int encode) {
		BufferedWriter write = null;
		
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current += ":";
			char[] value = Integer.toString(val2[i]).toCharArray();
			for(int c = 0; c < value.length; c++) {
				value[c] += encode;
				current += value[c];
			}
			try {
				write.write(current);
				if( i < val1.length - 1) {
					write.newLine();
				}
			}catch(IOException e) {e.printStackTrace();}
			
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {e.printStackTrace();}
	}
	
}
