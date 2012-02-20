package probeIt.util;

import java.util.ArrayList;
import gmt.GMTToolkit;
import pml.*;
import pml.loading.Loader;
import gravityMapScenario.gravityDataset.*;

public class TrustValueRetrieval
{
	PMLNode dagRoot;
	ArrayList<String> sourceDatasets;
	ArrayList<String> sourceNames;
	int count;
	
	public TrustValueRetrieval(PMLNode aNode, boolean remote)
	{
		Loader factory = new Loader(remote);
		dagRoot = factory.loadJustification(aNode.getURI(), aNode.getID());
		count = 0;
		sourceDatasets = new ArrayList<String>();
		sourceNames = new ArrayList<String>();
		populateTrustDataset(dagRoot);
	}

	public Dataset getTrustValueDataset()
	{
		Dataset[] datasets = new Dataset[sourceDatasets.size()];
		for(int i = 0; i < sourceDatasets.size(); i ++)
		{	
			datasets[i] = new Dataset((String)sourceDatasets.get(i), true);
		}
		
		int lagCounter = 0;
		
		GMTToolkit tk = new GMTToolkit(GMTToolkit.ConnectionType.WebService); 
		double[] trustValues = tk.getTrustValues(getArray(sourceNames));
		
		
		for(int i = 0; i < datasets.length; i ++)
		{
			double trustValue = trustValues[i];
		
			if(trustValue < 0)
			{
				lagCounter++;
				datasets[i].setNaNValue(2);
			}
			
			else
			{
				datasets[i].setColumnValue(2, trustValue * 10000000);
			}
		}
		if((datasets.length - lagCounter) <= 1)
			return null;
		return merge(datasets);
		
	}
	
	private static Dataset merge(Dataset[] datasets)
	{
		Dataset finalDataset = datasets[0];
					
		for(int i = 1; i < datasets.length; i ++)
		{
			if(datasets[i] != null)
				finalDataset = finalDataset.append(datasets[i]);
		}
		return finalDataset;
	}
	
	private void populateTrustDataset(PMLNode aNode)
	{
		
		if(aNode.getConclusion().getConclusionClassifier().isGravityDataset() && aNode.getCurrentlySelectedInferenceStep().isLeaf())
		{
			String dataset = aNode.getConclusion().getStringConclusion();
			
			sourceDatasets.add(dataset);
			String name = aNode.getCurrentlySelectedInferenceStep().getSourceName();
			if(name != null && name != "")
			{
				System.out.println("adding " + name);
				sourceNames.add(name);
			}
			else
			{
				System.out.println("couldn't add name..was null or empty");
				sourceNames.add("x");
			}
		}
		else if(!aNode.getCurrentlySelectedInferenceStep().isLeaf())
		{
			PMLNode aChild;
			for(int i = 0; i < aNode.getCurrentlySelectedInferenceStep().getAntecedents().length; i ++)
			{
				aChild = aNode.getCurrentlySelectedInferenceStep().getAntecedents()[i];
				populateTrustDataset(aChild);
			}
		}
	}
	
	private static String[] getArray(ArrayList<String> list)
	{
		String[] returnVals = new String[list.size()];
		for(int i = 0; i < returnVals.length; i ++)
		{
			returnVals[i] = list.get(i);
		}
		return returnVals;
	}
}
