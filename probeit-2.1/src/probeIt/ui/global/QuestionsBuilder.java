package probeIt.ui.global;

import pml.PMLNode;
import probeIt.graphics.CanvasDrawer_DAGJustification;
import probeIt.graphics.CanvasDrawer_TreeJustification;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.ProbeIt;
import probeIt.ui.WindowApplication;
import probeIt.ui.workers.SwingWorker;
import probeIt.ui.model.ViewsModel;
import javax.swing.JEditorPane;
public class QuestionsBuilder extends SwingWorker
{
	PMLNode nodeset;
	QuestionsView view;
	
	public QuestionsBuilder(PMLNode node, QuestionsView questView)
	{
		super();
		nodeset = node;
		view = questView;
	}

	public Object construct()
	{
		WindowApplication.getInstance().enableProgressBar("Building Questions");
		
		//add call to question generator here
		QuestionGenerator gen = new QuestionGenerator(ViewsModel.getInstance().getJustification());

		view.setQuestions(gen.generateQuestions());
		
		return null;
	}


	public void finished()
	{
		WindowApplication.getInstance().disableProgressBar();
	}
}