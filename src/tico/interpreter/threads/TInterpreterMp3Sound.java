/*
 * File: TInterpreterMp3Sound.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Antonio Rodriguez
 * 
 * Date: Aug 22, 2007
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

package tico.interpreter.threads;

import javazoom.jl.player.*;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.spi.AudioFileReader;
import javax.swing.CellEditor;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.ibex.nestedvm.Interpreter;
import org.tritonus.share.sampled.AudioUtils;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.jkernel.BackgroundDownloader;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.components.TInterpreterCell;

/**
 * The audio thread (MP3 format)
 * 
 * @author shaines (used by Antonio Rodríguez)
 * @version 1.0 Aug 22, 2007
 */

public class TInterpreterMp3Sound{

	private Player player;

	private InputStream is;

	private TPlayerThread pt;
	
	/** Creates a new instance of MP3Player */
	public TInterpreterMp3Sound(String filename) {
		try {
			// Create an InputStream to the file
			is = new FileInputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* [ADRIAN] trying to keep playing the alternative sound */
	public void TPlay() {
		try {
			player = new Player(is);
			pt = new TPlayerThread();
			pt.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void TJoin() {
		try {
			pt.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void TStop() {
		try {
			pt.stop();
		} catch (Exception e) {

		}

	}
	
	/* [ADRIAN]: It returns a boolean with thread's state */
	public boolean TIsAlive(){
		return pt.isAlive();
	}

	class TPlayerThread extends Thread {
		public void run() {
			try {
				player.play();
				boolean stop = false;
				while(!stop){
					if(player.isComplete()){
						TInterpreterConstants.semaforo.release();
						stop = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}