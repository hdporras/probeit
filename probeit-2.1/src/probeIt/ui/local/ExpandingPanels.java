package probeIt.ui.local;

import java.awt.*;  
import java.awt.event.*;  
import java.awt.font.*;  
import java.awt.image.BufferedImage;  
import javax.swing.*;  
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import probeIt.ui.local.swing.*;

public class ExpandingPanels extends MouseAdapter  
{  
    public ActionPanel[] aps;  
    JPanel[] panels;
    
    
    //inner panel
    //JPanel showing, showParents, showing2;
    //boolean alt = true, altpar = true, alt2 = true;
    //JButton show, parents, show2;

    public ExpandingPanels(JPanel p1, JPanel p2, JPanel p3, JPanel p4)  
    {  
        assembleActionPanels();  
        assemblePanels(p1, p2, p3, p4);
    }  

    public void mousePressed(MouseEvent e)
    {  
        ActionPanel ap = (ActionPanel)e.getSource();  
        if(ap.contains(e.getPoint()))  
        {  
            toggleVisibility(ap);
        }  
    }
    
    public void toggleVisibility(ActionPanel ap)
    {
    	ap.toggleSelection();
        togglePanelVisibility(ap);
    }

    private void togglePanelVisibility(ActionPanel ap)  
    {  
        int index = getPanelIndex(ap);
        if(panels[index].isShowing())
            panels[index].setVisible(false);  
        else
            panels[index].setVisible(true);  
        ap.getParent().validate();
    }

    private int getPanelIndex(ActionPanel ap)  
    {  
        for(int j = 0; j < aps.length; j++)  
            if(ap == aps[j])  
                return j;  
        return -1;  
    }  

    private void assembleActionPanels()  
    {  
        String[] ids = { "Conclusion", "Justified by", "Used to Infer", "Used to Finally Conclude" };  
        aps = new ActionPanel[ids.length];  
        for(int j = 0; j < aps.length; j++)
            aps[j] = new ActionPanel(ids[j], this);
        
        /*if(aps.length>0)
        {
        	aps[0].toggleSelection();
        	aps[0].setVisible(true);
        	aps[0].getParent().validate();
        }*/
        
    }  

