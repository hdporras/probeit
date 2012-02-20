package probeIt.ui.local;

import java.awt.*;

import pml.PMLNode;
import pml.loading.Loader;

import javax.swing.*;
import probeIt.viewerFramework.ViewerFactory;
import probeIt.ui.Toolbar;
import probeIt.ui.popup.provenance.LinkListener;
import probeIt.viewerFramework.DAGContentFactory;
public class HTMLView extends JPanel
{
	private JEditorPane _html;
	private JScrollPane scroll;
	ViewerFactory selector;
	DAGContentFactory contentSelector;
	Loader factory;
	
	LocalViewHTMLExtractor localViewHTML;
	
	public HTMLView(String URI, boolean isURI, boolean pml, boolean toolbar, boolean localView)
	{
		super();
		
		factory = new Loader(probeIt.ProbeIt.getInstance().isRemote());
		setLayout(new BorderLayout());
		
		initView(URI, isURI, pml, toolbar, localView);
	}
	
	private void initView(String URI, boolean isURI, boolean PML, boolean toolBar, boolean localView)
	{
		if(toolBar)
		{
			Toolbar toolbar=new Toolbar(Toolbar.CanvasType.provenanceView);
			toolbar.setup();
			add(toolbar, BorderLayout.NORTH);
		}
	
		try
		{
			if(PML && !localView)
			{
				String html = factory.loadDetailsHTML(URI);
				_html = new JEditorPane("text/html", html);
			}
			else if(PML && localView)
			{
				String html = factory.loadDetailsHTML(URI);
				localViewHTML = new LocalViewHTMLExtractor(html);
				
				_html = new JEditorPane("text/html", localViewHTML.getHTML());
				
			}
			else if(isURI)
				_html = new JEditorPane(URI);
			else
				_html = new JEditorPane("text/html", URI);
		
			_html.addHyperlinkListener(new LinkListener(this));
			_html.setEditable(false);
			_html.setCaretPosition(0);
			
			scroll = new JScrollPane(_html);
			
			add(scroll, BorderLayout.CENTER);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public LocalViewHTMLExtractor getLocalViewHTML()
	{return localViewHTML;}
	
	public void updateState()
	{	
		//remove(scroll);
		
		int oldPosition = _html.getCaretPosition();
		_html.setText(localViewHTML.getHTML());
		_html.setCaretPosition(oldPosition);
		
		//scroll = new JScrollPane(_html);
	
		//add(scroll, BorderLayout.CENTER);
		
	}
	
	public void updateText(String uri)
	{
		//int oldPosition = _html.getCaretPosition();
		remove(scroll);
		String html = factory.loadDetailsHTML(uri);
		localViewHTML = new LocalViewHTMLExtractor(html);
		
		_html.setContentType("text/html");
		_html.setText(localViewHTML.getHTML());
		_html.setCaretPosition(0);
		scroll = new JScrollPane(_html);

		add(scroll, BorderLayout.CENTER);
	}
	
	public void setText(String text)
	{
		remove(scroll);
		//String html = factory.loadDetailsHTML(uri);
		//localViewHTML = new LocalViewHTMLExtractor(html);
		
		//_html = new JEditorPane("text/xhtml", text);
		_html.setContentType("text/xml");
		_html.setText(text);
		
		//_html.addHyperlinkListener(new LinkListener(this));
		_html.setEditable(false);
		_html.setCaretPosition(0);
		
		scroll = new JScrollPane(_html);
		
		add(scroll, BorderLayout.CENTER);
		
		/*_html.setText(text);
		_html.setCaretPosition(0);
		scroll = new JScrollPane(_html);

		add(scroll, BorderLayout.CENTER);*/

	}
}
