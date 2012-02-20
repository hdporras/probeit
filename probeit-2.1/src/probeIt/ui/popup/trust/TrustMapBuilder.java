package probeIt.ui.popup.trust;

import pml.*;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import probeIt.action.ProbeItActions;
import probeIt.ui.WindowApplication;
import probeIt.ui.workers.SwingWorker;

import javax.swing.JFrame;
public class TrustMapBuilder extends SwingWorker implements ActionListener
{
	PMLNode _node;
	PMLQuery _query;
	JDialog jd = null;
	JLabel lb;
	String _URI;
	JButton cancelButton = new JButton("Cancel");
	boolean forcedEnd = false;
	TrustMapPanel view;
	PopupTrustMapView parentFrame;
	
	public TrustMapBuilder(PMLNode node, PopupTrustMapView frame)
	{
		super();
		parentFrame = frame;
		_node = node;
		init();
	}

	private void init()
	{
		jd = new JDialog(parentFrame, "Status");
		JPanel sp = new JPanel();
		lb = new JLabel("Building Trust Map View ...");
		lb.setVisible(true);
		sp.add(lb);
		sp.setVisible(true);
		JProgressBar progress = new JProgressBar();
		progress.setPreferredSize(new Dimension(240, 20));
		progress.setMinimum(0);
		progress.setIndeterminate(true);
		progress.setValue(0);
		progress.setBounds(20, 35, 240, 20);
		sp.add(progress);
		cancelButton.addActionListener(this);
		sp.add(cancelButton);
		jd.getContentPane().add(sp);
		jd.setSize(new Dimension(310, 120));
		jd.setBounds(500, 500, 310, 120);
		// jd.setDefaultLookAndFeelDecorated(true);
		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jd.setVisible(true);
	}

	public Object construct()
	{
		view = new TrustMapPanel(_node, parentFrame);
		return view;
	}


	public void finished()
	{
		jd.dispose();
		
		if (!forcedEnd && view != null)
		{
			parentFrame.setTrustMapView(view);
		}
		//ProbeItActions.updateAllEnabled();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(cancelButton))
		{
			this.interrupt();
			forcedEnd = true;
			jd.dispose();
			parentFrame.setErrorNotification();
		}
		return;
	}

}

