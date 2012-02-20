package probeIt.graphics.interactor;

import probeIt.graphics.figure.AnswerGraphic;
import probeIt.ui.model.PMLLoader;
import probeIt.ui.ViewsManager;
import diva.canvas.event.LayerEvent;
import diva.canvas.interactor.SelectionInteractor;

public class AnswerSelectionInteractor extends SelectionInteractor
{
	AnswerGraphic _lastPointedFigure;

	public void mousePressed(LayerEvent event)
	{
		super.mousePressed(event);
		_lastPointedFigure = (AnswerGraphic) event.getFigureSource();

		probeIt.ProbeIt.getInstance().getLogger().setGlobalViewBool(true);
		
		String clickedURI = _lastPointedFigure.getURI();
		String clickedID = _lastPointedFigure.getID();
		PMLLoader _loader = new PMLLoader(clickedURI, clickedID);
		_loader.start();
		
		/*
		if(!ProbeItViews.getInstance().isJustificationLoaded())
		{
			PMLLoader _loader = new PMLLoader(_lastPointedFigure.getURI(), _lastPointedFigure.getID());
			_loader.start();
		}
		else
			ProbeItViews.getInstance().setJustification(_lastPointedFigure.getNode());
	*/}
}
