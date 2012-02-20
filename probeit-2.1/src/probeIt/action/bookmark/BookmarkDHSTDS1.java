package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;
import probeIt.ProbeIt;
public class BookmarkDHSTDS1 extends ProbeItGenericAction
{
	public BookmarkDHSTDS1()
	{
		super("Route (Space) and Time Deviation with Weather Justification", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://iw.cs.utep.edu/dhs/examples/justifiedDeviation.owl#justifiedDeviation_1");
	}
}
