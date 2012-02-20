package probeIt.action;

import java.awt.event.ActionEvent;

import probeIt.ProbeIt;
import probeIt.ui.SelectURIDialog;
import probeIt.ui.Window;
import probeIt.util.PMLNodesetURIsFromURL;

/**
 * Set the user interface URI after verifying that the URI is valid.
 * 
 * @author paulo
 */
public class LookUpAction extends ProbeItGenericAction
{

	private String _URI;

	public LookUpAction()
	{
		super("Look Up", NO_ICON);
	}

	public void actionPerformed(ActionEvent e)
	{
		Window _ui = ProbeIt.getInstance().getWindow();
		_URI = _ui.getURI().trim();
		
		//Check if the input is a URL or URI
		if(_URI.contains("#"))
			_ui.setURI(_URI);
		else
		{
			//_URI = _URI.replace('/', '\\');
			//_URI = _URI.replaceFirst("http://", "");
			System.out.println(_URI);
			java.util.List<String> uris = PMLNodesetURIsFromURL.getNodesetURIsFromURL(_URI);//"//saturn/students/hdporras/Desktop/uri2.xml");//_URI);

			if(uris.size() > 1)
				new SelectURIDialog(ProbeIt.getInstance().getFrame(), uris);
			else if(uris.size() == 1)
				_ui.setURI(uris.get(0));
			else
			{
				System.out.println("No URI Found in URL!");
				_ui.setURI(uris.get(0));//? must change to popup warning.
				//return;
			}
		}
		
		//colapse menu after uri is choosen
		ProbeIt.getInstance().getWindow().getConfiguration().setColapsedValue(true);
		ProbeIt.getInstance().getWindow().updateView();
	}

	public boolean shouldBeEnabled()
	{
		return true;
	}
}