package probeIt.viewerFramework.viewers.imaging;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import java.awt.Rectangle;
import java.awt.Image;
import java.util.HashMap;

public class AutoCrop
{
	int[] pixels;
	//static HashMap cache;
	int bgRed, bgBlue, bgGreen;
	int numRows, numCols;
	int topRow, bottomRow, leftCol, rightCol;

	ImageProcessor processor;

	/*/
	private static Object getCached(Object key)
	{return cache.get(key);}
	
	private static void cacheData(Object key, Object data)
	{cache.put(key, data);}
	*/
	
	public AutoCrop(Object image) throws IllegalArgumentException
	{
		/*
		if(cache == null)
			cache = new HashMap();
		
		ImagePlus imagePlus = (ImagePlus)AutoCrop.getCached(image);
		
		if(imagePlus != null)
		{
			init(imagePlus);
		}*/
		
		ImagePlus imagePlus;
		if(image instanceof Image)
		{
			imagePlus = new ImagePlus("image", (Image)image);
			//AutoCrop.cacheData(image, imagePlus);
			init(imagePlus);
		}
		else if(image instanceof String)
		{
			System.out.println("Image path "+(String)image);
			imagePlus = new ImagePlus((String)image);
			//AutoCrop.cacheData(image, imagePlus);
			init(imagePlus);
		}
		else
			throw new IllegalArgumentException("input to autocrop must be java.io.Image or a String url/path");
	}
	
	private void init(ImagePlus imagePlus)
	{
		processor = imagePlus.getProcessor().convertToRGB();
		processor.setInterpolate(true);

		pixels = (int[]) processor.getPixels();

		bgRed = (int) (pixels[0] & 0xff0000) >> 16;
		bgGreen = (int) (pixels[0] & 0x00ff00) >> 8;
		bgBlue = (int) (pixels[0] & 0x0000ff);

		numCols = processor.getWidth();
		numRows = processor.getHeight();
	}

	public ImageProcessor Crop()
	{
		findTop();
		findBottom();
		findLeft();
		findRight();

		Rectangle roi = new Rectangle(leftCol, topRow, rightCol - leftCol,
				bottomRow - topRow);
		processor.setRoi(roi);
		ImageProcessor cropped = processor.crop();
		return cropped;
	}
	public ProbeitImage crop()
	{

		findTop();
		findBottom();
		findLeft();
		findRight();

		Rectangle roi = new Rectangle(leftCol, topRow, rightCol - leftCol,
				bottomRow - topRow);
		processor.setRoi(roi);
		ImageProcessor cropped = processor.crop();
		return new ProbeitImage(cropped.createImage(), cropped.getWidth(), cropped
				.getHeight());
	}

	public ProbeitImage scale(int width, int height)
	{
		int oldHeight = processor.getHeight();
		int oldWidth = processor.getWidth();
		
		int newHeight;
		int newWidth;
		if(width > oldWidth)
			newWidth = oldWidth;
		else
			newWidth = width;
		
		if(height > oldHeight)
			newHeight = oldHeight;
		else
			newHeight = height;
		
		ImageProcessor resized = processor.resize(newWidth, newHeight);
		return new ProbeitImage(resized.createImage(), resized.getWidth(), resized
				.getHeight());

	}
	public ProbeitImage cropAndScale(int width, int height)
	{

		findTop();
		findBottom();
		findLeft();
		findRight();

		Rectangle roi = new Rectangle(leftCol, topRow, rightCol - leftCol,
				bottomRow - topRow);
		processor.setRoi(roi);
		ImageProcessor cropped = processor.crop();
		
		int oldHeight = processor.getHeight();
		int oldWidth = processor.getWidth();
		
		int newHeight;
		int newWidth;
		if(width > oldWidth)
			newWidth = oldWidth;
		else
			newWidth = width;
		
		if(height > oldHeight)
			newHeight = oldHeight;
		else
			newHeight = height;

		ImageProcessor resize = cropped.resize(newWidth, newHeight);
		return new ProbeitImage(resize.createImage(), resize.getWidth(), resize
				.getHeight());
	}

	private void findTop()
	{
		int offset, index;
		int red, green, blue;
		boolean foundDiffColor = false;
		;

		for (int i = 0; i < numRows; i++)
		{
			offset = i * numCols;
			for (int j = 0; j < numCols; j++)
			{
				index = offset + j;
				red = (int) (pixels[index] & 0xff0000) >> 16;
				green = (int) (pixels[index] & 0x00ff00) >> 8;
				blue = (int) (pixels[index] & 0x0000ff);

				if (red != bgRed && green != bgGreen && blue != bgBlue)
					foundDiffColor = true;
			}

			if (foundDiffColor)
			{
				topRow = i - 1;
				break;
			}
		}
	}

	private void findBottom()
	{
		int offset, index;
		int red, green, blue;
		boolean foundDiffColor = false;
		;

		for (int i = numRows - 1; i >= 0; i--)
		{
			offset = i * numCols;
			for (int j = 0; j < numCols; j++)
			{
				index = offset + j;
				red = (int) (pixels[index] & 0xff0000) >> 16;
				green = (int) (pixels[index] & 0x00ff00) >> 8;
				blue = (int) (pixels[index] & 0x0000ff);

				if (red != bgRed && green != bgGreen && blue != bgBlue)
					foundDiffColor = true;
			}

			if (foundDiffColor)
			{
				bottomRow = i + 1;
				break;
			}
		}
	}

	private void findLeft()
	{
		int index;
		int red, green, blue;
		boolean foundDiffColor = false;
		;

		for (int j = 0; j < numCols; j++)
		{
			for (int i = 0; i < numRows; i++)
			{
				index = j + i * numCols;
				red = (int) (pixels[index] & 0xff0000) >> 16;
				green = (int) (pixels[index] & 0x00ff00) >> 8;
				blue = (int) (pixels[index] & 0x0000ff);

				if (red != bgRed && green != bgGreen && blue != bgBlue)
					foundDiffColor = true;
			}

			if (foundDiffColor)
			{
				leftCol = j - 1;
				break;
			}
		}
	}
	private void findRight()
	{
		int index;
		int red, green, blue;
		boolean foundDiffColor = false;
		;

		for (int j = numCols - 1; j >= 0; j--)
		{
			for (int i = 0; i < numRows; i++)
			{
				index = j + i * numCols;
				red = (int) (pixels[index] & 0xff0000) >> 16;
				green = (int) (pixels[index] & 0x00ff00) >> 8;
				blue = (int) (pixels[index] & 0x0000ff);

				if (red != bgRed && green != bgGreen && blue != bgBlue)
					foundDiffColor = true;
			}

			if (foundDiffColor)
			{
				rightCol = j + 1;
				break;
			}
		}
	}
}
