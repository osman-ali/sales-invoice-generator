/**
 * @Created by       :Othman Ali
 * @Email            :othmanalime@gmail.com
 * @version          :1.1
 * @Date              :23-10-2023
 * 
 * 
 */
package SIG.model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import SIG.view.InvoiceFrame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class FileOperations {
    private InvoiceFrame frame;
    //headerFile name
    public FileOperations(InvoiceFrame frame) {
        this.frame = frame;
    }

 public ArrayList<sigHeader> readFile(){
     String headerPath;
    //items file name
     String itemPath;
        File headerFile ;
        File itemFile;
        List<String> headerLines=null;
        List<String> itemLines=null;
        int result;
        ArrayList<sigHeader> invArray = new ArrayList<>();
  
            JOptionPane.showMessageDialog(frame, "Please insert Headers File then Lines File يجب اختيار ملف العملاء ثم ملف المنتجات "); 

            JFileChooser file = new JFileChooser();
            do{//do not close till the user chooses the right file
               result = file.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                headerFile = file.getSelectedFile();
                if(headerFile.getName().contains(".csv")){
                    headerPath=headerFile.getAbsolutePath();
                    break;
                }
                else{//display an error message
                  System.out.println("Wrong Headers File Formatتنسيق الملف غير صحيح اختار الملف الصحيح  ");
                 JOptionPane.showMessageDialog(frame, "Wrong Headers File Format please insert the correct file againتنسيق الملف غير صحيح اختار الملف الصحيح ");  
                }
            }
            }while(true);
            do{//do not close till the user chooses the right file
                result = file.showOpenDialog(frame);
                
                if (result == JFileChooser.APPROVE_OPTION) {
                    itemFile = file.getSelectedFile();
                    if(itemFile.getName().contains(".csv")){
                        itemPath= itemFile.getAbsolutePath();
                        break;
                    }
                    else{
                        //display an error message
                    System.out.println("Wrong Items File Formatملف المنتجات غير سليم ");
                 JOptionPane.showMessageDialog(frame, "Wrong Items File Formatملف المنتجات غير سليم ");
                }

                }
            }while(true);

            System.out.println(headerPath);
            System.out.println(itemPath);

                //ready each line in each file as string then save them in array of strings
               
            try {
                headerLines = Files.lines(Paths.get(headerPath)).collect(Collectors.toList());
            } catch (IOException ex) {
                System.out.println("Wrong Headers File Path مسار ملف العملاء غلط");
                 JOptionPane.showMessageDialog(frame, "Wrong Headers File Path مسار ملف العملاء غلط");
            }

            try {
                itemLines = Files.lines(Paths.get(itemPath)).collect(Collectors.toList());
            } catch (IOException ex) {
                System.out.println("Wrong Items File Path مسار ملف المنتجات غلط");
                 JOptionPane.showMessageDialog(frame, "Wrong Items File Path مسار ملف المنتجات غلط");
            }
               
                if(headerLines!=null && itemLines !=null){
                for (String headerLine : headerLines) {
                    /*split each element in header arrays that has the invoices from header file
                    then dipose ',' and save each string in another array of strings
                    */
                    String[] arr = headerLine.split(",");
                    String numToString = arr[0];
                    String date = arr[1];
                    String customerName = arr[2];
                    int num = Integer.parseInt(numToString);
                    //create new invoice and add it in  invArray
                    sigHeader invoice = new sigHeader(num, customerName, date);
                    invArray.add(invoice);
                    frame.getInvoices().add(invoice);
                   
                }
                /*split each element in itemLine arrays that has the invoices from items file
                    then dipose ',' and save each string in another array of strings
                    */
                for (String itemLine : itemLines) {
                    String[] arr = itemLine.split(",");
                    int num = Integer.parseInt(arr[0]);
                    String name1 = arr[1];
                    double unitPrice = Double.parseDouble(arr[2]);
                    int quantity = Integer.parseInt(arr[3]);
                    //create new line and add it inside its invoice
                    sigHeader invoice=getInvoiceByNum(num);
                    sigItem line = new sigItem(name1,quantity,unitPrice,invoice);
                    invoice.getItems().add(line);

                }
                }
                //update the two tables
                
                return invArray;        
            //}     
 }

 public sigHeader getInvoiceByNum(int num){
    for(sigHeader inv : frame.getInvoices()){
        if(inv.getNum()==num){
            return inv;
        }
    } 
        return null;
}

 public void saveFile(ArrayList<sigHeader> headers) {
        /*create new array of sigHeader to get the current invoices
        create two strings to stroe in them the final data as string data
        */
       //Header file name

        String invoices = "";
        String items = "";
        File headerFile;
        File lineFile;
        int result;
        /*read each invoice from the header table and save it in a new line inside invoices string
        then read each item line inside the invoice and save it i a new line inside items string
        */
        for(sigHeader header: headers){
            String headerLines = header.getInvoicesFromTabel();
            invoices=invoices+headerLines;
            invoices=invoices+"\n";
            
            for(sigItem item : header.getItems()){
                String itemFile = item.getItemsFromTabel();
                items = items+itemFile;
                items = items+"\n";
            }
        }
        //open two file chooser to save these two strings inside the desired files if they are not found
        
        JOptionPane.showMessageDialog(frame, "Kindly choose the Headers file then Lines file يجب اختيار ملف العملاء ثم ملف المنتجات");
        JFileChooser file = new  JFileChooser();
        do{//donot break till the user choose the right format
        result = file.showSaveDialog(frame);
        if(result == JFileChooser.APPROVE_OPTION){
            headerFile = file.getSelectedFile();
            if(headerFile.getName().contains(".csv")){//write only in the correct file
                FileWriter headFileWriter = null;
                try {
                    headFileWriter = new FileWriter(headerFile);
                    headFileWriter.write(invoices);
                    headFileWriter.flush();
                    break;
                } catch (IOException ex) {
                    Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        headFileWriter.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                }
                else{
                  System.out.println("Wrong Headers File Format ملف العملاء بتنسيق خطأ ");
                 JOptionPane.showMessageDialog(frame, "Wrong Headers File Format ملف العملاء بتنسيق خطأ ");  
                }
        }
        }while(true);
            
           
            do{//donot break till the user choose the right format
            result = file.showSaveDialog(frame);
            if(result == JFileChooser.APPROVE_OPTION){
                lineFile= file.getSelectedFile();
                if(lineFile.getName().contains(".csv")){//write only in the correct file
                FileWriter lineFileWriter = null;
                    try {
                        lineFileWriter = new FileWriter(lineFile);
                        lineFileWriter.write(items);
                        lineFileWriter.flush();
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            lineFileWriter.close();
                        } catch (IOException ex) {
                            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            }
            }
            else{
                System.out.println("Wrong Lines File Format ملف المنتجات بتنسيق خطأ ");
                 JOptionPane.showMessageDialog(frame, "Wrong Lines File Format ملف المنتجات بتنسيق خطأ ");   
            }
        }while(true);
       
        
}
}