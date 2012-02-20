package probeIt.graphics.figure;

import java.awt.geom.AffineTransform;
import java.awt.Font;
import probeIt.util.LineFormatter;
import pml.PMLNode;
import diva.canvas.CompositeFigure;

import java.awt.geom.Point2D;
import diva.canvas.Site;
import diva.canvas.interactor.BoundsGeometry;
import diva.canvas.toolbox.BasicRectangle;
import diva.canvas.toolbox.LabelWrapper;
import diva.canvas.toolbox.LabelFigure;
import java.net.URL;
import probeIt.graphics.buttons.*;
import probeIt.graphics.interactor.AnswerSelectionInteractor;

public class AnswerGraphic extends CompositeFigure
{

	// The geometry object
	private BoundsGeometry _nodeSetBlockGeom;

	int _nodeSetX;
	int _nodeSetY;

	int _inferenceStepX;
	int _inferenceStepY;

	PMLNode _node;

	BasicRectangle _nodeSetBlock;
	BasicRectangle _idBlock;
	
	private AnswerSelectionInteractor _defaultInteractor;

	boolean justificationEnabled;
	public AnswerGraphic(int x, int y, PMLNode nodeSet, boolean just)
	{
		justificationEnabled = just;
		_defaultInteractor = new AnswerSelectionInteractor();
		
		_nodeSetX = x;
		_nodeSetY = y;

		//int xPadding =;
		
		int iswidth = NodeSetGraphic.getISWidth(_node);
		
		_inferenceStepX = _nodeSetX + ( (NodeSetGraphic.WIDTH - iswidth) /2 );
		_inferenceStepY = _nodeSetY + 4;

		_node = nodeSet;

		addNodeSetBlock();
		addIDBlock();
		//addIDBlockBorder();
		addConclusionGraphic();
		addMetadataLabel();
		
		_nodeSetBlockGeom = new BoundsGeometry(this, getBounds());
		
		if(justificationEnabled)
			setInteractor(_defaultInteractor);
	}

	public void addViewerButton()
	{
		ViewerButton button = new ViewerButton(_nodeSetX+NodeSetGraphic.WIDTH-ViewerButton.WIDTH, _nodeSetY + NodeSetGraphic.HEIGHT + 34, _node);
		add(button);
	}
	
	public void addTrustButton()
	{
		TrustButton trustButton = new TrustButton((_nodeSetX + NodeSetGraphic.WIDTH-(2 * ViewerButton.WIDTH)), _nodeSetY+NodeSetGraphic.HEIGHT + 34, _node);
		add(trustButton);
	}
	
	private String getDateText()
	{
		String dateText = null;
		if(_node.getDate() != null)
			dateText = LineFormatter.formatSingleLineText(_node.getDate(), 100);
		return dateText;
	}
	
	private String getURLText()
	{
		String urlText = null;
		
		try
		{
			if(_node.getConclusion().getHasURL() == null)
				urlText = LineFormatter.formatSingleLineText(new URL(_node.getURI()).getHost(),30) + " [embedded]";
			else
				urlText = LineFormatter.formatSingleLineText(new URL(_node.getConclusion().getHasURL()).getHost(),40);
			
			return urlText;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private String getFromEngineText()
	{
		String engineText = null;
		if(_node.getCurrentlySelectedInferenceStep().getIEName() != null)
			engineText =  LineFormatter.formatText(_node.getCurrentlySelectedInferenceStep().getIEName(), 100, 1);
		return engineText;
	}
	
	private String getMetadata()
	{
		String dateText = this.getDateText();
		String urlText = this.getURLText();
		String fromEngineText = this.getFromEngineText();
		
		String metadata = "";
		if(dateText != null)
			metadata += "- " + dateText + "\n";
		if(urlText != null)
			metadata += "- " + urlText + "\n";
		if(fromEngineText != null)
			metadata += "- " + fromEngineText;
		
		return metadata;
	}

		
	private void addMetadataLabel()
	{
		double startX = _nodeSetBlock.getOrigin().getX();
		double startY = _nodeSetY + NodeSetGraphic.HEIGHT + 20;

		String metadata = this.getMetadata();
		
		LabelFigure lf = new LabelFigure(metadata);
		
		lf.setFont(new Font("Times", Font.BOLD, 10));
		lf.translateTo(startX, startY);
			
		add(lf);
	}
	
	public String getURI()
	{return _node.getURI();}

	private void addNodeSetBlock()
	{
		java.awt.Color color;
		color = new java.awt.Color(255, 255, 255);

		_nodeSetBlock = new BasicRectangle(_nodeSetX, _nodeSetY,
				NodeSetGraphic.WIDTH, NodeSetGraphic.HEIGHT, color);
		add(_nodeSetBlock);
	}

	private void addIDBlock()
	{
		int startX = _nodeSetX;
		int startY = _nodeSetY;

		java.awt.Color color;
		color = new java.awt.Color(255, 255, 255);

		_idBlock = new BasicRectangle(startX, startY, 30, 30, color);
		
		LabelWrapper lw = new LabelWrapper(_idBlock, _node.getID());
		add(lw);
	}
	
	private void addIDBlockBorder()
	{
		int startX = _nodeSetX; // + NodeSetGraphic.WIDTH - 30;
		int startY = _nodeSetY; // + NodeSetGraphic.HEIGHT - 30;
		add(new BasicRectangle(startX, startY, 30, 30));
	}

	private void addConclusionGraphic()
	{
		Point2D center = _nodeSetBlock.getOrigin();
		ConclusionLabel label = new ConclusionLabel(_node.getConclusion(), center.getX(), center.getY() + ((60/270) * NodeSetGraphic.HEIGHT));
		ConclusionGraphic graphic = new ConclusionGraphic(_node.getConclusion(), center.getX(), center.getY() - 100/270 * NodeSetGraphic.HEIGHT);
		
		
		
		if (!graphic.isEmpty())
		{
			add(graphic);
			add(graphic.getGraphicBorder());
		}
		else
		{
			add(label);
		}
	}

	public PMLNode getNode()
	{
		return _node;
	}

	public String getID()
	{
		return _node.getID();
	}

	public int getX()
	{
		return _nodeSetX;
	}

	public int getY()
	{
		return _nodeSetY;
	}

	/**
	 * Get the north site.
	 */
	public Site getNorth()
	{
		return _nodeSetBlockGeom.getN();
	}

	/**
	 * Get the south site.
	 */
	public Site getSouth()
	{
		return _nodeSetBlockGeom.getS();
	}

	/**
	 * Get the east site.
	 */
	public Site getEast()
	{
		return _nodeSetBlockGeom.getE();
	}

	/**
	 * Get the west site.
	 */
	public Site getWest()
	{
		return _nodeSetBlockGeom.getW();
	}

	/**
	 * Update the geometry
	 */
	public void transform(AffineTransform at)
	{
		super.transform(at);
		_nodeSetBlockGeom.setShape(getShape());
	}

	/**
	 * Update the geometry
	 */
	public void translate(double x, double y)
	{
		super.translate(x, y);
		_nodeSetBlockGeom.translate(x, y);
	}

}
