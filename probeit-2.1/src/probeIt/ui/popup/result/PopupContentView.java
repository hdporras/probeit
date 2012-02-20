package probeIt.ui.popup.result;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Insets;

import pml.impl.serializable.PMLConclusion;
import probeIt.viewerFramework.ViewerFactory;
import probeIt.ui.popup.Manager;
//import probeIt.ui.popup.HoverOverPopupInteractor;

public class PopupContentView extends JFrame
{
	JComponent viewer;
	int oldWidth, oldHeight;
	double ratio;
	Insets insets;
	JButton keep;
	JButton stationary;
	public boolean stayPut = false;

	public PopupContentView(PMLConclusion theConclusion, boolean singleViewOnly)
	{
		Manager.newFrame(this);
		this.setTitle(theConclusion.getID() + theConclusion.getConclusionClassifier().getNiceFormat());
		ViewerFactory selector = new ViewerFactory();

		//setting toolbar 
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new FlowLayout());
		toolbar.setBackground(Color.LIGHT_GRAY);
		keep = new JButton("Keep");
		keep.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				keepAction(evt);
			}
		});

		System.out.println("Adding keep button...");
		toolbar.add(keep);
		
		stationary = new JButton("Stationary");
		stationary.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stationaryAction(evt);
			}
		});

		System.out.println("Adding stationary button...");
		toolbar.add(stationary);
		
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(toolbar, BorderLayout.PAGE_START);
		
		if(singleViewOnly)
		{
			System.out.println("Single line...");
			viewer = selector.selectPrimaryViewer(theConclusion);
		}
		else
		{
			System.out.println("not single view...");
			viewer = selector.selectViewers(theConclusion);
		}
		
		viewer.setOpaque(true);
		getContentPane().add(viewer, BorderLayout.CENTER);
		insets = getInsets();
		oldWidth = viewer.getWidth()+insets.left+insets.right;
		oldHeight = viewer.getHeight()+insets.top+insets.bottom;
		ratio = ((double)oldWidth)/((double)oldHeight);
		
		if (oldWidth > 0 && oldHeight > 0)
			setSize(oldWidth, oldHeight);
		else
			setSize(200, 200);
		
		setVisible(true);
	}
	
	public void changeContent(PMLConclusion theConclusion, boolean singleViewOnly)
	{
		keep.setVisible(true);
		
		ViewerFactory selector = new ViewerFactory();
		this.remove(viewer);
		
		if(singleViewOnly)
			viewer = selector.selectPrimaryViewer(theConclusion);
		else
			viewer = selector.selectViewers(theConclusion);
		
		viewer.setOpaque(true);
		add(viewer);
		//setContentPane(viewer);
		
		setVisible(true);

	}
	
	
	private void keepAction(java.awt.event.ActionEvent evt)
	{
		keep.setVisible(false);
		stationary.setVisible(false);
		Manager.latestPopup = null;
		System.out.println("KEEPING");
		
		//repaint();
	}
	
	private void stationaryAction(java.awt.event.ActionEvent evt)
	{
		stayPut = !stayPut;
		if(stayPut)
		{
			stationary.setText("Not Stationary");
			System.out.println("Staying put.");
		}
		else
		{
			stationary.setText("Stationary");
			System.out.println("Viewer Updating to Mouse Position");
		}
	}
}
