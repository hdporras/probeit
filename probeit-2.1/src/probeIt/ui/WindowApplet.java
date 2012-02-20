package probeIt.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import probeIt.ProbeIt;
import probeIt.action.ProbeItActions;
import probeIt.ui.initial.InitialView;
import probeIt.ui.model.ViewsModel;

public class WindowApplet implements Window
{
	// The current URI
	private JTextField uri;
	
	WindowConfiguration config;
	
	// The URI Panel
	private JPanel uriPanel;

	// The intial view
	private JPanel initialView;
	
	private Container contentPane;
	private JApplet applet;
	//the configuration object which manages the views
	private ViewsManager viewsController;

	//the current view (jtabbedpane)
	private JComponent currentViews;
	
	//static final JPanel DEFAULT_VIEW = new InitialView();
	static final JPanel BLANK_VIEW = new JPanel();
	
	boolean initialViewSet;

	MainMenuBar menu;
	
	static WindowApplet instance;
	
	private WindowApplet(ViewsManager views, Container appletContentPane, JApplet appl)
	{
		applet = appl;
		applet.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		
		contentPane = appletContentPane;
		
		config = WindowConfiguration.getInstance();

		// Create the layout manager and set up constraints and the main panels
		contentPane.setLayout(new BorderLayout());
	
		//build uri panel and add to pane
		buildURIPanel();
		addURIPanel();

		// Create the menubar and set it
		menu = new MainMenuBar();
		applet.setJMenuBar(menu);
		//applet.setSize(900, 700);
		setViewController(views);
		//setInitialView();
	}
	
	public void setViewController(ViewsManager views)
	{viewsController = views;}
	
	private void addURIPanel()
	{contentPane.add(uriPanel, BorderLayout.NORTH);}

	public ViewsManager getViewConfiguration()
	{return viewsController;}
	
	/*private void setInitialView()
	{
		if(currentViews != null)
			contentPane.remove(currentViews);
		initialViewSet = true;
		
		initialView = new InitialView();
		currentViews = initialView;

		contentPane.add(currentViews, BorderLayout.CENTER);
	}*/
	
	public void dispose()
	{applet.destroy();}
	
	private void setProbeitViews()
	{
		initialViewSet = false;
		
		contentPane.removeAll();
		applet.setJMenuBar(null);
		
		if(viewsController == null)
			viewsController = ViewsManager.getInstance();
		
		System.out.println("getting pane");
		currentViews = viewsController.getViewPane();
		
		if(!config.isColapsed())
		{
			applet.setJMenuBar(menu);
			addURIPanel();
		}
		
		contentPane.add(currentViews, BorderLayout.CENTER);
		System.out.println("added pane");
		applet.validate();
		applet.show();
	}
	
	public void updateView()
	{
		setProbeitViews();
	}
	
	public WindowConfiguration getConfiguration()
	{
		return config;
	}
	
	/*
	public static WindowApplication getInstance()
	{
		if(instance == null)
			return getNewInstance();
		return instance;
	}*/
	
	public static WindowApplet getNewInstanceForApplet(Container pane, JApplet applet)
	{
		instance = new WindowApplet(ViewsManager.getInstance(), pane, applet);
		return instance;
	}
	
	private void buildURIPanel()
	{
		//initialize uri panel
		uriPanel = new JPanel();
		uriPanel.setBorder(BorderFactory.createLineBorder(java.awt.Color.gray));
		uriPanel.setSize(899, 50);

		//add uri label to indicate purpose of panel
		JLabel URILabel = new JLabel("URI");
		
		//initialize uri input field
		uri = new JTextField(50);
		
		//create uri button behavavior using probeIt actions
		JButton requestButton = new JButton(ProbeItActions.LookUp);
		JButton requestAnswerButton = new JButton(ProbeItActions.LookUpAnswer);
		
		//add label, uri input field, and button to uriPanel
		uriPanel.add(URILabel);
		uriPanel.add(uri);
		uriPanel.add(requestButton);
		uriPanel.add(requestAnswerButton);
	}
	
	public void setURIText(String newURI)
	{
		if (newURI == null)
		{newURI = "";}
		
		uri.setText(newURI);
		
	}

	public void setURI(String newURI)
	{
		if(initialViewSet)
		{setProbeitViews();}
		
		if (newURI == null)
		{newURI = "";}
		
		uri.setText(newURI);
		if (!newURI.equals(""))
		{
			ViewsModel.getInstance().setJustification(newURI);
        	((ProbeItView)ProbeIt.getInstance().getEmbeddedProbeIt().getViewPane()).setActiveIndex(ProbeItView.GLOBAL_JUSTIFICATION_TAB);
		} else
		{
			JOptionPane.showMessageDialog(null,
					"Could not determine the type of the PML document.",
					"Invalid URI", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void setVisible(boolean b)
	{}
	
	public String getURI()
	{return uri.getText();}
	
	public void resetUI()
	{
		uri.setText("");
		ProbeItActions.updateAllEnabled();
	}
	
	// ///////////////////////////////////////////////////////////////
	// The window listener
	//
	WindowListener windowListener = new WindowAdapter()
	{
		public void windowClosing(WindowEvent e)
		{
			ProbeItActions.Exit.actionPerformed(null);
		}
	};

}
