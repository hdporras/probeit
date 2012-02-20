package probeIt.ui.popup.source;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import pml.PMLNode;
import pml.util.pml.PMLSourceRetrieval;
import probeIt.ui.popup.Manager;
import probeIt.ui.popup.source.PopupSourceView;
import probeIt.ui.popup.trust.TrustMapBuilder;
import probeIt.viewerFramework.ViewerFactory;
import probeIt.viewerFramework.viewers.imaging.Overlay;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
public class PopupSourceView extends JFrame
{
	
	static PopupSourceView instance;
	PMLNode node;
	boolean forcedEnd=false;
	
	public PopupSourceView(PMLNode nodeSet)
	{
		super("Sources for: " + nodeSet.getID());
		Manager.newFrame(this);
		instance = this;
		node = nodeSet;
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JLabel("Pending..."));
		
		JScrollPane scroll = new JScrollPane();//how to add scroll*??!!!
		add(scroll, BorderLayout.EAST);
		
		String sources = PMLSourceRetrieval.getSources(nodeSet);
		JTextArea sourceTextArea = new JTextArea("Sources:\n" + sources);
		JScrollPane pane = new JScrollPane(sourceTextArea);
		getContentPane().add(pane);
		
		setSize(300, 400);
		setVisible(true);
		
	}
}


