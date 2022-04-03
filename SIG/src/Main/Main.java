package Main;
import Controller.MyActions;
import View.InvoiceGeneratorUI;

public class Main{
        public static void main(String args[]) {
            InvoiceGeneratorUI frame = new InvoiceGeneratorUI();
           MyActions lis = new MyActions(frame);

            frame.setTitle("Sales Invoice Generator");
            frame.setVisible(true);
        }

}