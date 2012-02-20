package probeIt.ui;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import probeIt.ProbeIt;
import probeIt.action.*;
import probeIt.ui.model.ViewsModel;

import probeIt.graphics.canvas.JustificationCanvas;
import probeIt.graphics.figure.NodeSetGraphic;
import pml.PMLNode;
import pml.PMLQuery;
import probeIt.ui.global.JustificationView;

import javax.swing.event.*;

import diva.canvas.CanvasPane;
import diva.canvas.JCanvas;
import java.awt.GridLayout;
import java.awt.geom.AffineTransform;


public class Toolbar extends JPanel implements ChangeListener
{
	public static enum CanvasType {queryView,justificationView,provenanceView,answerView};
	CanvasType canvasType;
	JComponent progressBar;
	static JSlider zoomSlider;
	
	public Toolbar()
	{
		super();
		canvasType = CanvasType.queryView;
	}
	
	public Toolbar(CanvasType type)
	{canvasType = type;}
	
	//sets up the toolbar depending on what canvas the user is currently using
	public void setup()
	{
		setLayout(new GridLayout(1,6));
		
		if(canvasType == CanvasType.queryView)
		{
			if(probeIt.ProbeIt.getInstance().getWindow().getViewConfiguration().isEnabledFeature_S())
			{
				JButton sourcesButton = new JButton(ProbeItActions.ShowSources);		
				add(sourcesButton, BoxLayout.X_AXIS);
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
				add(new JPanel());
			}
		}

		else if(canvasType == CanvasType.answerView)
		{
			System.out.println("answer view type");
			JButton seeGlobalViewButton = new JButton(ProbeItActions.LookUp);
			add(seeGlobalViewButton, BoxLayout.X_AXIS);
			add(new JPanel());
			add(new JPanel());
			add(new JPanel());
			add(new JPanel());
		}
		else if(canvasType == CanvasType.justificationView)
		{	
			//Zooming
			JPanel zoomButtonP = new JPanel();
			zoomButtonP.setLayout(new GridLayout(1,3));
			
			JLabel zoomL = new JLabel("  ZOOM:");
			//zoomL.setFont(new Font("Times", Font.BOLD, 10));
			zoomButtonP.add(zoomL);//, BoxLayout.X_AXIS);
			
			JButton zoomOut = new JButton(ProbeItActions.ZoomOut);
			JButton zoomIn = new JButton(ProbeItActions.ZoomIn);
			zoomButtonP.add(zoomOut);//, BoxLayout.X_AXIS);
			zoomButtonP.add(zoomIn);
			add(zoomButtonP);
			
			//Zooming Slider		
			zoomSlider = new JSlider(JSlider.HORIZONTAL, 5, 200, ((int)JustificationCanvas.getScale()*100));
			zoomSlider.addChangeListener(new ZoomSlider());
			add(zoomSlider);
	
			//Style
			String[] styles = new String[]{"Style - Tree", "Style - DAG", "Style - Forest", "Style - Questions"};
			JComboBox justificationStyle = new JComboBox(styles);
			
			if(ProbeIt.getInstance().getEmbeddedProbeIt().justStyle == probeIt.ui.ViewsManager.JustificationStyle.tree)
			{
				justificationStyle.setSelectedIndex(0);
			}
			else if(ProbeIt.getInstance().getEmbeddedProbeIt().justStyle == probeIt.ui.ViewsManager.JustificationStyle.dag)
			{
				justificationStyle.setSelectedIndex(1);
			}
			else if(ProbeIt.getInstance().getEmbeddedProbeIt().justStyle == probeIt.ui.ViewsManager.JustificationStyle.questions)
			{
				justificationStyle.setSelectedIndex(3);
			}
			else if(ProbeIt.getInstance().getEmbeddedProbeIt().justStyle == probeIt.ui.ViewsManager.JustificationStyle.forest)
			{
				justificationStyle.setSelectedIndex(2);
			}
			justificationStyle.setAction(ProbeItActions.JustStyleAction);
			add(justificationStyle);
			
			//Box Slider
			JLabel heightSliderLabel = new JLabel(" Height of Conclusions:");
			add(heightSliderLabel);
			JSlider boxYvalue = new JSlider(JSlider.HORIZONTAL, 40, 300, probeIt.graphics.figure.NodeSetGraphic.HEIGHT);
			boxYvalue.addChangeListener(this);			
			add(boxYvalue);
		}
		else //provenance
		{
			JButton pmlSourceButton = new JButton(ProbeItActions.PMLSource);
			add(pmlSourceButton);
			
			add(new JPanel());
			add(new JPanel());
			add(new JPanel());
			add(new JPanel());
			add(new JPanel());
		}
		
		JButton toggleExpandButton = new JButton();
		toggleExpandButton.setAction(ProbeItActions.ToggleExpand);
		add(toggleExpandButton);
	}
	
