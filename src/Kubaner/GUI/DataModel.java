package Kubaner.GUI;

/*
 * Hier sind die Objects durch Integers ersetzt, damit der
 * Comparator vern�nftig sortieren kann.
 */

import javax.swing.table.AbstractTableModel;

public class DataModel extends AbstractTableModel {
	private  final int ROWS;
	private  final int COLS;
	
	private Object[][] data;
	
	DataModel (int ROWS, int COLS) {
		this.ROWS = ROWS;
		this.COLS = COLS;
		data = new Object[ROWS][COLS];
		for ( int row = 0; row < data.length; row++ )
			for ( int col = 0; col < data[0].length; col++ )
				data[row][col] = "";
	}

	public int getColumnCount () {
		return COLS;
	}
	
	public int getRowCount () {
		return ROWS;
	}
	
	public String getColumnName ( int col ) {
		return "" + col;
	}
	
	public Object getValueAt ( int row, int col ) {
		return data[row][col];
	}

	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value.toString();
		fireTableCellUpdated(row, col);
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}
}