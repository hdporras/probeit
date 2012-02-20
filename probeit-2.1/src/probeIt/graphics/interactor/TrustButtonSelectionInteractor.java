package probeIt.graphics.interactor;

import diva.canvas.event.LayerEvent;
import diva.canvas.interactor.SelectionInteractor;
import probeIt.graphics.buttons.TrustButton;
import probeIt.ui.popup.trust.PopupTrustMapView;
import probeIt.ProbeIt;

public class TrustButtonSelectionInteractor extends SelectionInteractor
{
	public void mousePressed(LayerEvent event)
	{
		super.mousePressed(event);
		
		ProbeIt.getInstance().getLogger().setTrustBool(true);
		
		TrustButton button = (TrustButton) event.getFigureSource();
		this.getSelectionModel().removeSelection(button);
		PopupTrustMapView view = new PopupTrustMapView(button.getNode());
		view.buildMap();
	}
}
