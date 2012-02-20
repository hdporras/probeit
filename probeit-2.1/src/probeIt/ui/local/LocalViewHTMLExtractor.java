package probeIt.ui.local;

public class LocalViewHTMLExtractor
{
	public boolean expanded_conclusionMetadata;
	public boolean expanded_justificationAssertions;
	public boolean expanded_inferWithHelpOf;
	boolean isFinalConclusion;
	
	String headerAndConclusion;
	String conclusionMetadata;
	String justification;
	String justificationAssertions;
	String usedToInfer;
	String usedToInferWithHelp;
	String toFinallyConclude;
	String isConclusion;
	
	String restHTML;
	
	public LocalViewHTMLExtractor(String localViewHTML)
	{
		restHTML = localViewHTML;
		
		this.extractSections();
		this.separateConclusionFromMetadata();
		this.separateJustificationFromAssertions();
		this.separateInferFromHelpOf();
		
	}
	
	private void extractSections()
	{
		String[] parts = restHTML.split("<hr>");
		if(parts.length == 4)
		{
			this.isFinalConclusion = false;
			
			this.headerAndConclusion = parts[0];
			this.justification = parts[1];
			this.usedToInfer = parts[2];
			this.toFinallyConclude = parts[3];
		}
		else if(parts.length == 3)
		{
			this.isFinalConclusion = true;
			
			this.headerAndConclusion = parts[0];
			this.justification = parts[1];
			this.isConclusion = parts[2];
		}
	}
	
	private void separateConclusionFromMetadata()
	{
		String[] parts = headerAndConclusion.split("<div id=\"conc");
		
		headerAndConclusion = parts[0];
		conclusionMetadata = "<div id=\"conc" + parts[1];
	}
	
	private void separateJustificationFromAssertions()
	{	
		String[] parts = justification.split("<br><div");
		
		if(parts.length == 2)
		{
			this.justification = parts[0];
			this.justificationAssertions = "<br><div" + parts[1];
		}
	}
	
	private void separateInferFromHelpOf()
	{
		if(usedToInfer != null)
		{
			String[] parts = this.usedToInfer.split("none\"><ol><li>");
		
			if(parts.length == 2)
			{
				this.usedToInfer = parts[0] + "none\">";
				this.usedToInferWithHelp = "<ol><li>" + parts[1];
			}
		}
		
	}

	public void setExpandWithHelpOf(boolean expand)
	{
		this.expanded_inferWithHelpOf = expand;
	}
	public void setExpandConclusionMetadata(boolean expand)
	{
		this.expanded_conclusionMetadata = expand;
	}
	public void setExpandJustificationAssertions(boolean expand)
	{
		this.expanded_justificationAssertions = expand;
	}
	public String getHTML()
	{
		String result = "";
	
		if(this.expanded_conclusionMetadata)
		{
			result += this.headerAndConclusion;
			result += this.conclusionMetadata;
		}
		else
			result += this.headerAndConclusion;
		
	
		if(this.expanded_justificationAssertions)
		{
			result += "<hr>" + this.justification;
			result += this.justificationAssertions;
		}
		else
			result += "<hr>" + this.justification + "</div></li></ol>";
		
	
		if(this.isFinalConclusion)
			result+= "<hr>" + this.isConclusion;
		
		else
		{
			if(this.expanded_inferWithHelpOf)
			{
				result+= "<hr>" + this.usedToInfer;
				result+= this.usedToInferWithHelp;
				result+= "<hr>" + this.toFinallyConclude;
			}
			else
			{
				result+= "<hr>" + this.usedToInfer + "</li></ol>";
				result+= "<hr>" + this.toFinallyConclude;
			}
		}
		
		return result;
	}
}
