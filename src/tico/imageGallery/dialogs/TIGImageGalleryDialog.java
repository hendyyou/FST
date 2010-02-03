/*
 * File: TIGImageGalleryDialog.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Jan 18, 2008
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import tico.components.TButton;
import tico.components.TDialog;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.imageGallery.dataBase.TIGDataBase;

/*
 * This class displays the main window of the image Gallery. It allows
 * to search and select an image and introduces it in the TICO board which is 
 * being edited.
 */
public class TIGImageGalleryDialog extends TDialog{
	
	private boolean selected = false;
	
	private ImageIcon icon;
	
	private TEditor editor;
	
	private Vector images;
	
	private TIGDataBase dataBase;
	
	private GridBagConstraints c;
	
	private TIGThumbnailsDialog thumbnailsDialog;
	
	public TIGThumbnailsDialog getThumbnailsDialog() {
		return thumbnailsDialog;
	}

	public void setThumbnailsDialog(TIGThumbnailsDialog thumbnailsDialog) {
		this.thumbnailsDialog = thumbnailsDialog;
	}

	private JPanel thumbnailsPanel;
	
	private String pathImageSelected;
		
	public TIGImageGalleryDialog(TEditor editor, int index) {
		super(editor, true);
		
		this.editor = editor;
		dataBase = new TIGDataBase();
		TIGDataBase.conectDB();
		
		addWindowListener(new java.awt.event.WindowListener(){
			public void windowClosing(java.awt.event.WindowEvent e){
				dispose();
			}
			
			public void windowActivated(java.awt.event.WindowEvent e){}	  
			public void windowDeactivated(java.awt.event.WindowEvent e){}
			public void windowDeiconified(java.awt.event.WindowEvent e){}
			public void windowIconified(java.awt.event.WindowEvent e){}
			public void windowOpened(java.awt.event.WindowEvent e){}
			public void windowClosed(java.awt.event.WindowEvent e){}

		});
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle(TLanguage.getString("TImageGalleryDialog.TITLE"));
				
		// Create components
		// First, create the component that search from the names of the images
		JPanel searchNamePanel = new JPanel();
		images = new Vector();
		TIGSearchNameDialog searchNameDialog = new TIGSearchNameDialog();
		searchNamePanel = searchNameDialog.createSearchNamePanel(this.editor,dataBase,this);
		
		// Second, create the component that search from the words
		//that are associated to the images
		JPanel keyWordSearchPanel = new JPanel();
		TIGKeyWordSearchDialog keyWordSearchDialog = new TIGKeyWordSearchDialog(this.editor,dataBase);
		keyWordSearchPanel = keyWordSearchDialog.createKeyWordPanel(this);
		String keyWord1 = keyWordSearchDialog.KeyWord1();		
		
		//The images associated to the first key word in the Data Base are shown 
		//when the window is displayed  
		images = TIGDataBase.imageSearch("*");
		
		// Third, create the component that shows all the images
		thumbnailsPanel = new JPanel();
		thumbnailsDialog = new TIGThumbnailsDialog(false);
		// Create thumbnails panel with selection of images
		thumbnailsPanel = thumbnailsDialog.createThumbnailsPanel(images, true);
		thumbnailsPanel = thumbnailsDialog.updateThumbnailsPanel(images,index);
		
		thumbnailsPanel.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					icon = thumbnailsDialog.imageSelected();
					pathImageSelected = thumbnailsDialog.pathImageSelected();
					if (icon == null)
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TImageGalleryDialog.MESSAGE"),
								TLanguage.getString("TImageGalleryDialog.ERROR"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					else{
						selected = true;
						dispose();
					}
				}
			}
		});
				
		// Fourth, create two buttons. The first one insert the selected image
		// in the current board, and the second one closes the windows without changes
		JPanel buttons = new JPanel();
		TButton insertButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				icon = thumbnailsDialog.imageSelected();
				pathImageSelected = thumbnailsDialog.pathImageSelected();
				if (icon == null)
					JOptionPane.showConfirmDialog(null,
							TLanguage.getString("TImageGalleryDialog.MESSAGE"),
							TLanguage.getString("TImageGalleryDialog.ERROR"),
							JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
				else{
					selected = true;
					dispose();
				}
			}
		});
		insertButton.setText(TLanguage.getString("TImageGalleryDialog.INSERT"));

		TButton cancelButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setText(TLanguage.getString("TImageGalleryDialog.CANCEL"));
		
		ButtonGroup actionGroup = new ButtonGroup();	    
	    actionGroup.add(insertButton);
	    actionGroup.add(cancelButton);
		
				
		// Place the buttons
		GridBagConstraints but = new GridBagConstraints();
		buttons.setLayout(new GridBagLayout());
		
		but.fill = GridBagConstraints.BOTH;
		but.insets = new Insets(10, 10, 10, 10);
		but.gridx = 0;
		but.gridy = 0;
		buttons.add(insertButton, but);
		
		but.fill = GridBagConstraints.BOTH;
		but.insets = new Insets(10, 10, 10, 10);
		but.gridx = 1;
		but.gridy = 0;
		buttons.add(cancelButton, but);
					
		// Place components
		//GridBagConstraints c = new GridBagConstraints();
		getContentPane().setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(searchNamePanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(keyWordSearchPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 2;
		getContentPane().add(thumbnailsPanel, c);
		
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 3;
		getContentPane().add(buttons, c);		
		
		// Display the dialog
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setResizable(false);
		pack();

		setLocationRelativeTo(editor);
		setVisible(true);
	}
	
	/**
	 * Returns the selected <code>icon</code>.
	 * 
	 * @return The selected <code>icon</code>
	 */
	public ImageIcon getIcon() {
		return icon;
	}
	
	public String pathImageSelected(){
		return "images" + File.separator + pathImageSelected.substring(0,1).toUpperCase() + File.separator + pathImageSelected;
	}
	
	/**
	 * Update the thumbnails panel when a search has been made
	 */
	public void update(Vector result){				
		thumbnailsPanel = thumbnailsDialog.updateThumbnailsPanel(result,0);
	}
	
	public boolean isImageSelected(){
		return selected;
	}
	
	public int indexImageSelected(){
		return thumbnailsDialog.indexImageSelected();
	}
}