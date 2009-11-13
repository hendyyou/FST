/*
 * File: TBoardInterpretAction.java
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

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.interpreter.TInterpreter;

/**
 * Action wich begins the interpretation of the editor selected board.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TBoardInterpretAction extends TEditorAbstractAction {
	/**
	 * Constructor for TBoardInterpretAction.
	 * 
	 * @param editor The boards' editor
	 */
	public TBoardInterpretAction(TEditor editor) {
		super(editor, TLanguage.getString("TBoardInterpretAction.NAME"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		int choosenOption = JOptionPane.NO_OPTION;
		// Check if a board is selected
		if (getEditor().getCurrentBoard() == null) {
			JOptionPane.showMessageDialog(null,
					TLanguage.getString("TBoardInterpretAction.NO_BOARD_ERROR"),
					TLanguage.getString("WARNING") + "!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		// Ask the user if wants to save the project before opening the new one
		if (getEditor().isModified())
			choosenOption = JOptionPane.showConfirmDialog(null,
					TLanguage.getString("TBoardInterpretAction.ASK_SAVE") + "\n" + 
					TLanguage.getString("TBoardInterpretAction.ASK_SAVE_QUESTION"),
					TLanguage.getString("TBoardInterpretAction.MODIFIED_BOARD"),
					JOptionPane.YES_NO_CANCEL_OPTION);
		// If cancel, exit
		if (choosenOption == JOptionPane.CANCEL_OPTION)
			return;
		// If yes, run the project save action
		if (choosenOption == JOptionPane.YES_OPTION)
			new TProjectSaveAction(getEditor()).actionPerformed(e);
		// Launch project interpreter
		new TInterpreter(getEditor().getCurrentBoard());
	}
}
