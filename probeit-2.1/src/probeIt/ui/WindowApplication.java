package probeIt.ui;

import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JApplet;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import probeIt.ProbeIt;
import probeIt.action.ProbeItActions;
import pml.*;
import probeIt.ui.initial.InitialView;
import probeIt.ui.model.ViewsModel;


import java.awt.Container;

public class WindowApplication extends JFrame implements Window
{	
	// The current URI
	private JTextField uri;
	
	WindowConfiguration config;
	
	MainMenuBar menu;
	
	// The URI Panel
	private JPanel uriPanel;

	// The initial view
	//private JPanel initialView;
	
	private Container contentPane;
	
	//the configuration object which manages the views
	private ViewsManager viewsController;

	//the current view (jtabbedpane)
	private JComponent currentViews;
		
	//the status bar at the bottom 
	static final ProbeItProgress progress = new ProbeItProgress();
	
	//static final JPanel DEFAULT_VIEW = new InitialView();
	static final JPanel BLANK_VIEW = new JPanel();
	
	boolean initialViewSet;
	
	static WindowApplication instance;
	
	private WindowApplication(ViewsManager views)
	{
		super();
		menu = new MainMenuBar();
		config = WindowConfiguration.getInstance();
		contentPane = this.getContentPane();
		setTitle("Probe-It! Scientific Provenance Visualization Framework");
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		this.addComponentListener(new java.awt.event.ComponentAdapter()
		{
			public void componentResized(ComponentEvent event)
			{
				JFrame frame = (JFrame) event.getSource();
				frame.setSize(
						(frame.getWidth() < 900) ? 900 : frame.getWidth(),
						(frame.getHeight() < 700) ? 700 : frame.getHeight());
				repaint();
			}
		});

		// Create the layout manager and set up constraintsand the main panels
		contentPane.setLayout(new BorderLayout());
	
		//build uri panel and add to pane
		buildURIPanel();
		addURIPanel();

		// Create the menubar and set it
		setJMenuBar(menu);
		
		// Close the window on any window event
		addWindowListener(windowListener);
		setSize(900, 700);
		
		//setViewController(views);
		//setInitialView();
		updateView();
		
		//pack();
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
		
		//initialView = new InitialView();
		//currentViews = InitialView;

		contentPane.add(currentViews, BorderLayout.CENTER);
	}*/
	
	public void updateView()
	{
		System.out.println("[WindowApplication.updateView()] progress-" + progress.isEnabled());

		//initialViewSet = false;
		
		String progressLabel = progress.getName();
		boolean progressStatus = progress.isEnabled();
		
		contentPane.removeAll();
		this.setJMenuBar(null);
		
		((ProbeItView)ViewsManager.getInstance().getViewPane()).updateProbeItView();
		//currentViews = ViewsManager.getInstance().getViewPane();

		if(!config.isColapsed())
		{
			this.setJMenuBar(menu);
			addURIPanel();
		}

		contentPane.add(ViewsManager.getInstance().getViewPane(), BorderLayout.CENTER);

		contentPane.add(progress, BorderLayout.SOUTH);
		
		/*if (progressStatus) {
			progress.setName(progressLabel);
			if (progressStatus)
				progress.setEnabled(true);
		}*/

		System.out.println("[WindowApplication.updateView()] progress-" + progress.isEnabled());
		
		pack();
	}
	
	/*public void updateView()
	{
		setProbeitViews();
	}*/
	
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
	
	/*public static WindowApplication getNewInstance()
	{
		instance = new WindowApplication(ViewsManager.getInstance());
		return instance;
	}*/
	
	public static WindowApplication getInstance()
	{
		if (instance == null)
			instance = new WindowApplication(ViewsManager.getInstance());//getNewInstance());

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
		{updateView();}
		
		if (newURI == null)
		{newURI = "";}
		
		uri.setText(newURI);
		if (!newURI.equals(""))
		{
			ViewsModel.getInstance().setJustification(newURI);
        	((ProbeItView)ProbeIt.getInstance().getEmbeddedProbeIt().getViewPane()).setActiveIndex(ProbeItView.ANSWER_TAB);
		} else
		{
			JOptionPane.showMessageDialog(null,
					"Could not determine the type of the PML document.",
					"Invalid URI", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void enableProgressBar(String text)
	{
		progress.enableProgressBar(text);
	} 

	public void disableProgressBar()
	{
		progress.disableProgressBar();
	}


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
