package probeIt.viewerFramework.viewers;

import ij.process.ImageProcessor;
import javax.swing.*;

import javax.swing.ImageIcon;
import java.awt.Image;

import probeIt.viewerFramework.viewers.imaging.AutoCrop;
public class Viewer_ImageJ extends JScrollPane implements Viewer
{
	ImageProcessor initial_ip;
	ImageProcessor current_ip;
	int oldWidth, oldHeight;
	double ratio;

	public String getViewerName()
	{return "ImageJ Viewer";};
	
	private void initImageWithImage(Image pngImage)
	{
		crop(pngImage);
		oldWidth = current_ip.getWidth();
		oldHeight = current_ip.getHeight();

		ratio = ((double) current_ip.getWidth()) / ((double) current_ip.getHeight());
	
		setImage();
	}
	
	private void initImage(String pngFile)
	{
		crop(pngFile);
		oldWidth = current_ip.getWidth();
		oldHeight = current_ip.getHeight();

		ratio = ((double) current_ip.getWidth()) / ((double) current_ip.getHeight());
	
		setImage();
	}
	
	public void crop(Object pngFile)
	{
		if(pngFile instanceof String)
		{
			AutoCrop ac = new AutoCrop((String)pngFile);
			initial_ip = ac.Crop();
			current_ip = ac.Crop();
		}
		if(pngFile instanceof Image)
		{
			AutoCrop ac = new AutoCrop((Image)pngFile);
			initial_ip = ac.Crop();
			current_ip = ac.Crop();
		}
	}

	public int getLogicalWidth()
	{return initial_ip.getWidth();}
	
	public int getLogicalHeight()
	{return initial_ip.getHeight();}
	
	public JComponent getViewerInterface(Object reference)
	{
		if(reference instanceof String)
		{
			initImage((String)reference);
			setImage();
			return this;
		}
		if(reference instanceof Image)
		{
			initImageWithImage((Image)reference);
			setImage();
			return this;
		}
		else
			return null;
	}
	/*
	public void repaint()
	{

		int width = getWidth();
		int height = getHeight();
		
		if (width > 0 && height > 0)
		{
			if (oldWidth != width && oldHeight != height)
			{
				if (Math.abs(height-oldHeight) >= Math.abs(width-oldWidth))
				{
					oldHeight = height;
					oldWidth = (int) (ratio * ((double) oldHeight));
				}
				else
				{
					oldWidth = width;
					oldHeight = (int) ((double)oldWidth / ratio);
				}
			}
			current_ip = initial_ip.resize(oldWidth, oldHeight);
		}
		setImage();
	}*/
	
	public void repaint()
	{
/*
		int width = getWidth();
		int height = getHeight();
		
		if(width > 0 && height > 0)
			current_ip = initial_ip.resize(width, height);
	*/	
		setImage();
	}

	
	
	public void setImage()
	{
		if(current_ip != null)
		{
			ImageIcon icon = new ImageIcon(current_ip.createImage());
			setPreferredSize(new java.awt.Dimension(current_ip.getWidth()+1, current_ip.getHeight()+1));
			JLabel label = new JLabel(icon);
			setViewportView(label);
		}	
	}
}
