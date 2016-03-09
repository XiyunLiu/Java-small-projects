Bubble Game version 1.0 03/08/2016

GENERAL USAGE NOTES
————————————————————

- This program uses the objectdraw library to create an animation of a Bubble Clicking Game. The bubbles appear in random size, random shapeand random color. If you click on a specific bubble, it will disappear and you will get score based on some calculation on the size of the bubble; if you click on a blank area, two additional bubbles appear on the canvas.

- The game will end when there are more than 20 bublles on the screen. After the game terminates, your score will be showed on terminal.

- This program uses an external library “ogjectdraw”. 

	compile: javac -cp objectdraw.jar:. Animate.java

	run: java -cp objectdraw.jar:. Animate

==================================================================================

File Description:
————————————————————

- Animate.java : 
	main file of this program. Create a canvas with bubbles on it.

- DrawArc.java :
	helper class of this program. It draws bubbles at a random location. The bubbles 	move diagonally down and back up the screen.
	
==================================================================================

Problems:
——————————

- Need to add a timer later. The user should click the bubbles in a limited time.

- Additional bubbles should appear on the screen in period. It should appear faster and faster.

- Maybe I can add letter on the bubbles, and the user should click the bubbles in order to construct a valid word, or the user will lose points.


==================================================================================

Setup Eclipse
———————————————

1. Select File -> New -> Java Project2. In the pop up dialog, name the project HW9 and hit the Next > button3. Select the Libraries tab4. Select Add External JARs button5. Find and select the object draw.jar6. Select Ok, Apply, Ok

==================================================================================

Contact Info
—————————————

Email: liuxiyun.nku@hotmail.com
