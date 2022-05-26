/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import model.FileOp;
import View.View;
import Controller.*;

/**
 *
 * @author khaled
 */
public class MainFrameMotion implements MouseMotionListener
{
    private View view=null;
    public MainFrameMotion(View view)
    {
        this.view=view;
    }

    @Override
    public void mouseDragged(MouseEvent e){}

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if((FileOp.selectedInvoiceHeader!=null)&&(FileOp.selectedInvoiceLine!=null))
        {
            view.getCreatNewInvoiceButton().setEnabled(true);
        }
        else
        {
            view.getCreatNewInvoiceButton().setEnabled(false);
            view.getDeleteInvoiceButton().setEnabled(false);
            view.getInvoiceTotalLabel().setText("");
        }
       if((Controller.invoices.isEmpty())||(view.getInvoiceTable().getSelectedRow()<0))
        {
            view.getDeleteInvoiceButton().setEnabled(false);
        }
        else
        {
            view.getDeleteInvoiceButton().setEnabled(true);
        }
       if(Controller.isThereIsNotSavedEdit)
        {
            view.getCancelButton().setEnabled(Controller.isThereIsNotSavedEdit);
        }
        else
        {
            view.getCancelButton().setEnabled(Controller.isThereIsNotSavedEdit);
        }
    }
    
}
