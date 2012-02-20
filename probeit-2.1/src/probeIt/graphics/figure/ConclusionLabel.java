package probeIt.graphics.figure;

import java.awt.Font;
import diva.canvas.toolbox.LabelFigure;
import probeIt.viewerFramework.*;
import pml.*;
import pml.impl.serializable.PMLConclusion;
import probeIt.util.*;
public class ConclusionLabel extends LabelFigure
{
	PMLConclusion _theConclusion;
	double _x, _y;
	public ConclusionLabel(PMLConclusion theConclusion, double x, double y)
	{
		_theConclusion = theConclusion;
		_x = x;
		_y = y;
		setConclusionLabel();
	}
	
	private void setConclusionLabel()
	{
		DAGContentFactory selector = new DAGContentFactory();
		String label = selector.getText(_theConclusion);
		setString(LineFormatter.formatText(label, 30, NodeSetGraphic.HEIGHT/22));
		setFont(new Font("Times", Font.BOLD, 12));
		translateTo(_x, _y);
	}
}
