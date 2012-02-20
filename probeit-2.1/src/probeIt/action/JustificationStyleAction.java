package probeIt.action;

import java.awt.event.ActionEvent;

import pml.PMLNode;
import probeIt.ui.global.JustificationBuilder;
import probeIt.ui.global.JustificationView;
import probeIt.ui.ViewsManager.JustificationStyle;
import javax.swing.JComboBox;

import probeIt.ProbeIt;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.Window;
//import probeIt.util.URITest;

/**
 * Set the user interface URI after verifying that the URI is valid.
 * 
 * @author paulo
 */
public class JustificationStyleAction extends ProbeItGenericAction
{

	private String _URI;

	public JustificationStyleAction()
	{
		super("Justification Style", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		JComboBox justificationStyle = (JComboBox)e.getSource();
		if(justificationStyle.getSelectedIndex() == 0)
			ProbeIt.getInstance().getEmbeddedProbeIt().setCurrentJustificationStyle(JustificationStyle.tree);

		else if(justificationStyle.getSelectedIndex() == 1)
			ProbeIt.getInstance().getEmbeddedProbeIt().setCurrentJustificationStyle(JustificationStyle.dag);
			
		else if(justificationStyle.getSelectedIndex() == 2)
			ProbeIt.getInstance().getEmbeddedProbeIt().setCurrentJustificationStyle(JustificationStyle.forest);

		else if(justificationStyle.getSelectedIndex() == 3)
			ProbeIt.getInstance().getEmbeddedProbeIt().setCurrentJustificationStyle(JustificationStyle.questions);
		
    	//PMLNode node = ProbeIt.getInstance().getEmbeddedProbeIt().getJustification();
    	//ProbeIt.getInstance().getEmbeddedProbeIt().setJustification(node, true);
		((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.GLOBAL_JUSTIFICATION_TAB);
	}

	public boolean shouldBeEnabled()
	{
		return true;
	}
}