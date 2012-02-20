package probeIt.viewerFramework.viewers;
import javax.swing.*;
import java.awt.BorderLayout;
import org.mediavirus.parvis.gui.*;

public class Viewer_Parvis extends JPanel implements Viewer 
{	
	private void initialize(String dataFile)
	{
		MainPanel parvis = new MainPanel(dataFile);
		parvis.setVisible(true);
		setLayout(new BorderLayout());
		add(parvis, BorderLayout.CENTER);
	}

	public String getViewerName()
	{return "Multivariate Viewer";};
	
	public JComponent getViewerInterface(Object stf)
	{
		if(stf instanceof String)
		{
			initialize((String) stf);
			return this;
		}
		else
			return null;
	}
	
	public int getLogicalWidth()
	{return ViewerStyle.DEFAULT_WIDTH;}
	
	public int getLogicalHeight()
	{return ViewerStyle.DEFAULT_HEIGHT;}
}
