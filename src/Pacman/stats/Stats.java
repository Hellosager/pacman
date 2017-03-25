package Pacman.stats;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stats {
	private static final Pattern nameOfState = Pattern.compile("(\\s*\\w)*\\s*");
	private static final Pattern lastDigit = Pattern.compile("(\\d+)(?!.*\\d)");
	private Map<String, String> statsMap;

	public Stats() {
		loadStats();
	}

	private void loadStats() {
		File statsFile = new File("stats.txt");
		if(statsFile.exists()){
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(statsFile)));
				String line;
				while((line = in.readLine()) != null){
					if(line.matches("((\\s*[^\\d\\W])*)\\s*:\\s\\d*\\s*")){
						Matcher m = nameOfState.matcher(line);
						String key = m.group(line);	// liefert Name des Stats
						m = lastDigit.matcher(line);
						String value = m.group(line);	// liefert letzte Zahl im String
						statsMap.put(key, value);
					}
				}
			}
			catch (IOException e) { e.printStackTrace();}
		}else{
			initStatsFile();
		}
	}

	private void initStatsFile() {
		
	}

}
