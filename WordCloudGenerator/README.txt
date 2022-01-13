*************************************************************
	Project: Word Cloud Generator                       
	Author:  Máire Murphy G00375722                     
	Course:  H. Dip in Software Development             
*************************************************************


Project Description\Features
-----------------------------
The Word Cloud Genertor application generates a Word Cloud PNG image from a file or a URL source via the console. Upon startup, a separate thread is triggered to handle loading Ignore words from file into a Collection (these will be removed from the source content later). The User specifies:

1: The content source which can be a File Source (option 1 mains menu) or a URL Source (option 2 main menu).
2: The number of words to appear in the cloud output(<31) 
3: The name and location of the PNG output file. Done in two steps; the User specifies the directory location first, then the file name (User must input the .png extension).
All inputs are validated and the User has the option to cancel at any stage during input. Main menu option 3, allows the User to exit the application. Once all the inputs are set, the word cloud generation is triggered. 

The word cloud generation involves the following steps: the source content is scanned into a String. If the source is a URL - it is parsed to remove any script &  html tags. The source (file or URL) is then parsed using a more general word parser to remove non-ascii chars and leave words seperated by spaces. The String is split into in an array. The String array is loaded into a ConcurrentHashSet<String, Integer> - with the unique word as the key and the word frequency occurance as the value. Each ignore-word in the ignore word Set is removed from the ConcurrentHashSet using Threads. The remaining Collection of words are sorted by frequency value in descending order using a Lamda expression. A list is created with the most frequently occuring words with the size specifed by User's previous input.

Finally, a PNG image is created using the list of frequently occuring words in a Word Cloud format.The most frequest occuring word is the largest and centered. Word size decreases as the application moves down the list. The image is automatically displayed to the user on screen when complete. The User is informed of the status and the file output location in the Console.


Extras
-------------------------
Concurrent Programming: Thread Handling with a Thread Safe Collections
- Application launches a Thread to setup up the loading of the ignorewords.txt file (Runner.java)
- Threads to remove each ignore word from the ConcurrentHashMap. These thread priorities were increased to maximum to ensure they completed before the 		   	main thread created the image. Also the main thread was put to sleep for a few seconds to ensure completion. (WordCloud.java)
- Used a ConcurrentHashMap<String, Integer> as a thread safe collection.


Interfaces: Created an IOator Iterface to ensure that concrete classes that implement it, behave consistently with their input source: 1. validate that the source exists and that the content is input consistently( via a Scanner). 


Polymorphism: 
Implementation of the InputProcessor varies depending on the User's choice via the menu. For example, the InputProcesser validInputSource(Source) method implementation is completely different depending if the InputProcesser object is assigned FileIO type or a URLIO. 


Image automaticaly displayed:
For convenice the newly generated word cloud image is displayed to the User. 
		
Contents:
---------------------------

1. Extract G00375722.zip. Extracted zip contents:

WordCloudGenerator
├── README.txt
├── design.png
├── WordCloud.jar 
├── ignorewords.txt
└── src                   	            
    └── ie                 
        └── gmit 
	     └── dip      
   		   ├── AppController.java    
    		   ├── AppInterface.java
		   ├── ConsoleColour.java          
   		   ├── FileIO.java    
    		   ├── IgnoreWords.java 
		   ├── ImageDisplay.java
		   ├── InputProcesser.java  
   		   ├── IOator.java
   		   ├── Patterns.java 		   
		   ├── Runner.java
   		   ├── ScriptParsator.java
                   ├── Sources.java
   		   ├── URLIO.java
		   ├── WCImageMaker.java
   		   ├── WordCloud.java
		   └── WordParsator.java


To Run (In Command Prompt)
---------------------------
Option 1: To execute the JAR use> in WordCloudGenerator:
	java -cp ./wcloud.jar ie.gmit.dip.Runner

Option 2: Compile & run source. Navigate to the folder ../WordCloudGenerator/src:
	javac ie/gmit/dip/*.java
	java ie.gmit.dip.Runner			




Acknowledgements
-------------------

-Image display code in ImageDisplay.java was integrated from: alvin alexander, alvinalexander.com

-Code used to generate 2Dgraphics with no word collisions in WCImageMaker.java was integrated from: MadProgrammer:
https://stackoverflow.com/questions/34690321/java-graphics-random-text/34691463

-Lamda expression to sort collection by value (descending) in WordCloud.java was integrated from: https://www.tutorialspoint.com/how-can-we-sort-a-map-by-both-key-and-value-using-lambda-in-java
		


	
  		




