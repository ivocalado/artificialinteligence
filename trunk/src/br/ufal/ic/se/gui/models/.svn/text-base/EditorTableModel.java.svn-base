package br.ufal.ic.se.gui.models;
import java.util.*;

import javax.swing.table.AbstractTableModel;


import br.ufal.ic.se.node.TLetter;
import br.ufal.ic.se.models.*;

public class EditorTableModel extends AbstractTableModel {
    private String[] columnNames = {"Variavel", "Valor"};
    private ArrayList<Widget> dataList = new ArrayList<Widget>();

    public EditorTableModel(List<Widget> widgetList) {
	dataList.addAll(widgetList);
    }  
    
    public int getRowCount() {
	return dataList.size();
    }

    public int getColumnCount() {
	return columnNames.length;
    }

    public String getColumnName(int col) {
	return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
	Widget w = dataList.get(row);

	switch (col) {
	case 0:
	    return w.getLetter();
	case 1:
	    return w.getDescription();
	default:
	    return null;
	}	
    }

    public Widget getWidgetAt(int row) {
	return dataList.get(row);
    }

    public Widget removeWidgetAt(int row) {
	Widget w = dataList.remove(row); 
	fireTableDataChanged();
	return w;
    }    
    
    public List<Widget> getWidgetsList() {
	return dataList;
    }
}
