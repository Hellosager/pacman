package Pacman.highscore;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Pacman.Utils;
import Pacman.game.Game;
import Pacman.gui.Display;
import Pacman.gui.MainMenu;
import Pacman.stats.StatsView;

public class Highscore {
	private JFrame frame;
	private Display display;
	private Game game;
	private JLabel[] scoreLabels;
	private final static DecimalFormat scoreFormat = new DecimalFormat("00000");
	private int[] scores;
	private AnimationThread at;

	public Highscore(Display display) {
		this.frame = display.getFrame();
		this.display = display;
		scores = new int[3];
		initGui();
		loadScore();
		initScores();
	}

	public Highscore(Display display, Game game) {
		this(display);
		this.game = game;
		initGui();
		updateScores();
		initScores();
	}

	public void initGui() {
		frame.getContentPane().removeAll();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setLayout(new GridLayout(3, 1));
		frame.setLocation(frame.getX(), frame.getY());

		JLabel highscoreLabel = new JLabel("Highscore", JLabel.CENTER);
		highscoreLabel.setForeground(Color.RED);
		Font head = new Font("Arial", Font.BOLD, 110);
		highscoreLabel.setFont(head);
		frame.add(highscoreLabel); // 1

		int arrRow = 4, arrCol = 3;
		JPanel scoresPanel = new JPanel(new GridLayout(arrRow, arrCol));
		scoresPanel.setOpaque(false);
		JLabel scoresPanelLabels[][] = new JLabel[arrRow][arrCol];
		for (int row = 0; row < scoresPanelLabels.length; row++) {
			for (int col = 0; col < scoresPanelLabels[row].length; col++) {
				scoresPanel.add(scoresPanelLabels[row][col] = new JLabel());
			}
		}

		// Anzeige new Highscore!!!
		if (game != null && isNewHighscore()) {
			Font infoFont = new Font("Arial", Font.BOLD, 20);
			JLabel highscoreInfo = scoresPanelLabels[0][1];
			highscoreInfo.setForeground(Color.ORANGE);
			highscoreInfo.setFont(infoFont);
			highscoreInfo.setText("NEW HIGHSCORE !!!");
			highscoreInfo.setHorizontalAlignment(JLabel.CENTER);
		}

		Font scoresFont = new Font("Arial", Font.BOLD, 25);
		scoreLabels = new JLabel[3];
		scoreLabels[0] = scoresPanelLabels[1][1];
		scoreLabels[0].setHorizontalAlignment(JLabel.CENTER);
		scoreLabels[0].setForeground(Color.RED);
		scoreLabels[0].setFont(scoresFont);
		scoreLabels[1] = scoresPanelLabels[2][1];
		scoreLabels[1].setHorizontalAlignment(JLabel.CENTER);
		scoreLabels[1].setForeground(Color.RED);
		scoreLabels[1].setFont(scoresFont);
		scoreLabels[2] = scoresPanelLabels[3][1];
		scoreLabels[2].setHorizontalAlignment(JLabel.CENTER);
		scoreLabels[2].setForeground(Color.RED);
		scoreLabels[2].setFont(scoresFont);
		frame.add(scoresPanel); // 2

		JLabel menu = new JLabel("Menü", JLabel.CENTER);
		menu.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		menu.setBackground(Color.GRAY);
		menu.setFont(scoresFont);
		menu.setOpaque(true);
		Color mouseOver = new Color(142, 78, 163);
		menu.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				at.setRunning(false);
				new MainMenu(display);
			}

			public void mouseExited(MouseEvent e) {
				menu.setBackground(Color.GRAY);
			}

			public void mouseEntered(MouseEvent e) {
				menu.setBackground(mouseOver);
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		JLabel stats = new JLabel("Stats", JLabel.CENTER);
		stats.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		stats.setBackground(Color.GRAY);
		stats.setFont(scoresFont);
		stats.setOpaque(true);
		stats.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				at.setRunning(false);
				new StatsView(display);
			}

			public void mouseExited(MouseEvent e) {
				stats.setBackground(Color.GRAY);
			}

			public void mouseEntered(MouseEvent e) {
				stats.setBackground(mouseOver);
			}

			public void mouseClicked(MouseEvent e) {
			}
		});

		JPanel buttonAnimationPanel = new JPanel(new GridLayout(2, 1));
		buttonAnimationPanel.setOpaque(false);

		AnimationPanel animationPanel = new AnimationPanel();
		animationPanel.setBackground(Color.BLACK);

		JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
		buttonPanel.setOpaque(false);
		buttonPanel.setBackground(Color.BLACK);
		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				buttonPanel.add(menu);
			} else if (i == 2) {
				buttonPanel.add(stats);
			} else {
				buttonPanel.add(new JLabel());
			}
		}
		buttonAnimationPanel.add(animationPanel);
		buttonAnimationPanel.add(buttonPanel);
		frame.add(buttonAnimationPanel); // 3
		frame.repaint();
		frame.revalidate();
		animationPanel.init();
		at = new AnimationThread(animationPanel);
		at.start();
	}

	public void initScores() {
		scoreLabels[0].setText("1.     " + scoreFormat.format(scores[0])); // 5
																			// spaces
		scoreLabels[1].setText("2.     " + scoreFormat.format(scores[1]));
		scoreLabels[2].setText("3.     " + scoreFormat.format(scores[2]));

	}

	private boolean isNewHighscore() {
		return game.getScore() > scores[0];
	}

	private boolean isNewScore() {
		return game.getScore() > scores[2];
	}

	private int getIndexOfNewScore() {
		for (int i = 0; i < 3; i++)
			if (scores[i] == game.getScore())
				return i;
		return 2;
	}

	private void saveScore() {
		sortScore();
		File scoreFile = new File("score.txt");
		try {
			BufferedWriter out;
			scoreFile.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(scoreFile)));

			for (int i = 0; i < 3; i++)
				out.write(scores[i] + "\n");

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadScore() {
		File scoreFile = new File("score.txt");
		if (scoreFile.exists()) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(scoreFile)));
				for (int i = 0; i < 3; i++)
					scores[i] = Utils.parseInt(in.readLine());
				in.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		} else {
			for (int i = 0; i < 3; i++)
				scores[i] = 0;
		}
		sortScore();
	}

	private void updateScores() {
		if (isNewScore()) {
			scores[2] = game.getScore();
			sortScore();
			scoreLabels[getIndexOfNewScore()].setForeground(Color.ORANGE);
		}
		saveScore();
		loadScore();
	}

	private void sortScore() {
		int index = 2;
		while (index != 0 && scores[index] > scores[index - 1]) {
			int upScore = scores[index];
			scores[index] = scores[index - 1];
			scores[index - 1] = upScore;
			index--;
		}
	}

}
