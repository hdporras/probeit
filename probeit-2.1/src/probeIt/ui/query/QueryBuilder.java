package probeIt.ui.query;

import probeIt.ProbeIt;
import pml.*;
import pml.loading.Loader;
import probeIt.graphics.canvas.QueryCanvas;
import probeIt.graphics.CanvasDrawer_Query;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;
import probeIt.ui.workers.SwingWorker;

public class QueryBuilder extends SwingWorker
{
	boolean justification;
	QueryCanvas theCanvas;
	AnswersPanel swingCanvas;
	int pageNumber;
	boolean swing;
	int index;
	public QueryBuilder(QueryCanvas canvas, int page)
	{
		super();
		pageNumber = page;
		theCanvas = canvas;
		swing = false;
	}
	
	public QueryBuilder(AnswersPanel canvas, int page)
	{
		super();
		pageNumber = page;
		swingCanvas = canvas;
		swing = true;
		index = page * 6;
	}

	public Object construct()
	{
		WindowApplication.getInstance().enableProgressBar("Building query view ");
		
		if(!swing)
		{
			CanvasDrawer_Query drawer = new CanvasDrawer_Query(theCanvas, ViewsModel.getInstance().getQuery(), pageNumber);
			drawer.draw();
		}
		else
		{
			int counter = 0;
			swingCanvas.clear();
			
			if (ViewsModel.getInstance().getQuery() != null && 
				ViewsModel.getInstance().getQuery().getAnswerURIs().length > 0 && 
				ViewsModel.getInstance().getQuery().getAnswerURIs().length > index)
			{
				WindowApplication.getInstance().enableProgressBar("Building query view ");
				for(int i = index; i < index + 6 && i < ViewsModel.getInstance().getQuery().getAnswerURIs().length; i ++)
				{
					System.out.println("[Query loading] " + i);
					Loader answerLoader = new Loader(ProbeIt.getInstance().isRemote());
					PMLNode nodeSet = answerLoader.loadNode(ViewsModel.getInstance().getQuery().getAnswerURIs()[i],null);
				
					if(nodeSet.getID().equalsIgnoreCase(Loader.DEFAULT_ID))
						nodeSet.setID(index + counter + "");
					
					AnswerPanel answer = new AnswerPanel(nodeSet, 150, 10);
					swingCanvas.addAnswer(answer, counter);
					counter ++;
					ProbeIt.getInstance().getWindow().updateView();
				}

				swingCanvas.done();
				//WindowApplication.getInstance().disableProgressBar();
			}
			else if (ViewsModel.getInstance().getQuery() == null || 
				    (!(ViewsModel.getInstance().getQuery().getAnswerURIs().length > 0)))
			{
				swingCanvas.clear();
				swingCanvas.addNoAnswersPanel();
			}
		}
		return null;
	}

	public void finished()
	{
		System.out.println("Query Builder Finished");
		WindowApplication.getInstance().disableProgressBar();
	}
}

