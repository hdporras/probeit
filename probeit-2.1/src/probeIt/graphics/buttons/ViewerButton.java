package probeIt.graphics.buttons;

import java.awt.Font;

import probeIt.graphics.interactor.ViewerButtonSelectionInteractor;
import pml.PMLNode;
import diva.canvas.CompositeFigure;
import diva.canvas.toolbox.BasicRectangle;
import diva.canvas.toolbox.LabelFigure;

public class ViewerButton extends CompositeFigure
{
	static ViewerButtonSelectionInteractor interactor;
	PMLNode node;
	int x, y;

	public static final int HEIGHT = 20;
	public static final int WIDTH = 50;
	
	BasicRectangle button;
	public ViewerButton(int inX, int inY, PMLNode aNode)
	{
		if(interactor == null)
			interactor = new ViewerButtonSelectionInteractor();
		
		setInteractor(interactor);
		node = aNode;
		x = inX;
		y = inY;
		addButton();
		addLabel();
	}

	private void addButton()
	{
		button = new BasicRectangle(x, y, WIDTH, HEIGHT);
		add(button);
	}

	public PMLNode getNode()
	{
		return node;
	}

	private void addLabel()
	{
		LabelFigure label = new LabelFigure("Viewer");
		label.setFont(new Font("Times", Font.PLAIN, 10));
		
		label.translateTo(button.getOrigin().getX(), button.getOrigin().getY());
		add(label);
	}
}
