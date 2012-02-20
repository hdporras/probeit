package probeIt.viewerFramework.viewers.imaging;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import ij.gui.*;

import java.awt.Image;
public class Overlay
{

	ImageProcessor bottomProcessor;
	ImageProcessor topProcessor;
	ImageProcessor overlayedProcessor;
	boolean singleImageOnly;
	int newWidth;
	int newHeight;
	
	boolean beenAdjusted;
	
	public Overlay(Image bottomImage, Image topImage)
	{
		ImagePlus imagePlus1 = new ImagePlus("image", bottomImage);
		ImagePlus imagePlus2 = new ImagePlus("image", topImage);
		
		bottomProcessor = imagePlus1.getProcessor();
		topProcessor = imagePlus2.getProcessor();
		
		beenAdjusted = false;
		
		singleImageOnly = false;
	}
	
	public Overlay(Image bottomImage)
	{
		ImagePlus imagePlus1 = new ImagePlus("image", bottomImage);
		
		bottomProcessor = imagePlus1.getProcessor();
		
		beenAdjusted = false;
		
		singleImageOnly = true;
	}
	
	public ProbeitImage getOverlay(int topLayerWeight, int bottomLayerWeight)
	{
		
		if(singleImageOnly)
			topProcessor = setBlankTopProcessor();
		
		newWidth = Math.max(bottomProcessor.getWidth(), topProcessor.getWidth());
		newHeight = Math.max(bottomProcessor.getHeight(), topProcessor.getHeight());
	
		
		if(!beenAdjusted)
		{
			topProcessor.setInterpolate(true);
			topProcessor = topProcessor.resize(newWidth, newHeight);
			beenAdjusted = true;
		}
			
		int[] bottomPixels = (int[])bottomProcessor.getPixels();
		int[] topPixels = (int[])topProcessor.getPixels();
			
		int[] overlayedPixels = new int[newWidth * newHeight];
		
		int position;
		int topImagePixel;
		int bottomImagePixel;
		int overlayedPixel;
		int offset;
		int topImageIndex = 0;
		int bottomImageIndex = 0;
		for(int i = 0; i < newHeight; i ++)
		{
			offset = i * newWidth;
			for(int j = 0; j < newWidth; j ++)
			{
				position = offset + j;
				bottomImagePixel = -1;
				topImagePixel = -1;
				
				if(i < topProcessor.getHeight() && j < topProcessor.getWidth())
					topImagePixel = topPixels[topImageIndex++];
				
				if(i < bottomProcessor.getHeight() && j < bottomProcessor.getWidth())
					bottomImagePixel = bottomPixels[bottomImageIndex++];
				
				topImagePixel = grey2White(topImagePixel);
				overlayedPixel = overlayPixel(topImagePixel, bottomImagePixel, topLayerWeight, bottomLayerWeight);
				overlayedPixels[position] = overlayedPixel;
			}
		}
		return makeProbeitImage(overlayedPixels);
	}
	
	private ImageProcessor setBlankTopProcessor()
	{
		ImagePlus ip = NewImage.createRGBImage("overlayed", bottomProcessor.getWidth(), bottomProcessor.getHeight(), 1, NewImage.FILL_WHITE);
		topProcessor = ip.getProcessor();
		
		int[] topPixels = (int[])topProcessor.getPixels();
		int position, offset;
		for(int i = 0; i < bottomProcessor.getHeight(); i++)
		{
			offset = i * bottomProcessor.getWidth();
			for(int j = 0; j < bottomProcessor.getWidth(); j ++)
			{
				position = offset + j;
				topPixels[position] = makeBluePixel();
			}
			topProcessor.setPixels(topPixels);
		}
		return topProcessor;
	}
	
	private int makeBluePixel()
	{
		int red = 100;
		int green = 78;
		int blue = 230;
		
		int finalPixel = 0;
		finalPixel = (finalPixel | (red << 16)); 
		finalPixel = (finalPixel | (green << 8)); 
		finalPixel = (finalPixel | blue);
		
		return finalPixel;
	}
	
	private ProbeitImage makeProbeitImage(int[] pixels)
	{
		ImagePlus ip = NewImage.createRGBImage("overlayed", newWidth, newHeight, 1, NewImage.FILL_WHITE);
		ImageProcessor overlayedProcessor = ip.getProcessor();
		overlayedProcessor.setPixels(pixels);
		return new ProbeitImage(overlayedProcessor.createImage(), newWidth, newHeight);
	}
	
	private int grey2White(int pixel)
	{
		int red = (int) (pixel & 0xff0000) >> 16;
		int green = (int) (pixel & 0x00ff00) >> 8;
		int blue = (int) (pixel & 0x0000ff);
		
		if(red == 128 && green == 128 && blue == 128)	
			return -1;
		return pixel;	
	}
	
	private int overlayPixel(int topPixel, int bottomPixel, int topWeight, int bottomWeight)
	{
		if(topPixel == -1)
			return bottomPixel;
			
		int topRed = (int) (topPixel & 0xff0000) >> 16;
		int topGreen = (int) (topPixel & 0x00ff00) >> 8;
		int topBlue = (int) (topPixel & 0x0000ff);
		
		int bottomRed = (int) (bottomPixel & 0xff0000) >> 16;
		int bottomGreen = (int) (bottomPixel & 0x00ff00) >> 8;
		int bottomBlue = (int) (bottomPixel & 0x0000ff);
		
		int red = (topWeight * topRed + bottomRed * bottomWeight)/(topWeight + bottomWeight);
		int green = (topWeight * topGreen + bottomGreen * bottomWeight)/(topWeight + bottomWeight);
		int blue = (topWeight * topBlue + bottomBlue * bottomWeight)/(topWeight + bottomWeight);
		
		int finalPixel = 0;
		finalPixel = (finalPixel | (red << 16)); 
		finalPixel = (finalPixel | (green << 8)); 
		finalPixel = (finalPixel | blue);
		
		return finalPixel;
	}
}
