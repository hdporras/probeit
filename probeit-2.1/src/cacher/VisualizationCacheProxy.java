package cacher;

import java.util.LinkedList;

import edu.utep.trust.provenance.RDFStore;
import edu.utep.trust.provenance.RDFStore_Service;

public class VisualizationCacheProxy
{
	
	VisCacheService proxy;
	private static VisualizationCacheProxy instance;
	
	private VisualizationCacheProxy()
	{
		try
		{
			VisCacheServiceService service = new VisCacheServiceService();

			proxy = service.getVisCacheServicePort();

		}catch(Exception e)
		{e.printStackTrace();}
	}
	
	public static VisualizationCacheProxy getInstance()
	{
		if(instance == null)
			instance = new VisualizationCacheProxy();
		
		return instance;
	}
	
	public String[] getThumbnailCachedNodesetList(String[] nodesetURIs)
	{
		try
		{
			LinkedList<String> list = new LinkedList<String>();
			int size = nodesetURIs.length;
			
			for(int i=0; i < size; i++)
				list.add(nodesetURIs[i]);
			
			return proxy.getThumbnailCachedNodesetList(list).toArray(new String[1]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String getThumbnailCachedNodeset(String nodesetURI)
	{
		if(proxy == null)
			System.out.println("Proxy NULL!");
		
		try
		{return proxy.getThumbnail(nodesetURI);}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] getCachedNodesets(String nodesetURI)
	{
		try
		{
			return proxy.getVisualizations(nodesetURI).toArray(new String[1]);
		}catch(Exception e)
		{e.printStackTrace();
		return null;}
		
	}
	/*
	public boolean[] cacheNodeSets(String[] nodesetURIs)
	{
		try
		{
			return proxy.cacheNodesetList(nodesetURIs);
		}catch(Exception e)
		{e.printStackTrace();
		return null;}
	}
	
	public String[] getNoncachedNodeSets(String[] nodesetURIs)
	{
		try
		{
			return proxy.getNonCachedNodesetList(nodesetURIs);
		}catch(Exception e)
		{e.printStackTrace();
		return null;}
	}*/
	
	public String[] getViewers(String nodesetURI)
	{
		try
		{
			return proxy.getViewers(nodesetURI).toArray(new String[1]);
		}catch(Exception e)
		{e.printStackTrace();
		return null;}
	}
}
