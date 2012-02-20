package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;

public class BookmarkTPTP2PMLAgatha extends ProbeItGenericAction
{
	public BookmarkTPTP2PMLAgatha()
	{
		super("TPTP proof <Agatha>", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://inference-web.org/proofs/tptp/Problems/PUZ/PUZ001+1/query.owl#query");
	}

}
