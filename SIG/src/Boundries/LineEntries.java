package Boundries;

/**
 *
 * @author khaled
 */
public class LineEntries {
    private String itemName;
    private double itemPrice;
    private int itemCount;
    MainHeader header;


    public LineEntries (String itemName, double itemPrice, int itemCount, MainHeader header) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.header = header;
    }

    public MainHeader getHeader() {
        return header;
    }

    public void setHeader(MainHeader header) {
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
