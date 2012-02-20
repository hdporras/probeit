package probeIt.action;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import pml.util.pml.PMLSourceRetrieval;
import probeIt.graphics.interactor.NodeSelectionInteractor;
import probeIt.ui.popup.provenance.LinkListener;
import probeIt.ui.popup.source.PopupPMLSourceView;
import probeIt.util.GetURLContents;

public class PMLSourceAction extends ProbeItGenericAction
{
	static boolean inSourceView = false; 

	public PMLSourceAction(String name)
	{
		super(name, NO_ICON);
	}

	public PMLSourceAction()
	{
		super("PML Source", NO_ICON);
	}

	//*doesn't follow the hyperlinked PML, only uses originally clicked node.
	public void actionPerformed(ActionEvent e)
	{

		String uri = NodeSelectionInteractor._lastPointedFigure.getNode().getURI();
		System.out.println("PML URI: "+uri);

		if(!inSourceView)
		{
			String source;
			try
			{
				source =GetURLContents.downloadText(uri);
				System.out.println("PML Source: "+source);

				new PopupPMLSourceView(source, NodeSelectionInteractor._lastPointedFigure.getNode().getID());
				//additional actions when Exception is caught?
			}catch(MalformedURLException ex)
			{
				ex.printStackTrace();
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
			
			//inSourceView = true;
			
		}
	}
}
