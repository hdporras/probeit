package probeIt.ui;

import java.awt.BorderLayout;
import probeIt.ui.answer.*;
import probeIt.ui.avatar.AvatarView;
import probeIt.ui.global.QuestionsView;

import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;

import pml.PMLNode;
import probeIt.ProbeIt;
import probeIt.ui.global.JustificationView;
//import probeIt.ui.local.HTMLView;
import probeIt.ui.local.SwingView;
import probeIt.ui.model.ViewsModel;
import pml.loading.Loader;
import probeIt.ui.query.QueryView;
import probeIt.ui.workflow.SAWViewerPane;
import probeIt.ui.initial.*;
import probeIt.util.JarResourceLoader;

import java.awt.GridLayout;

public class ProbeItView extends JPanel
{
	static final String WELCOME_LABEL = "Welcome";
	static final String QUERY_LABEL = "Results View";
	static final String ANSWER_LABEL = "Product View";
	static final String SOURCE_LABEL = "Explanation View";
	static final String WORKFLOW_LABEL = "Workflow View";
	static final String JUSTIFICATION_LABEL = "Provenance Graph";

	public static final int WELCOME_TAB = 0;
	public static final int QUERY_TAB = 1;
	public static final int ANSWER_TAB = 2;
	public static final int LOCAL_JUSTIFICATION_TAB = 3;
	public static final int WORKFLOW_TAB = 4;
	public static final int GLOBAL_JUSTIFICATION_TAB = 5;
	
	private JTabbedPane tp;
	private AvatarView avatar;

	/*   these are the main tabs of the main tab panel of ProbeIi
	 *   tabs still need to be built
	 */
	JPanel welcomeTab; 
	JPanel queryTab;
	JPanel answerTab;
	JPanel workflowTab;
	JPanel justificationTab;
	JPanel provenanceTab;

	ProbeItProgress bottomPanel;

	public ProbeItView()
	{
		super();
		
		setLayout(new BorderLayout());

		welcomeTab = InitialView.getInstance();
		
		queryTab = new JPanel();
		queryTab.setLayout(new BorderLayout());
		
		answerTab = new JPanel();
		answerTab.setLayout(new BorderLayout());

		workflowTab = new JPanel();
		workflowTab.setLayout(new BorderLayout());

		justificationTab = new JPanel();
		justificationTab.setLayout(new BorderLayout());

		provenanceTab = new JPanel();
		provenanceTab.setLayout(new BorderLayout());

		buildEmptyPane();
        invalidate();
        validate();
	}

	public void resetTabs()
	{
		resetQueryTab();
		resetAnswerTab();
		resetWorkflowTab();
		resetJustificationTab();
		resetProvenanceTab();
	}

	public void addToolbar(JPanel toolbar)
	{bottomPanel.addCustomizedControl(toolbar);}

	public void removeToolbar()
	{bottomPanel.removeCustomizedControl();}

	/* this is were we can give control to a give tab
	 * by activating one tab, the code disable the other tabs
	 */
	public void setEnabledAt(int index, boolean enabled)
	{
		tp.setEnabledAt(index, enabled);
	}

	public int getActiveIndex()
	{ 
		return tp.getSelectedIndex(); 
	}
	
	public void setActiveIndex(int index)
	{
		tp.setEnabledAt(index, true);
		tp.setSelectedIndex(index);
	}

	private void buildEmptyPane()
	{
		tp = new JTabbedPane();
		tp.addTab(WELCOME_LABEL, welcomeTab);
		tp.addTab(QUERY_LABEL, queryTab);
		tp.addTab(ANSWER_LABEL, answerTab);
		tp.addTab(SOURCE_LABEL, provenanceTab);
		tp.addTab(WORKFLOW_LABEL, workflowTab);
		tp.addTab(JUSTIFICATION_LABEL, justificationTab);

		tp.setEnabledAt(WELCOME_TAB, true);
		tp.setEnabledAt(QUERY_TAB, false);
		tp.setEnabledAt(ANSWER_TAB, false);
		tp.setEnabledAt(WORKFLOW_TAB, false);
		tp.setEnabledAt(GLOBAL_JUSTIFICATION_TAB, false);
		tp.setEnabledAt(LOCAL_JUSTIFICATION_TAB, false);
		tp.addChangeListener(new ProbeItView.TabViewChanged());

		add(tp, BorderLayout.CENTER);
		
	}

	public void updateProbeItView() {
		JTabbedPane currentTP = tp;
		AvatarView currentAvatar = avatar;
		this.removeAll();
		this.add(currentTP, BorderLayout.CENTER);
		if (WindowConfiguration.getInstance().hasAvatar())
		 	this.add(currentAvatar, BorderLayout.WEST);
		this.repaint();
	}

/****************************** BUILD VIEWS ****************************************/
		
