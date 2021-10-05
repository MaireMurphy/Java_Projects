package ie.gmit.dip;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


import java.awt.*;


/**
 * A Java class to demonstrate how to load an image from disk with the
 * ImageIO class. Also shows how to display the image by creating an
 * ImageIcon, placing that icon an a JLabel, and placing that label on
 * a JFrame.
 * 
 * @author alvin alexander, alvinalexander.com
 */

public class ImageDisplay {
	private MessageDisplay md = new MessageDisplay();
	

	public ImageDisplay() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Image scaleimage(int wid, int hei, BufferedImage img){
	    Image im = img;
	    double scale;
	    double imw = img.getWidth();
	    double imh = img.getHeight();
	    if (wid > imw && hei > imh){
	        im = img;
	    }else if(wid/imw < hei/imh){
	        scale = wid/imw;
	        im = img.getScaledInstance((int) (scale*imw), (int) (scale*imh), Image.SCALE_SMOOTH);
	    }else if (wid/imw > hei/imh){
	        scale = hei/imh;
	        im = img.getScaledInstance((int) (scale*imw), (int) (scale*imh), Image.SCALE_SMOOTH);
	    }else if (wid/imw == hei/imh){
	        scale = wid/imw;
	        im = img.getScaledInstance((int) (scale*imw), (int) (scale*imh), Image.SCALE_SMOOTH);
	    } 
	    return im;
	}

	private BufferedImage imageToBufferedImage(Image im) {
	     BufferedImage bi = new BufferedImage
	        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
	     Graphics bg = bi.getGraphics();
	     bg.drawImage(im, 0, 0, null);
	     bg.dispose();
	     return bi;
	  }

	public ImageDisplay(String filename) throws Exception {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame editorFrame = new JFrame(filename);
					editorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

					BufferedImage image = null;
			
						image = ImageIO.read(new File(filename));
						Image img = scaleimage(600, 800, image);
						image = imageToBufferedImage(img);

				
					ImageIcon imageIcon = new ImageIcon(image);
					JLabel jLabel = new JLabel();
					jLabel.setIcon(imageIcon);
					
					editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

					editorFrame.pack();
					editorFrame.setLocationRelativeTo(null);
					editorFrame.setVisible(true);

				} catch (Exception e) {
					md.showMsg("error", "Problem occured. Unable to display the filtered image.");
				}
			}

		});

	}

}
