package probeIt.graphics.interactor;

import probeIt.graphics.buttons.SourceButton;
import probeIt.ui.popup.source.PopupSourceView;
import diva.canvas.event.LayerEvent;
import diva.canvas.interactor.SelectionInteractor;
import pml.PMLNode;

//This interactor handles the events when users wish to popup the sources
public class ShowSourcesInPopupInteractor extends SelectionInteractor
{
	PMLNode nodeSet;
	
	public ShowSourcesInPopupInteractor(PMLNode ns)
	{nodeSet = ns;}
	
	public void mousePressed(LayerEvent event)
	{
		super.mousePressed(event);
		
		SourceButton button = (SourceButton) event.getFigureSource();
		this.getSelectionModel().removeSelection(button);
		new PopupSourceView(nodeSet);
	}
}

