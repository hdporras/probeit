package probeIt.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class PMLNodesetURIsFromURL
{
	public static LinkedList<String> getNodesetURIsFromURL(String url)
	{
		URL u;
		InputStream is = null;

		try 
		{
			u = new URL(url);
			is = u.openStream();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);//doc = db.parse(file);//url);
			doc.getDocumentElement().normalize();
			System.out.println("Root element " + doc.getDocumentElement().getNodeName());

			//Check for rdf:Description
			NodeList descNodeLst = doc.getElementsByTagName("rdf:Description");

			System.out.println("rdf:Descritption nodes found: "+descNodeLst.getLength());

			LinkedList<String> descAnsList = new LinkedList<String>();
			if(descNodeLst.getLength() > 0)
				descAnsList = findRDFDescriptionNodesets(descNodeLst);

			//Check for pmlj:NodeSet
			NodeList pmljNodeLst = doc.getElementsByTagName("pmlj:NodeSet");

			System.out.println("pmlj:NodeSet nodes found: "+pmljNodeLst.getLength());

			LinkedList<String> pmljAnsList = new LinkedList<String>();
			if(pmljNodeLst.getLength() > 0)
				pmljAnsList = findPMLJNodesets(pmljNodeLst);
			
			
			//if either type of node found an answer...
			if(!pmljAnsList.isEmpty() || !descAnsList.isEmpty())
			{
				//combine results and return
				LinkedList<String> answerList = new LinkedList<String>();
				if(!pmljAnsList.isEmpty())
					answerList.addAll(pmljAnsList);
				if(!descAnsList.isEmpty())
					answerList.addAll(descAnsList);
				
				return answerList;
			}
			//otherwise return no results found..

		} 
		catch (MalformedURLException mue) {
			mue.printStackTrace();
			System.exit(1);
		}
		catch (IOException ioe){
			ioe.printStackTrace();
			System.exit(1);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally 
		{
			try {
				is.close();
			} catch (IOException ioe) {
			}

		}
		return null;
	}


	private static LinkedList<String> findPMLJNodesets(NodeList pmljNodeLst)
	{
		LinkedList<String> list = new LinkedList<String>();

		for (int s = 0; s < pmljNodeLst.getLength(); s++)
		{
			Node node = pmljNodeLst.item(s);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{

				Element pmlj = (Element) node;
				String answer = pmlj.getAttributes().getNamedItem("rdf:about").getNodeValue();
				list.add(answer);
				System.out.println("Answer: "+answer);
			}
		}
		
		return list;
	}

	private static LinkedList<String> findRDFDescriptionNodesets(NodeList nodeLst)
	{
		LinkedList<String> list = new LinkedList<String>();

		for (int s = 0; s < nodeLst.getLength(); s++)
		{
			Node node = nodeLst.item(s);

			if (node.getNodeType() == Node.ELEMENT_NODE)
			{

				Element rdf = (Element) node;
				NodeList elements = rdf.getElementsByTagName("rdf:type");
				System.out.println();//node.getLength());

				String ns = elements.item(0).getAttributes().getNamedItem("rdf:resource").getNodeValue();
				System.out.println("NodeSet?: "+ns);

				if(ns.contains("#NodeSet") && !ns.contains("#NodeSetL"))
				{
					String answer = rdf.getAttributes().getNamedItem("rdf:about").getNodeValue();
					list.add(answer);
					System.out.println("Answer: "+answer);
				}
			}
		}

		return list;
	}

	public static void main(String argv[]) {

		PMLNodesetURIsFromURL.getNodesetURIsFromURL("//saturn/students/hdporras/Desktop/uri2.xml");//"http://rio.cs.utep.edu/ciserver/ciprojects/GravityMapPML/ContourMapCMTroyNY_06613635342660732.owl");

	}
}