/*
 * File: TSoundChooser.java
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

package tico.interpreter;

import java.awt.Color;

import javax.swing.ImageIcon;


import org.jgraph.graph.AttributeMap;


import tico.components.TDialog;

import tico.interpreter.board.TInterpreterTextAreaRenderer;
import tico.interpreter.threads.TInterpreterSound;
import tico.interpreter.threads.TMp3Sound;

/**
 * 
 * @author Antonio Rodriguez
 *
 */
public class TInterpreterConstants {
	
	
	//Constants avalaibles to send text to an Area Text
	
	public static TInterpreterTextAreaRenderer textRender;
	public static int sendTextOn=0;
	public static int positionReceiver=0;
	public static AttributeMap map2;
	public static String lastName;
	public static int time;
	public static String textToSend;
	public static int Tejex=0;
	public static int Tejey=0;
	public static int Talt=0;
	public static int Tanch=0;
	
	
	//Constants avalaibles to play sound when the mouse is released in a cell

	public static int musicOn=0;
	public static String ruta;
	public static TMp3Sound audioMp3=null;
	public static TInterpreterSound audio=null;
	
	//To Resize the cell and update the cell
	public static int Flag_Resize_Border=0;
	public static int Original_border=0;
	public static int New_Border;
	public static AttributeMap vmap;
	public static Color Original_Color;
	public static int ejex=0;
	public static int ejey=0;
	public static int alt=0;
	public static int anch=0;
	
	
	
	//sincronize cell Browsing
	public static int SecondClic=0;
	public static int ClicReleased=0;
	
	//Window Finale
	public static int WindowController=0;
	public static TDialog FinalWindow=null;
	
	public static String interpreterCursor=null;
	public static String nameCursor=null;
	public static int interpreterDelay=1000;
	public static int interpreterAcumulatedCells=5;
	public static int isGrid=0;
	public static int undo=0;
	public static ImageIcon OriginalImage=null;
	public static int environmentOn=0;
	
}
