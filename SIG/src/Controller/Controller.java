package Controller;

import listeners.InvoicesLine;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import listeners.*;
import model.FileOp;
import model.InvoiceHeader;
import View.View;

/**
 *
 * @author khaled
 */
public class Controller implements ActionListener, KeyListener
{
    private  View          view;
    private FileOp fileOperations;
    private LoadTables loadTablesContents;
    private TableContent cleanTablesContent;
    public volatile static ArrayList <InvoiceHeader> invoices= new ArrayList<>();
    public volatile static int selectedRow=0;
    public volatile static boolean isThereIsNotSavedEdit = false;
    public volatile static int maxNumberOfExistedInvoices=0;
    /*Listeners Objects*/
    private InvoiceTable invoiceTableListener;
    private InvoicesLine invoicesLineTableListener;
    private FileMenitem fileMenuItemsListener;
    private MainFrameWin mainFrameWindowListener;
    private MainFrameMotion mainFrameMouseMotionListener;
    private AddDialListener addItemDialogWindowListener;
    private InvoiceDate invoiceDateTextFieldListener;
    private CustomerName customerNameTextFieldListener;
    public Controller (ArrayList<InvoiceHeader> invoices, View view) 
    {
        this.invoices=invoices;
        this.view = view;
        fileOperations= new FileOp(this.view);
        /*Listeners Initiation*/
        invoicesLineTableListener=new InvoicesLine(view);
        invoiceTableListener=new InvoiceTable(view,fileOperations,invoicesLineTableListener);
        fileMenuItemsListener=new FileMenitem(view,fileOperations,invoiceTableListener);
        mainFrameWindowListener=new MainFrameWin(view,fileOperations,invoiceTableListener);
        addItemDialogWindowListener=new AddDialListener(view);
        mainFrameMouseMotionListener=new MainFrameMotion(view);
        invoiceDateTextFieldListener=new InvoiceDate(view);
        customerNameTextFieldListener=new CustomerName(view);
        turnOnAllActionListerners(view);
        loadTablesContents= new LoadTables();
        cleanTablesContent= new TableContent();
    }
    private void turnOnAllActionListerners(View view) 
    {
        view.getLoadFile().addActionListener(fileMenuItemsListener);
        view.getLoadFile().setActionCommand("Load Fisle Sequence");
        view.getSaveFile().addActionListener(fileMenuItemsListener);
        view.getSaveFile().setActionCommand("Save File");
        view.getFileMenu().addMenuListener(fileMenuItemsListener);
        view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
        view.getInvoicesLineTable().getSelectionModel().addListSelectionListener(invoicesLineTableListener);
        view.addWindowListener(mainFrameWindowListener);
        view.getAddItemDialog().addWindowListener(addItemDialogWindowListener);
        view.getInvoiceDateTextField().addActionListener(invoiceDateTextFieldListener);
        view.getInvoiceDateTextField().addFocusListener(invoiceDateTextFieldListener);
        view.getCustomerNameTextField().addActionListener(customerNameTextFieldListener);
        view.getCustomerNameTextField().addFocusListener(customerNameTextFieldListener);
        view.getRootPane().addMouseMotionListener(mainFrameMouseMotionListener);        
        view.getCreatNewInvoiceButton().addActionListener(this);
        view.getCreatNewInvoiceButton().setActionCommand("Creat New Invoice");
        view.getCreatNewInvoiceOK().addActionListener(this);
        view.getCreatNewInvoiceOK().setActionCommand("Creat New Invoice OK");
        view.getCreatNewInvoiceCancel().addActionListener(this);
        view.getCreatNewInvoiceCancel().setActionCommand("Creat New Invoice Cancel");
        view.getDeleteInvoiceButton().addActionListener(this);
        view.getDeleteInvoiceButton().setActionCommand("Delete Invoice");
        view.getAddItemButton().addActionListener(this);
        view.getAddItemButton().setActionCommand("Add Item");
        view.getNewItemPrice().addKeyListener(this);
        view.getAddItemDialogCancel().addActionListener(this);
        view.getAddItemDialogCancel().setActionCommand("Add Item Dialog Cancel");
        view.getAddItemDialogOK().addActionListener(this);
        view.getAddItemDialogOK().setActionCommand("Add Item Dialog OK");
        view.getDeleteItemButton().addActionListener(this);
        view.getDeleteItemButton().setActionCommand("Delete Item");
        view.getCancelButton().addActionListener(this);
        view.getCancelButton().setActionCommand("Cancel Any Changes");
    }
    /**
    *
    * Implement All Listeners
    * 
    */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch(e.getActionCommand())
        {
            case "Creat New Invoice":
            {
                if(view.getFocusOwner()!=null)
                LeftSide.showCreatNewInvoiceDialog(view);
                break;
            }
            case "Creat New Invoice OK":
            {
                //disable list selection listener for a moment
                view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                //add new invoice
                LeftSide.addNewInvoice(view,invoices);
                //enable list selection listener
                view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
                if(isThereIsNotSavedEdit)
                {
                    view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                }
                else
                {
                    view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                }
                break;
            }
            case "Creat New Invoice Cancel":
            {
                view.getNewCustomerName().setText("");
                View.getCreatNewInvoiceDialog().setVisible(false);
                break;
            }
            case "Delete Invoice":
            {
                if(view.getFocusOwner()!=null)
                {
                    //disable list selection listener for a moment
                    view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    //delete the selected invoice
                    LeftSide.deleteSelectedInvoice(view,invoices);
                    //enable list selection listener
                    view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
                    if(isThereIsNotSavedEdit)
                    {
                        view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                    }
                    else
                    {
                        view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                    }
                }
                break;
            }
            case "Add Item":
            {
                if(view.getFocusOwner()!=null)
                RightSide.showCreatNewItemDialog(view);
                break;
            }
            case "Add Item Dialog OK":
            {
                RightSide.AddNewItem(view,invoices);
                LeftSide.calculateInvoiceTableTotal(invoices);
                LeftSide.updateTableTotal(view, invoices);
                RightSide.rightSideTextUpdater(view, invoices, view.getInvoiceTable().getSelectedRow());
                LoadTables.loadInvoicesLineTable(view, invoices);
                int sizeOfinvoicesLinesForTheSelectedInvoice=invoices.get(view.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                view.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice-1), (sizeOfinvoicesLinesForTheSelectedInvoice-1));
                View.getAddItemDialog().setVisible(false);
                if(isThereIsNotSavedEdit)
                {
                    view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                }
                else
                {
                    view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                }
                break;
            }
            case "Add Item Dialog Cancel":
            {
                View.getAddItemDialog().setVisible(false);
                view.getNewItemName().setText("");
                view.getNewItemPrice().setText("");
                view.getNewItemPriceSpinner().setValue((Object)1);
                break;
            }
            case "Delete Item":
            {
                if(view.getFocusOwner()!=null)
                {
                    RightSide.DeleteItem(view,invoices);
                    LeftSide.calculateInvoiceTableTotal(invoices);
                    LeftSide.updateTableTotal(view, invoices);
                    RightSide.rightSideTextUpdater(view, invoices, view.getInvoiceTable().getSelectedRow());
                    System.out.println(selectedRow);
                    LoadTables.loadInvoicesLineTable(view, invoices);
                    int sizeOfinvoicesLinesForTheSelectedInvoice=invoices.get(view.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                    if(sizeOfinvoicesLinesForTheSelectedInvoice>0)
                    view.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice-1), (sizeOfinvoicesLinesForTheSelectedInvoice-1));
                    if(isThereIsNotSavedEdit)
                    {
                        view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                    }
                    else
                    {
                        view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                    }
                }
                break;
            }
            case "Cancel Any Changes":
            {
                //Reload CSV files into tables
                if((FileOp.selectedInvoiceHeader!=null)&&(FileOp.selectedInvoiceLine!=null))
                {
                    view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    invoices=fileOperations.readFile();
                    LeftSide.calculateInvoiceTableTotal(invoices);
                    loadTablesContents.loadInvoicesHeaderTable(view,invoices);
                    maxNumberOfExistedInvoices=0;
                    fileOperations.getMaxNumberOfExistedInvoices(maxNumberOfExistedInvoices,invoices);
                    isThereIsNotSavedEdit=false;
                    view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
                    if(invoices.size()>=1)
                    view.getInvoiceTable().setRowSelectionInterval(0, 0);
                }
                if(isThereIsNotSavedEdit)
                {
                    view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                }
                else
                {
                    view.getCancelButton().setEnabled(isThereIsNotSavedEdit);
                }
                break;
            }
        }
        
    }
    //validate new item price
    @Override
    public void keyTyped(KeyEvent evnt)
    {
        char price=evnt.getKeyChar();
        if(Character.isLetter(price)&&!evnt.isAltDown()&&evnt.isShiftDown()&&evnt.isControlDown())
        {
            evnt.consume();
        }
        if((price=='f')||(price=='d'))
                evnt.consume();
        try {
            Float.parseFloat(view.getNewItemPrice().getText()+price);
        } catch (Exception e) {
            evnt.consume();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}
}
