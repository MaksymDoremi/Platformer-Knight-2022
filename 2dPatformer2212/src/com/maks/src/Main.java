package com.maks.src;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

public class Main {

	public static int frameWidth;

	public static void main(String[] args) {

		JFrame frame = new JFrame("Twilight Forest");
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frameWidth = 1550;

		frame.setSize(1550, 850);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("img/icon.png"));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(new Game());
		frame.setVisible(true);
		//List<Image> a = new LinkedList<Image>();
		//sortAndAddSprites("g", a);
	}

	public static void sortAndAddSprites(String sourceFolder, List<Image> sprites) {
		// declare source folder
		//sourceFolder = "img/user/left/attack";
		//sprites = new LinkedList<Image>();
		File[] files = new File(sourceFolder).listFiles();

		// sort array by this big method
		Arrays.sort(files, new Comparator<File>() {
			// create compare method for comparing two numbers, example "file (10).png", I need to
			// compare this "(10)"
			@Override
			public int compare(File o1, File o2) {
				int n1 = extractNumber(o1.getName());
				int n2 = extractNumber(o2.getName());
				return n1 - n2;
			}

			// extract "(number)" and set boundaries as "(", ")"
			private int extractNumber(String name) {
				// main integer
				int i = 0;
				try {
					// first boundary
					int s = name.indexOf('(') + 1;

					// second boundary
					int e = name.lastIndexOf(')');
					// create string with number between ()
					String number = name.substring(s, e);
					// parse it to the main integer
					i = Integer.parseInt(number);
				} catch (Exception e) {
					i = 0; // if filename does not match the format
							// then default to 0
				}
				return i;
			}
		});
		
		// fill main list with already sorted files
		for (File file : files) {
			if (file.isFile()) {
				// PROVEDL JSEM ZMENU TADY, ODSTRANIL JSEM toURI(), a nechal jenom getPath()
				// ToolKit vyazduje materskou slozku, img/a td
				sprites.add(Toolkit.getDefaultToolkit().getImage(file.getPath()));

			}
		}

	}

}
