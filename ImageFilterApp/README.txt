*************************************************************
*	Project: Image Filtering System                     *
*	Author:  Máire Murphy G00375722                     *
*	Course:  H. Dip in Software Development             *
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


How to Install and Execute:
---------------------------

1. Extract G00375722.zip. Extracted zip contents:

ImageProcessor
├── README.txt
└── src                   
    ├── manager.jar 	         
    ├── conf  			# sample comma delimited files with convolution kernel data
    |	 ├── gaussian.txt  
    |	 ├── outline.csv	    
    |	 └── motion.csv	 
    |	
    ├── images    		# sample pngs and jpg images   
    |	 ├── gmit.png  
    |	 ├── bridge.png	 
    |	 ├── flower.jpg	   
    |	 └── head.jpg    
    └── ie                 
        └── gmit 
	     └── dip 
		   ├── Runner.java       
   		   ├── Menu.java    
    		   ├── FileIO.java
		   ├── Kernel.java          
   		   ├── KernelDataProcessor.java    
    		   ├── ImageProcessor.java 
		   ├── MessageDisplay.java
		   ├── ImageDisplay.java  
   		   ├── ConsoleColour.java
   		   ├── ConsoleDecorColour.java
		   ├── Runner.class       
   		   ├── Menu.class    
    		   ├── FileIO.class
		   ├── Kernel.class          
   		   ├── KernelDataProcessor.class    
    		   ├── ImageProcessor.class 
		   ├── MessageDisplay.class
		   ├── MessageDisplay$1.class
		   ├── ImageDisplay.class  
   		   ├── ConsoleColour.class
   		   ├── ConsoleDecorColour.class		    
    		   └── ConsoleDecorColour.class	 



2. Open CMD prompt and navigate to the folder ../ImageProcessor/src that contains the 'ie' folder. 

	* Option 1. To run the compiled code use> java ie.gmit.dip.Runner			

		

	* Option 2. To execute the jar use> java -cp ./manager.jar ie.gmit.dip.Runner
	
  		




