package probeIt.action.bookmark;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.action.ProbeItGenericAction;
import probeIt.ui.model.ViewsModel;

public class BookmarkScientificPub extends ProbeItGenericAction
{
	public BookmarkScientificPub()
	{
		super("Scientific Pub", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ViewsModel.getInstance().newContext();
		ProbeIt.getInstance().getWindow().setURI("http://rio.cs.utep.edu/ciserver/ciprojects/ScientificPublication/ipaw-probeit.pdf_041364756901066657.owl#answer");
	}
}
