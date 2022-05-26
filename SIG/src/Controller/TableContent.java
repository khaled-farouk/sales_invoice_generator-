/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import model.InvoicesHeaderTab;
import model.InvoicesLineTab;
import View.View;

/**
 *
 * @author khaled
 */
public class TableContent
{
    public static void cleanInvoicesHeaderTable(View view) 
    {
       while(InvoicesHeaderTab.setInvoicesHeaderTableModel(view).getRowCount()>0)
       {
           InvoicesHeaderTab.setInvoicesHeaderTableModel(view).removeRow(0);
       }
    }
    public static void cleanInvoicesLineTable(View view) 
    {
       while(InvoicesLineTab.setInvoicesLineTableModel(view).getRowCount()>0)
       {
           InvoicesLineTab.setInvoicesLineTableModel(view).removeRow(0);
       }
    }
}
