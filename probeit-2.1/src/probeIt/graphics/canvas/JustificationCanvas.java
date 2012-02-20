package probeIt.graphics.canvas;


import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.util.HashMap;
import java.awt.Point;
import probeIt.ProbeIt;
import probeIt.graphics.buttons.ViewerButton;
import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.graphics.interactor.ViewerButtonSelectionInteractor;
import probeIt.graphics.interactor.NodeSelectionInteractor;
import pml.PMLNode;
import probeIt.graphics.figure.ForestNodeSetGraphic;
import probeIt.ui.popup.HoverOverPopupInteractor;
import probeIt.ui.model.ViewsModel;
import diva.canvas.CanvasPane;
import diva.canvas.FigureLayer;
import diva.canvas.GraphicsPane;
import diva.canvas.JCanvas;
import diva.canvas.Site;
import diva.canvas.connector.Arrowhead;
import diva.canvas.connector.ManhattanConnector;
import diva.canvas.connector.StraightConnector;
import diva.canvas.toolbox.BasicController;


public class JustificationCanvas
{
	// The JCanvas
	private JCanvas _canvas;
	
	//has the coordinates of a location of interest
	static Point pointOfInterest;
	
	// The GraphicsPane
	private GraphicsPane _graphicsPane;

	// nodesetGraphics
	public static HashMap nodeGraphics;//is it all right to be static!!?!?!
	
	private NodeSelectionInteractor _defaultInteractor;

	private int _maxX;
	private int _maxY;

	public FigureLayer _layer;
	ViewerButtonSelectionInteractor interactor;
	
	//indicates whether nodesets drawn on this canvas should accept local view requests on that node
	boolean provenance;
	
	//zooming variables
	public static JCanvas currentCanvas;
	
	static double scale = 1;
	
	/** Current Justification Canvas */
	public static JustificationCanvas justCanvas;
	HoverOverPopupInteractor popupInteractor;
	
	public JustificationCanvas()
	{
		nodeGraphics = new HashMap();
		_maxX = 0;
		_maxY = 0;
		_canvas = new JCanvas();
		_graphicsPane = (GraphicsPane) _canvas.getCanvasPane();
		
		
		popupInteractor = new HoverOverPopupInteractor(_graphicsPane);
		_defaultInteractor = new NodeSelectionInteractor();

		interactor = new ViewerButtonSelectionInteractor();
		
		//I believe this has some sort of side effect so I will leave it
		new BasicController(_graphicsPane);
		
		_layer = _graphicsPane.getForegroundLayer();

		currentCanvas = _canvas;
		
		//resetting to previous zoom.
		CanvasPane pane = _canvas.getCanvasPane();
        AffineTransform t = new AffineTransform();
        
        t.scale(scale, scale);
        
        pane.setTransform(t);
        
        justCanvas= this;

        //resetting to default zoom
		//scale=1;
	}
	
	public static double getScale()
	{return scale;}
	
	public static void setScale(double s)
	{
		scale = s;
		updatePointOfInterest();
	}
	
	public void setPointOfInterest(int x, int y)
	{	
		pointOfInterest = new Point((int)(x * scale),(int)(y * scale));
	}
	
	private static void updatePointOfInterest()
	{
		pointOfInterest = new Point((int)(pointOfInterest.getX() * scale),(int)(pointOfInterest.getY() * scale));
	}
	
	public Point getPointOfInterest()
	{
		if(pointOfInterest != null)
		{
			return pointOfInterest;
		}
		return new Point(0,0);
	}
	
	public void highlightNodeSetBlock(String uri)
	{
		System.out.println("trying to find ns with uri of: " + uri);
		for(int i = 0; i < _layer.getFigureCount(); i ++)
		{	
			Object afig = _layer.getFigures().get(i);
			
			NodeSetGraphic nsBlock;
			
			if(afig instanceof NodeSetGraphic)
			{
				nsBlock = (NodeSetGraphic)afig;
				if(nsBlock.getURI().equals(uri))
				{
					
					System.out.println("found match");
					
					setPointOfInterest(nsBlock.getX(), nsBlock.getY());
					
					Object currentSelection = ((NodeSelectionInteractor)nsBlock.getInteractor()).getSelectionModel().getFirstSelection();
					((NodeSelectionInteractor)nsBlock.getInteractor()).getSelectionModel().removeSelection(currentSelection);
					((NodeSelectionInteractor)nsBlock.getInteractor()).getSelectionModel().addSelection(nsBlock);
				}
			}
		}
	}
	
	public void setProvenance(boolean value)
	{provenance = value;}
	
	public static NodeSetGraphic getDrawnNode(PMLNode aNode)
	{return (NodeSetGraphic)nodeGraphics.get(aNode.getURI());}

