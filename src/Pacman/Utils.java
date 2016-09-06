package Pacman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	
}
