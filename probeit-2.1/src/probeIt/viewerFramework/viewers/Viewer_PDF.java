package probeIt.viewerFramework.viewers;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import org.jpedal.examples.simpleviewer.Commands;
import org.jpedal.examples.simpleviewer.SimpleViewer;

import probeIt.util.GetURLContents;

public class Viewer_PDF extends JPanel implements probeIt.viewerFramework.viewers.Viewer
{
	
	public String getViewerName()
	{return "PDF Viewer";};
	
	public int getLogicalWidth()
	{return ViewerStyle.DEFAULT_WIDTH;}
	
	public int getLogicalHeight()
	{return ViewerStyle.DEFAULT_HEIGHT;}
	
	public JComponent getViewerInterface(Object input)
	{
		if(input instanceof byte[])
		{
			initialize((byte[]) input);
			return this;
		}
		if(input instanceof String)
		{
			initialize((String)input);
			return this;
		}
		else
			return null;
	}
	
	private void initialize(String url)
	{
		try
		{
			System.out.println("Setting up PDF..");

		    SimpleViewer viewer = new SimpleViewer(this, null);
		    viewer.setupViewer();

		    byte[] data = GetURLContents.downloadFile(url);
		    
		    Object[] input = new Object[]{data, "PDF"};
		    viewer.executeCommand(Commands.OPENFILE, input);
		    
		    /*
		    Object[] input; 
		    //Specify file you wish to open (JPedal handles getting the byte data)
		    input = new Object[]{url};
		    
		    viewer.executeCommand(Commands.OPENURL, input);
		    */
		}
		catch(Exception e)
		{
			add(new JLabel(e.getMessage()));
			e.printStackTrace();
		}
		
		System.out.println("Finished");
	}
	
	private void initialize(byte[] pdfFile)
	{
		try
		{
			System.out.println("Setting up PDF (byteArray)..");

		    SimpleViewer viewer = new SimpleViewer(this, null);
		    viewer.setupViewer();
		    
//		    Open file by passing in the filename and a byte array
		    Object[] input = new Object[]{pdfFile, "PDF"};
		    viewer.executeCommand(Commands.OPENFILE, input);
		}
		catch(Exception e)
		{
			add(new JLabel(e.getMessage()));
			e.printStackTrace();
		}
		
		System.out.println("Finished");
	}
	
	public JComponent getViewerInterface()
	{return this;}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
	    frame.getContentPane().setLayout(new BorderLayout());
	    
	    Viewer_PDF pdfContainer = new Viewer_PDF();
	    
	    String filename = "http://iw.cs.utep.edu:80/VisCacheAccess/cached/16.pdf";//"http://rio.cs.utep.edu/ciserver/ciprojects/ScientificPublication/ipaw-probeit.pdf";
	    byte[] data = GetURLContents.downloadFile(filename);
	    
	    pdfContainer.getViewerInterface(filename);//data);
	    //pdfContainer.getViewerInterface("http://rio.cs.utep.edu/ciserver/ciprojects/ScientificPublication/ipaw-probeit.pdf");
	    
	    frame.add(pdfContainer, BorderLayout.CENTER);
	    pdfContainer.setVisible(true);
	    frame.setTitle("SimpleViewer in External Viewer");
	    frame.setSize(800, 600);
	    frame.setVisible(true);
	}
}
