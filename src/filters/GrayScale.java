package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import GUI.BaseLogger;
import image.ImageMatrix;
/**
 * This class provides a grayscale filter for images.
 * 
 */
public class GrayScale {
	
	
	/**
     * This method creates a grayscale version of the given image.
     * It performs the grayScaling based on the provided percentage for grayscale intensity, with 100% meaning a completely grayscale image. 
     * The final gray level is computed for each pixel by taking a weighted average of the red, green, and blue values.
     *
     * @param imageMatrix the original image as an ImageMatrix object
     * @param percentage the desired intensity for the grayscale effect, with 100% resulting in a fully grayscale image
     * @return a new ImageMatrix object representing the grayscaled version of the original image
     */
	public static ImageMatrix grayScalingFilter(ImageMatrix imageMatrix, int percentage) {
		ImageMatrix grayScaledImage = new ImageMatrix(imageMatrix.getWidth(), imageMatrix.getHeight(), BufferedImage.TYPE_INT_ARGB);
		double grayScalingAmount = percentage/100;
		try {
		for (int i = 0; i < imageMatrix.getWidth(); i++) {
			for (int j = 0; j < imageMatrix.getHeight(); j++) {
				Color coloredColors = new Color(imageMatrix.getRGB(j, i), true);
				int alpha = coloredColors.getAlpha();
				int red = imageMatrix.getRed(i, j);
				int green = imageMatrix.getGreen(i, j);
				int blue = imageMatrix.getBlue(i, j);

				int grayScaledRed =  (int)(red * 0.299 * grayScalingAmount + (1-grayScalingAmount) * red);
				int grayScaledGreen = (int)(green * 0.587 * grayScalingAmount + (1-grayScalingAmount) * green);
				int grayScaledBlue = (int)(blue * 0.114 * grayScalingAmount + (1-grayScalingAmount) * blue);
				
				int finalGrayScaled = grayScaledRed + grayScaledGreen + grayScaledBlue;
				
				if (finalGrayScaled > 255) finalGrayScaled = 255;
				else if (finalGrayScaled < 0) finalGrayScaled = 0;
				
				Color finalGray = new Color(finalGrayScaled, finalGrayScaled, finalGrayScaled, alpha);

				grayScaledImage.setRGB(i, j, finalGray.getRGB());
			}
			
		}
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			BaseLogger.Error.log(e.getMessage());
		}
		
		return grayScaledImage;
	}

}
