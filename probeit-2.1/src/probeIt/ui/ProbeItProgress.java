package probeIt.ui;
import java.awt.Dimension;
import javax.swing.JComponent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
public class ProbeItProgress extends JPanel
{	
	JPanel progressPanel;
	JProgressBar progress;
	JComponent control;
	JLabel label;
	
	public ProbeItProgress()
	{
		setLayout(new BorderLayout());
		progressPanel = new JPanel();
		progressPanel.setLayout(new BorderLayout());
		addProgressBar();
		addLabel();
		
		add(progressPanel, BorderLayout.EAST);
	}
	
	private void addLabel()
	{
		label = new JLabel();
		progressPanel.add(label, BorderLayout.WEST);
	}
	
	public void enableProgressBar(String statusText)
	{
		System.out.println("[ProbeItProgress.enable] " + statusText);
		progress.setIndeterminate(true);
		progress.setEnabled(true);
		
		label.setText(statusText);
	}
	
	public void disableProgressBar()
	{
		System.out.println("[ProbeItProgress.disable] " + label.getText());
		progress.setIndeterminate(false);
		
		label.setText("");
		//progress.setEnabled(false);
	}
	
	public void addCustomizedControl(JComponent comp)
	{
		control = comp;
		add(comp, BorderLayout.WEST);
	}
	
	public void removeCustomizedControl()
	{
		if(control != null)
		{
			remove(control);
		}
		control = null;
	}
	
	private void addProgressBar()
	{
		progress = new JProgressBar();
		progress.setPreferredSize(new Dimension(200, 15));
		//progress.setMinimum(0);
		progress.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		progress.setIndeterminate(false);
		
		//progress.setValue(0);
	
		progress.setEnabled(false);
		progressPanel.add(progress, BorderLayout.EAST);
	}

}
