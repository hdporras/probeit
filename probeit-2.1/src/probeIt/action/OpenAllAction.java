package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ui.ViewsManager;
import probeIt.ui.PublishDialog;
import probeIt.ui.model.ViewsModel;

public class OpenAllAction extends ProbeItGenericAction
{
	public OpenAllAction(String name)
	{
		super(name, NO_ICON);
	}

	public OpenAllAction()
	{
		super("OpenAll", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		//PublishDialog _pd = new PublishDialog();
	}

	public boolean shouldBeEnabled()
	{
		return ViewsModel.getInstance().hasContext();
	}

}
