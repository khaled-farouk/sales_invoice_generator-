package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author khaled
 */
public class Invoiceheader {
    private int invNum;
    private String customerName;
    private Date invDate;
    private ArrayList<invoiceLines> lines;
    //private double invTotal;

    public Invoiceheader(int invNum, String customerName, Date invDate, ArrayList<invoiceLines> lines) {
        this.invNum = invNum;
        this.customerName = customerName;
        this.invDate = invDate;
        //this.lines = new ArrayList<>(); //eager creation
    }




    public ArrayList<invoiceLines> getLines() {
        if(lines==null){
            lines=new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<invoiceLines> lines) {
        this.lines = lines;
    }


    public int getInvNum() {
        return invNum;
    }

    public void setInvNum(int invNum) {
        this.invNum = invNum;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    @Override
    public String toString() {
        return "InvoiceHeader{" + "invNum=" + invNum + ", customerName=" + customerName + ", invDate=" + invDate + '}';
    }
    public double getInvTotal() {
        double total=0.0;
        for (invoiceLines line : getLines()) {
            total+=line.getLineTotal();
        }
        return total;
    }
    public void addInvLine(invoiceLines line){
        getLines().add(line);
    }

}
