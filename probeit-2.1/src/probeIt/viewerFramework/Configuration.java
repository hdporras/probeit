package probeIt.viewerFramework;

import cacher.VisualizationCacheProxy;

import javax.swing.JComponent;

import probeIt.viewerFramework.transform.ConclusionTransformer;
import probeIt.viewerFramework.viewers.Viewer;
import probeIt.viewerFramework.viewers.Viewers;
import pml.impl.serializable.PMLConclusion;
import pml.util.GetURLContents;

public class Configuration
{
	Viewer viewer;
	ConclusionTransformer transformer;
	PMLConclusion conclusion;
	Object data;

	protected Configuration(PMLConclusion aConclusion, ConclusionTransformer aTransformer, Viewer aViewer, Object aData)
	{
		if(viewer ==  null)
			System.out.println("viewer is null (Configuration)");

		if(aConclusion == null)
			System.out.println("null conclusion (Configuration)");

		transformer = aTransformer;
		conclusion = aConclusion;
		viewer = aViewer;
		data = aData;
		System.out.println("Viewer set to: "+viewer.getViewerName());
	}
	
	//protected Configuration(PML) Conufiguration for default text viewer

	protected JComponent getViewerInterface()
	{

		//Object data;

		System.out.println("Approach to attain vis? for: "+viewer.getViewerName());
		System.out.println("Conclusion format: "+conclusion.getConclusionClassifier().getConclusionType());

		/*try
		{
			String url = conclusion.getHasURL();
			if(url.contains(".pdf"))
			{
				System.out.println("Is PDF... uploading PDF");
				data = GetURLContents.downloadFile(url);
				viewer = Viewers.getPDF();
				return viewer.getViewerInterface((byte[])data);
			}
		}catch(Exception e){e.printStackTrace();}

		System.out.println("Get Cached Vis");
		VisualizationCacheProxy proxy = VisualizationCacheProxy.getInstance();

		data = proxy.getCachedNodesest(conclusion.getURI());
*/

	/*	if(data == null)
		{
			System.out.println("No Vis found in Cache... Transform to text.");
			data = transformer.transform(conclusion);
			viewer = Viewers.getText();
		}
		else
		{
			//hardcoding viewer to ImageJ. Can go back to prefs, by commenting out.
			viewer = Viewers.getImageJ();
		}*/

		/*
		if(viewer.getViewerName().equals("Text Viewer"))
		{
			System.out.println("Transform to text.");
			data = transformer.transform(conclusion);
		}
		else
		{

		}*/
		/*		if(data == null)
			data = transformer.transform(conclusion);*/

		if(data == null)
			data = transformer.transform(conclusion);
		
		return viewer.getViewerInterface(data);
	}

	protected int getViewerHeight()
	{return viewer.getLogicalHeight();}

	protected int getViewerWidth()
	{return viewer.getLogicalWidth();}

	protected String getViewerName()
	{return viewer.getViewerName();}
	/*
	protected String getTransformerName()
	{return transformer.getName();}
	 */
	public String toString()
	{return /*getTransformerName()+":"+*/getViewerName();}
}
