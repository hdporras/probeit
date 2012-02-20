package probeIt.ui;

public class WindowConfiguration
{
	private static WindowConfiguration instance;
	
	private boolean isColapsed;
	private boolean dagStyle;
	private boolean hasAvatar;	
	
	private WindowConfiguration()
	{
		isColapsed = false;
		dagStyle = false;
		hasAvatar = false;
	}

	public static WindowConfiguration getInstance() {
		if (instance == null)
			instance = new WindowConfiguration();
		return instance;
	}
	
	public void setColapsedValue(boolean value)
	{
		isColapsed = value;
	}
	
	public boolean isColapsed()
	{	
		return isColapsed;
	}

	public void setHasAvatar(boolean value)
	{
		hasAvatar = value;
	}
	
	public boolean hasAvatar()
	{	
		return hasAvatar;
	}
}
