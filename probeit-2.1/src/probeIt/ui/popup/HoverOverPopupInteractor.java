package probeIt.ui.popup;

import java.util.Iterator;
import java.awt.Toolkit;

import probeIt.ProbeIt;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.ui.ViewsManager;
import probeIt.ui.popup.result.PopupContentView;
import pml.PMLNode;
import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.graphics.interactor.NodeSelectionInteractor;
import diva.canvas.interactor.CompositeInteractor;
import diva.canvas.event.LayerEvent;

import diva.canvas.GraphicsPane;

public class HoverOverPopupInteractor extends CompositeInteractor
{

	//*!!Zooming affecting Hover-over coordinates!!! fix!!!
	//When selected, not popping up. Must be because the selection doesn't want it to detect another selection over
	//the same node, when it's already in the LocalJustification Tab. This way not reloading a new tab.

	/** Used to figure how much the mouse has moved, in order to decide whether the user is trying to set off a popup by hovering over.*/
	private static int movement = 0;

	/** Value of how much the mouse can be moved and still trigger a hover over */
	private static int sensitivity = 0;

	/** Used to stop the multiple checking of the same NodeSetGraphic when detecting hover-over.*/
	public static NodeSetGraphic lastOpened;

	// JustificationCanvas

	public HoverOverPopupInteractor(GraphicsPane pane)
	{
		super();
		setMotionEnabled(true);
		pane.getBackgroundEventLayer().addLayerMotionListener(this);
	}

	public void mouseMoved(LayerEvent e)
	{
		movement++;
		super.mouseMoved(e);//super.mouseEntered(e);
		System.out.println("MOUSE MOVED!");
		//update movement
		int tempMove = movement;

		Iterator figures = JustificationCanvas.justCanvas._layer.figures();

		boolean found = false;

		if(NodeSelectionInteractor._lastPointedFigure != null && mouseInside(NodeSelectionInteractor._lastPointedFigure ,e))
		{
			System.out.println("AND HAS ENTERED A NODESET!");

			Runnable runnable = new WaitThread(e, tempMove, NodeSelectionInteractor._lastPointedFigure);
			Thread thread = new Thread(runnable);
			thread.start();

		}
		else
		{
			while(figures.hasNext() && !found)
			{
				//System.out.println("Running");
				Object temp = figures.next();
				//System.out.println("Figures: "+temp.getClass().getSimpleName().trim());

				if(temp.getClass().getSimpleName().trim().equalsIgnoreCase("NodeSetGraphic") ) //((NodeSetGraphic)temp).contains(new Point2D.Double(e.getX(), e.getY())))//
				{
					//System.out.println("TRUE");
					/*NodeSetGraphic nodeSet = (NodeSetGraphic)temp;

					int x = nodeSet.getX();
					int y = nodeSet.getY();

					//System.out.println("X: "+x+" < "+e.getX()+" < "+(NodeSetGraphic.WIDTH+x));
					//System.out.println("Y: "+y+" < "+e.getY()+" < "+(NodeSetGraphic.HEIGHT+y));
					if(nodeSet != lastOpened && x*JustificationCanvas.scale < e.getX() &&  (NodeSetGraphic.WIDTH+x)*JustificationCanvas.scale > e.getX() && y*JustificationCanvas.scale < e.getY() &&  (NodeSetGraphic.HEIGHT+y)*JustificationCanvas.scale > e.getY())
					{
						found = true;
						System.out.println("AND HAS ENTERED A NODESET!");

						Runnable runnable = new WaitThread(e, tempMove, nodeSet);
						Thread thread = new Thread(runnable);
						thread.start();

					}*/
					NodeSetGraphic nodeSet = (NodeSetGraphic)temp;

					if(mouseInside(nodeSet, e))
					{
						found = true;
						System.out.println("AND HAS ENTERED A NODESET!");

						Runnable runnable = new WaitThread(e, tempMove, nodeSet);
						Thread thread = new Thread(runnable);
						thread.start();
					}
				}
				//else
				//System.out.println("FALSE!!");
			}
		}

	}

	public boolean mouseInside(NodeSetGraphic nodeSet, LayerEvent e)
	{
		int x = nodeSet.getX();
		int y = nodeSet.getY();

		//System.out.println("X: "+x+" < "+e.getX()+" < "+(NodeSetGraphic.WIDTH+x));
		//System.out.println("Y: "+y+" < "+e.getY()+" < "+(NodeSetGraphic.HEIGHT+y));
		if(nodeSet != lastOpened && x*JustificationCanvas.getScale() < e.getX() &&  (NodeSetGraphic.WIDTH+x)*JustificationCanvas.getScale() > e.getX() && y*JustificationCanvas.getScale() < e.getY() &&  (NodeSetGraphic.HEIGHT+y)*JustificationCanvas.getScale() > e.getY())
		{
			return true;
		}

		return false;
	}

	public void mouseExited(LayerEvent e)
	{
		super.mouseExited(e);
		System.out.println("MOUSE EXITED!");
		movement+=sensitivity+1;
	}


	private class WaitThread implements Runnable
	{
		LayerEvent e;
		int tempMove;
		NodeSetGraphic nodeSet;

		public WaitThread(LayerEvent event, int move, NodeSetGraphic node)
		{
			e=event;
			tempMove = move;
			nodeSet = node;
		}

		//-------------------------problems/bugs----------------------------------------------
		//1. few problems when waiting on a long load pop-up and go to another one.
		//2. when the last pop-up is minimized it doesn't pop back to the front of screen...

