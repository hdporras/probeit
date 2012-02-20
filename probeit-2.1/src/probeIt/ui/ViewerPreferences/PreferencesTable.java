package probeIt.ui.ViewerPreferences;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

public class PreferencesTable extends JPanel {
    private boolean DEBUG = false;

    public PreferencesTable() {
        super(new GridLayout(1,0));

        JTable table = new JTable(new MyTableModel());
        table.setGridColor(java.awt.Color.GRAY);
        table.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Set up column sizes.
        //initColumnSizes(table);

        setUpTransformersColumn(table, table.getColumnModel().getColumn(1));
        setUpViewersColumn(table, table.getColumnModel().getColumn(2));
        
        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    /*
     * This method picks good column sizes.
     * If all column heads are wider than the column's cells'
     * contents, then you can just use column.sizeWidthToFit().
     */
    
    /*
    private void initColumnSizes(JTable table) {
        MyTableModel model = (MyTableModel)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();

        for (int i = 0; i < 3; i++)
        {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }
    }*/

    public void setUpTransformersColumn(JTable table, TableColumn transformerColumn) {
        
    	//Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        
        String[] transformerNames = probeIt.viewerFramework.TransformersAndViewers.getTransformerNames();
        
        for(String name : transformerNames)
        {
        		comboBox.addItem(name);
        }
        
        transformerColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(java.awt.Color.CYAN);
        
        renderer.setToolTipText("Click for combo box");
        transformerColumn.setCellRenderer(renderer);
    }
    
    public void setUpViewersColumn(JTable table, TableColumn viewersColumn) {
        
    	//Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        
        String[] viewerNames = probeIt.viewerFramework.TransformersAndViewers.getViewerNames();
        
        for(String name : viewerNames)
        {comboBox.addItem(name);}
        
        viewersColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBackground(java.awt.Color.CYAN);
        
        renderer.setToolTipText("Click for combo box");
        viewersColumn.setCellRenderer(renderer);
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Known Formats",
                                        "Transformer",
                                        "Viewer"};
   
        private Preferences data = Preferences.getCurrentPreferences();
      
        public final Object[] longValues = {"LONGLONGLONGLONGLONGLONG", "LONGLONGLONGLONGLONGLONG", "LONGLONGLONGLONGLONGLONG"};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.getNumberofFormats();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
        	
        	if(col == 0)
        		return getFormatName(data.getValueAt(row, col));
        	
            return getClassName(data.getValueAt(row, col));
        }
        
        private String getClassName(String fullyQualName)
        {
        	if(fullyQualName == null)
        	{
        		System.out.println("why is this null");
        	}
        	System.out.println(fullyQualName);
    		String[] chunks = fullyQualName.split("\\.");
    		return chunks[chunks.length-1];
        }
        
    	private String getFormatName(String formatURI)
    	{
    		String[] chunks = formatURI.split("/");
    		return chunks[chunks.length-1];
    	}

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 1) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            data.setValueAt(value, row, col);
            fireTableCellUpdated(row, col);

            if (DEBUG) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + data.getValueAt(i,j));
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TableRenderDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        //Create and set up the content pane.
        PreferencesTable newContentPane = new PreferencesTable();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

