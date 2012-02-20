package probeIt.ui.query;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.util.ArrayList;

//import probeIt.ui.PMLLoader;
import probeIt.ui.ViewsManager;
import probeIt.ui.avatar.AvatarView;
import probeIt.ui.model.ViewsModel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.JTextArea;
import java.awt.Font;
public class AnswersPanel extends JPanel implements MouseListener
{
	int numAnswers;
	ArrayList<AnswerPanel> answerPanels;
	GridBagConstraints c;
	public AnswersPanel()
	{
		//System.out.println("[AnswersPanel()]");
		answerPanels = new ArrayList();
		c = new GridBagConstraints();
		setLayout(new GridLayout(2,3));
		setBackground(java.awt.Color.WHITE);
		numAnswers = 0;
	}
	
	public void addNoAnswersPanel()
	{
		setLayout(new BorderLayout());
		JTextArea message = new JTextArea("Query yielded 0 results...");
		message.setFont(new Font("Times", Font.BOLD, 20));
		add(message, BorderLayout.CENTER);
	}
	
	public void addAnswer(AnswerPanel answer, int id)
	{
		/*
		int pos = id % 6;
		System.out.println("Position: " + pos);
		switch(pos)
		{
			case 0:
				c.gridx = 0;
				c.gridy = 0;
				break;
			case 1:
				c.gridx = 1;
				c.gridy = 0;
				break;
			case 2:
				c.gridx = 2;
				c.gridy = 0;
				break;
			case 3:
				c.gridx = 0;
				c.gridy = 1;
				break;
			case 4:
				c.gridx = 1;
				c.gridy = 1;
				break;
			case 5:
				c.gridx = 2;
				c.gridy = 1;
				break;
		}
		
		c.fill = GridBagConstraints.HORIZONTAL;
		*/
		answer.addMouseListener(this);
		if(numAnswers < 6)
		{
			numAnswers++;
			answerPanels.add(answer);
			super.add(answer);
		}
	}
	
	public void removeSelections()
	{
		for(int i = 0; i < answerPanels.size(); i ++)
		{
			answerPanels.get(i).setBorder(javax.swing.BorderFactory.createEmptyBorder());
		}
	}
	
	public void done()
	{
		System.out.println("[AnswersPanel.done()]");
		for(int i = numAnswers; i < 6; i ++)
		{
			javax.swing.JPanel panel = new javax.swing.JPanel();
			panel.setBackground(java.awt.Color.WHITE);
			super.add(panel);
		}
	}
	
	public void clear()
	{
		removeAll();
		setLayout(new GridLayout(2,3));
		numAnswers = 0;
	}
	
	public void mouseClicked(MouseEvent event)
	{
		AnswerPanel anAnswer = (AnswerPanel)event.getSource();
		probeIt.ProbeIt.getInstance().getLogger().setGlobalViewBool(true);
		
		removeSelections();
		anAnswer.setBorder(javax.swing.BorderFactory.createLineBorder(Color.YELLOW));
		
		if (ViewsManager.getInstance().isEnabledFeature_A()) {
			String selectedURI = anAnswer.getNode().getURI();
			AvatarView.getInstance().setSelection(selectedURI);
		} else {
			ViewsModel.getInstance().setAnswer(anAnswer.getNode().getURI());
			//PMLLoader _loader = new PMLLoader(anAnswer.getNode().getURI(), "temp id for now");
			//_loader.start();			
		}
	}
	
	public void mouseReleased(MouseEvent event)
	{
	}
	
	public void mouseExited(MouseEvent event)
	{
	}
	
	public void mousePressed(MouseEvent event)
	{
	}
	
	public void mouseEntered(MouseEvent event)
	{
	}
}
