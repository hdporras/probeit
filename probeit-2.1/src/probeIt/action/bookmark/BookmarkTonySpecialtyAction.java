package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;

public class BookmarkTonySpecialtyAction extends ProbeItGenericAction
{
	public BookmarkTonySpecialtyAction()
	{
		super("Tony's Specialty", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://inference-web.org/proofs/tonys/tonys.owl#tonys");
	}

}
