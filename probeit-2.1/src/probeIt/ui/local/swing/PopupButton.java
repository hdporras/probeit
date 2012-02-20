package probeIt.ui.local.swing;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.popup.provenance.PopupHTMLView;
import pml.*;

public class PopupButton extends JButton 
{
	String uri;
	//PopupHTMLView popup;
	
	public PopupButton(String text, String _uri)
	{
		super(text);
		uri = _uri;
		//setHorizontalAlignment(JButton.LEADING);
		setBackground(Color.yellow);
		setBorder(new LineBorder(Color.black));
		
		this.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				if(uri != null)				
					new PopupHTMLView(uri, true);
				else
					System.out.println("Warning -- Link to URI is NULL.");
			}
		});
	}
}
