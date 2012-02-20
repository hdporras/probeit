package probeIt.graphics.figure;

import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import probeIt.viewerFramework.*;
import diva.canvas.toolbox.BasicRectangle;
import pml.PMLQuery;
import probeIt.ui.WindowApplication;
import probeIt.viewerFramework.ViewerFactory;
import diva.canvas.CompositeFigure;
import diva.canvas.Figure;
import diva.canvas.Site;
import diva.canvas.interactor.BoundsGeometry;
import diva.canvas.toolbox.BasicEllipse;
import diva.canvas.toolbox.LabelFigure;
import diva.canvas.toolbox.LabelWrapper;

/**
 * The rendering of probeIt.model.Query. The query is represented by an ellipse
 * labeled with the query content. The query model is provided by the user
 * interface instance.
 * 
 * @author paulo
 * 
 */
public class QueryGraphic extends CompositeFigure
{

	// The geometry object
	private BoundsGeometry _geometry;
	private int _x;
	private int _y;
	private PMLQuery _theQuery;
	private BasicEllipse _queryContent;

	/**
	 * Create a new instance of this figure.
	 */
	public QueryGraphic(PMLQuery aQuery, int x, int y)
	{
		_x = x;
		_y = y;
		_theQuery = aQuery;
		// this.add(buildQueryGraphic());
		// this.add(buildQueryBorder());
		setImage();
		//addSeparator();
		_geometry = new BoundsGeometry(this, getBounds());
	}

	/**
	 * The label of the query. The label often contains the content of the PML
	 * query, which is a textual representation of the query.
	 * 
	 * @return Figure
	 */
	/*
	 * private Figure buildQueryGraphic() { java.awt.Color color = new
	 * java.awt.Color(100, 200, 30); _queryContent = new BasicEllipse(_x, _y +
	 * 25, 500, 60, color); return _queryContent; }
	 * 
	 * private Figure buildQueryBorder() { _queryContent = new BasicEllipse(_x,
	 * _y + 25, 500, 60); return _queryContent; }
	 */

	public void repaint()
	{
		setImage();
		super.repaint();
	}

	public void setImage()
	{
		LabelFigure lf;

		if (_theQuery != null)
		{
			DAGContentFactory selector = new DAGContentFactory();
			String label = selector.getText(_theQuery);
			lf = new LabelFigure(formatMultiLineText(label));
		} 
		else
			lf = new LabelFigure("Query");

		lf.setFont(new Font("Courier", Font.BOLD, 14));
		lf.translate(_x, _y);
		add(lf);
	}
	
	
	public String getLabel()
	{
		DAGContentFactory selector = new DAGContentFactory();
		String label = selector.getText(_theQuery);
		
		return label;
	}
	
	public LabelFigure getLabelFig()
	{
		LabelFigure lf;
		
		if (_theQuery != null)
		{
			DAGContentFactory selector = new DAGContentFactory();
			String label = selector.getText(_theQuery);
			lf = new LabelFigure(formatMultiLineText(label));
		} 
		else
			lf = new LabelFigure("Query");

		lf.setFont(new Font("Courier", Font.BOLD, 14));
		lf.translate(_x, _y);
		
		return lf;
	}

	public int getX()
	{
		return _x;
	}

	public int getY()
	{
		return _y;
	}

	/**
	 * Get the north site.
	 */
	public Site getNorth()
	{
		return _geometry.getN();
	}

	/**
	 * Get the south site.
	 */
	public Site getSouth()
	{
		return _geometry.getS();
	}

	/**
	 * Get the east site.
	 */
	public Site getEast()
	{
		return _geometry.getE();
	}

	/**
	 * Get the west site.
	 */
	public Site getWest()
	{
		return _geometry.getW();
	}

	/**
	 * Update the geometry
	 */
	public void transform(AffineTransform at)
	{
		super.transform(at);
		_geometry.setShape(getShape());
	}

	/**
	 * Update the geometry
	 */
	public void translate(double x, double y)
	{
		super.translate(x, y);
		_geometry.translate(x, y);
	}

	private String formatMultiLineText(String kif)
	{
		String[] lines = kif.split("\n");
		String cleanedKif = "";
		for (int i = 0; i < lines.length && i < 4; i++)
		{
			String line = lines[i];
			String fixedLine = "";
			for (int j = 0; j < line.length(); j++)
			{
				char character = line.charAt(j);

				if (j >= 100)
				{
					fixedLine = fixedLine + "...";
					break;
				}
				fixedLine = fixedLine + character;
			}
			cleanedKif = cleanedKif + fixedLine + "\n";
		}
		if (lines.length >= 5 && cleanedKif.endsWith("...\n"))
			cleanedKif = cleanedKif.substring(0, cleanedKif.length() - 7)
					+ " more...";
		else if (lines.length >= 5)
			cleanedKif = cleanedKif.substring(0, cleanedKif.length() - 1)
					+ " more...";

		return cleanedKif;
	}
}
