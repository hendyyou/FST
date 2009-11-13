/*
 * File: TEnvironmentExecution.java
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
package tico.environment;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class TEnvironmentExecution {

	private String Executable=null;
	private String Arguments=null;
	public Robot writer=null;
	
	public TEnvironmentExecution(String Command)
	{
		
		String[] Comand=Command.split(" <> ");
		
		
		Executable=Comand[0];
		Arguments=Comand[1];
		
		if (isPerseoExe()) {
			executePerseo(Arguments);
		}
	}
	
	private void executePerseo(String arguments2) {
		// TODO Auto-generated method stub
		//System.out.println("EJECUTANDO CON PERSEO"+ arguments2);
		try {
			Runtime.getRuntime().exec(Executable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writePerseoArguments();
	}

	private void writePerseoArguments() {
		// TODO Auto-generated method stub
		Robot writer=null;
		
		try {
			writer =new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.delay(3000);
		writer.keyPress(KeyEvent.VK_E);
		
		TrateCharacter(Arguments.substring(1,2));
		
		TrateCharacter(Arguments.substring(2,3));
		writer.keyPress(KeyEvent.VK_B);
		TrateCharacter(Arguments.substring(4,5));
		TrateCharacter(Arguments.substring(5,6));
		
		
		
		writer.keyPress(KeyEvent.VK_ALT);
		writer.keyPress(KeyEvent.VK_TAB);
		writer.keyRelease(KeyEvent.VK_ALT);
		
		
		
	}

	private void TrateCharacter(String c) {
		// TODO Auto-generated method stub
		
		Robot writer2 =null;
		try {
			writer2 =new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(c.equals("0"))
			writer2.keyPress(KeyEvent.VK_NUMPAD0);
		if(c.equals("1"))
			writer2.keyPress(KeyEvent.VK_NUMPAD1);
		if(c.equals("2"))
			writer2.keyPress(KeyEvent.VK_NUMPAD2);
		if(c.equals("3"))
			writer2.keyPress(KeyEvent.VK_NUMPAD3);
		if(c.equals("4"))
			writer2.keyPress(KeyEvent.VK_NUMPAD4);
		if(c.equals("5"))
			writer2.keyPress(KeyEvent.VK_NUMPAD5);
		if(c.equals("6"))
			writer2.keyPress(KeyEvent.VK_NUMPAD6);
		if(c.equals("7"))
			writer2.keyPress(KeyEvent.VK_NUMPAD7);
		if(c.equals("8"))
			writer2.keyPress(KeyEvent.VK_NUMPAD8);
		if(c.equals("9"))
			writer2.keyPress(KeyEvent.VK_NUMPAD9);
	}	

	public boolean isPerseoExe(){
		if (Executable.contains("PerseoMini.exe"))
			return true;
		else	
			return false;
		
	}
}
