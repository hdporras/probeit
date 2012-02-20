package probeIt;
import java.util.ArrayList;
import gmt.GMTToolkit.ConnectionType;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Frame;

import javax.swing.JApplet;

public class ProbeItApplet extends JApplet
{
	ProbeIt app;
	public void init()
	{
		String aURI = this.getParameter("pmlURI");
		String features = this.getParameter("features");
		String remote = this.getParameter("remote");
		String globalStyle = this.getParameter("globalStyle");
		
		
		app = new ProbeIt(getContentPane(), this);
		
		
		ArrayList<String> params = new ArrayList<String>();
		
		if(aURI != null && !aURI.trim().equalsIgnoreCase(""))
			params.add("-u"+aURI);
		
		if(features != null)
			params.add("-f"+features);
		
		if(remote != null)
			params.add("-r"+remote);
			
		if(globalStyle != null)
			params.add("-s"+globalStyle);
		
		
		String[] paramArray = new String[params.size()];
		for(int i = 0; i < paramArray.length; i ++)
		{
			paramArray[i] = params.get(i);
		}
		
			app.setParameters(paramArray);
		
		
		app.setGMTConnectionType(ConnectionType.RMIService);
		app.openFullProbeIt();
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		System.out.println("width,height: " + width + "," + height);
		if(app != null)
			ProbeIt.getInstance().getFrame().setSize(width,height);
	}
}
