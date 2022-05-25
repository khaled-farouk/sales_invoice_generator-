package Model;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class LineTable extends DefaultTableModel {
    private String [] colsLT = {"No.","Item Name","Price",
            "Count","Title"};

    ArrayList<InvoiceLine>  invoicesFromLine;

    public LineTable (ArrayList<InvoiceLine> invoicesFromLine){
        this.invoicesFromLine = invoicesFromLine;
    }

    @Override
    public int getColumnCount() {
        return colsLT.length;
    }

    @Override
    public String getColumnName(int column) {
        return colsLT[column];
    }

    @Override
    public int getRowCount() {
        if(this.invoicesFromLine==null){
            invoicesFromLine = new ArrayList<>();
        }
        return invoicesFromLine.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
       InvoiceLine invoiceLine = invoicesFromLine.get(row);
        switch (column){
            case 0: return invoiceLine.getNumOfItems();
            case 1: return invoiceLine.getProduct();
            case 2: return invoiceLine.getPrice();
            case 3: return invoiceLine.getCount();
            case 4: return invoiceLine.getLineTotal();
        }
        return null;
    }

    @Override
    public void removeRow(int row) {
        invoicesFromLine.remove(row);
    }


    public ArrayList<InvoiceLine> getInvoicesFromLine() {
        return invoicesFromLine;
    }
}
