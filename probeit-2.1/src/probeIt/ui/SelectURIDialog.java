package probeIt.ui;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Frame;

import probeIt.ProbeIt;
import probeIt.util.PMLNodesetURIsFromURL;
//import iw.model.*;
//import iw.model.util.*;


public class SelectURIDialog extends JDialog implements ActionListener
{

	private JScrollPane scrollPane = new JScrollPane();
	private JButton openb = new JButton("Select");
	private JButton cancelb = new JButton("Cancel");
	private JPanel mainPanel = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();

	private String selectedURI = "";
	
	/*
	public static void main(String[] args)
	{
		java.util.List<String> uris = PMLNodesetURIsFromURL.getNodesetURIsFromURL("//saturn/students/hdporras/Desktop/uri2.xml");//new java.util.LinkedList<String>();
		//uris.add("C:/Users/Hugo/Desktop/temp/uri.xml");
		//JPanel p = new JPanel();
		
		JFrame frame = new JFrame();
		
		SelectURIDialog uriD = new SelectURIDialog(frame, uris);
	}*/

	public SelectURIDialog(Frame parentFrame, java.util.List<String> uris)
	{
		super(parentFrame, true);

		int nuris = uris.size();
		setSize(500, (nuris + 2) * 50);
		setLocation(300, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.white);

		topPanel.setBackground(Color.white);
		centerPanel.setBackground(Color.white);
		bottomPanel.setBackground(Color.white);

		CreateTopPane();
		CreateCenterPane(uris);
		CreateBottomPane();

		scrollPane.add(centerPanel);
		centerPanel.setVisible(true);
		scrollPane.setVisible(true);
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		//add(mainPanel);
		getContentPane().add(mainPanel);
		
		setVisible(true);

	}

	private void CreateTopPane()
	{
		topPanel.add(new JLabel("Select NodeSet"));
	}

	private void CreateCenterPane(java.util.List<String> uris)
	{
		centerPanel.setLayout(new GridLayout(uris.size(), 1));
		ButtonGroup bg = new ButtonGroup();
		if (uris != null)
		{
			for (int i = 0; i < uris.size(); i++)
			{
				//System.out.println("inside");
				
				String uriStr = uris.get(i);//currentURI = uris.get(i);

				String rname = uriStr.substring(uriStr.lastIndexOf("#"));
				//String desc = currentURI.getDescription();
				// if (desc.length() > 50) {
				// desc = desc.subSequence(0, 49) + " ... ";
				// }
				JRadioButton rb = new JRadioButton(rname + ": " + uriStr);
				rb.setBackground(Color.white);
				//if (currentURI.getIsTop())
					//rb.setForeground(Color.red);
				SelectionListener sl = new SelectionListener(uriStr);//currentURI.getUriStr());
				rb.addActionListener(sl);
				bg.add(rb);
				centerPanel.add(rb);
			}
		}
	}
	private void CreateBottomPane()
	{
		bottomPanel.setLayout(new FlowLayout());
		openb.addActionListener(this);
		cancelb.addActionListener(this);
		bottomPanel.add(openb);
		bottomPanel.add(cancelb);

	}

	public void actionPerformed(ActionEvent event)
	{
		ProbeIt.getInstance().getWindow().setURI(selectedURI);

		this.dispose();
	}

	class SelectionListener implements ActionListener
	{
		String uriStr = "";
		public SelectionListener(String uriStr)
		{
			this.uriStr = uriStr;
		}
		public void actionPerformed(ActionEvent event)
		{
			selectedURI = uriStr;
		}
	}

}