package probeIt.ui;

import javax.swing.JPanel;
import pml.PMLNode;
import pml.PMLQuery;
import probeIt.ProbeIt;
import probeIt.ui.model.ViewsModel;

public class ViewsManager {	
	
	static ViewsManager instance;
	
	boolean feature_R;
	boolean feature_S;
	boolean feature_T;
	boolean feature_J;
	boolean feature_MV;
	boolean feature_P;
	boolean feature_L;
	boolean feature_A;
	boolean feature_W;

	ProbeItView viewPane;
	ViewsModel model;
	
	private ViewsManager()
	{
		//System.out.println("[ViewsManager construct]");		
		viewPane = new ProbeItView();
		model = ViewsModel.getInstance();
	}
	
	public static void setInstance(ViewsManager view)
	{
		//System.out.println("[ViewsManager.setInstance()]");		
		instance = view;
	}
	
	public static ViewsManager getInstance()
	{
		//System.out.println("[ViewsManager.getInstance()]");		
		if(instance == null)
			instance = new ViewsManager();
		
		return instance;
	}
	
	public enum JustificationStyle {dag,tree,forest,questions};
	
	//set initially to dag
	public JustificationStyle justStyle = JustificationStyle.dag;

	boolean isShowingSources;

	public boolean isForest()
	{
		if(justStyle == JustificationStyle.forest)
			return true;
		return false;
	}
	
	public boolean isDAGStyle()
	{
		if(justStyle == JustificationStyle.dag)
			return true;
		return false;
	}

	public boolean isQuestionsStyle()
	{
		if(justStyle == JustificationStyle.questions)
			return true;
		return false;
	}
	
	public boolean isTreeStyle()
	{
		if(justStyle == JustificationStyle.tree)
			return true;
		return false;
	}
	
	public void setIsShowingSources(boolean show)
	{
		isShowingSources = show;
	}
	
	public boolean isShowingSources()
	{
		return isShowingSources;
	}
	
	public void setCurrentJustificationStyle(JustificationStyle style)
	{
		justStyle = style;
	}
	
	public JustificationStyle getJustificationStyle()
	{
		return justStyle;
	}
		
	public void addToolbar(JPanel toolbar)
	{
		viewPane.addToolbar(toolbar);
	}
	
	public void removeToolbar()
	{
		viewPane.removeToolbar();
	}
	
	public JPanel getViewPane()
	{
		/*if(model.hasContext())
			updatePane();*/
		
		return viewPane;
	}
	
	public void enableAllFeatures()
	{
		enableFeature_R();
		enableFeature_S();
		enableFeature_T();
		enableFeature_J();
		enableFeature_MV();
		enableFeature_P();
	}
	
	public void disableAllFeatures()
	{
		//disableFeature_R();
		disableFeature_S();
		disableFeature_T();
		disableFeature_J();
		disableFeature_MV();
		disableFeature_P();
	}
	
	public void enableFeature_R()
	{
		feature_R=true;
		//viewPane.queryViewDirtied();
	}
	
	public void enableFeature_S()
	{
		enableFeature_R();
		feature_S=true;
		//viewPane.queryViewDirtied();
	}
	
	public void enableFeature_T()
	{
		enableFeature_R();
		feature_T=true;
	}
	
	public void enableFeature_J()
	{
		feature_J=true;
	}
	
	public void enableFeature_MV()
	{
		enableFeature_J();
		feature_MV=true;
	}
	
	public void enableFeature_P()
	{
		enableFeature_J();
		feature_P=true;
	}
	
	public void enableFeature_W()
	{
		feature_W=true;
	}
	
	public void disableFeature_W()
	{
		feature_W=false;
	}
	
	public void enableFeature_L()
	{
		feature_L=true;
	}
	
	public void disableFeature_L()
	{
		feature_L=false;
	}
	
	public void enableFeature_A()
	{
		feature_A=true;
		//viewPane.buildQueryView();
	}
	
	public void disableFeature_A()
	{
		feature_A=false;
		//viewPane.buildQueryView();
	}

	public void disableFeature_R()
	{
		//viewPane.queryViewDirtied();
		feature_R = false;
		disableFeature_S();
		disableFeature_T();
	}
	
	public void disableFeature_S()
	{
		feature_S=false;
	}

	public void disableFeature_T()
	{
		feature_T=false;
	}
	
	public void disableFeature_J()
	{
		disableFeature_MV();
		disableFeature_P();
		feature_J=false;
		model.newAnswerJustificationContext();
	}
	
	public void disableFeature_MV()
	{
		feature_MV=false;
	}
	
	public void disableFeature_P()
	{
		feature_P=false;
	}
	
	public boolean isEnabledFeature_L()
	{
		return feature_L;
	}
	
	public boolean isEnabledFeature_R()
	{
		return feature_R;
	}

	public boolean isEnabledFeature_S()
	{
		return feature_S;
	}
	
	public boolean isEnabledFeature_T()
	{
		return feature_T;
	}
	
	public boolean isEnabledFeature_J()
	{
		return feature_J;
	}
	
	public boolean isEnabledFeature_MV()
	{
		return feature_MV;
	}
	
	public boolean isEnabledFeature_P()
	{
		return feature_P;
	}
	
	public boolean isEnabledFeature_A()
	{
		return feature_A;
	}
	
	public boolean isEnabledFeature_W()
	{
		return feature_W;
	}
	
	public boolean isEnabled()
	{
		return isEnabledFeature_R() || isEnabledFeature_J();
	}
	
}
