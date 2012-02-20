package probeIt.ui.ViewerPreferences;
import pml.util.pml.*;
public class Preferences
{
	private static Preferences currentPrefs;
	
	public static Preferences getCurrentPreferences()
	{
		if(currentPrefs == null)
		{
			currentPrefs = new Preferences();
			currentPrefs.viewerPrefs = new String[][]{
										{"http://rio.cs.utep.edu/ciserver/ciprojects/formats/BINARYINTARRAY.owl#BINARYINTARRAY", null, "probeIt.viewerFramework.viewers.Viewer_ImageJ"},
										{"http://rio.cs.utep.edu/ciserver/ciprojects/formats/BINARYFLOATARRAY.owl#BINARYFLOATARRAY","probeIt.viewerFramework.transform.Conclusion2Text", "probeIt.viewerFramework.viewers.Viewer_ImageJ"},
			                            {URIs.FORMAT_ASCII, "probeIt.viewerFramework.transform.Conclusion2Text","probeIt.viewerFramework.viewers.Viewer_Text"},
					                    {URIs.FORMAT_BINARY, "probeIt.viewerFramework.transform.Conclusion2Hexadecimal","probeIt.viewerFramework.viewers.Viewer_Hexadecimal"},
					                    {URIs.FORMAT_ESRIGRID, "probeIt.viewerFramework.transform.Conclusion2ColoredGridImage","probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {URIs.FORMAT_GRID_PARAMS, "probeIt.viewerFramework.transform.Conclusion2Text","probeIt.viewerFramework.viewers.Viewer_Text"},
					                    {URIs.FORMAT_PDF, "probeIt.viewerFramework.transform.Conclusion2ByteArray","probeIt.viewerFramework.viewers.Viewer_PDF"},
					                    {URIs.FORMAT_PS, "probeIt.viewerFramework.transform.Conclusion2PSImage", "probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {URIs.FORMAT_REGION, "probeIt.viewerFramework.transform.renderableKIF.Conclusion2RegionImage","probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {URIs.FORMAT_SOLAR_CHIPBIN, "probeIt.viewerFramework.transform.Conclusion2Hexadecimal", "probeIt.viewerFramework.viewers.Viewer_Hexadecimal"},
					                    {URIs.FORMAT_SOLAR_DATE, "probeIt.viewerFramework.transform.Conclusion2Text", "probeIt.viewerFramework.viewers.Viewer_Text"},
					                    {URIs.FORMAT_SOLAR_EQL, "probeIt.viewerFramework.transform.Conclusion2Text", "probeIt.viewerFramework.viewers.Viewer_EQLXML"},
					                    {URIs.FORMAT_SOLAR_FITS, "probeIt.viewerFramework.transform.Conclusion2Hexadecimal", "probeIt.viewerFramework.viewers.Viewer_Hexadecimal"},
					                    {URIs.FORMAT_SOLAR_GIF, "probeIt.viewerFramework.transform.Conclusion2JavaImage", "probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {URIs.FORMAT_DERBYDB, "probeIt.viewerFramework.transform.Conclusion2HTML", "probeIt.viewerFramework.viewers.Viewer_HTML"},
					                    {URIs.FORMAT_TAB_DELIMITED_DS, "probeIt.viewerFramework.transform.Conclusion2GravityPointsImage","probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {"http://iw.cs.utep.edu/earthscience/seismic/registry/FMT/binCoverage.owl#binCoverage", "probeIt.viewerFramework.transform.Conclusion2CoverageImage", "probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {"http://iw.cs.utep.edu/earthscience/seismic/registry/FMT/binDuSum.owl#binDuSum", "probeIt.viewerFramework.transform.Conclusion2DuSumImage", "probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {"http://iw.cs.utep.edu/earthscience/seismic/registry/FMT/binTime.owl#binTime", "probeIt.viewerFramework.transform.Conclusion2Time3DImage", "probeIt.viewerFramework.viewers.Viewer_ImageJ"},
					                    {"http://iw.cs.utep.edu/earthscience/seismic/registry/FMT/binVelocity.owl#binVelocity", "probeIt.viewerFramework.transform.Conclusion2VelocityImage", "probeIt.viewerFramework.viewers.Viewer_ImageJ"},};
									
		}	
		return currentPrefs;
	}

	private String[][] viewerPrefs;
	
	public Preferences()
	{
		viewerPrefs = new String[URIs.KNOWN_FORMATS.length][3];
	}

	public void setValueAt(Object value, int row, int column)
	{
		viewerPrefs[row][column] = (String)value;
	}
	
	public String getValueAt(int row, int column)
	{
		String valueAt = viewerPrefs[row][column];
		if(valueAt == null)
			return "Null damn";
		
		return valueAt;
	}
	
	public int getNumberofFormats()
	{return viewerPrefs.length;}
	
	public String getPreferredViewer(String formatURI)
	{
		for(int i = 0; i < viewerPrefs.length; i ++)
		{
			if(viewerPrefs[i][0].equalsIgnoreCase(formatURI))
			{
				return viewerPrefs[i][2];
			}
		}
		
		return "probeIt.viewerFramework.viewers.Viewer_Text";
	}
	
	public String getPreferredTransformer(String formatURI)
	{
		for(int i = 0; i < viewerPrefs.length; i ++)
		{
			if(viewerPrefs[i][0].equalsIgnoreCase(formatURI))
			{
				return viewerPrefs[i][1];
			}
		}
		
		return "probeIt.viewerFramework.transform.Conclusion2Text";
	}
	
	
}
