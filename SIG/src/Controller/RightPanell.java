/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.text.ParseException;
import java.util.ArrayList;

import model.FileOp;
import model.InvoiceHeader;
import model.InvoiceLine;
import View.View;

/**
 *
 * @author khaled
 */
public class RightPanell
{

    static void rightON(View view)
    {
        view.getAddItemButton().setEnabled(true);
        view.getInvoiceDateTextField().setEditable(true);
        view.getCustomerNameTextField().setEditable(true);
    }
    public static void rightSideTextUpdat(View view, ArrayList<InvoiceHeader> invoices, int selectedRow)
    {
        if(selectedRow!=-1)
        {
            view.getInvoiceNumberLabel().setText(Integer.toString(invoices.get(selectedRow).getInoviceNumber()));
            view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(selectedRow).getInoviceDate()));
            view.getCustomerNameTextField().setText(invoices.get(selectedRow).getInoviceCustomerName());
            view.getInvoiceTotalLabel().setText(Float.toString(invoices.get(selectedRow).getInoviceTotal()));
        }
    }
    public static void UpdateNamefield(View view, ArrayList<InvoiceHeader> invoices)
    {
        //Confirm that user want to customer name
        int choice=view.showYesNoCancelDialog(view.getRightSidePanel(), "Do you want To save customer name changes?", "Customer name change confirmation");
        if(choice==0) //yes
        {
            invoices.get(view.getInvoiceTable().getSelectedRow()).setInoviceCustomerName(view.getCustomerNameTextField().getText());
            LeftPanell.updateeName(view,invoices);
            view.getCustomerNameTextField().requestFocus();
            Cntrol.isThereIsnotSvdEd =true;
        }
        else if(choice==1) //no
        {
            view.getCustomerNameTextField().requestFocus();
        }
        else //cancel
        {
            view.getCustomerNameTextField().setText(invoices.get(Cntrol.selectedR).getInoviceCustomerName());
            view.getCustomerNameTextField().requestFocus();
        }
    }

    static void rightOFF(View view)
    {
        view.getInvoiceDateTextField().setText("");
        view.getCustomerNameTextField().setText("");
        view.getInvoiceTotalLabel().setText("");
        view.getInvoiceNumberLabel().setText("");
        view.getAddItemButton().setEnabled(false);
        view.getDeleteItemButton().setEnabled(false);
        view.getInvoiceDateTextField().setEditable(false);
        view.getCustomerNameTextField().setEditable(false);
    }
    public static void dateValidae(View view, ArrayList<InvoiceHeader> invoices)
    {
        //Confirm that user want to change date
        int choice=view.showYesNoCancelDialog(view.getRightSidePanel(), "Do you want To save date changes?", "Date change confirmation");
        if(choice==0)//yes
        {
            try 
            {
                //Check if new date have the pattern "dd-MM-yyyy" exactly using regex expression
                if(!(view.getInvoiceDateTextField().getText().matches("^\\d{2}\\-\\d{2}\\-\\d{4}")))
                {
                    throw new Exception();
                }
                //Check if Date is valid date
                FileOp.date.setLenient(false);
                FileOp.date.parse(view.getInvoiceDateTextField().getText());
                invoices.get(view.getInvoiceTable().getSelectedRow()).setInoviceDate(view.getDate().parse(view.getInvoiceDateTextField().getText()));
                LeftPanell.updateeDate(view,invoices);
                view.getInvoiceDateTextField().requestFocus();
                Cntrol.isThereIsnotSvdEd =true;
            } catch (ParseException ex) 
            {
                view.setJOptionPaneMessagMessage(view.getRightSidePanel(),"Please Enter A Valid Date","Wrong Date","ERROR_MESSAGE");
                view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(view.getInvoiceTable().getSelectedRow()).getInoviceDate()));
                view.getInvoiceDateTextField().requestFocus();
            }
            catch (Exception ex)
            {
                view.setJOptionPaneMessagMessage(view.getRightSidePanel(),"Please Enter A Valid Date Format (e.g 05-09-1993)", "Wrong Date Format","ERROR_MESSAGE");
                view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(view.getInvoiceTable().getSelectedRow()).getInoviceDate()));
                view.getInvoiceDateTextField().requestFocus();
            }
        }
        else if(choice==1)//no
        {
            view.getInvoiceDateTextField().requestFocus();
        }
        else //cancel
        {
            view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(Cntrol.selectedR).getInoviceDate()));
            view.getInvoiceDateTextField().requestFocus();
        }
    }


    
    static void AddNewONe(View view, ArrayList<InvoiceHeader> invoices)
    {
        String itemName;
        float price=0;
        int count=0;
        boolean errorFlag=false;
        itemName=view.getNewItemName().getText();
        if(itemName.equalsIgnoreCase(""))
        {
            View.getAddItemDialog().setVisible(false);
            view.setJOptionPaneMessagMessage(view.getRightSidePanel(), "Please Enter A Name For The Item", "Empty Item Name Entered", "ERROR_MESSAGE");
            CreateeNewItem(view);
        }
        else if(view.getNewItemPrice().getText().equalsIgnoreCase(""))
        {
            View.getAddItemDialog().setVisible(false);
            view.setJOptionPaneMessagMessage(view.getRightSidePanel(), "Please Enter A Price For The Item", "Empty Price Entered", "ERROR_MESSAGE");
            CreateeNewItem(view);
        }
        else
        {
            try {
                    price=Float.parseFloat(view.getNewItemPrice().getText());
                } catch ( Exception e ) 
                {
                    errorFlag=true;
                }
            try {
                    view.getNewItemPriceSpinner().commitEdit();
                    count = (Integer)view.getNewItemPriceSpinner().getValue();
                } catch ( Exception e ) 
                {
                    errorFlag=true;
                }
            if(!errorFlag)
            {   
                InvoiceHeader temp = invoices.get(view.getInvoiceTable().getSelectedRow());
                InvoiceLine newItem= new InvoiceLine(itemName, price, count, temp);
                temp.getInvoicerow().add(newItem);
                Cntrol.isThereIsnotSvdEd =true;
            }
            view.getNewItemName().setText("");
            view.getNewItemPrice().setText("");
            view.getNewItemPriceSpinner().setValue((Object)1);
        }
    }
    static void DeleteItem(View view, ArrayList<InvoiceHeader> invoices)
    {
        if(view.getInvoicesLineTable().getSelectedRow()>=0)
        {
            int rowToBeDeleted;
            rowToBeDeleted=view.getInvoicesLineTable().getSelectedRow();
            invoices.get(view.getInvoiceTable().getSelectedRow()).getInvoicerow().remove(rowToBeDeleted);
            Cntrol.isThereIsnotSvdEd =true;
        }
    }
    static void CreateeNewItem(View view)
    {
        view.setLocations();
        View.getAddItemDialog().setTitle("Add New Item To Invoice Number "+ view.getInvoiceNumberLabel().getText());
        View.getAddItemDialog().setVisible(true);
    }


}
