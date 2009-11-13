/*
 * File: TInterpreterReadAccumulated.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * * Authors: Antonio Rodríguez
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

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;


import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;


import tico.board.TBoardConstants;
import tico.components.resources.TFileUtils;
import tico.configuration.TLanguage;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterConstants;

import tico.interpreter.threads.TInterpreterSound;
import tico.interpreter.threads.TMp3Sound;

public class TInterpreterReadAccumulatedArea extends TInterpreterAbstractAction
{
	TInterpreterSound nAudio=null;
	TMp3Sound nMp3Audio=null;


	public TInterpreterReadAccumulatedArea(TInterpreter interpreter) {
		super(interpreter, TLanguage.getString("TInterpreterRead.NAME"));
			
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//STOP ALL! ONLY FOR READING
		interpreter=getInterpreter();
		interpreter.TInterpreterRestoreCursor();
		
		if (interpreter.run==1){
			
			interpreter.run=0;
			interpreter.BrowseGrid=0;
			TInterpreterConstants.SecondClic=0;
			TInterpreterConstants.ClicReleased=0;
			TInterpreterRun.fin=false;
			interpreter.TStart.stop();
			TInterpreterConstants.musicOn=0;
			
			//Recover Original Text Area
			TInterpreterConstants.sendTextOn=0;
			
			if(TInterpreterConstants.map2!=null)
			TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
			
		
			//Recover Original Cell
			if(TInterpreterConstants.vmap!=null)
			{	if (TInterpreterConstants.Original_Color!=null){
				TBoardConstants.setBorderColor(TInterpreterConstants.vmap,TInterpreterConstants.Original_Color);
				TBoardConstants.setLineWidth(TInterpreterConstants.vmap,TInterpreterConstants.Original_border);
				}
			}
			if(TInterpreterConstants.textRender!=null)
				TInterpreterConstants.textRender.paint(interpreter.getGraphics());
			interpreter.update(interpreter.getGraphics());
			//Stop Current Sound
			if ( TInterpreterConstants.audio != null)
			{
				TInterpreterConstants.audio.stop();
			}
			
		}
		
		if (TInterpreterConstants.WindowController==1)
		{//hide it;
			
			TInterpreterConstants.WindowController=0;
			TInterpreterConstants.FinalWindow.setVisible(false);
		
		}
		
		
		int index=0;
		for (index=0;index< TInterpreter.AccumulatedList.size();index++)
		{
			CellView vista=(CellView)TInterpreter.AccumulatedList.get(index);
			AttributeMap map=vista.getAllAttributes();
			String NameSound = TBoardConstants.getSoundFile(map);
			//reproduce the sound if it is != null
		
			
			
			if (NameSound!=null)
			{
				if (nMp3Audio!=null){
					
					nMp3Audio.TJoin();
				}	
				if (nAudio!=null){
					
					try {
					 nAudio.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			String extension = TFileUtils.getExtension(NameSound);
			if (extension.equals("mp3"))
			{ nMp3Audio=new TMp3Sound(NameSound);
			  nMp3Audio.TPlay();
			  nAudio=null;
			}
			else 
			{ nAudio=new TInterpreterSound(NameSound);
			  nAudio.start();
			  nMp3Audio=null;
			}
			}//end if;
		}//end for;
		
		
}
		
}

