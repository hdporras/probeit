package probeIt.action;

import java.awt.event.ActionEvent;
import probeIt.ui.popup.Manager;
public class CloseAllAction extends ProbeItGenericAction
{
	public CloseAllAction(String name)
	{
		super(name, NO_ICON);
	}

	public CloseAllAction()
	{
		super("Close All", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{Manager.closeAllPopups();}

	public boolean shouldBeEnabled()
	{
		return true;
	}
}
