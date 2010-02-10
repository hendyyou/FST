/*
 * File: TIGNewImageDataDialog.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Mar 31, 2008
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
 

package tico.imageGallery.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


import tico.components.TButton;
import tico.components.TDialog;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.imageGallery.components.TIGImportTask;
import tico.imageGallery.dataBase.TIGDataBase;


 * This class displays the dialog that contains all the data of an image
 * and allows to modify all that information 
 
public class TIGNewImageDataDialog extends TDialog{
	
	private TEditor myEditor;
	
	private String oldName;
	
	private String oldPath;
	
	private TIGDataBase myDataBase;
	
	private TIGKeyWordInsertDialog keyWordPanel;
	
	private TIGModifyImageNameDialog imagePanel;
	
	private TIGImportTask myTask;
	
	private int option = 0;
	
	public TIGNewImageDataDialog(TEditor editor, TIGDataBase dataBase,ImageIcon icon, String path, String name,TIGImportTask task) {
		super(editor, TLanguage.getString("TIGNewImageDataDialog.NAME"),true);	
		this.myEditor = editor;
		myDataBase = dataBase;
		this.oldName = name;
		this.oldPath = path;
		myTask = task;
		
		// Create components
		// First, create component that shows the name of the image
		imagePanel = new TIGModifyImageNameDialog(
				TLanguage.getString("TIGNewImageDataDialog.DATANAME"),icon,oldName);
		
		// Second, create the key word component that shows the concepts asociated
		//or not to the image
		keyWordPanel = new TIGKeyWordInsertDialog(path);
				
		// Third, create two buttons, the first for modifying the image, and the second
		//for closing without changes
		JPanel buttons = new JPanel();
		TButton insertButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				String newName = imagePanel.returnName();
				option = 1;
				Vector concepts = new Vector();
				concepts = keyWordPanel.returnKeyWords();
				TIGDataBase.insertDB(concepts, oldPath, newName);
				dispose();
			}
		});
		insertButton.setText(TLanguage.getString("TIGNewImageDataDialog.NEXT"));
		
		TButton cancelButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		cancelButton.setText(TLanguage.getString("TIGNewImageDataDialog.CANCEL"));
		
		TButton exitButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				myTask.stop();
				dispose();
			}
		});
		exitButton.setText(TLanguage.getString("TIGNewImageDataDialog.EXIT"));
		
		// Place buttons
		GridBagConstraints but = new GridBagConstraints();
		buttons.setLayout(new GridBagLayout());
		
		but.fill = GridBagConstraints.CENTER;
		but.insets = new Insets(10, 5, 10, 5);
		but.gridx = 1;
		but.gridy = 0;
		buttons.add(insertButton, but);		
		
		but.fill = GridBagConstraints.CENTER;
		but.insets = new Insets(10, 5, 10, 5);
		but.gridx = 2;
		but.gridy = 0;
		buttons.add(cancelButton, but);
		
		but.fill = GridBagConstraints.CENTER;
		but.insets = new Insets(10, 5, 10, 5);
		but.gridx = 3;
		but.gridy = 0;
		buttons.add(exitButton, but);
					
		// Place components
		GridBagConstraints c = new GridBagConstraints();
		getContentPane().setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(imagePanel, c);

		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(keyWordPanel, c);
		
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(10, 5, 10, 5);
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
	
	public int getOption(){
		return option;
	}
	
}
*/