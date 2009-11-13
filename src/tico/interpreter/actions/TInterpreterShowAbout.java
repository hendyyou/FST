/*
 * File: TInterpreterShowAbout.java
 * 		This file is part of Tico, an application
 * 		to create and perfom interactive comunication boards to be
 * 		used by people with severe motor disabilities.
 * 
  * Authors: Antonio Rodríguez
 * 
 * Date:	May-2006 
 *  
 * Based on: @(#)AbstractActionDefault.java 1.0 12-MAY-2004
 *           Copyright (c) 2001-2005, Gaudenz Alder
 *           From JGraphPad 5.7.3.1.1
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
import tico.interpreter.TInterpreter;
import tico.interpreter.dialog.TAboutDialogInterpreter;


/**
 * Action wich exists from the interpreter application.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TInterpreterShowAbout  extends  TInterpreterAbstractAction {
	/**
	 * Constructor for TInterpreterExitAction.
	 * 
	 * @param interpreter The interpreter' interpreter
	 */

	public TInterpreterShowAbout(TInterpreter interpreter) {
		/*Name the menuItem*/
		super(interpreter, TLanguage.getString("TEditorAboutAction.NAME"));
	}

	public void actionPerformed(ActionEvent e) {
		new TAboutDialogInterpreter(getInterpreter());
	}

}
