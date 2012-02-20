package probeIt.ui.model;

import org.apache.log4j.helpers.Loader;

import probeIt.ProbeIt;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;

import pml.PMLNode;
import pml.PMLQuery;

public class ViewsModel {	
	
	static ViewsModel instance;
	
	private static int LOAD_EMPTY = 0;
	private static int LOAD_ANSWER = 1;
	private static int LOAD_JUSTIFICATION = 2;
	
	private PMLNode  nodeSet;       // Root node set of a PML justification trace
	private PMLQuery query;         // A PML query, which is the state of the current query view

	/**
	 *  The variable @level is associated with the variable nodeSet
	 *  
	 *  The model can have no justification loaded (LOAD_EMPTY) or loaded. When loaded, it can 
	 *  have just the last node set loaded (LOAD_ANSWER) or the entire justification (LOAD_JUSTIFICATION). 
	 *  
	 *  If the entire justification is loaded, no other action is required for a given URI. If the last
	 *  node set is loaded, it is still possible to load the rest of the justification.
	 *  
	 *  The URI of the workflow would be loaded whenever the answer or the entire justification is
	 *  loaded and the last node set is associated with a workflow.
	 */
	private int level; 
	
	private ViewsModel()
	{
		//System.out.println("[ViewsModel()]");		
		level = LOAD_EMPTY;
	}
	
	/*public static void setInstance(ViewsModel view)
	{
		System.out.println("[ViewsModel.setInstance()]");		
		instance = view;
	}*/
	
	public static ViewsModel getInstance()
	{
		//System.out.println("[ViewsModel.getInstance()]");		
		if (instance == null)
			instance = new ViewsModel();
		
		return instance;
	}
	
	/* 
	 * Verifies if the model has an associated query.
	 */
	public boolean hasQuery()
	{
		//System.out.println("[hasQuery] " + query + " at instance " + this);
		return query != null;
	}
	
	/*
	 *  Verifies if the model has at least of associated nodeset
	 */
	public boolean hasAnswer()
	{
		return nodeSet != null;
	}
	
	public boolean hasWorkflow()
	{
		if (nodeSet == null || nodeSet.getConclusion() == null)
			return false;

		String workflowURI = nodeSet.getConclusion().getDirectTypeOf();

		return workflowURI != null;
	}
	
	public boolean hasJustification()
	{
		return nodeSet != null && level == LOAD_JUSTIFICATION;
	}
	
	public PMLQuery getQuery()
	{
		return query;
	}
	
	public PMLNode getAnswer()
	{
		return nodeSet;
	}

	public String getWorkflowURI()
	{
		if (nodeSet == null)
			return null;
		
		return nodeSet.getConclusion().getDirectTypeOf();
	}
	
	public PMLNode getJustification()
	{
		if (level == LOAD_JUSTIFICATION)
			return nodeSet;
		else
			return null;
	}
	
	public String getLocalViewNodesetURI()
	{
		if (nodeSet == null)
			return null;
		
		return nodeSet.getURI();
	}
	
	/* 
	 *  A context for the model is defined by the presence of either a query or a nodeset  
	 */
	public boolean hasContext()
	{
		return (query != null) || (nodeSet != null);
	}
	
	/* 
	 * Resets model to an empty state
	 */
	public void newContext()
	{
		//System.out.println("[newContext]");
		query = null;
		nodeSet = null;
		level = LOAD_EMPTY;    		
		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetTabs();
	}
	
	/* 
	 * Resets model's node set to null
	 */
	public void newAnswerJustificationContext()
	{
		nodeSet = null;
		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetAnswerTab();
		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetJustificationTab();
		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetWorkflowTab();
		level = LOAD_EMPTY;
	}

	/* 
	 * Resets model's query to null
	 */
	public void newQueryContext()
	{
		//System.out.println("[newQueryContext]");
		nodeSet = null;
		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetQueryTab();
		level = LOAD_EMPTY;
	}

	/* 
	 *  Assigns a query from a loaded PMLQuery to the model.
	 */
	public void setQuery(PMLQuery aQuery)
	{
		System.out.println("[setQuery with PMLQuery] " + aQuery.getURI());
		query = aQuery;
		((ProbeItView)ViewsManager.getInstance().getViewPane()).buildQueryView();
		((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
	}
	
	/*
	 *  Loads a PMLQuery from a given URI. Assigns the
	 *  loaded query to the model.
	 */
	public void setQuery(String uri)
	{
		System.out.println("[setQuery with String] " + uri);
		PMLLoader loader = new PMLLoader(uri.trim());
		loader.start();  // this call will eventually invoke setQuery(PMLQuery)
	}
	
	/* 
	 *  Assigns a node set from a loaded PMLNode to
	 *  the model.
	 */
	public void setAnswer(PMLNode aNodeSet)
	{
		if (nodeSet != null && aNodeSet != null && nodeSet.equals(aNodeSet))
			return;
		
		nodeSet = aNodeSet;
		((ProbeItView)ViewsManager.getInstance().getViewPane()).buildAnswerView();
		if (aNodeSet.getURI() != null)
			((ProbeItView)ViewsManager.getInstance().getViewPane()).buildWorkflowView();
		((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.ANSWER_TAB);
		level = LOAD_ANSWER;
	}
	
	/*
	 *  Loads the PML node set for the given URI. Assigns the
	 *  loaded node set to the model.
	 */
	public void setAnswer(String uri)
	{
		if (nodeSet != null && nodeSet.getURI().equals(uri))
			return;
		
		PMLLoader loader = new PMLLoader(uri.trim());
		loader.setLoadAnswerOnly(true);
		loader.start();  // this call will eventually invoke setAnswer(PMLNode) 
		level = LOAD_ANSWER;
	}
	
	public void setJustification(PMLNode aNodeSet)
	{
		if (nodeSet != null && nodeSet.equals(aNodeSet) && level == LOAD_JUSTIFICATION) {
			// nothing to do, full justification is already loaded
			return;
		}
		
		nodeSet = aNodeSet;
		((ProbeItView)ViewsManager.getInstance().getViewPane()).buildJustificationView(ViewsManager.getInstance().justStyle);
		((ProbeItView)ViewsManager.getInstance().getViewPane()).buildProvenanceView();
		if (aNodeSet.getURI() != null)
			((ProbeItView)ViewsManager.getInstance().getViewPane()).buildWorkflowView();
		level = LOAD_JUSTIFICATION;
	}
	
	/*
	 *  Loads the justification trace for the given URI. Assigns the
	 *  last node set of the loaded justification as the model's node
	 *  set.
	 */
	public void setJustification(String uri)
	{
		if (nodeSet != null && nodeSet.getURI().equals(uri) && level == LOAD_JUSTIFICATION) {
			// nothing to do, full justification is already loaded
			return;
		}
		
		PMLLoader loader = new PMLLoader(uri.trim());
		loader.start();  // this call will eventually invoke setJustification(PMLNode)
		level = LOAD_JUSTIFICATION;
	}
	
}
