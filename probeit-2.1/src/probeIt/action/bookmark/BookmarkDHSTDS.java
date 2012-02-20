package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;
import probeIt.ProbeIt;
public class BookmarkDHSTDS extends ProbeItGenericAction
{
	public BookmarkDHSTDS()
	{
		super("Route (Space) and Time Deviation", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://iw.cs.utep.edu/dhs/examples/deviations.owl#deviations_1");
	}
}
