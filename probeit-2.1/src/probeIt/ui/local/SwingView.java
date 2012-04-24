package probeIt.ui.local;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import probeIt.ProbeIt;
import pml.loading.Loader;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import probeIt.ui.ProbeItView;
import probeIt.ui.Toolbar;
import probeIt.ui.ViewsManager;
import probeIt.ui.local.swing.*;
import probeIt.ui.model.ViewsModel;
import probeIt.viewerFramework.DAGContentFactory;
import probeIt.viewerFramework.viewers.imaging.ProbeitImage;
import pml.PMLNode;

public class SwingView 
{
	//going to have to go through PML-API after done make sure I'm not missing any important checks (if-else's) that change the style/content of the information displayed (might not be checking all booleans).

	//-need to change package visible only components in PML-API in order to get:
	// 1. second part of conclusion metadata.

	///!!!!!!!
	static final String browseProofNodeStr = "BrowseNodeSet?uri=";
	static final String browseNodeSetStr = "BrowseNodeSet?uri=";
	static final String assumptionRuleURI = "http://inference-web.org/registry/DPR/Assumption.owl#Assumption";
	static String currentConclusionURI;

	public static JPanel getNodeSetDetailsSwing(String strRep, String uri)
	{
		currentConclusionURI = uri;

		ExpandingPanels test = new ExpandingPanels(new JPanel(), new JPanel(), new JPanel(), new JPanel());
		JPanel swingPanel = new JPanel(new BorderLayout());

		if (strRep != null)
		{
			String concStr = strRep.substring(0, strRep.indexOf(" endConclusion >>>)))! "));
			JPanel concP = getNodeSetConclusion(concStr);
			strRep = strRep.substring(strRep.indexOf(" endConclusion >>>)))! ") + " endConclusion >>>)))! ".length() );

			String isStr = strRep.substring(0, strRep.indexOf(" endNSInfStep >>>)))! "));		
			JPanel isP = getNodeSetInferenceSteps(isStr);
			strRep = strRep.substring(strRep.indexOf(" endNSInfStep >>>)))! ") + " endNSInfStep >>>)))! ".length() ); 

			System.out.println("BEFORE UsedToInfer: "+strRep);
			
			String utiStr = strRep.substring(0, strRep.indexOf(" endUsedToInfer >>>)))! "));
			JPanel utiP = getUsedToInfer(utiStr);
			strRep = strRep.substring(strRep.indexOf(" endUsedToInfer >>>)))! ") + " endUsedToInfer >>>)))! ".length() );

			System.out.println(strRep);
			String finalConcStr = strRep.substring(0, strRep.indexOf(" endFinalConclusion >>>)))! "));
			JPanel finalConcP = getFinalConclusion(finalConcStr);

			System.out.println("Done!!");
			test = new ExpandingPanels(concP, isP, utiP, finalConcP);
		}


		Toolbar toolbar=new Toolbar(Toolbar.CanvasType.provenanceView);
		toolbar.setup();

		JScrollPane scrollP = new JScrollPane(test.getComponent());
		scrollP.getVerticalScrollBar().setUnitIncrement(30);

		swingPanel.add(toolbar, BorderLayout.NORTH);				
		swingPanel.add(scrollP, BorderLayout.CENTER);

		test.toggleVisibility(test.aps[0]);

		return swingPanel;
	}


