/**
 * 
 */
package probeIt.ui.workflow;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Point;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import com.hp.hpl.jena.ontology.OntClass;

/**
 * @author Leonardo Salayandia
 *
 */
public class IndividualNode extends DefaultGraphCell {
	private static final long serialVersionUID = 1L;
	private OntClass ind;

	public IndividualNode(OntClass ind, boolean highlight) {
		this.ind = ind;
		
		// set node name
		this.setUserObject(SAW.getSAWInstanceQName(ind));
		
		// compute node coord
		Point locCoord = SAW.getInstanceCoordinate(ind);
		double w = 150;
		double h = 25;
		double x = 0;
		double y = 0;
		if (locCoord != null) {
			x = locCoord.getX() - (w/2);
			y = locCoord.getY() - (h/2);
		}
		// set common attributes
		AttributeMap attr = this.getAttributes();
		GraphConstants.setBounds(attr, new Rectangle.Double(x,y,w,h));
		GraphConstants.setChildrenSelectable(attr, false);
		GraphConstants.setOpaque(attr, true);
		GraphConstants.setBorderColor(attr, (highlight) ? new Color(255,0,0) : new Color(0,0,0));
		GraphConstants.setLineWidth(attr, (highlight) ? 2 : 1);
		GraphConstants.setResize(attr, false); // can't resize nodes, standardized size, currently size info not stored on mbw
		GraphConstants.setEditable(attr, false); // can't edit text directly on graph
		GraphConstants.setConnectable(attr, false); // can't connect edges, connection done through node overlapping
		GraphConstants.setSelectable(attr, false); // select enabled for dragging
		GraphConstants.setSizeable(attr, false);
		//set attributes according to individual type
		if (SAW.isPMLSourceType(ind)) {
			GPCellViewFactory.setViewClass(attr, "edu.utep.cybershare.sawviewer.JGraphEllipseView");
			GraphConstants.setGradientColor(attr, new Color(255,128,0));
		}
		else {
			GraphConstants.setGradientColor(attr, new Color(0,255,0));
		}
		this.addPort();
		
		GraphConstants.setDisconnectable(this.getAttributes(), false);
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
		if (o instanceof IndividualNode) {
			ans = ((IndividualNode) o).getIndividual().equals(ind);
		}
		return ans;
	}
}
