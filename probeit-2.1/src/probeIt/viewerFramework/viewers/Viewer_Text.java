package probeIt.viewerFramework.viewers;

import java.awt.Font;
import java.net.URL;

import javax.swing.*;

import probeIt.util.GetURLContents;
public class Viewer_Text extends JScrollPane implements Viewer 
{
	public void initialize(String text)
	{
		JTextArea textArea = new JTextArea();
		textArea.setText(text);
		textArea.setFont(new Font("Courier", Font.PLAIN, 12));
		setViewportView(textArea);
		
		System.out.println(text);
	}

	public void initializeTextFromURI(String uri)
	{
		String text;
		try
		{
			text = GetURLContents.downloadText(uri);

		}catch(Exception e){
			e.printStackTrace();
			text = uri;
		}
		System.out.println(text);
		
		JTextArea textArea = new JTextArea();
		textArea.setText(text);
		textArea.setFont(new Font("Courier", Font.PLAIN, 12));
		setViewportView(textArea);
	}

	public String getViewerName()
	{return "Text Viewer";};

	public JComponent getViewerInterface(Object viewable)
	{
		if(viewable instanceof String)
		{
			URL url = null;
			try
			{
				url = new URL((String)viewable);
			}catch(Exception e){System.out.println("String '"+viewable+"' not a url");/*e.printStackTrace();*/ url=null;}
			
			if(url != null)
			{
				System.out.println("Is URL");
				initializeTextFromURI((String)viewable);
			}
			else
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
	
	public static void main(String[] args)
	{
		Viewer_Text text = new Viewer_Text();
		text.getViewerInterface("http://rio.cs.utep.edu/ciserver/ciprojects/ScientificPublication/v2-input.txt");
	}
}
