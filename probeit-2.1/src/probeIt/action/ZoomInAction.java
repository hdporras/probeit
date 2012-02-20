package probeIt.action;

import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import diva.canvas.JCanvas;
import diva.canvas.CanvasPane;

import probeIt.ProbeIt;
import probeIt.ui.Toolbar;
import probeIt.ui.global.JustificationView;
import probeIt.graphics.canvas.JustificationCanvas;

public class ZoomInAction extends ProbeItGenericAction
{

	
	public ZoomInAction(String name)
	{
		super(name, NO_ICON);
	}

	public ZoomInAction()
	{
		super("(+)", NO_ICON);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		//QueryCanvasSummaries canvas=QueryCanvasSummaries.getQueryInstance();
		
        JCanvas canvas = JustificationCanvas.getCurrentCanvas();//.getCanvasPane();
        CanvasPane pane = canvas.getCanvasPane();
        AffineTransform t = new AffineTransform();

        /*double scaleX, scaleY;
        
        scaleX = t.getScaleX();
        scaleY = t.getScaleY();
        
        /*if(scaleX<100.0 && scaleY<100.0)
        {
        	scaleX+=5;
        	scaleY+=5;
        }*/
        if(JustificationCanvas.getScale() <= 1.90)
        {
        	JustificationCanvas.setScale(JustificationCanvas.getScale() + .1);

        	t.scale(JustificationCanvas.getScale(), JustificationCanvas.getScale());
        	Toolbar.updateZoom();

        	pane.setTransform(t);
        	JustificationView.getInstance().repaint();
        }
	}
}
