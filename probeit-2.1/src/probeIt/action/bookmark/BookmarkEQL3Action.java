package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;

public class BookmarkEQL3Action extends ProbeItGenericAction
{
	public BookmarkEQL3Action()
	{
		super("Low clouds passing over...", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://iw.cs.utep.edu/astronomy/solar/eql-pml/extendedQLProduct_1222896409993.owl#answer");
	}
}
