package probeIt.action;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import probeIt.ProbeIt;
import probeIt.ui.ProbeItView;
import probeIt.ui.model.ViewsModel;

public class ShowSourcesAction extends ProbeItGenericAction
{

	
	public ShowSourcesAction(String name)
	{
		super(name, NO_ICON);
	}

	public ShowSourcesAction()
	{
		super("Show Sources", NO_ICON);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		JButton button = (JButton)e.getSource();

		boolean showing = ProbeIt.getInstance().getEmbeddedProbeIt().isShowingSources();
		
		if(showing)
		{
			button.setText("Show Sources");
		}
		else
		{
			button.setText("Hide Sources");
		}
		
		ProbeIt.getInstance().getEmbeddedProbeIt().setIsShowingSources(!showing);
		//ProbeIt.getInstance().getEmbeddedProbeIt().setQuery(ProbeIt.getInstance().getEmbeddedProbeIt().getQuery());
		((ProbeItView)ProbeIt.getInstance().getEmbeddedProbeIt().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
	}
}