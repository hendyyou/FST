/*
 * File: TInterpreterRun.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * * Authors: Antonio Rodríguez
 * 
 * Date:	May-2006 
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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;

import tico.board.TBoard;
import tico.board.TBoardConstants;
import tico.board.TBoardModel;
import tico.components.resources.TFileUtils;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterConstants;
import tico.interpreter.TInterpreterMarqueeHandler;
import tico.interpreter.board.TInterpreterCellRenderer;
import tico.interpreter.threads.TInterpreterSound;
import tico.interpreter.threads.TMp3Sound;
import tico.interpreter.threads.TThreads;

public class TInterpreterRun extends TInterpreterAbstractAction implements ActionListener, Runnable {

 
	Robot Retraso;
	
	public static boolean fin=false;
	private double Tancho,Talto;
	public static TInterpreterCellRenderer Elrender=null;
	private static int accessController=0;
	public TBoard tablero;
	public CellView[] views;
	public static int i=0;
	public static boolean fina=false;
	public static int viewsPos=0;
	public static Map boardMap;
	private String BSound;
	public int actionPerformed=0;
    public int count=0;
	
	public TInterpreterRun (TInterpreter interpreter) {
		
		super(interpreter,TLanguage.getString("TInterpreterRunAction.NAME"),TResourceManager.getImageIcon("run.gif"));
		
	}
	
	/*Allocate The Mouse cursor in the point of the cellView respect from screen*/
	
	private void TInterpreterMoveCursorToView (CellView vista)
	
	{
		double pox= vista.getBounds().getX();
		double poy= vista.getBounds().getY();
		double alto=vista.getBounds().getHeight();
		alto=alto/2;
		
		double ancho=vista.getBounds().getWidth();
		ancho=ancho/2;
		
		Point point=new Point();
		
		point.setLocation(pox,poy);//el punto es respecto al tablero
	
		SwingUtilities.convertPointToScreen(point,interpreter.interpretArea);
	
		pox=point.getX();
		poy=point.getY();
		
		interpreter.InterpreterRobot.mouseMove((int)pox+(int)ancho,(int)poy+(int)alto);
		
	}
	
	//Browsing!
	
	private void DrawRectangle (CellView vista,int flag,Color color,int grosor)
	
	{		
		double pox= vista.getBounds().getX();
		double poy= vista.getBounds().getY();		
		double anch=vista.getBounds().getWidth();
		double alt=vista.getBounds().getHeight()+10;
		Point point=new Point();
		point.setLocation(pox,poy);//el punto es respecto al tablero
	
		SwingUtilities.convertPointToScreen(point,interpreter.interpretArea);
	
		pox=point.getX();
		
		poy=point.getY();
		
	    
		if (flag==0)
		{	//Draw Column
			TRectangle algo = new TRectangle((int)pox,(int)poy,(int)anch+10,(int)Talto+10,color,grosor);
			algo.paint(interpreter.getGraphics());
		}
		if (flag==1)
		{//Draw Row
			
			TRectangle algo = new TRectangle((int)pox,(int)poy,(int)Tancho+10,(int)alt,color,grosor);
			algo.paint(interpreter.getGraphics());			
		}
		if (flag==2){	
		
			TRectangle algo = new TRectangle((int)pox,(int)poy,(int)Tancho+10,(int)Talto+10,color,grosor);
			algo.paint(interpreter.getGraphics());
		}
		
	}
	

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		interpreter=getInterpreter();
		
		
		
		if (TInterpreter.TStart!=null)
		{ 
			TInterpreter.TStart.stop();
		}
		if (interpreter.run==0){
			TInterpreter.TStart=new TThreads(this);
			TInterpreter.TStart.start();
		}//end if;
	}//end ActionPerformed
	
	
	public void setTextValues ()
	{
		double x = TBoardConstants.getBounds(TInterpreterConstants.map2).getX()-5;
		double y = TBoardConstants.getBounds(TInterpreterConstants.map2).getY()-5;
		TInterpreterConstants.Talt=(int)TBoardConstants.getBounds(TInterpreterConstants.map2).getHeight()+100;
		TInterpreterConstants.Tanch=(int)TBoardConstants.getBounds(TInterpreterConstants.map2).getWidth()+100;
		Point point=new Point();
		point.setLocation(x,y);//el punto es respecto al tablero
	
		SwingUtilities.convertPointToScreen(point,interpreter.interpretArea);
	
		x=point.getX();
		y=point.getY();
		TInterpreterConstants.Tejex=(int)x;
		TInterpreterConstants.Tejey=(int)y;
	}
	private void updateScreenText()
	{		
			Graphics g = interpreter.getGraphics();	
			
			g.setClip(TInterpreterConstants.Tejex,TInterpreterConstants.Tejey,TInterpreterConstants.Tanch,TInterpreterConstants.Talt);
			TInterpreterConstants.textRender.paint(g);
			interpreter.update(g);
		
	}

	private void InterpreterClicSound ()
	
	{
		 TInterpreterConstants.musicOn=0;
		 
		 String extension= TFileUtils.getExtension(TInterpreterConstants.ruta);
		 
		try { 
		 if (extension.equals("mp3"))
		 {
			 TInterpreterConstants.audioMp3 = new TMp3Sound (TInterpreterConstants.ruta);
			 TInterpreterConstants.audioMp3.TPlay();
			 
			 TInterpreterConstants.audioMp3.TJoin();
		 }
		 else
		 {
			
		 
		 TInterpreterConstants.audio=new TInterpreterSound(TInterpreterConstants.ruta);	
		 TInterpreterConstants.audio.start();
		 
		try {
			TInterpreterConstants.audio.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		}
		catch (Exception e)
		{
			
		}
	
				
	}
	
	private int ObtainRow (CellView[] hijos)
	{
		int longitud=hijos.length;
		AttributeMap hijo=hijos[longitud-1].getAllAttributes();
		int row= TBoardConstants.getRow(hijo);
		return row;
		
	}
	
	private int ObtainColumn (CellView[] hijos){
		int longitud=hijos.length;
		AttributeMap hijo=hijos[longitud-1].getAllAttributes();
		
		int columna = TBoardConstants.getColumn(hijo);
		return columna;
	}
	

	private void  TMoveCursorToController ()
	{
		//Disable All efects before
		if (TInterpreterMarqueeHandler.render!=null){
		 	TInterpreterMarqueeHandler.render.mouseOver=0;
		} 	
		 
			if(TInterpreterConstants.vmap!=null)
			{	if (TInterpreterConstants.Original_Color!=null){
				TBoardConstants.setBorderColor(TInterpreterConstants.vmap,TInterpreterConstants.Original_Color);
				TBoardConstants.setLineWidth(TInterpreterConstants.vmap,TInterpreterConstants.Original_border);
				}
			
		
				Graphics g = interpreter.getGraphics();	
				g.setClip(TInterpreterConstants.ejex,TInterpreterConstants.ejey,TInterpreterConstants.anch,TInterpreterConstants.alt);
				interpreter.update(g);
			}
		
		//Move Mouse to Controller
		Point point=interpreter.Controller.getLocation();
	    SwingUtilities.convertPointToScreen(point,interpreter.ControllerPane);
	    int height=interpreter.Controller.getHeight();
	    int width=interpreter.Controller.getWidth();
		double pox=point.getX();
		double poy=point.getY();
		interpreter.InterpreterRobot.mouseMove((int)pox+width/2,(int)poy+height/2);
		TInterpreterRun.i=-1;
	
		
	}
	 public int selectView (Object o,CellView[] vistas)
		 {  
		
		 	int indice=0;
		 	boolean end=false;
			 while ((indice < vistas.length) && (!end))
			 {
				 end = vistas[indice].equals(o);
				 indice++;
			 }
			 indice--;
			 return indice;
		 }

	public void run() {
		// TODO Auto-generated method stub
		interpreter.run = 1;
		//interpreter.InterpreterRobot.setAutoDelay(2000);
		interpreter.TIntepreterChangeCursor();
		if (TInterpreterConstants.WindowController==1)
		{//hide Window Controller;
			
			TInterpreterConstants.WindowController=0;
			TInterpreterConstants.FinalWindow.setVisible(false);
			
		}
     int index;
     boolean end=false;
     interpreter.InterpreterRobot.mouseMove(150,150); 
     int soundCounter=0;
     int actionPerformed=0;
     int count=0;
		while (interpreter.run==1){
    
		tablero = interpreter.getBoard();
		
		if (interpreter.getActivateBar()==1){
    	
		//ALLOCATE MOUSE TO NEXT BROWSEABLE COMPONENT (BROWSING MODE)
			 
    	 views = tablero.getGraphLayoutCache().getAllViews();
 		 boardMap = ((TBoardModel)tablero.getModel()).getAttributes();
 		
 		 BSound=TBoardConstants.getSoundFile(boardMap);
	 	if ((BSound!=null)&&(soundCounter==0)){
	 			
	 			soundCounter=1;
	 			TInterpreterConstants.ruta=BSound;
	 			InterpreterClicSound();
	 	 }
	 		
 		  ArrayList listaOrdenada=TBoardConstants.getOrderedCellList(boardMap);
 	      for (TInterpreterRun.i = 0; TInterpreterRun.i < listaOrdenada.size(); TInterpreterRun.i++)
			{ 
 	    	 
				end=false;
				index=0;
				
				while ((index < views.length) && (!end))
				{  
					if (listaOrdenada.get(TInterpreterRun.i).equals(views[index].getCell()))
							{
								viewsPos=index; end=true;
							}
					index++;
				}
				
				if (accessController==1)
				{ 	
					TMoveCursorToController();
					TInterpreterConstants.isGrid=0;
					accessController=0;
					interpreter.InterpreterRobot.delay(TInterpreterConstants.interpreterDelay);
					
				}

				if (views[viewsPos]!=null){
					//Get Attributes
					AttributeMap map=views[viewsPos].getAllAttributes();
					if ((TBoardConstants.isBrowseable(map))){
					
						//Move Cursor to corresponding cell;
						if (views[viewsPos].isLeaf())
						{
							TInterpreterConstants.isGrid=0;
						}
						else TInterpreterConstants.isGrid=1;
						
						TInterpreterMoveCursorToView(views[viewsPos]);
						actionPerformed=0;count=0;
						while ((actionPerformed==0)&&(count*1000<TInterpreterConstants.interpreterDelay))
						{
							
							count++;
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (TInterpreterConstants.sendTextOn==1)
							{   actionPerformed=1;
								TInterpreterConstants.lastName=TBoardConstants.getText(TInterpreterConstants.map2);
								//Change Text To Show
								TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.textToSend);
								setTextValues();
								updateScreenText();

								interpreter.InterpreterRobot.delay(TInterpreterConstants.time*1000);
								TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
								//TInterpreterConstants.textRender.paint(interpreter.getGraphics());
								setTextValues();
								updateScreenText();
								TInterpreterConstants.sendTextOn=0;
							}
						
							if (TInterpreterConstants.musicOn==1){
								actionPerformed=1;
								InterpreterClicSound();
							}
							//change Current board?
						
							if (interpreter.BoardChange==1)
							{
							//Update Views, 
								actionPerformed=1;
								interpreter.changeBoard(TInterpreterMarqueeHandler.followingBoard);
								tablero=TInterpreterMarqueeHandler.followingBoard;
								views = TInterpreterMarqueeHandler.followingBoard.getGraphLayoutCache().getAllViews();
								boardMap = ((TBoardModel)tablero.getModel()).getAttributes();
				  		 
								BSound=TBoardConstants.getSoundFile(boardMap);
								soundCounter=0;
								if ((BSound!=null)&&(soundCounter==0)){
									soundCounter=1;
									TInterpreterConstants.ruta=BSound;
									InterpreterClicSound();
								}
				 		
								listaOrdenada=TBoardConstants.getOrderedCellList(boardMap);
								TInterpreterRun.i=-1;
								accessController=1;
								interpreter.BoardChange=0;
								interpreter.BrowseGrid=0;
								TInterpreterConstants.SecondClic=0;
								TInterpreterConstants.ClicReleased=0;
								TInterpreterRun.fin=false;
								TInterpreterConstants.vmap=null;
							}
						}
						
						//Accesing Grid. Browse it;
						
						if (interpreter.BrowseGrid==1){
							TInterpreterConstants.isGrid=0;
							
							interpreter.BrowseGrid=0;
							TInterpreterConstants.ClicReleased=0;
							AttributeMap MAP = views[interpreter.Grid].getAllAttributes();
							Tancho=views[interpreter.Grid].getBounds().getWidth();
							Talto=views[interpreter.Grid].getBounds().getHeight();
							Color color=TBoardConstants.getChangeColorGrid(MAP);
						
							int grosor=TBoardConstants.getChangeLineWidthGrid(MAP);
							DrawRectangle(views[interpreter.Grid],2,color,grosor);
							interpreter.update(interpreter.getGraphics());
							ArrayList ObjectList=TBoardConstants.getOrderedCellList(MAP);
							
							CellView childs[]=views[interpreter.Grid].getChildViews();
							//Find child for 
							int order =TBoardConstants.getOrder(MAP);
							
							if ((order==0)||(order==3))
							{   int  indiceGrid=0;
								fina=false;
								while ((indiceGrid < ObjectList.size()) && (!fina))
								{	
													
									Object elto=ObjectList.get(indiceGrid);
									int indice=0;
									
									while ((indice < views.length) && (!fina))
									{	
										
										if (elto.equals(views[indice].getCell())){
										
										TInterpreterMoveCursorToView(views[indice]);
										actionPerformed=0;count=0;
										while ((actionPerformed==0)&&(count*1000<TInterpreterConstants.interpreterDelay))
										{
											
											count++;																				
											try {
												Thread.sleep(1000);
											} catch (InterruptedException e) {
											// TODO Auto-generated catch block
												e.printStackTrace();
											}
										
											if (TInterpreterConstants.sendTextOn==1)
											{ 	
												actionPerformed=1;  
												TInterpreterConstants.lastName=TBoardConstants.getText(TInterpreterConstants.map2);
												//Change Text To Show
												TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.textToSend);
												setTextValues();
												updateScreenText();

												interpreter.InterpreterRobot.delay(TInterpreterConstants.time*1000);
												TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
												//TInterpreterConstants.textRender.paint(interpreter.getGraphics());
												setTextValues();
												updateScreenText();
												TInterpreterConstants.sendTextOn=0;
												fina=true;
											}
									
											if (TInterpreterConstants.musicOn==1)
											{	
												actionPerformed=1;  						
												InterpreterClicSound();
												fina=true;
											}
											//change Current board?
									
											if (interpreter.BoardChange==1)
											{
												actionPerformed=1;  
												interpreter.changeBoard(TInterpreterMarqueeHandler.followingBoard);
												tablero=TInterpreterMarqueeHandler.followingBoard;
												views = TInterpreterMarqueeHandler.followingBoard.getGraphLayoutCache().getAllViews();
												boardMap = ((TBoardModel)tablero.getModel()).getAttributes();
								 				BSound=TBoardConstants.getSoundFile(boardMap);
								 				soundCounter=0;
								 				if ((BSound!=null)&&(soundCounter==0))
								 				{
								 					soundCounter=1;
								 					TInterpreterConstants.ruta=BSound;
								 					InterpreterClicSound();
								 				}
								 				listaOrdenada=TBoardConstants.getOrderedCellList(boardMap);
									
								 				TInterpreterRun.i=-1;
								 				accessController=1;
									
								 				interpreter.BoardChange=0;
								 				interpreter.BrowseGrid=0;
								 				TInterpreterConstants.SecondClic=0;
								 				TInterpreterConstants.ClicReleased=0;
								 				TInterpreterRun.fin=false;
								 				TInterpreterConstants.vmap=null;
								 				fina=true;
											}
										}
										
										//end if;
										}
									indice++;
									}//end for long childs
									indiceGrid++;
								}//end for;
								
							}//end if order;
							
							if (order==1){//mode Row-column
								
								
								interpreter.BrowseGrid=2;
									
								int row=ObtainRow(childs);
								int column=ObtainColumn(childs);
									
								int RowsCounter=0;
								TInterpreterConstants.ClicReleased=0;
								int pos=row*column;
								int indice=0;
								int initial=0;
								for (indice=0; indice < views.length;indice++)
								{
									if(childs[0].equals(views[indice]))initial=indice;
								}
								fin=false;
								while ((RowsCounter < row)&&(!fin))
									{
									
									pos=initial-RowsCounter*column;//Main loop in the mode row-column
									TInterpreterMoveCursorToView(views[pos]);
									DrawRectangle(views[pos],1,color,grosor);
									interpreter.InterpreterRobot.delay(TInterpreterConstants.interpreterDelay);
									interpreter.update(interpreter.getGraphics());
						
								    if (TInterpreterConstants.ClicReleased==1)
										{
											
											interpreter.BrowseGrid=1;
											int ColumnsCounter=0;
											int posicion=pos;
											fin=false;
											while ((ColumnsCounter < column) && (!fin))
											{	
												pos=posicion-ColumnsCounter;
												TInterpreterMoveCursorToView(views[pos]);
												//TInterpreterMarqueeHandler.mouseOver(pos);
												
												actionPerformed=0;count=0;
												while ((actionPerformed==0)&&(count*1000<TInterpreterConstants.interpreterDelay))
												{
													
													count++;	
													try {
														Thread.sleep(1000);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												
													if (TInterpreterConstants.sendTextOn==1)
													{  
														actionPerformed=1;
													
														TInterpreterConstants.lastName=TBoardConstants.getText(TInterpreterConstants.map2);
														//Change Text To Show
														TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.textToSend);
														setTextValues();
														updateScreenText();

														interpreter.InterpreterRobot.delay(TInterpreterConstants.time*1000);
														TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
													
														setTextValues();
														updateScreenText();
														TInterpreterConstants.sendTextOn=0;
														fin=true;
													}
											
													if (TInterpreterConstants.musicOn==1){							
														InterpreterClicSound();
														fin=true;
														actionPerformed=1;
													}
													if (interpreter.BoardChange==1){
													
														actionPerformed=1;
														//Update Views, 
														fin=true;
														interpreter.changeBoard(TInterpreterMarqueeHandler.followingBoard);
														tablero=TInterpreterMarqueeHandler.followingBoard;
														views = TInterpreterMarqueeHandler.followingBoard.getGraphLayoutCache().getAllViews();
														boardMap = ((TBoardModel)tablero.getModel()).getAttributes();
										 		
														BSound=TBoardConstants.getSoundFile(boardMap);
														soundCounter=0;
														if ((BSound!=null)&&(soundCounter==0)){
															soundCounter=1;
										 	 				TInterpreterConstants.ruta=BSound;
										 	 				InterpreterClicSound();
														}
														listaOrdenada=TBoardConstants.getOrderedCellList(boardMap);

												
														TInterpreterRun.i=-1;
														accessController=1;
														interpreter.BoardChange=0;
														interpreter.BrowseGrid=0;
														TInterpreterConstants.SecondClic=0;
														TInterpreterConstants.ClicReleased=0;
														
														TInterpreterConstants.vmap=null;
														}
												}
													
												ColumnsCounter++;
												if(ColumnsCounter == row)
													ColumnsCounter=0;
										
											}
											
										}//end if;
									  	RowsCounter++;
									  	if (RowsCounter==column) RowsCounter=0;
										
									}//end loop
								
							}	//end order files	
							
							if (order==2)//mode column-file
							
							{	
									
								interpreter.BrowseGrid=2;
									
								int row=ObtainRow(childs);
								int column=ObtainColumn(childs);
									
								int RowsCounter=0;
								TInterpreterConstants.ClicReleased=0;
								fin=false;
								while ((RowsCounter < column)&&(!fin))
									{
									//Main loop in the mode row-column
									TInterpreterMoveCursorToView(childs[RowsCounter]);
									
									
									//Dibujar el rectangulo en la posicion que le corresponde;
									DrawRectangle(childs[RowsCounter],0,color,grosor);
									
									
									interpreter.InterpreterRobot.delay(TInterpreterConstants.interpreterDelay);
									interpreter.update(interpreter.getGraphics());
								    if (TInterpreterConstants.ClicReleased==1)
										{
											
											interpreter.BrowseGrid=1;
											int ColumnsCounter=0;
											
											while ((ColumnsCounter < row) && (!fin))
											{	
												int posicion;
												posicion=(TInterpreterConstants.SecondClic);
												
												
												posicion=posicion - (column*ColumnsCounter);
												
												TInterpreterMoveCursorToView(views[posicion]);
												
												
												actionPerformed=0;count=0;
												while ((actionPerformed==0)&&(count*1000<TInterpreterConstants.interpreterDelay))
												{
													
													count++;	
													try {
														Thread.sleep(1000);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												
													if (TInterpreterConstants.sendTextOn==1)
													{  
														actionPerformed=1;
													
														TInterpreterConstants.lastName=TBoardConstants.getText(TInterpreterConstants.map2);
														//Change Text To Show
														TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.textToSend);
														setTextValues();
														updateScreenText();

														interpreter.InterpreterRobot.delay(TInterpreterConstants.time*1000);
														TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
													
														setTextValues();
														updateScreenText();
														TInterpreterConstants.sendTextOn=0;
														fin=true;
													}
											
													if (TInterpreterConstants.musicOn==1){							
														InterpreterClicSound();
														fin=true;
														actionPerformed=1;
													}
													if (interpreter.BoardChange==1){
													
														actionPerformed=1;
												//Update Views, 
														
														interpreter.changeBoard(TInterpreterMarqueeHandler.followingBoard);
														tablero=TInterpreterMarqueeHandler.followingBoard;
														views = TInterpreterMarqueeHandler.followingBoard.getGraphLayoutCache().getAllViews();
														boardMap = ((TBoardModel)tablero.getModel()).getAttributes();
										 		
														BSound=TBoardConstants.getSoundFile(boardMap);
														soundCounter=0;
														if ((BSound!=null)&&(soundCounter==0)){
															soundCounter=1;
										 	 				TInterpreterConstants.ruta=BSound;
										 	 				InterpreterClicSound();
														}
														listaOrdenada=TBoardConstants.getOrderedCellList(boardMap);

												
														TInterpreterRun.i=-1;
														accessController=1;
														interpreter.BoardChange=0;
														interpreter.BrowseGrid=0;
														TInterpreterConstants.SecondClic=0;
														TInterpreterConstants.ClicReleased=0;
														
														TInterpreterConstants.vmap=null;
														fin=true;
														}
												}
												ColumnsCounter++;
												if(ColumnsCounter == row)
													ColumnsCounter=0;
										
											}
											
										}//end if;
									
									    
										RowsCounter++;
										
										if (RowsCounter==column) RowsCounter=0;
										
									}//end loop
								
								}//end order2
						//Update All constants
							
						interpreter.BrowseGrid=0;
						TInterpreterConstants.SecondClic=0;
						TInterpreterConstants.ClicReleased=0;
						TInterpreterRun.fin=false;
							
						}//end if into a grid;
						
					} //endif browseable component;
			}//endif views[i]!=null
		
				
			
			
			if (i==listaOrdenada.size()-1)
			{	TInterpreterRun.i=-1;
				accessController=1;
				
			}
		
		if (views.length==0)
		{
			TMoveCursorToController();
		}
			
		}//end for all components
	}//end if;
	if (interpreter.getActivateBar()==0){
		    	
		//NO ALLOCATE MOUSE TO NEXT BROWSEABLE COMPONENT (BROWSING MODE)
		    	
		if (TInterpreterConstants.sendTextOn==1)
		{   
			TInterpreterConstants.lastName=TBoardConstants.getText(TInterpreterConstants.map2);
			//Change Text To Show
			TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.textToSend);
			setTextValues();
			updateScreenText();

			interpreter.InterpreterRobot.delay(TInterpreterConstants.time*1000);
			TBoardConstants.setText(TInterpreterConstants.map2,TInterpreterConstants.lastName);
			//TInterpreterConstants.textRender.paint(interpreter.getGraphics());
			setTextValues();
			updateScreenText();
			TInterpreterConstants.sendTextOn=0;
		}
		
		if(TInterpreterConstants.vmap!=null)
		{
			if(TBoardConstants.getEnvironmentAction(TInterpreterConstants.vmap)!=null){
				
			
				try {
					Runtime.getRuntime().exec("C:\\Archivos de programa\\B&J Adaptaciones\\B&J Perseo\\PerseoMini.exe");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (TInterpreterConstants.musicOn==1){							
			InterpreterClicSound();
		}
		if (interpreter.BoardChange==1){
			
			
		//Update Views, 
			
		interpreter.changeBoard(TInterpreterMarqueeHandler.followingBoard);
		tablero=TInterpreterMarqueeHandler.followingBoard;
		views = TInterpreterMarqueeHandler.followingBoard.getGraphLayoutCache().getAllViews();
 		boardMap = ((TBoardModel)tablero.getModel()).getAttributes();
 		
 		 BSound=TBoardConstants.getSoundFile(boardMap);
 		 soundCounter=0;
 	 	if ((BSound!=null)&&(soundCounter==0)){
 	 			soundCounter=1;
 	 			TInterpreterConstants.ruta=BSound;
 	 			InterpreterClicSound();
 	 	 }
 		accessController=1;
		interpreter.BoardChange=0;
		TInterpreterConstants.environmentOn=0;
		TInterpreterConstants.SecondClic=0;
		TInterpreterConstants.ClicReleased=0;
		TInterpreterRun.fin=false;
		TInterpreterConstants.vmap=null;
				
								
				
		}//end if;
	}
   }//end while
 }//end RUN
}//end class
