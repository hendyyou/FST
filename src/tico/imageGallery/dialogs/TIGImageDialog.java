/*
 * File: TIGImageDialog.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Mar 4, 2008
 * 
 * Company: Universidad de Zaragoza, CPS, DIIS
 * 
 * License:
 * 		This program is free software; you can redistribute it and/or
 * 		modify it under the terms of the GNU General Public License
 * 		as published by the Free Software Foundation; either version 2
 * 		of the License, or (at your option) any later version.
 * 
 * 		This program is distributed in the hope that it will be useful,
 * 		but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 		GNU General Public License for more details.
 * 
 * 		You should have received a copy of the GNU General Public License
 * 		along with this program; if not, write to the Free Software Foundation,
 * 		Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package tico.imageGallery.dialogs;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.MediaTracker;
import java.io.File;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import tico.components.resources.TFileUtils;
import tico.configuration.TLanguage;

/*
 * This class displays the panel that contains the thumbnail of an image
 */
public class TIGImageDialog extends JLabel{
	
	// Component size constants
	private final static int PREVIEW_WIDTH = 125;
	private final static int PREVIEW_HEIGHT = 125;
	
	// Component margin
	private final static int PREVIEW_MARGIN = 15;
	
	private ImageIcon image;
	
	private ImageIcon imageResized;
	
	public TIGImageDialog(){
		
	}
	
	protected JLabel createImageLabel(String image_path){ 
		
		File image = new File("Image" + File.separator + image_path.substring(0,1).toUpperCase() + File.separator + image_path);
			
		setImageFile(image);
							
		return this;
	}
	
	private void setImageFile(File imageFile) {
		// Temporal image
		image = null;
		setText("");

		if (imageFile != null) {
			// Test if need to be loaded with JAI libraries (different
			// format than JPG, PNG and GIF)
						
			if (TFileUtils.isJAIRequired(imageFile)) {
				// Load it with JAI class
				RenderedOp src = JAI.create("fileload", imageFile
						.getAbsolutePath());
				BufferedImage bufferedImage = src.getAsBufferedImage();
				image = new ImageIcon(bufferedImage);
				
			} else {
				// Create it as usual
				image = new ImageIcon(imageFile.getAbsolutePath());
			}

			//this.setSize(image.getIconWidth(),image.getIconHeight());
			
			if (image.getImageLoadStatus() == MediaTracker.ERRORED){
				setText(TLanguage.getString("TIGImageDialog.TEXT"));
				this.setIcon(null);
			}
			else{				
				// Resize image is needed
				int maxPreviewHeight = PREVIEW_HEIGHT - PREVIEW_MARGIN;
				if (image.getIconHeight() > maxPreviewHeight)
					imageResized = new ImageIcon(image.getImage().getScaledInstance(-1,
							maxPreviewHeight, Image.SCALE_DEFAULT));
			
				int maxPreviewWidth = PREVIEW_WIDTH - PREVIEW_MARGIN;
				if (image.getIconWidth() > maxPreviewWidth)
					imageResized = new ImageIcon(image.getImage().getScaledInstance(
							maxPreviewWidth, -1, Image.SCALE_DEFAULT));
			
				this.setIcon(imageResized);
			}
		}		
	}
	
	/**
	 * Returns the selected <code>icon</code>.
	 * 
	 * @return The selected <code>icon</code>
	 */
	public ImageIcon returnIcon() {
		return image;
	}
	
	/**
	 * Returns the selected <code>icon</code>.
	 * 
	 * @return The selected <code>icon</code>
	 */
	public ImageIcon returnIconResized() {
		return image;
	}
}
