package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.model.ViewsModel;

public class BookmarkGravityAction_new extends ProbeItGenericAction
{
	public BookmarkGravityAction_new()
	{
		super("Gravity Map", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://rio.cs.utep.edu/ciserver/ciprojects/GravityMapProvenance/gravityContourMap.ps_038568341971146025.owl#answer");
	}

}
