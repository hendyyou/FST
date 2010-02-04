/*
 * File: TInterpreterControllerCellReadMouseListener.java
 * 		This file is part of Tico, an application
 * 		to create and perform interactive communication boards to be
 * 		used by people with severe motor disabilities.
 * 
 * Authors: Carolina Palacio
 * 
 * Date:	Dec, 2009 
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

package tico.interpreter.listeners;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.actions.TInterpreterExitAction;
import tico.interpreter.actions.TInterpreterReadAction;
import tico.interpreter.actions.TInterpreterStopAction;
import tico.interpreter.actions.TInterpreterUndoAction;
import tico.interpreter.actions.TInterpreterUndoAllAction;
import tico.interpreter.components.TInterpreterCell;


public class TReadCellListener implements MouseListener {
	
	TInterpreter interpreter;

	public TReadCellListener() {
		interpreter = TInterpreterConstants.interpreter;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if ((interpreter.run==1)){
			if (TInterpreterConstants.interpreter.getActivateBrowsingMode()==1){ // barrido automatico
				try {					
					TInterpreterConstants.semaforo.acquire();		
				//	System.out.println("ACQUIRE READ");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			TInterpreterReadAction read = new TInterpreterReadAction(interpreter);
			read.actionPerformed(null);
			if (TInterpreterConstants.interpreter.getActivateBrowsingMode()==1){ // barrido automatico				
			//	System.out.println("RELEASE READ");
				try {
					TInterpreterConstants.semaforo.release();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}			
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if ((interpreter.run==1)){
			TInterpreterCell controllerCellButton = (TInterpreterCell) arg0.getSource();
			Border thickBorder = new LineBorder(TInterpreterConstants.SELECTED_BORDER_COLOR, TInterpreterConstants.SELECTED_BORDER_SIZE);
			controllerCellButton.setBorder(thickBorder);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {		
		if ((interpreter.run==1)){
			TInterpreterCell controllerCellButton = (TInterpreterCell) arg0.getSource();
			Border thickBorder = new LineBorder(TInterpreterConstants.BORDER_COLOR, TInterpreterConstants.BORDER_SIZE);
			controllerCellButton.setBorder(thickBorder);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	
}

    