package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;

public class BookmarkTPTP2PML extends ProbeItGenericAction
{
	public BookmarkTPTP2PML()
	{
		super("TPTP proof <PUZ031+1>", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://inference-web.org/proofs/tptp/Solutions/PUZ/PUZ031+1/EP---0.999/answer.owl#answer");
	}

}
