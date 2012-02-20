package probeIt.viewerFramework.viewers;

import java.awt.Font;
import javax.swing.*;
public class Viewer_HTML extends JScrollPane implements Viewer 
{
	public void initialize(String text)
	{
		JEditorPane htmlPane = new JEditorPane("text/html", text);
		setViewportView(htmlPane);
	}
	
	public String getViewerName()
	{return "HTML Viewer";};
	
	public JComponent getViewerInterface(Object viewable)
	{
		if(viewable instanceof String)
		{
			initialize((String)viewable);
			return this;
		}
		else
			return null;
	}
	
	public JComponent getViewerInterface()
	{return this;}
	
	public int getLogicalWidth()
	{return ViewerStyle.DEFAULT_WIDTH;}
	
	public int getLogicalHeight()
	{return ViewerStyle.DEFAULT_HEIGHT;}
}
