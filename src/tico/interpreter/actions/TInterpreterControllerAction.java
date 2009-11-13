/*
 * File: TInterpreterControllerAction.java
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
import java.awt.Color;

import java.awt.FlowLayout;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;



import tico.board.TBoardConstants;

import tico.components.TButton;
import tico.components.TDialog;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterActionSet;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.threads.TThreads;

/**
 * 
 * @author Antonio Rodriguez
 *
 */
public class TInterpreterControllerAction extends TInterpreterAbstractAction implements ActionListener, Runnable{
	Robot gest;
	
	
	TButton Stop;
	TButton Close;
	TButton Return;
	TButton Read;
	TButton Undo;
	
	
	public void AsignAttributesFinalWindow ()
	{
		TInterpreterConstants.FinalWindow.setUndecorated(true);
		
		interpreter.BackPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
			
			
		Close = new TButton(interpreter.getActionSet().getAction(TInterpreterActionSet.INTERPRETER_EXIT_ACTION));
		ImageIcon imageExit=TResourceManager.getImageIcon("logout.png"); 
		Close.setVerticalTextPosition(SwingConstants.BOTTOM);
		Close.setHorizontalTextPosition(SwingConstants.CENTER);
		Close.setFocusPainted(false);
		Close.setIcon(imageExit);
		Close.setBackground(Color.green);
		Close.setSize(250,250);
		
		
		Undo = new TButton(interpreter.getActionSet().getAction(TInterpreterActionSet.INTERPRETER_UNDO));
		ImageIcon imageUndo=TResourceManager.getImageIcon("undo.png"); 
		Undo.setVerticalTextPosition(SwingConstants.BOTTOM);
		Undo.setHorizontalTextPosition(SwingConstants.CENTER);
		Undo.setFocusPainted(false);
		Undo.setIcon(imageUndo);
		Undo.setBackground(Color.green);
		Undo.setSize(250,250);
		
			
		Read = new TButton(interpreter.getActionSet().getAction(TInterpreterActionSet.INTERPRETER_READ));
		ImageIcon IRead= TResourceManager.getImageIcon("audio.png");
		Read.setVerticalTextPosition(SwingConstants.BOTTOM);
		Read.setHorizontalTextPosition(SwingConstants.CENTER);
		Read.setFocusPainted(false);
		Read.setIcon(IRead);
		Read.setBackground(Color.green);
		Read.setSize(250,250);
			
					
		Return = new TButton(interpreter.getActionSet().getAction(TInterpreterActionSet.INTERPRETER_RUN));
		ImageIcon IReturn= TResourceManager.getImageIcon("back.png");
		Return.setVerticalTextPosition(SwingConstants.BOTTOM);
		Return.setHorizontalTextPosition(SwingConstants.CENTER);
		Return.setFocusPainted(false);
		Return.setIcon(IReturn);
		Return.setBackground(Color.green);
		Return.setSize(250,250);
		
		
		Stop = new TButton(interpreter.getActionSet().getAction(TInterpreterActionSet.INTERPRETER_STOP));
		ImageIcon IStop=TResourceManager.getImageIcon("close.png");
		Stop.setVerticalTextPosition(SwingConstants.BOTTOM);
		Stop.setHorizontalTextPosition(SwingConstants.CENTER);
		Stop.setFocusPainted(false);
		Stop.setIcon(IStop);
		Stop.setBackground(Color.green);
		Stop.setSize(250,250);
			
			
			
		Stop.addMouseListener(new TInterpreterMouseActions(Stop));
		Undo.addMouseListener(new TInterpreterMouseActions(Undo));
		Read.addMouseListener(new TInterpreterMouseActions(Read));
		Return.addMouseListener(new TInterpreterMouseActions(Return));
		Close.addMouseListener(new TInterpreterMouseActions(Close));
		//Add Buttons :)
		interpreter.BackPanel.setBackground(Color.blue);
		interpreter.BackPanel.add(Close);
		interpreter.BackPanel.add(Undo);
		interpreter.BackPanel.add(Read);
		interpreter.BackPanel.add(Return);
		interpreter.BackPanel.add(Stop);
		
		//Add Panel
			
		interpreter.BackPanel.setBackground(Color.white);
		TInterpreterConstants.FinalWindow.setContentPane(interpreter.BackPanel);
			 
		//Window Attributes
		TInterpreterConstants.FinalWindow.setLocation(850,50);
		TInterpreterConstants.FinalWindow.setSize(110,500);
		TInterpreterConstants.FinalWindow.setResizable(false);
		TInterpreterConstants.FinalWindow.setVisible(true);
	
	}
	
