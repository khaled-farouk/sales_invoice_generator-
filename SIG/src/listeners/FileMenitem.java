/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.FileOp;
import View.View;
import Controller.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
/**
 *
 * @author khaled
 */
public class FileMenitem implements ActionListener ,MenuListener
{
    private FileOp fileoperation;
    private InvoiceTable invoiceTableAc;
    private View view=null;
    
    public FileMenitem(View view, FileOp fileOperations, InvoiceTable invoiceTableListener)
    {
        this.view=view;
        this.fileoperation =fileOperations;
        this.invoiceTableAc =invoiceTableListener;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "Load Fisle Sequence":
            {
                /*Clear Both Table*/
                Cntrol.isThereIsnotSvdEd =false;
                TableData.DeleteInvoicesHeader(view);
                TableData.DeleteLineTable(view);
                //clean total
                view.getInvoiceTotalLabel().setText("");
                fileoperation.FilePaths();
                if((FileOp.selectINvOHeader !=null)&&(FileOp.selectInVoLine !=null))
                {
                    Cntrol.invoices= fileoperation.readFile();
                    fileoperation.testMain(Cntrol.invoices);
                    LeftPanell.calculateeTotal(Cntrol.invoices);
                    LoadTables.loadInvoicesHeaderTable(view, Cntrol.invoices);
                    fileoperation.getMaxNumberOfExistedInvoices(Cntrol.maxNumInvoices, Cntrol.invoices);
                }
                break;
            }
            case "Save File":
            {
                //Write the invoices arraylist
                fileoperation.writeFile(Cntrol.invoices);
                //Reload CSV files into tables
                if((FileOp.selectINvOHeader !=null)&&(FileOp.selectInVoLine !=null))
                {
                    view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableAc);
                    Cntrol.invoices= fileoperation.readFile();
                    LeftPanell.calculateeTotal(Cntrol.invoices);
                    LoadTables.loadInvoicesHeaderTable(view, Cntrol.invoices);
                    Cntrol.isThereIsnotSvdEd =false;
                    view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableAc);
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
                break;
            }
        }
    }

    @Override //Enable or disable Save File Button in File Menu
    public void menuSelected(MenuEvent e) 
    {
        if((Cntrol.isThereIsnotSvdEd))
        {
            view.getSaveFile().setEnabled(true);
        }
        else
        {
            view.getSaveFile().setEnabled(false);
        }
    }

    @Override
    public void menuDeselected(MenuEvent e){}

    @Override
    public void menuCanceled(MenuEvent e){}
    
}
