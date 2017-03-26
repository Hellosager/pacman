package Pacman.stats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stats {
	private static final String[] DEFAULT_STAT_NAMES = { StatNames.PLAYED_TIME, 
			StatNames.EATEN_TILES, StatNames.DEATHS, StatNames.LEVEL_FINISHED,
			StatNames.CHEAT_GHOSTPATH};
	private static final Pattern nameOfState = Pattern.compile("(\\s*\\w)*\\s*");
	private static final Pattern lastDigit = Pattern.compile("(\\d+)(?!.*\\d)");
	private Map<String, Number> statsMap;

	public Stats() {
		loadStats();
	}

	private void loadStats() {
		File statsFile = new File("stats.txt");
		statsMap = new LinkedHashMap<String, Number>();
		if (statsFile.exists()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(statsFile)));
				String line;
				while ((line = in.readLine()) != null) {
					// Wenn eine Stats Zeile im File valid ist
					if (line.matches("((\\s*[^\\d\\W])*)\\s*:\\s\\d*\\s*")) {
						Matcher m = nameOfState.matcher(line);
						m.find();
						String key = m.group().trim(); // liefert Name des Stats
						m = lastDigit.matcher(line);
						m.find();
						String value = m.group(); // liefert letzte Zahl im
														// String
						statsMap.put(key, new Long(value));
					}
					for (String name : DEFAULT_STAT_NAMES)
						if (!statsMap.containsKey(name))
							statsMap.put(name, new Integer(0));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			initStatsFile();
			saveStats();
		}
	}

	private void initStatsFile() {
		statsMap = new LinkedHashMap<String, Number>();
		for (String name : DEFAULT_STAT_NAMES)
			statsMap.put(name, new Integer(0));
	}

	public void saveStats() {
		File statsFile = new File("stats.txt");
		try {
			BufferedWriter out;
			statsFile.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(statsFile)));
			for (String key : statsMap.keySet())
				out.write(key + " : " + statsMap.get(key) + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(String key, int add){
		statsMap.put(key, statsMap.get(key).longValue() + (long) add);
	}
	
	public Map getStatsMap() {
		return statsMap;
	}

}
