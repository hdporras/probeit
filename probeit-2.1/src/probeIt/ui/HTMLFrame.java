package probeIt.ui;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;

public class HTMLFrame extends JFrame implements HyperlinkListener
{

	private JEditorPane _html;

	public HTMLFrame(String _site, String _title)
	{
		setTitle(_title);
		setSize(700, 600);
		getContentPane().setLayout(new BorderLayout());
		JPanel _tp = new JPanel();
		_tp.setLayout(new BorderLayout());
		getContentPane().add(_tp, BorderLayout.CENTER);

		try
		{
			URL url = new URL(_site);
			_html = new JEditorPane(url);
			_html.setEditable(false);

			JScrollPane _sp = new JScrollPane();
			_sp.getViewport().add(_html, BorderLayout.CENTER);

			_tp.add(_sp, BorderLayout.CENTER);
			_html.addHyperlinkListener(this);
		} catch (MalformedURLException e)
		{
			System.out.println("Malformed URL: " + e);
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});
	}

	public HTMLFrame(String _site, String _title, boolean isURL)
	{
		setTitle(_title);
		setSize(700, 600);
		getContentPane().setLayout(new BorderLayout());
		JPanel _tp = new JPanel();
		_tp.setLayout(new BorderLayout());
		getContentPane().add(_tp, BorderLayout.CENTER);

		try
		{
			if (isURL)
			{
				URL url = new URL(_site);
				_html = new JEditorPane(url);
				_html.setEditable(false);
			} else
			{
				_html = new JEditorPane();
				_html.setEditable(false);
				String nsStr = "";

				nsStr = _site;
				_html.setContentType("text/html");
				_html.setText(_site);
			}

			JScrollPane _sp = new JScrollPane();
			_sp.getViewport().add(_html, BorderLayout.CENTER);

			_tp.add(_sp, BorderLayout.CENTER);
			_html.addHyperlinkListener(this);
		} catch (MalformedURLException e)
		{
			System.out.println("Malformed URL: " + e);
		} catch (IOException e)
		{
			System.out.println("IOException: " + e);
		}
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
			}
		});
	}

	public void hyperlinkUpdate(HyperlinkEvent event)
	{
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		{
			Cursor _c = _html.getCursor();
			Cursor _wait = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
			JEditorPane _pane = (JEditorPane) event.getSource();
			if (event instanceof HTMLFrameHyperlinkEvent)
			{
				HTMLFrameHyperlinkEvent _evt = (HTMLFrameHyperlinkEvent) event;
				HTMLDocument _doc = (HTMLDocument) _pane.getDocument();
				_doc.processHTMLFrameHyperlinkEvent(_evt);
			} else
			{
				try
				{
					_pane.setPage(event.getURL());
				} catch (Throwable _t)
				{
					_t.printStackTrace();
				}
			}
			_html.setCursor(_c);
		}
	}

}
