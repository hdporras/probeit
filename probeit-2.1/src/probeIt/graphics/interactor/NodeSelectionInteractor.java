package probeIt.graphics.interactor;

import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.model.ViewsModel;
import diva.canvas.event.LayerEvent;
import diva.canvas.interactor.SelectionInteractor;

public class NodeSelectionInteractor extends SelectionInteractor
{
	public static NodeSetGraphic _lastPointedFigure;
	
	public void mousePressed(LayerEvent event)
	{
		super.mousePressed(event);
		_lastPointedFigure = (NodeSetGraphic) event.getFigureSource();
		
		probeIt.ProbeIt.getInstance().getLogger().setLocalViewBool(true);
		//ViewsModel.getInstance().setAnswer(_lastPointedFigure.getURI()); //change back to local view instead of answer view.
		((ProbeItView)ViewsManager.getInstance().getViewPane()).buildProvenanceView(_lastPointedFigure.getURI());
		//*
		probeIt.ui.global.JustificationView.getInstance().setPointOfInterest(_lastPointedFigure.getX(), _lastPointedFigure.getY());
	}
}
