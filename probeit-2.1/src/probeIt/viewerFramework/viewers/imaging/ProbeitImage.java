package probeIt.viewerFramework.viewers.imaging;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.net.URL;


public class ProbeitImage
{
	private Image img;
	private int w, h;
	
	public ProbeitImage(String url, int width, int height)
	{
		//url = url.replaceFirst("/usr/local/tomcat/tomcat5.0.28/webapps", "http://iw.cs.utep.edu:8080");
		System.out.println("URL: "+url);
		
		try {
		    img = ImageIO.read(new URL(url));
		} catch (Exception e) {
			e.printStackTrace();
		}
		w = width;
		h = height;
	}

	public ProbeitImage(Image image, int width, int height)
	{

		img = image;
		w = width;
		h = height;
	}

	public Image getImage()
	{
		return img;
	}

	public int getWidth()
	{
		return w;
	}

	public int getHeight()
	{
		return h;
	}
}
