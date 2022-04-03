/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

//import Model.InvoiceLine;
import View.InvoiceGeneratorUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author khaled
 */
public class MyActions extends Component {

    InvoiceGeneratorUI frame;
    String first_path = "../InvoiceHeader.csv";
    String second_path = "../InvoiceLine.csv";
    static int NoNum;
    DefaultTableModel header_Model;
    DefaultTableModel line_Model;

    public MyActions(InvoiceGeneratorUI frame) {
        this.frame = frame;
    }

    public void loadFile(java.awt.event.ActionEvent evt) {

        // if you want to choose the 2 files
        //two path fields will be changed as you selected
        BufferedReader br = null;
        BufferedReader br2 = null;
        int total = 0;
        int invNum = 1;
        header_Model = (DefaultTableModel) frame.getInvoicesTable().getModel();
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            first_path = fc.getSelectedFile().getPath();

            try {
                br = new BufferedReader(new FileReader(new File(first_path)));
                Object[] tableLines = br.lines().toArray();
                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    header_Model.addRow(dataRow);
                }
                //add invoice lines
                JFileChooser fc2 = new JFileChooser();
                int result2 = fc2.showOpenDialog(frame);
                if (result2 == JFileChooser.APPROVE_OPTION) {
                    second_path = fc2.getSelectedFile().getPath();

                    br2 = new BufferedReader(new FileReader(new File(second_path)));
                    line_Model = (DefaultTableModel) frame.getInvoiceItemsTable().getModel();
                    Object[] tableLines2 = br2.lines().toArray();
                    int[] n = new int[tableLines2.length];//  n=itemCount*itemPrice
                    int num;
                    int j = 0;
                    for (int i = 0; i < tableLines2.length; i++) {
                        String line = tableLines2[i].toString().trim();
                        String[] dataRow = line.split(",");
                        line_Model.addRow(dataRow);
                        n[i] = Integer.parseInt(line_Model.getValueAt(i, 2).toString()) * Integer.parseInt(line_Model.getValueAt(i, 3).toString());


                        String invNumStr = dataRow[0];
                        num = n[i];

                        //add Tolal to header
                        if (Integer.parseInt(invNumStr) == invNum) {
                            total += num;

                        } else {
                            header_Model.setValueAt(String.valueOf(total), j, 3);
                            invNum++;
                            j++;
                            total = 0;
                            total += num;
                        }
                        //end of adding Tolal to header

                        line_Model.setValueAt(String.valueOf(num), i, 4);
                    }
                    header_Model.setValueAt(String.valueOf(total), j, 3);//last total inv
                    //
                    NoNum = line_Model.getRowCount();
                    //
                }
            } catch (Exception ex) {
                Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                    br2.close();
                } catch (IOException ex) {
                    Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void createBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // create new invoice row as you typed in invoice data
        header_Model = (DefaultTableModel) frame.getInvoicesTable().getModel();
        header_Model.addRow(new Object[]{
                frame.getNumTextField().getText(),
                frame.getDateTextField().getText(),
                frame.getNameTextField().getText(),
                frame.getTotalTextField().getText()
        });
        NoNum = header_Model.getRowCount();
        NoNum++; //to increase invoice number
        frame.getNumTextField().setText(String.valueOf(NoNum));
    }

    public void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // save invoice items table in the specified csv file
        if (frame.getInvoiceItemsTable().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Empty!!, please load a .txt file");
        } else {

            BufferedWriter bw2 = null;
            File file2 = null;
            try {
                file2 = new File(second_path);
                bw2 = new BufferedWriter(new FileWriter(file2.getAbsoluteFile()));
                for (int i = 0; i < frame.getInvoiceItemsTable().getRowCount(); i++) {
                    for (int j = 0; j < frame.getInvoiceItemsTable().getColumnCount(); j++) {
                        String s = (String) frame.getInvoiceItemsTable().getModel().getValueAt(i, j);
                        if (s == null) {
                            bw2.write(" ");
                        } else {
                            bw2.write(s + ",");
                        }
                    }

                    bw2.write("\n");
                }
                JOptionPane.showMessageDialog(null, "Saved successfully");

            } catch (IOException ex) {
                Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bw2.close();
            } catch (IOException ex) {
                Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void saveFileTabActionPerformed(java.awt.event.ActionEvent evt) {
        // save 2 csv files

        BufferedWriter bw = null;
        BufferedWriter bw2 = null;

        try {
            File file = new File(first_path);
            bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            for (int i = 0; i < frame.getInvoicesTable().getRowCount(); i++) {
                for (int j = 0; j < frame.getInvoicesTable().getColumnCount(); j++) {
                    String s = (String) frame.getInvoicesTable().getModel().getValueAt(i, j);
                    if (s == null) {
                        bw.write(" ");
                    } else {
                        bw.write(s + ",");
                    }
                }
                bw.write("\n");
            }
            File file2 = new File(second_path);
            bw2 = new BufferedWriter(new FileWriter(file2.getAbsoluteFile()));
            for (int i = 0; i < frame.getInvoiceItemsTable().getRowCount(); i++) {
                for (int j = 0; j < frame.getInvoiceItemsTable().getColumnCount(); j++) {
                    String s = (String) frame.getInvoiceItemsTable().getModel().getValueAt(i, j);
                    if (s == null) {
                        bw2.write(" ");
                    } else {
                        bw2.write(s + ",");
                    }
                }
                bw2.write("\n");
            }

        } catch (IOException ex) {
            Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
                bw2.close();
            } catch (IOException ex) {
                Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        JOptionPane.showMessageDialog(null, "Two files saved sucessfully");

    }

    public void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {
        header_Model = (DefaultTableModel) frame.getInvoicesTable().getModel();
        if (frame.getInvoicesTable().getSelectedRowCount() == 1) { //if you already select a row
            header_Model.removeRow(frame.getInvoicesTable().getSelectedRow());            // remove selected row from the model
            JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
        } else {
            if (frame.getInvoicesTable().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Empty Table, please load a txt file");
            } else {
                JOptionPane.showMessageDialog(null, "Select a row to delete");

            }
        }
    }

    public void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {
        line_Model = (DefaultTableModel) frame.getInvoiceItemsTable().getModel();

        if (frame.getInvoiceItemsTable().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Empty Table, please load a txt file");
        } else {
            line_Model.setNumRows(0);
            BufferedReader br2 = null;
            try {
                File file2 = new File(second_path);
                br2 = new BufferedReader(new FileReader(file2));
                Object[] tableLines = br2.lines().toArray();
                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    line_Model.addRow(dataRow);
                }
            } catch (IOException ex) {
                Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br2.close();
                } catch (IOException ex) {
                    Logger.getLogger(InvoiceGeneratorUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
