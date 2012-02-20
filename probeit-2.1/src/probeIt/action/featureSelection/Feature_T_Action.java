package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;

public class Feature_T_Action extends FeatureCheckBoxAction
{
	public Feature_T_Action()
	{
		super("T: trust map capabilities (requires result view)");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
			ViewsManager.getInstance().enableFeature_T();
		
		else
			ViewsManager.getInstance().disableFeature_T();
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_T();
	}
}
