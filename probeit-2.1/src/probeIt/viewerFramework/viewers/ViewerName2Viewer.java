package probeIt.viewerFramework.viewers;
import java.util.HashMap;

public class ViewerName2Viewer
{
	private static HashMap<String,Viewer> map;
	
	public static Viewer getViewer(String viewerName)
	{
		if(viewerName == null)
		{
			return Viewers.getText();
		}
		
		
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_EQL"))
			return Viewers.getEQLookup();
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_Hexadecimal"))
			return Viewers.getHexadecimal();
		if(viewerName.equals("http://rio.cs.utep.edu/ciserver/ciprojects/viskoOperator/imageJ-viewer.owl#imageJ-viewer"))
			return Viewers.getImageJ();
		if(viewerName.equals("http://rio.cs.utep.edu/ciserver/ciprojects/viskoOperator/parvis-viewer.owl#parvis-viewer"))
			return Viewers.getParvis();
		if(viewerName.equals("http://rio.cs.utep.edu/ciserver/ciprojects/viskoOperator/pdf-viewer.owl#pdf-viewer"))
			return Viewers.getPDF();
		if(viewerName.equals("http://rio.cs.utep.edu/ciserver/ciprojects/viskoOperator/plain-text-viewer.owl#plain-text-viewer"))
			return Viewers.getText();
		if(viewerName.equals("http://rio.cs.utep.edu/ciserver/ciprojects/viskoOperator/html-viewer.owl#html-viewer"))
			return Viewers.getHTML();
			
			
		/*
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_EQL"))
			return Viewers.getEQLookup();
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_Hexadecimal"))
			return Viewers.getHexadecimal();
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_ImageJ"))
			return Viewers.getImageJ();
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_Parvis"))
			return Viewers.getParvis();
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_PDF"))
			return Viewers.getPDF();
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_Text"))
			return Viewers.getText();
		if(viewerName.equals("probeIt.viewerFramework.viewers.Viewer_HTML"))
			return Viewers.getHTML();
		*/
				
		return Viewers.getText();
	}
}
