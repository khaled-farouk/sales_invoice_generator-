package udacity;
import Actions.MyActions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import java.awt.TextField;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Main{
        public static void main(String args[]) {
            MyFrame frame = new MyFrame();
           MyActions lis = new MyActions(frame);

            frame.setTitle("Sales Invoice Generator");
            frame.setVisible(true);
        }

}