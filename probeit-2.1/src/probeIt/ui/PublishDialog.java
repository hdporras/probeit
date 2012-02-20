package probeIt.ui;
import java.awt.Frame;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import probeIt.ProbeIt;
//import probeIt.util.URITest;
//import probeIt.pml.PMLPublisher;


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
public class PublishDialog extends JDialog implements ActionListener
{

	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JLabel baseURILabel;
	JTextField baseURIField;
	JLabel basePathLabel;
	JTextField basePathField;
	JButton browseButton;
	JButton okButton;
	JButton cancelButton;
	String baseURI;
	String basePath;

	public PublishDialog(Frame frame)
	{

		super(frame, "Save As", true);
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		baseURILabel = new JLabel("Base URI ");
		baseURIField = new JTextField(30);
		basePathLabel = new JLabel("Base Path");
		basePathField = new JTextField(26);
		browseButton = new JButton("..");
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		getContentPane().add(panel1, BorderLayout.NORTH);
		getContentPane().add(panel2, BorderLayout.CENTER);
		getContentPane().add(panel3, BorderLayout.SOUTH);
		panel1.add(baseURILabel);
		panel1.add(baseURIField);
		panel2.add(basePathLabel);
		panel2.add(basePathField);
		panel2.add(browseButton);
		panel3.add(okButton);
		panel3.add(cancelButton);
		setLocation(new Point(220, 100));
		resetValues();
		pack();

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		browseButton.addActionListener(this);
		setVisible(true);
	}

	private void resetValues()
	{
		baseURIField.setText("");
		basePathField.setText(System.getProperty("user.dir"));
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(okButton))
		{
			baseURI = baseURIField.getText();
			//if (URITest.validURI(baseURI, false))
			if(true)
			{
				basePath = basePathField.getText();
				try
				{
					//PMLPublisher.publish(ProbeItViews.getInstance().getJustification().getURI(), baseURI, basePath);
					JOptionPane.showMessageDialog(null,
							"PML document saved");
				} catch (Exception _e)
				{
					JOptionPane.showMessageDialog(null, "Problem generating local PML document.",
							"Saving PML document",
							JOptionPane.WARNING_MESSAGE);
				}
				resetValues();
				setVisible(false);
			}
		} else if (e.getSource().equals(cancelButton))
		{
			baseURI = "";
			basePath = "";
			resetValues();
			setVisible(false);
		} else if (e.getSource().equals(browseButton))
		{
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int retVal = fc.showOpenDialog(this);
			if (retVal == JFileChooser.APPROVE_OPTION)
			{
				basePathField.setText(fc.getSelectedFile().getPath() + "/");
			}
		}
	}

	public String getBaseURI()
	{
		return baseURI;
	}

	public String getBasePath()
	{
		return basePath;
	}
}
