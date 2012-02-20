package probeIt.ui.local.swing;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class ShowButton extends JButton
{
	JPanel showhideP;
	boolean visible;
	String show, hide;

	public ShowButton(JPanel showP, String text)
	{
		super(text);

		if(showP == null)
		{
			setHorizontalAlignment(JButton.LEADING);
			setBackground(Color.gray);
			setBorder(new LineBorder(Color.black));
		}
		else
		{	
			setHorizontalAlignment(JButton.LEADING);
			setBackground(Color.yellow);
			setBorder(new LineBorder(Color.black));

			visible = false;
			showhideP = showP;
			showhideP.setVisible(visible);

			this.addActionListener(new java.awt.event.ActionListener(){
				public void actionPerformed(java.awt.event.ActionEvent evt) {

					if(!visible)
					{	
						visible = true;
						showhideP.setVisible(visible);
					}
					else 
					{
						visible = false;
						showhideP.setVisible(visible);
					}
				}
			});
		}
	}

	public ShowButton(JPanel showP, String text, boolean visibility)
	{
		super(text);
		setHorizontalAlignment(JButton.LEADING);
		setBackground(Color.yellow);
		setBorder(new LineBorder(Color.black));

		visible = visibility;
		showhideP = showP;
		showhideP.setVisible(visible);

		this.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if(!visible)
				{	
					visible = true;
					showhideP.setVisible(visible);
				}
				else 
				{
					visible = false;
					showhideP.setVisible(visible);
				}
			}
		});
	}

	public ShowButton(JPanel showP, String showText, String hideText)
	{
		super(showText);
		setHorizontalAlignment(JButton.LEADING);
		setBackground(Color.yellow);
		setBorder(new LineBorder(Color.black));

		visible = false;
		showhideP = showP;
		showhideP.setVisible(visible);
		show = showText;
		hide = hideText;


		this.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if(!visible)
				{	
					visible = true;
					showhideP.setVisible(true);

					ShowButton button = ((ShowButton)evt.getSource());//.setText(show);
					button.setText(button.hide);
				}
				else 
				{
					visible = false;
					showhideP.setVisible(false);

					ShowButton button = ((ShowButton)evt.getSource());//.setText(hide);
					button.setText(button.show);
				}
			}
		});
	}

	public ShowButton(JPanel showP, String showText, String hideText, Boolean visibility)
	{
		super(showText);
		setHorizontalAlignment(JButton.LEADING);
		setBackground(Color.yellow);
		setBorder(new LineBorder(Color.black));

		visible = visibility;
		showhideP = showP;
		showhideP.setVisible(visible);
		show = showText;
		hide = hideText;


		this.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				if(!visible)
				{	
					visible = true;
					showhideP.setVisible(true);

					ShowButton button = ((ShowButton)evt.getSource());//.setText(show);
					button.setText(button.hide);
				}
				else 
				{
					visible = false;
					showhideP.setVisible(false);

					ShowButton button = ((ShowButton)evt.getSource());//.setText(hide);
					button.setText(button.show);
				}
			}
		});
	}
}
