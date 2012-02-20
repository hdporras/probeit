package probeIt.ui.popup.source;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import probeIt.ui.popup.Manager;

public class PopupPMLSourceView extends JFrame
{
	public PopupPMLSourceView(String text, String id)
	{
		super("PML Source Viewer");//for: " + id);
		Manager.newFrame(this);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JLabel("Pending..."));
		
		JScrollPane scroll = new JScrollPane();//how to add scroll*??!!!
		add(scroll, BorderLayout.EAST);
		
		//String sources = PMLSourceRetrieval.getSources(nodeSet);
		JTextArea sourceTextArea = new JTextArea("PML Source: \n\n"+text);
		JScrollPane pane = new JScrollPane(sourceTextArea);
		getContentPane().add(pane);
		
		setSize(450, 450);
		setVisible(true);
	}
}
