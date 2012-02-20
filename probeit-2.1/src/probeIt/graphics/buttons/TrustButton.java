package probeIt.graphics.buttons;

import java.awt.geom.AffineTransform;
import java.awt.Font;

import probeIt.graphics.interactor.TrustButtonSelectionInteractor;
import pml.PMLNode;
import diva.canvas.CompositeFigure;
import java.awt.geom.Point2D;
import diva.canvas.Site;
import diva.canvas.interactor.BoundsGeometry;
import diva.canvas.toolbox.BasicRectangle;
import diva.canvas.toolbox.LabelWrapper;
import diva.canvas.toolbox.LabelFigure;

public class TrustButton extends CompositeFigure
{
	static TrustButtonSelectionInteractor interactor;
	PMLNode node;
	int x, y;

	public static final int HEIGHT = 20;
	public static final int WIDTH = 50;
	
	BasicRectangle button;
	public TrustButton(int inX, int inY, PMLNode aNode)
	{
		node = aNode;
		x = inX;
		y = inY;
		addButton();
		addLabel();

		if (interactor == null)
			interactor = new TrustButtonSelectionInteractor();

		setInteractor(interactor);
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
		LabelFigure label = new LabelFigure("Trust Map");
		label.setFont(new Font("Times", Font.PLAIN, 10));
		
		label.translateTo(button.getOrigin().getX(), button.getOrigin().getY());
		add(label);
	}
}
