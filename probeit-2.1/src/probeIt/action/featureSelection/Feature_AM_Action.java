package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;
import probeIt.ui.ProbeItView;

import javax.swing.JCheckBoxMenuItem;

public class Feature_AM_Action extends FeatureCheckBoxAction
{
	public Feature_AM_Action()
	{
		super("A: avatar view with multimedia");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
		{
			((ProbeItView)ViewsManager.getInstance().getViewPane()).buildAvatarView(true);
			ViewsManager.getInstance().enableFeature_A();
		}
		else {
			ViewsManager.getInstance().disableFeature_A();
		}
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_A();
	}
}