	public static JPanel getNodeSetConclusion(String str)
	{
		//P1 (Conclusion)
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.setBorder(BorderFactory.createEmptyBorder(5,10,0,10));

		JPanel concP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		concP.setBorder(BorderFactory.createEmptyBorder(5,10,10,10));

		//Adding Conclusion Image or text. 
		DAGContentFactory selector = new DAGContentFactory();

		/* should use factory instead 
		Builder b = new Builder();
		PMLNode node = b.loadNode(currentConclusionURI, null);//ProbeItViewsConfiguration.getInstance().getLocalViewNodesetURI(), null);
		*/
		
		Loader factory = new Loader(ProbeIt.getInstance().isRemote());
		PMLNode node = factory.loadNode(currentConclusionURI, null);
		

		ProbeitImage image = selector.getImage(node.getConclusion(), 150, 100);//(NodeSetGraphic.HEIGHT * 5/7));

		if(image != null)
		{
			ImageIcon imageIcon = new ImageIcon(image.getImage());
			JLabel icon = new JLabel(imageIcon);

			icon.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent evt) 
				{
					if(evt.getButton() == MouseEvent.BUTTON1)
					{
						if(currentConclusionURI != null)
						{
							ViewsModel.getInstance().setAnswer(currentConclusionURI);
							//update NodeSelectionInteractor._lastPointedFigure to the linked NodeSet. Will have to check URI's in tree to find correct one, unless another method?
						}
						else
							System.out.println("PMLNode (link) is NULL.");
					}
				}
			});
			
			concP.add(icon);
			//PMLNode nodeset, int imgSize
		}
		else
		{
			JTextArea text = new JTextArea();
			text.setEditable(false);
			text.setBackground(concP.getBackground());
			//text.setFont(text.getFont().deriveFont((float)13.0));
			String concLbl = str.substring(0, str.indexOf(" endConcLbl ))) "));
			//System.out.println("Conclusion: "+concLbl);
			 String concText;
			 
			 try
			 {
				 if(node.getConclusion().isByReference())
					 concText = probeIt.util.GetURLContents.downloadText(node.getConclusion().getHasURL());
				 else
					 concText = node.getConclusion().getStringConclusion();
				 
				 text.setText(probeIt.util.LineFormatter.formatText(concText, 70, 10));//"~ hates(butler, butler)  |  ~ hates(butler, charles)");
				 //text.setBorder(new EtchedBorder());
				 
				 text.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent evt) 
						{
							if(evt.getButton() == MouseEvent.BUTTON1)
							{
								if(currentConclusionURI != null)
								{
									ViewsModel.getInstance().setAnswer(currentConclusionURI);
									//update NodeSelectionInteractor._lastPointedFigure to the linked NodeSet. Will have to check URI's in tree to find correct one, unless another method?
								}
								else
									System.out.println("PMLNode (link) is NULL.");
							}
						}
					});
				 
				 
				 concP.add(text);
			 }catch(Exception e)
			 {e.printStackTrace();}
		}

		//Conclusion metadata
		JPanel metadataBP = new JPanel();
		metadataBP.setLayout(new BoxLayout(metadataBP, BoxLayout.Y_AXIS));
		metadataBP.setBorder(BorderFactory.createEmptyBorder(3,10,3,10));//new BevelBorder(BevelBorder.RAISED));

		JPanel metadataP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		metadataP.setBorder(new BevelBorder(BevelBorder.RAISED));
		//JLabel metaLbl = new JLabel("No additional metadata");

		String meta = str.substring(str.indexOf(" endConcLbl ))) ") + " endConcLbl ))) ".length() , str.indexOf(" endMetaLbl ))) "));
		//JEditorPane metahtml = new JEditorPane("text/html", meta);

		HTMLView metaview = new HTMLView(meta, false, false, false, false);

		//JLabel metaLbl = new JLabel(getConclusionMetadata(ns));
		metadataP.add(metaview);//metaLbl);

		JPanel metaBtnP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ShowButton metaBtn = new ShowButton(metadataP, "show metadata", "hide metadata");
		metaBtn.setBackground(Color.gray);
		metaBtnP.add(metaBtn);

		metadataBP.add(metaBtnP);
		metadataBP.add(metadataP);
		metadataBP.setBackground(new Color(200,200,200));
		metaBtnP.setBackground(new Color(200,200,200));

		p1.add(concP);
		p1.add(metadataBP);


		return p1;
	}

	/*	public static String getConclusionLabel(IWNodeSet ns)
	{	  	
		String rendered = "";
		SentenceRenderer renderer;
		if (ns != null){
			renderer = new SentenceRenderer(ns, "English");
			//rendered = renderer.render();

			//rendered = rendered.substring(5, rendered.length()-6);
			rendered = renderer.getRawString();
			System.out.println("Conclusion Name: "+rendered);
		}
		return rendered;
	}*/

	/*	private static String getConclusionMetadata (IWNodeSet ns) 
	{
		String rawStr = null;
		SentenceRenderer renderer;
		String conclusionStr = null;

		if (ns != null)
		{
			renderer = new SentenceRenderer(ns, "English");
			rawStr = renderer.getRawString();

			if ((rawStr != null && rawStr.trim().length()>0)) //as long as rawStr is not empty
			{
				conclusionStr = "";
				String langLink = "<b>IW-Text-Printer</b>"; // default for language with no renderer

				conclusionStr += "The conclusion is renderred by "+langLink;				
				conclusionStr += " from:<br>"+rawStr;
			}
			else
			{
				//...... what if rawStr is empty (no conclusion label)
			}
		}


		return conclusionStr;
	}*/


