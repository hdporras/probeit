package probeIt.action;

import java.util.Vector;

import probeIt.action.bookmark.*;
import probeIt.action.bookmark.BookmarkTonySpecialtyAction;
import probeIt.action.featureSelection.*;

/**
 * Responsible for the activation/deactivation of actions. The class has a
 * static instantiation of most actions, which are stored in a vector for easy
 * management of their behaviour.
 * 
 * @author paulo
 * 
 */
public class ProbeItActions {

	private static final int MAX_ACTIONS = 100;

	static Vector _allActions = new Vector(MAX_ACTIONS);
	
	// file menu
	//public static ProbeItGenericAction FileOpen = new FileOpenAction();
	public static ProbeItGenericAction Close = new CloseAction();
	public static ProbeItGenericAction SaveAs = new SaveAsAction();
	public static ProbeItGenericAction Exit = new ExitAction();
	
	// bookmark menu
	public static ProbeItGenericAction BookmarkTony = new BookmarkTonySpecialtyAction();
//	public static ProbeItGenericAction BookmarkGravity = new BookmarkGravityAction_bs1();
//	public static ProbeItGenericAction BookmarkGravity1 = new BookmarkGravityAction_wa1();
//	public static ProbeItGenericAction BookmarkGravity2 = new BookmarkGravityAction_bsgroup();
//	public static ProbeItGenericAction BookmarkGravity3 = new BookmarkGravityAction_dsgroup();
//	public static ProbeItGenericAction BookmarkGravity4 = new BookmarkGravityAction_real();
	public static ProbeItGenericAction BookmarkGravity4 = new BookmarkGravityAction_new();
	public static ProbeItGenericAction BookmarkTPTP = new BookmarkTPTP2PML();
	public static ProbeItGenericAction BookmarkTPTPAgatha = new BookmarkTPTP2PMLAgatha();
	public static ProbeItGenericAction BookmarkEQL1 = new BookmarkEQL1Action();
	public static ProbeItGenericAction BookmarkEQL2 = new BookmarkEQL2Action();
	public static ProbeItGenericAction BookmarkEQL3 = new BookmarkEQL3Action();
	public static ProbeItGenericAction BookmarkTDS = new BookmarkDHSTDS();
	public static ProbeItGenericAction BookmarkTDS1 = new BookmarkDHSTDS1();
	public static ProbeItGenericAction BookmarkSciPub = new BookmarkScientificPub();
	public static ProbeItGenericAction BookmarkHoles = new BookmarkHolesCode_new();
	public static ProbeItGenericAction BookmarkHolesFull = new BookmarkHolesCode_full();
	
	// help menu
	public static ProbeItGenericAction HelpIndex = new HelpIndexAction();
	public static ProbeItGenericAction About = new AboutAction();

	//configuration
	public static ProbeItGenericAction Config_R = new Feature_R_Action();
	public static ProbeItGenericAction Config_J = new Feature_J_Action();
	public static ProbeItGenericAction Config_MV = new Feature_MV_Action();
	public static ProbeItGenericAction Config_S = new Feature_S_Action();
	public static ProbeItGenericAction Config_P = new Feature_P_Action();
	public static ProbeItGenericAction Config_T = new Feature_T_Action();
	public static ProbeItGenericAction Config_L = new Feature_L_Action();
	public static ProbeItGenericAction Config_A = new Feature_A_Action();
	public static ProbeItGenericAction Config_AM = new Feature_AM_Action();
	public static ProbeItGenericAction Config_W = new Feature_W_Action();
	
	// opening URI actions
	public static ProbeItGenericAction LookUp = new LookUpAction();
	public static ProbeItGenericAction LookUpAnswer = new LookUpAnswerAction();
	
	//main window layout actions
	public static ProbeItGenericAction ToggleExpand = new ToggleShowURIPanelAction();
	
	public static ProbeItGenericAction JustStyleAction = new JustificationStyleAction();
	
	//Toolbar actions
	public static ProbeItGenericAction ShowSources = new ShowSourcesAction();
	public static ProbeItGenericAction ZoomIn = new ZoomInAction();
	public static ProbeItGenericAction ZoomOut = new ZoomOutAction();
	public static ProbeItGenericAction PMLSource = new PMLSourceAction();

	// viewer related actions
	public static ProbeItGenericAction CloseAll = new CloseAllAction();
	public static ProbeItGenericAction OpenAll = new OpenAllAction();
	
	//preferences
	public static ProbeItGenericAction SetPreferences = new SetViewerPreferencesAction();
	
	public static void updateAllEnabled()
	{
		FeatureCheckBoxGroup.updateButtons();
		
		java.util.Enumeration actions = _allActions.elements();
		while (actions.hasMoreElements())
		{
			ProbeItGenericAction a = (ProbeItGenericAction) actions.nextElement();
				a.updateEnabled();
		}
	}
}

/* end class ProbeItActions */
