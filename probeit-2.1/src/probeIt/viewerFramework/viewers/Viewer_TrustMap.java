package probeIt.viewerFramework.viewers;

import gmt.GMTToolkit;
import probeIt.ProbeIt;
import probeIt.util.TrustValueRetrieval;
import probeIt.viewerFramework.viewers.imaging.AutoCrop;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.PMLNode;
import gravityMapScenario.gravityDataset.Dataset;

public class Viewer_TrustMap
{
	String mapImagePath;
	String scaleImagePath;
	public ProbeitImage getTrustMapImage(PMLNode aNode)
	{
		TrustValueRetrieval tr = new TrustValueRetrieval(aNode, ProbeIt.getInstance().isRemote());
		Dataset ds = tr.getTrustValueDataset();
		
		GMTToolkit tk = new GMTToolkit(ProbeIt.getInstance().getGMTConnectionType());
		String griddedDS = tk.getNearneighborGriddedData(ds.backToAscii(), 0.02, 0.2);
		
		if(griddedDS == null)
			return null;
		
		String[] mapAndScale = tk.esriGrd2grdImagePS_trust(griddedDS);
		String map = mapAndScale[0];
		String scale = mapAndScale[1];
		
		mapImagePath = tk.ps2PNG(map);
		scaleImagePath = tk.ps2PNG(scale);
		
		if(mapImagePath != null && scaleImagePath != null)
		{
			AutoCrop ac = new AutoCrop(mapImagePath);
			return ac.cropAndScale(500, 500);
		}
		
		//**String result = cache.;
		
		return null;
	}
	
	public ProbeitImage getScaleImage()
	{
		AutoCrop ac = new AutoCrop(scaleImagePath);
		return ac.cropAndScale(500, 500);	
	}
}
