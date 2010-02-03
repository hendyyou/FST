/*
 * File: TIGThumbnailsDialog.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Feb 20, 2008
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

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.util.Vector;

import tico.components.TButton;
import tico.components.resources.TFileUtils;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;

/*
 * This class displays the panel that contains the four images that are part of the
 * result of a search and has buttons so that its possible to see all the images selected.
 * It also displays information about the images.
 */ 
public class TIGThumbnailsDialog extends JPanel implements KeyListener, MouseListener {
	
	//1 <= index <= 4
	//Index point to the image selected between the four displayed
	protected int index = 0;
	
	//This index points to the first image that is being displayed.
	//E.g., if the images 11 to 14 are being displayed, groupIndex will be 11.
	protected int groupIndex = 0;
	
	//Number of images displayed on the panel
	private final int IMAGES_DISPLAYED = 4;
	
	//Number of images in the result vector
	protected int total;
	
	protected int image_index_display_selected = 1;
	
	protected String nImagesString;
	
	protected ImageIcon imageSelected;
	
	private TButton leftButton;
	
	private TButton rightButton;
	
	// Indicates if the selection of images is enabled
	private Boolean enabledSelection;
	//Contains the path of the image selected
	private String path;
	
	private Vector result;
	
	//Those Strings contain the path of the four displayed images
	//private String pathImage;
	private String pathImage[] = new String [IMAGES_DISPLAYED];
	private String path1;
	private String path2;
	private String path3;
	private String path4;
	
	//Those Strings contain the name of the four displayed images
	private String imageName;
	private String imageName1;
	private String imageName2;
	private String imageName3;
	private String imageName4;
		
	JLabel nImagesTextField;
	
	JLabel thumbnail = new JLabel();
	JLabel thumbnails1 = new JLabel();
	JLabel thumbnails2 = new JLabel();
	JLabel thumbnails3 = new JLabel();
	JLabel thumbnails4 = new JLabel();
	
	TIGThumbImage thum = new TIGThumbImage();
	TIGThumbImage thum1 = new TIGThumbImage();
	TIGThumbImage thum2 = new TIGThumbImage();
	TIGThumbImage thum3 = new TIGThumbImage();
	TIGThumbImage thum4 = new TIGThumbImage();
	
	
	TIGThumbImage[] thumbImages = new TIGThumbImage[IMAGES_DISPLAYED];
	//Vector de thumbnails
	JLabel[] thumbnails = new JLabel[IMAGES_DISPLAYED];
	//Vector de booleanos
	Boolean[] containsImage = new Boolean[IMAGES_DISPLAYED];
	
	GridBagConstraints gridBag;
	
	boolean select;
		
	public TIGThumbnailsDialog(boolean select){
		
		this. select = select;
		
		this.addMouseListener(this);
		this.addKeyListener(this); 
		
		this.addKeyListener(new java.awt.event.KeyAdapter(){
			
		});	
		
	}
	
