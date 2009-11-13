/*
 * File: TMp3SOund.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Antonio Rodriguez
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

package tico.interpreter.threads;

// Import the JLayer classes
import javazoom.jl.player.*;

// Import the Java classes
import java.io.*;

/**
 * 
 * @author shaines
 */
public class TMp3Sound {

	private Player player;

	private InputStream is;

	private TPlayerThread pt;

	/** Creates a new instance of MP3Player */
	public TMp3Sound(String filename) {
		try {
			// Create an InputStream to the file
			is = new FileInputStream(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	class TPlayerThread extends Thread {
		public void run() {
			try {
				player.play();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

} // End class
