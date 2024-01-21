package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import GUI.BaseLogger;
import image.ImageMatrix;

/**
 * The Brightness class applies a brightness filter to an ImageMatrix.
 */

public class Brightness {
	
	/**
	 * Adjusts the brightness of the given ImageMatrix based on the provided percentage.
	 * It increases RGB value of each pixel by specified amount. 
	 *
	 * @param toFilter The original image in the form of an ImageMatrix to be filtered.
	 * @param percentage The amount of brightness to be added. It is a percentage of the original brightness.
	 *                   
	 * @return The filtered image in the form of an ImageMatrix.
	 */
	public static ImageMatrix BrightnessFilter(ImageMatrix toFilter, int percentage) {
		BufferedImage image = new BufferedImage(toFilter.getWidth(), toFilter.getHeight(), BufferedImage.TYPE_INT_ARGB);
		try {
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color originalColors = new Color(toFilter.getRGB(i, j));
				int brightenedRed = (int) originalColors.getRed() + originalColors.getRed() * percentage / 100;
				int brightenedGreen = (int) originalColors.getGreen() + originalColors.getGreen() * percentage / 100 ;
				int brightenedBlue = (int) originalColors.getBlue() + originalColors.getBlue() * percentage / 100;
				if (brightenedRed > 255) brightenedRed = 255;
				else if (brightenedRed < 0) brightenedRed = 0;
				if (brightenedGreen > 255) brightenedGreen = 255;
				else if (brightenedGreen < 0) brightenedGreen = 0;
				if (brightenedBlue > 255) brightenedBlue = 255;
				else if (brightenedBlue < 0) brightenedBlue = 0;
				
				int brightenedRGB = ImageMatrix.convertRGB(brightenedRed, brightenedGreen, brightenedBlue, toFilter.getAlpha(i, j));
				image.setRGB(i, j, brightenedRGB);
			}
		}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			BaseLogger.Error.log(e.getMessage());
		}

		return new ImageMatrix(image);
	}

}