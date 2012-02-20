/*
 * AvatarView.java
 *
 * Created on Feb 24, 2011, 11:02:09 PM
 */

package probeIt.ui.avatar;

import java.util.*;
import java.awt.*;
import javax.swing.JFrame;

import probeIt.ProbeIt;
import probeIt.ui.model.ViewsModel;
import probeIt.ui.WindowApplication;
import probeIt.ui.avatar.video.*;
import probeIt.ui.initial.*;
import probeIt.ui.*;

/**
 * @author paulo
 */
public class AvatarView extends java.awt.Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//public static Container avatarParent;
    public static AvatarView instance;
    private boolean hasVideo = false;             // if uses video and voice recognition 

    public static final int NONE = 0;             // empty list of answers
    public static final int LIST_PUBLICATION = 1; // list of publications
    public static final int LIST_PRODUCT = 2;     // list of products
    public static final int LIST_RAW_DATA = 3;    // list of raw data

    final int XSIZE = 260;
    
    public static AuthorDialog authorUI;
    public static TypeDialog typeUI;
    public static AvatarOracle oracle = new AvatarOracle();

    private AvatarGeneric currentAvatar;
    private int answerType = NONE;

    public static AvatarView getInstance() {
       if(instance == null)
           instance = new AvatarView(false);
       return instance;
    }

    /** Creates new form AvatarUI */
    public AvatarView(boolean hasMultimedia) {
    	hasVideo = hasMultimedia;
    	//avatarParent = parentContainer;
    	instance = this;
        initComponents();
        if (hasVideo) {
            ((CardLayout)videoPanel.getLayout()).show(videoPanel,"card2");
        } else {
            ((CardLayout)videoPanel.getLayout()).show(videoPanel,"card3");
        }
        setAvatar(AvatarView.oracle);
        //clearAnswers();
        authorUI = new AuthorDialog((JFrame)(WindowApplication.getInstance()), true);
        authorUI.setLocation(250,350);
        typeUI = new TypeDialog((JFrame)(WindowApplication.getInstance()), true);
        typeUI.setLocation(250,400);
   }

    public void setAvatar(AvatarGeneric avt) {
        if (currentAvatar == null) {
            showVideo(MediaPanel.FIRST_WELCOME);
        } else {
            if (!currentAvatar.getName().equals(avt.getName())) {
                Comments.fire(Comments.WELCOME);
                if (avt.equals(oracle)) {
                  showVideo(MediaPanel.SIMPLE_WELCOME);
                }
            }
        }
        if (currentAvatar != null && !currentAvatar.equals(avt)) {
            ViewsModel.getInstance().newContext();
        }
        currentAvatar = avt;
        //jLabel1.setText(currentAvatar.getName());
        avatarName.setText(currentAvatar.getName());
        
        // new set of questions
        questionPanel.setModel(new javax.swing.AbstractListModel() {
            String[] strings = currentAvatar.getQuestions();
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        questionPanel.clearSelection();
        questionPanel.updateUI();
        
        // clear the state of the view manager for the new avatar and set it to show the answer
        if (avt.equals(AvatarView.oracle)) {
        	if (ViewsModel.getInstance().hasQuery()) {
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetAnswerTab();
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetWorkflowTab();
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetJustificationTab();
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetProvenanceTab();
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
        	} else {
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetTabs();        		
        		((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.WELCOME_TAB);
        	}
        } else {
    		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetQueryTab();
    		((ProbeItView)ViewsManager.getInstance().getViewPane()).buildWorkflowView();
    		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetJustificationTab();
    		((ProbeItView)ViewsManager.getInstance().getViewPane()).resetProvenanceTab();
        	((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.ANSWER_TAB);
        }
        ((ProbeItView)ViewsManager.getInstance().getViewPane()).updateUI();
 
        // new icon
        thePicture.setIcon(currentAvatar.getAvatarIcon());

        // refresh screen
        invalidate();
        validate();
    }

    public AvatarGeneric getAvatar() {
        return currentAvatar;
    }

    public void clearAvatarSelection() {
        questionPanel.clearSelection();
    }

    public void setAnswerType(int newType) {
        //clearAnswers();
        answerType = newType;
    }

    public void writeComment(String msg) {
        commentTextField.setText(msg);
    }

    public void showVideo(String videoType) {
        if (hasVideo) {
            mediaPanel1.invokeMediaPanel(videoType);
        }
    }
    
    public boolean getHasVideo() {
    	return hasVideo;
    }

    private void initComponents() {

    	videoPanel = new javax.swing.JPanel();
        mediaPanel1 = new MediaPanel();
        thePicture = new javax.swing.JLabel();
        avatarNameLabel = new javax.swing.JLabel();
        avatarName = new javax.swing.JTextField();
        questionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        questionPanel = new javax.swing.JList();
        commentLabel = new javax.swing.JLabel();
        commentTextField = new javax.swing.JTextField();
        filler = new javax.swing.JLabel();
        setBackground(java.awt.Color.lightGray);

        this.setMaximumSize(new java.awt.Dimension(XSIZE - 70, 700));
        this.setMinimumSize(new java.awt.Dimension(XSIZE - 70, 700));
        this.setPreferredSize(new java.awt.Dimension(XSIZE - 70, 700));
        this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        videoPanel.setMaximumSize(new java.awt.Dimension(XSIZE, 220));
        videoPanel.setMinimumSize(new java.awt.Dimension(XSIZE, 220));
        videoPanel.setPreferredSize(new java.awt.Dimension(XSIZE, 220));
        videoPanel.setLayout(new java.awt.CardLayout());

        mediaPanel1.setMaximumSize(new java.awt.Dimension(XSIZE, 220));
        mediaPanel1.setMinimumSize(new java.awt.Dimension(XSIZE, 220));
        mediaPanel1.setPreferredSize(new java.awt.Dimension(XSIZE, 220));
        mediaPanel1.setLayout(new java.awt.FlowLayout());
        thePicture.setMaximumSize(new java.awt.Dimension(XSIZE, 220));
        thePicture.setMinimumSize(new java.awt.Dimension(XSIZE, 220));
        thePicture.setPreferredSize(new java.awt.Dimension(XSIZE, 220));
        videoPanel.add(mediaPanel1, "card2");
        videoPanel.add(thePicture, "card3");

        this.add(videoPanel);

        avatarNameLabel.setText("Avatar Name:");
        avatarNameLabel.setAlignmentX(LEFT_ALIGNMENT);
        this.add(avatarNameLabel);

        avatarName.setBackground(new java.awt.Color(0, 0, 102));
        avatarName.setFont(new java.awt.Font("Copperplate Gothic Light", 1, 13));
        avatarName.setForeground(new java.awt.Color(255, 255, 255));
        avatarName.setMaximumSize(new java.awt.Dimension(XSIZE, 21));
        avatarName.setPreferredSize(new java.awt.Dimension(XSIZE, 21));
        avatarName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avatarNameActionPerformed(evt);
            }
        });
        this.add(avatarName);

        questionLabel.setText("Question:");
        questionLabel.setAlignmentX(LEFT_ALIGNMENT);
        this.add(questionLabel);

        questionPanel.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        questionPanel.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        questionPanel.setVisibleRowCount(10);
        questionPanel.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                questionPanelValueChanged(evt);
            }
        });

        jScrollPane1.setMaximumSize(new java.awt.Dimension(XSIZE, 140));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(XSIZE, 140));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(XSIZE, 140));
        jScrollPane1.setViewportView(questionPanel);

        this.add(jScrollPane1);

        commentLabel.setText("Comments:");
        commentLabel.setAlignmentX(LEFT_ALIGNMENT);
        commentLabel.setMaximumSize(new java.awt.Dimension(XSIZE, 16));
        commentLabel.setMinimumSize(new java.awt.Dimension(XSIZE, 16));
        commentLabel.setPreferredSize(new java.awt.Dimension(XSIZE, 16));
        this.add(commentLabel);

        commentTextField.setFont(new java.awt.Font("Tahoma", 1, 13));
        commentTextField.setForeground(new java.awt.Color(204, 0, 0));
        commentTextField.setMaximumSize(new java.awt.Dimension(XSIZE, 66));
        commentTextField.setMinimumSize(new java.awt.Dimension(XSIZE, 66));
        commentTextField.setPreferredSize(new java.awt.Dimension(XSIZE, 66));
        this.add(commentTextField);

        filler.setMaximumSize(new java.awt.Dimension(XSIZE, 3000));
        filler.setMinimumSize(new java.awt.Dimension(XSIZE, 5));
        //filler.setPreferredSize(new java.awt.Dimension(XSIZE, 3000));
        this.add(filler);

    }

    /*
     * This method identifies that the user has asked for a question. The
     * number of the selection is returned to the appropriate avatar who is
     * going to addressing the request.
     */
    private void questionPanelValueChanged(javax.swing.event.ListSelectionEvent evt) {
        Comments.clear();
        int selectedIndex = questionPanel.getSelectedIndex();
        currentAvatar.performQuestion(selectedIndex);
    }

    private void avatarNameActionPerformed(java.awt.event.ActionEvent evt) {
    }

    public void setSelection(String selection) {
    	System.out.println("Selection: " + selection);
    	System.out.println(answerType);
    	switch (answerType) {
        case NONE: break;
        case LIST_PUBLICATION: {
             currentAvatar.setSelectedPublication(selection);
             break;
             }
        case LIST_PRODUCT: {
             currentAvatar.setSelectedProduct(selection);
             break;
             }
    	}
    }
    
    // Variables declaration
    private javax.swing.JTextField avatarName;
    private javax.swing.JLabel avatarNameLabel;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JTextField commentTextField;
    private javax.swing.JLabel filler;
    private javax.swing.JScrollPane jScrollPane1;
    private MediaPanel mediaPanel1;
    private javax.swing.JLabel questionLabel;
    private javax.swing.JList questionPanel;
    private javax.swing.JLabel thePicture;
    private javax.swing.JPanel videoPanel;
    // End of variables declaration

}
