package filters;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

import GUI.BaseLogger;
import image.ImageMatrix;

/**
 * This class contains method to blur an image.
 */

public class Blurring {
	
	/**
	 * This static method applies a blurring filter to an image, using a convolution operation.
	 * The level of blurring is determined by the percentage parameter, with larger values producing a greater blur.
	 * Percentage parameter formulates to the kernel size. Then, a convolution kernel is created to perform a uniform blur.
	 * 
	 * 
	 * @param toFilter The ImageMatrix to be blurred.
	 * @param percentage The percentage of blurring to be applied. Must be between 0 and 100.
	 * @return A new ImageMatrix that is the result of applying the blurring filter to the input ImageMatrix.
	 */
	
	public static ImageMatrix blurringFilter(ImageMatrix toFilter, int percentage) {
		BufferedImage image = new BufferedImage(toFilter.getWidth(), toFilter.getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage alphaImage = new BufferedImage(toFilter.getWidth(), toFilter.getHeight(), BufferedImage.TYPE_INT_ARGB);
		int minBlur = 1;
		int maxBlur = 25;
	    int kernelSize = (int) (minBlur + (maxBlur - minBlur) * percentage / 100);
		try {
	    for (int y = 0; y < toFilter.getHeight(); y++) {
	        for (int x = 0; x < toFilter.getWidth(); x++) {
	            image.setRGB(x, y, toFilter.getRGB(x, y));
	            int alpha = (toFilter.getRGB(x, y) >> 24) & 0xff;
	            alphaImage.getRaster().setSample(x, y, 0, alpha);
	        }
	    }

		float[] matrix = new float[kernelSize*kernelSize];
        for (int i = 0; i < kernelSize*kernelSize; i++) {
    		matrix[i] = 1.0f/(kernelSize*kernelSize);
        }
		Kernel kernel = new Kernel(kernelSize, kernelSize, matrix);

        BufferedImageOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        image = op.filter(image, null);
        BufferedImage blurredAlphaImage = op.filter(alphaImage, null);
        
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int alpha = blurredAlphaImage.getRaster().getSample(x, y, 0);
                int color = image.getRGB(x, y) & 0x00ffffff;  
                image.setRGB(x, y, (alpha << 24) | color);  
            }
        }
		}
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			BaseLogger.Error.log(e.getMessage());
		}
        
        ImageMatrix blurredImageMatrix = new ImageMatrix(image);
		return blurredImageMatrix;
	}
	
}
