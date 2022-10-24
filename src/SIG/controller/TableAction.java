/**
 * @Created by       :Othman Ali
 * @Email            :othmanalime@gmail.com
 * @version          :1.1
 * @Date              :23-10-2022
 * 
 * 
 */
package SIG.controller;

import SIG.model.ShowLineTabel;                        //عرض الجدول 
import SIG.model.sigHeader;                            //
import SIG.model.sigItem;                              //المنتجات
import SIG.view.InvoiceFrame;                         // اطار الفاتورة 
import java.util.ArrayList;                           //تحويل الملف الي ليست
import javax.swing.event.ListSelectionEvent;        //الاجراء الممحدد وقت تنفيذ الكود والضغط علي زار معين
import javax.swing.event.ListSelectionListener;    //الاجراء المنفذ وقت تنفيذ البرنامج 

/**Sources and references
 * https://stackoverflow.com/questions/30401280/get-items-from-arraylist-in-arraylist/30401506#30401506
 * https://www.geeksforgeeks.org/arraylist-get-method-java-examples/
 * https://harmash.com/tutorials/java/overriding
 * https://www.tutorialspoint.com/java/java_overriding.htm
 * https://www.geeksforgeeks.org/overriding-in-java/
 * https://www.javatpoint.com/how-to-read-csv-file-in-java
 * https://www.geeksforgeeks.org/reading-csv-file-java-using-opencsv/
 * https://mkyong.com/java/how-to-export-data-to-csv-file-java/
 * 
 */ 

public class TableAction implements ListSelectionListener{
    private InvoiceFrame frame;

    public TableAction(InvoiceFrame frame) {
        this.frame = frame;
    }
    
 
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int invoiceIndex = frame.getTableInvoiceHeader().getSelectedRow();
        if(invoiceIndex!= -1){
             sigHeader selectedRow = frame.getInvoices().get(invoiceIndex);             //
             ArrayList<sigItem> items = selectedRow.getItems();                          //
             frame.getLabelCustomerName().setText(selectedRow.getName());               //الاسماء               
             frame.getLabelInvoiceNum().setText(""+selectedRow.getNum());               //        رقم الفاتورة 
             frame.getLabelInvoiceDate().setText(selectedRow.getDate());                //              التاريخ
             frame.getLabelTostalCost().setText(""+selectedRow.getTotalInvoice());     //       اجمالي المبلغ 
             ShowLineTabel line = new ShowLineTabel(items);                               //            المنتجات 
             frame.getTableInvoiceLines().setModel(line);                                 //            الفواتير 
             line.fireTableDataChanged();
             
        }
    }
    
}
