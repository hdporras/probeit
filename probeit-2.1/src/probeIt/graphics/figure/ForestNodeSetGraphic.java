package probeIt.graphics.figure;

import java.awt.geom.AffineTransform;
import java.awt.Color;
import java.awt.Font;

import probeIt.graphics.CanvasDrawer_DAGJustification;
import pml.PMLNode;
import pml.util.pml.PMLStatistics;
import probeIt.graphics.buttons.InferenceStepButton;
import diva.canvas.CompositeFigure;
import java.awt.geom.Point2D;
import diva.canvas.Site;
import diva.canvas.interactor.BoundsGeometry;
import diva.canvas.toolbox.BasicRectangle;
import diva.canvas.toolbox.LabelFigure;
import pml.impl.serializable.*;
public class ForestNodeSetGraphic extends NodeSetGraphic
{

	public static int WIDTH = 230;
	public static int HEIGHT = 125;
	public int IS_WIDTH;
	public static int IS_HEIGHT = 30;

	// The geometry object
	private BoundsGeometry _nodeSetBlockGeom;

	//position of the NodeSetGraphic on the canvas
	int _nodeSetX;
	int _nodeSetY;
	
	//point in the middle of all children
	public int _idealX;

	PMLNode _node;
	
	public CanvasDrawer_DAGJustification.CellWrapper cellwrapper;
	public boolean sugiyamaVisited;

	boolean isReferencedManyTimes = false;
	
	BasicRectangle[] inferenceStepBlocks;
	BasicRectangle nodeSetBlock;
	
	/**
	 * Create a new instance of this figure.
	 */
	public ForestNodeSetGraphic(int x, int y, PMLNode nodeSet)
	{
		//super(x, y, nodeSet);
		_nodeSetX = x;
		_nodeSetY = y;

		_node = nodeSet;
		
		IS_WIDTH = getISWidth(_node);
		
		setup();
		
		sugiyamaVisited=false;
	}
	
	public ForestNodeSetGraphic(PMLNode nodeSet)
	{
		//super(nodeSet);
		_node = nodeSet;
		sugiyamaVisited=false;
	}
	
	public void setXY(int x, int y)
	{
		_nodeSetX = x;
		_nodeSetY = y;
		
		IS_WIDTH = getISWidth(_node);
	}
	
	public void setup()
	{
        //call in this order for now
		addNodeSetBlock();
		addNodeSetBorder();
		
		addInferenceStepBlocks();
		addInferenceStepBorders();
			
		addEngineAndRuleLabels();

		addConclusionGraphic();

		_nodeSetBlockGeom = new BoundsGeometry(this, getBounds());
	}
	
	public void setReferenceManyTimes()
	{isReferencedManyTimes = true;}
	
	public boolean isReferencedManyTimes()
	{return isReferencedManyTimes;}

	public String getURI()
	{return _node.getURI();}

	private void addNodeSetBorder()
	{add(new BasicRectangle(_nodeSetX, _nodeSetY, WIDTH, HEIGHT));}

	private void addInferenceStepBorders()
	{
		
		int x,y;
		int offset = 2;
		y = _nodeSetY + offset;

		for(int i = 0; i < inferenceStepBlocks.length; i ++)
		{
			x = _nodeSetX +  (i * IS_WIDTH) + (offset * (1+i));
			inferenceStepBlocks[i] = new BasicRectangle(x, y, IS_WIDTH, IS_HEIGHT); 
			add(inferenceStepBlocks[i]);
		}
	}

	private void addNodeSetBlock()
	{
		java.awt.Color color;

		if (PMLStatistics.isForestLeaf(_node))
			color = new java.awt.Color(255, 165, 79);
		
		else
			color = new java.awt.Color(173, 216, 230);

		nodeSetBlock = new BasicRectangle(_nodeSetX, _nodeSetY, WIDTH, HEIGHT, color);

		add(nodeSetBlock);
	}

	private void addInferenceStepBlocks()
	{
		inferenceStepBlocks = new BasicRectangle[_node.getNumberOfInferenceSteps()];

		int x,y;
		int offset = 2;
		y = _nodeSetY + offset;

		for(int i = 0; i < inferenceStepBlocks.length; i ++)
		{
			x = _nodeSetX + (IS_WIDTH * i) + (offset * (i+1));
			inferenceStepBlocks[i] = new BasicRectangle(x, y, IS_WIDTH, IS_HEIGHT, Color.WHITE); 
			add(inferenceStepBlocks[i]);
		}
	}

	private void addEngineAndRuleLabels()
	{
		for(int i = 0; i < inferenceStepBlocks.length; i ++)
		{
			String engineText = "no engine";
			String ruleText = "no rule";
			
			PMLInferenceStep anIS = _node.getInferenceSteps().get(i);

			if(anIS.getIEName() != null)
				engineText = anIS.getIEName();
			
			if(anIS.getRuleName() != null)
				ruleText = anIS.getRuleName();
			
			LabelFigure lf = new LabelFigure(engineText);
			LabelFigure lf1 = new LabelFigure(ruleText);
			
			lf.setFont(new Font("Times", Font.PLAIN, 10));
			lf1.setFont(new Font("Times", Font.PLAIN, 10));
			
			Point2D center = inferenceStepBlocks[i].getOrigin();
			lf.translateTo(center.getX(), center.getY() + 7);
			lf1.translateTo(center.getX(), center.getY() - 7);
			
			add(lf);
			add(lf1);
		}
	}

	private void addConclusionGraphic()
	{
		Point2D center = nodeSetBlock.getOrigin();
		ConclusionLabel label = new ConclusionLabel(_node.getConclusion(),center.getX(), center.getY() + 20);
		ConclusionGraphic graphic = new ConclusionGraphic(_node.getConclusion(), center.getX(), center.getY() + 20);
	
		if (!graphic.isEmpty())
		{
			add(graphic);
			add(graphic.getGraphicBorder());
		}
		else
			add(label);
	}
	
	/** Returns IS icon Width according to whether the node has Multiple inference steps or just a single one.*/
	public static int getISWidth(PMLNode node)
	{
		if(node.getNumberOfInferenceSteps() > 1)
			return WIDTH/node.getNumberOfInferenceSteps() -4;
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
	public Site getNorth(int index)
	{
		BoundsGeometry geom = new BoundsGeometry(this, inferenceStepBlocks[index].getBounds());
		return geom.getN();
	}

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
