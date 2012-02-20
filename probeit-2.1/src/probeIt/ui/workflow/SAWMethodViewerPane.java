package probeIt.ui.workflow;

import java.awt.Dimension;

import javax.swing.JPanel;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.Ontology;

/**
 * GUI to edit properties of Method instances included in a SAW
 * @author Leonardo Salayandia
 */
public class SAWMethodViewerPane extends JPanel {
	private static final long serialVersionUID = 1L;	
    private javax.swing.JScrollPane commentJScrollPane;
    private javax.swing.JTextArea comment;
    private javax.swing.JLabel commentLabel;
    private javax.swing.JTextField detailedBy;
    private javax.swing.JLabel detailedByJLabel;
    private javax.swing.JLabel infEngineJLabel;
    private javax.swing.JTextField infEngine;
    private javax.swing.JLabel nameJLabel;
    private javax.swing.JTextField name;
    private javax.swing.JLabel typeJLabel;
    private javax.swing.JTextField type;
    private javax.swing.JLabel sourceJLabel;
    private javax.swing.JTextField source;

    /** Creates new form SAWMethodViewerPane */
    public SAWMethodViewerPane() {
        initComponents();
    }
    
    /**
     * Initialize window to edit a SAW instance.
     * @param methodURI
     */
    public void setMethod(String methodURI) {
    	if (methodURI != null && !methodURI.isEmpty()) {
    		String sawURI = URI.getNameSpace(methodURI);
    		OntModel saw = SAW.readSAW(sawURI);
    		if (saw != null) {
    			setMethod(saw.getOntClass(methodURI));
    			return;
    		}
    	}
    	// something was null, clear pane
    	clearPane();
    }
    
    public void setMethod(OntClass method) {
    	if (method != null) {
			type.setText(SAW.getClassQName(SAW.getSAWInstanceType(method)));
    		name.setText(SAW.getSAWInstanceLabel(method));
    		comment.setText(SAW.getSAWInstanceComment(method));
    		
    		Individual ie = SAW.getInferenceEngine(method);
    		infEngine.setText((ie == null) ? "" : ie.getURI());
    		
    		Individual src = SAW.getPMLSource(method);
    		source.setText((src == null) ? "" : src.getURI());
    		
    		Ontology sawOnt = SAW.getDetailedBy(method);
    		detailedBy.setText((sawOnt == null) ? "" : sawOnt.getURI());
		}
    	else {
        	// method was null, clear pane
        	clearPane();
    	}
	}
    
    /**
     * Clear pane fields
     */
    private void clearPane() {
    	type.setText("");
    	name.setText("");
    	comment.setText("");
    	infEngine.setText("");
    	source.setText("");
    	detailedBy.setText("");
    }


    /** 
     * This method is called from within the constructor to
     * initialize the form.
     */
    private void initComponents() {

    	name = new javax.swing.JTextField();
        commentJScrollPane = new javax.swing.JScrollPane();
        comment = new javax.swing.JTextArea();
        commentLabel = new javax.swing.JLabel();
        nameJLabel = new javax.swing.JLabel();
        typeJLabel = new javax.swing.JLabel();
        infEngineJLabel = new javax.swing.JLabel();
        type = new javax.swing.JTextField();
        infEngine = new javax.swing.JTextField();
        detailedByJLabel = new javax.swing.JLabel();
        detailedBy = new javax.swing.JTextField();
        sourceJLabel = new javax.swing.JLabel();
        source = new javax.swing.JTextField();
        
        setName("SAWMethodViewerPane"); // NOI18N
        
        name.setEditable(false);
        name.setName("name"); // NOI18N

        commentJScrollPane.setName("commentJScrollPane"); // NOI18N

        comment.setColumns(20);
        comment.setRows(5);
        comment.setLineWrap(true);
        comment.setWrapStyleWord(true);
        comment.setName("comment"); // NOI18N
        comment.setEditable(false);
        commentJScrollPane.setViewportView(comment);
        
        commentLabel.setText(""); // NOI18N
        commentLabel.setName("commentLabel"); // NOI18N

        nameJLabel.setText("Name:"); // NOI18N
        nameJLabel.setName("nameJLabel"); // NOI18N

        typeJLabel.setText("Type:"); // NOI18N
        typeJLabel.setName("typeJLabel"); // NOI18N

        infEngineJLabel.setText("Agent:"); // NOI18N
        infEngineJLabel.setName("infEngineJLabel"); // NOI18N

        type.setEditable(false);
        type.setName("type"); // NOI18N

        infEngine.setEditable(false);
        infEngine.setName("infEngine"); // NOI18N

        detailedByJLabel.setText("Detailed by:"); // NOI18N
        detailedByJLabel.setName("detailedByJLabel"); // NOI18N

        detailedBy.setEditable(false);
        detailedBy.setName("detailedBy"); // NOI18N
        
        sourceJLabel.setText("Source Pub:"); // NOI18N
        sourceJLabel.setName("sourceJLabel"); // NOI18N
        
        source.setEditable(false);
        source.setName("source");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    	.addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    		.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    			.addComponent(typeJLabel)
                                .addComponent(nameJLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                               	.addComponent(type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(name, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        	.addComponent(infEngineJLabel)
                        	.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        	.addComponent(infEngine, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        	.addComponent(sourceJLabel)
                        	.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        	.addComponent(source, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        	.addComponent(detailedByJLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(detailedBy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(commentLabel, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(commentJScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))    
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(typeJLabel))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nameJLabel))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(source, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(sourceJLabel))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(infEngine, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(infEngineJLabel))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(detailedBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(detailedByJLabel))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(commentLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(commentJScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addContainerGap())
            );
            this.setMinimumSize(new Dimension(300,300));
    }
}
