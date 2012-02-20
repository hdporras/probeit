package probeIt.viewerFramework.transform;

import pml.PMLQuery;
import probeIt.util.GetURLContents;
import pml.impl.serializable.PMLConclusion;
public class Conclusion2Text implements ConclusionTransformer,QueryTransformer
{
	public String getName()
	{return "Text-Transformer";}
	public Object transform(PMLConclusion conclusion)
	{
		try
		{
			if(conclusion.isByReference())
				return GetURLContents.downloadText(conclusion.getHasURL());
		
			return conclusion.getStringConclusion();
			
		}catch(Exception e)
		{e.printStackTrace();}
		return null;
	}
	
	public String transformQuery(PMLQuery query)
	{
		return query.getContent();
	}
}
