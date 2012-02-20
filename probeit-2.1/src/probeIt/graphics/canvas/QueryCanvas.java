package probeIt.graphics.canvas;

import diva.canvas.FigureLayer;
import diva.canvas.GraphicsPane;
import diva.canvas.JCanvas;
import probeIt.ProbeIt;
import probeIt.graphics.buttons.SourceButton;
import probeIt.graphics.buttons.TrustButton;
import probeIt.graphics.buttons.ViewerButton;
import probeIt.graphics.figure.AnswerGraphic;
import probeIt.graphics.figure.NodeSetGraphic;
import java.util.HashMap;
import probeIt.graphics.interactor.AnswerSelectionInteractor;
import probeIt.graphics.interactor.ViewerButtonSelectionInteractor;
import pml.PMLNode;
import pml.loading.Loader;
import java.util.HashMap;
public class QueryCanvas
{
	
	//private static int X_SEPARATION = NodeSetGraphic.WIDTH + 30;
	//private static int Y_SEPARATION = NodeSetGraphic.HEIGHT + ViewerButton.HEIGHT + 50;
	private static int QUERY_X = 10;
	private static int QUERY_Y = 10;
	
	HashMap nodeSetMap;
	
	int counter;
	
	//int counter;
	
	// The JCanvas
	private JCanvas _canvas;

	// The GraphicsPane
	private GraphicsPane _graphicsPane;

	private int _maxX;
	private int _maxY;

	private int currentX, currentY;

	private FigureLayer _layer;
	
	private boolean trustMode;
	private boolean justifications;
	boolean showSources;

	public QueryCanvas()
	{
		currentX = 10;
		currentY = 10;
		_maxX = 0;
		_maxY = 0;
		counter = 0;
		_canvas = new JCanvas();
		_graphicsPane = (GraphicsPane) _canvas.getCanvasPane();
		
		_layer = _graphicsPane.getForegroundLayer();
		
		nodeSetMap = new HashMap();
	}
	public void clear()
	{
		_layer.clear();
		reset();
	}
	
	public void reset()
	{
		currentX = 10;
		currentY = 10;
		_maxX = 0;
		_maxY = 0;
	}
	
	public void setShowSources(boolean show)
	{showSources = show;}
	
	public void setJustification(boolean just)
	{justifications = just;}
	
	public void setTrust(boolean trust)
	{trustMode = trust;}

	public JCanvas getJCanvas()
	{return _canvas;}
	
	private void setNextPoint()
	{
		if (currentX + NodeSetGraphic.WIDTH + 30 > 700)
		{
			currentX = QUERY_X;
			currentY = currentY + NodeSetGraphic.HEIGHT + ViewerButton.HEIGHT + 75;
		} else
		{
			currentX = currentX + NodeSetGraphic.WIDTH + 30;
		}
	}

	
	public AnswerGraphic drawNode(String uri)
	{
		// get new bounds of cavas
		if (currentX > _maxX)
			_maxX = currentX;
		if (currentY > _maxY)
			_maxY = currentY;
	
		AnswerGraphic node = (AnswerGraphic)nodeSetMap.get(uri);
		PMLNode nodeSet;
		if(node == null)
		{
			Loader answerLoader = new Loader(ProbeIt.getInstance().isRemote());
			nodeSet = answerLoader.loadNode(uri,null);
		
			if(nodeSet.getID().equalsIgnoreCase(Loader.DEFAULT_ID))
				nodeSet.setID(getNextID());
				
			node = new AnswerGraphic(currentX, currentY, nodeSet, justifications);
		}
		else
			nodeSet = node.getNode();
			
		node.addViewerButton();
		_layer.add(node);
		
		if(showSources)
		{
			Loader factory = new Loader(ProbeIt.getInstance().isRemote());
			nodeSet = factory.loadJustification(nodeSet.getURI(), nodeSet.getID());
			
			SourceButton moreSources=new SourceButton(currentX, currentY+NodeSetGraphic.HEIGHT+ViewerButton.HEIGHT + 35, nodeSet);
			_layer.add(moreSources);
		}
		
		//hackk..right now the only artifacts with trust maps are in PS format luckily
		if(trustMode && nodeSet.getConclusion().getConclusionClassifier().isPS())
			node.addTrustButton();
		
		setNextPoint();
		
		nodeSetMap.put(uri, node);
		
		return node;
	}
	
	private String getNextID()
	{return String.valueOf(counter++);}
	
	/*
	private QueryGraphic drawQuery()
	{
		_queryGraphic = new QueryGraphic(query, QUERY_X, QUERY_Y);
		return _queryGraphic;
	}*/
}