		//3. Should it pop a nodeset viewer even if there's already a kept view of the same nodeSet ?
		// (probably should[reapeated nodes in tree, closed kept window not allowing you to open new one(could be fixed)])

		//4. Bug when waiting over nodeset and then clicking... then popup appears over local Justification view
		// (clicking should cancel popup generation)
		// now problem when just clicking (just on the cloud(Telescope) justification) (Tony's doesn't even seem to react, whatt's the difference?)

		public void run()
		{


			//wait a little...
			try
			{
				System.out.println("Waiting...");
				Thread.sleep(500);
			}catch(InterruptedException ex)
			{
				System.out.println("Waiting for Hover Over Interrupt Exception!");
				ex.printStackTrace();
				return;
			}


			//check if the mouse has moved too much since the original move was detected.
			if(movement <= tempMove+sensitivity && movement >= tempMove)//if the mouse has been relatively still (decided by sensitivity) then popup the information.
			{
				lastOpened = nodeSet;
				System.out.println("Creating Popup...");
				boolean singleViewOnly = !ViewsManager.getInstance().isEnabledFeature_MV();

				//System.out.println("IN! : "+temp.getClass());
				PMLNode node = ((NodeSetGraphic)nodeSet).getNode();//nodeSet.getNode();
				//getSelectionModel().removeSelection(button);

				//? should this still be logged in?
				ProbeIt.getInstance().getLogger().setViewerBool(true);

//				???
				if(Manager.latestPopup!=null)
					Manager.latestPopup.changeContent(node.getConclusion(), singleViewOnly);
				else//......
					Manager.latestPopup = new PopupContentView(node.getConclusion(), singleViewOnly);

				if(!Manager.latestPopup.stayPut)
				{
					//Popup Orientation.
					int screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
					int screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
					int mouseX = e.getXOnScreen();
					int mouseY = e.getYOnScreen();
					int popX = mouseX;
					int popY = mouseY;

					//System.out.println("Screen size : ("+x+", "+y+")");
					//	System.out.println("Mouse Pos : ("+mouseX+", "+mouseY+")");

					if(Manager.latestPopup.getWidth() + mouseX > screenX && (Manager.latestPopup.getWidth() + mouseX)-screenX > -(mouseX-Manager.latestPopup.getWidth()))
						popX -= Manager.latestPopup.getWidth();

					//assumption is that user wants to try to keep the size of windows as close as possible to their current setting.
					//                                windows bar(30) just an estimate still have to make dynamic
					System.out.println(((Manager.latestPopup.getHeight() + mouseY +30)-screenY) +">"+ (-(mouseY-Manager.latestPopup.getHeight())));
					if(Manager.latestPopup.getHeight() + mouseY + 30 > screenY  && (Manager.latestPopup.getHeight() + mouseY + 30)-screenY > -(mouseY-Manager.latestPopup.getHeight()))
					{	
						if(mouseY - Manager.latestPopup.getHeight() < 0)
							Manager.latestPopup.setSize(Manager.latestPopup.getWidth(), mouseY);
						popY -= Manager.latestPopup.getHeight();
					}


					Manager.latestPopup.setLocation(popX, popY);
				}

				Manager.latestPopup.toFront();
				Manager.latestPopup.requestFocus();

				System.out.println("Done.");
			}
		}
	}



	/*public void mouseEntered(LayerEvent e)
	{
		System.out.println("MOUSE ENTERED!");
		super.mouseEntered(e);

		boolean found = false;
		Iterator figures = JustificationCanvas.justCanvas._layer.figures();

		while(figures.hasNext() && !found)
		{
			Object temp = figures.next();
			System.out.println("Figures: "+temp.getClass());
			if(temp.getClass().equals("class probeIt.graphics.figure.NodeSetGraphic") ) //((NodeSetGraphic)temp).contains(new Point2D.Double(e.getX(), e.getY())))//
			{
				//NodeSetGraphic nodeSet = (NodeSetGraphic)temp;

				//int x = nodeSet.getX();
				//int y = nodeSet.getX();

				//if(x <= e.getX() &&  NodeSetGraphic.WIDTH+x >= e.getX() && y <= e.getY() &&  NodeSetGraphic.HEIGHT+y >= e.getY())
				//{

				boolean singleViewOnly = !ProbeItViewsConfiguration.getInstance().isEnabledFeature_MV();

				//System.out.println("IN! : "+temp.getClass());
				PMLNode node = ((NodeSetGraphic)temp).getNode();//nodeSet.getNode();
				//getSelectionModel().removeSelection(button);

				//? should this still be logged in?
				ProbeIt.getInstance().getLogger().setViewerBool(true);

				if(Manager.latestPopup!=null)
					Manager.latestPopup.changeContent(node.getConclusion(), singleViewOnly);
				else
					Manager.latestPopup = new PopupContentView(node.getConclusion(), singleViewOnly);
				//}
			}

		}

	}*/

	/*public void mousePressed(LayerEvent event)
	{
		super.mousePressed(event);
		boolean singleViewOnly = !ProbeItViewsConfiguration.getInstance().isEnabledFeature_MV();
		PMLNode node = ((NodeSetGraphic) event.getFigureSource()).getNode();
		//getSelectionModel().removeSelection(button);

		ProbeIt.getInstance().getLogger().setViewerBool(true);

		if(Manager.latestPopup!=null)
			Manager.latestPopup.changeContent(node.getConclusion(), singleViewOnly);
		else
			Manager.latestPopup = new PopupContentView(node.getConclusion(), singleViewOnly);
	}*/
}
