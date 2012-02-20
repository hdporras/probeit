/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package probeIt.ui.avatar.queries;

import java.util.*;

import probeIt.ui.ViewsManager;
import probeIt.ui.avatar.*;
import probeIt.ui.model.ViewsModel;
import pml.*;
import pml.impl.serializable.*;

/**
 * @author paulo
 */
public class QueryInMemoryJustification {

	private String typeURI;
	private int requestedType;
	private List<PMLNode> visitedPML;
	private List<String> selectedURIs;
	
	/*
	 * 	This class performs queries over PML justifications loaded 
	 *  in memory. These are the steps to create the query:
	 *  
	 *     1) Create empty PMLQuery
	 *     2) Traverse PML DAG
	 *     3) For each conclusion, if it is a product, add to PMLQuery
	 *     4) Return PMLQuery
	 *  
	 */
	public QueryInMemoryJustification() {
	}
	
	public PMLQuery execute(int type) {
		PMLNode node = ViewsModel.getInstance().getJustification();
		requestedType = type;
		if (node == null) {
			node = ViewsModel.getInstance().getAnswer();
			if (node == null) {
				System.out.println("Unable to establish a valid node set URI");
				return null;
			} else {
				ViewsModel.getInstance().setJustification(node.getURI());
				node = ViewsModel.getInstance().getJustification();
				if (node == null) {
					System.out.println("Unable to load a valid node set URI");
					return null;
				}
			}				
		}
		PMLNode currentNode = node;
		PMLQuery query = new PMLQueryImplSerializable();		
		typeURI = SPOntology.getURI(type);
		System.out.println("MemoryQuery: URI " + node.getURI() + " type " + typeURI);
		visitedPML = new ArrayList<PMLNode>();
		selectedURIs = new ArrayList<String>();
		traverse(currentNode);
		String[] answers = new String[selectedURIs.size()];
		for (int i=0; i < selectedURIs.size(); i++)
			answers[i] = selectedURIs.get(i);
		System.out.println("Output of MemoryQuery (length): " + answers.length);
		((PMLQueryImplSerializable)query).setURI("http://localhost/InMemoryQuery");
		((PMLQueryImplSerializable)query).setAnswerURIs(answers);
		PMLLanguageClassifier lang = new PMLLanguageClassifier();
		lang.setFormat(PMLLanguageClassifier.TextFormat);
		lang.setLanguage(PMLLanguageClassifier.EnglishLang);
		((PMLQueryImplSerializable)query).setQueryClassifier(lang);
		((PMLQueryImplSerializable)query).setContent("(List antecedents of <" + node.getURI() + ">)");
		return query;
	}
	
	public void traverse(PMLNode node) {
		if (node == null || node.getInferenceSteps() == null)
			return;
		if (visitedPML.indexOf(node) != -1)
			return;
		visitedPML.add(node);
		PMLInferenceStep IS;
		System.out.println("traversed: " + node.getURI());
		List<PMLInferenceStep> listIS = node.getInferenceSteps();
		for(int i=0; i < listIS.size(); i++) {
			IS = listIS.get(i);
			PMLNode[] nodeList = IS.getAntecedents();
			if (nodeList != null)
				for (int j=0; j < nodeList.length; j++) {
					if (nodeList[j].getConclusion().getSPType() == requestedType) {
						selectedURIs.add(nodeList[j].getURI());				
						System.out.println("Selected: " + nodeList[j].getURI());
					}
					traverse(nodeList[j]);
				}
		}
		return;		
	}

}
