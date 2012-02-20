package probeIt.action;

import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import probeIt.ui.ViewerPreferences.PreferencesTable;
public class SetViewerPreferencesAction extends ProbeItGenericAction
{
	public SetViewerPreferencesAction()
	{
		super("Viewer Preferences", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		JDialog prefTableFrame = new JDialog(probeIt.ProbeIt.getInstance().getFrame(), "Transformer/Viewer Preferences", false);
		prefTableFrame.getContentPane().add(new PreferencesTable());
		prefTableFrame.setSize(700,300);
		//prefTableFrame.pack();
		
		prefTableFrame.setVisible(true);
	}

	public boolean shouldBeEnabled()
	{
		return true;
	}
}
