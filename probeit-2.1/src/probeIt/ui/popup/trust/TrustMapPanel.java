package probeIt.ui.popup.trust;
import javax.swing.*;

import java.awt.BorderLayout;
import probeIt.viewerFramework.DAGContentFactory;
import probeIt.viewerFramework.ViewerFactory;
import probeIt.viewerFramework.viewers.Viewer_TrustMap;
import probeIt.viewerFramework.viewers.imaging.Overlay;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.*;

import java.awt.event.*;
public class TrustMapPanel extends JPanel implements AdjustmentListener
{
	Overlay overlayer;
	JPanel trustMapPanel;
	JPanel controlPanel;
	JScrollBar scrollBar;
	ProbeitImage scale;
	int topWeight;
	PopupTrustMapView parent;
	boolean error = false;
	
	public TrustMapPanel(PMLNode nodeset, PopupTrustMapView frame)
	{
		setLayout(new BorderLayout());
		parent = frame;
		init(nodeset);
		topWeight = 50;
		setTrustMap(topWeight, 100 - topWeight);
		setControlPanel();
		setVisible(true);
		setSize(700, 650);
	}
	
	private void setControlPanel()
	{

		if(!error)
		{
			controlPanel = new JPanel();
			controlPanel.setLayout(new BorderLayout());
			scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 50, 1, 0, 100);
			scrollBar.setBlockIncrement(10);
			scrollBar.addAdjustmentListener(this);
		
			JLabel label = new JLabel("less <- OPACITY of TRUST MAP -> more");
			controlPanel.add(scrollBar, BorderLayout.NORTH);
			controlPanel.add(label, BorderLayout.SOUTH);
		
			add(controlPanel, BorderLayout.SOUTH);
		}

	}
	
	private void init(PMLNode nodeset)
	{
		Viewer_TrustMap viewer = new Viewer_TrustMap();
		ProbeitImage trustMapImage = viewer.getTrustMapImage(nodeset);
		
		if(trustMapImage == null)
		{
			error = true;
			return;
		}
		
		DAGContentFactory selector = new DAGContentFactory();
		ProbeitImage conclusionImage = selector.getImage(nodeset.getConclusion(), 1000000, 100000);
		
		scale = viewer.getScaleImage();
		overlayer = new Overlay(conclusionImage.getImage(), trustMapImage.getImage());
	}
	
	private void setErrorNotification()
	{
		error = true;
		removeAll();
		add(new JLabel("Map must be based on data from multiple KNOWN PML sources in order to have an associated trust map..."), BorderLayout.CENTER);
	}
	
	private void setTrustMap(int topWeight, int bottomWeight)
	{
		if(trustMapPanel != null)
			remove(trustMapPanel);

		if(!error)
		{

		ProbeitImage trustMap = overlayer.getOverlay(topWeight, bottomWeight);
		trustMapPanel = new JPanel();
		trustMapPanel.setLayout(new BorderLayout());
		trustMapPanel.add(makeImageLabel(trustMap), BorderLayout.WEST);
	
		JPanel scalePanel = new JPanel();
		scalePanel.setLayout(new BorderLayout());
		scalePanel.add(new JLabel("High Trust"), BorderLayout.NORTH);
		scalePanel.add(new JLabel("Low Trust"), BorderLayout.SOUTH);
		scalePanel.setSize(60, 150);
		
	
		trustMapPanel.add(makeImageLabel(scale), BorderLayout.CENTER);
		trustMapPanel.add(scalePanel, BorderLayout.EAST);
		add(trustMapPanel, BorderLayout.NORTH);
		}
		else
			setErrorNotification();
		
		show();
		repaint();
	}
	
	private JLabel makeImageLabel(ProbeitImage image)
	{
		return new JLabel(new ImageIcon(image.getImage()));
	}
	
	public void adjustmentValueChanged(AdjustmentEvent e)
	{
			topWeight = scrollBar.getValue();
			setTrustMap(topWeight, 100 - topWeight);
			parent.show();
	}
}
