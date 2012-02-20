package probeIt;

import java.awt.Frame;
import probeIt.action.*;
import probeIt.ui.Window;
import probeIt.ui.WindowApplication;
import probeIt.ui.WindowApplet;
import probeIt.ui.ViewsManager;
import probeIt.util.FileUtils;
import probeIt.logging.Logger;
import probeIt.ui.popup.Manager;
import java.awt.Container;
import javax.swing.JApplet;
import javax.swing.JFrame;
import gmt.GMTToolkit.ConnectionType;

public class ProbeIt {
	
	static ProbeIt instance;
	String[] parameters;
	Window ui;
	ViewsManager views;
	Frame parentFrame;
	Logger logger = new Logger();
	boolean isRemote = false;
	boolean isLoaded = false;
	boolean avatarView = false;
	
	ConnectionType gmtConnectionType = ConnectionType.RMIService;
	
	public void setGMTConnectionType(ConnectionType aType)
	{gmtConnectionType = aType;}
	
	public ConnectionType getGMTConnectionType()
	{return gmtConnectionType;}
	
	public Logger getLogger()
	{return logger;}
	
	public boolean isRemote()
	{return isRemote;}
	
	public static ProbeIt getInstance()
	{
		if(instance == null)
			instance = new ProbeIt();
		return instance;
	}
	
	protected ProbeIt()
	{
		instance = this;
		isRemote = false;
		FileUtils.cleanWorkspace();
		WindowApplication app = WindowApplication.getInstance();
		ui = app;
		parentFrame = app;
		views = ViewsManager.getInstance();
		logger = new Logger();	
		isLoaded = true;
		Manager.closeAllPopups();
	}
	
	public Frame getFrame()
	{return parentFrame;}
	
	 private Frame findParentFrame(JApplet applet)
	 { 
		    Container c = applet; 
		    while(c != null){ 
		      if (c instanceof Frame) 
		        return (Frame)c; 
		 
		      c = c.getParent(); 
		    } 
		    return (Frame)null; 
	 }
	
	public ProbeIt(Container container, JApplet applet)
	{
		instance = this;
		ui = WindowApplet.getNewInstanceForApplet(container, applet);
		isRemote = false;
		parentFrame = findParentFrame(applet);
		views = ViewsManager.getInstance();
		logger = new Logger();
		isLoaded = true;
		Manager.closeAllPopups();
	}
	
	public ProbeIt(JFrame owner)
	{
		instance = this;
		isRemote = false;
		parentFrame = owner;
		views = ViewsManager.getInstance();
		logger = new Logger();
		isLoaded = true;
		Manager.closeAllPopups();
	}
	
	public ProbeIt(JFrame owner, ViewsManager v)
	{
		instance = this;
		isRemote = false;
		parentFrame = owner;
		views = ViewsManager.getInstance();
		logger = new Logger();
		isLoaded = true;
		Manager.closeAllPopups();
	}
	
	public void setRemote()
	{isRemote = true;}
	
	public ViewsManager getEmbeddedProbeIt()
	{return views;}
	
	protected void setParameters(String[] params)
	{parameters = params;}
	
	public void openFullProbeIt()
	{
		ui.setViewController(views);
		
		// clean workspace dir
		if(!isRemote())
			FileUtils.cleanWorkspace();
		
		String uri = null;
		String features = null;
		String aFlag;
		String globalStyle = "none";
		
		if(parameters != null)
		{
			for(int i = 0; i < parameters.length; i ++)
			{
				aFlag = parameters[i];
				if(aFlag.startsWith("-u"))
					uri = aFlag.substring(2).trim();
				else if(aFlag.startsWith("-f"))
					features = aFlag.substring(2).trim();
				else if(aFlag.startsWith("-r"))
					isRemote = true;
				else if(aFlag.startsWith("-s"))
					globalStyle = aFlag.substring(2).trim();
				else if(aFlag.startsWith("-a"))
					avatarView = true;
			}
		}
		
		if(features == null)
			views.enableAllFeatures();
		else if(features.equalsIgnoreCase("l"))
		{
			System.out.println("local view first");
			views.enableAllFeatures();
			views.enableFeature_L();
		}
		else
		{
			views.disableAllFeatures();
			char[] featureFlags = features.toCharArray();
			char feature;
			for(int i = 0; i < featureFlags.length; i ++)
			{
				feature = featureFlags[i];
				System.out.println("found feature: " + feature);
				if(feature == 'r')
					views.enableFeature_R();
				else if(feature == 's')
					views.enableFeature_S();
				else if(feature == 't')
					views.enableFeature_T();
				else if(feature == 'j')
					views.enableFeature_J();
				else if(feature == 'm')
					views.enableFeature_MV();
				else if(feature == 'p')
					views.enableFeature_P();
				else if(feature == 'l')
					views.enableFeature_L();
					
			}
		}
		
		if(avatarView)
			views.enableFeature_A();
		
		//views.updatePane();
		ProbeItActions.updateAllEnabled();
		ui.setVisible(true);
		
		if(uri != null)
		{
			ui.setURIText(uri);
			ProbeItActions.LookUp.actionPerformed(null);
			//FileOpenAction.openPMLFile(uri);
		}
		
		if(globalStyle != null)
		{
			if(globalStyle.equalsIgnoreCase("tree"))
				views.setCurrentJustificationStyle(ViewsManager.JustificationStyle.tree);
			else if(globalStyle.equalsIgnoreCase("dag"))
				views.setCurrentJustificationStyle(ViewsManager.JustificationStyle.dag);
		}
		
		((JFrame)ui).repaint();
		
	}
	
	public boolean isLoaded()
	{return isLoaded;}
	
	public Window getWindow()
	{return getInstance().ui;}
	
	public static void main(String[] args)
	{
		ProbeIt app = new ProbeIt();
		app.setGMTConnectionType(ConnectionType.RMIService);
		app.setParameters(args);
		app.openFullProbeIt();
	}
}
