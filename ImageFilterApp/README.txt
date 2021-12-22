*************************************************************
*	Project: Image Filtering System                     *
*	Author:  Máire                                      *
*	                                                    *
*************************************************************

Project Description
-------------------
Image filtering application that uses a 2D array called a convolution kernel to manipulate the image pixels to produce a filtered effect. The resulting filtered image is saved as Out.png and it's location is displayed on screen. The filtered image is also displayed to the user in a seperate pop up window. To proceed click the cmd prompt window to make it the active again and choose the next option. (Out.png is overwritten for each filter operation)

Features
---------

* Main menu option 1: The user can specify an image (name and path). Relative and absolute paths can be used. The image is validated to make sure it exists and that it is a valid image. The application remembers this image and can be filtered many times until the user decides to change the image. The currently selected image is shown above menu. The user can enter 'c' to cancel and is returned to the main menu.

* Main menu option 2 & 3: Filter images using predefined filters. The user can select a filter that detects lines (i.e.laplace) or uses a special effect (i.e. emboss). (These convolution kernels (double[][] arrays) are predefined as constants in an Enum). The user will be prompted to enter an image if not specified earlier. The image is processed with the chosen filter option and the filtered output displayed. The filtered image is saved as Out.png. The current submenu is repeated until the user selects 0 to return to the main menu.

* Main menu option 4: Allows user to select a file that contains a convolution kernel filter that is delimited with commas.  The user can enter 'c' to cancel and is returned to the main menu. The chosen file is validated and converted into a double[][] array. (The user will be prompted to enter an image if not specified earlier.) The convolution kernel is diplayed on screen. The selected image is then filtered using this 'Custom' kernel. The filtered image is saved as Out.png and displayed in a seperate window.

* Main menu option 5: Allows user to input a convolution kernel directly via the console. The user is asked for the size of the kernel, which is validated. If valid, the user then inputs each kernel value. The user can enter '99' to cancel and is returned to the main menu. (The user will be prompted to enter an image if not specified earlier.) The selected image is then filtered using the 'Custom' kernel. The filtered image is saved as Out.png and displayed in a seperate window

*Main menu option 6: Quit the application. Kills any on-going processes such as open images and exits the application.




 To execute
 -----------
1. Download manager.jar
2. Navigate to the folder that contains manager.jar using CMD.
3. In the command prompt use: java -cp ./manager.jar ie.gmit.dip.Runner
	
  		




