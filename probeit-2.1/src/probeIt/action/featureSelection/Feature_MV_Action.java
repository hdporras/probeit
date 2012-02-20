package probeIt.action.featureSelection;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.ViewsManager;
import probeIt.ui.HTMLFrame;

public class Feature_MV_Action extends FeatureCheckBoxAction
{
	public Feature_MV_Action()
	{
		super("MV: multiple viewer capabilities (requires requires justification view)");
	}

	public void actionPerformed(ActionEvent e)
	{
		if(!shouldBeSelected())
		{
			ViewsManager.getInstance().enableFeature_MV();
			System.out.println("enableing");
		}
		
		else
		{
			ViewsManager.getInstance().disableFeature_MV();
			System.out.println("disableing");
		}
		//ViewsManager.getInstance().updatePane();
		FeatureCheckBoxGroup.updateButtons();
	
	}

	public boolean shouldBeSelected()
	{
		return ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_MV();
	}
}
