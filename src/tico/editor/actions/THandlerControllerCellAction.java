/*
 * File: THandlerCellAction.java
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

import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.editor.handler.TCellMarqueeHandler;
import tico.editor.handler.TControllerCellMarqueeHandler;

/**
 * Action wich sets <code>TCellMarqueeHandler</code> as the selected board
 * marque handler.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class THandlerControllerCellAction extends TEditorAbstractAction {
	/**
	 * Constructor for THandlerCellAction.
	 * 
	 * @param editor The boards' editor
	 */
	public THandlerControllerCellAction(TEditor editor) {
		super(editor, TLanguage.getString("THandlerControllerCellAction.NAME"),
				TResourceManager.getImageIcon("handler-cell-controller-22.png"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		editor.getCurrentBoard().setMarqueeHandler(
				new TControllerCellMarqueeHandler(getEditor().getCurrentBoardContainer()));
	}
}