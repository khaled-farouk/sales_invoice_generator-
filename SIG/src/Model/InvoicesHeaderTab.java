/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.swing.table.DefaultTableModel;
import View.View;

/**
 *
 * @author khaled
 */
public class InvoicesHeaderTab
{
    public static DefaultTableModel setInvoicesHeaderTableModel(View view)
    {
        DefaultTableModel newTable= new DefaultTableModel() {};
        newTable= (DefaultTableModel) view.getInvoiceTable().getModel();
        view.getInvoiceTable().setDefaultEditor(Object.class, null);
        return newTable;
    }
    
}
