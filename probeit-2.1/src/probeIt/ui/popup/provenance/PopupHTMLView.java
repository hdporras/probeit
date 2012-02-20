package probeIt.ui.popup.provenance;
import javax.swing.*;
import probeIt.ui.local.HTMLView;
import probeIt.ui.popup.Manager;
public class PopupHTMLView extends JFrame
{
	String uri; //uri of provenance information
	boolean isPML; //indicates whether provenance is PML based or standard HTML
	public PopupHTMLView(String URI, boolean PML)
	{
		Manager.newFrame(this);
		uri = URI;
		isPML = PML;
		init();
	}
	
	private void init()
	{
		HTMLView html = new HTMLView(uri, true, isPML, false, false);
		getContentPane().add(html);
		this.setVisible(true);
		this.setSize(500, 500);
	}
}
