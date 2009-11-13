
/*
 * File: TInterpreterLanguajes.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Aug 22, 2006
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

import java.awt.event.ActionEvent;

import tico.configuration.TLanguage;
import tico.editor.dialogs.TEditorDialog;
import tico.interpreter.TInterpreter;
import tico.interpreter.dialog.TInterpreterDialog;



public class TInterpreterLanguajes extends TInterpreterAbstractAction
{



	 /* Constructor for TSelectAllAction.
	 * 
	 * @param editor The boards' editor
	 */
	

	public TInterpreterLanguajes(TInterpreter interpreter) {
		super(interpreter, TLanguage.getString("TInterpreterLanguaje.NAME"));
	}



	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new TInterpreterDialog(getInterpreter());
	}
}