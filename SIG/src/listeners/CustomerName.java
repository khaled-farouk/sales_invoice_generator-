/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import View.View;
import Controller.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author khaled
 */
public class CustomerName implements FocusListener, ActionListener
{
    private View view=null;
    
    public CustomerName(View view)
    {
        this.view=view;
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        if(!((Cntrol.invoices.get(Cntrol.selectedR).getInoviceCustomerName()).equals((view.getCustomerNameTextField().getText()))))
        {
            RightPanell.UpdateNamefield(view, Cntrol.invoices);
            view.getInvoiceTable().setRowSelectionInterval(Cntrol.selectedR, Cntrol.selectedR);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(!((Cntrol.invoices.get(Cntrol.selectedR).getInoviceCustomerName()).equals((view.getCustomerNameTextField().getText()))))
        {
            RightPanell.UpdateNamefield(view, Cntrol.invoices);
        }
    }
    
    @Override
    public void focusGained(FocusEvent e){}

   
}
