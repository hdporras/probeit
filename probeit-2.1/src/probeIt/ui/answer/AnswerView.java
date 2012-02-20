package probeIt.ui.answer;
import javax.swing.JPanel;
import probeIt.viewerFramework.*;
import java.awt.BorderLayout;
import probeIt.ProbeIt;
import probeIt.ui.Toolbar;
import probeIt.ui.model.ViewsModel;
import pml.*;

/**
 * 
 * The query tab in the Probe-It main user interface. This tab is enabled if the
 * query in the main user interface is not null.
 * 
 * @author
 * 
 */
public class AnswerView extends JPanel
{
	public AnswerView()
	{
		super();
		
		ViewerFactory fact = new ViewerFactory();
		
		setLayout(new BorderLayout());
		
		Toolbar tb = new Toolbar(Toolbar.CanvasType.answerView);
		tb.setup();
		add(tb, BorderLayout.NORTH);
		add(fact.selectPrimaryViewer(ViewsModel.getInstance().getAnswer().getConclusion()), BorderLayout.CENTER);
	}
}