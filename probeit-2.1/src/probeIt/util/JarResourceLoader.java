package probeIt.util;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class JarResourceLoader
{

	public ImageIcon getImageIcon(String imageName)
	{
		try
		{
			ClassLoader cl = this.getClass().getClassLoader();
			ImageIcon imageIcon  = new ImageIcon(cl.getResource("probeIt/image/" + imageName));
			return imageIcon;

		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private String getProofNodeTemplateURL()
	{
		ClassLoader cl = this.getClass().getClassLoader();
		return cl.getResource("org.inference_web.iw.app.browser.jsp.ProofNodeTemplate.xml").toString();
	}
	
	public Image getImage(String imageName)
	{
		try
		{
			ClassLoader cl = this.getClass().getClassLoader();
			return ImageIO.read(cl.getResource("probeIt/image/" + imageName));

		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getImageURL(String imageName)
	{
		try
		{
			ClassLoader cl = this.getClass().getClassLoader();
			return cl.getResource("probeIt/image/" + imageName).toString();

		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}

}
