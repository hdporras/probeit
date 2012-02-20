package probeIt.graphics.figure;

import java.awt.geom.AffineTransform;
import java.awt.Font;

import probeIt.graphics.CanvasDrawer_DAGJustification;
import pml.PMLNode;
import probeIt.graphics.buttons.InferenceStepButton;
import diva.canvas.CompositeFigure;
import java.awt.geom.Point2D;
import diva.canvas.Site;
import diva.canvas.interactor.BoundsGeometry;
import diva.canvas.toolbox.BasicRectangle;
import diva.canvas.toolbox.LabelFigure;

public class NodeSetGraphic extends CompositeFigure
{

	public static int WIDTH = 230;
	public static int HEIGHT = 125;
	public int IS_WIDTH = WIDTH - 6;
	public static int IS_HEIGHT = 30;

	// The geometry object
	private BoundsGeometry _nodeSetBlockGeom;
	private BoundsGeometry _inferenceStepBlockGeom;

	//position of the NodeSetGraphic on the canvas
	int _nodeSetX;
	int _nodeSetY;

	// where the arrow will point to?
	int _inferenceStepX;
	int _inferenceStepY;
	
	//point in the middle of all children
	public int _idealX;

	PMLNode _node;
	
	public CanvasDrawer_DAGJustification.CellWrapper cellwrapper;
	public boolean sugiyamaVisited;

	boolean isReferencedManyTimes = false;
	
	BasicRectangle inferenceStepBlock;
	BasicRectangle nodeSetBlock;
	
	public static boolean showInferenceStepInfo = true;
	public static boolean showConclusion = true;
	public static boolean showArrows = true;
	
	/**
	 * Create a new instance of this figure.
	 */
	
	public NodeSetGraphic()
	{}
	
	public NodeSetGraphic(int x, int y, PMLNode nodeSet)
	{
		_nodeSetX = x;
		_nodeSetY = y;

		_node = nodeSet;
		
		IS_WIDTH = getISWidth(_node);
		
		_inferenceStepX = _nodeSetX + ((WIDTH - IS_WIDTH) / 2);
		_inferenceStepY = _nodeSetY + 4;

		setup();
		
		sugiyamaVisited=false;
	}
	
	public NodeSetGraphic(PMLNode nodeSet)
	{
		_node = nodeSet;
		sugiyamaVisited=false;
	}
	
	public void setXY(int x, int y)
	{
		_nodeSetX = x;
		_nodeSetY = y;
		
		IS_WIDTH = getISWidth(_node);
		
		_inferenceStepX = _nodeSetX + ((WIDTH - IS_WIDTH) / 2);
		_inferenceStepY = _nodeSetY + 4;
	}
	
	public void setup()
	{
        //call in this order for now
		addNodeSetBlock();
		addNodeSetBorder();

		if(_node.getNumberOfInferenceSteps() > 0 && (showArrows || showInferenceStepInfo))
		{
			System.out.println("has inference step...");
			
				addInferenceStepBlock();
				addInferenceStepBorder();
			
				addRuleLabel();
				addEngineLabel();
		}
		else
			buildInferenceStepBlock();

		addConclusionGraphic();

		_nodeSetBlockGeom = new BoundsGeometry(this, getBounds());
		//if(showArrows || showInferenceStepInfo)
			_inferenceStepBlockGeom = new BoundsGeometry(this, inferenceStepBlock.getBounds());
	}
	
	public void setReferenceManyTimes()
	{isReferencedManyTimes = true;}
	
	public boolean isReferencedManyTimes()
	{return isReferencedManyTimes;}

	public String getURI()
	{return _node.getURI();}

	private void addNodeSetBorder()
	{add(new BasicRectangle(_nodeSetX, _nodeSetY, WIDTH, HEIGHT));}

	private void addInferenceStepBorder()
	{
		if(_node.getNumberOfInferenceSteps() > 1 && showArrows)
		{
			if(showInferenceStepInfo)
			{
				add(new BasicRectangle( _nodeSetX+3, _inferenceStepY, ((WIDTH - IS_WIDTH) / 2)-6, IS_HEIGHT));
				add(new BasicRectangle(_inferenceStepX, _inferenceStepY, IS_WIDTH,IS_HEIGHT));
				add(new BasicRectangle( _inferenceStepX + IS_WIDTH+3, _inferenceStepY, ((WIDTH - IS_WIDTH) / 2)-6, IS_HEIGHT));
			}
			else
			{
				add(new BasicRectangle( _nodeSetX+3, _inferenceStepY, (IS_WIDTH / 2)-6, IS_HEIGHT));
				add(new BasicRectangle( _nodeSetX + (IS_WIDTH/2)+6, _inferenceStepY, (IS_WIDTH / 2)-6, IS_HEIGHT));
			}
		}
		else if(showInferenceStepInfo)
		{
			add(new BasicRectangle(_inferenceStepX, _inferenceStepY, IS_WIDTH, IS_HEIGHT));
		}
	}

