Workbook updating version 1.0 03/07/2016

GENERAL USAGE NOTES
————————————————————

- This program is to first create a GUI that allows the user to select the master sheet, vendor sheet, and where to save the updated master sheet, then update the master sheet and save it.

- To run this program, the user should setup Eclipse or run in ieng6.ucsd.edu server

==================================================================================

File Description:
————————————————————

- HW9.java : 
	main file of this program. Create a GUI with four buttons and three textview

- Create_update.java :
	helper class of this program. Update the workbook using Apache POI.
	
==================================================================================

Setup Eclipse
———————————————

1. Select File -> New -> Java Project2. In the pop up dialog, name the project HW9 and hit the Next > button3. Select the Libraries tab4. Select Add External JARs button5. Find and select the 4 provided Apache POI JAR files6. Click to expand the JRE System Library7. Select Access Rules: No rules defined8. Hit the Edit button9. Select Add... button10. Change the Resolution to Accessible and Rule Pattern to javafx/**11. Select Ok, Apply, Ok

==================================================================================

Run on ieng6 server
————————————————————

javac –cp “/home/linux/ieng6/cs11wb/public/HW9_libs/*:.” HW9.java java –cp “/home/linux/ieng6/cs11wb/public/HW9_libs/*:.” HW9