//	Justified By

	public static JPanel getNodeSetInferenceSteps(String str)
	{
		System.out.println("--- --- getNodeSetInferenceSteps (View) --- ---");
		System.out.println(str);		

		JPanel jbP = new JPanel();
		jbP.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		jbP.setLayout(new BoxLayout(jbP, BoxLayout.Y_AXIS));

		if (!str.equalsIgnoreCase(" NO INFERENCE STEPS "))
		{
			for (int i=1; !str.startsWith(" endInferenceSteps >>>) "); i++)
			{


				JPanel isP = new JPanel();//new FlowLayout(FlowLayout.LEFT));
				//isP.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
				isP.setLayout(new BoxLayout(isP, BoxLayout.X_AXIS));

				JLabel numL = new JLabel(i+". ");
				isP.add(numL);

				String oneStep = str.substring(0, str.indexOf(" endONESTEP ))) "));
				isP.add(getOneInferenceStep(oneStep));

				jbP.add(isP);

				str = str.substring(str.indexOf(" endONESTEP ))) ") + " endONESTEP ))) ".length());
			} 						
		}  	
		return jbP;
	}

	public static JPanel getOneInferenceStep(String str)
	{
		System.out.println("--- --- getOneInferenceStep (View) --- ---");
		System.out.println(str);

		JPanel jbP = new JPanel();
		jbP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		jbP.setLayout(new BoxLayout(jbP, BoxLayout.Y_AXIS));

		JPanel isP;

		String infTitle = str.substring(0, str.indexOf(" endISTitle ))) "));
		isP = getInferenceTitle(infTitle);
		str = str.substring(str.indexOf(" endISTitle ))) ") + " endISTitle ))) ".length());

		String metadata = str.substring(0, str.indexOf(" endISMetadata ))) "));
		str = str.substring(str.indexOf(" endISMetadata ))) ") + " endISMetadata ))) ".length());

		String assertions = str.substring(0, str.indexOf(" endASSERT )))"));

		jbP.add(isP);
		jbP.add(getISMetadata(metadata));
		jbP.add(getAssertions(assertions));

		return jbP;
	}

	public static JPanel getInferenceTitle(String str)
	{
		System.out.println("--- --- getInferenceTitle (View) --- ---");
		System.out.println(str);		

		int nAntes = 0;

		JButton ruleB=null, engineB=null;
		JPanel justified = new JPanel(new FlowLayout(FlowLayout.LEFT));

		JPanel justContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
		justContent.setLayout(new BoxLayout(justContent, BoxLayout.Y_AXIS));
		//justContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		justContent.setBorder(new BevelBorder(BevelBorder.RAISED));//new LineBorder(Color.black));

		if (!str.equalsIgnoreCase("NO TITLE")) 
		{
			str = str.substring(" InferenceTitle: ".length());

			if (str.startsWith(" rule: ( "))
			{
				ruleB = getProvenanceSwingRep(str.substring(" rule: ( ".length(), str.indexOf(" !>>> ")));
				str = str.substring(str.indexOf(" !>>> ") + " !>>> ".length());
			}

			if (str.startsWith(" engine: ( "))
			{
				engineB = getProvenanceSwingRep(str.substring(" engine: ( ".length(), str.indexOf(" !>>> ")));
				str = str.substring(str.indexOf(" !>>> ") + " !>>> ".length());
			}

			//inferred by inference engine....
			JLabel inferL = new JLabel("Inferred by inference engine ");

			JLabel ruleL = new JLabel("with declarative rule ");

			JPanel showParents = new JPanel();
			if(!str.startsWith(" NO ANTECEDNTS "))
				showParents = getAntecedents(str.substring(0, str.indexOf(" endANTS  )) ")));

			str = str.substring(str.indexOf(" endANTS  )) ") + " endANTS  )) ".length());

			nAntes = Integer.parseInt(str.substring(0, str.indexOf(" endNumANTS )) ")));

			ShowButton parents;

			if(nAntes > 1)
				parents = new ShowButton(showParents,"parents:");
			else if(nAntes == 1)
				parents = new ShowButton(showParents,"parent:");
			else//keep this last option?
				parents = new ShowButton(null,"parents");

			justified.add(inferL);

			if(engineB != null)
				justified.add(engineB);
			if(ruleB != null){
				justified.add(ruleL);
				justified.add(ruleB);
			}
			if(showParents != null){
				justified.add(new JLabel("from the "));
			}else{
				justified.add(new JLabel("without "));
			}
			justified.add(parents);
			justified.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

			justContent.add(justified);
			if(showParents != null)
				justContent.add(showParents);

		}
		return justContent;
	}

	public static JPanel getAntecedents(String str)
	{
		System.out.println("--- --- getAntecedents (View) --- ---");
		System.out.println(str);
		JPanel antecedentsP = new JPanel();
		antecedentsP.setLayout(new BoxLayout(antecedentsP, BoxLayout.Y_AXIS));

		if (!str.equalsIgnoreCase(" NO ANTECEDNTS ")) 
		{
			for (int ai = 1; !str.startsWith(" endAntecedents ))) "); ai++) 
			{
				LinkButton antB;

				String antecedent = str.substring(0, str.indexOf(" endNodeSet --)"));
				antB = getNodeSetSwingRep(antecedent);

				if(antB != null)
				{
					JLabel antL = new JLabel(ai+". ");

					JPanel antP = new JPanel(new FlowLayout(FlowLayout.LEFT));
					antP.add(antL);
					antP.add(antB);
					antecedentsP.add(antP);
				}
				else
					ai--;

				str = str.substring(str.indexOf(" endNodeSet --)") + " endNodeSet --)".length());

			}
			return antecedentsP;
		}
		else
		{
			System.out.println(" NO ANTECEDNTS ");
		}

		return null;
	}

	private static JPanel getISMetadata(String str)
	{
		System.out.println("--- --- getISMetadata (View) --- ---");
		JPanel metadataContent = new JPanel();
		metadataContent.setLayout(new BoxLayout(metadataContent, BoxLayout.Y_AXIS));
		metadataContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//new BevelBorder(BevelBorder.RAISED));

		JPanel metadata = new JPanel(new FlowLayout(FlowLayout.LEFT));
		metadata.setBorder(new BevelBorder(BevelBorder.RAISED));
		JLabel metaL; 

		JPanel padding = new JPanel();
		padding.setBorder(BorderFactory.createEmptyBorder(2,10,2,10));

		String theRest = str.substring(" Metadata: ".length(), str.indexOf(" endMetadata ))) "));	 

		metaL = new JLabel(theRest);

		metadata.add(metaL);

		JPanel metaButtonP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ShowButton metaB = new ShowButton(metadata, "show metadata", "hide metadata");

		metaButtonP.add(metaB);
		metaB.setBackground(Color.gray);
		metadataContent.setBackground(new Color(200,200,200));
		metaButtonP.setBackground(new Color(200,200,200));

		metadataContent.add(metaButtonP);
		metadataContent.add(metadata);

		return metadataContent;
	}

	/*	private static String getOneInferenceStep(IWNodeSetOccur ns, IWInferenceStepOccur infStep, Element template)
	{
		String result = null;
		//String theRest = null;

		String engineType = null;
		String ruleType = null;

		org.inference_web.pml.context.accessor.Ontology engineOnt = null;
		org.inference_web.pml.context.accessor.Ontology ruleOnt = null;
		OntClass engineCls = null;
		OntClass ruleCls = null;

		if (ns != null && infStep != null) 
		{



			String context = ProofDetails.getContextHTMLList(ns);
			if (ns.isAxiom()) {
				if (context != null && context.trim().length()>0) {
					result += "&nbsp;&nbsp;&nbsp;&nbsp;" + context +	"</div>";

				}
			} 
			else 
			{
				result += "<br></div>";

				if (context != null)
				{
					//rStr = ProofDetails.getRandomString();    		
					context = "Descended from the assertions: " +
					//"<a id=\"Control"+rStr+"\" href=\"javascript:toggle('Text"+rStr+"', 'Control"+rStr+"', 'show', 'hide');\">"+
					"<label class=\"adornment\">show</label></a><br>" +
					/*"<div id=\"Text"+rStr+"\" class=showhide style=\"display: none\*//*">"+context+"</div>";
					result += "<div class=pink_continue>"+context+"</div>";

				}
			}
		}
		return result;
	}*/


	public static JButton getProvenanceSwingRep(String str)
	{
		System.out.println("--- --- getProvenanceSwingRep (View) --- ---");
		PopupButton popup = null;

		if(str.startsWith(" ProvenanceButton: "))
			str = str.substring(" ProvenanceButton: ".length());

		if (str.startsWith(" (URISTR: "))
		{
			String uriStr = str.substring(" (URISTR: ".length(), str.indexOf(" )[Provenance] "));
			String name = str.substring(str.indexOf(" )[Provenance] ") +" )[Provenance] ".length(), str.indexOf(" endProvenanceButton ))) "));

			popup = new PopupButton(name, uriStr);
		} 
		else 
		{
			//what should happen in this case?
			//result = getProvenanceDetailsHTMLInList(pePo);
			System.out.println("IN ELSE!! (SwingView class) NEED to getProvenanceDetailsHTMLInLIst(pePo).");
		}

		return popup;
	}


	public static LinkButton getNodeSetSwingRep(String str)
	{ 
		System.out.println("--- --- getNodeSetSwingRep (View) --- ---");
		System.out.println(str);

		if(str.startsWith(" NodeSet Link: ( "))
		{
			int start = " NodeSet Link: ( ".length();
			int end = str.indexOf(" <-!-> ", start);
			String rawStr = str.substring(start, end);

			String uriStr = getParsedURI(str);

			System.out.println("( URI "+uriStr+" )[IWNodeSet] "+ rawStr);
			//System.out.println("CCURI " + currentConclusionURI);

			return getNodeSetSwingRep(rawStr, uriStr);
		} 

		System.out.println("Warning -- IWNodeSet NULL");
		return null;
	}

	public static String getParsedURI(String str)
	{
		if(str.startsWith(" NodeSet Link: ( "))
		{
			int start = " NodeSet Link: ( ".length();
			int end = str.indexOf(" <-!-> ", start);
			str = str.substring(end+" <-!-> ".length());

			String uriStr = str.substring(0, str.indexOf(" endNodeSetString) "));

			return uriStr;	
		}

		return null;
	}

	public static LinkButton getNodeSetSwingRep(String rawStr, String uriStr)
	{
		System.out.println("----getNodeSetSwingRep (View)----");

		if (uriStr != null)
			return new LinkButton(rawStr, uriStr);

		System.out.println("Warning -- URI NULL");
		return null;
	}


	static String getShorttenedURI (String inURI) {
		String result = inURI;
		if (inURI != null) {
			String displayStr = inURI;
			if (displayStr.length()>35) {
				displayStr = displayStr.substring(0,25)+"..."+displayStr.substring(displayStr.length()-10);
				result = displayStr;
			}

		}

		return result;
	}


	public static JPanel getAssertions(String str)
	{
		System.out.println("--- --- getAssertions (View) --- ---");

		JPanel assertionContent = new JPanel();
		assertionContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		assertionContent.setLayout(new BoxLayout(assertionContent, BoxLayout.Y_AXIS));

		JPanel assertionsP = new JPanel();
		assertionsP.setLayout(new BoxLayout(assertionsP, BoxLayout.Y_AXIS));
		assertionsP.setBorder(new BevelBorder(BevelBorder.RAISED));

		System.out.println(str);

		if(str.startsWith(" Assertions: "))
		{
			str = str.substring(" Assertions: ".length());

			if(str.startsWith(" ancestorAssertions(2): ")  ||  str.startsWith(" ancestorAssertion(1): "))
			{
				if(str.startsWith(" ancestorAssertions(2): "))
					str = str.substring(" ancestorAssertions(2): ".length());
				else
					str = str.substring(" ancestorAssertion(1): ".length());

				JPanel assertP = new JPanel();
				assertP.setLayout(new BoxLayout(assertP, BoxLayout.Y_AXIS));
				assertP.setBorder(new BevelBorder(BevelBorder.RAISED));
				assertionsP.add(assertP);


				for (int cntr = 1; !str.startsWith(" endAncestorAssertion >>) "); cntr++)
				{
					JPanel linkP = new JPanel(new FlowLayout(FlowLayout.LEFT));
					assertionsP.add(assertP);

					JLabel numL = new JLabel(cntr+". ");
					linkP.add(numL);

					int index = str.indexOf(" NodeSetLink --) ");
					String nodeSet = str.substring(0, index);
					linkP.add( getNodeSetSwingRep(nodeSet) );
					str = str.substring(index + " NodeSetLink --) ".length());

					assertP.add(linkP);

					index = str.indexOf(" SourceInformation --) ");
					String sourceStr = str.substring(0, index);
					assertP.add( getSourceUsage(sourceStr) );
					str = str.substring(index + " SourceInformation --) ".length());
				}
			}
			if(str.startsWith(" ancestorAssumptions(2): ")  ||  str.startsWith(" ancestorAssumption(1): "))
			{
				if(str.startsWith(" ancestorAssumptions(2): "))
					str = str.substring(" ancestorAssumptions(2): ".length());
				else
					str = str.substring(" ancestorAssumption(1): ".length());

				JPanel assertP = new JPanel();
				assertP.setLayout(new BoxLayout(assertP, BoxLayout.Y_AXIS));
				assertP.setBorder(new BevelBorder(BevelBorder.RAISED));
				assertionsP.add(assertP);


				for (int cntr = 1; !str.startsWith(" endAncestorAssumption >>) "); cntr++)
				{
					JPanel linkP = new JPanel(new FlowLayout(FlowLayout.LEFT));
					assertionsP.add(assertP);

					JLabel numL = new JLabel(cntr+". ");
					linkP.add(numL);

					int index = str.indexOf(" NodeSetLink --) ");
					String nodeSet = str.substring(0, index);
					linkP.add( getNodeSetSwingRep(nodeSet) );
					str = str.substring(index + " NodeSetLink --) ".length());

					assertP.add(linkP);

					index = str.indexOf(" SourceInformation --) ");
					String sourceStr = str.substring(0, index);
					assertP.add( getSourceUsage(sourceStr) );
					str = str.substring(index + " SourceInformation --) ".length());
				}
			}
			if(str.startsWith(" dischargedAncestorAssertions(2): ")  ||  str.startsWith(" dischargedAncestorAssertion(1): "))
			{
				if(str.startsWith(" dischargedAncestorAssertions(2): "))
					str = str.substring(" dischargedAncestorAssertions(2): ".length());
				else
					str = str.substring(" dischargedAncestorAssertion(1): ".length());

				JPanel assertP = new JPanel();
				assertP.setLayout(new BoxLayout(assertP, BoxLayout.Y_AXIS));
				assertP.setBorder(new BevelBorder(BevelBorder.RAISED));
				assertionsP.add(assertP);


				for (int cntr = 1; !str.startsWith(" endDischargedAncestorAssertion >>) "); cntr++)
				{
					JPanel linkP = new JPanel(new FlowLayout(FlowLayout.LEFT));
					assertionsP.add(assertP);

					JLabel numL = new JLabel(cntr+". ");
					linkP.add(numL);

					int index = str.indexOf(" NodeSetLink --) ");
					String nodeSet = str.substring(0, index);
					linkP.add( getNodeSetSwingRep(nodeSet) );
					str = str.substring(index + " NodeSetLink --) ".length());

					assertP.add(linkP);

					index = str.indexOf(" SourceInformation --) ");
					String sourceStr = str.substring(0, index);
					assertP.add( getSourceUsage(sourceStr) );
					str = str.substring(index + " SourceInformation --) ".length());
				}
			}
			if(str.startsWith(" dischargedAncestorAssumptions(2): ")  ||  str.startsWith(" dischargedAncestorAssumption(1): "))
			{
				if(str.startsWith(" dischargedAncestorAssumptions(2): "))
					str = str.substring(" dischargedAncestorAssumptions(2): ".length());
				else
					str = str.substring(" dischargedAncestorAssumption(1): ".length());

				JPanel assertP = new JPanel();
				assertP.setLayout(new BoxLayout(assertP, BoxLayout.Y_AXIS));
				assertP.setBorder(new BevelBorder(BevelBorder.RAISED));
				assertionsP.add(assertP);


				for (int cntr = 1; !str.startsWith(" endDischargedAncestorAssumption >>) "); cntr++)
				{
					JPanel linkP = new JPanel(new FlowLayout(FlowLayout.LEFT));
					assertionsP.add(assertP);

					JLabel numL = new JLabel(cntr+". ");
					linkP.add(numL);

					int index = str.indexOf(" NodeSetLink --) ");
					String nodeSet = str.substring(0, index);
					linkP.add( getNodeSetSwingRep(nodeSet) );
					str = str.substring(index + " NodeSetLink --) ".length());

					assertP.add(linkP);

					index = str.indexOf(" SourceInformation --) ");
					String sourceStr = str.substring(0, index);
					assertP.add( getSourceUsage(sourceStr) );
					str = str.substring(index + " SourceInformation --) ".length());
				}
			}
		} 
		else {
			//content=getSourceUsage(ns);
		}

		JPanel assertionsTitleP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel descended = new JLabel("Descended from the ");
		ShowButton assertionsB = new ShowButton(assertionsP,"assertions:");
		assertionsTitleP.add(descended);
		assertionsTitleP.add(assertionsB);

		assertionContent.add(assertionsTitleP);
		assertionContent.add(assertionsP);
		assertionsTitleP.setBackground(new Color(230,230,230));
		assertionContent.setBackground(new Color(230,230,230));


		return assertionContent;
	}

	private static JPanel getSourceUsage(String str)
	{
		System.out.println("--- --- getSourceUsage (View) --- ---");

		if(str.startsWith(" Source:-- "))
			str = str.substring(" Source:-- ".length());

		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));

		if(!str.startsWith(" endofSource >>) "))
		{
			String srcUri = "";
			String check = "";

			int index = str.indexOf(" sourceProvenance >>) ");
			if(index >= 0)
			{
				check = str.substring(index, index+(" sourceProvenance >>) ".length()));
				srcUri = str.substring(0, index);
			}

			if(check.equalsIgnoreCase(" sourceProvenance >>) "))
			{
				p.add(new ProvSourceButton("source provenance", srcUri));

				str = str.substring(index+" sourceProvenance >>) ".length() );
			}

			index = str.indexOf(" sourceDocument >>) ");
			if(index >= 0)
			{
				check = str.substring(index, index+ " sourceDocument >>) ".length());
				srcUri = str.substring(0, index);
			}

			if(check.equalsIgnoreCase(" sourceDocument >>) "))
			{
				p.add(new ProvSourceButton("source usage", srcUri));

				str = str.substring(index+" sourceDocument >>) ".length() );
			}

			index = str.indexOf(" sourceUsage >>) ");
			if(index >= 0)
			{
				check = str.substring(index, index+ " sourceUsage >>) ".length());
				srcUri = str.substring(0, index);
			}

			if(check.equalsIgnoreCase(" sourceUsage >>) "))
			{
				p.add(new ProvSourceButton("source", srcUri));

				str = str.substring(index+" sourceUsage >>) ".length() );
			}
			return p;
		}
		else
		{
			p.add(new PopupButton("no source", null));
			return p;
		}
	}


	private static JPanel getUsedToInfer (String str) 
	{
		System.out.println("--- --- getUsedToInfer (View) --- ---");

		JPanel utiP = new JPanel();
		utiP.setLayout(new BoxLayout(utiP, BoxLayout.Y_AXIS));

		System.out.println(str);
		if(str.startsWith(" UsedtoInfer: "))
		{
			for (int i=1; !str.startsWith(" endofInfer) "); i++ ) 
			{


				if (str.startsWith(" UsedtoInfer: "))
				{
					int start = " UsedtoInfer: ".length();
					int end = str.indexOf(" with help of: ");

					String linkStr = str.substring(start, end);

					str = str.substring(end + " with help of: ".length());

					start = 0;
					end = str.indexOf(" endofHelp) ");

					String siblingStr = str.substring(start, end);
					str = str.substring(end + " endofHelp) ".length());

					JPanel inferP = new JPanel(new BorderLayout());
					JLabel numL = new JLabel(i+". ");
					inferP.add(numL, BorderLayout.WEST);
					inferP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

					JPanel content = new JPanel();
					content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
					//content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

					JPanel inferred = new JPanel(new FlowLayout(FlowLayout.LEFT));
					//LinkButton link = new LinkButton("hates(butler, butler)", null);	
					JLabel help = new JLabel("with help of: ");

					JPanel showing;// = new JPanel();//new FlowLayout(FlowLayout.LEFT));

					//subgoalStr = getNodeSetSwingRep(subgoal);
					showing = getSiblings(siblingStr);

					showing.setLayout(new BoxLayout(showing, BoxLayout.Y_AXIS));
					ShowButton show = new ShowButton(showing, "show", "hide");

					inferred.add(getNodeSetSwingRep(linkStr));
					inferred.add(help);
					inferred.add(show);
					inferred.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

					content.add(inferred);
					content.add(showing);
					content.setBorder(new BevelBorder(BevelBorder.RAISED));//LineBorder(Color.black));
					//content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
					inferP.add(content, BorderLayout.CENTER);

					utiP.add(inferP);

				}
			}
		}
		else
		{
			System.out.println("No Used to Infer Section");
		}
		return utiP;
	}

	private static JPanel getSiblings (String str)
	{
		System.out.println("--- --- getSiblings (View) --- ---");
		String siblingStr = null;

		JPanel showingP = new JPanel();
		showingP.setLayout(new BoxLayout(showingP, BoxLayout.Y_AXIS));


		if(str.startsWith(" Siblings: "))
		{
			str = str.substring(" Siblings: ".length());
			int cntr = 1;
			while (!str.startsWith(" endOfSiblings) "))
			{
				int start = 0;
				int end = str.indexOf(" --!) ");

				siblingStr = str.substring(start, end);

				if(currentConclusionURI.equalsIgnoreCase(getParsedURI(siblingStr)))
				{
					str = str.substring(end + " --!) ".length());
				}
				else
				{
					str = str.substring(end + " --!) ".length());

					JPanel showingOne = new JPanel(new FlowLayout(FlowLayout.LEFT));
					JLabel numL = new JLabel(cntr+". ");

					showingOne.add(numL);
					showingOne.add( getNodeSetSwingRep(siblingStr) );
					showingP.add(showingOne);

					cntr++;
				}
			}  		
		}
		return showingP;
	}

	private static JPanel getFinalConclusion (String strRep /*IWNodeSetOccur ns, IWNodeSetOccur root, IWQuery query, List questions, boolean isRoot*/)
	{
		System.out.println("--- --- getFinalConclusion (View) --- ---");
		LinkButton goalLinkB = null;

		//String strRep = SwingViewStringRep.getFinalConclusion(ns, root, query, questions, isRoot);


		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));

		JPanel finalCP = new JPanel();
		finalCP.setLayout(new BoxLayout(finalCP, BoxLayout.Y_AXIS));
		finalCP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));


		if (strRep.startsWith(" Conclusion: 1. ( "))
		{
//			goalStr = "<li>"+ProofDetails.getConclusionWidget(goal,"concluded data:",true)+"</li>";
			//goalStr = getNodeSetSwingRep(goal);
			int start = " Conclusion: 1. ( ".length();
			int end = strRep.indexOf(" endNodeConc) ", start);

			String linkStr = strRep.substring(start, end);
			strRep = strRep.substring(end + " endNodeConc) ".length());

			goalLinkB = (LinkButton) getNodeSetSwingRep(linkStr);

			JPanel conclusionP = new JPanel(new FlowLayout(FlowLayout.LEFT));
			JLabel firstCL = new JLabel("1. ");
			//LinkButton conclusionB = new LinkButton("$false", null);
			conclusionP.add(firstCL);
			conclusionP.add( goalLinkB );
			conclusionP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			conclusionP.setBackground(new Color(230,230,230));
			finalCP.add(conclusionP);
		}

		System.out.println("---- After Conclusion Button ----");
		//cntr?
		while (!strRep.startsWith(" endQueryAnswer) ")) 
		{
			System.out.println(strRep);
			if(strRep.startsWith(" that answers the query: ( "))
			{
				System.out.println("---- In QA ----");
				int start = " that answers the query: ( ".length();
				int end = strRep.indexOf(" --!) ", start);

				String queryStr = strRep.substring(start, end);
				strRep = strRep.substring(end + " --!) ".length());

				JPanel cnfP = new JPanel(new FlowLayout(FlowLayout.LEFT));
				cnfP.setBorder(new BevelBorder(BevelBorder.RAISED));

				JPanel cnfBorderP = new JPanel();
				cnfBorderP.setLayout(new BoxLayout(cnfBorderP, BoxLayout.Y_AXIS));
				cnfBorderP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

				//JLabel cnfText1 = new JLabel(" cnf(prove_neither_charles_nor_butler_did_it,negated_conjecture, ");
				//JLabel cnfText2 = new JLabel("( killed(butler,agatha) ");
				//JLabel cnfText3 = new JLabel("| killed(charles,agatha) )). ");

				JTextArea cnfText = new JTextArea(queryStr);
				cnfText.setEditable(false);
				cnfText.setBackground(cnfBorderP.getBackground());
				cnfText.setFont(cnfText.getFont().deriveFont((float)13.0));

				//JLabel cnfText1 = new JLabel(queryStr);
				cnfBorderP.add(cnfText);
				//cnfBorderP.add(cnfText2);
				//cnfBorderP.add(cnfText3);
				cnfP.add(cnfBorderP);
				JPanel answersP = new JPanel();
				answersP.setLayout(new BoxLayout(answersP, BoxLayout.Y_AXIS));
				JPanel aButtonP = new JPanel(new FlowLayout(FlowLayout.LEFT));
				ShowButton answersB = new ShowButton(cnfP,"that answers query:");
				aButtonP.add(answersB);
				answersP.add(aButtonP);
				answersP.add(cnfP);
				answersP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				answersP.setBackground(new Color(200,200,200));
				aButtonP.setBackground(new Color(200,200,200));
				//answersP.setBackground(new Color(151,203,208));

				finalCP.add(answersP);

			}

			System.out.println("---- After QA ----");

			if(strRep.startsWith(" that is a formal representation of the question: "))
			{
				System.out.println("---- In Formal Rep ----");
				int start = " that is a formal representation of the question: ".length();
				int end = strRep.indexOf(" --!) ", start);

				String questionStr = strRep.substring(start, end);
				strRep = strRep.substring(end + " --!) ".length());

				JPanel repAnswerP = new JPanel(new FlowLayout(FlowLayout.LEFT));
				repAnswerP.setBorder(new BevelBorder(BevelBorder.RAISED));

				JPanel repBorderP = new JPanel();
				repBorderP.setLayout(new BoxLayout(repBorderP, BoxLayout.Y_AXIS));
				repBorderP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

				JTextArea repText = new JTextArea( questionStr);
				repText.setEditable(false);
				repText.setBackground(repBorderP.getBackground());
				repText.setFont(repText.getFont().deriveFont((float)13.0));

				//JLabel repText1 = new JLabel(questionStr);
				repBorderP.add(repText);
				repAnswerP.add(repBorderP);

				JPanel formalRepP = new JPanel();
				formalRepP.setLayout(new BoxLayout(formalRepP, BoxLayout.Y_AXIS));
				JPanel repButtonP = new JPanel(new FlowLayout(FlowLayout.LEFT));
				ShowButton repB = new ShowButton(repAnswerP,"that is a formal representation of the question:");
				repButtonP.add(repB);
				formalRepP.add(repButtonP);
				formalRepP.add(repAnswerP);
				formalRepP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				formalRepP.setBackground(new Color(230,230,230));
				repButtonP.setBackground(new Color(230,230,230));

				finalCP.add(formalRepP);
			}
			System.out.println("---- After Formal Rep ----");
		}
		//}
		//bSystem.out.println("qa result="+result);
		p4.add(finalCP);
		return p4;
	}


	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setSize(600, 600);

		//frame.add(getNodeSetDetailsSwing("test"));

		String test = "Conclusion: 1. ( " + "NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) "+" endNodeConc) " +  " that answers the query: ( "+"THIS is a QUERY 1"+" --!) " + " that is a formal representation of the question: "+ "THIS is a QUESTION 1"+" --!) " +  " that answers the query: ( "+"THIS is a QUERY 2"+" --!) " + " that is a formal representation of the question: "+ "THIS is a QUESTION 2"+" --!) " +  " that is a formal representation of the question: "+ "THIS is a QUESTION 3"+" --!) " + " that answers the query: ( "+"THIS is a QUERY 4"+" --!) " + " endQueryAnswer) ";

		//frame.add(getFinalConclusion(test));

		SwingView.getNodeSetSwingRep("NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) ");

		//frame .add(SwingView.getSiblings(" Siblings: " +"NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) "+" --!) "  + "NodeSet Link: ( NUM2 <-!-> URI2 endNodeSetString) "+" --!) " + "NodeSet Link: ( NUMBER3 <-!-> URI3 endNodeSetString) "+" --!) "   + " endOfSiblings) "));

		//frame .add(SwingView.getUsedToInfer(" UsedtoInfer: " + "NodeSet Link: ( RAWSTR!!! <-!-> URI10 endNodeSetString) " + " with help of: " + " Siblings: " +"NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) "+" --!) "  + "NodeSet Link: ( NUM2 <-!-> URI2 endNodeSetString) "+" --!) " + "NodeSet Link: ( NUMBER3 <-!-> URI3 endNodeSetString) "+" --!) "   + " endOfSiblings) " +  " endofHelp) " + " UsedtoInfer: " + "NodeSet Link: ( RAWSTR!!! <-!-> URI10 endNodeSetString) " + " with help of: " + " Siblings: " +"NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) "+" --!) "  + "NodeSet Link: ( NUM2 <-!-> URI2 endNodeSetString) "+" --!) " + "NodeSet Link: ( NUMBER3 <-!-> URI3 endNodeSetString) "+" --!) "   + " endOfSiblings) " +  " endofHelp) " +  " UsedtoInfer: " + "NodeSet Link: ( RAWSTR!!! <-!-> URI10 endNodeSetString) " + " with help of: " + " Siblings: " +"NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) "+" --!) "  + "NodeSet Link: ( NUM2 <-!-> URI2 endNodeSetString) "+" --!) " + "NodeSet Link: ( NUMBER3 <-!-> URI3 endNodeSetString) "+" --!) "   + " endOfSiblings) " +  " endofHelp) " + " endofInfer) "));
		//frame.add(SwingView.getSourceUsage(   " Source:-- " + "Source provenance URI" + " sourceProvenance >>) " + "RenderSourceUsage?srcUsgStr="
		//		+ "DOCURI" + " sourceDocument >>) " + "RenderV1SourceUsage?srcUsgStr="
		//		+ "SOURCEURI" + " sourceUsage >>) " + " endofSource >>) "  ));

		frame .add(SwingView.getAssertions(  " Assertions: " + " ancestorAssertions(2): " +  "NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) " + " NodeSetLink --) " + " Source:-- " + "Source provenance URI" + " sourceProvenance >>) " + "RenderSourceUsage?srcUsgStr="
				+ "DOCURI" + " sourceDocument >>) " + "RenderV1SourceUsage?srcUsgStr="
				+ "SOURCEURI" + " sourceUsage >>) " + " endofSource >>) " + " SourceInformation --) "  +  "NodeSet Link: ( RAWSTR1 <-!-> URI1 endNodeSetString) " + " NodeSetLink --) " + " Source:-- " + "Source provenance URI" + " sourceProvenance >>) " + "RenderSourceUsage?srcUsgStr="
				+ "DOCURI" + " sourceDocument >>) " + "RenderV1SourceUsage?srcUsgStr="
				+ "SOURCEURI" + " sourceUsage >>) " + " endofSource >>) " + " SourceInformation --) "  + " endAncestorAssertion >>) " + " dischargedAncestorAssumption(1): "  + "NodeSet Link: ( RAWSTR2 <-!-> URI2 endNodeSetString) " + " NodeSetLink --) " + " Source:-- " + "Source provenance URI" + " sourceProvenance >>) " + "RenderSourceUsage?srcUsgStr="
				+ "DOCURI" + " sourceDocument >>) " + "RenderV1SourceUsage?srcUsgStr="
				+ "SOURCEURI" + " sourceUsage >>) " + " endofSource >>) " + " SourceInformation --) " + " endDischargedAncestorAssumption >>) "+ /*+ dischargedAssertionLabel + dischargedAssumptionLabel +*/ " endAssertions ))) "  ));
		frame.setVisible(true);//*/

	}
}
