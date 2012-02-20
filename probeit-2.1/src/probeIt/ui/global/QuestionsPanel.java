package probeIt.ui.global;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuestionsPanel extends JPanel implements ActionListener
{
	JEditorPane textPane;
	
	Question[] questions;
	
	JPanel resultPageNumbers;
	JButton decrementPage;
	JButton incrementPage;
	
	JLabel indexText;

	int currentPage;
	
	public QuestionsPanel(Question[] ques)
	{
		super();
		
		questions = ques;
		textPane = new JEditorPane();
		textPane.setFont(new java.awt.Font("Arial", Font.PLAIN, 24));
		setLayout(new BorderLayout());
		
		add(textPane, BorderLayout.CENTER);
		
		buildResultPageNumbers();
		add(resultPageNumbers, BorderLayout.SOUTH);
		
		updatePageNumberPanel();
	}
	
	private void updatePageNumberPanel()
	{	
		if(questions == null)
		{
			textPane.setText("No questions can be generated from this PML...");
			return ;
		}
		if(currentPage == 0 && currentPage == questions.length-1)
		{
			decrementPage.setEnabled(false);
			incrementPage.setEnabled(false);
		}
		else if(currentPage == 0)
			decrementPage.setEnabled(false);
		else if(currentPage == questions.length)
			incrementPage.setEnabled(false);
		else
		{
			decrementPage.setEnabled(true);
			incrementPage.setEnabled(true);
		}
		
		indexText.setText(currentPage + 1 + " of " + questions.length + " questions. ");
		
		textPane.setText("\n\n\n\n - " + questions[currentPage].getQuestion());
	}
	
	private void buildResultPageNumbers()
	{
		resultPageNumbers = new JPanel();
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		
		resultPageNumbers.setLayout(new BorderLayout());
		
		decrementPage = new JButton("<-");
		decrementPage.addActionListener(this);
		decrementPage.setActionCommand("<");
		
		incrementPage = new JButton("->");
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
		if(currentPage != questions.length)
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
	}
}
