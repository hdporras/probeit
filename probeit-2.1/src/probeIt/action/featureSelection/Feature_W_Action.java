package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;

import javax.swing.JCheckBoxMenuItem;

public class Feature_W_Action extends FeatureCheckBoxAction
{
	public Feature_W_Action()
	{
		super("W: workflow view");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
			ViewsManager.getInstance().enableFeature_W();
		
		else
			ViewsManager.getInstance().disableFeature_W();
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_W();
	}
}