	public TInterpreterControllerAction (TInterpreter interpreter) {
		super(interpreter,TLanguage.getString("TInterpreterControllerAction.NAME"));

	}
	
	public void actionPerformed(ActionEvent e)
	{// TODO Auto-generated method stub
	
	interpreter=getInterpreter();

	TInterpreterConstants.WindowController=1;

	
	
	if (interpreter.run==1){	
		//stop all
		interpreter.run=0;
		//Kill All Threads
		interpreter.BrowseGrid=0;
		TInterpreterConstants.SecondClic=0;
		TInterpreterConstants.ClicReleased=0;
		TInterpreterRun.fin=false;
		
		//if music on => stop it;
		interpreter.TStart.stop();
		
		TInterpreterConstants.musicOn=0;
		
		TInterpreterConstants.sendTextOn=0;
		
		if(TInterpreterConstants.map2!=null)
		TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
		
		
		interpreter.update(interpreter.getGraphics());
			
		if(TInterpreterConstants.vmap!=null)
		{	if (TInterpreterConstants.Original_Color!=null){
			TBoardConstants.setBorderColor(TInterpreterConstants.vmap,TInterpreterConstants.Original_Color);
			TBoardConstants.setLineWidth(TInterpreterConstants.vmap,TInterpreterConstants.Original_border);
			}
		}
		if ( TInterpreterConstants.audio != null)
		{
			 TInterpreterConstants.audio.stop();
		}
	interpreter.update(interpreter.getGraphics());
	}
	
	TInterpreter.TStart =new TThreads(this);
	TInterpreter.TStart.start();
	
}
	public void run() {
		// TODO Auto-generated method stub
		Point p;
		int alt;
		int anch;
		interpreter=getInterpreter();
		TInterpreterConstants.FinalWindow= new TDialog(interpreter,false);
		AsignAttributesFinalWindow();
		
		interpreter.TIntepreterChangeCursor();
		
		try {
			gest=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gest.setAutoDelay(TInterpreterConstants.interpreterDelay);
		
		//Cambiar el mouse
				
		while (TInterpreterConstants.WindowController==1)
			{	
				//Move to first Button;
				
				p=Close.getLocation();
				alt=Close.getHeight();
				anch=Close.getWidth();
				//Calculate the relative Position.
				SwingUtilities.convertPointToScreen(p,interpreter.BackPanel);
				gest.mouseMove((int)p.getX()+anch/2,(int)p.getY()+alt/2);
				
				
				p=Undo.getLocation();
				alt=Undo.getHeight();
				anch=Undo.getWidth();
				//Calculate the relative Position.
				SwingUtilities.convertPointToScreen(p,interpreter.BackPanel);
				gest.mouseMove((int)p.getX()+anch/2,(int)p.getY()+alt/2);
				if (TInterpreterConstants.WindowController==0)
				{	
					TInterpreterConstants.FinalWindow.setVisible(false);
					return;
				
				}
				
				//Move To Second Button
				p=Read.getLocation();
				alt=Read.getHeight();
				anch=Read.getWidth();
				SwingUtilities.convertPointToScreen(p,interpreter.BackPanel);
				gest.mouseMove((int)p.getX()+anch/2,(int)p.getY()+alt/2);
				if (TInterpreterConstants.WindowController==0)
				{	
					TInterpreterConstants.FinalWindow.setVisible(false);
					return;
					
				}
				//Move to Third Button
				p=Return.getLocation();
				alt=Return.getHeight();
				anch=Return.getWidth();
				SwingUtilities.convertPointToScreen(p,interpreter.BackPanel);
				gest.mouseMove((int)p.getX()+anch/2,(int)p.getY()+alt/2);
				if (TInterpreterConstants.WindowController==0)
				{	
					TInterpreterConstants.FinalWindow.setVisible(false);
					return;
					
				}
				//Move To Four Button
				p=Stop.getLocation();
				alt=Stop.getHeight();
				anch=Stop.getWidth();
				SwingUtilities.convertPointToScreen(p,interpreter.BackPanel);
				gest.mouseMove((int)p.getX()+anch/2,(int)p.getY()+alt/2);
				if (TInterpreterConstants.WindowController==0)
				{	
					TInterpreterConstants.FinalWindow.setVisible(false);
					return;
					
				}
			}
	
	}
	

}
