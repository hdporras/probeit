package probeIt.ui.query;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.*;

import probeIt.ProbeIt;
import pml.PMLNode;
import probeIt.ui.ViewsManager;
import probeIt.ui.popup.result.PopupContentView;
import probeIt.ui.popup.trust.PopupTrustMapView;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.Icon;

public class ButtonPanel extends JPanel implements ActionListener
{
	JButton viewButton;
	JButton trustButton;
	PMLNode nodeset;
	
	public ButtonPanel(PMLNode node)
	{
		nodeset = node;
		
		setBackground(Color.WHITE);
		
	    Icon viewIcon = new ImageIcon(getClass().getResource("/probeIt/image/view-icon.PNG"));
	    Icon trustIcon = new ImageIcon(getClass().getResource("/probeIt/image/trust-icon.PNG"));
	    
	    //viewButton = new JButton("view");
	    viewButton = new JButton(viewIcon);
	    viewButton.setBorder(null);
	    viewButton.setBackground(null);
	    viewButton.addActionListener(this);
		viewButton.setActionCommand("view");
		
		//trustButton = new JButton("trust map");
		trustButton = new JButton(trustIcon);
	    trustButton.setBorder(null);
	    trustButton.setBackground(null);
		trustButton.addActionListener(this);
		trustButton.setActionCommand("trust map");
		
		if(!ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_T() || !node.getConclusion().getConclusionClassifier().isPS())
			trustButton.setEnabled(false);
		
		setLayout(new GridLayout(2,1));
		
		add(viewButton);
		add(trustButton);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		if(command.equalsIgnoreCase("view"))
		{
			ProbeIt.getInstance().getLogger().setViewerBool(true);
			boolean singleViewOnly = !ViewsManager.getInstance().isEnabledFeature_MV();
			new PopupContentView(nodeset.getConclusion(), singleViewOnly);
			
		}
		else if(command.equalsIgnoreCase("trust map"))
		{
			ProbeIt.getInstance().getLogger().setTrustBool(true);
			
			PopupTrustMapView view = new PopupTrustMapView(nodeset);
			view.buildMap();
		}
	}
}
