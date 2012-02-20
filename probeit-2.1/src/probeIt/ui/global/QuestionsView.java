package probeIt.ui.global;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import probeIt.ProbeIt;
import pml.PMLNode;
import pml.PMLQuery;
import probeIt.ui.Toolbar;
import probeIt.ui.query.AnswersPanel;
import probeIt.ui.query.QueryBuilder;
import probeIt.ui.model.ViewsModel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionsView extends JPanel
{
	QuestionsBuilder builder;
	static QuestionsView instance;
	
	public QuestionsView()
	{
		super();
		
		// log that user has accessed this global view
		probeIt.ProbeIt.getInstance().getLogger().setGlobalViewBool(true);

		setLayout(new BorderLayout());
		
		Toolbar tb = JustificationView.getToolbar();
		
		if (tb == null)
		{
			tb = new Toolbar(Toolbar.CanvasType.justificationView);
			tb.setup();
		}
		
		add(tb, BorderLayout.NORTH);
		
		builder = new QuestionsBuilder(ViewsModel.getInstance().getJustification(), this);
		builder.start();

		instance = this;
	
	}
	
	public void setQuestions(Question[] questions)
	{
		QuestionsPanel panel = new QuestionsPanel(questions);
		add(panel, BorderLayout.CENTER);
	}
}
