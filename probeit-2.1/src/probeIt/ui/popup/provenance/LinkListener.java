package probeIt.ui.popup.provenance;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import probeIt.ProbeIt;
import pml.PMLNode;
import probeIt.ui.local.HTMLView;

public class LinkListener implements HyperlinkListener
{
	private static final String browseNS = "BrowseNodeSet?uri=";
	private static final String openProv = "javascript: void openProvenance('";
	private static final String expandConc = "javascript:toggle('concText";
	private static final String expandAsserts = "javascript:toggle('Text";
	private static final String expandHelpOf = "javascript:toggle('Textt";
	
	public static HTMLView view;
	public LinkListener(HTMLView v)
	{view = v;}
	
	private String decoder(String encoded)
	{
		System.out.println("encoded URL: " + encoded);
		try
		{
			return URLDecoder.decode(encoded, "UTF-8");
		}
		catch(UnsupportedEncodingException uee)
		{System.out.println(uee);}
		return null;
	}
	
	/*
	private Node findNode(Node node, String matchURI)
	{
		Node matchNode = null;
		if(node.getURI().equalsIgnoreCase(matchURI))
		{
			return node;
		}
		else if(node.isLeaf())
		{
			return null;
		}
		else
		{
			for(int i = 0; i < node.getChildren().length; i ++)
			{
				matchNode = findNode(node.getChildren()[i], matchURI);
				if(matchNode != null)
				{
					return matchNode;
				}
			}
			return matchNode;
		}
	}*/

	public void hyperlinkUpdate(HyperlinkEvent event)
	{
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		{
			String linkText = event.getDescription();
			System.out.println("HyperLink command " + linkText);
			
			if(linkText.startsWith(browseNS))
			{
				linkText = linkText.substring(browseNS.length(), linkText.length());
				String decodedURL = decoder(linkText);

				view.updateText(decodedURL);	
			}
			else if(linkText.startsWith(expandConc))
			{
				view.getLocalViewHTML().setExpandConclusionMetadata(!view.getLocalViewHTML().expanded_conclusionMetadata);
				view.updateState();
			}
			else if(linkText.startsWith(this.expandHelpOf))
			{
				view.getLocalViewHTML().setExpandWithHelpOf(!view.getLocalViewHTML().expanded_inferWithHelpOf);
				view.updateState();
			}
			else if(linkText.startsWith(expandAsserts))
			{
				view.getLocalViewHTML().setExpandJustificationAssertions(!view.getLocalViewHTML().expanded_justificationAssertions);
				view.updateState();
			}
			else if(linkText.startsWith(openProv))
			{
				linkText = linkText.substring(openProv.length(), linkText.length() - 3);
				String decodedURL = decoder(linkText);
				PopupHTMLView view = new PopupHTMLView(decodedURL, true);
			}
			else
			{
				PopupHTMLView view = new PopupHTMLView(linkText, false);
			}
		}
	}
}
