import java.io.File;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author: Xiyun Liu
 * about: a helper class for HW9. Generate the update excel
 */
public class Create_update {
	
	private Workbook master;
	private Workbook vendor;
	public Workbook update;
	 
	private static Sheet master_sheet;
	private static Sheet vendor_sheet;
	int MAP_col = -1;
	int SKU_col = -1;
	private HashMap<String,Double> vendor_map= new HashMap<>();
	
  /**
   * Constructor
   * @param input_master - the master excel
   * @param input_vendor - the vendor excel
   * @param update - the updated excel
   */	
	public Create_update (Workbook input_master, Workbook input_vendor, Workbook update) {
		this.master = input_master;
		this.vendor = input_vendor;
		this.update = update;
		master_sheet = master.getSheetAt(0);
	}
	
  /**
   * Default constructor
   */
	public Create_update() {
		// TODO Auto-generated constructor stub
	}

  /**
   * Find the column number of "MAP" and "SKU" in the vendor excel
   * @param input_vendor - the vendor excel
   */
	public void find_col_num(Workbook input_vendor) {
		vendor_sheet = input_vendor.getSheetAt(0);
		Row first_row = vendor_sheet.getRow(0);
		int count =0;
		for (Cell cell : first_row) {
            if (cell.getRichStringCellValue().getString().equals("MAP")) {
            	MAP_col = count;
            }
            else if (cell.getRichStringCellValue().getString().equals("SKU")) {
            	SKU_col = count;
            }
            count++;
        }
	}

  /**
   * Generate the Hashmap, whose key is vendor's "SKU" and value is vendor's "MAP"
   */
	public void make_vendor_map () {
		int rowStart = 1;
	    int rowEnd = vendor_sheet.getLastRowNum();
	    for (int i = rowStart; i <= rowEnd; i++) {
	    	Row row = vendor_sheet.getRow(i);
			String sku = row.getCell(SKU_col).getRichStringCellValue().getString();
			
			Double map = row.getCell(MAP_col).getNumericCellValue();
			vendor_map.put(sku,map);
		}
		
	}
	
  /**
   * Generate the update workbook
   */
	public void make_update() throws IllegalArgumentException{
		CreationHelper createHelper = update.getCreationHelper();
		Sheet sheet1 = update.getSheetAt(0);
	    Sheet sheet2 = update.cloneSheet(0);

	    // create following row
	    int rowStart = 1;
	    int rowEnd = master_sheet.getLastRowNum();
	    sheet2.getRow(0).createCell(5).setCellValue("Detail");
	    
	    for (int i = rowStart; i <= rowEnd; i++) {
	    	//to do
	    	Row row1 = sheet1.getRow(i);
	    	Row row2 = sheet2.getRow(i);
	    	Cell cell1 = row1.getCell(2);
	    	Cell cell2 = row2.getCell(2);
	    	if (vendor_map.containsKey(row1.getCell(0).getRichStringCellValue().getString())) {
	    		
	    		Double pre = row1.getCell(2).getNumericCellValue();
	    		Double cur = vendor_map.get(row1.getCell(0).getRichStringCellValue().getString());

	    		Cell detail = row2.createCell(5);
	    		detail.setCellType(Cell.CELL_TYPE_STRING);
	    		
	    		if (pre.equals(cur)) {	    
	    			detail.setCellValue("Remain same");
	    		}
	    		else if (pre<cur) {
	    			detail.setCellValue("Increase");
	    		}
	    		else {
	    			detail.setCellValue("Decrease");
	    		}
	    		
	    		cell1.setCellValue(cur);	    		
	    		cell2.setCellValue(cur);
	    		
	    	}
	    	else {
	    		Cell detail = row2.createCell(5);
	    		detail.setCellValue("Not found");
	    	}
	    }
	    
	}
	
  /**
   * Call all the necessary functions 
   */
	public void run() {
		find_col_num(this.vendor);
		if (MAP_col == -1 || SKU_col == -1) {
			Alert alert = new Alert(AlertType.WARNING,"The vendor sheet you chose is not relevent. Please choose the correct file.");
			alert.showAndWait();
		}
		else {
			make_vendor_map();
			try {
				make_update();
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			}
		}
			
	}
		
}
