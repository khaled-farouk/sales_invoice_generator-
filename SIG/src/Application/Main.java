package Application;


import Controller.Cntrol;
import java.util.ArrayList;

import model.InvoiceHeader;
import View.View;

/**
 *
 * @author khaled
 */
public class Main
{
    public static void main(String[] args)
    {
        ArrayList <InvoiceHeader> invoices= new ArrayList<>();
        View          view= new View();
        view.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        view.setVisible(true);
        view.setLocations();
        Cntrol c= new Cntrol(invoices, view);
    }
}
