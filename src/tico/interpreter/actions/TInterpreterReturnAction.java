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
import tico.interpreter.TInterpreterConstants;

public class TInterpreterReturnAction extends TInterpreterAbstractAction{
	
	String nameCell;
	
	public TInterpreterReturnAction (TInterpreter interpreter, String name){
		super(interpreter,TLanguage.getString("TInterpreterUndo"));
		nameCell = name; 
	}
	public void actionPerformed(ActionEvent e) {
		if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE)){ // barrido automatico
			
			try {						
				TInterpreterConstants.semaforo.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}
		
		interpreter=getInterpreter();
		String returnBoard = interpreter.getProject().getBoardToReturn();
		interpreter.getProject().setPositionCellToReturn(interpreter.getProject().getPositionCellToReturnByName(returnBoard, interpreter.getProject().getCellToReturn()));
		
		TInterpreterConstants.tableroActual = interpreter.getProject().getBoard(returnBoard);
		TInterpreterConstants.countRun = interpreter.getProject().getPositionCellToReturn();
		TInterpreterConstants.boardOrderedCells = TInterpreterConstants.tableroActual.getOrderedCellListNames();
		
		interpreter.getProject().setBoardToReturn(interpreter.getProject().getCurrentBoard());					
		interpreter.getProject().setCellToReturn(nameCell);		

		//Repinta el tablero actual para que se quede en el estado inicial
		interpreter.repaintCurrentBoard(false);
		
		interpreter.changeBoard(returnBoard);
		interpreter.getProject().setBoardToGo(returnBoard);
		interpreter.getProject().setBoardChanged(true);

		interpreter.getProject().setCurrentBoard(returnBoard);
		
		if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.MANUAL_SCANNING_MODE)){
			TInterpreter.boardListener.click();
		}
		
		if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE)){ // barrido automatico
			try {
				TInterpreterConstants.semaforo.release();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}				
		}
	}

}
