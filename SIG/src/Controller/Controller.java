package Controller;

import View.InvoiceGeneratorUI;
import View.HeaderDialog;
import View.LineDialog;
import Model.HeaderTable;
import Model.InvoiceHeader;
import Model.InvoiceLine;
import Model.LineTable;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Controller extends JFrame {

    public Controller(InvoiceGeneratorUI frame) throws HeadlessException {
        this.frame = frame;
    }

    InvoiceGeneratorUI frame;
    InvoiceHeader header;
    private ArrayList<InvoiceHeader> invoices = new ArrayList<>();
    private HeaderTable headerTable;
    private LineTable lineTable;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
    private ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();
    HeaderDialog headerDialog;
    LineDialog lineDialog;


    public void loadFiles() throws Exception, ParseException {
        invoices.clear();
        invoiceLines.clear();
        JOptionPane.showMessageDialog(this, "Please select Invoice Header file",
                "Invoice Header", JOptionPane.WARNING_MESSAGE);
        JFileChooser fc = new JFileChooser();
        int option = fc.showOpenDialog(this);
        File selectedFile;
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            FileReader fr = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] headerSegments = line.split(",");
                String invNumStr = headerSegments[0];
                String invDateStr = headerSegments[1];
                String customerName = headerSegments[2];
                int invNum = Integer.parseInt(invNumStr);
                dateFormat = new SimpleDateFormat("dd-mm-yyyy");
                Date invDate = dateFormat.parse(invDateStr);
                InvoiceHeader header = new InvoiceHeader(invNum, invDate, customerName);
                invoices.add(header);
            }
            br.close();
            fr.close();
        }



        JOptionPane.showMessageDialog(this, "Please select Invoice Line file",
                "Invoice Line", JOptionPane.WARNING_MESSAGE);
        option = fc.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            FileReader fr = new FileReader(selectedFile);
            BufferedReader br = new BufferedReader(fr);
            fr = new FileReader(selectedFile);
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] lineSegments = line.split(",");
                String invNumStr = lineSegments[0];
                String item = lineSegments[1];
                String priceStr = lineSegments[2];
                String countStr = lineSegments[3];
                int invNum = Integer.parseInt(invNumStr);
                double price = Double.parseDouble(priceStr);
                int count = Integer.parseInt(countStr);

                header = findByNum(invNum);
                InvoiceLine invLine = new InvoiceLine(item, price, count, header);
                header.addLine(invLine);
            }
            br.close();
            fr.close();

            headerTable = new HeaderTable(invoices);
            headerTable = new HeaderTable(invoices);
            frame.getjTable1().setModel(headerTable);
            frame.getjTable1().validate();

        }
    }




        public void saveDataInLines() throws IOException {
        JOptionPane.showMessageDialog(this,"Please Save Header File","Invoice Line ", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION){
            File newFile = fileChooser.getSelectedFile();
          PrintWriter printWriter = new PrintWriter(newFile);
          for(InvoiceLine invoiceLine : invoiceLines){
              printWriter.printf("%d,%s,%s",invoiceLine.getNumOfItems(),invoiceLine.getProduct(),invoiceLine.getPrice(),invoiceLine.getCount());
              printWriter.println();
          }
          printWriter.close();
          JOptionPane.showMessageDialog(this,"Successfully saved", "Information",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void saveDataInHeader() throws Exception {
        JOptionPane.showMessageDialog(this,"Please Save Header File","Invoice Header ", JOptionPane.WARNING_MESSAGE);
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(this);
        if (option ==JFileChooser.APPROVE_OPTION);
        File newFile = fileChooser.getSelectedFile();
        PrintWriter printWriter = new PrintWriter(newFile);
        for(InvoiceHeader header : invoices){
            printWriter.printf("%d,%s,%s",header.getNum(),header.getDate().toString(),header.getCustomerName());
            printWriter.println();
        }
        printWriter.close();
        JOptionPane.showMessageDialog(this,"File Saved","Information",JOptionPane.INFORMATION_MESSAGE);
    }


    public void createInvoice(){
        headerDialog = new HeaderDialog(frame);
        headerDialog.setVisible(true);
    }


    public void createInvoiceOk() {

        String customerNameInput = headerDialog.getCustomerNameField().getText();
        String invoiceDateInputStr = headerDialog.getDateField().getText();
        Date invoiceDateInput = new Date();
        try {
            invoiceDateInput = dateFormat.parse(invoiceDateInputStr);
        } catch (ParseException exception) { }
        headerDialog.setVisible(false);
        int num = getMaxNum() + 1;
        InvoiceHeader newInvoiceHeader = new InvoiceHeader(num,invoiceDateInput,customerNameInput);

        invoices.add(newInvoiceHeader);
        headerTable = new HeaderTable(invoices);
        frame.getjTable1().setModel(headerTable);
        headerTable.fireTableDataChanged();
    }

    public void createInvoiceCancel(){
        headerDialog.setVisible(false);
    }


    public void addNewItem(){
        lineDialog = new LineDialog(frame);
        lineDialog.setVisible(true);
    }



    public void addNewItemOk(){
        String itemNameInput = lineDialog.getItemNameField().getText();
        String itemCountStr = lineDialog.getItemCountField().getText();
        String itemPriceStr = lineDialog.getItemPriceField().getText();
        int itemCount = Integer.parseInt(itemCountStr);
        double itemPrice = Double.parseDouble(itemPriceStr);

        lineDialog.setVisible(false);

        int row = frame.getjTable1().getSelectedRow();
        if (row > 0) {
            InvoiceHeader header = invoices.get(row);
            InvoiceLine line = new InvoiceLine(itemNameInput,itemPrice,itemCount,header);
            header.addLine(line);
            lineTable.fireTableDataChanged();
            headerTable.fireTableDataChanged();
        }

    }

    public void cancelAddingNewItem (){
        lineDialog.setVisible(false);}

    public void deleteInvoice(){
        int IndexOfRow = frame.getjTable1().getSelectedRow();
        if (IndexOfRow >= 0) {
            headerTable.removeRow(IndexOfRow);
            headerTable.fireTableDataChanged();
            lineTable.fireTableDataChanged();
        }
    }

        public void deleteItem(){
        int selectedRow = frame.getjTable2().getSelectedRow();
        if (selectedRow >= 0) {
            lineTable.removeRow(selectedRow);
            lineTable.fireTableDataChanged();
            headerTable.fireTableDataChanged();
        }
    }


    public InvoiceHeader findByNum(int num){
        for (InvoiceHeader header : invoices ){
            if(header.getNum()== num){
                return header;
            }
        }
        return null;
    }

    public void jTable1RowSelected() {

        int rowIndex = frame.getjTable1().getSelectedRow();
        if (rowIndex >= 0) {
            InvoiceHeader row = headerTable.getInvoicesFromHeader().get(rowIndex);
            frame.getInvNumLbl().setText("" + row.getNum());
            frame.getInvDateTF().setText(row.getDate().toString());
            frame.getCusNameTF().setText(row.getCustomerName());
            frame.getInvTotalLb().setText("" + row.getInvHeaderTotal());
            ArrayList<InvoiceLine> invoiceLines = row.getLines();
            lineTable = new LineTable(invoiceLines);
            frame.getjTable2().setModel(lineTable);
            lineTable.fireTableDataChanged();

        }
    }

        public int getMaxNum (){
        int num = 0;
        for (InvoiceHeader invoiceHeader : invoices){
            if (invoiceHeader.getNum() > num){
                num = invoiceHeader.getNum();
            }
        }
        return num;
    }

}



