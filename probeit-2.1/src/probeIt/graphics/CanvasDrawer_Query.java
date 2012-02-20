package probeIt.graphics;

import probeIt.ProbeIt;
import probeIt.graphics.canvas.QueryCanvas;
import pml.PMLNode;
import pml.PMLQuery;
import pml.loading.Loader;

/**
 * The canvas holding Probe-It's graphical representation of a PML query and
 * associated PML node sets, each nodo set corresponding to a query answer.
 * 
 * @author paulo
 * 
 */

public class CanvasDrawer_Query
{	
	private PMLQuery query;
	QueryCanvas theCanvas;
	int index;
	
	public CanvasDrawer_Query(QueryCanvas canvas, PMLQuery theQuery, int pageNumber)
	{
		index = pageNumber * 6;
		query = theQuery;
		theCanvas = canvas;
		theCanvas.clear();
	}

	/**
	 * The method retrieves the query from the main user interface, calls
	 * drawQuery, and drawNode once for each node set associated with the query.
	 * 
	 * @return JCanvas with a graphical representation of the query and its
	 *         answers
	 */
	public void draw()
	{
		if (query.getAnswerURIs().length > 0 && query.getAnswerURIs().length > index)
		{
			for(int i = index; i < index + 6 && i < query.getAnswerURIs().length; i ++)
			{
				theCanvas.drawNode(query.getAnswerURIs()[i]);
			}
		}
	}
}