	/*
	 * Creates the thumbnail panel and all its components
	 */
	protected JPanel createThumbnailsPanel(Vector result, Boolean enabled){
		this.enabledSelection = enabled;
		this.result = result;
		total = result.size();
		String thumbName;
		String pathSrc;
		String pathTh;
		
		gridBag = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		//Create the arrows to navigate on the list of images
		//Left Button
		leftButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		leftButton.setIcon(TResourceManager.getImageIcon("move-left-16.png"));
		leftButton.setMargin(new Insets(2, 2, 2, 2));
		leftButton.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.PREVIOUS"));
		leftButton.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				index=(index-IMAGES_DISPLAYED)%total;
				if (index<0){
					index = index + total;
				}
				updateThumbnailsPanel(index);
			}
		});
		//Right Button
		rightButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		rightButton.setIcon(TResourceManager.getImageIcon("move-right-16.png"));
		rightButton.setMargin(new Insets(2, 2, 2, 2));
		rightButton.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.NEXT"));
		rightButton.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				index = (index+IMAGES_DISPLAYED)%total;
				updateThumbnailsPanel(index);
			}
		});
		//Create the text that will show how many images are, and what name are being displayed
		if (result.size()==1){
			nImagesString = TLanguage.getString("TIGThumbnailsDialog.ONE_IMAGE_FOUND");
		}else{
			nImagesString = result.size() + " " + TLanguage.getString("TIGThumbnailsDialog.IMAGES_FOUND");
		}
		nImagesTextField = new JLabel(nImagesString);
		
		//Configure the background of the panel
		this.setOpaque(true);
		this.setBackground(Color.WHITE);
		
		this.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151))));
		
		//Create every panel that will contain an image
		
	/*	if (result.size()==0){
			//Aqui nunca debería entrar, ya que si no hay imágenes sacará un error
			for (int i=0; i<IMAGES_DISPLAYED; i++){
				thumbnails[i] = thumbImages[i].createImageLabel(null);
				thumbnails[i].setPreferredSize(new Dimension(125,125));
				thumbnails[i].setText("");
				thumbnails[i].setToolTipText(null);
				thumbnails[i].setPreferredSize(new Dimension(130,130));
				thumbImages[i].updateBorder(Color.WHITE);
				
				containsImage[i] = false;
				
				gridBag.fill = GridBagConstraints.BOTH;
				gridBag.insets = new Insets(10, 10, 10, 10);
				gridBag.gridx = i;
				gridBag.gridy = 0;
				this.add(thumbnail, gridBag);
			}
			leftButton.setEnabled(false);
			rightButton.setEnabled(false);
		}else{*/
			if (result.size()<=IMAGES_DISPLAYED){
				leftButton.setEnabled(false);
				rightButton.setEnabled(false);
			}
			for (int i=0; i<IMAGES_DISPLAYED; i++){
				thumbImages[i]= new TIGThumbImage();
			}
			for (int i=0; i<IMAGES_DISPLAYED; i++){
				if (i<result.size()){
					Vector data = new Vector(2);
					data = (Vector)result.elementAt(i%IMAGES_DISPLAYED);
				
					pathImage[i] = (String)data.elementAt(0);
					thumbName = pathImage[i].substring(0, pathImage[i].lastIndexOf('.')) + "_th.jpg";
					
					pathSrc = System.getProperty("user.dir") + File.separator + "images" + File.separator +
	        	 		pathImage[i].substring(0,1).toUpperCase() +	File.separator + pathImage[i];
		        	 
		        	pathTh = System.getProperty("user.dir") + File.separator + "images" + File.separator +
		        	 	pathImage[i].substring(0,1).toUpperCase() +	File.separator + thumbName;
		        	
		        	createThumbnail(pathSrc, pathTh);
					
					thumbnails[i] = thumbImages[i].createImageLabel(thumbName);
					imageName = (String)data.elementAt(1);
					thumbnails[i].setToolTipText(imageName);
					thumbnails[i].setPreferredSize(new Dimension(130,130));

					if (enabledSelection){
						thumbnails[i].addMouseListener(new selectedImageListener(i));
					}
					
					containsImage[i] = true;

				}else{ //No result
					thumbnails[i] = thumbImages[i].createImageLabel(null);
					thumbnails[i].setPreferredSize(new Dimension(125,125));
					thumbnails[i].setText("");
					thumbnails[i].setToolTipText(null);
					thumbnails[i].setPreferredSize(new Dimension(130,130));
					thumbImages[i].updateBorder(Color.WHITE);
					
					containsImage[i] = false;
				}
				
			}
			gridBag.fill = GridBagConstraints.BOTH;
			gridBag.insets = new Insets(10, 10, 10, 10);
			gridBag.gridy = 0;
			for (int i=0; i<IMAGES_DISPLAYED; i++){
				gridBag.gridx = i;
				System.out.println(thumbnails[i].getText());
				this.add(thumbnails[i], gridBag);
			}		
		//}
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.insets = new Insets(10, 30, 10, 30);
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		this.add(leftButton, gridBag);
		
		gridBag.fill = GridBagConstraints.CENTER;
		gridBag.insets = new Insets(10, 30, 10, 30);
		gridBag.gridwidth = 2;
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		this.add(nImagesTextField, gridBag);
		
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.insets = new Insets(10, 30, 10, 30);
		gridBag.gridx = 3;
		gridBag.gridy = 1;
		this.add(rightButton, gridBag);
		
		
		/*
		this.result = result;
		total = result.size();
		String thumbName;
						
		thum = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		//Create the arrows to navigate on the list of images
		leftButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		rigthButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		leftButton.setIcon(TResourceManager
				.getImageIcon("move-left-16.png"));
		leftButton.setMargin(new Insets(2, 2, 2, 2));
		leftButton.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.PREVIOUS"));
		leftButton.addMouseListener(new java.awt.event.MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e){
				int i = groupIndex - 4;
				if (total > 4){
					if (i < 0){
						if ( i > -4)
							i = 0;
						else{
							if ((total - 4) > 0)
								i = total - 4;					
							else
								i = 0;
						}
					}
					updateThumbnailsPanel(i);
				}
			}
		});
		
		rigthButton.setIcon(TResourceManager
				.getImageIcon("move-right-16.png"));
		rigthButton.setMargin(new Insets(2, 2, 2, 2));
		rigthButton.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.NEXT"));
		rigthButton.addMouseListener(new java.awt.event.MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e){
				int i = groupIndex + 4;
				if (total > 4){
					if ((i + 4) > total)
						if (i < total) 
							i = total - 4;
						else 
							i = 0;
					updateThumbnailsPanel(i);
				}
			}
		});
		
		//Create the test that will show how many images are and what name are being displayed
		nImagesString = TLanguage.getString("TIGThumbnailsDialog.IMAGE") + " " + index + " " +
						TLanguage.getString("TIGThumbnailsDialog.OF") + " " + result.size();
		nImagesTextField = new JLabel(nImagesString);
		
		//Configure the background of the panel
		Color blanco = new Color(255,255,255);
		this.setOpaque(true);
		this.setBackground(blanco);
		
		this.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151))));
		
		//Create every panel that will contain an image
		if (result.size() > 0){
			Vector data1 = new Vector(2);
			data1 = (Vector)result.elementAt((index%4)-1);
		
			path1 = (String)data1.elementAt(0);
			thumbName = path1.substring(0, path1.lastIndexOf('.')) + "_th.jpg";

			thumbnails1 = thum1.createImageLabel(thumbName);
			//thumbnails1.setSize(new Dimension(100,100));		
			imageName1 = (String)data1.elementAt(1);
			thumbnails1.setToolTipText(imageName1);
			thumbnails1.setSize(125,125);
			thumbnails1.setBorder(new MatteBorder(
				new Insets(2, 2, 2, 2), Color.WHITE));
			thumbnails1.addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e){
					selectThumbnail(1);
				}
			});
			if ((index == 1) && 
				(thum1.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) != 0)){
				thumbnails1.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
				imageSelected = thum1.returnIcon();
				path = path1;
				index = 1;
			}
		}else{
			thumbnails1 = thum1.createImageLabel(null);
			thumbnails1.setPreferredSize(new Dimension(125,125));
			thumbnails1.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails1.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails1.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails1.setToolTipText(null);
		}
		
		thum.fill = GridBagConstraints.BOTH;
		thum.insets = new Insets(10, 10, 10, 10);
		thum.gridx = 0;
		thum.gridy = 0;
		this.add(thumbnails1,thum);
				
		if (result.size() >= 2){
			Vector data2 = new Vector(2);
			data2 = (Vector) result.elementAt(index%4);
		
			path2 = (String)data2.elementAt(0);
			thumbName = path2.substring(0, path2.lastIndexOf('.')) + "_th.jpg";

			thumbnails2 = thum2.createImageLabel(thumbName);
			//thumbnails2.setSize(new Dimension(100,100));
			imageName2 = (String)data2.elementAt(1);
			thumbnails2.setToolTipText(imageName2);
			thumbnails2.setSize(125,125);
			thumbnails2.setBorder(new MatteBorder(
				new Insets(2, 2, 2, 2), Color.WHITE));
			thumbnails2.addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e){
					selectThumbnail(2);
				}
			});	
		
			if ((index == 2) && 
				(thum2.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) != 0)){
				thumbnails2.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
				imageSelected = thum2.returnIcon();
				path = path2;
				index = 2;
			}			
		}else{
			thumbnails2 = thum2.createImageLabel(null);
			thumbnails2.setPreferredSize(new Dimension(125,125));
			thumbnails2.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails2.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails2.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails2.setToolTipText(null);
		}
		thum.fill = GridBagConstraints.BOTH;
		thum.insets = new Insets(10, 10, 10, 10);
		thum.gridx = 1;
		thum.gridy = 0;
		this.add(thumbnails2, thum);
		
		if (result.size() >= 3){
			Vector data3 = new Vector(2);
			data3 = (Vector) result.elementAt((index%4)+1);
		
			path3 = (String)data3.elementAt(0);
			thumbName = path3.substring(0, path3.lastIndexOf('.')) + "_th.jpg";

			thumbnails3 = thum3.createImageLabel(thumbName);
			//thumbnails3.setSize(new Dimension(100,100));
			imageName3 = (String)data3.elementAt(1);
			thumbnails3.setToolTipText(imageName3);
			thumbnails3.setSize(125,125);
			thumbnails3.setBorder(new MatteBorder(
				new Insets(2, 2, 2, 2), Color.WHITE));
			thumbnails3.addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e){
					selectThumbnail(3);
				}
			});
		
			if ((index == 3) && 
				(thum3.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) != 0)){
				thumbnails3.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
				imageSelected = thum3.returnIcon();
				path = path3;
				index = 3;
			}
			
		}else{
			thumbnails3 = thum3.createImageLabel(null);
			thumbnails3.setPreferredSize(new Dimension(125,125));
			thumbnails3.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails3.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails3.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails3.setToolTipText(null);
		}
		thum.fill = GridBagConstraints.BOTH;
		thum.insets = new Insets(10, 10, 10, 10);
		thum.gridx = 2;
		thum.gridy = 0;
		this.add(thumbnails3, thum);
		
		if (result.size() >= 4){
			Vector data4 = new Vector(2);
			data4 = (Vector) result.elementAt((index%4)+2);
		
			path4 = (String)data4.elementAt(0);
			thumbName = path4.substring(0, path4.lastIndexOf('.')) + "_th.jpg";

			thumbnails4 = thum4.createImageLabel(thumbName);
			//thumbnails4.setSize(new Dimension(100,100));
			imageName4 = (String)data4.elementAt(1);
			thumbnails4.setToolTipText(imageName4);
			thumbnails4.setSize(125,125);
			thumbnails4.setBorder(new MatteBorder(
				new Insets(2, 2, 2, 2), Color.WHITE));
			thumbnails4.addMouseListener(new java.awt.event.MouseAdapter(){
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e){
					selectThumbnail(4);
				}
			});
		
			if ((index == 4) && 
				(thum4.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) != 0)){
				thumbnails4.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
				imageSelected = thum4.returnIcon();
				path = path4;
				index = 4;
			}
			
		}else{
			thumbnails4 = thum4.createImageLabel(null);
			thumbnails4.setPreferredSize(new Dimension(125,125));
			thumbnails4.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails4.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails4.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails4.setToolTipText(null);
		}
		
		//Place all the components
		thum.fill = GridBagConstraints.BOTH;
		thum.insets = new Insets(10, 10, 10, 10);
		thum.gridx = 3;
		thum.gridy = 0;
		this.add(thumbnails4, thum);
		
		thum.fill = GridBagConstraints.BOTH;
		thum.insets = new Insets(10, 30, 10, 30);
		thum.gridx = 0;
		thum.gridy = 2;
		this.add(leftButton, thum);
		
		thum.fill = GridBagConstraints.BOTH;
		thum.insets = new Insets(10, 30, 10, 30);
		thum.gridx = 3;
		thum.gridy = 2;
		this.add(rigthButton, thum);
		
		thum.fill = GridBagConstraints.CENTER;
		thum.insets = new Insets(10, 30, 10, 30);
		thum.gridwidth = 2;
		thum.gridx = 1;
		thum.gridy = 2;
		this.add(nImagesTextField, thum);
		*/
		return this;
	}
	
	/*
	 * Update the thumbnails panel after modifying an image in order to delete the previous selection
	 */
	protected JPanel updateThumbnailsPanel(){
		path = null;
		return updateThumbnailsPanel(result,index);
	}
	
	protected JPanel updateThumbnailsPanel(int first){
		return updateThumbnailsPanel(result,first);
	}

	/*
	 * Update the thumbnails panel because of three reasons. First, because the left 
	 * or right arrows have been clicked. Second, a search has been made and third, 
	 * an image has been deleted.
	 */
	/*protected JPanel updateThumbnailsPanel(Vector result, String name){
		this.result = result;
		int i = getPosition(name);
		index = 1;
		return updateThumbnailsPanel(result, i);
	}*/
	
	/*
	 * Update the thumbnails panel because of three reasons. First, because the left 
	 * or right arrows have been clicked. Second, a search has been made and third, 
	 * an image has been deleted.
	 */
	protected JPanel updateThumbnailsPanel(Vector result, int first){
		this.result = result;
		total = result.size();
		String thumbName;
		String pathSrc;
		String pathTh;
		
		System.out.println("FIRST: "+first);
		//Update every panel that will contain an image
		
		if (result.size()==0){ //No search results
			for (int i=0; i<IMAGES_DISPLAYED; i++){
				thumbnails[i] = thumbImages[i].createImageLabel(null);
				thumbnails[i].setPreferredSize(new Dimension(125,125));
				thumbnails[i].setText("");
				thumbnails[i].setToolTipText(null);
				thumbnails[i].setPreferredSize(new Dimension(130,130));
				thumbImages[i].updateBorder(Color.WHITE);
				
				containsImage[i] = false;
			}
			leftButton.setEnabled(false);
			rightButton.setEnabled(false);
			
			nImagesString = result.size() + " " + TLanguage.getString("TIGThumbnailsDialog.IMAGES_FOUND");
			nImagesTextField.setText(nImagesString);
			
		}else{ //Results > 0
			if (result.size()==1){
				nImagesString = TLanguage.getString("TIGThumbnailsDialog.ONE_IMAGE_FOUND");
			}else{
				nImagesString = result.size() + " " + TLanguage.getString("TIGThumbnailsDialog.IMAGES_FOUND");
			}
			nImagesTextField.setText(nImagesString);
			
			if (result.size()<=IMAGES_DISPLAYED){ //Results <= IMAGES_DISPLAYED
				for (int i=0; i<IMAGES_DISPLAYED; i++){
					if (i<result.size()){
						Vector data = new Vector(2);
						data = (Vector)result.elementAt(i%IMAGES_DISPLAYED);
					
						pathImage[i] = (String)data.elementAt(0);
						thumbName = pathImage[i].substring(0, pathImage[i].lastIndexOf('.')) + "_th.jpg";
						
						pathSrc = System.getProperty("user.dir") + File.separator + "images" + File.separator +
	        	 		pathImage[i].substring(0,1).toUpperCase() +	File.separator + pathImage[i];
		        	 
						pathTh = System.getProperty("user.dir") + File.separator + "images" + File.separator +
		        	 	pathImage[i].substring(0,1).toUpperCase() +	File.separator + thumbName;
		        	
			        	 createThumbnail(pathSrc, pathTh);
						
						thumbnails[i] = thumbImages[i].createImageLabel(thumbName);
						imageName = (String)data.elementAt(1);
						thumbnails[i].setToolTipText(imageName);
						thumbnails[i].setPreferredSize(new Dimension(130,130));
						thumbImages[i].updateBorder(Color.GREEN);
						
						if (enabledSelection){
							thumbnails[i].addMouseListener(new selectedImageListener(i));
						}						

						containsImage[i] = true;

					}else{ //No result
						thumbnails[i] = thumbImages[i].createImageLabel(null);
						thumbnails[i].setPreferredSize(new Dimension(125,125));
						thumbnails[i].setText("");
						thumbnails[i].setToolTipText(null);
						thumbnails[i].setPreferredSize(new Dimension(130,130));
						thumbImages[i].updateBorder(Color.WHITE);
						
						containsImage[i] = false;
					}
				}
				leftButton.setEnabled(false);
				rightButton.setEnabled(false);
				
			}else{  //Results > IMAGES_DISPLAYED
				int i;
				int indexThumb = 0;
				int restartIndex = 0;
				for (i=first; i<(IMAGES_DISPLAYED+first); i++){
					if (i<result.size()){
						Vector data = new Vector(2);
						data = (Vector)result.elementAt(i);
					
						pathImage[indexThumb] = (String)data.elementAt(0);
						thumbName = pathImage[indexThumb].substring(0, pathImage[indexThumb].lastIndexOf('.')) + "_th.jpg";
						
						pathSrc = System.getProperty("user.dir") + File.separator + "images" + File.separator +
	        	 		pathImage[indexThumb].substring(0,1).toUpperCase() +	File.separator + pathImage[indexThumb];
		        	 
						pathTh = System.getProperty("user.dir") + File.separator + "images" + File.separator +
		        	 	pathImage[indexThumb].substring(0,1).toUpperCase() +	File.separator + thumbName;
		        			        	
			        	createThumbnail(pathSrc, pathTh);
						
						thumbnails[indexThumb] = thumbImages[indexThumb].createImageLabel(thumbName);
						imageName = (String)data.elementAt(1);
						thumbnails[indexThumb].setToolTipText(imageName);
						thumbnails[indexThumb].setPreferredSize(new Dimension(130,130));
						thumbImages[indexThumb].updateBorder(Color.GREEN);
						
						if (enabledSelection){
							thumbnails[indexThumb].addMouseListener(new selectedImageListener(indexThumb));
						}						

						containsImage[indexThumb] = true;
						
					}else{
						Vector data = new Vector(2);
						data = (Vector)result.elementAt(restartIndex);
						
						pathImage[indexThumb] = (String)data.elementAt(0);
						thumbName = pathImage[indexThumb].substring(0, pathImage[indexThumb].lastIndexOf('.')) + "_th.jpg";
						
						pathSrc = System.getProperty("user.dir") + File.separator + "images" + File.separator +
	        	 		pathImage[indexThumb].substring(0,1).toUpperCase() +	File.separator + pathImage[indexThumb];
		        	 
						pathTh = System.getProperty("user.dir") + File.separator + "images" + File.separator +
		        	 	pathImage[indexThumb].substring(0,1).toUpperCase() +	File.separator + thumbName;
		        	
						createThumbnail(pathSrc,pathTh);
						
						thumbnails[indexThumb] = thumbImages[indexThumb].createImageLabel(thumbName);
						imageName = (String)data.elementAt(1);
						thumbnails[indexThumb].setToolTipText(imageName);
						thumbnails[indexThumb].setPreferredSize(new Dimension(130,130));
						thumbImages[indexThumb].updateBorder(Color.GREEN);
						
						if (enabledSelection){
							thumbnails[indexThumb].addMouseListener(new selectedImageListener(indexThumb));
						}						
						
						containsImage[indexThumb] = true;
						restartIndex++;
					}
					indexThumb++;
				}
				leftButton.setEnabled(true);
				rightButton.setEnabled(true);
			}
			
		}

		/*nImagesString = TLanguage.getString("TIGThumbnailsDialog.IMAGE") + " " + (first) + " " +
			TLanguage.getString("TIGThumbnailsDialog.OF") + " " + result.size();		
		nImagesTextField.setText(nImagesString);
		
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.insets = new Insets(10, 30, 10, 30);
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		this.add(leftButton, gridBag);
		
		gridBag.fill = GridBagConstraints.CENTER;
		gridBag.insets = new Insets(10, 30, 10, 30);
		gridBag.gridwidth = 2;
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		this.add(nImagesTextField, gridBag);
		
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.insets = new Insets(10, 30, 10, 30);
		gridBag.gridx = 3;
		gridBag.gridy = 1;
		this.add(rigthButton, gridBag);*/
		/*
		String thumbName;
		this.result = result;
		total = result.size();
		groupIndex = first;
		
		if (result.size()==0){
			System.out.println("updateThumbnailsPanel");
			System.out.println("FIRST: "+first);
			// There are no results for that search
			//JOptionPane.showConfirmDialog(null,
			//		TLanguage.getString("TIGThumbnailsDialog.NO_RESULTS"),
			//		TLanguage.getString("TIGThumbnailsDialog.NAME_RESULT"),
			//		JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
			index=0;
			thumbnails1 = thum1.createImageLabel(null);
			thumbnails1.setPreferredSize(new Dimension(125,125));
			thumbnails1.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails1.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails1.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails1.setToolTipText(null);
			
		}else{
			//Update every panel that will display the new images
			Vector data1 = new Vector(2);
			data1 = (Vector)result.elementAt(groupIndex);
		
			path1 = (String)data1.elementAt(0);
			thumbName = path1.substring(0, path1.lastIndexOf('.')) + "_th.jpg";

			thumbnails1 = thum1.createImageLabel(thumbName);
			//thumbnails1.setSize(new Dimension(100,100));		
			imageName1 = (String)data1.elementAt(1);
			thumbnails1.setToolTipText(imageName1);
			thumbnails1.setSize(125,125);
			thumbnails1.setBorder(new MatteBorder(
				new Insets(2, 2, 2, 2), Color.WHITE));
		}
		
		if (index == 1){
			thumbnails1.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
			imageSelected = thum1.returnIcon();
			path = path1;
			index = 1;
		}

		thumbnails1.repaint();
				
		Vector data2 = new Vector(2);
		if (result.size() > (groupIndex + 1)){			
			data2 = (Vector) result.elementAt(groupIndex + 1);
			path2 = (String)data2.elementAt(0);
			thumbName = path2.substring(0, path2.lastIndexOf('.')) + "_th.jpg";

			thumbnails2 = thum2.createImageLabel(thumbName);
			//thumbnails2.setSize(new Dimension(100,100));
			imageName2 = (String)data2.elementAt(1);
			thumbnails2.setToolTipText(imageName2);
			thumbnails2.setSize(125,125);
			thumbnails2.setBorder(new MatteBorder(
					new Insets(2, 2, 2, 2), Color.WHITE));
		
			if (index == 2){
				thumbnails2.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
				imageSelected = thum2.returnIcon();
				path = path2;
				index = 2;
			}			
		}else{
			thumbnails2 = thum2.createImageLabel(null);
			thumbnails2.setPreferredSize(new Dimension(125,125));
			thumbnails2.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails2.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails2.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails2.setToolTipText(null);
		}
		
		thumbnails2.repaint();
		
		Vector data3 = new Vector(2);
		if (result.size() > (groupIndex + 2)){	
			data3 = (Vector) result.elementAt(groupIndex + 2);		
			path3 = (String)data3.elementAt(0);
			thumbName = path3.substring(0, path3.lastIndexOf('.')) + "_th.jpg";

			thumbnails3 = thum3.createImageLabel(thumbName);
			//thumbnails3.setSize(new Dimension(100,100));
			imageName3 = (String)data3.elementAt(1);
			thumbnails3.setToolTipText(imageName3);
			thumbnails3.setSize(125,125);
			thumbnails3.setBorder(new MatteBorder(
					new Insets(2, 2, 2, 2), Color.WHITE));
			
			if (index == 3){
				thumbnails3.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
				imageSelected = thum3.returnIcon();
				path = path3;
				index = 3;
			}			
		}else{
			thumbnails3 = thum3.createImageLabel(null);
			thumbnails3.setPreferredSize(new Dimension(125,125));
			thumbnails3.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails3.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails3.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails3.setToolTipText(null);
		}
		
		thumbnails3.repaint();
		
		if (result.size() > (groupIndex + 3)){
			Vector data4 = new Vector(2);
			data4 = (Vector) result.elementAt(groupIndex + 3);
		
			path4 = (String)data4.elementAt(0);
			thumbName = path4.substring(0, path4.lastIndexOf('.')) + "_th.jpg";

			thumbnails4 = thum4.createImageLabel(thumbName);
			//thumbnails4.setSize(new Dimension(100,100));
			imageName4 = (String)data4.elementAt(1);
			thumbnails4.setToolTipText(imageName4);
			thumbnails4.setSize(125,125);
			thumbnails4.setBorder(new MatteBorder(
				new Insets(2, 2, 2, 2), Color.WHITE));
	
			if (index == 4){
				thumbnails4.setBorder(new MatteBorder(new Insets(2, 2, 2, 2), Color.RED));
				imageSelected = thum4.returnIcon();
				path = path4;
				index = 4;
			}			
		}else{
			thumbnails4 = thum4.createImageLabel(null);
			thumbnails4.setPreferredSize(new Dimension(125,125));
			thumbnails4.setText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails4.setHorizontalAlignment(SwingConstants.CENTER);
			//thumbnails4.setToolTipText(TLanguage.getString("TIGThumbnailsDialog.RESULT"));
			thumbnails4.setToolTipText(null);
		}
		
		thumbnails4.repaint();
		
		nImagesString = TLanguage.getString("TIGThumbnailsDialog.IMAGE") + " " + (index + groupIndex) + " " +
						TLanguage.getString("TIGThumbnailsDialog.OF") + " " + result.size();		
		nImagesTextField.setText(nImagesString);
		*/
		return this;
	}
	
	/*
	 * Delete the red border of the previous selected image 
	 * and paint a red border around the actual selected image. Update the
	 * information about the actual selected image.
	 */
	protected void selectThumbnail(int imageClicked){
		System.out.println("Imagen seleccionada: "+imageClicked);
		System.out.println("Vector de booleanos: ");
		
		//Saves the path of the selected image
		path = pathImage[imageClicked];
		
		//Update border of images
		if (containsImage[imageClicked]){
			for (int i=0; i<IMAGES_DISPLAYED; i++){
				if (i==imageClicked){
					thumbImages[i].updateBorder(Color.RED);
					imageSelected = thumbImages[i].returnIcon();
				}else{
					if (containsImage[i]){
						thumbImages[i].updateBorder(Color.GREEN);
					}else{
						thumbImages[i].updateBorder(Color.WHITE);
					}
				}				
			}
		}
		
		for (int i=0; i<4; i++){
			System.out.println("----- pos: "+i+"  "+containsImage[i]);
		}
	/*	if (image_index_display_selected != imageClicked)
		{
			switch (image_index_display_selected)
			{		
				case 1: 
					if ((imageClickedText(imageClicked).compareTo("") == 0) || (select &&
						(imageClickedText(imageClicked).compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)))
						thumbnails1.setBorder(new MatteBorder(
								new Insets(2, 2, 2, 2), Color.WHITE));
					break;
				case 2: 
					if ((imageClickedText(imageClicked).compareTo("") == 0) || (select &&
							(imageClickedText(imageClicked).compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)))
						thumbnails2.setBorder(new MatteBorder(
								new Insets(2, 2, 2, 2), Color.WHITE));
					break;
				case 3: 
					if ((imageClickedText(imageClicked).compareTo("") == 0) || (select &&
							(imageClickedText(imageClicked).compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)))
						thumbnails3.setBorder(new MatteBorder(
								new Insets(2, 2, 2, 2), Color.WHITE));
					break;
				case 4: 
					if ((imageClickedText(imageClicked).compareTo("") == 0) || (select &&
							(imageClickedText(imageClicked).compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)))
						thumbnails4.setBorder(new MatteBorder(
								new Insets(2, 2, 2, 2), Color.WHITE));
			}
			switch (imageClicked)
			{
				case 1: 
					if ((thum1.returnText().compareTo("") == 0) || 
						((thum1.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)
								&& select)){
						thumbnails1.setBorder(new MatteBorder(				
								new Insets(2, 2, 2, 2), Color.RED));
						imageSelected = thum1.returnIcon();
						path = path1;
						index = 1;
						image_index_display_selected = 1;
					}
					break;
				case 2: 
					if ((thum2.returnText().compareTo("") == 0) || 
							((thum2.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)
									&& select) && ((groupIndex + 2) <= result.size())){
						thumbnails2.setBorder(new MatteBorder(
								new Insets(2, 2, 2, 2), Color.RED));
						imageSelected = thum2.returnIcon();
						path = path2;
						index = 2;
						image_index_display_selected = 2;
					}
					break;
				case 3: 
					if ((thum3.returnText().compareTo("") == 0) || 
							((thum3.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)
									&& select) && ((groupIndex + 3) <= result.size())){
						thumbnails3.setBorder(new MatteBorder(
								new Insets(2, 2, 2, 2), Color.RED));
						imageSelected = thum3.returnIcon();
						path = path3;
						index = 3;
						image_index_display_selected = 3;
					}
					break;
				case 4: 
					if ((thum4.returnText().compareTo("") == 0) || 
							((thum4.returnText().compareTo(TLanguage.getString("TIGThumbImageDialog.TEXT")) == 0)
									&& select) && ((groupIndex + 4) <= result.size())){
						thumbnails4.setBorder(new MatteBorder(
								new Insets(2, 2, 2, 2), Color.RED));
						imageSelected = thum4.returnIcon();
						path = path4;
						index = 4;
						image_index_display_selected = 4;
				}
			}
			nImagesString = TLanguage.getString("TIGThumbnailsDialog.IMAGE") + " " + (index + groupIndex) + " " +
							TLanguage.getString("TIGThumbnailsDialog.OF") + " " + result.size();		
			nImagesTextField.setText(nImagesString);
		}*/
	}
	
	/*
	 * Returns the ImageIcon of the selected image
	 */
	protected ImageIcon imageSelected(){
		if (path != null){
			TIGThumbImage image = new TIGThumbImage();
			image.createImageLabel(path);
			return image.returnIcon();
		}
		else return null;
	}
	
	/*
	 * Returns the path of the selected image
	 */
	public String pathImageSelected(){
		return path;
	}
	/*
	 * Returns the text of the indicated thumbnail
	 */
	protected String imageClickedText(int imageClicked){
		switch (imageClicked)
		{		
			case 1: return thum1.returnText();
			case 2: return thum2.returnText();
			case 3: return thum3.returnText();
			case 4: return thum4.returnText();
			default: return "";
		}
	}
	
	public JPanel deleteImage(){
		int i=0;
		boolean deleted = false;
		String pathImage;
		Vector<String> image = new Vector<String>(2);
		if (path!=null){
			while (i<result.size() && !deleted){
				image = (Vector<String>) result.elementAt(i);
				pathImage = (String)image.elementAt(0);
				if (pathImage.equals(path)){
					result.removeElementAt(i);
					deleted = true;
				}
				i++;
			}
		}
		updateThumbnailsPanel(result, index);
		return this;
	}
	
	protected int getVectorIndex(char letter){
		int i = 0;
		boolean founded = false;
		while ((i < result.size()) && (!founded)){
			Vector data = new Vector(2);
			data = (Vector) result.elementAt(i);
			String name = ((String)data.elementAt(1)).toLowerCase();
			if (letter == 'ñ'){
				String aux = replace(name);
				if ((aux.charAt(0) == 'n') && (aux.length() > 1))
					if ((aux.charAt(1) == 'z'))
						founded = true;
					else i++;
				else i++;
			}else
				if (replace(name).charAt(0) >= letter) 
					founded = true;
				else i++;
		}
		if (!founded)
			return 0;
		selectThumbnail(1);
		if ((i >= result.size()) || (((result.size()-4) <= i)&& (i < result.size()))){
			selectThumbnail(5-(result.size()-i));
			return (result.size()-4);
		}			
		return i;
	}	
	
	@Override
	public boolean isFocusable(){
		return true;
	}

	public void mousePressed(java.awt.event.MouseEvent e) {}
	public void mouseReleased(java.awt.event.MouseEvent e) {}
	public void mouseClicked(java.awt.event.MouseEvent e) {}
	public void mouseEntered(java.awt.event. MouseEvent e) {
		System.out.println("ENTRO");
		this.requestFocus();
	}

	public void mouseExited(java.awt.event.MouseEvent e) {}

	public void keyReleased(java.awt.event.KeyEvent e){
		char letter = e.getKeyChar();
		if ((('a' <= letter) && (letter <= 'z')) || (('0' <= letter) && (letter <= '9')) || (letter == 'ñ')){
			int i = getVectorIndex(letter);
			updateThumbnailsPanel(i);
		}
		if (e.getKeyCode() == KeyEvent.	VK_RIGHT ){
			if ((index + 1) <= 4)
				selectThumbnail(index+1);
			else{
				int i = groupIndex + 4;
				if (total > 4){
					if ((i + 4) > total)
						if (i < total) 
							i = total - 4;
						else 
							i = 0;
					updateThumbnailsPanel(i);
					selectThumbnail((index+1)%4);
				}
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			if ((index - 1) >= 1)
				selectThumbnail(index-1);
			else{
				int i = groupIndex - 4;
				if (total > 4){
					if (i < 0){
						if ( i > -4)
							i = 0;
						else{
							if ((total - 4) > 0)
								i = total - 4;					
							else
								i = 0;
						}
					}
					updateThumbnailsPanel(i);
					selectThumbnail(4);
				}
			}
		}
	}

	public void keyTyped(java.awt.event.KeyEvent e) {}

	public void keyPressed(java.awt.event.KeyEvent e) {} 
	
	private static String replace(String word){
		CharSequence seq_nz = "nz";
		CharSequence seq_ñ = "ñ";
		CharSequence seq_NZ = "NZ";
		CharSequence seq_Ñ = "Ñ";
		String result = word.replace(' ', '_').replace(',', '-').replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o').replace('ú', 'u').
		replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U').replace(seq_ñ,seq_nz).replace(seq_Ñ,seq_NZ).toLowerCase();
		return result;
	}
	
	 //private void createThumbnail(ImageIcon image, String path){
	private void createThumbnail(String pathImage, String pathThumbnail){
	    	final int PREVIEW_WIDTH = 125;
	    	final int PREVIEW_HEIGHT = 125;	
	    	
	    	 File srcImage = new File(pathImage);
        	 ImageIcon imageIcon = null;
        	 if (srcImage != null) {			        		 
        		 // Test if need to be loaded with JAI libraries (different
					 // format than JPG, PNG and GIF)
					 if (TFileUtils.isJAIRequired(srcImage)) {
						 // Load it with JAI class
						 RenderedOp src_aux = JAI.create("fileload", srcImage.getAbsolutePath());
						 BufferedImage bufferedImage = src_aux.getAsBufferedImage();
						 imageIcon = new ImageIcon(bufferedImage);				
					 } else {
						 // Create it as usual
						 imageIcon = new ImageIcon(srcImage.getAbsolutePath());
					 }
        	 }
        	 File thum = new File (pathThumbnail);
        	 if (thum.exists()){
        		 System.out.println("Ya existe, no la creo");
        	 }else{
	    	try{
				int thumbWidth = PREVIEW_WIDTH;
				int thumbHeight = PREVIEW_HEIGHT;
				double thumbRatio = (double)thumbWidth / (double)thumbHeight;
				int imageWidth = imageIcon.getIconWidth();
				int imageHeight = imageIcon.getIconHeight();
				double imageRatio = (double)imageWidth / (double)imageHeight;
				if (thumbRatio < imageRatio) {
					thumbHeight = (int)(thumbWidth / imageRatio);
				} else {
					thumbWidth = (int)(thumbHeight * imageRatio);
				}
				BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics2D = thumbImage.createGraphics();
				graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				graphics2D.drawImage(imageIcon.getImage(), 0, 0, thumbWidth, thumbHeight, null);
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(pathThumbnail));
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);
				int quality = 100;
				quality = Math.max(0, Math.min(quality, 100));
				param.setQuality((float)quality / 100.0f, false);
				encoder.setJPEGEncodeParam(param);
				encoder.encode(thumbImage);
				out.close(); 
				} catch (Exception ex) {
					System.out.println("Error creating THUMBNAIL");
					System.out.println(ex.toString());   
				}	
        	 }
	    }
	
	/*private int getPosition(String name){
		int i = 0;
		boolean founded = false;
		while ((i < result.size()) && (!founded)){
			Vector data = new Vector(2);
			data = (Vector) result.elementAt(i);
			String element = (String) data.elementAt(1);
			if (element.compareTo(name) == 0)
				founded = true;
			else i++;
		}
		if (!founded) i = 0;
		return i;
	}*/
	
	protected int indexImageSelected(){
		int i = 0;
		boolean found = false;
		String pathImage;
		Vector<String> image = new Vector<String>(2);
		if (path!=null){
			while (i<result.size() && !found){
				image = (Vector<String>) result.elementAt(i);
				pathImage = (String)image.elementAt(0);
				if (pathImage.equals(path)){
					found = true;
				}else{
					i++;
				}
			}
		}
		if (!found) i=0;
		return i;
	}
	
	class selectedImageListener implements MouseListener{
		int selected;
		public selectedImageListener(int imageSelected){
			selected = imageSelected;
		}
		public void mouseClicked(MouseEvent arg0) {
			selectThumbnail(selected);
		}
		public void mouseEntered(MouseEvent arg0) {
		}
		public void mouseExited(MouseEvent arg0) {			
		}
		public void mousePressed(MouseEvent arg0) {
		}
		public void mouseReleased(MouseEvent arg0) {
		}
		
	}
}
