package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;

public class Feature_L_Action extends FeatureCheckBoxAction
{
	public Feature_L_Action()
	{
		super("L: local view shown first");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
			ViewsManager.getInstance().enableFeature_L();
		
		else
			ViewsManager.getInstance().disableFeature_L();
		
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_L();
	}
}
