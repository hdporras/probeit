package probeIt.ui;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;

import probeIt.ProbeIt;

import java.awt.GridLayout;
import javax.swing.JFrame;
/**
 * Creates a local copy of the current PML document, i.e., query or justication.
 * Asks for a base URI to replace the URIs in the original PML document. Asks
 * for a base path in the local filesystem to store the copy of the PML
 * document.
 * 
 * @author paulo
 * 
 */
public class AboutDialog extends JDialog implements ActionListener
{

	JPanel panel_center;
	JPanel panel_south;
	JButton okButton;


	public AboutDialog(Frame parentFrame)
	{
		super(parentFrame, "About", true);
		
		panel_center = new JPanel(new GridLayout(0, 1));
		panel_south = new JPanel();
		panel_center.add(new JLabel(" "));
		panel_center
				.add(new JLabel(
						"  Probe-It! - Scientific Provenance Visualization Framework  "));
		panel_center.add(new JLabel("  Version 0.4  (February 2007)"));
		panel_center.add(new JLabel(" "));
		panel_center.add(new JLabel("  University of Texas at El Paso"));
		panel_center.add(new JLabel(
				"  http://trust.utep.edu/ciminer/software/probeit"));
		panel_center.add(new JLabel(" "));
		okButton = new JButton("OK");

		getContentPane().add(panel_center, BorderLayout.CENTER);
		getContentPane().add(panel_south, BorderLayout.SOUTH);
		panel_south.add(okButton);
		setLocation(new Point(250, 300));
		pack();

		okButton.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		setVisible(false);
	}

}
