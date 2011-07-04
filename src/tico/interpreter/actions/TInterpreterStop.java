/*
 * File: TInterpreterStop.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Antonio Rodríguez
 * 
 * Date:	May-2006 
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
import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.ActionEvent;

import tico.board.TBoardConstants;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.interpreter.*;


public class TInterpreterStop extends TInterpreterAbstractAction

{ Robot gest;

	public  TInterpreterStop (TInterpreter interpreter){
		
		super(interpreter,TLanguage.getString("TInterpreterStopAction.NAME"),TResourceManager.getImageIcon("stopON.gif"));
	}
	
	public void TStopAll ()
	{
		interpreter=getInterpreter();
		interpreter.TInterpreterRestoreCursor();
		interpreter.BoardChange=0;
		if (interpreter.run==1){
			
			interpreter.run=0;
			//Kill All Threads
			interpreter.BrowseGrid=0;
			TInterpreterConstants.SecondClic=0;
			TInterpreterConstants.ClicReleased=0;
			TInterpreterRun.fin=false;
			
			//if music on => stop it;
			if(TInterpreterConstants.audioMp3!=null)
			{
				TInterpreterConstants.audioMp3.TStop();
			}
			interpreter.TStart.stop();
			
			if (TInterpreterMarqueeHandler.render!=null)
			{
				TInterpreterMarqueeHandler.render.mouseOver=0;
			}
			TInterpreterConstants.musicOn=0;
			TInterpreterConstants.sendTextOn=0;
			
			if(TInterpreterConstants.map2!=null)
			TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
			
			
				
			if(TInterpreterConstants.vmap!=null)
			{	if (TInterpreterConstants.Original_Color!=null){
				TBoardConstants.setBorderColor(TInterpreterConstants.vmap,TInterpreterConstants.Original_Color);
				TBoardConstants.setLineWidth(TInterpreterConstants.vmap,TInterpreterConstants.Original_border);
				}
			}
			//Actualize Screen
			Graphics g = interpreter.getGraphics();	
			g.setClip(TInterpreterConstants.ejex,TInterpreterConstants.ejey,TInterpreterConstants.anch,TInterpreterConstants.alt);
			interpreter.update(g);
			
				//Stop Current Sound
			
			if ( TInterpreterConstants.audio != null)
			{	TInterpreterConstants.audio.stop();
			}			
			
		}
		
		if (TInterpreterConstants.WindowController==1)
		{//hide it;
			
			TInterpreterConstants.WindowController=0;
			TInterpreterConstants.FinalWindow.setVisible(false);
			
		}
	}
					
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			
				TStopAll();
				int posx, posy;
				posx=600;
				posy=200;

		}
	
}

	
