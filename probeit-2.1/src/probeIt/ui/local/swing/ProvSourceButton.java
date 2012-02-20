package probeIt.ui.local.swing;

public class ProvSourceButton extends PopupButton 
{
	public ProvSourceButton(String text, String _uri)
	{
		super(text, _uri);
		this.setFont(this.getFont().deriveFont((float)8.0));
	}
}