	private void addNodeSetBlock()
	{
		java.awt.Color color;

		if (_node.getCurrentlySelectedInferenceStep().isLeaf())
			color = new java.awt.Color(255, 165, 79);
		else
			color = new java.awt.Color(173, 216, 230);

		nodeSetBlock = new BasicRectangle(_nodeSetX, _nodeSetY, WIDTH, HEIGHT, color);

		add(nodeSetBlock);
	}

	private void addInferenceStepBlock()
	{
		inferenceStepBlock = new BasicRectangle(_inferenceStepX, _inferenceStepY, IS_WIDTH, IS_HEIGHT, java.awt.Color.white);

		if(_node.getNumberOfInferenceSteps() > 1 && showArrows)
		{
			if(showInferenceStepInfo)
			{
				add(new InferenceStepButton( _nodeSetX+3, _inferenceStepY, ((WIDTH - IS_WIDTH) / 2)-6, IS_HEIGHT, _node, false));
				add(inferenceStepBlock);
				add(new InferenceStepButton( _inferenceStepX+IS_WIDTH+3, _inferenceStepY, ((WIDTH - IS_WIDTH) / 2)-6, IS_HEIGHT, _node, true));
			}
			else
			{
				add(new InferenceStepButton( _nodeSetX+3, _inferenceStepY, (IS_WIDTH / 2)-6, IS_HEIGHT, _node, false));
				add(new InferenceStepButton( _nodeSetX + (IS_WIDTH/2)+6, _inferenceStepY, ( IS_WIDTH / 2)-6, IS_HEIGHT, _node, true));
			}
		}
		else if(showInferenceStepInfo)
			add(inferenceStepBlock);
	}
	
	private void buildInferenceStepBlock()
	{
		inferenceStepBlock = new BasicRectangle(_inferenceStepX,_inferenceStepY, IS_WIDTH, IS_HEIGHT, java.awt.Color.white);
	}

	private void addEngineLabel()
	{
		if(showInferenceStepInfo)
		{
			String engineText = "no engine";
			
			if(_node.getCurrentlySelectedInferenceStep().getIEName() != null)
				engineText = _node.getCurrentlySelectedInferenceStep().getIEName();
			
			LabelFigure lf = new LabelFigure(engineText);
			
			lf.setFont(new Font("Times", Font.PLAIN, 10));
			Point2D center = inferenceStepBlock.getOrigin();
			lf.translateTo(center.getX(), center.getY() + 7);
			add(lf);
		}
	}

	private void addRuleLabel()
	{
		if(showInferenceStepInfo)
		{
			String ruleText = "no rule";
			
			if(_node.getCurrentlySelectedInferenceStep().getRuleName() != null)
				ruleText = _node.getCurrentlySelectedInferenceStep().getRuleName();
			
			LabelFigure lf = new LabelFigure(ruleText);
			lf.setFont(new Font("Times", Font.PLAIN, 10));
			Point2D center = inferenceStepBlock.getOrigin();
			lf.translateTo(center.getX(), center.getY() - 7);
			add(lf);
		}
	}

	private void addConclusionGraphic()
	{
		if(showConclusion)
		{
			Point2D center = nodeSetBlock.getOrigin();
			ConclusionGraphic graphic = new ConclusionGraphic(
					_node.getConclusion(), center.getX(), center.getY() + 20);
	
			if (!graphic.isEmpty())
			{
				add(graphic);
				add(graphic.getGraphicBorder());
			}
			else
			{
				ConclusionLabel label = new ConclusionLabel(_node.getConclusion(), center.getX(), center.getY() + 20);
				add(label);
			}
		}
	}
	
	/** Returns IS icon Width according to whether the node has Multiple inference steps or just a single one.*/
	public static int getISWidth(PMLNode node)
	{
		if(node.getNumberOfInferenceSteps() > 1 && showInferenceStepInfo)
			return ((WIDTH*18)/23);
		else
			return WIDTH-6;
	}

	public PMLNode getNode()
	{return _node;}

	public int getX()
	{return _nodeSetX;}
	

	public int getY()
	{return _nodeSetY;}

	/**
	 * Get the north site.
	 */
	public Site getNorth()
	{return _inferenceStepBlockGeom.getN();}

	/**
	 * Get the south site.
	 */
	public Site getSouth()
	{return _nodeSetBlockGeom.getS();}

	/**
	 * Get the east site.
	 */
	public Site getEast()
	{return _nodeSetBlockGeom.getE();}

	/**
	 * Get the west site.
	 */
	public Site getWest()
	{return _nodeSetBlockGeom.getW();}

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
