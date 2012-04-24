package probeIt.ui;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Component;
import probeIt.action.featureSelection.*;
import probeIt.action.ProbeItActions;
import javax.swing.ButtonGroup;
public class MainMenuBar extends JMenuBar
{

	JMenu menuFile;
	JMenu menuBookmark;
	JMenu subMenuGravity;
	JMenu subMenuEQL;
	JMenu subMenuTPTP;
	JMenu subMenuTDS;
	JMenu subMenuHolesCode;
	JMenu menuPreferences;
	JMenu menuViewers;
	JMenu menuConfiguration;
	JMenu menuHelp;
	
	JMenuItem itemFileOpen;
	JMenuItem itemClose;
	JMenuItem itemExit;
	JMenuItem itemSaveAs;
	JMenuItem itemBookmarkSelect;
	JMenuItem itemHelpIndex;
	JMenuItem itemAbout;
	JMenuItem itemCloseAll;
	JMenuItem itemOpenAll;
	
	JCheckBoxMenuItem item_config_R;
	JCheckBoxMenuItem item_config_S;
	JCheckBoxMenuItem item_config_J;
	JCheckBoxMenuItem item_config_MV;
	JCheckBoxMenuItem item_config_P;
	JCheckBoxMenuItem item_config_T;
	JCheckBoxMenuItem item_config_L;
	JCheckBoxMenuItem item_config_A;   // avatar without multimedia
	JCheckBoxMenuItem item_config_AM;  // avatar with multimedia


	JPanel userPanel;
	JButton login;
	JTextField userName;
	JLabel userLabel;

	public MainMenuBar()
	{

		// Create menus
		menuFile = new JMenu("File");
		menuBookmark = new JMenu("Bookmark");
		//subMenuGravity = new JMenu("Gravity Map Bookmarks");
		//subMenuEQL = new JMenu("Enhanced QuickLook Bookmaks");
		subMenuTPTP = new JMenu("TPTP Proofs");
		subMenuHolesCode = new JMenu("Hole's Code Executions");
		//subMenuTDS = new JMenu("DHS - Threat Detection System");
		menuPreferences = new JMenu("Preferences");
		menuConfiguration = new JMenu("Configuration");
		menuViewers = new JMenu("Viewers");
		menuHelp = new JMenu("Help");

		// Create the menu items
		//itemFileOpen = menuFile.add(ProbeItActions.FileOpen);
		itemClose = menuFile.add(ProbeItActions.Close);
		itemSaveAs = menuFile.add(ProbeItActions.SaveAs);
		itemExit = menuFile.add(ProbeItActions.Exit);
		
		itemCloseAll = menuViewers.add(ProbeItActions.CloseAll);
		//itemOpenAll = menuViewers.add(ProbeItActions.OpenAll);
		
		item_config_R = new JCheckBoxMenuItem();
		item_config_R.setAction(ProbeItActions.Config_R);
		
		item_config_S = new JCheckBoxMenuItem();
		item_config_S.setAction(ProbeItActions.Config_S);
		
		item_config_J = new JCheckBoxMenuItem();
		item_config_J.setAction(ProbeItActions.Config_J);
		
		item_config_MV = new JCheckBoxMenuItem();
		item_config_MV.setAction(ProbeItActions.Config_MV);
		
		item_config_P = new JCheckBoxMenuItem();
		item_config_P.setAction(ProbeItActions.Config_P);
		
		item_config_T = new JCheckBoxMenuItem();
		item_config_T.setAction(ProbeItActions.Config_T);
		
		item_config_L = new JCheckBoxMenuItem();
		item_config_L.setAction(ProbeItActions.Config_L);
		
		item_config_A = new JCheckBoxMenuItem();
		item_config_A.setAction(ProbeItActions.Config_A);
		
		item_config_AM = new JCheckBoxMenuItem();
		item_config_AM.setAction(ProbeItActions.Config_AM);
		
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_R);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_S);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_T);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_J);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_MV);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_P);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_L);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_A);
		FeatureCheckBoxGroup.addFeatureCheckBox(item_config_AM);
		
		menuConfiguration.add(item_config_R);
		menuConfiguration.add(item_config_S);
		menuConfiguration.add(item_config_J);
		menuConfiguration.add(item_config_MV);
		menuConfiguration.add(item_config_P);
		menuConfiguration.add(item_config_T);
		menuConfiguration.add(item_config_L);
		menuConfiguration.add(item_config_A);
		menuConfiguration.add(item_config_AM);
		
		itemHelpIndex = menuHelp.add(ProbeItActions.HelpIndex);
		itemAbout = menuHelp.add(ProbeItActions.About);
		
		menuPreferences.add(ProbeItActions.SetPreferences);
		
		menuBookmark.add(ProbeItActions.BookmarkTony);
		menuBookmark.add(ProbeItActions.BookmarkSciPub);
		menuBookmark.add(ProbeItActions.BookmarkGravity);
		
		//subMenuEQL.add(ProbeItActions.BookmarkEQL1);
		//subMenuEQL.add(ProbeItActions.BookmarkEQL2);
		//subMenuEQL.add(ProbeItActions.BookmarkEQL3);
		
		subMenuTPTP.add(ProbeItActions.BookmarkTPTP);
		subMenuTPTP.add(ProbeItActions.BookmarkTPTPAgatha);
		
		//subMenuTDS.add(ProbeItActions.BookmarkTDS);
		//subMenuTDS.add(ProbeItActions.BookmarkTDS1);
		
		subMenuHolesCode.add(ProbeItActions.BookmarkHoles);
		subMenuHolesCode.add(ProbeItActions.BookmarkHolesFull);
		
		menuBookmark.add(subMenuTPTP);
		//menuBookmark.add(subMenuTDS);
		menuBookmark.add(subMenuHolesCode);
		
		// Login pane
		login = new JButton("Sign in");
		userName = new JTextField(10);
		userName.setEditable(false);
		userLabel = new JLabel("User:");
		userPanel = new JPanel();
		userPanel.setMaximumSize(new Dimension(240, 33));
		userPanel.add(userLabel);
		userPanel.add(userName);
		userPanel.add(login);

		// Build the menus
		this.add(menuFile);
		this.add(menuBookmark);
		this.add(menuPreferences);
		this.add(menuConfiguration);
		this.add(menuViewers);
		this.add(menuHelp);
		this.add(Box.createHorizontalGlue());
		this.add(userPanel, Component.RIGHT_ALIGNMENT);
	}

}