    private void assemblePanels(JPanel p1, JPanel p2, JPanel p3, JPanel p4)  
    {

        //P1 (Conclusion)
        /*JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        JLabel text = new JLabel();
		//text.setEditable(false);
		text.setText("~ hates(butler, butler)  |  ~ hates(butler, charles)");
		text.setBorder(new EtchedBorder());
		
		
		//Conclusion metadata
		JPanel metadataBP = new JPanel();
		metadataBP.setLayout(new BoxLayout(metadataBP, BoxLayout.Y_AXIS));
		metadataBP.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));//new BevelBorder(BevelBorder.RAISED));
		
		JPanel metadataP = new JPanel();
		metadataP.setBorder(new BevelBorder(BevelBorder.RAISED));
		JLabel metaLbl = new JLabel("No additional metadata");
		metadataP.add(metaLbl);
		
		JPanel metaBtnP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ShowButton metaBtn = new ShowButton(metadataP, "show metadata", "hide metadata");
		metaBtn.setBackground(Color.gray);
		metaBtnP.add(metaBtn);
		
		metadataBP.add(metaBtnP);
		metadataBP.add(metadataP);
		metadataBP.setBackground(new Color(200,200,200));
		metaBtnP.setBackground(new Color(200,200,200));
		
		p1.add(text);
		p1.add(metadataBP);*/

		
		//P2 (Justified By)
		/*JPanel p2 = new JPanel();
		p2.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		
		
		JPanel justP = new JPanel(new BorderLayout());
		JLabel first = new JLabel("1.");
        justP.add(first, BorderLayout.WEST);
        justP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        
        
        JPanel justContent = new JPanel(new FlowLayout(FlowLayout.LEFT));
        justContent.setLayout(new BoxLayout(justContent, BoxLayout.Y_AXIS));
        //justContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        justContent.setBorder(new BevelBorder(BevelBorder.RAISED));//new LineBorder(Color.black));
        
        //inferred by inference engine....
        JPanel justified = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel inferL = new JLabel("Inferred by inference engine ");
        
        PopupButton engineB = new PopupButton("EP 0.999", "http://inference-web.org/registry/IE/EP.owl#EP");
		engineB.setHorizontalAlignment(JButton.LEADING); // optional
		//engineB.setBackground(Color.yellow);
		//engineB.setBorder(new LineBorder(Color.black));
		
		JLabel declL = new JLabel("with declarative rule ");
		
		JButton declB = new JButton("EP spm");
		declB.setHorizontalAlignment(JButton.LEADING);
		declB.setBackground(Color.yellow);
		declB.setBorder(new LineBorder(Color.black));
		
		JLabel from = new JLabel("from the ");
		
		JPanel showParents = new JPanel();
		
		ShowButton parents = new ShowButton(showParents,"parents:");
		
		
		//showParents.setBorder(new LineBorder(Color.black));//BevelBorder(BevelBorder.RAISED));//LineBorder(Color.black));
		showParents.setLayout(new BoxLayout(showParents, BoxLayout.Y_AXIS));
		
		JLabel firstPL = new JLabel("1. ");
        LinkButton firstPB = new LinkButton("~ hates(X1, agatha)  |  ~ hates(X1, butler)  |  ~ hates(X1, charles)", "http://inference-web.org/proofs/tptp/Solutions/PUZ/PUZ001-1/EP---0.999/answer.owl#ns_6");
                
        JPanel firstP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstP.add(firstPL);
        firstP.add(firstPB);
        
        JLabel secondPL = new JLabel("2. ");
        LinkButton secondPB = new LinkButton("hates(butler, agatha)", null);
        
        JPanel secondP = new JPanel(new FlowLayout(FlowLayout.LEFT));
        secondP.add(secondPL);
        secondP.add(secondPB);
        
        showParents.add(firstP);
        showParents.add(secondP);
		
		
		justified.add(inferL);
		justified.add(engineB);
		justified.add(declL);
		justified.add(declB);
		justified.add(from);
		justified.add(parents);
		justified.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		justContent.add(justified);
		justContent.add(showParents);
		
		//content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		justP.add(justContent, BorderLayout.CENTER);
		justP.setBackground(new Color(230,230,230));

		//metadata
		JPanel metadataContent = new JPanel();
		metadataContent.setLayout(new BoxLayout(metadataContent, BoxLayout.Y_AXIS));
		metadataContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));//new BevelBorder(BevelBorder.RAISED));
		
		JPanel metadata = new JPanel();
		metadata.setBorder(new BevelBorder(BevelBorder.RAISED));
		JLabel metaL = new JLabel("No additional metadata");
		metadata.add(metaL);
		
		JPanel metaButtonP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ShowButton metaB = new ShowButton(metadata, "show metadata", "hide metadata");
		metaB.setBackground(Color.gray);
		metaButtonP.add(metaB);
		
		metadataContent.setBackground(new Color(200,200,200));
		metaButtonP.setBackground(new Color(200,200,200));
		
		
		//Assertions
		JPanel assertionContent = new JPanel();
		assertionContent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		assertionContent.setLayout(new BoxLayout(assertionContent, BoxLayout.Y_AXIS));
		
		JPanel assertionsP = new JPanel();
		assertionsP.setLayout(new BoxLayout(assertionsP, BoxLayout.Y_AXIS));
		assertionsP.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JPanel fP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		assertionsP.add(fP);
		JLabel a1L = new JLabel("1.");
		fP.add(a1L);
		LinkButton a1B = new LinkButton("~ hates(X1, agatha) | ~ hates(X1, butler) | ~ hates(X1, charles)", null);
		fP.add(a1B);
		
		JPanel assertionsTitleP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel descended = new JLabel("Descended from the ");
		ShowButton assertionsB = new ShowButton(assertionsP,"assertions:");
		assertionsTitleP.add(descended);
		assertionsTitleP.add(assertionsB);
		
		assertionContent.add(assertionsTitleP);
		assertionContent.add(assertionsP);
		assertionsTitleP.setBackground(new Color(230,230,230));
		assertionContent.setBackground(new Color(230,230,230));
		
		metadataContent.add(metaButtonP);
		metadataContent.add(metadata);
		
		p2.add(justP);
		p2.add(metadataContent);
		p2.add(assertionContent);
		*/
		//P3 (Used to Infer)
		/*
        JPanel p3 = new JPanel(new BorderLayout());
        JLabel one = new JLabel("1.");
        p3.add(one, BorderLayout.WEST);
        p3.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        //content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JPanel inferred = new JPanel(new FlowLayout(FlowLayout.LEFT));
        LinkButton b = new LinkButton("hates(butler, butler)", null);	
		JLabel help = new JLabel("with help of: ");
		
		JPanel showing = new JPanel();//new FlowLayout(FlowLayout.LEFT));
		showing.setLayout(new BoxLayout(showing, BoxLayout.Y_AXIS));
		ShowButton show = new ShowButton(showing, "show", "hide");
		
		JPanel showingOne = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel s1 = new JLabel("1. ");
        LinkButton sb = new LinkButton("hates(butler, charles)", null);
		
		showingOne.add(s1);
		showingOne.add(sb);
		showing.add(showingOne);
		//showing.setVisible(false);
		
		inferred.add(b);
		inferred.add(help);
		inferred.add(show);
		inferred.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		content.add(inferred);
		content.add(showing);
		content.setBorder(new BevelBorder(BevelBorder.RAISED));//LineBorder(Color.black));
		//content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		p3.add(content, BorderLayout.CENTER);
        */
        
		//p4
		
		/*JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));
		
		JPanel finalCP = new JPanel();
		finalCP.setLayout(new BoxLayout(finalCP, BoxLayout.Y_AXIS));
		finalCP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JPanel conclusionP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel firstCL = new JLabel("1. ");
		LinkButton conclusionB = new LinkButton("$false", null);
		conclusionP.add(firstCL);
		conclusionP.add(conclusionB);
		conclusionP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		conclusionP.setBackground(new Color(230,230,230));
		
		JPanel cnfP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cnfP.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JPanel cnfBorderP = new JPanel();
		cnfBorderP.setLayout(new BoxLayout(cnfBorderP, BoxLayout.Y_AXIS));
		cnfBorderP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JLabel cnfText1 = new JLabel(" cnf(prove_neither_charles_nor_butler_did_it,negated_conjecture, ");
		JLabel cnfText2 = new JLabel("( killed(butler,agatha) ");
		JLabel cnfText3 = new JLabel("| killed(charles,agatha) )). ");
		cnfBorderP.add(cnfText1);
		cnfBorderP.add(cnfText2);
		cnfBorderP.add(cnfText3);
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
		
		
		JPanel repAnswerP = new JPanel(new FlowLayout(FlowLayout.LEFT));
		repAnswerP.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		JPanel repBorderP = new JPanel();
		repBorderP.setLayout(new BoxLayout(repBorderP, BoxLayout.Y_AXIS));
		repBorderP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JLabel repText1 = new JLabel("Someone who lives in Dreadbury Mansion killed Aunt Agatha. ");
		JLabel repText2 = new JLabel("Agatha, the butler, and Charles live in Dreadbury Mansion, ");
		JLabel repText3 = new JLabel("and are the only people who live therein. A killer always ");
		JLabel repText4 = new JLabel("hates his victim, and is never richer than his victim. ");
		JLabel repText5 = new JLabel("Charles hates no one that Aunt Agatha hates. Agatha hates ");
		JLabel repText6 = new JLabel("everyone except the butler. The butler hates everyone not ");
		JLabel repText7 = new JLabel("richer than Aunt Agatha. The butler hates everyone Aunt ");
		JLabel repText8 = new JLabel("Agatha hates. No one hates everyone. Agatha is not the ");
		JLabel repText9 = new JLabel("butler. Therefore : Agatha killed herself. ");
		
		//JScrollPane editorScrollPane = new JScrollPane(repText);
        //editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //editorScrollPane.setMinimumSize(new Dimension(300, 300));


		repBorderP.add(repText1);
		repBorderP.add(repText2);
		repBorderP.add(repText3);
		repBorderP.add(repText4);
		repBorderP.add(repText5);
		repBorderP.add(repText6);
		repBorderP.add(repText7);
		repBorderP.add(repText8);
		repBorderP.add(repText9);
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
		
		finalCP.add(conclusionP);
		finalCP.add(answersP);
		finalCP.add(formalRepP);
		
		
		p4.add(finalCP);*/
        //JTextArea textArea = new JTextArea(8,12);  
        //textArea.setLineWrap(true);  
        //p4.add(new JScrollPane(textArea));  

       /* justP.setBackground(Color.white);
        p1.setBackground(Color.white);
        p2.setBackground(Color.white);
        p3.setBackground(Color.white);
        text.setBackground(Color.gray);*/
        
    	
    	
        //Add hiding/showing Panels
        panels = new JPanel[] { p1, p2, p3, p4 };  
        
    }  

