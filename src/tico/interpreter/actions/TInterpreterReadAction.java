/*
 * File: TInterpreterReadAction.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * * Authors: Antonio Rodríguez
 * 
 * Date:	May-2006 
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

package tico.interpreter.actions;

import java.awt.event.ActionEvent;

import tico.components.resources.TFileUtils;
import tico.configuration.TLanguage;
import tico.interpreter.TInterpreter;
import tico.interpreter.components.TInterpreterAccumulatedCell;
import tico.interpreter.threads.TInterpreterMp3Sound;
import tico.interpreter.threads.TInterpreterWavSound;

/**
 * 
 * @author Antonio Rodríguez
 *
 */

public class TInterpreterReadAction extends TInterpreterAbstractAction
{
	TInterpreterWavSound nAudio=null;
	TInterpreterMp3Sound nMp3Audio=null;


	public TInterpreterReadAction(TInterpreter interpreter) {
		super(interpreter, TLanguage.getString("TInterpreterRead.NAME"));		
	}
	
	public void actionPerformed(ActionEvent e) {

		interpreter = getInterpreter();
			
			if (nMp3Audio!=null){					
				nMp3Audio.TJoin();
			}	
			if (nAudio!=null){					
				try {
				 nAudio.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
			int index=0;
			for (index=0;index < TInterpreter.accumulatedCellsList.size();index++)
			{			
				String nameSound = ((TInterpreterAccumulatedCell) (TInterpreter.accumulatedCellsList.get(index))).getSound();
				
				if (nameSound!=null)
				{
					String extension = TFileUtils.getExtension(nameSound);
					
					if (extension.equals("mp3"))
					{ nMp3Audio=new TInterpreterMp3Sound(nameSound);
					  nMp3Audio.TPlay();
					  nMp3Audio.TJoin();
					}
					else 
					{ nAudio=new TInterpreterWavSound(nameSound);
					  nAudio.start();
					  try {
						nAudio.join();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					}
				}
			}
	}
		
}

