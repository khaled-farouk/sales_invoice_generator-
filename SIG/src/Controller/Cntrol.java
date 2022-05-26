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
public class Cntrol implements ActionListener, KeyListener
{
    private  View vieww;
    private FileOp fileOpe;
    private LoadTables ModifyloadTable;
    private TableData cleanTable;
    public volatile static ArrayList <InvoiceHeader> invoices= new ArrayList<>();
    public volatile static int selectedR =0;
    public volatile static boolean isThereIsnotSvdEd = false;
    public volatile static int maxNumInvoices =0;
    /*Listeners Objects*/
    private InvoiceTable TableActions;
    private InvoicesLine LineActions;
    private FileMenitem fileActions;
    private MainFrameWin mainWindowActions;
    private MainFrameMotion MotionActions;
    private AddDialListener AddWindowActions;
    private InvoiceDate UpdateDateActions;
    private CustomerName customerNameActions;
    public Cntrol(ArrayList<InvoiceHeader> invoices, View view)
    {
        this.invoices=invoices;
        this.vieww = view;
        fileOpe = new FileOp(this.vieww);
        /*Listeners Initiation*/
        LineActions =new InvoicesLine(view);
        TableActions =new InvoiceTable(view, fileOpe, LineActions);
        fileActions =new FileMenitem(view, fileOpe, TableActions);
        mainWindowActions =new MainFrameWin(view, fileOpe, TableActions);
        AddWindowActions =new AddDialListener(view);
        MotionActions =new MainFrameMotion(view);
        UpdateDateActions =new InvoiceDate(view);
        customerNameActions =new CustomerName(view);
        ADdingActions(view);
        ModifyloadTable = new LoadTables();
        cleanTable = new TableData();
    }
    private void ADdingActions(View view)
    {
        view.getLoadFile().addActionListener(fileActions);
        view.getLoadFile().setActionCommand("Load Fisle Sequence");
        view.getSaveFile().addActionListener(fileActions);
        view.getSaveFile().setActionCommand("Save File");
        view.getFileMenu().addMenuListener(fileActions);
        view.getInvoiceTable().getSelectionModel().addListSelectionListener(TableActions);
        view.getInvoicesLineTable().getSelectionModel().addListSelectionListener(LineActions);
        view.addWindowListener(mainWindowActions);
        view.getAddItemDialog().addWindowListener(AddWindowActions);
        view.getInvoiceDateTextField().addActionListener(UpdateDateActions);
        view.getInvoiceDateTextField().addFocusListener(UpdateDateActions);
        view.getCustomerNameTextField().addActionListener(customerNameActions);
        view.getCustomerNameTextField().addFocusListener(customerNameActions);
        view.getRootPane().addMouseMotionListener(MotionActions);
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
                if(vieww.getFocusOwner()!=null)
                LeftPanell.CreatNewInvoic(vieww);
                break;
            }
            case "Creat New Invoice OK":
            {
                //disable list selection listener for a moment
                vieww.getInvoiceTable().getSelectionModel().removeListSelectionListener(TableActions);
                //add new invoice
                LeftPanell.addNewInvo(vieww,invoices);
                //enable list selection listener
                vieww.getInvoiceTable().getSelectionModel().addListSelectionListener(TableActions);
                if(isThereIsnotSvdEd)
                {
                    vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                }
                else
                {
                    vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                }
                break;
            }
            case "Creat New Invoice Cancel":
            {
                vieww.getNewCustomerName().setText("");
                View.getCreatNewInvoiceDialog().setVisible(false);
                break;
            }
            case "Delete Invoice":
            {
                if(vieww.getFocusOwner()!=null)
                {
                    //disable list selection listener for a moment
                    vieww.getInvoiceTable().getSelectionModel().removeListSelectionListener(TableActions);
                    //delete the selected invoice
                    LeftPanell.deletechooseninvo(vieww,invoices);
                    //enable list selection listener
                    vieww.getInvoiceTable().getSelectionModel().addListSelectionListener(TableActions);
                    if(isThereIsnotSvdEd)
                    {
                        vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                    }
                    else
                    {
                        vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                    }
                }
                break;
            }
            case "Add Item":
            {
                if(vieww.getFocusOwner()!=null)
                RightPanell.CreateeNewItem(vieww);
                break;
            }
            case "Add Item Dialog OK":
            {
                RightPanell.AddNewONe(vieww,invoices);
                LeftPanell.calculateeTotal(invoices);
                LeftPanell.updateTotal(vieww, invoices);
                RightPanell.rightSideTextUpdat(vieww, invoices, vieww.getInvoiceTable().getSelectedRow());
                LoadTables.loadInvoicesLineTable(vieww, invoices);
                int sizeOfinvoicesLinesForTheSelectedInvoice=invoices.get(vieww.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                vieww.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice-1), (sizeOfinvoicesLinesForTheSelectedInvoice-1));
                View.getAddItemDialog().setVisible(false);
                if(isThereIsnotSvdEd)
                {
                    vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                }
                else
                {
                    vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                }
                break;
            }
            case "Add Item Dialog Cancel":
            {
                View.getAddItemDialog().setVisible(false);
                vieww.getNewItemName().setText("");
                vieww.getNewItemPrice().setText("");
                vieww.getNewItemPriceSpinner().setValue((Object)1);
                break;
            }
            case "Delete Item":
            {
                if(vieww.getFocusOwner()!=null)
                {
                    RightPanell.DeleteItem(vieww,invoices);
                    LeftPanell.calculateeTotal(invoices);
                    LeftPanell.updateTotal(vieww, invoices);
                    RightPanell.rightSideTextUpdat(vieww, invoices, vieww.getInvoiceTable().getSelectedRow());
                    System.out.println(selectedR);
                    LoadTables.loadInvoicesLineTable(vieww, invoices);
                    int sizeOfinvoicesLinesForTheSelectedInvoice=invoices.get(vieww.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                    if(sizeOfinvoicesLinesForTheSelectedInvoice>0)
                    vieww.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice-1), (sizeOfinvoicesLinesForTheSelectedInvoice-1));
                    if(isThereIsnotSvdEd)
                    {
                        vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                    }
                    else
                    {
                        vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                    }
                }
                break;
            }
            case "Cancel Any Changes":
            {
                //Reload CSV files into tables
                if((FileOp.selectINvOHeader !=null)&&(FileOp.selectInVoLine !=null))
                {
                    vieww.getInvoiceTable().getSelectionModel().removeListSelectionListener(TableActions);
                    invoices= fileOpe.readFile();
                    LeftPanell.calculateeTotal(invoices);
                    ModifyloadTable.loadInvoicesHeaderTable(vieww,invoices);
                    maxNumInvoices =0;
                    fileOpe.getMaxNumberOfExistedInvoices(maxNumInvoices,invoices);
                    isThereIsnotSvdEd =false;
                    vieww.getInvoiceTable().getSelectionModel().addListSelectionListener(TableActions);
                    if(invoices.size()>=1)
                    vieww.getInvoiceTable().setRowSelectionInterval(0, 0);
                }
                if(isThereIsnotSvdEd)
                {
                    vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
                }
                else
                {
                    vieww.getCancelButton().setEnabled(isThereIsnotSvdEd);
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
            Float.parseFloat(vieww.getNewItemPrice().getText()+price);
        } catch (Exception e) {
            evnt.consume();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e){}
}
