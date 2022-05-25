/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author khaled
 */
public class InvoiceHeader {
    private int num;
    private Date date;
    private String customerName;
    private double invHeaderTotal;
    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(int num, Date date, String customerName) {
        this.num = num;
        this.date = date;
        this.customerName = customerName;
    }

    public int getNum() {
        return num;
    }

    public Date getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getInvHeaderTotal() {
               return invHeaderTotal;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setInvHeaderTotal(double invHeaderTotal) {
        this.invHeaderTotal = invHeaderTotal;
    }

    public ArrayList<InvoiceLine> getLines() {
        if (lines == null){
            lines = new ArrayList<>();
        }
        return lines;
    }

//    public void setLines(ArrayList<InvoiceLine> lines) {
//
//        this.lines = lines;
//    }

 public void addLine(InvoiceLine line){
        getLines().add(line);
        setInvHeaderTotal(getInvHeaderTotal() + line.getLineTotal() );
 }


}
