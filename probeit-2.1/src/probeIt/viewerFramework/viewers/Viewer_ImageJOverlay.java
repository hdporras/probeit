package probeIt.viewerFramework.viewers;
import ij.process.ImageProcessor;
import javax.swing.*;

import java.awt.Image;
import javax.swing.ImageIcon;

import probeIt.viewerFramework.viewers.imaging.AutoCrop;
public class Viewer_ImageJOverlay extends JScrollPane
{
	ImageProcessor initial_ip;
	ImageProcessor current_ip;
	int oldWidth, oldHeight;
	double ratio;

	public Viewer_ImageJOverlay(Image bottomImage, Image topImage)
	{
		AutoCrop ac = new AutoCrop(bottomImage);
		AutoCrop ac1 = new AutoCrop(topImage);
		
		initial_ip = ac.Crop();
		current_ip = ac.Crop();

		oldWidth = current_ip.getWidth();
		oldHeight = current_ip.getHeight();

		ratio = ((double) current_ip.getWidth()) / ((double) current_ip.getHeight());
	
		setImage();
	}

	public int getLogicalWidth()
	{return initial_ip.getWidth();}
	
	public int getLogicalHeight()
	{return initial_ip.getHeight();}
	
	public JComponent getViewerInterface()
	{return this;}
	
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
	}

	
	
	public void setImage()
	{
		if(current_ip != null)
		{
			ImageIcon icon = new ImageIcon(current_ip.createImage());
			setPreferredSize(new java.awt.Dimension(current_ip.getWidth(), current_ip.getHeight()));
			JLabel label = new JLabel(icon);
			setViewportView(label);
		}	
	}
}
