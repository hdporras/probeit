package probeIt.ui.initial;

import gmt.GMTToolkit.ConnectionType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import probeIt.ProbeIt;
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.util.*;

/**
 * Displays Probe-It background image when no query or node set is being
 * displayed.
 * 
 * @author paulo
 * 
 */
public class InitialView extends JPanel
{
	//JarResourceLoader loader = 
	//public static final ImageIcon image = (new JarResourceLoader()).getImageIcon(InitialView.BACKGROUND_IMAGE);
	//public static final String BACKGROUND_IMAGE = "probeIt-bg2.PNG";

	public static InitialView instance;

	private JButton avatarWMM;
	private JButton avatarWOMM;
	private JButton classic;
	private JPanel buttonPanel;
	
	// Background image
	//private JLabel backgroundImage;

	private InitialView()
	{
		
		avatarWMM = new JButton();
		avatarWMM.setLayout(new BorderLayout());
		JLabel label1 = new JLabel("Avatar WITH");
		JLabel label2 = new JLabel("Multimedia");
		avatarWMM.add(BorderLayout.NORTH,label1);
		avatarWMM.add(BorderLayout.SOUTH,label2);
		avatarWMM.setBackground(new java.awt.Color(255, 187, 120));
        avatarWMM.setMaximumSize(new java.awt.Dimension(120, 50));
        avatarWMM.setMinimumSize(new java.awt.Dimension(120, 50));
        avatarWMM.setOpaque(true);
        avatarWMM.setPreferredSize(new java.awt.Dimension(120, 50));
        avatarWMM.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED), new EmptyBorder(5,5,5,5))) ;  
        avatarWMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //jButton1ActionPerformed(evt);
            }
        });

		avatarWOMM = new JButton();
		avatarWOMM.setLayout(new BorderLayout());
		label1 = new JLabel("Avatar WITHOUT");
		label2 = new JLabel("Multimedia");
		avatarWOMM.add(BorderLayout.NORTH,label1);
		avatarWOMM.add(BorderLayout.SOUTH,label2);
		avatarWOMM.setBackground(new java.awt.Color(255, 187, 120));
        avatarWOMM.setMaximumSize(new java.awt.Dimension(120, 50));
        avatarWOMM.setMinimumSize(new java.awt.Dimension(120, 50));
        avatarWOMM.setOpaque(true);
        avatarWOMM.setPreferredSize(new java.awt.Dimension(120, 50));
        avatarWOMM.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED), new EmptyBorder(5,5,5,5))) ;  
        avatarWOMM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).buildAvatarView(false);
        		ViewsManager.getInstance().enableFeature_A();
        		ProbeIt.getInstance().getWindow().getConfiguration().setColapsedValue(true);
        		ProbeIt.getInstance().getWindow().getConfiguration().setHasAvatar(true);
        		ProbeIt.getInstance().getWindow().updateView();
            }
        });

		classic = new JButton("Classic");
		classic.setBackground(new java.awt.Color(255, 187, 120));
        classic.setMaximumSize(new java.awt.Dimension(120, 50));
        classic.setMinimumSize(new java.awt.Dimension(120, 50));
        classic.setOpaque(true);
        classic.setPreferredSize(new java.awt.Dimension(120, 50));
        classic.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED), new EmptyBorder(5,5,5,5))) ;  
        classic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
        		ViewsManager.getInstance().disableFeature_A();
        		ProbeIt.getInstance().getWindow().getConfiguration().setColapsedValue(false);
        		ProbeIt.getInstance().getWindow().getConfiguration().setHasAvatar(false);
        		ProbeIt.getInstance().getWindow().updateView();
            }
        });

        buttonPanel = new JPanel();
		buttonPanel.setBackground(new java.awt.Color(255, 204, 153));
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(avatarWMM);
        buttonPanel.add(avatarWOMM);
        buttonPanel.add(classic);
        
		this.setBackground(new java.awt.Color(255, 204, 153));
		// Read image from a file
		//JarResourceLoader loader = new JarResourceLoader();
		//ImageIcon image = loader.getImageIcon(InitialView.BACKGROUND_IMAGE);
		Icon logoIcon = new ImageIcon(getClass().getResource("/probeIt/image/probeIt-bg2.PNG"));
		//while (image.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) { 
		//	System.out.println("Image loading incomplete");
		//}
		JLabel backgroundImage = new JLabel(logoIcon);
		//System.out.println("Image: " + image);
	
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder());
		//if (backgroundImage != null)
		//{
		add(backgroundImage, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
		
		//}

	}

	public static InitialView getInstance() {
		if (instance == null)
			instance = new InitialView();
		
		return instance;
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.add(new InitialView());
		frame.setVisible(true);
	}

	
}