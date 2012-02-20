package probeIt.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * A template for creating actions managed by ProbeItActions.
 * 
 * @author paulo
 * 
 */
public class ProbeItGenericAction extends AbstractAction
{

	public static boolean HAS_ICON = true;
	public static boolean NO_ICON = false;
	
	public ProbeItGenericAction(String name)
	{
		this(name, true, HAS_ICON);
	}

	public ProbeItGenericAction(String name, boolean hasIcon)
	{
		this(name, true, hasIcon);
	}

	public ProbeItGenericAction(String name, boolean global, boolean hasIcon)
	{
		super(name);
		/*
		 * if (hasIcon) { Icon icon = Util.loadIconResource(name); if (icon !=
		 * null) putValue(Action.SMALL_ICON, icon); else {
		 * System.out.println("icon not found: " + name); } }
		 */
		putValue(Action.SHORT_DESCRIPTION, name + " ");
		if (global)
			ProbeItActions._allActions.addElement(this);
	}

	/**
	 * Performs the work the action is supposed to do.
	 */
	public void actionPerformed(ActionEvent e)
	{
		// System.out.println("pushed " + getValue(Action.NAME));
		ProbeItActions.updateAllEnabled();
	}

	public void updateEnabled(Object target)
	{
		setEnabled(shouldBeEnabled());
	}

	public void updateEnabled()
	{
		boolean b = shouldBeEnabled();
		setEnabled(b);
	}

	/**
	 * Returns true if this action should be available to the user. Subclass
	 * implementations of this method should always call shouldBeEnabled's
	 * super() first.
	 */
	public boolean shouldBeEnabled()
	{
		return true;
	}
} /* end class GenericAction */
