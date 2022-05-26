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
public class InvoiceDate implements FocusListener, ActionListener
{
    private View view=null;
    
    public InvoiceDate(View view)
    {
        this.view=view;
    }
    
    @Override
    public void focusLost(FocusEvent e)
    {
        if((!(Cntrol.invoices.isEmpty()))&&(Cntrol.selectedR >=0))
        {
            if(!((view.getDate().format(Cntrol.invoices.get(Cntrol.selectedR).getInoviceDate())).equals((view.getInvoiceDateTextField().getText()))))
            {
                RightPanell.dateValidae(view, Cntrol.invoices);
                view.getInvoiceTable().setRowSelectionInterval(Cntrol.selectedR, Cntrol.selectedR);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(!((view.getDate().format(Cntrol.invoices.get(Cntrol.selectedR).getInoviceDate())).equals((view.getInvoiceDateTextField().getText()))))
        {
            RightPanell.dateValidae(view, Cntrol.invoices);
        }
    }
    @Override
    public void focusGained(FocusEvent e){}   
}
