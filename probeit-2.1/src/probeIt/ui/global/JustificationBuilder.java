package probeIt.ui.global;

import javax.swing.JOptionPane;

import pml.PMLNode;
import probeIt.graphics.CanvasDrawer_DAGJustification;
import probeIt.graphics.CanvasDrawer_TreeJustification;
import probeIt.graphics.CanvasDrawer_ForestJustification;
import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.ProbeIt;
import probeIt.ui.WindowApplication;
import probeIt.ui.model.ViewsModel;
import probeIt.ui.workers.SwingWorker;

public class JustificationBuilder extends SwingWorker
{
	JustificationCanvas justCanvas;
	JustificationView view;

	public JustificationBuilder(JustificationCanvas canvas, JustificationView justView)
	{
		super();
		justCanvas = canvas;
		view = justView;
	}

	public Object construct()
	{
		WindowApplication.getInstance().enableProgressBar("Building global justification view ");
		try
		{
			if(ProbeIt.getInstance().getEmbeddedProbeIt().isDAGStyle())
			{
				CanvasDrawer_DAGJustification drawer = new CanvasDrawer_DAGJustification(justCanvas);
				drawer.draw();
			}
			else if(ProbeIt.getInstance().getEmbeddedProbeIt().isForest())
			{
				CanvasDrawer_ForestJustification drawer = new CanvasDrawer_ForestJustification(justCanvas);
				drawer.draw();
			}
			else //is tree style
			{
				CanvasDrawer_TreeJustification drawer = new CanvasDrawer_TreeJustification(justCanvas);
				drawer.draw();
			}
			System.out.println("builder: " +javax.swing.SwingUtilities.isEventDispatchThread());
			System.out.println("end...");
		}catch(Exception e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error Drawing justification for " + ViewsModel.getInstance().getJustification().getURI(),
					"Drawing Justification", JOptionPane.WARNING_MESSAGE);
		}
		return null;
	}


	public void finished()
	{
		System.out.println("In finished (JusificationBuilder)");
		view.setInteractiveView();
		System.out.println("view set as interactive (JusificationBuilder)");
		WindowApplication.getInstance().disableProgressBar();
		System.out.println("progress bar disabled (JusificationBuilder)");
	}
}