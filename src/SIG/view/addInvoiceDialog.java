/**
 * @Created by       :Othman Ali
 * @Email :othmanalime@gmail.com
 * @version :1.1
 * @Date :23-10-2022
 *
 *
 */
package SIG.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

//form Create New Customer     : فورم اضافة عميل جديد
public class addInvoiceDialog extends JDialog {

    private JTextField customerName;
    private JTextField invoiceDate;
    private JLabel custNameLabel;
    private JLabel dateLabel;
    private JButton ok;
    private JButton cancel;

// 
    public addInvoiceDialog(InvoiceFrame frame) {
        custNameLabel = new JLabel("          New Customer Name:");
        customerName = new JTextField(30);
        dateLabel = new JLabel("         Date:");
        invoiceDate = new JTextField(30);
        ok = new JButton("Save");
        cancel = new JButton("Cancel");
        ok.setActionCommand("createInvoice");
        cancel.setActionCommand("cancelInvoice");
        ok.addActionListener(frame.getController());
        cancel.addActionListener(frame.getController());
        setLayout(new GridLayout(4, 4));
        add(custNameLabel);
        add(customerName);
        add(dateLabel);
        add(invoiceDate);
        add(ok);
        add(cancel);

        pack();

    }

    public JTextField getCustomerName() {
        return customerName;
    }

    public JTextField getInvoiceDate() {
        return invoiceDate;
    }

}
