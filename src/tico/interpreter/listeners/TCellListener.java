/*
 * File: TCellListener.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Isabel González y Carolina Palacio
 * 
 * Date: Nov, 2009
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
import java.awt.MenuBar;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import tico.components.resources.TFileUtils;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.components.TInterpreterAccumulatedCell;
import tico.interpreter.components.TInterpreterCell;
import tico.interpreter.components.TInterpreterTextArea;
import tico.interpreter.threads.TInterpreterMp3Sound;
import tico.interpreter.threads.TInterpreterVideo;
import tico.interpreter.threads.TInterpreterWavSound;


public class TCellListener implements MouseListener {
	
	TInterpreterCell cell;
	TInterpreter interpreter;

	public TCellListener(TInterpreterCell c) {
		cell = c;
		interpreter = TInterpreterConstants.interpreter;
	}

	public void mouseClicked(MouseEvent arg0) {
		
		if ((TInterpreter.run==1)){
			
			TInterpreterCell cell = (TInterpreterCell) arg0.getSource();
			
			if (TInterpreterConstants.interpreter.getActivateBrowsingMode()==1){ // barrido automatico
				
				try {						
					TInterpreterConstants.semaforo.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			}	
			
			if (cell.isAccumulated()){
					
					if (TInterpreter.accumulatedCellsList.size() < TInterpreterConstants.interpreterAcumulatedCells){
						TInterpreterAccumulatedCell accumulatedCell = new TInterpreterAccumulatedCell();
						accumulatedCell.setAttributes(cell);
						TInterpreter.accumulatedCellsList.add(accumulatedCell);
						TInterpreter.accumulatedCells.add(accumulatedCell);
						
						TInterpreter.accumulatedCells.updateUI();
					}
			}
			
			if (cell.getSoundPath()!= null){
				
				String extension= TFileUtils.getExtension(cell.getSoundPath());
				if (extension.equals("mp3")){
					TInterpreterConstants.audioMp3 = new TInterpreterMp3Sound(cell.getSoundPath());
					TInterpreterConstants.audioMp3.TPlay();			 
					TInterpreterConstants.audioMp3.TJoin();
				}
				else {
					TInterpreterConstants.audio=new TInterpreterWavSound(cell.getSoundPath());	
					TInterpreterConstants.audio.start();
					try {
						TInterpreterConstants.audio.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
							System.err.println("Error al reproducir el sonido de la celda");
						}
				}
			}
		
			if (cell.getVideoPath()!=null) {
				Point point = cell.getLocationOnScreen();
				if (cell.getVideoPath()!=null){
					cell.setXVideo(point.x-5);
					cell.setYVideo(point.y-54);
				}	
				try {
					TInterpreterVideo video = new TInterpreterVideo(cell.getVideoPath(), cell.getXVideo(), 
							cell.getYVideo(), cell.getWidth(), cell.getHeight(), interpreter);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//System.out.println("VIDEO NULL");
			}
		
			if (cell.getTextAreaToSend()!= null){
				TInterpreterTextArea textArea = TInterpreter.getCurrentBoard().getTextAreaByName(cell.getTextAreaToSend());

				if (textArea != null){
					String originalText = textArea.getText();
					
					textArea.setText(cell.getTextToSend());
					
					interpreter.interpretArea.paintImmediately(0, 0, interpreter.interpretArea.getWidth(), interpreter.interpretArea.getHeight());

					waiting w = new waiting(cell.getTimeSending());
					w.start();
					try {
						w.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					textArea.setText(originalText);
					TInterpreter.interpretArea.removeAll();
					//No vuelvo al estado inicial para que la celda que envía texto permanezca seleccionada
					TInterpreter.getCurrentBoard().paintBoard(TInterpreter.interpretArea, false);
					TInterpreter.interpretArea.repaint();
				}
			}
		
			if (cell.getBoardToGo()!= null){
				
				TInterpreterConstants.tableroActual = interpreter.getProject().getBoard(cell.getBoardToGo());
				TInterpreterConstants.countRun = 0;
				TInterpreterConstants.boardOrderedCells = TInterpreterConstants.tableroActual.getOrderedCellListNames();
				interpreter.getProject().setPositionCellToReturn(0);

					interpreter.getProject().setBoardToReturn(interpreter.getProject().getCurrentBoard());					
					interpreter.getProject().setCellToReturn((((TInterpreterCell)arg0.getSource()).getName()));
					
					interpreter.getProject().getPositionCellToReturnByName(interpreter.getProject().getBoardToReturn(), interpreter.getProject().getCellToReturn());
					
					//Repinta el tablero, para que se quede en el estado inicial
					interpreter.repaintCurrentBoard(false);
					
					interpreter.changeBoard(cell.getBoardToGo());		
					interpreter.getProject().setBoardChanged(true);
					interpreter.getProject().setBoardToGo(cell.getBoardToGo());
					
					interpreter.getProject().setCurrentBoard(cell.getBoardToGo());
			}
			
			if (cell.getCommand()!=null){
				try {
					Runtime.getRuntime().exec(cell.getCommand());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println(e.getCause());
				}				
			}
			
			if (TInterpreterConstants.interpreter.getActivateBrowsingMode()==1){ // barrido automatico
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
		
		if ((TInterpreter.run==1)){
			TInterpreterCell cell = (TInterpreterCell) arg0.getSource();
			//Default border attributes
			Color colorLine = Color.red;
			int widthLine = 1;			
			
			if (cell.getAlternativeIcon()!=null){
				cell.setIcon(cell.getAlternativeIcon());
			}
			
			if (cell.getAlternativeBorderSize()!= 0){
				cell.setBorderPainted(true);
				colorLine = cell.getAlternativeBorderColor();
				widthLine= cell.getAlternativeBorderSize();
			}
			
			Border thickBorder = new LineBorder(colorLine, widthLine);
			cell.setBorder(thickBorder);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
		if ((TInterpreter.run==1)){
			
			TInterpreterCell cell = (TInterpreterCell) arg0.getSource();

			// Sets the original icon
			cell.setIcon(cell.getDefaultIcon());

			
			if (cell.isTransparentBorder()){
				cell.setBorderPainted(false);
			}
			else{
				cell.setBorder(new LineBorder(cell.getBorderColor(), (int)cell.getBorderSize()));
			}
			
			if (cell.isTransparentBackground()){
				cell.setFocusPainted(false);
				cell.setContentAreaFilled(false);
			}
			else if(cell.getBackground()!= null){
				cell.setFocusPainted(true);
				cell.setBackground(cell.getBackground());
			}			
			}
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {

	}
	
   class waiting extends Thread{
		
		private int secs;
		
		public waiting(int segundos){
			secs = segundos;
		}
		
			public void run(){
				try {					
		            Thread.sleep(secs*1000);		            
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
			}
		}
	
}

    
