package test;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class TestOther extends JFrame {
//	public TestOther(String title) {
//		super(title);
//		
//		addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				dispose();
//			}
//
//			public void windowClosed(WindowEvent e) {
//				System.out.println("windowClosed方法被调用");
//			}
//		});
//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setSize(200, 200);
//		setLocationRelativeTo(null);
//		setVisible(true);
//	}

	public static void main(String[] args) {
//		 try {
//	            String testPathString = "C:\\Users\\Administrator\\Desktop\\";
//	            WritableWorkbook workbook = Workbook.createWorkbook(new File(testPathString + "output.xls"));
//	            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
//	            Label label = new Label(0, 2, "A label record kkkkkkkkkkkkkkkkk");
//	            WritableCellFormat wcf = new WritableCellFormat();
//	            wcf.setWrap(true);
////	            wcf.setShrinkToFit(true);
//	            label.setCellFormat(wcf);
//	            sheet.addCell(label); 
//	            workbook.write(); 
//	            workbook.close();
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        } catch (RowsExceededException e) {
//	            e.printStackTrace();
//	        } catch (WriteException e) {
//	            e.printStackTrace();
//	        }
		String aa="https://shopee.com.my/healthystyle.my";
		System.out.println(aa.substring(aa.indexOf(".my/")+4));
	}
}