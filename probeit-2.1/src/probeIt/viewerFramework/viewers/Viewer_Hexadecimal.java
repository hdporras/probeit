package probeIt.viewerFramework.viewers;
import java.awt.BorderLayout;
import java.math.BigInteger;
import java.text.DecimalFormat;

import javax.swing.*;
import javax.swing.table.TableColumn;
public class Viewer_Hexadecimal extends JScrollPane implements Viewer
{
	public JComponent getViewerInterface(Object hexChars)
	{
		if(hexChars instanceof char[])
		{
			setViewportView(buildTables((char[])hexChars));
			return this;
		}
		else
			return null;
	}
	
	public String getViewerName()
	{return "Hexadecimal Viewer";}
	
	public int getLogicalWidth()
	{return ViewerStyle.DEFAULT_WIDTH;}
	
	public int getLogicalHeight()
	{return ViewerStyle.DEFAULT_HEIGHT;}
	
	private JPanel buildTables(char[] hexStringArray)
	{
		System.out.println(hexStringArray.length);
		try
		{
			BigInteger hexVal; // used to convert counter variable 'i' to hex
			String asciiVal; // the ascii value of the current byte
			byte[] temp = new byte[1];
			int index;

			// create table hex editor view
			// *************************************************************************************
			// the indexTable increments each row in the hex table

			String[][] indexTable, hexTable, asciiTable;
			DecimalFormat myFormat = new DecimalFormat("0");
			int rows = Integer.parseInt(myFormat.format((double) hexStringArray.length / 32));
			System.out.println(rows);
			indexTable = new String[rows][1];
			hexTable = new String[rows][16];
			asciiTable = new String[rows][16];

			int hexRow = 0, asciiRow = 0;
			int hexCol = 0, asciiCol = 0;
			String hexDigit = "";
			System.out.println(hexStringArray.length);
			for (int i = 0; i < hexStringArray.length && i < 3000; i++)
			{
				/***************************************************************
				 * This section creates the index table
				 **************************************************************/
				if ((i % 32) == 0)
				{
					hexVal = new BigInteger(String.valueOf(i / 2));
					indexTable[(i / 32)][0] = hexVal.toString(16);
				}

				/***************************************************************
				 * This section creates the hex table
				 **************************************************************/
				if (((i % 32) == 0) && (i != 0))
				{// add new row if on 16th byte
					hexTable[hexRow][hexCol] = hexDigit;
					hexDigit = "";
					hexRow++;
					hexCol = 0;
				}

				if (((i % 2) == 0) && (i != 0) && ((i % 32) != 0))
				{// add new cell for each hex pair
					hexTable[hexRow][hexCol] = hexDigit;
					hexDigit = ""; // reset hexBuffer
					hexCol++;
				}

				// add hexadecimal character to table
				hexDigit = hexDigit
						+ new Character(hexStringArray[i]).toString();

				if (i == hexStringArray.length - 1)
					hexTable[hexRow][hexCol] = hexDigit;

				/***************************************************************
				 * This section creates the ascii table
				 **************************************************************/
				
				if (((i % 32) == 0) && (i != 0))
				{
					asciiRow++;
					asciiCol = 0;
				}

				if ((i % 2) == 0)
				{
					BigInteger bi = new BigInteger(hexDigit, 16);
					String strByte = bi.toString(8);
					//index = (i / 2);
					//temp[0] = rawBinary[index];
					asciiVal = new String(strByte.getBytes(), "US-ASCII");
					asciiTable[asciiRow][asciiCol] = asciiVal;
					asciiCol++;
				}
			}
			String[] indexHeader = new String[] { "" };
			String[] hexAsciiHeader = new String[] { "0", "1", "2", "3", "4",
					"5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

			// index JTable setup
			JTable indexTbl = new JTable(indexTable, indexHeader);
			indexTbl.setBackground(java.awt.Color.cyan);
			indexTbl = shrinkTable(indexTbl, 35);

			// hex JTable setup
			JTable hexTbl = new JTable(hexTable, hexAsciiHeader);
			hexTbl = shrinkTable(hexTbl, 25);

			// ascii JTable setup
			JTable asciiTbl = new JTable(asciiTable, hexAsciiHeader);
			asciiTbl.setBackground(java.awt.Color.yellow);
			asciiTbl = shrinkTable(asciiTbl, 12);

			// tabel panel
			JPanel tablePanel = new JPanel();
			JPanel headerPanel = new JPanel();

			tablePanel.setLayout(new BorderLayout());
			headerPanel.setLayout(new BorderLayout());

			headerPanel.add(indexTbl.getTableHeader(), BorderLayout.WEST);
			headerPanel.add(hexTbl.getTableHeader(), BorderLayout.CENTER);
			headerPanel.add(asciiTbl.getTableHeader(), BorderLayout.EAST);

			tablePanel.add(headerPanel, BorderLayout.NORTH);
			tablePanel.add(indexTbl, BorderLayout.WEST);
			tablePanel.add(hexTbl, BorderLayout.CENTER);
			tablePanel.add(asciiTbl, BorderLayout.EAST);

			return tablePanel;

		} catch (Exception e)
		{
			JTextField _errorMessage = new JTextField("Encoding Error: "
					+ e.getLocalizedMessage());
			JPanel _errorPanel = new JPanel();
			e.printStackTrace();
			_errorPanel.add(_errorMessage);
			return _errorPanel;
		}
	}
	
	private JTable shrinkTable(JTable table, int width)
	{
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++)
		{
			column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(width);
		}
		table.setEnabled(false);
		return table;
	}
}
