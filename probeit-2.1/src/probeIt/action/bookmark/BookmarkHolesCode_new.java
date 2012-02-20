package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.model.ViewsModel;

public class BookmarkHolesCode_new extends ProbeItGenericAction
{
	public BookmarkHolesCode_new()
	{
		super("Holes Code Provenance", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://rio.cs.utep.edu/ciserver/ciprojects/HolesCodeProvenance/DVelocityModelProduct_01044731520407759.owl#answer");
	}



}
