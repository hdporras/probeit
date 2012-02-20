package probeIt.action.featureSelection;
import probeIt.action.ProbeItGenericAction;
import java.util.ArrayList;
import javax.swing.JCheckBoxMenuItem;
public class FeatureCheckBoxGroup
{
	private static ArrayList featureCheckBoxes;
	
	
	public static void addFeatureCheckBox(JCheckBoxMenuItem mi)
	{
		if(featureCheckBoxes == null)
			featureCheckBoxes = new ArrayList();
		featureCheckBoxes.add(mi);
	}
	
	public static void updateButtons()
	{
		if(featureCheckBoxes != null)
		{
			for(int i = 0; i < featureCheckBoxes.size(); i ++)
			{
				JCheckBoxMenuItem box = (JCheckBoxMenuItem)featureCheckBoxes.get(i);
				box.setSelected(((FeatureCheckBoxAction)box.getAction()).shouldBeSelected());
			}
		}
	}
}
