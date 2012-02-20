package probeIt.ui.model;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import probeIt.ProbeIt;
import probeIt.action.ProbeItActions;
import pml.PMLNode;
import pml.PMLQuery;
import pml.loading.Loader;
import probeIt.ui.workers.SwingWorker;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.WindowApplication;

import java.awt.Frame;

public class PMLLoader extends SwingWorker implements ActionListener
{
	int typeLoading;
	final static int NOTHING = 0;
	final static int QUERY_LOADING = 1;
	final static int NODE_LOADING = 2;
	final static int JUSTIFICATION_LOADING = 3;

	PMLQuery _query;
	PMLNode _node;
	
	//int lastOperation = NOTHING;
	Frame ui = ProbeIt.getInstance().getFrame();
	ViewsManager views = ViewsManager.getInstance();
	JDialog jd = null;
	JLabel lb;
	String _URI;
	JButton cancelButton = new JButton("Cancel");
	boolean forcedEnd = false;
	private String _id;
	//boolean startWithLocalView;
	boolean loadAnswerOnly;

	public PMLLoader(String _uri, String id)
	{
		super();
		typeLoading = NOTHING;
		_id = id;
		init(_uri);
	}
	
	public void setLoadAnswerOnly(boolean loadAnswer)
	{
		loadAnswerOnly = loadAnswer;
	}
	
	/*public void setStartWithLocalView()
	{
		startWithLocalView = true;
	}*/

	public PMLLoader(String _uri)
	{
		super();
		_id = Loader.DEFAULT_ID;
		init(_uri);
	}

	private void init(String _uri)
	{
		_URI = _uri;
		
		/*
		jd = new JDialog(ui, "Status");
		JPanel sp = new JPanel();
		lb = new JLabel("loading PML document ...");
		lb.setVisible(true);
		sp.add(lb);
		sp.setVisible(true);
		JProgressBar progress = new JProgressBar();
		progress.setPreferredSize(new Dimension(240, 20));
		progress.setMinimum(0);
		progress.setIndeterminate(true);
		progress.setValue(0);
		progress.setBounds(20, 35, 240, 20);
		sp.add(progress);
		cancelButton.addActionListener(this);
		sp.add(cancelButton);
		jd.getContentPane().add(sp);
		jd.setSize(new Dimension(310, 120));
		jd.setBounds(500, 500, 310, 120);
		// jd.setDefaultLookAndFeelDecorated(true);
		jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jd.setVisible(true);*/
		
		WindowApplication.getInstance().enableProgressBar("Loading PML Document ");
	}

	public Object construct()
	{
		Loader loader = new Loader(ProbeIt.getInstance().isRemote());

		/*
		 *   Loads a PML Node Set
		 */
		if (loader.isNode(_URI))
		{
			try
			{
				if (loadAnswerOnly)
				{
					System.out.println("Loading answerOnly");
					//lb.setText("Loading justification ...");
					WindowApplication.getInstance().enableProgressBar("Loading nodeset ");
					_node = loader.loadNode(_URI, _id);
					typeLoading = NODE_LOADING;
				}
				else
				{
					// This test if the avatar mode is on
					//if (ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_A()) {
						// the recursion to load a justification stops at nodes of type SP
						//_node = loader.loadJustificationSP(_URI, _id);  
					//} else {
						_node = loader.loadJustification(_URI, _id);
					//}
					typeLoading = JUSTIFICATION_LOADING;
				}
				return _node;
			} catch (Exception _e)
			{
				_e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error reading " + _URI,
						"Reading Node Set URI", JOptionPane.WARNING_MESSAGE);
			}
			return null;
		/*
		 *   Loads a PML Query
		 */
		} else if (loader.isQuery(_URI))
		{
			try
			{
				//lb.setText("Loading query ...");
				WindowApplication.getInstance().enableProgressBar("Loading query ");
				_query = loader.loadQuery(_URI);
				//lastOperation = QUERY_LOADING;
				System.out.println("Query loaded");
				typeLoading = QUERY_LOADING;
				return _query;
			} catch (Exception _e)
			{
				_e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error reading " + _URI,
						"Reading URI", JOptionPane.WARNING_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Error reading " + _URI,
					"Reading URI", JOptionPane.WARNING_MESSAGE);			
		}
		return null;
	}

	public void finished()
	{		
		switch (typeLoading) {
		case QUERY_LOADING: 
			ViewsModel.getInstance().setQuery(_query);
			//((ProbeItView)ViewsManager.getInstance().getViewPane()).buildQueryView();
			break;
		case NODE_LOADING: 	
			ViewsModel.getInstance().setAnswer(_node);
			//((ProbeItView)ViewsManager.getInstance().getViewPane()).buildAnswerView();
			break;
		case JUSTIFICATION_LOADING: 
			ViewsModel.getInstance().setJustification(_node);
			//((ProbeItView)ViewsManager.getInstance().getViewPane()).buildJustificationView(ViewsManager.getInstance().justStyle);
			break;
		}
		WindowApplication.getInstance().disableProgressBar();
	
		if (ViewsModel.getInstance().hasJustification())
		{
			String conc = ViewsModel.getInstance().getJustification().getConclusion().getStringConclusion().trim();
		
			if (conc.startsWith("hasThreat") || conc.startsWith("hasNoThreat") || conc.startsWith("hasJustifiedThreat"))
			{
				System.out.println("a dhs proof");
				ProbeIt.getInstance().getEmbeddedProbeIt().setCurrentJustificationStyle(ViewsManager.JustificationStyle.questions);
			}
		}
		
		//jd.dispose();
		/*if (!forcedEnd)
		{
			if (lastOperation == NODE_SET_LOADING && ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_L())
			{
				if (ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_A()) {
					views.setJustification(_node, false);
				} else {
					views.setJustification(_node, true);
				}
					//views.setProvenance(_node.getURI());
			}
			else if(lastOperation == NODE_SET_LOADING && !ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_L())
			{
				if (ProbeIt.getInstance().getEmbeddedProbeIt().isEnabledFeature_A()) {
					views.setJustification(_node, false);
				} else {
					views.setJustification(_node, true);
				}
			}
			else if(lastOperation ==  ANSWER_LOADING)
			{
				views.setAnswer(_node);
			}
			else if (lastOperation == QUERY_LOADING)
				views.setQuery(_query);
		} */
		ProbeItActions.updateAllEnabled();
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource().equals(cancelButton))
		{
			this.interrupt();
			forcedEnd = true;
			jd.dispose();
			ProbeIt.getInstance().getWindow().resetUI();
		}
		return;
	}

}
