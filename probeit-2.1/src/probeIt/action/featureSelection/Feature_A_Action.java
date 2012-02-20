package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;
import probeIt.ui.ProbeItView;

import javax.swing.JCheckBoxMenuItem;

public class Feature_A_Action extends FeatureCheckBoxAction
{
	public Feature_A_Action()
	{
		super("A: avatar view without multimedia");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
		{
			((ProbeItView)ViewsManager.getInstance().getViewPane()).buildAvatarView(false);
			ViewsManager.getInstance().enableFeature_A();
			ProbeIt.getInstance().getWindow().getConfiguration().setColapsedValue(true);
			ProbeIt.getInstance().getWindow().getConfiguration().setHasAvatar(true);
		} else {
			ViewsManager.getInstance().disableFeature_A();
			ProbeIt.getInstance().getWindow().getConfiguration().setColapsedValue(false);
			ProbeIt.getInstance().getWindow().getConfiguration().setHasAvatar(false);
		}
		ProbeIt.getInstance().getWindow().updateView();
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_A();
	}
}
