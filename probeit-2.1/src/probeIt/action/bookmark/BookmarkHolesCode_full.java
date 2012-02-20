package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.model.ViewsModel;

public class BookmarkHolesCode_full extends ProbeItGenericAction
{
	public BookmarkHolesCode_full()
	{
		super("Holes Code Full", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://rio.cs.utep.edu/ciserver/ciprojects/HolesCodeFullPML/DVelocityModelProduct_07166223683518539.owl#answer");
	}



}
