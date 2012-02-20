package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;
import probeIt.ui.ViewsManager;

public class Feature_J_Action extends FeatureCheckBoxAction
{

	public Feature_J_Action()
	{
		super("J: justification view");
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_J();
	}
	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
			ViewsManager.getInstance().enableFeature_J();
		
		else
			ViewsManager.getInstance().disableFeature_J();
		
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	}
}
