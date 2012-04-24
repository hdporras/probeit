package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.ui.ProbeItView;
import probeIt.ui.SelectURIDialog;
import probeIt.ui.ViewsManager;
import probeIt.ui.Window;
import probeIt.ui.model.ViewsModel;
import probeIt.util.PMLNodesetURIsFromURL;

public class BuildProvenanceGraphAction extends ProbeItGenericAction 
{
	public BuildProvenanceGraphAction()
	{
		super("Prvenance Graph", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		//build Justification view using current state of ProbeItView
		//((ProbeItView)ViewsManager.getInstance().getViewPane()).buildJustificationView(ViewsManager.JustificationStyle.dag);
		ViewsModel.getInstance().setJustification();
		
	}

	public boolean shouldBeEnabled()
	{
		return true;
	}
	
}
