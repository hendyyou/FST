/*
 * File: TCellListener.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Isabel Gonz�lez y Carolina Palacio
 * 
 * Date: Nov, 2009
 * 
 * Company: Universidad de Zaragoza, CPS, DIIS
 * 
 * License:
 * 		This program is free software: you can redistribute it and/or 
 * 		modify it under the terms of the GNU General Public License 
 * 		as published by the Free Software Foundation, either version 3
 * 		of the License, or (at your option) any later version.
 * 
 * 		This program is distributed in the hope that it will be useful,
 * 		but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 		GNU General Public License for more details.
 * 
 * 		You should have received a copy of the GNU General Public License
 *     	along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

package tico.interpreter.listeners;

import java.awt.Color;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import javazoom.jl.player.Player;
import javazoom.jl.player.ThreadedPlayer;

import sun.misc.Launcher;
import tico.components.resources.TFileUtils;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterBoard;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.actions.TInterpreterRun;
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
		
		boolean changeCell = false;

		if ((TInterpreter.run == 1)) {

			if (arg0.getButton() == MouseEvent.BUTTON3) { // Right button
				if (TInterpreter.returnMouseMode().equals(
						TInterpreterConstants.MANUAL_SCANNING_MODE)) {
					TInterpreter.boardListener.click();
				}
			} else {

				// TInterpreterCell cell = (TInterpreterCell) arg0.getSource();

				if (cell.isAccumulated()) {

					if (TInterpreter.accumulatedCellsList.size() < TInterpreterConstants.accumulatedCells) {
						TInterpreterAccumulatedCell accumulatedCell = new TInterpreterAccumulatedCell();
						accumulatedCell.setAttributes(cell);
						TInterpreter.accumulatedCellsList.add(accumulatedCell);
						TInterpreter.accumulatedCells.add(accumulatedCell);

						TInterpreter.accumulatedCells.updateUI();
					}
				}

				/*
				 * ADRIAN: If there is an alternative audio playing it will be
				 * stopped to play another audio file
				 */

				if (cell.getAlternativeSoundPath() != null) {

					String extension = TFileUtils
							.getExtension(cell.alternativeSoundPath);
					if (extension.equals("mp3")) {
						if (TInterpreterConstants.alternativeAudioMp3
								.TIsAlive()) {
							TInterpreterConstants.alternativeAudioMp3.TStop();
							try {
								TInterpreterConstants.semaforoAudio.release();
								
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(cell.getSoundPath() == null){
								if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE)) { // barrido automático
									//TInterpreterConstants.semaforo.release();
									changeCell = true;
								}
							}
							System.out.println("Stop alternative sound");
						}
					} else {
						if (TInterpreterConstants.alternativeAudio.isAlive()) {
							TInterpreterConstants.alternativeAudio.interrupt();
							try {
								TInterpreterConstants.semaforoAudio.release();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							if(cell.getSoundPath() == null){
								if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE)) { // barrido automático
									//TInterpreterConstants.semaforo.release();
									changeCell = true;
								}
							}
							System.out.println("Stop alternative sound");
						}
					}
				}

				if (cell.getSoundPath() != null) {

					String extension = TFileUtils.getExtension(cell
							.getSoundPath());
					if (extension.equals("mp3")) { //MP3
						/*
						 * ADRIAN: If there is a music playing it will be
						 * stopped with one click otherwise a sound will be
						 * played
						 */
						if (TInterpreterConstants.audioMp3 == null) { // Sound not playing, playing first time
							TInterpreterConstants.audioMp3 = new TInterpreterMp3Sound(
									cell.getSoundPath());
							try {
								TInterpreterConstants.semaforoAudio.acquire(); 
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							TInterpreterConstants.audioMp3.TPlay();
							System.out.println("Play sound first time");
						} else {
							if (TInterpreterConstants.audioMp3.TIsAlive()) { //Sound already playing
								TInterpreterConstants.audioMp3.TStop();
								try {
									TInterpreterConstants.semaforoAudio.release();
									System.out.println("Stop audio");
									if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE)) { // barrido automático
										//TInterpreterConstants.semaforo.release();
										changeCell = true;
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} else { //Sound not playing
								TInterpreterConstants.audioMp3 = new TInterpreterMp3Sound(
										cell.getSoundPath());
								try {
									TInterpreterConstants.semaforoAudio.acquire();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								TInterpreterConstants.audioMp3.TPlay();
								System.out.println("Play audio");
							}
						}
					} else { //WAV
						/*
						 * ADRIAN: If there is a music playing it will be
						 * stopped with one click otherwise a sound will be
						 * played
						 */
						if (TInterpreterConstants.audio == null) { //Audio not playing, first time playing.
							TInterpreterConstants.audio = new TInterpreterWavSound(
									cell.getSoundPath());
							try {
								TInterpreterConstants.semaforoAudio.acquire();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							TInterpreterConstants.audio.start();
							System.out.println("Play sound first time");
						} else { 
							if (TInterpreterConstants.audio.isAlive()) { //Sound already playing
								TInterpreterConstants.audio.interrupt();
								try {
									TInterpreterConstants.semaforoAudio.release();
									if (TInterpreter.returnMouseMode().equals( //Trying to change the cell
											TInterpreterConstants.AUTOMATIC_SCANNING_MODE)) { // barrido automático
										//TInterpreterConstants.semaforo.release();
										changeCell = true;
									}
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} else {
								TInterpreterConstants.audio = new TInterpreterWavSound( //Sound not playing
										cell.getSoundPath());
								try {
									TInterpreterConstants.semaforoAudio.acquire();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								TInterpreterConstants.audio.start();
							}
						}
					}
				}

				if (cell.getVideoPath() != null) {

					/* [ADRIAN] If there is an audio playing it will be stopped. */
					// TODO Semaphores must be stopped
					if (TInterpreterConstants.audio != null)
						if (TInterpreterConstants.audio.isAlive())
							TInterpreterConstants.audio.interrupt();
					if (TInterpreterConstants.audioMp3 != null)
						if (TInterpreterConstants.audioMp3.TIsAlive())
							TInterpreterConstants.audioMp3.TStop();
					if (TInterpreterConstants.alternativeAudio != null)
						if (TInterpreterConstants.alternativeAudio.isAlive())
							TInterpreterConstants.alternativeAudio.interrupt();
					if (TInterpreterConstants.alternativeAudioMp3 != null)
						if (TInterpreterConstants.alternativeAudioMp3
								.TIsAlive())
							TInterpreterConstants.alternativeAudioMp3.TStop();

					Point point = cell.getLocationOnScreen();
					cell.setXVideo(point.x - 5);
					cell.setYVideo(point.y - 54);
					try {
						TInterpreterVideo video = new TInterpreterVideo(
								cell.getVideoPath(), cell.getXVideo(),
								cell.getYVideo(), cell.getWidth(),
								cell.getHeight(), interpreter);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (cell.getVideoURL() != null) {

					/* [ADRIAN] If there is an audio playing it will be stopped */
					// TODO Semaphores must be stopped
					if (TInterpreterConstants.audio != null)
						if (TInterpreterConstants.audio.isAlive())
							TInterpreterConstants.audio.interrupt();
					if (TInterpreterConstants.audioMp3 != null)
						if (TInterpreterConstants.audioMp3.TIsAlive())
							TInterpreterConstants.audioMp3.TStop();
					if (TInterpreterConstants.alternativeAudio != null)
						if (TInterpreterConstants.alternativeAudio.isAlive())
							TInterpreterConstants.alternativeAudio.interrupt();
					if (TInterpreterConstants.alternativeAudioMp3 != null)
						if (TInterpreterConstants.alternativeAudioMp3
								.TIsAlive())
							TInterpreterConstants.alternativeAudioMp3.TStop();

					Point point = cell.getLocationOnScreen();
					cell.setXVideo(point.x - 5);
					cell.setYVideo(point.y - 54);
					try {
						TInterpreterVideo video = new TInterpreterVideo(
								cell.getVideoURL(), cell.getXVideo(),
								cell.getYVideo(), cell.getWidth(),
								cell.getHeight(), interpreter);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				if (cell.getTextAreaToSend() != null) {
					TInterpreterTextArea textArea = TInterpreter
							.getCurrentBoard().getTextAreaByName(
									cell.getTextAreaToSend());

					if (textArea != null) {
						String originalText = textArea.getText();

						textArea.setText(cell.getTextToSend());

						interpreter.interpretArea.paintImmediately(0, 0,
								interpreter.interpretArea.getWidth(),
								interpreter.interpretArea.getHeight());

						waiting w = new waiting(cell.getTimeSending());
						w.start();
						try {
							w.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						textArea.setText(originalText);
						TInterpreter.interpretArea.removeAll();
						// No vuelvo al estado inicial para que la celda que
						// env�a texto permanezca seleccionada
						TInterpreter.getCurrentBoard().paintBoard(
								TInterpreter.interpretArea, false);
						TInterpreter.interpretArea.repaint();
					}
				}

				if (cell.getBoardToGo() != null) {

					TInterpreterConstants.currentBoard = interpreter
							.getProject().getBoard(cell.getBoardToGo());
					TInterpreterConstants.countRun = 0;
					TInterpreterConstants.boardOrderedCells = TInterpreterConstants.currentBoard
							.getOrderedCellListNames();
					interpreter.getProject().setPositionCellToReturn(0);

					interpreter.getProject().setBoardToReturn(
							interpreter.getProject().getCurrentBoard());
					interpreter.getProject().setCellToReturn(
							(((TInterpreterCell) arg0.getSource()).getName()));

					interpreter.getProject().getPositionCellToReturnByName(
							interpreter.getProject().getBoardToReturn(),
							interpreter.getProject().getCellToReturn());

					// Repinta el tablero, para que se quede en el estado
					// inicial
					interpreter.repaintCurrentBoard(false);

					interpreter.changeBoard(cell.getBoardToGo());
					interpreter.getProject().setBoardChanged(true);
					interpreter.getProject().setBoardToGo(cell.getBoardToGo());

					interpreter.getProject().setCurrentBoard(
							cell.getBoardToGo());

					if (TInterpreter.returnMouseMode().equals(
							TInterpreterConstants.MANUAL_SCANNING_MODE)) {
						TInterpreter.boardListener.click();
					}
				}

				if (cell.getCommand() != null) {
					try {
						Process p = Runtime.getRuntime()
								.exec(cell.getCommand());
						try {
							// Waiting for the end of the call to environment
							// control
							p.waitFor();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println(e.getCause());
					}
				}

				
				  if(TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE) && changeCell){ // barrido automatico try {
					  try{
						  TInterpreterConstants.semaforo.release(); 
					  } catch (InterruptedException e) { 
						  e.printStackTrace(); 
					  }
				  }
				 
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

		if ((TInterpreter.run == 1)) {
			
			if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE)) { // barrido automático
				try {
						TInterpreterConstants.semaforo.acquire();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			TInterpreterCell cell = (TInterpreterCell) arg0.getSource();
			// Default border attributes
			Color colorLine = Color.red;
			int widthLine = 1;
			
			if (cell.getAlternativeBorderSize() != 0) {
				cell.setBorderPainted(true);
				colorLine = cell.getAlternativeBorderColor();
				widthLine = cell.getAlternativeBorderSize();
			}

			Border thickBorder = new LineBorder(colorLine, widthLine);
			cell.setBorder(thickBorder);

			if (cell.getAlternativeIcon() != null) {
				cell.setIcon(cell.getAlternativeIcon());
			}

			/* [ADRIAN]: Adding alternative sound */
			if (cell.getAlternativeSoundPath() != null) {

				String extension = TFileUtils.getExtension(cell
						.getAlternativeSoundPath());
				if (extension.equals("mp3")) {
					try {
						TInterpreterConstants.semaforoAudio.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TInterpreterConstants.alternativeAudioMp3 = new TInterpreterMp3Sound(
							cell.getAlternativeSoundPath());
					TInterpreterConstants.alternativeAudioMp3.TPlay();
					System.out.println("Play alternative audio");
					// TInterpreterConstants.audioMp3.TJoin();
				} else {
					try {
						TInterpreterConstants.semaforoAudio.acquire();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					TInterpreterConstants.alternativeAudio = new TInterpreterWavSound(
							cell.getAlternativeSoundPath());
					TInterpreterConstants.alternativeAudio.start();
					System.out.println("Play alternative audio");
					/*
					 * try { TInterpreterConstants.audio.join(); } catch
					 * (InterruptedException e) { e.printStackTrace();
					 * System.out.println(
					 * "Error al reproducir el sonido alternativo de la celda");
					 * }
					 */
				}
			}
		
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

		if ((TInterpreter.run == 1)) {

			TInterpreterCell cell = (TInterpreterCell) arg0.getSource();

			// Sets the original icon
			cell.setIcon(cell.getDefaultIcon());

			/*
			 * ADRIAN: If there is an alternative audio playing it will be
			 * stopped to play another audio file
			 */

			if (cell.getAlternativeSoundPath() != null) {

				String extension = TFileUtils
						.getExtension(cell.alternativeSoundPath);
				if (extension.equals("mp3")) {
					if (TInterpreterConstants.alternativeAudioMp3.TIsAlive()) {
						TInterpreterConstants.alternativeAudioMp3.TStop();
						try {
							TInterpreterConstants.semaforoAudio.release();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} else {
					if (TInterpreterConstants.alternativeAudio.isAlive()) {
						TInterpreterConstants.alternativeAudio.interrupt();
						try {
							TInterpreterConstants.semaforoAudio.release();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}

			/* [ADRIAN] If there is a sound playing it will be stopped */
			if (cell.getSoundPath() != null) {

				String extension = TFileUtils.getExtension(cell.soundPath);
				if (extension.equals("mp3")) {
					if(TInterpreterConstants.audioMp3 != null){
						if (TInterpreterConstants.audioMp3.TIsAlive()) {
							TInterpreterConstants.audioMp3.TStop();
							try {
								TInterpreterConstants.semaforoAudio.release();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				} else {
					if(TInterpreterConstants.audio != null){
						if (TInterpreterConstants.audio.isAlive()) {
							TInterpreterConstants.audio.interrupt();
							try {
								TInterpreterConstants.semaforoAudio.release();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

			if (cell.isTransparentBorder()) {
				cell.setBorderPainted(false);
			} else {
				cell.setBorder(new LineBorder(cell.getBorderColor(), (int) cell
						.getBorderSize()));
			}

			if (cell.isTransparentBackground()) {
				cell.setFocusPainted(false);
				cell.setContentAreaFilled(false);
			} else if (cell.getBackground() != null) {
				cell.setFocusPainted(true);
				cell.setBackground(cell.getBackground());
			}
		
			if (TInterpreter.returnMouseMode().equals(TInterpreterConstants.AUTOMATIC_SCANNING_MODE)) { // barrido automático
				try {
						TInterpreterConstants.semaforo.release();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void mousePressed(MouseEvent arg0) {

	}

	public void mouseReleased(MouseEvent arg0) {

	}

	class waiting extends Thread {

		private int secs;

		public waiting(int segundos) {
			secs = segundos;
		}

		public void run() {
			try {
				Thread.sleep(secs * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