	public static void updateZoom()
	{
		zoomSlider.setValue((int)(JustificationCanvas.getScale()*100));
	}
	
	public void setType(CanvasType type)
	{
		canvasType = type;
	}
	
	public CanvasType getType()
	{
		return canvasType;
	}
	
	public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        
        if(!source.getValueIsAdjusting()) 
        {
            int height = (int)source.getValue();
            NodeSetGraphic.HEIGHT=height;
            
            if(canvasType == CanvasType.queryView)
            {
            	PMLQuery query = ViewsModel.getInstance().getQuery();
            	ViewsModel.getInstance().setQuery(query);
            	((ProbeItView)ProbeIt.getInstance().getEmbeddedProbeIt().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
            }
            else
            {
            	if(height < 60)
            	{
            		NodeSetGraphic.showInferenceStepInfo = false;
            		NodeSetGraphic.showConclusion = false;
            		NodeSetGraphic.showArrows = false;
            		NodeSetGraphic.WIDTH = 60;
            	}
            	else if(height < 80)
            	{
            		NodeSetGraphic.showInferenceStepInfo = false;
            		NodeSetGraphic.showConclusion = false;
            		NodeSetGraphic.showArrows = true;
            		NodeSetGraphic.WIDTH = 120;
            	}
            	else if(height < 100)
            	{
            		NodeSetGraphic.showInferenceStepInfo = false;
            		NodeSetGraphic.showConclusion = true;
            		NodeSetGraphic.showArrows = true;
            		NodeSetGraphic.WIDTH = 230;
            	}
            	else
            	{
            		NodeSetGraphic.showInferenceStepInfo = true;
            		NodeSetGraphic.showConclusion = true;
            		NodeSetGraphic.showArrows = true;
            		NodeSetGraphic.WIDTH = 230;
            	}
            	
            	PMLNode node = ViewsModel.getInstance().getJustification();
            	ViewsModel.getInstance().setJustification(node);
            	((ProbeItView)ProbeIt.getInstance().getEmbeddedProbeIt().getViewPane()).setActiveIndex(ProbeItView.GLOBAL_JUSTIFICATION_TAB);
            }            
        }
    }
	
	private class ZoomSlider implements ChangeListener
	{
		public void stateChanged(ChangeEvent e) 
		{
	        JSlider source = (JSlider)e.getSource();
	        
	        if(!source.getValueIsAdjusting())
	        {
	        	JCanvas canvas = JustificationCanvas.getCurrentCanvas();//.getCanvasPane();
	            CanvasPane pane = canvas.getCanvasPane();
	            AffineTransform t = new AffineTransform();
	        	
	        	JustificationCanvas.setScale((double)source.getValue()/100.0);
	        	System.out.println("scale: "+ JustificationCanvas.getScale());
	        	
	        	t.scale(JustificationCanvas.getScale(), JustificationCanvas.getScale());
	            pane.setTransform(t);
	            JustificationView.getInstance().repaint();
	        }
		}
	        
	}
}
