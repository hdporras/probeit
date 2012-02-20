package probeIt.viewerFramework.viewers;

import javax.swing.*;
public interface Viewer
{
	public int getLogicalWidth();
	public int getLogicalHeight();
	public String getViewerName();
	public JComponent getViewerInterface(Object viewable); //return JComponent that can be put inside a jcontainter
}