	public void buildAvatarView(boolean hasMultimedia)
	{		
		avatar = new AvatarView(hasMultimedia);
		//((WindowApplication)ProbeIt.getInstance().getWindow()).updateView();			
	 	setEnabledAt(ProbeItView.WELCOME_TAB, true);
	 	setActiveIndex(ProbeItView.WELCOME_TAB);

	 	this.add(avatar, BorderLayout.WEST);
		ProbeIt.getInstance().getFrame().repaint();
		//setAvatarPanel(view);
		//this.setActiveIndex(0);
	}
	
	public void buildQueryView()
	{
		//System.out.println("[buildQuerView()]");
		QueryView view = new QueryView();
		setQueryTab(view);
		this.setActiveIndex(QUERY_TAB);
		ProbeIt.getInstance().getFrame().repaint();
	}

	public void buildAnswerView()
	{
		AnswerView view = new AnswerView();
		setAnswerTab(view);
		this.setActiveIndex(ANSWER_TAB);
		ProbeIt.getInstance().getFrame().repaint();
	}

	public void buildWorkflowView()
	{
		SAWViewerPane view = new SAWViewerPane();
		//TODO replace NULL second argument for the String URI of the concept to highlight in the SAW
		view.setWorkflow(ViewsModel.getInstance().getWorkflowURI(), null);
		setWorkflowTab(view);
		this.setActiveIndex(WORKFLOW_TAB);
		ProbeIt.getInstance().getFrame().repaint();
	}
	
	public void buildJustificationView(ViewsManager.JustificationStyle style)
	{
		JComponent view;
		if(style == ViewsManager.JustificationStyle.questions)
			view = new QuestionsView();
		else
			view = new JustificationView();

		setJustificationTab(view);
		this.setActiveIndex(GLOBAL_JUSTIFICATION_TAB);
		ProbeIt.getInstance().getFrame().repaint();
	}

	public void buildProvenanceView()
	{	
		String stringView = new Loader(ProbeIt.getInstance().isRemote()).getNodeSetDetailsString(ViewsModel.getInstance().getLocalViewNodesetURI());

		JPanel view = SwingView.getNodeSetDetailsSwing(stringView, ViewsModel.getInstance().getLocalViewNodesetURI());
		setProvenanceTab(view);
		this.setActiveIndex(LOCAL_JUSTIFICATION_TAB);
	}
	
/************************ ADD AND REMOVE VIEWS TO TABS ******************************/
	
	public void setQueryTab(JComponent view)
	{
		resetQueryTab();
		queryTab.add(view);
		tp.setEnabledAt(QUERY_TAB, true);
	}

	private void setAnswerTab(JComponent view)
	{
		resetAnswerTab();
		answerTab.add(view);
		tp.setEnabledAt(ANSWER_TAB, true);
	}

	private void setJustificationTab(JComponent view)
	{
		resetJustificationTab();
		justificationTab.add(view);
		tp.setEnabledAt(GLOBAL_JUSTIFICATION_TAB, true);
	}

	private void setWorkflowTab(JComponent view)
	{
		resetWorkflowTab();
		workflowTab.add(view);
		tp.setEnabledAt(WORKFLOW_TAB, true);
	}
	
	private void setProvenanceTab(JComponent view)
	{
		resetProvenanceTab();
		provenanceTab.add(view);
		tp.setEnabledAt(LOCAL_JUSTIFICATION_TAB, true);
	}
	
	public void resetQueryTab()
	{
		queryTab.removeAll();
		tp.setEnabledAt(QUERY_TAB, false);
	}

	public void resetAnswerTab()
	{
		answerTab.removeAll();
		tp.setEnabledAt(ANSWER_TAB, false);
	}

	public void resetWorkflowTab()
	{
		workflowTab.removeAll();
		tp.setEnabledAt(WORKFLOW_TAB, false);
	}
	
	public void resetJustificationTab()
	{
		justificationTab.removeAll();
		tp.setEnabledAt(GLOBAL_JUSTIFICATION_TAB, false);
	}

	public void resetProvenanceTab()
	{
		provenanceTab.removeAll();
		tp.setEnabledAt(LOCAL_JUSTIFICATION_TAB, false);
	}
	
	private class TabViewChanged implements javax.swing.event.ChangeListener
	{
		public void stateChanged(ChangeEvent e)
		{
			//System.out.println("state changed...........................");

			/*if(tp.isEnabledAt(ANSWER_TAB))
			{
				if(ViewsModel.getInstance().hasAnswer())
				{
					System.out.println("highlighting node...");

					JustificationView.getInstance().highlightNodeSet(ViewsModel.getInstance().getLocalViewNodesetURI());
				}
			} */
		}	
	}
}
