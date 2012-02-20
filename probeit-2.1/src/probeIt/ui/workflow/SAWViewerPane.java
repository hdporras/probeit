package probeIt.ui.workflow;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JScrollPane;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;

/**
 * @author Leonardo Salayandia
 *
 */
public class SAWViewerPane extends JScrollPane {
	private static final long serialVersionUID = 1L;
		
	public void setWorkflow(String workflowURI, String selectedURI) {
		try{
			if (workflowURI != null && !workflowURI.isEmpty()) {
				OntModel saw = SAW.readSAW(workflowURI);
				setWorkflow(saw, selectedURI);
			}
		}
		catch(java.security.AccessControlException e)
		{
			System.out.println("SAW Viewer cannot load file...");
			e.printStackTrace();
		}
	}
	
	public void setWorkflow(OntModel saw, String selectedURI) {
		JGraph sawGraph = this.createWorkflowGraph(saw, selectedURI);
		this.setWorkflow(sawGraph);
	}
		
	private JGraph createWorkflowGraph(OntModel saw, String selectedURI) {
		JGraph ans = null;
		if (saw != null) {
			// create the workflow graph
    		HashMap<OntClass,DefaultGraphCell> cellMap = new HashMap<OntClass,DefaultGraphCell>();
			// get the method individuals, and create a node for each
			for (Iterator<OntClass> i= SAW.listMethodSAWInstances(saw); i.hasNext(); ) {
				OntClass ind = i.next();
				// all method individuals should be anonymous, 
				// do not include semantic abstract workflow individuals in the workflow graph
				//if (ind.isAnon() && !SAW.isSemanticAbstractWorkflowType(ind)) {
				if (!SAW.isSemanticAbstractWorkflowType(ind)) {
					IndividualNode indNode = new IndividualNode(ind, ind.getURI().equals(selectedURI));
					cellMap.put(ind, indNode);
				}
			}
			// get the pmlp:source individuals, and create a node for each
			for (Iterator<OntClass> i = SAW.listSourceSAWInstances(saw); i.hasNext(); ) {
				OntClass ind = i.next();
				// exception, InferenceEngine is a subclass of Source, but we only want to display Source
				if (!SAW.isInferenceEngineType(ind)) {
					IndividualNode indNode = new IndividualNode(ind, ind.getURI().equals(selectedURI));
					cellMap.put(ind, indNode);
				}
			}
			// get the data individuals, and create an edge for each
			for (Iterator<OntClass> i = SAW.listDataSAWInstances(saw); i.hasNext(); ) {
				OntClass data = i.next();
				// get source of edge
				DefaultGraphCell srcNode = null;
				OntClass src = SAW.getIsOutputOf(data);
				if (src != null) {
					srcNode = cellMap.get(src);
				}
				// get target of edge
				DefaultGraphCell targetNode = null;
				OntClass target = SAW.getIsInputTo(data);
				if (target != null) {
					targetNode = cellMap.get(target);	
				}
				// create edge
				IndividualEdge dataCell = new IndividualEdge(data, srcNode, targetNode, data.getURI().equals(selectedURI));
				cellMap.put(data, dataCell);
			}
			// set GUI
			if (cellMap.size() > 0) {
				DefaultGraphCell[] cells = new DefaultGraphCell[cellMap.size()];
				cellMap.values().toArray(cells);
				
				ans = new JGraph(new DefaultGraphModel());
				ans.getGraphLayoutCache().setFactory(new GPCellViewFactory());
				ans.getGraphLayoutCache().insert(cells);	
			}
		}
		return ans;
	}
	
	private void setWorkflow(JGraph sawGraph) {
		if (sawGraph != null) {
			// Control-drag should clone selection
			sawGraph.setCloneable(false);
			// Do not allow labels to be edited in the graph
			sawGraph.setEditable(false);
			// Enable edit without final RETURN keystroke
			sawGraph.setInvokesStopCellEditing(true);
			// When over a cell, jump to its default port (we only have one, anyway)
			sawGraph.setJumpToDefaultPort(true);
			// Set graph to be connectable through GUI
			sawGraph.setConnectable(true);
			// set graph to be movable
			sawGraph.setMoveable(false);
			// set graph to be disconnectable through GUI
			sawGraph.setDisconnectable(false);
			sawGraph.setSelectionEnabled(true);
		}
		this.setViewportView(sawGraph);
	}		
}
