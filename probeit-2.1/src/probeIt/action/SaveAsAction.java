package probeIt.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import probeIt.ProbeIt;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;
import probeIt.ui.PublishDialog;

/**
 * Current PML file is saved in a different location. URIs inside the new copy
 * of the PML document are replaced to corresponding new location. This option
 * is mainly used to create local copies of a given PML document.
 * 
 * @author paulo
 * 
 */
public class SaveAsAction extends ProbeItGenericAction
{
	public SaveAsAction(String name)
	{
		super(name, NO_ICON);
	}

	public SaveAsAction()
	{
		super("Save As", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		PublishDialog _pd = new PublishDialog(ProbeIt.getInstance().getFrame());
	}

	public boolean shouldBeEnabled()
	{
		return ViewsModel.getInstance().hasJustification();
	}
}
