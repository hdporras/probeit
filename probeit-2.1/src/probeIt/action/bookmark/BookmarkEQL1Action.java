package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;

public class BookmarkEQL1Action extends ProbeItGenericAction
{
	public BookmarkEQL1Action()
	{
		super("2008-09-18T17:54:09Z", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://iw.vsto.org/pml/extendedQLProduct_1222276649909.owl#answer");
	}
}
