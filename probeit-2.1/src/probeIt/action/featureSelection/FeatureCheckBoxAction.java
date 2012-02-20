package probeIt.action.featureSelection;
import probeIt.action.ProbeItGenericAction;
public abstract class FeatureCheckBoxAction extends ProbeItGenericAction
{	
	public FeatureCheckBoxAction(String name)
	{super(name);}
	
	public abstract boolean shouldBeSelected();
	
}
