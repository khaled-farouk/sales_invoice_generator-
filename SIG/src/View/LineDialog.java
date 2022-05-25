package View;

import javax.swing.*;
import java.awt.*;

public class LineDialog extends JDialog {
    private JTextField itemNameField;
    private JTextField itemCountField;
    private JTextField itemPriceField;
    private JLabel itemNameLabel;
    private JLabel itemCountLabel;
    private JLabel itemPriceLabel;
    private JButton saveButton;
    private JButton cancelButton;

    public LineDialog (InvoiceGeneratorUI f) {
        itemNameLabel = new JLabel("Product Name");
        itemNameField = new JTextField("");
        itemCountLabel = new JLabel("How Many?");
        itemCountField = new JTextField("");
        itemPriceLabel = new JLabel("Price");
        itemPriceField = new JTextField("");

        saveButton = new JButton("Save");
        saveButton.addActionListener(f);
        saveButton.setActionCommand("Add new Item OK");
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(f);
        cancelButton.setActionCommand("Add New Item Cancel");
        setLayout(new GridLayout(4,2));
        add(itemNameLabel);
        add(itemNameField);
        add(itemCountLabel);
        add(itemCountField);
        add(itemPriceLabel);
        add(itemPriceField);
        add(saveButton);
        add(cancelButton);
        pack();
        setTitle("Add New Item");



    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
}
