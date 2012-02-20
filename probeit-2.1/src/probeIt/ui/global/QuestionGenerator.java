package probeIt.ui.global;
import pml.PMLNode;
import pml.impl.serializable.PMLInferenceStep;

public class QuestionGenerator
{
	PMLNode nodeset;
	
	String tjPrefix = "trafficJam";
	String taPrefix = "trafficAccident";
	String wdPrefix = "weatherDelay";

	//boolean for justifications for anomalies
	boolean hasTrafficJam, hasTrafficAccident, hasWeatherDelay;
	
	//boolean for anomalies
	boolean hasTimeDeviation, hasRouteDeviation;
	
	String tdPrefix = "hasTimeDeviation";
	String rdPrefix = "hasRouteDeviation";
	
	public QuestionGenerator(PMLNode node)
	{
		nodeset = node;
	}
	
	public Question[] generateQuestions()
	{
		setup(nodeset);
		return getQuestions(nodeset);
	}
	
	
	private void setup(PMLNode aNode)
	{
		if(aNode.getNumberOfInferenceSteps() <= 0)
		{
			System.out.println("no inference steps........");
		}
		else
		{
			PMLInferenceStep anIS;
			String childQuestions = "";
			PMLNode[] children;
			
			for(int i = 0; i < aNode.getNumberOfInferenceSteps(); i ++)
			{
				anIS = aNode.getInferenceSteps().get(i);
				if(anIS.getAntecedents() != null && anIS.getAntecedents().length > 0)
				{
					for(int j = 0; j < anIS.getAntecedents().length; j ++)
					{
						setup(anIS.getAntecedents()[j]);
					}
				}
			}
			
			//check if this conclusion has to do with anomalies
			String text = aNode.getConclusion().getStringConclusion().trim();
			if(text.startsWith(this.taPrefix))
				hasTrafficAccident = true;
			else if(text.startsWith(this.wdPrefix))
				hasWeatherDelay = true;
			else if(text.startsWith(this.tjPrefix))
				hasTrafficJam = true;
			
			//check if this conclusion has to do with justifications
			if(text.startsWith(this.tdPrefix))
				hasTimeDeviation = true;
			else if(text.startsWith(this.rdPrefix))
				hasRouteDeviation = true;
	
		}
	}
	
	private Question[] getQuestions(PMLNode node)
	{
		String text = node.getConclusion().getStringConclusion().trim();
		Question[] questions = null;
		
		if(text.startsWith("hasThreat"))
		{
			String question = "Given that there were no traffic accidents, traffic jams, or weather delays affecting your trip, what caused you";
			
			if(hasTimeDeviation && hasRouteDeviation)
			{
				String q1 = question + " to be late?";
				String q2 = question + "r route deviation?";
				
				questions = new Question[2];
				questions[0] = new Question(q1);
				questions[1] = new Question(q2);
			}
			else if(hasTimeDeviation)
			{
				String q1 = question + " to be late?";
				
				questions = new Question[1];
				questions[0] = new Question(q1);
			}
			else if(hasRouteDeviation)
			{
				String q2 = question + "r route deviation?";
				
				questions = new Question[1];
				questions[0] = new Question(q2);
			}
			
		}
		else if(text.startsWith("hasJustifiedThreat"))
		{
			String question = "I have a report of the following events: ";
			if(hasTrafficAccident)
				question += "traffic accident, ";
			if(hasTrafficJam)
				question += "traffic jam,";
			if(hasWeatherDelay)
				question += "bad weather";
			
			question += ". Which event caused you";
			
			if(hasTimeDeviation && hasRouteDeviation)
			{
				String q1 = question + " to be late?";
				String q2 = question + "r route deviation?";
				
				questions = new Question[2];
				questions[0] = new Question(q1);
				questions[1] = new Question(q2);
			}
			else if(hasTimeDeviation)
			{
				String q1 = question + " to be late?";
				
				questions = new Question[1];
				questions[0] = new Question(q1);
			}
			else if(hasRouteDeviation)
			{
				String q2 = question + "r route deviation?";
				
				questions = new Question[1];
				questions[0] = new Question(q2);
			}
			
		}
		else if(text.startsWith("hasNoThreat"))
		{
			
			String q = "Your information checks out!!!";
			
			questions = new Question[1];
			questions[0] = new Question(q);
		}
		return questions;
	}
}
