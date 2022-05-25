package View;

import javax.swing.*;
import java.awt.*;

public class HeaderDialog extends JDialog {
    private JTextField customerNameField;
    private JTextField dateField;
    private JLabel customerNameLabel;
    private JLabel dateLabel;
    private JButton saveButton;
    private JButton cancelButton;

public HeaderDialog (InvoiceGeneratorUI f) {
    customerNameLabel = new JLabel("Customer Name");
    customerNameField = new JTextField("");
    dateLabel = new JLabel("Invoice Date");
    dateField = new JTextField("");
    saveButton = new JButton("Save");
    saveButton.addActionListener(f);
    saveButton.setActionCommand("Create Invoice Ok");
    cancelButton = new JButton("Cancel");
    cancelButton.addActionListener(f);
    cancelButton.setActionCommand("Create Invoice Cancel");
    setLayout(new GridLayout(3,2));
    add(dateLabel);
    add(dateField);
    add(customerNameLabel);
    add(customerNameField);
    add(saveButton);
    add(cancelButton);
    pack();
    setTitle("Create new Invoice");
}

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    public JTextField getDateField() {
        return dateField;
    }
}