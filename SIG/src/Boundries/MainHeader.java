package Boundries;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author khaled
 */
public class MainHeader {
    private int invNum;
    private String customerName;
    private Date invDate;
    private ArrayList<LineEntries> lines;
    //private double invTotal;

    public MainHeader(int invNum, String customerName, Date invDate, ArrayList<LineEntries> lines) {
        this.invNum = invNum;
        this.customerName = customerName;
        this.invDate = invDate;
        //this.lines = new ArrayList<>(); //eager creation
    }




    public ArrayList<LineEntries> getLines() {
        if(lines==null){
            lines=new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<LineEntries> lines) {
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
        for (LineEntries line : getLines()) {
            total+=line.getLineTotal();
        }
        return total;
    }
    public void addInvLine(LineEntries line){
        getLines().add(line);
    }

}
