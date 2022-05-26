/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import View.View;
import Controller.*;
import model.FileOp;

/**
 *
 * @author khaled
 */
public class MainFrameWin implements WindowListener
{
    private FileOp fileOperations;
    private InvoiceTable invoiceTableListener;
    private View view=null;
    public MainFrameWin(View view, FileOp fileOperations, InvoiceTable invoiceTableListener)
    {
        this.view=view;
        this.fileOperations=fileOperations;
        this.invoiceTableListener=invoiceTableListener;
    }
    
    @Override
    public void windowClosing(WindowEvent e)
    {
        int option;
        if(Cntrol.isThereIsnotSvdEd)
        {
            option= view.showSaveDontSaveCancelDialog(view, "Do You Want To Save Changes?", "Exit");
            if(option == 1)
            {
                System.exit(0);
            }
            else if(option == 0)
            {
                view.setVisible(false);
                saveFile();
                System.exit(0);
            }
            else {}
        }
        else {System.exit(0);}
    }
    void saveFile()
    {
        //Write the invoices arraylist
        fileOperations.writeFile(Cntrol.invoices);
        //Reload CSV files into tables
        if((FileOp.selectINvOHeader !=null)&&(FileOp.selectInVoLine !=null))
        {
            view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
            Cntrol.invoices=fileOperations.readFile();
            LeftPanell.calculateeTotal(Cntrol.invoices);
            LoadTables.loadInvoicesHeaderTable(view, Cntrol.invoices);
            Cntrol.isThereIsnotSvdEd =false;
            view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
            if(Cntrol.invoices.size()>=1)
            view.getInvoiceTable().setRowSelectionInterval(0, 0);
        }
        if(Cntrol.isThereIsnotSvdEd)
        {
            view.getCancelButton().setEnabled(Cntrol.isThereIsnotSvdEd);
        }
        else
        {
            view.getCancelButton().setEnabled(Cntrol.isThereIsnotSvdEd);
        }
    }
    @Override
    public void windowOpened(WindowEvent e){}

    @Override
    public void windowClosed(WindowEvent e){}

    @Override
    public void windowIconified(WindowEvent e){}

    @Override
    public void windowDeiconified(WindowEvent e){}

    @Override
    public void windowActivated(WindowEvent e)
    {
        //Disable and enable Creat and Delete invoice buttons
        if((FileOp.selectINvOHeader !=null)&&(FileOp.selectInVoLine !=null))
        {
            view.getCreatNewInvoiceButton().setEnabled(true);
        }
        else
        {
            view.getCreatNewInvoiceButton().setEnabled(false);
            view.getDeleteInvoiceButton().setEnabled(false);
            view.getInvoiceTotalLabel().setText("");
        }
    }

    @Override
    public void windowDeactivated(WindowEvent e)
    {
        //Disable and enable Creat and Delet invoice buttons
        if((FileOp.selectINvOHeader !=null)&&(FileOp.selectInVoLine !=null))
        {
            view.getCreatNewInvoiceButton().setEnabled(true);
        }
        else
        {
            view.getCreatNewInvoiceButton().setEnabled(false);
            view.getDeleteInvoiceButton().setEnabled(false);
            view.getInvoiceTotalLabel().setText("");
        }
    }
    
}
