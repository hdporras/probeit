package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import javax.swing.JButton;
public class ToggleShowURIPanelAction extends ProbeItGenericAction
{
	
	public ToggleShowURIPanelAction(String name)
	{
		super(name, NO_ICON);
	}
	
	public ToggleShowURIPanelAction()
	{super("Show Menus", NO_ICON);}

	public void actionPerformed(ActionEvent e)
	{
		JButton button = (JButton)e.getSource();
		boolean state = ProbeIt.getInstance().getWindow().getConfiguration().isColapsed();
		
		if(state)
		{
			button.setText("Hide Menus");
		}
		else
		{
			button.setText("Show Menus");
		}
		ProbeIt.getInstance().getWindow().getConfiguration().setColapsedValue(!state);
		ProbeIt.getInstance().getWindow().updateView();
	}

	public boolean shouldBeEnabled()
	{return true;}
}
