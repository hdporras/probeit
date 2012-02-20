package probeIt.viewerFramework.viewers;

public class Viewers
{
	//public static Viewer XMDV = new Viewer_XMDV();
	
	public static Viewer getParvis()
	{
		Viewer viewer = new Viewer_Parvis();
		return viewer;
		
	}
	
	public static Viewer getHTML()
	{
		Viewer viewer = new Viewer_HTML();
		return viewer;
	}
	
	public static Viewer getHexadecimal()
	{
		Viewer viewer = new Viewer_Hexadecimal();
		return viewer;
	}
	
	public static Viewer getImageJ()
	{
		Viewer viewer = new Viewer_ImageJ();
		return viewer;
	}
	
	public static Viewer getPDF()
	{
		Viewer viewer = new Viewer_PDF();
		return viewer;
	}
	
	public static Viewer getEQLookup()
	{
		Viewer viewer = new Viewer_EQLXML();
		return viewer;
	}
	
	public static Viewer getText()
	{
		Viewer viewer = new Viewer_Text();
		return viewer;
	}
	
	
}