    private void addComponents(Component c1, Component c2, Container c,  
            GridBagConstraints gbc)  
    {  
        gbc.anchor = gbc.EAST;  
        gbc.gridwidth = gbc.RELATIVE;  
        c.add(c1, gbc);  
        gbc.anchor = gbc.WEST;  
        gbc.gridwidth = gbc.REMAINDER;  
        c.add(c2, gbc);  
        gbc.anchor = gbc.CENTER;  
    }  

    public JPanel getComponent()  
    {  
        JPanel panel = new JPanel(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();  
        gbc.insets = new Insets(1,3,0,3);  
        gbc.weightx = 1.0;  
        gbc.fill = gbc.HORIZONTAL;  
        gbc.gridwidth = gbc.REMAINDER;  
        for(int j = 0; j < aps.length; j++)  
        {  
            panel.add(aps[j], gbc);  
            panel.add(panels[j], gbc);  
            panels[j].setVisible(false);  
        }  
        JLabel padding = new JLabel();  
        gbc.weighty = 1.0;  
        panel.add(padding, gbc);  
        return panel;  
    }  

   /* public static void main(String[] args)  
    {  
        ExpandingPanels test = new ExpandingPanels();  
        JFrame f = new JFrame();  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        f.getContentPane().add(new JScrollPane(test.getComponent()));  
        f.setSize(360,500);  
        f.setLocation(200,100);  
        f.setVisible(true);  
    }*/  
}