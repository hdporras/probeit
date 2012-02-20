package probeIt.viewerFramework;
import probeIt.util.ClassScope;
import java.util.ArrayList;
public class TransformersAndViewers
{	   
	public static String[] getTransformerNames()
	{  
		/*
		ClassLoader appLoader = ClassLoader.getSystemClassLoader();
	   
	         
	    ClassLoader[] loaders = new ClassLoader[] { appLoader};  
	    final Class< ?>[] classes = ClassScope.getLoadedClasses(loaders);
	         
	         ArrayList<String> trans = new ArrayList<String>();
	      
	         for (int i = 0; i < classes.length; i ++)
	         {  
	             String className = classes[i].getName();
	             if(className.startsWith("probeIt.viewerFramework.transform.Conclusion2"))
	             {
	            	 trans.add(className);
	             }
	         }
	         
	         String transNames[] = new String[trans.size()];
	         
	         for(int i = 0; i < transNames.length ;i++)
	         {
	        	 transNames[i] = trans.get(i);
	         }
	         
	         return transNames;*/
	
		String transNames[] = new String[]{
				"probeIt.viewerFramework.transform.Conclusion2ByteArray",
				"probeIt.viewerFramework.transform.Conclusion2BinaryLogoImage",
				"probeIt.viewerFramework.transform.Conclusion2ColoredGridImage",
				"probeIt.viewerFramework.transform.Conclusion2GravityPointsImage",
				"probeIt.viewerFramework.transform.Conclusion2Hexadecimal",
				"probeIt.viewerFramework.transform.Conclusion2JavaImage",
				"probeIt.viewerFramework.transform.Conclusion2KIFText",
				"probeIt.viewerFramework.transform.Conclusion2PSImage",
				"probeIt.viewerFramework.transform.Conclusion2STF",
				"probeIt.viewerFramework.transform.Conclusion2Text",
				"probeIt.viewerFramework.transform.Conclusion2XMDVData",
				"probeIt.viewerFramework.transform.Conclusion2HTML",
				"probeIt.viewerFramework.transform.renderableKIF.Conclusion2RegionImage"};
		
		return transNames;
		
	     } 
	
	public static String[] getViewerNames()
	{  
		/*
	         ClassLoader appLoader = ClassLoader.getSystemClassLoader();
	   
	         ClassLoader[] loaders = new ClassLoader[] { appLoader};  
	         final Class< ?>[] classes = ClassScope.getLoadedClasses(loaders);
	         
	         ArrayList<String> viewers = new ArrayList<String>();
	         
	         for (int i = 0; i < classes.length; i ++)
	         {  
	             String className = classes[i].getName();
	             if(className.startsWith("probeIt.viewerFramework.viewers.Viewer_"))
	             {
	            	 viewers.add(className);
	             }
	         }
	         
	         String viewerNames[] = new String[viewers.size()];
	         
	         for(int i = 0; i < viewerNames.length ;i++)
	         {
	        	 viewerNames[i] = viewers.get(i);
	         }
	         
	         return viewerNames;*/
		
        String[] viewerNames = new String[]{
        "probeIt.viewerFramework.viewers.Viewer_EQLXML",
        "probeIt.viewerFramework.viewers.Viewer_Hexadecimal",
        "probeIt.viewerFramework.viewers.Viewer_ImageJ",
        "probeIt.viewerFramework.viewers.Viewer_ImageJOverlay",
        "probeIt.viewerFramework.viewers.Viewer_Parvis",
        "probeIt.viewerFramework.viewers.Viewer_PDF",
        "probeIt.viewerFramework.viewers.Viewer_Text",
        "probeIt.viewerFramework.viewers.Viewer_HTML",
        "probeIt.viewerFramework.viewers.Viewer_TrustMap"};
        
        return viewerNames;
	     
	
	}  
}
