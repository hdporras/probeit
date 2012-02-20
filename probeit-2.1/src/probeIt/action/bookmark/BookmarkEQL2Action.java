package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;

public class BookmarkEQL2Action extends ProbeItGenericAction
{
	public BookmarkEQL2Action()
	{
		super("2008-09-18T19:38:09Z", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://iw.vsto.org/pml/extendedQLProduct_1222276800122.owl#answer");
	}
}
