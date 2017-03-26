package Pacman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Utils {

	public static String loadFileAsString(String path){
		StringBuilder builder = new StringBuilder();
		try{
			InputStream is = Utils.class.getClassLoader().getResourceAsStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");
			br.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static int parseInt(String s){
		try{
		int i = Integer.parseInt(s);
		return i;
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}		
	}
	
	public static ArrayList<String> getPrimaryLevel(){
		InputStream is = Utils.class.getClassLoader().getResourceAsStream("level/levelnames.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		ArrayList<String> fileNamesList = new ArrayList<String>();
		
		try {
			String line;
			while((line = br.readLine()) != null){
				fileNamesList.add("level/" + line);
			}
		} catch (IOException e) {e.printStackTrace();}
		
		return fileNamesList;
	}
	
	public static ArrayList<String> getAllLevel(){
		ArrayList<String> fileNamesList = getPrimaryLevel();
		
		File f = new File("Files/Level/");
		if(f.exists()){
			for(int i = 0; i < f.listFiles().length; i++)
				fileNamesList.add("Files/Level/" + f.listFiles()[i].getName());
		}
		
		return fileNamesList;
		
	}
	
	public static void createReadme(){
		try{
			if(!new File("Files").exists())
				new File("Files").mkdir();
			File readMe = new File("Files/readme.txt");
			if(!readMe.exists()){
				InputStream is = Utils.class.getClassLoader().getResourceAsStream("readme.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(readMe)));
				String line;
				while((line = br.readLine()) != null){
					out.write(line);
					out.newLine();
				}
				br.close();
				out.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
