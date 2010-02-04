/*
 * File: TImageGalleryButton.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: May 23, 2008
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

package tico.imageGallery;

import tico.pluginInterfaces.TPluginIMInterface;
import tico.editor.TEditor;
import tico.editor.TFileHandler;
import tico.imageGallery.dataBase.TIGDataBase;
import tico.imageGallery.dialogs.TIGImageGalleryDialog;
import tico.components.TButton;
import tico.components.TImageChooser;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tico.configuration.TLanguage;

/**
 * Implementation of the button that opens the image gallery
 * 
 * @author Patricia M. Jaray
 * @version 1.0 May 23, 2008
 */

//TODO Esta clase est� programada como un plugin porque inicialmente lo era. Actualmente no se comporta como tal, por tanto deber�a reprogramarse.

public class TImageGalleryButton implements TPluginIMInterface{
	
	private TEditor myEditor;
	
	private TImageChooser theChooser;
		
	private Vector<Vector<String>> data;
	
	private int indexImageSelected = 0;
	
	private String path = null;
	
	/**
	 * Creates a <code>TButton</code> to open the image gallery into the specified 
	 * <code>TImageChooser</code> of the <code>TEditor</code>
	 * 
	 * @param editor The boards' editor
	 * @param chooser The <code>TImageChooser</code> where the <code>TButton</code> 
	 * to open the image gallery is going to be displayed
	 * 
	 * @return The generated <code>TButton</code> to open the image gallery
	 */
	
	public TButton init(TEditor editor, TImageChooser chooser){
		myEditor = editor;
		TIGDataBase.conectDB();
		data = TIGDataBase.imageSearch("*");
		TIGDataBase.closeDB();
		
		theChooser = chooser;
		
		TButton openGalleryButton = new TButton(new AbstractAction(TLanguage.getString("TImageChooser.BUTTON_OPEN")) {
			public void actionPerformed(ActionEvent e) {
				if (data.size() > 0){
					TIGImageGalleryDialog imageGallery = new TIGImageGalleryDialog(myEditor,indexImageSelected);
					// Import the file to the application directory
					if (imageGallery.isImageSelected()){
						File selectedFile = new File(imageGallery.pathImageSelected());
						path = System.getProperty("user.dir")+File.separator+imageGallery.pathImageSelected();
						indexImageSelected = imageGallery.indexImageSelected();
						try {
							selectedFile = TFileHandler.importFile(selectedFile);
						} catch (Exception ex) {
							// If the import fails show an error message
							JOptionPane.showMessageDialog(null,
								TLanguage.getString("TImageChooser.CHOOSE_IMAGE_ERROR"),
								TLanguage.getString("ERROR")+"!",
								JOptionPane.ERROR_MESSAGE);
						}
						ImageIcon newImageIcon = new ImageIcon(selectedFile
							.getAbsolutePath(), selectedFile
							.getAbsolutePath());
					
						theChooser.setIcon(newImageIcon);	
						theChooser.updateComponents();
					}
				}else
					JOptionPane.showConfirmDialog(null,
							TLanguage.getString("TIGManageImageAction.EMPTY"),
							TLanguage.getString("TIGManageImageAction.ERROR"),
							JOptionPane.CLOSED_OPTION ,JOptionPane.WARNING_MESSAGE );
			}
		});
		return openGalleryButton;
	}
	
	/**
	 * Returns the path of the image selected from the image gallery
	 * 
	 * @return The <code>path</code> of the selected image from the image gallery
	 */
	public String getPath(){
		return path;
	}
}