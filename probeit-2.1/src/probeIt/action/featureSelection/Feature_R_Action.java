package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;

import javax.swing.JCheckBoxMenuItem;

public class Feature_R_Action extends FeatureCheckBoxAction
{
	public Feature_R_Action()
	{
		super("R: results view");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
			ViewsManager.getInstance().enableFeature_R();
		
		else
			ViewsManager.getInstance().disableFeature_R();
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_R();
	}
}
