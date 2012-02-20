/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AuthorDialog.java
 *
 * Created on Feb 26, 2011, 1:13:30 PM
 */

package probeIt.ui.avatar;

import probeIt.ProbeIt;
import probeIt.ui.avatar.triplestore.IndividualComboBox;
import probeIt.ui.avatar.triplestore.ListAuthors;
import probeIt.ui.avatar.queries.PublicationsByAuthor;
import probeIt.ui.avatar.video.*;
import probeIt.ui.model.ViewsModel;

import javax.swing.*;

/**
 * @author paulo
 */
public class AuthorDialog extends javax.swing.JDialog {

	public static AuthorDialog authorDialog;
	
    public static PublicationsByAuthor publicationQuery = new PublicationsByAuthor();

    /** Creates new form AuthorDialog */
    public AuthorDialog(javax.swing.JFrame parent, boolean modal) {
        super(parent, modal);
        authorDialog = this;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new ListAuthors();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setResizable(false);

        jLabel1.setText("Author");
        jPanel1.add(jLabel1);

        jComboBox1.setPreferredSize(new java.awt.Dimension(300, 22));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBox1);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jButton1.setText("Cancel");
        jButton1.setMaximumSize(new java.awt.Dimension(90, 30));
        jButton1.setMinimumSize(new java.awt.Dimension(90, 30));
        jButton1.setOpaque(false);
        jButton1.setPreferredSize(new java.awt.Dimension(90, 30));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setText("Submit");
        jButton2.setMaximumSize(new java.awt.Dimension(90, 30));
        jButton2.setMinimumSize(new java.awt.Dimension(90, 30));
        jButton2.setOpaque(false);
        jButton2.setPreferredSize(new java.awt.Dimension(90, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        
        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        AvatarView.getInstance().setAvatar(AvatarView.oracle);
        AvatarView.authorUI.setVisible(false);
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String authorURI = ((IndividualComboBox.Individual)((IndividualComboBox)(jComboBox1)).getSelectedItem()).getURI();
        ViewsModel.getInstance().setQuery(publicationQuery.doQuery(authorURI));
        //ProbeIt.getInstance().getWindow().setURI(publicationQuery.doQuery(authorURI));
        //System.out.println("Acionado: " + ((IndividualComboBox.Individual)((IndividualComboBox)evt.getSource()).getSelectedItem()).getURI());
        //if (query.setPublications(((JComboBox)(evt.getSource())).getSelectedItem().toString())) {
        //        AvatarView.getInstance().showVideo(MediaPanel.HAS_ANSWER);
        //} else {
        //        Comments.fire(Comments.NO_ANSWER);
        //        AvatarView.getInstance().showVideo(MediaPanel.NO_ANSWER);
        // }
		
        AvatarView.getInstance().setAvatar(AvatarView.oracle);
        AvatarView.authorUI.setVisible(false);
    }

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        //String authorURI = ((IndividualComboBox.Individual)((IndividualComboBox)evt.getSource()).getSelectedItem()).getURI();
		//ProbeIt.getInstance().getWindow().setURI(publicationQuery.doQuery(authorURI));
        //System.out.println("Acionado: " + ((IndividualComboBox.Individual)((IndividualComboBox)evt.getSource()).getSelectedItem()).getURI());
        //if (query.setPublications(((JComboBox)(evt.getSource())).getSelectedItem().toString())) {
        //        AvatarView.getInstance().showVideo(MediaPanel.HAS_ANSWER);
        //} else {
        //        Comments.fire(Comments.NO_ANSWER);
        //        AvatarView.getInstance().showVideo(MediaPanel.NO_ANSWER);
        // }
        //AvatarView.getInstance().setAvatar(AvatarView.oracle);
        //AvatarView.authorUI.setVisible(false);
    }

    public ListAuthors getListAuthors() {
    	return jComboBox1;
    }
    
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AuthorDialog dialog = new AuthorDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private ListAuthors jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration

}
