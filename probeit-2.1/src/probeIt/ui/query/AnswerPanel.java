package probeIt.ui.query;
import javax.swing.JPanel;import javax.swing.BorderFactory; 
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;
import javax.swing.JButton;

import probeIt.ProbeIt;
import probeIt.graphics.figure.NodeSetGraphic;
import probeIt.util.LineFormatter;
import probeIt.viewerFramework.DAGContentFactory;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.PMLNode;
import pml.util.pml.PMLSourceRetrieval;
import pml.loading.Loader;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
public class AnswerPanel extends JPanel
{
	JPanel conclusionPanel;
	JPanel informationPanel;
	JLabel id;
	JPanel viewer;
	JTextArea sources;
	PMLNode answerNode;
	int imageSize;
	int numLines;
	
	public AnswerPanel(PMLNode nodeset, int imgSize, int numOfLines)
	{
		answerNode = nodeset;
		imageSize = imgSize;
		numLines = numOfLines;
		
		buildConclusionPanel();
		buildInformationPanel();
		buildIDLabel();
		buildButtonPanel();
		
		setLayout(new GridBagLayout());
		setBorder(javax.swing.BorderFactory.createEmptyBorder());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.ipadx = 10;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(id, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		add(conclusionPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		add(informationPanel, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0;
		c.gridx = 2;
		c.gridy = 0;
		add(viewer, c);
		
		
		
		if(ProbeIt.getInstance().getEmbeddedProbeIt().isShowingSources())
		{
			buildSourcesPanel();
			
			c.fill = GridBagConstraints.BELOW_BASELINE;
			c.weightx = 0;
			c.gridx = 0;
			c.gridy = 2;
			add(sources, c);
			
			//System.out.println("showing sources mode");
			
		}
		setBackground(java.awt.Color.WHITE);
		
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
	
	private void buildSourcesPanel()
	{
		sources = new JTextArea();
		probeIt.action.PopupFullSourcesAction action = new probeIt.action.PopupFullSourcesAction();
		sources.addMouseListener(action);
		
		PMLNode justification;
		Loader loader = new Loader(ProbeIt.getInstance().isRemote());
		justification = loader.loadJustification(answerNode.getURI(), answerNode.getID());
		action.setNodeset(justification);
		sources.setText(PMLSourceRetrieval.getFirstSources(justification));
	}
	
	private void buildButtonPanel()
	{
		viewer = new ButtonPanel(answerNode);
	}
	
	private void buildIDLabel()
	{
		id = new JLabel(answerNode.getID());
	}
	
	private void buildInformationPanel()
	{
		informationPanel = new JPanel();
		informationPanel.setLayout(new BorderLayout());
		JTextArea text = new JTextArea(getMetadata());
		informationPanel.add(text, BorderLayout.CENTER);
	}
	
	public PMLNode getNode()
	{return answerNode;}
	
	private void buildConclusionPanel()
	{
		conclusionPanel = new JPanel();
		conclusionPanel.setBackground(Color.WHITE);
		DAGContentFactory selector = new DAGContentFactory();
		ProbeitImage dagImage = selector.getImage(answerNode.getConclusion(), imageSize, imageSize - 50);
		
		if(dagImage != null)
		{
			ImageIcon graphicIcon = new ImageIcon(dagImage.getImage());
			JLabel graphic = new JLabel(graphicIcon);
			conclusionPanel.add(graphic);
		}
		else
		{
			String text = selector.getText(answerNode.getConclusion());
			JTextArea textLabel = new JTextArea(LineFormatter.formatText(text, 30, 4));
			textLabel.setLineWrap(true);
			textLabel.setEditable(false);
			conclusionPanel.setBorder(BorderFactory.createLineBorder(Color.black));	
			conclusionPanel.add(textLabel);
		}
	}
	
	private String getMetadata()
	{
		String dateText = this.getDateText();
		String urlText = this.getURLText();
		String fromEngineText = this.getFromEngineText();
		
		String metadata = "";
		if(dateText != null)
			metadata += "Date: " + dateText + "\n";
		if(urlText != null)
			metadata += "Hosted at: " + urlText + "\n";
		if(fromEngineText != null)
			metadata += "Created by: " + fromEngineText;
		
		return metadata;
	}
	
	private String getDateText()
	{
		String dateText = null;
		if(answerNode.getDate() != null)
			dateText = LineFormatter.formatSingleLineText(answerNode.getDate(), 100);
		return dateText;
	}
	
	private String getURLText()
	{
		String urlText = null;
		
		try
		{
			if(answerNode.getConclusion().getHasURL().equalsIgnoreCase("NO URL OF CONCLUSION ENCODED IN PML"))
				urlText = LineFormatter.formatSingleLineText(new URL(answerNode.getURI()).getHost(),30) + " [embedded]";
			else
				urlText = LineFormatter.formatSingleLineText(new URL(answerNode.getConclusion().getHasURL()).getHost(),40);
			
			return urlText;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	private String getFromEngineText()
	{
		String engineText = null;
		if(answerNode.getCurrentlySelectedInferenceStep().getIEName() != null)
			engineText =  LineFormatter.formatText(answerNode.getCurrentlySelectedInferenceStep().getIEName(), 100, 1);
		return engineText;
	}	
}
