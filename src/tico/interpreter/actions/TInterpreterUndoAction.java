/*
 * File: TRectangle.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Antonio ROdriguez
 * 
 * Date: Jan, 2007
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

public class TInterpreterUndoAction extends TInterpreterAbstractAction{

	
	public TInterpreterUndoAction (TInterpreter interpreter){
		super(interpreter,TLanguage.getString("TInterpreterUndoAction.NAME"));
	}
	public void actionPerformed(ActionEvent e) {
		interpreter=getInterpreter();
		if (TInterpreter.accumulatedCellsList.size()>0){
			TInterpreter.accumulatedCellsList.remove(TInterpreter.accumulatedCellsList.size()-1);
			interpreter.accumulatedCells.remove(TInterpreter.accumulatedCellsList.size());
			interpreter.accumulatedCells.updateUI();
		}
		
	}

}
