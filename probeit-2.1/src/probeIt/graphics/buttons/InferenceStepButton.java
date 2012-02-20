package probeIt.graphics.buttons;

import java.awt.Font;

import diva.canvas.CompositeFigure;
import diva.canvas.interactor.BoundsGeometry;
import diva.canvas.toolbox.BasicRectangle;
import diva.canvas.toolbox.LabelFigure;

import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.graphics.interactor.InferenceStepSelectionInteractor;
import pml.PMLNode;
import pml.util.pml.PMLSourceRetrieval;

public class InferenceStepButton extends CompositeFigure
{
	
	// The geometry object
	//private BoundsGeometry _nodeSetBlockGeom;

	int x;
	int y;

	PMLNode _node;

	BasicRectangle _block;
	
	/** Is true if getting next IS, false if getting previous */
	boolean next;
	
	private InferenceStepSelectionInteractor interactor;

	boolean justificationEnabled;
	
	public static int HEIGHT;
	public static int WIDTH;
	
	BasicRectangle button;
	
	public InferenceStepButton(int x, int y, int width, int height, PMLNode nodeSet, boolean nxt)
	{
		_node = nodeSet;
		next = nxt;
		
		this.x = x;
		this.y = y;
		
		HEIGHT = height;
		WIDTH = width;
		
		addButton();
		addLabel();
	}
	
	private void addButton()
	{
		if(next)//next
		{
			if((_node.getNumberOfInferenceSteps()-1-_node.getIndex()) > 0)//if hasNextInferenceStep
				button = new BasicRectangle(x, y, WIDTH, HEIGHT, java.awt.Color.white);
			else
				button = new BasicRectangle(x, y, WIDTH, HEIGHT, java.awt.Color.gray);
		}
		
		else//previous
		{
			if((_node.getIndex()) > 0)//if hasPrevInferenceStep
				button = new BasicRectangle(x, y, WIDTH, HEIGHT, java.awt.Color.white);
			else
				button = new BasicRectangle(x, y, WIDTH, HEIGHT, java.awt.Color.gray);
		}
		
	  	if (interactor == null)
	  		interactor = new InferenceStepSelectionInteractor();//_node, next);

	  	setInteractor(interactor);
		
		add(button);
	}
	
	private void addLabel()
	{
		PMLSourceRetrieval.resetCounter();
		String str = "";
		int fontsize = 20;
		
        if(next)
        {
        	str = ">";
        }
        else //previous
        {
        	str = "<";
        	
        	LabelFigure indexlb = new LabelFigure(""+(_node.getIndex()+1)+"/"+_node.getNumberOfInferenceSteps());
        	indexlb.setAnchor(8);
        	indexlb.setFont(new Font("Times", Font.PLAIN, fontsize-8));
        	indexlb.translateTo(x-3, y + (HEIGHT/2));
        	add(indexlb);
        }
		
		LabelFigure label = new LabelFigure(str);
		label.setAnchor(8);//anchor northwest.
		label.setFont(new Font("Times", Font.PLAIN, fontsize));
		
		if(next)
			label.translateTo(x, y+(HEIGHT/2) - (fontsize/2));//y+(height/2) - (fontsize/2);
		else
			label.translateTo(x, y);
			
		add(label);
	}
	
	public boolean getNext()
	{
		return next;
	}
	
	public PMLNode getNode()
	{
		return _node;
	}
}