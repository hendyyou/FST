/*
 * File: TProjectSaveAsAction.java
 * 		This file is part of Tico, an application
 * 		to create and perfom interactive comunication boards to be
 * 		used by people with severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Jan 30, 2006
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
package tico.editor.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import tico.board.TProject;
import tico.components.resources.ProjectFilter;
import tico.components.resources.TFileUtils;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.configuration.TSetup;
import tico.editor.TEditor;
import tico.editor.TProjectHandler;

/**
 * Action which saves the current editor project into a file with the specified
 * name.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TProjectSaveAsAction extends TEditorAbstractAction {
	private static File defaultDirectory = null;
	
	/**
	 * Constructor for TProjectSaveAsAction.
	 * 
	 * @param editor The boards' editor
	 */
	public TProjectSaveAsAction(TEditor editor) {
		super(editor, TLanguage.getString("TProjectSaveAsAction.NAME"),
				TResourceManager.getImageIcon("archive-save-as-22.png"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// Open a JFileChooser
		JFileChooser fileChooser = new JFileChooser();
		// Customize JFileChooser
		fileChooser.setDialogTitle(TLanguage.getString("TProjectSaveAsAction.SAVE_PROJECT"));
		fileChooser.setCurrentDirectory(defaultDirectory);
		fileChooser.setSelectedFile(new File(getEditor().getProject()
				.getName() + "." + TFileUtils.TCO));
		fileChooser.addChoosableFileFilter(new ProjectFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);

		// Checks if the user has chosen a file
		int returnValue = fileChooser.showSaveDialog((Component)null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// Get the chosen file
			File selectedFile = fileChooser.getSelectedFile();
			// Set its directory as next first JFileChooser directory
			defaultDirectory = selectedFile.getParentFile();
			// Check the extension and if it has not, add it
			selectedFile = new File(TFileUtils.setExtension(selectedFile
					.getAbsolutePath(), TFileUtils.TCO));
			// Check if the file exists and if the user wants to overwrite it
			if (selectedFile.exists())
				if (JOptionPane.showConfirmDialog(null,
						TLanguage.getString("TProjectSaveAsAction.CHOOSE_FILE_EXISTS") + "\n" +
						TLanguage.getString("TProjectSaveAsAction.CHOOSE_FILE_EXISTS_QUESTION"),
						TLanguage.getString("TProjectSaveAsAction.CHOOSE_FILE_OVERWRITE"),
						JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
					return;

			try {
				// Set the selected name to the project
				getEditor().getProject().setName(TFileUtils.getFilename(selectedFile));
				// Get the project
				TProject project = getEditor().getProject();
				// Save the file
				TProjectHandler.saveProject(project, selectedFile);
				// Set modified to false
				getEditor().setModified(false);
				// Set the selected file as the base file for the project
				getEditor().setProjectFile(selectedFile);
				// Set the editor home directory
				TSetup.setEditorHome(selectedFile.getParent().toString());
			} catch (Exception ex) {
				// If the import fails show an error message
				JOptionPane.showMessageDialog(null,
						TLanguage.getString("TProjectSaveAsAction.SAVE_ERROR"),
						TLanguage.getString("Error") + "!",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
