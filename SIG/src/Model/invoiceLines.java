package Model;

/**
 *
 * @author khaled
 */
public class invoiceLines {
    private String itemName;
    private double itemPrice;
    private int itemCount;
    Invoiceheader header;


    public invoiceLines(String itemName, double itemPrice, int itemCount, Invoiceheader header) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.header = header;
    }

    public Invoiceheader getHeader() {
        return header;
    }

    public void setHeader(Invoiceheader header) {
        this.header = header;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public String toString() {
        return "InvoiceLine{" + "itemName=" + itemName + ", itemPrice=" + itemPrice + ", itemCount=" + itemCount + '}';
    }

    public double  getLineTotal(){
        return getItemCount()*getItemPrice();
    }

}
