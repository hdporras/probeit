package probeIt.ui;
public interface Window
{
	void setViewController(ViewsManager views);
	ViewsManager getViewConfiguration();
	void setURI(String newURI);
	void setURIText(String newURI);
	String getURI();	
	void resetUI();
	void setVisible(boolean b);
	void dispose();
	void updateView();
	WindowConfiguration getConfiguration();
}
