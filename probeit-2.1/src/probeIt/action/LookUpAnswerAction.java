package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.ui.Window;
//import probeIt.util.URITest;
import probeIt.ui.model.ViewsModel;

/**
 * Set the user interface URI after verifying that the URI is valid.
 * 
 * @author paulo
 */
public class LookUpAnswerAction extends ProbeItGenericAction
{

	private String _URI;

	public LookUpAnswerAction()
	{
		super("Look Up Answer", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		Window _ui = ProbeIt.getInstance().getWindow();
		_URI = _ui.getURI().trim();
		
		ViewsModel.getInstance().setAnswer(_URI);
		
		//colapse menu after uri is choosen
		ProbeIt.getInstance().getWindow().getConfiguration().setColapsedValue(true);
		ProbeIt.getInstance().getWindow().updateView();
	}

	public boolean shouldBeEnabled()
	{
		return true;
	}
}