package filters;

import java.awt.image.BufferedImage;

import GUI.BaseLogger;
import image.ImageMatrix;

/**
 * The Sharpening class provides a filter to sharpen images.
 * It first blurs the image and then compares the blurred and original image 
 * to enhance the edge contrast and bring out details.
 */

public class Sharpening {
	
	/**
     * The sharpeningFilter method applies a sharpening effect to an ImageMatrix.
     * 
     * The method first applies a blurring filter to the image, 
     * then it enhances the contrast between the blurred and original image, 
     * by making pixelwise additions and subtractions.
     *
     * @param imageMatrix The original ImageMatrix object to be sharpened.
     * @param percentage The degree of sharpening to be applied, with 0% meaning no sharpening and 100% meaning maximum sharpening.
     * @return A new ImageMatrix object that is a sharpened version of the original ImageMatrix.
     */
	public static ImageMatrix sharpeningFilter(ImageMatrix imageMatrix, int percentage) {
		int minBlur = 1;
		int maxBlur = 25;
	    int kernelSize = (int) (minBlur + (maxBlur - minBlur) * percentage / 100);
		ImageMatrix blurredImage = Blurring.blurringFilter(imageMatrix, kernelSize);		
		ImageMatrix sharpenedImage = new ImageMatrix(imageMatrix.getWidth(), imageMatrix.getHeight(), BufferedImage.TYPE_INT_ARGB);
		try {
		for (int i = 0; i < imageMatrix.getWidth(); i++) {
			for (int j = 0; j < imageMatrix.getHeight(); j++) {
				int originalAlpha = imageMatrix.getAlpha(i, j);
				int originalRed = imageMatrix.getRed(i, j);
				int originalGreen = imageMatrix.getGreen(i, j);
				int originalBlue = imageMatrix.getBlue(i, j);
				
				int blurredAlpha = blurredImage.getAlpha(i, j);
				int blurredRed = blurredImage.getRed(i, j);
				int blurredGreen = blurredImage.getGreen(i,j);
				int blurredBlue = blurredImage.getBlue(i, j);
				
				int sharpenedAlpha = Math.min(originalAlpha + Math.abs(originalAlpha - blurredAlpha), 255);
				int sharpenedRed = Math.min(originalRed + Math.abs(originalRed - blurredRed), 255);
				int sharpenedGreen = Math.min(originalGreen + Math.abs(originalGreen - blurredGreen), 255);
				int sharpenedBlue = Math.min(originalBlue + Math.abs(originalBlue - blurredBlue), 255);
            
				sharpenedImage.setRGB(i, j, ImageMatrix.convertRGB(sharpenedRed, sharpenedGreen, sharpenedBlue, sharpenedAlpha));
            
			}
	  }
		
	  }
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			BaseLogger.Error.log(e.getMessage());
		}
        return sharpenedImage;
	}
}
