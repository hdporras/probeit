package probeIt.ui.popup;
import java.util.ArrayList;
import java.awt.Window;
import javax.swing.JFrame;

import probeIt.ui.popup.result.PopupContentView;
public class Manager
{
	static ArrayList<Window> popups = new ArrayList<Window>();
	public static PopupContentView latestPopup;
	
	public static void newFrame(java.awt.Window frame)
	{popups.add(frame);}
	
	public static void closeAllPopups()
	{
		for(int i = 0; i < popups.size(); i ++)
		{
			Window popup = popups.get(i);
			popup.dispose();
		}
		
		if(latestPopup != null)
			latestPopup.dispose();
		
		newContext();
	}
	
	private static void newContext()
	{
		popups = new ArrayList<Window>();
		latestPopup = null;
	}
}
