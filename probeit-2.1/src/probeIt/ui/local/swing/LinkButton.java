package probeIt.ui.local.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import probeIt.ProbeIt;
import probeIt.graphics.figure.NodeSetGraphic;
import pml.*;
import pml.impl.nonSerializable.PMLNodeImpl;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.model.ViewsModel;
import probeIt.util.LineFormatter;
import probeIt.viewerFramework.DAGContentFactory;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.loading.Loader;

public class LinkButton extends JPanel//JButton
{
	String link;

	public LinkButton(String text, String uri)
	{
		super();
		link = uri;
		
		DAGContentFactory selector = new DAGContentFactory();
	
		/* use factory istead
		Builder b = new Builder();
		//PMLNode n = new PMLNodeImpl(uri, null, false);
		PMLNode node = b.loadNode(uri, null);*/
		
		Loader factory = new Loader(ProbeIt.getInstance().isRemote());
		PMLNode node = factory.loadNode(uri, null);
	
		ProbeitImage image = selector.getImage(node.getConclusion(), 150, 100);//(NodeSetGraphic.HEIGHT * 5/7));
		
		if(image != null)
		{
			ImageIcon imageIcon = new ImageIcon(image.getImage());
			JLabel icon = new JLabel(imageIcon);

			icon.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent evt) 
				{
					if(evt.getButton() == MouseEvent.BUTTON1)
					{
						if(link != null)
						{
							//ViewsModel.getInstance().setAnswer(link);
							((ProbeItView)ViewsManager.getInstance().getViewPane()).buildProvenanceView(link);
							//update NodeSelectionInteractor._lastPointedFigure to the linked NodeSet. Will have to check URI's in tree to find correct one, unless another method?
						}
						else
							System.out.println("PMLNode (link) is NULL.");
					}
				}
			});
			
			this.add(icon);
		}
		else
		{
			//setHorizontalAlignment(JButton.LEADING);
			
			JTextArea content = new JTextArea();
		
			 try
			 {
				 if(node.getConclusion().isByReference())
					 text = probeIt.util.GetURLContents.downloadText(node.getConclusion().getHasURL());
				 else
					 text = node.getConclusion().getStringConclusion();
				 
				 content.setText(probeIt.util.LineFormatter.formatText(text, 70, 10));//"~ hates(butler, butler)  |  ~ hates(butler, charles)");
				 //text.setBorder(new EtchedBorder());
				 
			 }catch(Exception e)
			 {e.printStackTrace();}
			
			

			content.setBackground(Color.yellow);
			content.setBorder(new LineBorder(Color.black));
			content.setEditable(false);
			content.setFont(content.getFont().deriveFont(Font.BOLD));
			
			content.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent evt) 
				{
					if(evt.getButton() == MouseEvent.BUTTON1)
					{
						if(link != null)
						{
							//ViewsModel.getInstance().setAnswer(link);
							((ProbeItView)ViewsManager.getInstance().getViewPane()).buildProvenanceView(link);
							//update NodeSelectionInteractor._lastPointedFigure to the linked NodeSet. Will have to check URI's in tree to find correct one, unless another method?
						}
						else
							System.out.println("PMLNode (link) is NULL.");
					}
				}
			});
			
			this.add(content);
		}
	}
}
