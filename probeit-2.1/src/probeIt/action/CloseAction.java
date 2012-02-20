package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.ui.ViewsManager;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;

public class CloseAction extends ProbeItGenericAction
{
	public CloseAction(String name)
	{
		super(name, NO_ICON);
	}

	public CloseAction()
	{
		super("Close", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		ProbeIt.getInstance().getWindow().resetUI();
	}

	public boolean shouldBeEnabled()
	{
		// System.out.println("In close: [hasContext] " +
		// AppWindow.getInstance().hasContext());
		return ViewsModel.getInstance().hasContext();
	}
}
