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
import probeIt.ui.ProbeItView;
import probeIt.ui.ViewsManager;
import probeIt.ui.avatar.triplestore.IndividualComboBox;
import probeIt.ui.avatar.triplestore.ListAuthors;
import probeIt.ui.avatar.triplestore.ListDataProductTypes;
import probeIt.ui.avatar.queries.ProductsByType;
import probeIt.ui.avatar.video.*;
import probeIt.ui.model.ViewsModel;

import javax.swing.*;

/**
 *
 * @author paulo
 */
public class TypeDialog extends javax.swing.JDialog {

	public static TypeDialog typeDialog;
	
    public ProductsByType productQuery = new ProductsByType();

    /** Creates new form AuthorDialog */
    public TypeDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        typeDialog = this;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new ListDataProductTypes();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setResizable(false);

        jLabel1.setText("Type");
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

    // The Cancel button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        AvatarView.getInstance().setAvatar(AvatarView.oracle);
        AvatarView.typeUI.setVisible(false);
    }

    // The Submit button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String typeURI = ((IndividualComboBox.Individual)(jComboBox1).getSelectedItem()).getURI();
        System.out.println("Acionado: " + typeURI);
		//ProbeIt.getInstance().getWindow().setURI(productQuery.doQuery(typeURI));
        ViewsModel.getInstance().setQuery(productQuery.doQuery(typeURI));
        ((ProbeItView)ViewsManager.getInstance().getViewPane()).setActiveIndex(ProbeItView.QUERY_TAB);
        //ViewsManager.getInstance().updatePane();
        /*if (query.setProducts(((JComboBox)(evt.getSource())).getSelectedItem().toString())) {
                AvatarView.getInstance().showVideo(MediaPanel.HAS_ANSWER);
        } else {
                Comments.fire(Comments.NO_ANSWER);
                AvatarView.getInstance().showVideo(MediaPanel.NO_ANSWER);
        }*/
        AvatarView.getInstance().setAvatar(AvatarView.oracle);
        AvatarView.typeUI.setVisible(false);
    }

    // The selection of one of the types
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        //String typeURI = ((IndividualComboBox.Individual)((IndividualComboBox)evt.getSource()).getSelectedItem()).getURI();
        //System.out.println("Acionado: " + typeURI);
		//ProbeIt.getInstance().getWindow().setURI(productQuery.doQuery(typeURI));
        /*if (query.setProducts(((JComboBox)(evt.getSource())).getSelectedItem().toString())) {
                AvatarView.getInstance().showVideo(MediaPanel.HAS_ANSWER);
        } else {
                Comments.fire(Comments.NO_ANSWER);
                AvatarView.getInstance().showVideo(MediaPanel.NO_ANSWER);
        }*/
        //AvatarView.getInstance().setAvatar(AvatarView.oracle);
        //AvatarView.typeUI.setVisible(false);
    }

    public ListDataProductTypes getListDataProductType() {
    	return jComboBox1;
    }
    
    // Variables declaration
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private ListDataProductTypes jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration

}
