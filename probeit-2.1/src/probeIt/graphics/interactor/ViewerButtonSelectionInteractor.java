package probeIt.graphics.interactor;

import javax.swing.JOptionPane;

import diva.canvas.event.LayerEvent;
import diva.canvas.interactor.SelectionInteractor;
import probeIt.graphics.buttons.ViewerButton;
import probeIt.ui.popup.result.PopupContentView;
import probeIt.ui.ViewsManager;
import probeIt.ProbeIt;
import probeIt.ui.popup.Manager;

public class ViewerButtonSelectionInteractor extends SelectionInteractor
{
	public void mousePressed(LayerEvent event)
	{
		super.mousePressed(event);
		boolean singleViewOnly = !ViewsManager.getInstance().isEnabledFeature_MV();
		ViewerButton button = (ViewerButton) event.getFigureSource();
		getSelectionModel().removeSelection(button);
		
		ProbeIt.getInstance().getLogger().setViewerBool(true);
		
		Manager.latestPopup = new PopupContentView(button.getNode().getConclusion(), singleViewOnly);
	}
}
