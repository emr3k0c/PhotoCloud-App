package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import GUI.BaseLogger;
import image.ImageMatrix;

/**
 * The Contrast class applies a contrast filter to an ImageMatrix.
 */

public class Contrast {

	/**
     * Adjusts the contrast of the given ImageMatrix based on the provided percentage.
     * Creates a contrast rate by making a formulation. 
     * 0 percentage give the image itself and 100 percentage applies maximum contrast.
     *
     * @param toFilter The original image in the form of an ImageMatrix to be filtered.
     * @param percentage The amount of contrast adjustment, represented as a percentage.
     *                   
     * @return The contrast-adjusted image in the form of an ImageMatrix.
     */
	public static ImageMatrix ContrastFilter(ImageMatrix toFilter, int percentage) {
		BufferedImage image = new BufferedImage(toFilter.getWidth(), toFilter.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int minContrast = 1;
		int maxContrast = 2;
	    double contrastRate = minContrast + (maxContrast - minContrast) * percentage / 100.0;
		try {
	    for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color originalColors = new Color(toFilter.getRGB(i, j));
				int contrastedRed = (int) (((((originalColors.getRed() / 255.0) - 0.5) * contrastRate) + 0.5) * 255.0);
				int contrastedGreen = (int) (((((originalColors.getGreen() / 255.0) - 0.5) * contrastRate) + 0.5) * 255.0);
				int contrastedBlue = (int) (((((originalColors.getBlue() / 255.0) - 0.5) * contrastRate) + 0.5) * 255.0);
				
				if (contrastedRed > 255) contrastedRed = 255;
				else if (contrastedRed < 0) contrastedRed = 0;
				if (contrastedGreen > 255) contrastedGreen = 255;
				else if (contrastedGreen < 0) contrastedGreen = 0;
				if (contrastedBlue > 255) contrastedBlue = 255;
				else if (contrastedBlue < 0) contrastedBlue = 0;
				
				int contrastedRGB = ImageMatrix.convertRGB(contrastedRed, contrastedGreen, contrastedBlue, toFilter.getAlpha(i, j));
				image.setRGB(i, j, contrastedRGB);
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


