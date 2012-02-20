package probeIt.viewerFramework.viewers;
import javax.imageio.ImageIO;
import javax.swing.*;
import probeIt.util.JarResourceLoader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

//import org.apache.soap.encoding.soapenc.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pml.impl.serializable.PMLConclusion;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.ByteArrayInputStream;

public class Viewer_EQLXML extends JSplitPane implements Viewer
{
	Viewer solarImage;
	String imageURL;
	String comment;
	String date;
	String time;
	String observer;
	
	public Viewer_EQLXML()
	{
		super(JSplitPane.VERTICAL_SPLIT);
	}
	
	public String getViewerName()
	{return "Extended Quick Lookup Viewer";}
	
	public int getLogicalWidth()
	{return solarImage.getLogicalWidth();}
	
	public int getLogicalHeight()
	{return solarImage.getLogicalHeight() + 50;}
	
	public JComponent getViewerInterface(Object eqlXML)
	{
		if(eqlXML instanceof String)
		{
			String eqlProductXML = (String)eqlXML;
			populateFields(eqlProductXML, false);
			solarImage = Viewers.getImageJ();
			Viewer logComment = Viewers.getText();
			Viewer commentDate = Viewers.getText();
			Viewer commentTime = Viewers.getText();
			Viewer observer = Viewers.getText();
			
			JComponent eqlImage;
			if(imageURL == null)
			{
				JarResourceLoader loader = new JarResourceLoader();
				eqlImage = solarImage.getViewerInterface(loader.getImage("error_icon.gif"));
			}
			else
				eqlImage = solarImage.getViewerInterface(imageURL);
			
			JPanel commentPanel = new JPanel();
			commentPanel.setLayout(new java.awt.GridLayout(4, 1));
			commentPanel.add(commentDate.getViewerInterface("Observation Date: " + date));
			commentPanel.add(commentTime.getViewerInterface("Observation Time: " +time));
			commentPanel.add(logComment.getViewerInterface("Observation: " + this.comment));
			commentPanel.add(observer.getViewerInterface("Observer: " + this.observer));
			
			JScrollPane metadata = new JScrollPane(commentPanel);
			
			add(eqlImage);
			add(metadata);

			this.setDividerLocation(solarImage.getLogicalHeight());
			
			return this;
		}
		else
			return null;
	}
	
	private void populateFields(String xml, boolean urlOnly)
	{		
		//get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			//parse eqlXMLFile
			ByteArrayInputStream str = new ByteArrayInputStream(xml.getBytes());
			Document doc = db.parse(str);
			
			//set fields
			setImageURL(doc);
			
			if(!urlOnly)
			{
				//set comment
				setLogComment(doc);
			
				//set timestamp fields
				setTimestamp(doc);
			
				//get observer name
				setObserver(doc);
			}
			
		}catch(Exception e)
		{e.printStackTrace();}
	}
	
	private void setLogComment(Document doc)
	{
		//get root of xml file, which is element EQL
		Element eqlElement = doc.getDocumentElement();
		
		Element logElement = (Element)eqlElement.getElementsByTagName("log").item(0);
		Element messageElement = (Element)logElement.getElementsByTagName("message").item(0);
		this.comment = messageElement.getChildNodes().item(0).getNodeValue();
	}
	
	public Object extractImageURLFromXML(String xml)
	{
		populateFields(xml, true);
		if(imageURL == null)
		{
			JarResourceLoader loader = new JarResourceLoader();
			return loader.getImage("error_icon.gif");
		}
		else
			return imageURL;
	}
	
	private void setImageURL(Document doc)
	{
		//get root of xml file, which is element EQL
		Element eqlElement = doc.getDocumentElement();
		
		if(eqlElement.getElementsByTagName("image").getLength() != 0)
		{
			Element imageElement = (Element)eqlElement.getElementsByTagName("image").item(0);
			imageURL = imageElement.getChildNodes().item(0).getNodeValue();
		}
	}
	
	private void setObserver(Document doc)
	{
		//get root of xml file, which is element EQL
		Element eqlElement = doc.getDocumentElement();
		Element logElement = (Element)eqlElement.getElementsByTagName("log").item(0);
		Element obser = (Element)logElement.getElementsByTagName("observer").item(0);
		
		observer = obser.getChildNodes().item(0).getNodeValue();
	}
	
	private void setTimestamp(Document doc)
	{
		//get root of xml file, which is element EQL
		Element eqlElement = doc.getDocumentElement();
		
		Element logElement = (Element)eqlElement.getElementsByTagName("log").item(0);
		Element timestamp = (Element)logElement.getElementsByTagName("timestamp").item(0);
		Element date = (Element)timestamp.getElementsByTagName("date").item(0);
		Element month = (Element)date.getElementsByTagName("month").item(0);
		Element day = (Element)date.getElementsByTagName("day").item(0);
		Element year = (Element)date.getElementsByTagName("year").item(0);
		
		this.date = month.getChildNodes().item(0).getNodeValue() + "/" + day.getChildNodes().item(0).getNodeValue() + "/" + year.getChildNodes().item(0).getNodeValue();
		
		Element time = (Element)timestamp.getElementsByTagName("time").item(0);
		Element hour = (Element)time.getElementsByTagName("hour").item(0);
		Element min = (Element)time.getElementsByTagName("minute").item(0);
		Element sec = (Element)time.getElementsByTagName("second").item(0);
		Element timeZone = (Element)time.getElementsByTagName("timezone").item(0);
		
		this.time = hour.getChildNodes().item(0).getNodeValue() + ":" + min.getChildNodes().item(0).getNodeValue() + ":" + sec.getChildNodes().item(0).getNodeValue() + ":" + timeZone.getChildNodes().item(0).getNodeValue(); 	
	}
}
