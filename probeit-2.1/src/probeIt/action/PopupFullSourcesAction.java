package probeIt.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import probeIt.ProbeIt;
import probeIt.ui.popup.source.PopupSourceView;
import probeIt.ui.query.AnswerPanel;
import pml.PMLNode;

public class PopupFullSourcesAction extends ProbeItGenericAction implements MouseListener
{

	PMLNode nodeset;
	
	public void mouseClicked(MouseEvent event)
	{
		new PopupSourceView(nodeset);
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
	
	public PopupFullSourcesAction(String name)
	{
		super(name, NO_ICON);
	}
	
	public void setNodeset(PMLNode anode)
	{
		nodeset = anode;
	}

	public PopupFullSourcesAction()
	{
		super("Popup Sources", NO_ICON);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		new PopupSourceView(nodeset);
	}
}