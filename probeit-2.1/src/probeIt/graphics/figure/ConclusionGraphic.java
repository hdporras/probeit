package probeIt.graphics.figure;

import diva.canvas.toolbox.ImageFigure;
import diva.canvas.Figure;
import diva.canvas.toolbox.BasicRectangle;
import probeIt.viewerFramework.*;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.impl.serializable.PMLConclusion;

public class ConclusionGraphic extends ImageFigure
{
	PMLConclusion _theConclusion;
	ProbeitImage dagImage;
	double _x, _y;

	public ConclusionGraphic(PMLConclusion theConclusion, double x, double y)
	{
		_theConclusion = theConclusion;
		setDAGImage();
		setGraphic(x, y);
	}
	public boolean isEmpty()
	{
		if (dagImage == null)
			return true;
		return false;
	}

	public Figure getGraphicBorder()
	{
		if (dagImage != null)
			return new BasicRectangle(_x - 1, _y - 1, dagImage.getWidth() + 2,
					dagImage.getHeight() + 2);
		return null;
	}

	private void setDAGImage()
	{
		DAGContentFactory selector = new DAGContentFactory();
		dagImage = selector.getImage(_theConclusion, (NodeSetGraphic.WIDTH - 10), (NodeSetGraphic.HEIGHT - 50));	
	}

	private void setGraphic(double x, double y)
	{
		if (dagImage != null)
		{
			setImage(dagImage.getImage());
			adjustXY(dagImage, x, y);
			translate(_x, _y);
		}
	}

	private void adjustXY(ProbeitImage image, double x, double y)
	{
		int xMid = image.getWidth() / 2;
		int yMid = image.getHeight() / 2;
		_x = x - xMid;
		_y = y - yMid;
	}
}
