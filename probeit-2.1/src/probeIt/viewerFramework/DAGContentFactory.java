package probeIt.viewerFramework;

import cacher.VisualizationCacheProxy;
/*import vtkHolesService.VTKHoleTransformersProxy;
import probeIt.viewerFramework.transform.ConclusionTransformers;
import probeIt.viewerFramework.transform.QueryTransformers;
import probeIt.viewerFramework.transform.renderableKIF.RenderableKIFTransformers;*/
import probeIt.viewerFramework.viewers.imaging.AutoCrop;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.PMLQuery;
import pml.impl.serializable.PMLConclusion;
import probeIt.viewerFramework.transform.*;

public class DAGContentFactory
{
	static VisualizationCacheProxy proxy = VisualizationCacheProxy.getInstance();

	public ProbeitImage getImage(PMLConclusion conclusion, int width, int height)
	{
		//*
		try
		{
			System.out.println("GetImage, from Cache...");
			System.out.println("conlusion.getURI: "+conclusion.getURI());
			String cachedURL = proxy.getThumbnailCachedNodeset(conclusion.getURI());
			if(cachedURL != null)
				return new ProbeitImage(cachedURL, width, height);
		}catch(Exception e){
			e.printStackTrace();
		}//*/
		
		return null;
	}

	public String getText(PMLQuery query)
	{
		String returnString;

        if (query == null) 
        	return "";
        
		if (query.getQueryClassifier().isKif())
			returnString = QueryTransformers.KIFText.transformQuery(query);
		else
			returnString = QueryTransformers.text.transformQuery(query);

		//returnString = "temp String for testing (DAGContentFactory)";
		return returnString;
	}

	public String getText(PMLConclusion conclusion)
	{
		String returnString;

		if (conclusion.getConclusionClassifier().isKif())
			returnString = (String)ConclusionTransformers.KIFText.transform(conclusion);
		else
			returnString = (String)ConclusionTransformers.text.transform(conclusion);

		//returnString = "temp String for testing (DAGContentFactory)";
		return returnString;
	}
}
