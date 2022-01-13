package ie.gmit.dip;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontMetrics;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 
 * Makes a Word Cloud and saves as an PNG image in location requested. The Word
 * Cloud displays the number of frequently occurring words requested.
 * 
 * Some Collision code & Graphics2D code was amalgamated from MadProgrammer:
 * https://stackoverflow.com/questions/34690321/java-graphics-random-text/34691463
 * 
 * @author Maire Murphy & MadProgrammer
 * 
 *
 */

public class WCImageMaker {

	/**
	 * Picks one of five colours randomly
	 * @param g2d
	 * @return Graphic2D object set with the random colour
	 */
	//O(1) - single operation
	private static Graphics2D pickRandomColour(Graphics2D g2d) {
		// colours for font
		int min = 1;
		int max = 7;
		int rand = min + (int) (Math.random() * ((max - min) + 1));
		switch (rand) {
		case 1 -> g2d.setColor(Color.GREEN);
		case 2 -> g2d.setColor(Color.WHITE);
		case 3 -> g2d.setColor(Color.YELLOW);
		case 4 -> g2d.setColor(Color.BLUE);
		case 5 -> g2d.setColor(Color.CYAN);
		case 6 -> g2d.setColor(Color.MAGENTA);
		case 7 -> g2d.setColor(Color.RED);
		}
		return g2d;
	}

	/**
	 * Selects one of five fonts randomly
	 * @return
	 */
	//O(1) - single operation
	private static Font pickRandomFont() {
		// colours for font
		Font font = new Font("Times New Roman", Font.PLAIN, 13);
		int min = 1;
		int max = 5;
		int rand = min + (int) (Math.random() * ((max - min) + 1));

		switch (rand) {
		case 1 -> font = new Font("Arial Black", Font.ITALIC, 13);
		case 2 -> font = new Font("Verdana", Font.BOLD, 13);
		case 3 -> font = new Font("Times New Roman", Font.PLAIN, 13);
		case 4 -> font = new Font("Helvetica ", Font.ITALIC, 13);
		case 5 -> font = new Font("Courier New", Font.PLAIN, 13);
		}
		return font;
	}

	/**
	 * Creates image filling it with words from the List until the required number of words is
	 * reached. The image is save as a png in the destination.
	 * 
	 * @param maxWords: number of words to display
	 * @param words: list of words
	 * @param destination: location and name of image
	 * @return true if successfully created.
	 */
	//O(n^3) - this method & collision() contain 3 nested loops in total 
	public static boolean createImage(List<String> words, String destination) {
		//Set size of image
		int height = 500;
		int width = 600;
		Random rnd = new Random();
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Font font;
		Graphics2D g2d = img.createGraphics();
		g2d.setColor(Color.DARK_GRAY); //set back ground of image to grey
		g2d.fillRect(0, 0, width, height);

		List<Rectangle2D> used = new ArrayList<>(30); 
		int size = 38;

		//loop for each word in list
		for (int i = 0; i < words.size(); i++) {

			String word = words.get(i);

			g2d = pickRandomColour(g2d);
			font = pickRandomFont();
			//special case: ensure the first word used a noticeable font
			if (i == 0)
				font = new Font("Arial Black", Font.BOLD, 13);

			Font drawFont = font.deriveFont((float) size);
			FontMetrics fm = g2d.getFontMetrics(drawFont);
			//calculate a rectangle boundary around the current word based in font size
			Rectangle2D bounds = fm.getStringBounds(word, g2d);
			//loop until a position is found that does not collide with an existing word rectangle boundary
			do {
				//pick a random x and y position 
				int xPos = rnd.nextInt(width - (int) bounds.getWidth());
				int yPos = rnd.nextInt(height - (int) bounds.getHeight());
				//special case: ensure first word is near the center of the image
				if (i == 0) {
					xPos = (width / 2 - (int) bounds.getWidth());
					yPos = (height / 2 - (int) bounds.getHeight());
				}
				bounds.setFrame(xPos, yPos, bounds.getWidth(), bounds.getHeight());
			} while (collision(used, bounds));
			//successfully found a location in the image that does not cause a collision
			used.add(bounds); //record that location in the bounds array
			g2d.setFont(drawFont);
			//add word to the image
			g2d.drawString(word, (float) bounds.getX(), (float) bounds.getY() + fm.getAscent());
			//reduce the size of the font for each iteration
			size--;
		}
		g2d.dispose();
		try {
			ImageIO.write(img, "png", new File(destination));
			return true;
		} catch (IOException e) {
			AppInterface.showErrMsg("Problem encountered creating the image. " + e.getMessage());
			return false;
		}

	}

	/**
	 * code by: MadProgrammer:
	 * https://stackoverflow.com/questions/34690321/java-graphics-random-text/34691463
	 * Checks if the current rectangle boundary collides with an existing boundary
	 * 
	 * @param used: records boundaries already used in the image
	 * @param bounds: current boundary 
	 */
	//O(n) - worst case no bounds intersect so goes through all rectangle bounds
	private static boolean collision(List<Rectangle2D> used, Rectangle2D bounds) {
		boolean collides = false;
		for (Rectangle2D check : used) {
			if (bounds.intersects(check)) {
				collides = true;
				break;
			}
		}
		return collides;
	}

}
