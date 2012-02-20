package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ui.WindowApplication;
import probeIt.ui.HTMLFrame;

public class HelpIndexAction extends ProbeItGenericAction
{
	public HelpIndexAction(String name)
	{
		super(name, NO_ICON);
	}

	public HelpIndexAction()
	{
		super("Index", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		HTMLFrame htmlFrame = new HTMLFrame("file:///"
				+ System.getProperty("user.dir") + "/doc/help/index.html",
				"Probe-It! Help");
		htmlFrame.setVisible(true);
	}

	public boolean shouldBeEnabled()
	{
		return true;
	}
}