	public void makeEdge(NodeSetGraphic parentNode, NodeSetGraphic childNode)
	{
		Site childSite = childNode.getSouth();		
		Site parSite = parentNode.getNorth();

		//use straight line connector?
		boolean dagConnect = childNode.isReferencedManyTimes();
		
		System.out.println("childsite : "+childSite.getX()+", "+childSite.getY());
		System.out.println("parsite : "+parSite.getX()+", "+parSite.getY());
		
		Arrowhead arrow = new Arrowhead(parSite.getX(), parSite.getY(), parSite.getNormal());
		if(dagConnect)
		{
			StraightConnector connector = new StraightConnector(childSite, parSite);
			connector.setHeadEnd(arrow);
			_layer.add(connector);
		}
		else
		{
			ManhattanConnector connector = new ManhattanConnector(childSite,parSite);
			connector.setHeadEnd(arrow);
			_layer.add(connector);
		}
	}
	
	
	public void makeForestEdge(ForestNodeSetGraphic parentNode, NodeSetGraphic childNode, int ISIndex)
	{
		Site childSite = childNode.getSouth();		
		Site parSite = parentNode.getNorth(ISIndex);

		//use straight line connector?
		boolean dagConnect = childNode.isReferencedManyTimes();
		
		System.out.println("childsite : "+childSite.getX()+", "+childSite.getY());
		System.out.println("parsite : "+parSite.getX()+", "+parSite.getY());
		
		Arrowhead arrow = new Arrowhead(parSite.getX(), parSite.getY(), parSite.getNormal());
		if(dagConnect)
		{
			StraightConnector connector = new StraightConnector(childSite, parSite);
			connector.setHeadEnd(arrow);
			_layer.add(connector);
		}
		else
		{
			ManhattanConnector connector = new ManhattanConnector(childSite,parSite);
			connector.setHeadEnd(arrow);
			_layer.add(connector);
		}
	}
	
	
	public void makeEdge(NodeSetGraphic parentNode, NodeSetGraphic childNode, boolean dag)
	{
		Site childSite = childNode.getSouth();
		Site parSite = parentNode.getNorth();

		//use straight line connector
		boolean dagConnect = dag;
		
		Arrowhead arrow = new Arrowhead(parSite.getX(), parSite.getY(), parSite.getNormal());
		if(dagConnect)
		{
			StraightConnector connector = new StraightConnector(childSite, parSite);
			connector.setHeadEnd(arrow);
			_layer.add(connector);
		}
		else
		{
			ManhattanConnector connector = new ManhattanConnector(childSite,parSite);
			connector.setHeadEnd(arrow);
			_layer.add(connector);
		}
	}
	
	public NodeSetGraphic drawForestNode(int x, int y, PMLNode node)
	{	
		ForestNodeSetGraphic nodeSet = new ForestNodeSetGraphic(x, y, node);
		return drawNode(nodeSet);
	}

	public NodeSetGraphic drawNode(int x, int y, PMLNode node)
	{	
		NodeSetGraphic nodeSet = new NodeSetGraphic(x, y, node);
		return drawNode(nodeSet);
	}
	
	public NodeSetGraphic drawNode(NodeSetGraphic nodeSet)
	{
		int x = nodeSet.getX();
		int y = nodeSet.getY();
		//get new bounds of cavas
		if (x > _maxX)
			_maxX = x;
		if (y > _maxY)
			_maxY = y;
		
		//ViewerButton button = new ViewerButton(x+NodeSetGraphic.WIDTH-ViewerButton.WIDTH, y + NodeSetGraphic.HEIGHT, nodeSet.getNode());
		//button.setInteractor(interactor);
		
		nodeSet.setInteractor(popupInteractor);//new HoverOverPopupInteractor(_graphicsPane));
		
		if(provenance)
			nodeSet.setInteractor(_defaultInteractor);

		_layer.add(nodeSet);
		//_layer.add(button);

		
		/* if root node, then highlight and set to point of interest*/
		if(ViewsModel.getInstance().getJustification().getURI().equalsIgnoreCase(nodeSet.getURI()))
		{
			((NodeSelectionInteractor)nodeSet.getInteractor()).getSelectionModel().addSelection(nodeSet);
			setPointOfInterest(nodeSet.getX(), nodeSet.getY());
		}
		
		//add nodeset grapic to cache
		nodeGraphics.put(nodeSet.getURI(), nodeSet);
		return nodeSet;

	}
	public JCanvas getCurrentDAG()
	{
		_canvas.setPreferredSize(new Dimension(_maxX + 300, _maxY + 300));
		return _canvas;
	}
	
	public JCanvas getJCanvas()
	{return _canvas;}
	
	public static JCanvas getCurrentCanvas()
	{return currentCanvas;}
}
