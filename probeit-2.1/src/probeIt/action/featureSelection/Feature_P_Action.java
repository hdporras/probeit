package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;

public class Feature_P_Action extends FeatureCheckBoxAction
{
	public Feature_P_Action()
	{
		super("P: provenance view (requires justification view)");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
			ViewsManager.getInstance().enableFeature_P();
		
		else
			ViewsManager.getInstance().disableFeature_P();
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_P();
	}
}
