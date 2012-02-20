package probeIt.logging;

public class Logger
{
	boolean sourceButtonClicked, 
	trustButtonClicked,
	viewerButtonClicked,
	globalViewUsed,
	localViewUsed;
	
	public Logger()
	{
		sourceButtonClicked = false;
		trustButtonClicked = false;
		viewerButtonClicked = false;
		globalViewUsed = false;
		localViewUsed = false;
	}
	
	public void setSourceBool(boolean bool)
	{sourceButtonClicked = bool;}
	
	public void setGlobalViewBool(boolean bool)
	{globalViewUsed=bool;}
	
	public void setLocalViewBool(boolean bool)
	{localViewUsed=bool;}
	
	public void setTrustBool(boolean bool)
	{trustButtonClicked = bool;}

	public void setViewerBool(boolean bool)
	{viewerButtonClicked = bool;}
	
	public boolean getSourceBool()
	{return sourceButtonClicked;}
	
	public boolean getTrustBool()
	{return trustButtonClicked;}
	
	public boolean getViewerBool()
	{return viewerButtonClicked;}
	
	public boolean getGlobalViewBool()
	{return globalViewUsed;}
	
	public boolean getLocalViewBool()
	{return localViewUsed;}
}
