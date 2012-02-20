/**
 * 
 */
package probeIt.ui.workflow;

import java.awt.Color;
import java.awt.Point;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import com.hp.hpl.jena.ontology.OntClass;

/**
 * @author Leonardo Salayandia
 *
 */
public class IndividualEdge extends DefaultEdge {
	private static final long serialVersionUID = 1L;
private OntClass ind;
	
	public IndividualEdge(OntClass ind, DefaultGraphCell src, DefaultGraphCell target, boolean highlight) {
		this.ind = ind;
		
		// set node name
		this.setUserObject(SAW.getSAWInstanceQName(this.ind));
		
		// set edge src
		if (src != null) {
			this.setSource(src.getChildAt(0));
		}

		// set edge target
		if (target != null) {
			this.setTarget(target.getChildAt(0));	
		}
		
		// set other attributes
		AttributeMap attr = this.getAttributes();
		GraphConstants.setLineEnd(attr, GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(attr, true);
		GraphConstants.setSelectable(attr, false);
		GraphConstants.setEditable(attr, false);	
		GraphConstants.setBendable(attr, false);
		GraphConstants.setLineColor(getAttributes(), (highlight) ? new Color(255,0,0) : new Color(0,0,0));
		GraphConstants.setLabelAlongEdge(attr, true); // tilts text to match flow of edge, and allows to set x/y coord of label
		
		
		// set position of label
		Point labelCoord = SAW.getInstanceCoordinate(ind);
		if (labelCoord != null) {
			GraphConstants.setLabelPosition(attr, labelCoord);
		}		
	}
	
	/**
	 * 
	 * @return
	 */
	public OntClass getIndividual() {
		return ind;
	}
	
	public boolean equals(Object o) {
		boolean ans = false;
		if (o instanceof IndividualEdge) {
			ans = ((IndividualEdge) o).getIndividual().equals(ind);
		}
		return ans;
	}
}
