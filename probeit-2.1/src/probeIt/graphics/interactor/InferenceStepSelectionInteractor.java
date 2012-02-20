package probeIt.graphics.interactor;

import java.awt.Point;

import probeIt.ProbeIt;
import probeIt.graphics.buttons.InferenceStepButton;
import pml.PMLNode;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.global.JustificationView;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.graphics.figure.NodeSetGraphic;
import diva.canvas.event.LayerEvent;
import diva.canvas.interactor.SelectionInteractor;
import diva.canvas.event.LayerMotionListener;

public class InferenceStepSelectionInteractor extends SelectionInteractor implements LayerMotionListener
{
	
	public void mousePressed(LayerEvent event)
	{
		super.mousePressed(event);
		
		InferenceStepButton button = (InferenceStepButton) event.getFigureSource();
		this.getSelectionModel().removeSelection(button);
		
		if(button.getNext())
		{
			if((button.getNode().getNumberOfInferenceSteps()-1-button.getNode().getIndex()) > 0)//if hasNextInferenceStep
			{button.getNode().getNextInferenceStep();}
		}
		else
		{
			if((button.getNode().getIndex()) > 0)//if hasPrevInferenceStep
			{button.getNode().getPrevInferenceStep();}
		}
  
    	NodeSetGraphic nsGraphic = JustificationCanvas.getDrawnNode(button.getNode());
    	JustificationView.getInstance().setPointOfInterest(nsGraphic.getX(), nsGraphic.getY());
    	
		//repaint
		//PMLNode node = ProbeIt.getInstance().getEmbeddedProbeIt().getJustification();
    	//ProbeIt.getInstance().getEmbeddedProbeIt().setJustification(node, true);
		((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.GLOBAL_JUSTIFICATION_TAB);
	}
}
