package probeIt.graphics.buttons;

import java.awt.Font;

import probeIt.graphics.interactor.ShowSourcesInPopupInteractor;
import pml.PMLNode;
import pml.util.pml.PMLSourceRetrieval;
import diva.canvas.CompositeFigure;
import diva.canvas.toolbox.BasicRectangle;
import diva.canvas.toolbox.LabelFigure;


public class SourceButton extends CompositeFigure
{
	ShowSourcesInPopupInteractor interactor;
	PMLNode node;
	int x, y;

	public static final int HEIGHT = 100;
	public static final int WIDTH = 230;
	
	BasicRectangle button;
	
	public SourceButton(int inX, int inY, PMLNode aNode)
	{
		node = aNode;
		x = inX;
		y = inY;
		
		addLabel();

		if (interactor == null)
			interactor = new ShowSourcesInPopupInteractor(aNode);

		setInteractor(interactor);
	}

	private void addButton()
	{
		button = new BasicRectangle(x, y, WIDTH, HEIGHT);
		add(button);
	}

	private void addLabel()
	{
		PMLSourceRetrieval.resetCounter();
		String sources = "Sources:\n" + PMLSourceRetrieval.getFirstSources(node);
		
		if(PMLSourceRetrieval.moreSources())//if there are more sources that aren't shown on the canvas display "..."
			sources += "... click for more sources ...";
		
		if(sources.trim().equalsIgnoreCase("Sources:"))
			sources = "No PML sources found";
		
		LabelFigure label = new LabelFigure(sources);
		label.setAnchor(8);//anchor northwest.
		label.setFont(new Font("Times", Font.PLAIN, 10));
		
		label.translateTo(x, y);
		add(label);
	}
}