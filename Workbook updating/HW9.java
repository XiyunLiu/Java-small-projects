import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author: Xiyun Liu
 * about: a GUI that allows the user to select the master sheet, vendor sheet, 
 *        and where to save the updated master sheet. 
 *
 * compile: javac –cp "/home/linux/ieng6/cs11wb/public/HW9_libs/*:." HW9.java
 * run: –cp "/home/linux/ieng6/cs11wb/public/HW9_libs/*:." HW9
 */
public class HW9 extends Application{
	public File master_sheet;
	public File vendor_sheet;
	public File update_sheet;
	public Workbook input_master;
	public Workbook input_vendor;
	private String master_path; //to do
	private String vendor_path; //to do
	private String update_path;
	public Workbook update;
	private InputStream inp;
	
  /**
   * main function
   */
	public static void main (String[] args) {
		launch(args);
		return;
	}
	
  /**
   * Start function. Create a GUI with four buttons and three textview.
   * @param applicationStage - GUI stage
   */
	public void start(Stage applicationStage) {

		GridPane pane = new GridPane();
		Scene scene = new Scene(pane);
		
		Button open_master = new Button("Open Master Sheet...");
		Button open_vendor = new Button("Open Vendor Sheet...");
		Button save_update = new Button("Save Updated Sheet...");
		Button run = new Button("Update Master Sheet...");
		TextField master_path_text = new TextField();
		TextField vendor_path_text = new TextField();
		TextField update_path_text = new TextField();
		master_path_text.setEditable(false);
		vendor_path_text.setEditable(false);
		update_path_text.setEditable(false);
		
		// Set action to button, to open the master excel
        open_master.setOnAction( e-> {
        	FileChooser fileChooser = new FileChooser();
        	fileChooser.getExtensionFilters().add(
        			new FileChooser.ExtensionFilter("xlsx", "*.xlsx")
        			);
        	master_sheet = fileChooser.showOpenDialog(applicationStage);
        	
            if (master_sheet != null) {
            	master_path = master_sheet.getAbsolutePath(); 
            	master_path_text.setText(master_path);//to do
            	try {
					input_master = WorkbookFactory.create(master_sheet);
					inp = new FileInputStream(master_path);
					update = WorkbookFactory.create(inp);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        // Set action to button, to open the vendor excel
        open_vendor.setOnAction( e-> {
        	FileChooser fileChooser = new FileChooser();
        	fileChooser.getExtensionFilters().add(
        			new FileChooser.ExtensionFilter("xlsx", "*.xlsx")
        			);
        	vendor_sheet = fileChooser.showOpenDialog(applicationStage);
            if (vendor_sheet != null) {
            	try {
					input_vendor = WorkbookFactory.create(vendor_sheet);
					Create_update check = new Create_update();
					check.find_col_num(input_vendor);
					if (check.MAP_col == -1 || check.SKU_col == -1) {
						Alert alert = new Alert(AlertType.WARNING,"The vendor sheet you chose is not relevent. Please choose the correct file.");
						alert.showAndWait();
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	vendor_path = vendor_sheet.getAbsolutePath();
            	vendor_path_text.setText(vendor_path);	
            }
            else {
            
            }
        });
        
       // Set action to button, to choose where to save the output excel
        save_update.setOnAction( e-> { 
            DirectoryChooser directChooser = new DirectoryChooser();
        	File update_dir = directChooser.showDialog(applicationStage);
            update_path = update_dir.getAbsolutePath();
            update_path_text.setText(update_path);	
        });
       
        // Set action to button, generate the output excel
        run.setOnAction( e-> {
        	
        	// check input error
        	if (master_sheet == null) {
        		Alert alert = new Alert(AlertType.ERROR, 
                        "Haven't chosen master sheet");
        		alert.showAndWait();
        	}
        	else if (vendor_sheet == null) {
        		Alert alert = new Alert(AlertType.ERROR, 
                        "Haven't chosen vendor sheet");
        		alert.showAndWait();        		
        	}
        	else if (update_path == null) {
        		Alert alert = new Alert(AlertType.ERROR, 
                        "Haven't chosen save path");
        		alert.showAndWait();        		
        	}
        	
            // run class Create_update 
        	FileOutputStream fileOut;
			try {
				fileOut = new FileOutputStream(update_path+"/update_sheet.xlsx");
				Create_update generate = new Create_update(input_master,input_vendor,update);
				System.out.println("Start run");
				generate.run();
				System.out.println("Finish run");
				update.write(fileOut);
	            fileOut.close();
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
        	
        });
        
        // Layout
	    pane.add(open_master, 0, 0);
	    pane.add(open_vendor, 0, 1);
	    pane.add(save_update, 0, 2);
	    pane.add(run, 0, 3);
	    
	    pane.add(master_path_text, 1, 0);
	    pane.add(vendor_path_text, 1, 1);
	    pane.add(update_path_text, 1, 2);
	    
	    applicationStage.setScene(scene);
		applicationStage.show();
	}
}
