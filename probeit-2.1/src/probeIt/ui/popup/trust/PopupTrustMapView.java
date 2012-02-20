package probeIt.ui.popup.trust;

import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;

import probeIt.ui.popup.Manager;
import probeIt.viewerFramework.ViewerFactory;
import probeIt.viewerFramework.viewers.imaging.Overlay;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.*;

import java.awt.event.*;

public class PopupTrustMapView extends JFrame
{
	PMLNode node;
	
	public PopupTrustMapView(PMLNode nodeSet)
	{
		super("Trust Map for Answer: " + nodeSet.getID());
		Manager.newFrame(this);
		node = nodeSet;
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JLabel("Pending..."));
		setSize(750, 600);
		
		setVisible(true);
	}
	
	public void buildMap()
	{
		TrustMapBuilder builder = new TrustMapBuilder(node, this);
		builder.start();
	}
	
	public void setTrustMapView(JComponent view)
	{
		getContentPane().removeAll();
		getContentPane().add(view, BorderLayout.CENTER);
		repaint();
		show();
	}
	
	public void setErrorNotification()
	{
		getContentPane().removeAll();
		getContentPane().add(new JLabel("Error making trustmap..."), BorderLayout.CENTER);
	}
}
