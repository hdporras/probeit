package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.ui.WindowApplication;
public class ExitAction extends ProbeItGenericAction
{
	public ExitAction()
	{
		super("Exit", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ProbeIt.getInstance().getWindow().dispose();
	}

	public boolean shouldBeEnabled()
	{
		return true;
	}
}
