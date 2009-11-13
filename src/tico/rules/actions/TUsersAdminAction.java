/* File: TUsersAdminAction.java
 * 		This file is part of Tico, an application
 * 		to create and perfom interactive comunication boards to be
 * 		used by people with severe motor disabilities.
 * 
 * Authors: Beatriz Mateo
 * 
 * Date: Sep 6, 2007
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
package tico.rules.actions;

import java.awt.event.ActionEvent;

import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.editor.actions.TEditorAbstractAction;
import tico.rules.dialogs.TUsersAdminDialog;

/**
 * Action which opens the users administration dialog.
 * 
 * @author Beatriz Mateo
 * @version 0.1 Sep 6, 2007
 */
public class TUsersAdminAction extends TEditorAbstractAction {
	/**
	 * Constructor for TUsersAdminAction.
	 * 
	 * @param editor The boards' editor
	 */
	public TUsersAdminAction(TEditor editor) {
		super(editor, TLanguage.getString("TUsersAdminAction.NAME"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		new TUsersAdminDialog(getEditor());
	}
}
