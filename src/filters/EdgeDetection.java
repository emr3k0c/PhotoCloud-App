package filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import GUI.BaseLogger;
import image.ImageMatrix;

/**
 * The EdgeDetection class applies an edge detection filter to an ImageMatrix.
 */
public class EdgeDetection {
	
	
	/**
     * Applies grayscale and blur filters to the given ImageMatrix.
     * This is called image pre-processing. The purpose is adjusting the resulting image quality.
     *
     * @param imageMatrix The original image in the form of an ImageMatrix to be pre-processed.
     * @return The preprocessed image in the form of an ImageMatrix.
     */
	public static ImageMatrix Preprocessing(ImageMatrix imageMatrix) {
		ImageMatrix grayScaledImage = GrayScale.grayScalingFilter(imageMatrix, 1);
		ImageMatrix preProcessedImage = Blurring.blurringFilter(grayScaledImage, 25);
		return preProcessedImage;
	}
	
	
	/**
     * Detects the edges in the given ImageMatrix based on the provided percentage.
     * Provided percentage is related with threshold value. 
     * The edge detection algorithm calculates intensity gradients in the image and marks pixels exceeding a certain threshold as edges.
     *
     * @param imageMatrix The original image in the form of an ImageMatrix to be filtered.
     * @param percentage The sensitivity of edge detection, represented as a percentage. Higher values result in more edges being detected.
     * @return The edge detected image in the form of an ImageMatrix.
     */
	public static ImageMatrix EdgeDetectionFilter(ImageMatrix imageMatrix, int percentage) {
	    ImageMatrix preProcessedImage = Preprocessing(imageMatrix);
	    int thresholdValue = (int) (100-percentage);
	    int h = preProcessedImage.getHeight(), w = preProcessedImage.getWidth(), threshold=thresholdValue;
	    ImageMatrix edgeDetected = new ImageMatrix(w, h, BufferedImage.TYPE_BYTE_GRAY);
	    int[][] vert = new int[w][h];
	    int[][] horiz = new int[w][h];
	    double[][] edgeWeight = new double[w][h]; // changed to double for precision
	    try {
	    for (int y=1; y<h-1; y++) {
	        for (int x=1; x<w-1; x++) {
	            int alpha = new Color(imageMatrix.getRGB(x, y), true).getAlpha();
	            vert[x][y] = (int)(preProcessedImage.getRGB(x+1, y-1)& 0xFF + 2*(preProcessedImage.getRGB(x+1, y)& 0xFF) + preProcessedImage.getRGB(x+1, y+1)& 0xFF- preProcessedImage.getRGB(x-1, y-1)& 0xFF - 2*(preProcessedImage.getRGB(x-1, y)& 0xFF) - preProcessedImage.getRGB(x-1, y+1)& 0xFF);
	            horiz[x][y] = (int)(preProcessedImage.getRGB(x-1, y+1)& 0xFF + 2*(preProcessedImage.getRGB(x, y+1)& 0xFF) + preProcessedImage.getRGB(x+1, y+1)& 0xFF- preProcessedImage.getRGB(x-1, y-1)& 0xFF - 2*(preProcessedImage.getRGB(x, y-1)& 0xFF) - preProcessedImage.getRGB(x+1, y-1)& 0xFF);
	            edgeWeight[x][y] = Math.sqrt(vert[x][y] * vert[x][y] + horiz[x][y] * horiz[x][y]) / (255 * Math.sqrt(2)) * 100; // normalize to percentage
	            if (edgeWeight[x][y] > threshold) {
	                edgeDetected.setRGB(x, y, ImageMatrix.convertRGB(255, 255, 255, alpha));
	            }
	            else {
	                edgeDetected.setRGB(x, y, ImageMatrix.convertRGB(0, 0, 0, alpha));
	            }
	        }
	    }
	    }
	    catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			BaseLogger.Error.log(e.getMessage());
		}
	    return edgeDetected;
	}

	

}
