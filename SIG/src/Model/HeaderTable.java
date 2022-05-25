package Model;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public class HeaderTable extends DefaultTableModel {
    private String [] cols = {"No.","Date","Customer","Total"};

    private ArrayList<InvoiceHeader> invoicesFromHeader;

    public HeaderTable(ArrayList<InvoiceHeader> invoicesFromHeader){
        this.invoicesFromHeader = invoicesFromHeader;
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int column) {
        return cols[column];
    }

    @Override
    public int getRowCount() {
        if(this.invoicesFromHeader == null){
            invoicesFromHeader = new ArrayList<>();
        }
        return invoicesFromHeader.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        InvoiceHeader invoiceHeader = invoicesFromHeader.get(row);
        switch (column){
            case 0: return invoiceHeader.getNum();
            case 1: return invoiceHeader.getDate();
            case 2: return invoiceHeader.getCustomerName();
            case 3: return  invoiceHeader.getInvHeaderTotal();
        }
        return null;
    }

    public ArrayList<InvoiceHeader> getInvoicesFromHeader() {
        return invoicesFromHeader;
    }

    @Override
    public void removeRow(int row) {
        invoicesFromHeader.remove(row);
    }
}
