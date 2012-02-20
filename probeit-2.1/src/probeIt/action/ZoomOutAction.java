package probeIt.action;

import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

import diva.canvas.CanvasPane;
import diva.canvas.JCanvas;

import probeIt.ProbeIt;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.ui.global.JustificationView;
import probeIt.ui.Toolbar;

public class ZoomOutAction extends ProbeItGenericAction
{

	
	public ZoomOutAction(String name)
	{
		super(name, NO_ICON);
	}

	public ZoomOutAction()
	{
		super("(-)", NO_ICON);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
	
        JCanvas canvas = JustificationCanvas.getCurrentCanvas();//.getCanvasPane();
        CanvasPane pane = canvas.getCanvasPane();
        AffineTransform t = new AffineTransform();


        /*double scaleX, scaleY;
        
        scaleX = t.getScaleX();
        scaleY = t.getScaleY();
        
        /*if(scaleX>0.0 && scaleY>0.0)
        {
        	scaleX-=5;
        	scaleY-=5;
        }*/
        if(JustificationCanvas.getScale() >= .15)
        {
        	JustificationCanvas.setScale(JustificationCanvas.getScale() - .1);

        	t.scale(JustificationCanvas.getScale(), JustificationCanvas.getScale());
        	Toolbar.updateZoom();

        	pane.setTransform(t);
        	JustificationView.getInstance().repaint();//getPanner().repaint();
        }
	}
}
