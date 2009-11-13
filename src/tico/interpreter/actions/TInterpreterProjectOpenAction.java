/*
 * File: TInterpreterProjectOpenAction.java
 * 		This file is part of Tico, an application
 * 		to create and perfom interactive comunication boards to be
 * 		used by people with severe motor disabilities.
 * 
 * Authors: Antonio Rodríguez
 * 
 * Date:	May-2006 
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
package tico.interpreter.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import tico.components.resources.ProjectFilter;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.editor.TProjectHandler;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.TPanel;

/**
 * Action wich opens a project from a file and set it to the interpreter.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TInterpreterProjectOpenAction extends TInterpreterAbstractAction {
	private static File defaultDirectory = null;

	/**
	 * Constructor for TProjectOpenAction.
	 * 
	 * @param interpreter The boards' interpreter
	 */
	public TInterpreterProjectOpenAction(TInterpreter interpreter) {
		super(interpreter, TLanguage.getString("TProjectOpenAction.NAME"),
				TResourceManager.getImageIcon("archive-open-22.png"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// Open the project
		// Open a JFileChooser
		JFileChooser fileChooser = new JFileChooser();
		// Customoze JFileChooser
		fileChooser.setDialogTitle(TLanguage.getString("TProjectOpenAction.OPEN_PROJECT"));
		fileChooser.setCurrentDirectory(defaultDirectory);
		fileChooser.addChoosableFileFilter(new ProjectFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);

		// Checks if the user has chosen a file
		int returnValue = fileChooser.showOpenDialog((Component)null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// Get the chosen file
			
			// Set its directory as next first JFileChooser directory
			
			TInterpreterConstants.vmap=null;
			TInterpreterConstants.map2=null;
			interpreter.BrowseGrid=0;
			TInterpreterConstants.SecondClic=0;
			TInterpreterConstants.ClicReleased=0;
			TInterpreterRun.fin=false;
			File selectedFile = fileChooser.getSelectedFile();
			defaultDirectory = selectedFile.getParentFile();
			try {
				//Update Maps
				
				getInterpreter().deleteProject();
				// Load the project
				
				getInterpreter().setProject(TProjectHandler.loadProject(selectedFile));
				
				
			} catch (Exception ex) {
				// If the import fails show an error message
				JOptionPane.showMessageDialog(null,
						TLanguage.getString("TProjectOpenAction.OPEN_ERROR"),
						TLanguage.getString("ERROR") + "!",
						JOptionPane.ERROR_MESSAGE);
			}
			
			getInterpreter().accumulatedArea.removeAll();
			
				
			getInterpreter().AccumulatedList = new ArrayList();
			getInterpreter().accumulatedArea.updateUI();
			TInterpreterRun.i=0;
		}
	}
}
