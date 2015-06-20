package Kubaner.Logic;

import javax.swing.table.AbstractTableModel;

public class MasterModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rows;
	private int cols;
	
	private Object[][] data;
	
	MasterModel (int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		data = new Object[rows][cols];
	}
	
	
	public int getColumnCount () {
		return cols;
	}
	
	
	public int getRowCount () {
		return rows;
	}
	
	
	public Object getValueAt ( int row, int col ) {
		return data[row][col];
	}

	
	public void setValueAt(Object value, int row, int col) {
		if(-1 < row && row < this.rows && -1 < col && col < this.cols) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}
	}


	public boolean isCellEditable(int row, int col) {
		return false;
	}
}
