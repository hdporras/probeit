package probeIt.viewerFramework;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

import cacher.VisualizationCacheProxy;
//import probeIt.viewerFramework.transform.*;
import probeIt.viewerFramework.transform.ConclusionTransformer;
import probeIt.viewerFramework.transform.TransformerName2Transformer;
import probeIt.viewerFramework.viewers.*;
import pml.impl.serializable.PMLConclusion;
//import probeIt.viewerFramework.transform.renderableKIF.RenderableKIFTransformers;
//import probeIt.viewerFramework.transform.ConclusionTransformers;
import probeIt.viewerFramework.viewers.Viewers;
import probeIt.ui.ViewerPreferences.Preferences;
//import vtkHolesService.VTKHoleTransformersProxy;

public class ViewerFactory
{
	public JComponent selectPrimaryViewer(PMLConclusion conclusion)
	{
		JTabbedPane tp = new JTabbedPane();
		tp.setTabPlacement(JTabbedPane.BOTTOM);
		int width = 0, height = 0;

		List configurations = getViewerConfigurations(conclusion);
		Configuration config = (Configuration) configurations.get(0);
		JComponent viewerInterface = config.getViewerInterface();
		String name = config.getViewerName();

		if (viewerInterface != null)
		{
			width = config.getViewerWidth();
			height = config.getViewerHeight();
			viewerInterface.setSize(width, height);
			tp.add(name, viewerInterface);
		} else if (viewerInterface == null)
			tp.add(name, new JLabel("error configuring viewer (selectPrimaryViewer)\n" +
					"Viewer type: "+config.viewer.getViewerName()));

		int adjWidth = width + 50;// insets.left + insets.right;
		int adjHeight = height + 100;// insets.top+insets.bottom;
		if (width > 0 && height > 0)
			tp.setSize(new java.awt.Dimension(adjWidth, adjHeight));

		return tp;
	}

	public JComponent selectViewers(PMLConclusion conclusion)
	{
		JTabbedPane tp = new JTabbedPane();
		tp.setTabPlacement(JTabbedPane.BOTTOM);
		int width = 0, height = 0;

		List configurations = getViewerConfigurations(conclusion);

		Configuration config;
		for (int i = 0; i < configurations.size(); i++)
		{
			config = (Configuration) configurations.get(i);
			String name = config.toString();
			JComponent viewerInterface = config.getViewerInterface();
			if (i == 0 && viewerInterface != null)
			{
				width = config.getViewerWidth();
				height = config.getViewerHeight();
				viewerInterface.setSize(width, height);
			}
			if (viewerInterface != null)
			{
				viewerInterface.setSize(width, height);
				tp.add(name, viewerInterface);
				// JOptionPane.showMessageDialog(null, "added a viewer");
			} else if (viewerInterface == null)
				tp.add(name, new JLabel("error configuring viewer (selectViewers) \n" +
						"Viewer type: "+config.viewer.getViewerName()));
		}

		int adjWidth = width + 50;
		int adjHeight = height + 100;

		if (width > 0 && height > 0)
			tp.setSize(new java.awt.Dimension(adjWidth, adjHeight));

		return tp;
	}

	private List getViewerConfigurations(PMLConclusion conclusion)
	{
		Preferences viewerPrefs = Preferences.getCurrentPreferences();
		String formatURI = conclusion.getConclusionClassifier().getConclusionType();

		ArrayList configs = new ArrayList();

		//****Should the cache return a Viewer or format?(VIEWER). should the preferred viewer be part of probeit2? No

		System.out.println("FormatURI: "+formatURI);
		String transName = viewerPrefs.getPreferredTransformer(formatURI);
		//String viewerName = viewerPrefs.getPreferredViewer(formatURI);

		ConclusionTransformer trans = TransformerName2Transformer.getTransformer(transName);


		VisualizationCacheProxy proxy = VisualizationCacheProxy.getInstance();
		String[] viewerNameURIs = proxy.getViewers(conclusion.getURI());

		//System.out.println("Get Cached Vis");

		String[] visualizations = proxy.getCachedNodesets(conclusion.getURI());
		
		if(visualizations != null && visualizations.length > 0)
		{
			System.out.println("Vis found in Cache.");
			for(int i=0; i < visualizations.length; i++)
			{
				System.out.println("viewerURI: "+ viewerNameURIs[i]);
				System.out.println("Conclusion: "+ conclusion);
				
				/*String viewerName = Preferences.getCurrentPreferences().getPreferredViewer(viewerNameURIs[i]);
				System.out.println("viewerName: "+ viewerName);*/
				
				Viewer viewer = ViewerName2Viewer.getViewer(viewerNameURIs[i]);

				System.out.println("Viewer: "+viewer.getViewerName());

				//***hard coded to imageJ viewer for now. This must change!
				//Viewer viewer = Viewers.getImageJ();

				configs.add(new Configuration(conclusion ,trans ,viewer, visualizations[i]));
			}
		}
		else
		{
			System.out.println("No Vis found in Cache... Transform to text.");
			//data = transformer.transform(conclusion);
			Viewer viewer = Viewers.getText();
			
			configs.add(new Configuration(conclusion, trans, viewer, null));
			configs.add(new Configuration(conclusion, trans, viewer, null));
			//***transform to text if nothing is cached?
		}
		
		return configs;

	}
}
