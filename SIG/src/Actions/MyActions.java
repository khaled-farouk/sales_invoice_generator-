/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Actions;

//import Model.InvoiceLine;
import udacity.MyFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    MyFrame frame;
    String first_path = "../InvoiceHeader.csv";
    String second_path = "../InvoiceLine.csv";
    static int NoNum;
    DefaultTableModel model;
//    DefaultTableModel IHmodel = (DefaultTableModel) frame.getInvoicesTable().getModel();
//    DefaultTableModel ILmodel = (DefaultTableModel) frame.getInvoiceItemsTable().getModel();
//

    public MyActions(MyFrame frame) {
        this.frame = frame;
    }

    public void loadFile(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Please, select header file!", "Attension", JOptionPane.WARNING_MESSAGE);
        // if you want to choose the 2 files
        //two path fields will be changed as you selected
        BufferedReader br = null;
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            first_path = fc.getSelectedFile().getPath();

            try {
                br = new BufferedReader(new FileReader(new File(first_path)));
                model = (DefaultTableModel) frame.getInvoicesTable().getModel();
                Object[] tableLines = br.lines().toArray();
                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    model.addRow(dataRow);
                }
            } catch (IOException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //line..line
        BufferedReader br2 = null;
        JFileChooser fc2 = new JFileChooser();
        int result2 = fc2.showOpenDialog(frame);
        if (result2 == JFileChooser.APPROVE_OPTION) {
            second_path = fc2.getSelectedFile().getPath();

            try {
                br2 = new BufferedReader(new FileReader(new File(second_path)));
                model = (DefaultTableModel) frame.getInvoiceItemsTable().getModel();
                Object[] tableLines = br2.lines().toArray();
                //String s= model.getColumnName(4);
                int[] n = new int[tableLines.length];//  n=itemCount*itemPrice
                int num;
                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    model.addRow(dataRow);
                    n[i] = Integer.parseInt(model.getValueAt(i, 2).toString()) * Integer.parseInt(model.getValueAt(i, 3).toString());

                    //System.out.println(n[i]);
                    num = n[i];
                    model.setValueAt(String.valueOf(num), i, 4);
                }
                //
                NoNum = model.getRowCount();




            } catch (IOException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br2.close();
                } catch (IOException ex) {
                    Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    public void createBtnActionPerformed(ActionEvent evt) {
        // create new invoice row as you typed in invoice data
        model = (DefaultTableModel) frame.getInvoicesTable().getModel();
        model.addRow(new Object[]{
                frame.getNumTextField().getText(),
                frame.getDateTextField().getText(),
                frame.getNameTextField().getText(),
                frame.getTotalTextField().getText()
        });
        NoNum = model.getRowCount();
        NoNum++; //to increase invoice number
        frame.getNumTextField().setText(String.valueOf(NoNum));
    }

    public void saveBtnActionPerformed(ActionEvent evt) {
        // save invoice items table in the specified csv file
        if (frame.getInvoiceItemsTable().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Empty Table, please load a txt file");
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
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                bw2.close();
            } catch (IOException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void saveFileTabActionPerformed(ActionEvent evt) {
        // save 2 csv files

        BufferedWriter bw = null;
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

        } catch (IOException ex) {
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        BufferedWriter bw2 = null;
        try {
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
            Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw2.close();
            } catch (IOException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {
        model = (DefaultTableModel) frame.getInvoicesTable().getModel();
        if (frame.getInvoicesTable().getSelectedRowCount() == 1) { //if you already select a row
            model.removeRow(frame.getInvoicesTable().getSelectedRow());            // remove selected row from the model
            JOptionPane.showMessageDialog(null, "Selected row deleted successfully");
        } else {
            if (frame.getInvoicesTable().getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Empty Table, please load a txt file");
            } else {
                JOptionPane.showMessageDialog(null, "Select a row to delete");

            }
        }
    }

    public void cancelBtnActionPerformed(ActionEvent evt) {
        // undo changes , read the file again
        model = (DefaultTableModel) frame.getInvoiceItemsTable().getModel();

        if (frame.getInvoiceItemsTable().getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Empty Table, please load a txt file");
        } else {
            model.setNumRows(0);
            BufferedReader br2 = null;
            try {
                File file2 = new File(second_path);
                br2 = new BufferedReader(new FileReader(file2));
                Object[] tableLines = br2.lines().toArray();
                for (int i = 0; i < tableLines.length; i++) {
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    model.addRow(dataRow);
                }
            } catch (IOException ex) {
                Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    br2.close();
                } catch (IOException ex) {
                    Logger.getLogger(MyFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

}
