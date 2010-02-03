/*
 * File: TInterpreterConstants.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo MuÃ±oz
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

package tico.interpreter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

import org.jgraph.graph.AttributeMap;

//import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import tico.components.TDialog;
import tico.interpreter.components.TInterpreterCell;
import tico.interpreter.dialogs.TAboutDialogInterpreter;
import tico.interpreter.threads.TInterpreterWavSound;
import tico.interpreter.threads.TInterpreterMp3Sound;

/**
 * 
 * @author Antonio Rodriguez
 *
 */
public class TInterpreterConstants {
	
	
	//Constants avalaibles to send text to an Area Text
	
	public final static Color BACKGROUND_COLOR = Color.WHITE;
	
	public final static Color BORDER_COLOR = Color.BLACK;
	
	public final static Color SELECTED_BORDER_COLOR = Color.RED;
	
	public final static int BORDER_SIZE = 1;
	
	public final static int SELECTED_BORDER_SIZE = 4;

	public static int sendTextOn=0;
	public static String lastName;
	public static int time;
	public static String textToSend;
	public static int Tejex=0;
	public static int Tejey=0;
	public static int Talt=0;
	public static int Tanch=0;
	
	//Semáforo que controla la sincronización entre el barrido automático y las acciones de las celdad
	//public static Semaphore semaforo = new Semaphore(1,true);
	public static TSemaphore semaforo = new TSemaphore(1);
	
	//Semáforo que controla la sincronización entre el barrido del controlador y la lectura de las celdas
	//public static Semaphore semaforoRead = new Semaphore(1,true);
	//Constants avalaibles to play sound when the mouse is released in a cell

	public static int musicOn=0;
	public static String ruta;
	public static TInterpreterMp3Sound audioMp3=null;
	public static TInterpreterWavSound audio=null;

	
	//To Resize the cell and update the cell
	public static int Flag_Resize_Border=0;
	public int Original_border=0;
	public static int New_Border;
	public static AttributeMap vmap;
	public Color Original_Color;
	public static int ejex=0;
	public static int ejey=0;
	public static int alt=0;
	public static int anch=0;
	
	//sincronize cell Browsing
	public static int SecondClic=0;
	public static int ClicReleased=0;
	
	public static String interpreterCursor=null;
	public static String nameCursor=null;
	public static int interpreterDelay=1000;
	public static int interpreterAcumulatedCells=5;
	public static int isGrid=0;
	public static int undo=0;
	
	public static int environmentOn=0;
	public static TInterpreter interpreter;
	public static Object textRender;
	
	public String boardtogo;
	public String currentBoard;
	public static TInterpreterBoard tableroActual;
	public static int countRun = 0;
	public static ArrayList<String> boardOrderedCells;
	public static int x,y;
	
	public Color changeColor= Color.red;
	public int lineChangeWidth=4;
	public TInterpreter tinterpreter;
	public Color Original_Border_Color;
	public boolean accumulated;
	public TInterpreterCell cell;
	
	//public int segs;
	//public String textareatosend;
	//public TInterpreterBoard board;
	//public String textSend;
	public String soundFile;
	
	public ImageIcon AlternativeIcon;
	public boolean WithAlternativeIcon = false;
	public ImageIcon OriginalIcon;
	public String videoFile;
	public String videoName;
	
	public boolean transparente= true;
	public boolean transparenteBorder = true;
	
	public String boardToReturn;
	public String cellToReturn;
//board
	
}
