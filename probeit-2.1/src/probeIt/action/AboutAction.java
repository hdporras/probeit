package probeIt.action;
import probeIt.ProbeIt;
import java.awt.event.ActionEvent;

import probeIt.ui.WindowApplication;
import probeIt.ui.AboutDialog;

/**
 * Identifies ProbeIt's authors, organization and version.
 * 
 * @author paulo
 */

public class AboutAction extends ProbeItGenericAction
{
	public AboutAction(String name)
	{
		super(name, NO_ICON);
	}

	public AboutAction()
	{
		super("About", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		AboutDialog _ad = new AboutDialog(ProbeIt.getInstance().getFrame());
	}
	public boolean shouldBeEnabled()
	{
		return true;
	}
}
