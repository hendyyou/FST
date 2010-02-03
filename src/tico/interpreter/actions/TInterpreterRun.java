/*
 * File: TInterpreterRun.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * * Authors: Antonio Rodríguez
 * 
 * Date:	May-2006 
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

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterBoard;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.TInterpreterProject;
import tico.interpreter.components.TInterpreterCell;
import tico.interpreter.threads.TThreads;

public class TInterpreterRun extends TInterpreterAbstractAction implements ActionListener, Runnable {
 
	Robot Retraso;
	
	public static boolean fin=false;
	private double Tancho,Talto;
	private static int accessController=0;
	public static boolean fina = false;
	
	public int actionPerformed=0;
    public int count=0;

	private TInterpreterConstants tic;
	
	public TInterpreterRun (TInterpreter interpreter) {
		
		super(interpreter,TLanguage.getString("TInterpreterRunAction.NAME"),TResourceManager.getImageIcon("run.gif"));
		tic = new TInterpreterConstants();
	}	

	public void actionPerformed(ActionEvent e) {
		
		interpreter=getInterpreter();
			
		try {
			TInterpreterConstants.semaforo.releaseWhenStop();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TInterpreter.TStart=new TThreads(this);
		TInterpreter.TStart.start();
		
	}
	
	public void run() {//Velocidad del
		
		interpreter.run = 1;
		interpreter.interpreterRobot.setAutoDelay(TInterpreterConstants.interpreterDelay);
		interpreter.TIntepreterChangeCursor();
	
	    TInterpreterProject project = interpreter.getProject();
	    TInterpreterConstants.tableroActual = project.getBoard(project.getBoardToGo());
	    project.setBoardChanged(false);
	    	    	
		if (interpreter.getActivateBrowsingMode()==1){
					
			if (TInterpreterConstants.tableroActual.getOrderedCellListNames().size()!=0){
				
				TInterpreterConstants.boardOrderedCells = TInterpreterConstants.tableroActual.getOrderedCellListNames();
				
				int posInicioBarrido = interpreter.getProject().getPositionCellToReturn();
				TInterpreterConstants.countRun = posInicioBarrido;
				
				while (TInterpreterConstants.countRun < TInterpreterConstants.tableroActual.getOrderedCellListNames().size() && interpreter.run==1){		
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					try {						
						TInterpreterConstants.semaforo.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					TInterpreterCell cell = TInterpreterConstants.tableroActual.getCellByName(TInterpreterConstants.boardOrderedCells.get(TInterpreterConstants.countRun));
					Point point = cell.getLocation();
					SwingUtilities.convertPointToScreen(point, interpreter.interpretArea);
            		cell.setCenter(new Point(point.x+cell.getWidth()/2,point.y+cell.getHeight()/2));
            		
            		TInterpreterConstants.x = cell.getCenter().x -1;
            		TInterpreterConstants.y = cell.getCenter().y -1;
            		interpreter.interpreterRobot.mouseMove(cell.getCenter().x,cell.getCenter().y);

            		TInterpreterConstants.countRun++;
					if (TInterpreterConstants.countRun==TInterpreterConstants.tableroActual.getOrderedCellListNames().size())
						TInterpreterConstants.countRun=0;
					
					try {
						TInterpreterConstants.semaforo.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}								
			}
			
		}    
   }
	
 }

