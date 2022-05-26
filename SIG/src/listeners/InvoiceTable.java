/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;

import Controller.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.FileOp;
import View.View;

/**
 *
 * @author khaled
 */
public class InvoiceTable implements ListSelectionListener
{
    private FileOp fileOperations;
    private View view=null;
    private InvoicesLine invoicesLineTableListener;
    public InvoiceTable(View view, FileOp fileOperations, InvoicesLine invoicesLineTableListener)
    {
        this.view=view;
        this.fileOperations=fileOperations;
        this.invoicesLineTableListener=invoicesLineTableListener;
    }
    @Override
    public void valueChanged(ListSelectionEvent e) 
    {
        if(Cntrol.invoices.size()>=1)
        {
            //Dected the latest change
            if(!e.getValueIsAdjusting())
            {
                view.getDeleteItemButton().setEnabled(false);
                Cntrol.selectedR =view.getInvoiceTable().getSelectedRow();
                view.getInvoicesLineTable().getSelectionModel().removeListSelectionListener(invoicesLineTableListener);
                LoadTables.loadInvoicesLineTable(view, Cntrol.invoices);
                RightPanell.rightSideTextUpdat(view, Cntrol.invoices, Cntrol.selectedR);
                view.getInvoicesLineTable().getSelectionModel().addListSelectionListener(invoicesLineTableListener);
            }
        }
    }
}
