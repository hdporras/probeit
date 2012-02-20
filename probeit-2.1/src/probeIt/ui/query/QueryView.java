package probeIt.ui.query;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import probeIt.graphics.canvas.QueryCanvas;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.*;
import probeIt.ProbeIt;
import pml.*;
import probeIt.ui.ViewsManager;
import probeIt.ui.Toolbar;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;
import probeIt.viewerFramework.DAGContentFactory;
import javax.swing.BorderFactory;

import java.awt.Dimension;

/**
 * 
 * The query tab in the Probe-It main user interface. This tab is enabled if the
 * query in the main user interface is not null.
 * 
 * @author
 * 
 */
public class QueryView extends JPanel implements ActionListener
{
	JPanel resultPageNumbers;
	JButton decrementPage;
	JButton incrementPage;
	//PMLQuery query;
	JLabel indexText;
	//QueryCanvas canvas;
	AnswersPanel swingCanvas;
	int currentPage;

	public QueryView()
	{
		super();
				
		System.out.println("[QueryView()]");

		JScrollPane scroll;
		setLayout(new BorderLayout());

		currentPage = 0;
		
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		Toolbar toolbar = new Toolbar(Toolbar.CanvasType.queryView);

		//query = ProbeIt.getInstance().getWindow().getViewConfiguration().getQuery();

		/*
		canvas = new QueryCanvas();
		canvas.setJustification(ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_J());
		canvas.setShowSources(ProbeIt.getInstance().getEmbeddedProbeIt().isShowingSources());
		canvas.setTrust(ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_T());*/
		
		swingCanvas = new AnswersPanel();
		swingCanvas.setBorder(BorderFactory.createTitledBorder("Answers"));
		
		//scroll = new JScrollPane(canvas.getJCanvas());
		//scroll = new JScrollPane(swingCanvas);
		//scroll.setBorder(BorderFactory.createTitledBorder("Answers"));
		
		DAGContentFactory selector = new DAGContentFactory();
		String label = selector.getText(ViewsModel.getInstance().getQuery());
		
		JTextArea qtext = new JTextArea();
		qtext.setLineWrap(true);
		qtext.setWrapStyleWord(true);
		qtext.setEditable(false);
		qtext.setText(label);
		qtext.setBackground(Color.white);
		qtext.setFont(new java.awt.Font("Times", Font.BOLD, 16));
		qtext.setBorder(BorderFactory.createTitledBorder("Question"));

		toolbar.setup();

		center.add(qtext, BorderLayout.NORTH);
		if(ViewsManager.getInstance().isEnabledFeature_A())
			qtext.setVisible(false);
		else
			qtext.setVisible(true);
		
		//center.add(scroll, BorderLayout.CENTER);
		center.add(swingCanvas, BorderLayout.CENTER);
		add(center, BorderLayout.CENTER);
		
		this.buildResultPageNumbers();
		
		add(this.resultPageNumbers, BorderLayout.SOUTH);
		
		
		add(toolbar, BorderLayout.NORTH);
		
		//QueryBuilder builder = new QueryBuilder(query, canvas ,currentPage);
		System.out.println("[calling QueryBuilder...]");
		QueryBuilder builder = new QueryBuilder(swingCanvas ,currentPage);
		builder.start();
		
		
	}
	
	private void updatePageNumberPanel()
	{	
		if (!ViewsModel.getInstance().hasQuery())
			return;
		
		if(currentPage == 0 && currentPage == ViewsModel.getInstance().getQuery().getAnswerURIs().length/6)
		{
			decrementPage.setEnabled(false);
			incrementPage.setEnabled(false);
		}
		else if(currentPage == 0 && currentPage != ViewsModel.getInstance().getQuery().getAnswerURIs().length/6)
		{
			decrementPage.setEnabled(false);
			incrementPage.setEnabled(true);
		}
		
		else if(currentPage == ViewsModel.getInstance().getQuery().getAnswerURIs().length/6)
		{
			decrementPage.setEnabled(true);
			incrementPage.setEnabled(false);
		}
		else
		{
			decrementPage.setEnabled(true);
			incrementPage.setEnabled(true);
		}
		
		indexText.setText(currentPage + 1 + " of " + 
				(ViewsModel.getInstance().getQuery().getAnswerURIs().length/6 + 1) + " pages. " + 
				ViewsModel.getInstance().getQuery().getAnswerURIs().length + " results.");
	}
	
	private void buildResultPageNumbers()
	{
		resultPageNumbers = new JPanel();
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		
		resultPageNumbers.setLayout(new BorderLayout());
		
	    Icon decrementIcon = new ImageIcon(getClass().getResource("/probeIt/image/left-arrow.PNG"));
	    Icon incrementIcon = new ImageIcon(getClass().getResource("/probeIt/image/right-arrow.PNG"));
	    

		decrementPage = new JButton(decrementIcon);
		decrementPage.setBorder(null);
		decrementPage.setBackground(null);
		decrementPage.addActionListener(this);
		decrementPage.setActionCommand("<");
		
		incrementPage = new JButton(incrementIcon);
		incrementPage.setBorder(null);
		incrementPage.setBackground(null);
		incrementPage.addActionListener(this);
		incrementPage.setActionCommand(">");
		
		indexText = new JLabel();
		
		updatePageNumberPanel();
		
		controlPanel.add(decrementPage, BorderLayout.WEST);
		controlPanel.add(indexText, BorderLayout.CENTER);
		controlPanel.add(incrementPage, BorderLayout.EAST);
		
		resultPageNumbers.add(controlPanel, BorderLayout.WEST);
	}
	
	private void incrementCounter()
	{
		if(currentPage != ViewsModel.getInstance().getQuery().getAnswerURIs().length/6)
			currentPage ++;
	}
	
	private void decrementCounter()
	{
		if(currentPage != 0)
			currentPage --;
	}
	
	public void actionPerformed(ActionEvent event)
	{
		if(event.getActionCommand().equalsIgnoreCase("<"))
		{
			decrementCounter();
		}
		else
			incrementCounter();
		
		updatePageNumberPanel();

		WindowApplication.getInstance().enableProgressBar("Building query view ");
		QueryBuilder builder = new QueryBuilder(swingCanvas ,currentPage);
		builder.start();
	}
}
