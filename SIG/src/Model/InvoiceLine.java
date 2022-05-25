/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author khaled
 */
public class InvoiceLine {
    private int numOfItems;
    private String product;
    private double price;
    private int count;
    private double lineTotal;
    private InvoiceHeader invoice;
//    ArrayList<InvoiceHeader>arrInvHeader;

    public InvoiceLine(String product, double price, int count,InvoiceHeader invoice) {
//        this.numOfItems = numOfItem;
        this.product = product;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
        this.setLineTotal(this.count * this.price);
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(double lineTotal) {
        this.lineTotal = lineTotal;
    }

}
