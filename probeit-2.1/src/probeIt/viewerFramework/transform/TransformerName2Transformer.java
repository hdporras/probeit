package probeIt.viewerFramework.transform;

import java.util.HashMap;
//import probeIt.viewerFramework.transform.renderableKIF.RenderableKIFTransformers;

public class TransformerName2Transformer
{
	private static HashMap<String,ConclusionTransformer> map;
	
	public static ConclusionTransformer getTransformer(String transformerName)
	{
		if(map == null)
		{
			map = new HashMap();
			map.put("probeIt.viewerFramework.transform.Conclusion2Text", ConclusionTransformers.text);
		/*	map.put("probeIt.viewerFramework.transform.Conclusion2Hexadecimal", ConclusionTransformers.hexadecimal);
			map.put("probeIt.viewerFramework.transform.Conclusion2ColoredGridImage", ConclusionTransformers.coloredGridImage);
			map.put("probeIt.viewerFramework.transform.Conclusion2ByteArray", ConclusionTransformers.byteArray);
			map.put("probeIt.viewerFramework.transform.Conclusion2PSImage", ConclusionTransformers.PSImage);
			map.put("probeIt.viewerFramework.transform.renderableKIF.Conclusion2RegionImage", RenderableKIFTransformers.KIFRegionTransformer);
			map.put("probeIt.viewerFramework.transform.Conclusion2JavaImage", ConclusionTransformers.javaImage);
			map.put("probeIt.viewerFramework.transform.Conclusion2GravityPointsImage", ConclusionTransformers.gravityPointsImage);
			map.put("probeIt.viewerFramework.transform.Conclusion2HTML", ConclusionTransformers.html);
			map.put("probeIt.viewerFramework.transform.Conclusion2OKC", ConclusionTransformers.XMDVData);
			map.put("probeIt.viewerFramework.transform.Conclusion2STF", ConclusionTransformers.stf);
            map.put("probeIt.viewerFramework.transform.Conclusion2CoverageImage", ConclusionTransformers.cover3d);
            map.put("probeIt.viewerFramework.transform.Conclusion2DuSumImage", ConclusionTransformers.dusum3d);
            map.put("probeIt.viewerFramework.transform.Conclusion2Time3DImage", ConclusionTransformers.time3d);
            map.put("probeIt.viewerFramework.transform.Conclusion2VelocityImage", ConclusionTransformers.velocity3d);
		*/}
		if(transformerName == null)
		{
			System.out.println("transformername == null");
			Object trans = map.get("probeIt.viewerFramework.transform.Conclusion2Text");
			if(trans == null)
				System.out.println("gtransfomer is null");
		}
		return map.get(transformerName);
	}
}
