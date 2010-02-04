/*
 * File: TInterpreterProject.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Isabel González y Carolina Palacio
 * 
 * Date: Nov, 2009
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
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import tico.board.TBoardConstants;
import tico.configuration.TLanguage;
import tico.editor.TFileHandler;
import tico.editor.TProjectHandler;
import tico.interpreter.components.TInterpreterCell;
import tico.interpreter.components.TInterpreterLabel;
import tico.interpreter.components.TInterpreterLine;
import tico.interpreter.components.TInterpreterOval;
import tico.interpreter.components.TInterpreterRectangle;
import tico.interpreter.components.TInterpreterRoundRectangle;
import tico.interpreter.components.TInterpreterTextArea;
import tico.interpreter.listeners.TCellListener;
import tico.interpreter.listeners.TExitCellListener;
import tico.interpreter.listeners.TReadCellListener;
import tico.interpreter.listeners.TReturnCellListener;
import tico.interpreter.listeners.TStopCellListener;
import tico.interpreter.listeners.TUndoAllCellListener;
import tico.interpreter.listeners.TUndoCellListener;

public class TInterpreterProject { 
	
	private static ArrayList<TInterpreterBoard> boardList;
	private static String name;
	private static String initialBoardname = null;
	private static String boardToGo = null;
	private boolean boardChanged = false;
	private static String boardToReturn = null;
	private String cellToReturn = null;
	private static String currentBoard = null;
	private int positionCellToReturn = 0;
	
	public TInterpreterProject(){
		boardList= new ArrayList();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String nameProject) {
		name= nameProject;
	}
	
	public int getPositionCellToReturnByName(String boardName, String cell){
		TInterpreterBoard board= this.getBoard(boardName);
		ArrayList<String> cellNames = board.getOrderedCellListNames();
		int i = 0;
		Boolean salir = false;
		int posCelda = -1;
		while (i<cellNames.size() && !salir){
    		if (cell.equals(cellNames.get(i))){
    			posCelda = i;
    			salir = true;
    		}
    		i++;
    	}
		return posCelda;
	}
	
	public int getPositionCellToReturn(){
		return this.positionCellToReturn;
	}
	
	public void setPositionCellToReturn(int i){
		this.positionCellToReturn = i;
	}
	
	public static String getinitialBoardname() {
		return initialBoardname;
	}
	
	public void setBoardToGo(String boardToGo) {
		this.boardToGo = boardToGo;
	}

	public String getBoardToGo() {
		return boardToGo;
	}
	
	public void setBoardChanged(boolean boardChanged) {
		this.boardChanged = boardChanged;
	}

	public boolean isBoardChanged() {
		return boardChanged;
	}
	
	public void insertBoard(TInterpreterBoard board){
		boardList.add(board);
	}
	
	public TInterpreterBoard getBoard (String name){
		int indice=0;
		while (indice< boardList.size()){			
			if ((boardList.get(indice).getName()).equals(name)){
				return(boardList.get(indice));
			}
			indice++;
			
		}
		return null;
		
	}
	
	public void setBoardToReturn(String board) {
		boardToReturn = board;
	}

	public String getBoardToReturn() {
		return boardToReturn;
	}

	public void setCellToReturn(String cell) {
		cellToReturn = cell;
	}

	public String getCellToReturn() {
		return cellToReturn;
	}

	public void setCurrentBoard(String current) {
		currentBoard = current;
	}

	public String getCurrentBoard() {
		return currentBoard;
	}

	public static TInterpreterProject XMLDecode(File file, String projectName){
		  
		  float linewidth=(float) 0.0;
		  Rectangle r= new Rectangle();
		  Color foregroundColor = null;
		  Color backgroundColor= null;
		  Color borderColor = null;
		  String id = null;
		  String text = "";
		  Font font = null;
		  int ha = 0;
		  int va = 0;
		  int vtp=0;
		  ImageIcon icon = null;
		  ImageIcon alternativeIcon = null;
		  boolean transparentBackground = true;
		  boolean transparentBorder = true;
		  TInterpreterProject project = new TInterpreterProject();
			
	     try {
	        SAXBuilder builder=new SAXBuilder(false); 
	        
	        Document doc= builder.build(file);
	        
	        Element raiz=doc.getRootElement();
	        if (projectName != "")
				 project.setName(projectName);
	        else{
		        String nameproject= raiz.getAttribute("name").getValue();
		        project.setName(nameproject);
	        }
	        // initialBoard
	        Element initialBoard= raiz.getChild("initial_board");
	        initialBoardname = initialBoard.getValue();
	        project.setBoardToReturn(initialBoardname);
	        project.setCurrentBoard(initialBoardname);
	        
	        List boards = raiz.getChildren("board");
	        
	        Iterator i = boards.iterator();
	        while (i.hasNext()){
	        	
	            Element board= (Element)i.next();
	            String name= board.getAttributeValue("name");
	            TInterpreterBoard myboard= new TInterpreterBoard();
	            myboard.setName(name);
	            
	            Element model= board.getChild("model");
	            Element attributes = model.getChild("attributes");
	            List attributeBoard = attributes.getChildren();
	            Iterator bcomp = attributeBoard.iterator();
	            while (bcomp.hasNext()){
	            	Element attribBoardValue=  (Element)bcomp.next();
	            	String componentType= attribBoardValue.getAttribute("key").getValue();
	            	
	            	//imageResizeStyle
	            	int imageResizeStyle;
	            	if (componentType.equals("imageResizeStyle")){
	            		imageResizeStyle = Integer.parseInt(attribBoardValue.getValue());
	            		myboard.setImageResizeStyle(imageResizeStyle);
	            	}
	            	
	            	//orderedCellList	            	
	            	if (componentType.equals("orderedCellList")){
	            		List orderedCells = attribBoardValue.getChildren();
	            		Iterator ocomp = orderedCells.iterator();
	            		if (ocomp.hasNext())
	     	            while (ocomp.hasNext()){
	     	            	Element nameCell=  (Element)ocomp.next();
	     	            	String cellName = nameCell.getValue();
	     	            	myboard.insertOrderedCellName(cellName);
	     	            }	     	           
	            	}
	            	
	            	// size
	            	if (componentType.equals("size")){
	            		float width= Float.parseFloat(attribBoardValue.getChild("width").getValue().trim());
                		float height= Float.parseFloat(attribBoardValue.getChild("height").getValue().trim());
                		myboard.setWidth(width);
                		myboard.setHeight(height);
	            	}

	            	// soundFile
	            	if (componentType.equals("soundFile")){
	            		File soundFile = null;
	            		String partialPath = attribBoardValue.getValue();
	            		try {
	            			soundFile = TFileHandler.importFile(new File(TProjectHandler
        							.getTempDirectory(), partialPath));
        					
        				} catch (IOException e) {
        					e.printStackTrace();
        				} 
	            		myboard.setSoundFile(partialPath);
	            	}
	            	
	            	// imageFile
	            	if (componentType.equals("icon")){
	            		
	            		File imageFile= null;
	            		String partialPath = attribBoardValue.getValue();
	            		
	            			try {
	        					imageFile = TFileHandler.importFile(new File(TProjectHandler
	        							.getTempDirectory(), partialPath));
	        					
	        				} catch (IOException e) {
	        					e.printStackTrace();
	        				} 
	            		myboard.setImageFile(partialPath);
	            	}
	            	
	            	// backgroundColor	            	
                	if (componentType.equals("backgroundColor")){
                		String micolor= attribBoardValue.getValue().trim();
                		
                		micolor = micolor.substring(2, micolor.length());
                		int red = Integer.parseInt(micolor.substring(0,2), 16);
                		int green = Integer.parseInt(micolor.substring(2,4), 16);
                		int blue = Integer.parseInt(micolor.substring(4,6), 16);
                		
                		backgroundColor = new Color(red,green,blue);
                		myboard.setBackgroundColor(backgroundColor);
                		
                	}	            	
	            	
	            }
	            
	            List components = model.getChildren("component");
	            Iterator icomp = components.iterator();
	            while (icomp.hasNext()){
	            	Element component= (Element)icomp.next();
	            	String componentType= component.getAttribute("type").getValue();
	            	icon = null;
	            	alternativeIcon= null;
	            	String command=null;
	            	
	            	text = "";
					
					foregroundColor=null;
	            	borderColor=null;
	            	backgroundColor=null;
	            	
	            	transparentBorder = true;
	            	transparentBackground = true;
	            	
	            	String soundFile = null;
	            	String videoFile = null;
					
	            	if (componentType.equals("cell")){
	            		TInterpreterCell cell = new TInterpreterCell();
	            		linewidth=(float) 0.0;
	            		//TInterpreterConstants tic= new TInterpreterConstants();
	            		boardToGo = initialBoardname;

	            		Element attributesLabel= component.getChild("attributes");
	            		List attributesLabelList= attributesLabel.getChildren("attribute");
	            		Iterator iattributes = attributesLabelList.iterator();
	            		
	            		while (iattributes.hasNext()){
	                    	Element attribute= (Element)iattributes.next();
	                    	
	            		// font
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("font")){
	                		int size = Integer.parseInt(attribute.getChild("size").getValue());
	                		
	                		String family = attribute.getChild("family").getValue();
	                		
	                		if ((attribute.getChild("bold") != null)&&(attribute.getChild("italic") != null)){
	                			font= new Font(family,3, size);
	                		}
	                		else if (attribute.getChild("bold") != null){
	                			font= new Font(family,Font.BOLD, size);
	                		}
	                		else if (attribute.getChild("italic") != null){
	                			font= new Font(family,Font.ITALIC, size);
	                		}
	                		else{
	                			font= new Font(family,0, size);
	                		}	                		
	                	}
	                	
	                	// id
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
	                		id= attribute.getValue().trim();	                		
	                	}	                	
	                	
	                	// followingBoard
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("followingBoard")){
	                		cell.setBoardToGo(attribute.getValue());
	                	}
	                	
	                	// bounds
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
	                		
	                		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
	                		r.x= (int) x;
	                		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
	                		r.y= (int) y;
	                		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
	                		r.width= (int) width;
	                		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
	                		r.height= (int) height;	     
	                	} 
	                	
	                	// backgroundColor
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("backgroundColor")){
	                		String micolor= attribute.getValue().trim();
	                		
	                		micolor = micolor.substring(2, micolor.length());
	                		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                		
	                		backgroundColor = new Color(red,green,blue);
	             
							transparentBackground = false;
	                	}              	
	                	
	                	// borderColor
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("bordercolor")){
	                		String borderColorRectangle= attribute.getValue().trim();
	                		
	                		borderColorRectangle = borderColorRectangle.substring(2, borderColorRectangle.length());
	                		int red = Integer.parseInt(borderColorRectangle.substring(0,2), 16);
	                		int green = Integer.parseInt(borderColorRectangle.substring(2,4), 16);
	                		int blue = Integer.parseInt(borderColorRectangle.substring(4,6), 16);
	                		
	                		borderColor = new Color(red,green,blue);
	                		
							transparentBorder = false;
	                	}
	                	
	                	// linewidth
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("linewidth")){
	                		linewidth= (Float.valueOf(attribute.getValue().trim()));	         
	                	}
	                	
	                	// text
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("text")){
	                		text = attribute.getValue().trim();	                		
	                	}
	                	
	                	// environmentAction
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("environmentAction")){
	                		command = attribute.getValue().trim();	          
	                	}
	                	
	                	// foregroundColor
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("foregroundColor")){
	                		String micolor= attribute.getValue().trim();
	                		
	                		micolor = micolor.substring(2, micolor.length());
	                		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                		
	                		foregroundColor = new Color(red,green,blue);
	                	}
	                	
	                	// changeColor
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("ChangeColor")){
	                		String micolor= attribute.getValue().trim();
	                		
	                		micolor = micolor.substring(2, micolor.length());
	                		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                		
	                		cell.setAlternativeBorderColor(new Color(red,green,blue));
	                	}
	                	
	                	// LineChangeWidth
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("LineChangeWidth")){
	                		cell.setAlternativeBorderSize(Integer.parseInt(attribute.getValue()));
	                		
	                	}
	                	// accumulated
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("accumulated")){
	                		
	                		String valor= (attribute.getValue().trim());
	                		if (valor.equals("true")){
	                			cell.setAccumulated(true);
	                		}
	                		else if (valor.equals("false")){
	                			cell.setAccumulated(false);
	                		}
	                	}
	                	
	                	// sendTextTimer
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("sendTextTimer")){
	                		cell.setTimeSending(Integer.parseInt(attribute.getValue()));	                		
	                	}
	                	
	                	// sendTextTarget
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("sendTextTarget")){
	                		cell.setTextAreaToSend(attribute.getValue());
	                	}
	                	
	                	// sendText
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("sendText")){
	                		cell.setTextToSend(attribute.getValue());
	                		//tic.board= myboard;
	                	}
	                	
	                	// alternativeIcon
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("alternativeIcon")){
	                		File imageFile= null;
		            		String partialPath = attribute.getValue();
		            		
		            			try {
		        					imageFile = TFileHandler.importFile(new File(TProjectHandler
		        							.getTempDirectory(), partialPath));
		        					
		        				} catch (IOException e) {
		        					e.printStackTrace();
		        				} 
		        				alternativeIcon = new ImageIcon(TFileHandler.getCurrentDirectoryPath()+File.separator + attribute.getValue());
		        				cell.setAlternativeIcon(alternativeIcon);
	                	}
	                	
	                	// verticalTextPosition
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("verticalTextPosition")){
	                		vtp= Integer.parseInt(attribute.getValue());
	                	}
	                	
	                	// icon
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("icon")){
	                		 
	                		File imageFile= null;
		            		String partialPath = attribute.getValue();
		            		
		            			try {
		        					imageFile = TFileHandler.importFile(new File(TProjectHandler
		        							.getTempDirectory(), partialPath));
		        					
		        				} catch (IOException e) {
		        					e.printStackTrace();
		        				} 
		        				icon = new ImageIcon(TFileHandler.getCurrentDirectoryPath()+File.separator + attribute.getValue());
		        			
		        				cell.setDefaultIcon(icon);
	                	}	    
	                	
	                	
	                	// soundFile
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("soundFile")){
		                	// Save the sound to be played when mouse clicked
		            		soundFile = attribute.getValue();
		            		if (soundFile != null){		            			
		            			soundFile=TFileHandler.getCurrentDirectoryPath() + File.separator + soundFile;
		            			File sound = null;
			            		String partialPath = attribute.getValue();
			            		try {
			            			sound = TFileHandler.importFile(new File(TProjectHandler
		        							.getTempDirectory(), partialPath));
		        					
		        				} catch (IOException e) {
		        					e.printStackTrace();
		        				} 
		            		}	
	                	}
	                	
	                	// videoFile
	                	
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("videoFile")){
		                	// Save the video to be played when mouse clicked
		            		videoFile = attribute.getValue();
		            		if (videoFile != null){		     
		            			videoFile=TFileHandler.getCurrentDirectoryPath() + File.separator + videoFile;
		            			File video = null;
			            		String partialPath = attribute.getValue();
			            		try {
			            			video = TFileHandler.importFile(new File(TProjectHandler
		        							.getTempDirectory(), partialPath));
		        					
		        				} catch (IOException e) {
		        					e.printStackTrace();
		        				}      		
		     
		  
		            		}	
		            		
	                	}
	            	}
	            		
	            	            		
	            		cell.setAttributes(id, r, text, font, foregroundColor, vtp, icon, linewidth, borderColor, backgroundColor, transparentBackground, transparentBorder, alternativeIcon);
	            		            		
	            		//cell.setAlternativeAttributes(alternativeIcon);
	            		cell.setActionsAttributes(soundFile, videoFile, command);
	            		/*if (tic.transparenteBorder)
	            		 * 
		            		cell.setAttributes(command, id, borderColor, (float)0.0, backgroundColor, r, text, font, foregroundColor, icon,alternativeIcon, vtp, tic,tic.transparente, tic.transparenteBorder);

	            		else	            			
	            		cell.setAttributes(command, id, borderColor, linewidth, backgroundColor, r, text, font, foregroundColor, icon,alternativeIcon, vtp, tic,tic.transparente, tic.transparenteBorder);
	            		
	            		if (videoFile!=null){	            
	            			cell.setText(videoFile.substring(6, videoFile.length()));	
	            		}*/
	            			            		
	            		cell.addMouseListener(new TCellListener(cell));
	            		/*if (tic.accumulated){
	            			tic.cell= cell;
	            		}*/
	            		
	            		myboard.insertCell(cell);
	            		myboard.insertComponent(cell);
	            	}
	            	
	            	else if (componentType.equals("controllerCell")){
	            		int action = 0;
	            		Element attributesLabel= component.getChild("attributes");
	            		List attributesLabelList= attributesLabel.getChildren("attribute");
	            		Iterator iattributes = attributesLabelList.iterator();
	            		while (iattributes.hasNext()){
	            			Element attribute= (Element)iattributes.next();
	            			
		            			if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
			                		id = attribute.getValue().trim();	                		
			                	}
		            			
		            			if (((attribute.getAttribute("key").getValue()).trim()).equals("actionCode")){
			                		action = (Integer.valueOf(attribute.getValue().trim()));
			                	}	
	            			
		            			if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
			                		
			                		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
			                		r.x= (int) x;
			                		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
			                		r.y= (int) y;
			                		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
			                		r.width= (int) width;
			                		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
			                		r.height= (int) height;	  
			                	} 
		            			
		            			// font
			                	if (((attribute.getAttribute("key").getValue()).trim()).equals("font")){
			                		int size = Integer.parseInt(attribute.getChild("size").getValue());
			                		
			                		String family = attribute.getChild("family").getValue();
			                		
			                		if ((attribute.getChild("bold") != null)&&(attribute.getChild("italic") != null)){
			                			font= new Font(family,3, size);
			                		}
			                		else if (attribute.getChild("bold") != null){
			                			font= new Font(family,Font.BOLD, size);
			                		}
			                		else if (attribute.getChild("italic") != null){
			                			font= new Font(family,Font.ITALIC, size);
			                		}
			                		else{
			                			font= new Font(family,0, size);
			                		}	                		
			                	}
		            			
		            			// foregroundColor
			                	if (((attribute.getAttribute("key").getValue()).trim()).equals("foregroundColor")){
			                		String micolor= attribute.getValue().trim();
			                		
			                		micolor = micolor.substring(2, micolor.length());
			                		int red = Integer.parseInt(micolor.substring(0,2), 16);
			                		int green = Integer.parseInt(micolor.substring(2,4), 16);
			                		int blue = Integer.parseInt(micolor.substring(4,6), 16);
			                		
			                		foregroundColor = new Color(red,green,blue);
			                	}
			                	
			                	if (((attribute.getAttribute("key").getValue()).trim()).equals("icon")){
			                		 
			                		File imageFile= null;
				            		String partialPath = attribute.getValue();
				            			try {
				        					imageFile = TFileHandler.importFile(new File(TProjectHandler
				        							.getTempDirectory(), partialPath));				        					
				        				} catch (IOException e) {
				        					e.printStackTrace();
				        				}
				        				icon = new ImageIcon(TFileHandler.getCurrentDirectoryPath()+File.separator + attribute.getValue());
				        	
			            		}	 
		            			
			            		if (((attribute.getAttribute("key").getValue()).trim()).equals("text")){
			                		text= attribute.getValue().trim();	  				        
			                	}
			            		
	            		}
	            		
	            		if (action == TBoardConstants.EXIT_ACTION_CODE){//Salir
	            		//if (text.equals("Salir")){
                			TInterpreterCell exitCell = new TInterpreterCell();
                			exitCell.setAttributes2(id, r, TLanguage.getString("TInterpreterExitAction.NAME"), font, foregroundColor, icon);
                			exitCell.addMouseListener(new TExitCellListener());
                			myboard.insertCell(exitCell);
                			myboard.insertComponent(exitCell);
                		}else if (action == TBoardConstants.UNDO_ACTION_CODE){//Deshacer
	            		//}else if (text.equals("Borrar celda")){
                			TInterpreterCell undoCell = new TInterpreterCell();
                			undoCell.setAttributes2(id, r, TLanguage.getString("TInterpreterUndoAction.NAME"), font, foregroundColor, icon);
                			undoCell.addMouseListener(new TUndoCellListener());
                			myboard.insertCell(undoCell);
                			myboard.insertComponent(undoCell);
                		}else if (action == TBoardConstants.UNDO_ALL_ACTION_CODE){//Borrar todo
	            		//}else if (text.equals("Borrar celdas")){
                			TInterpreterCell undoAllCell = new TInterpreterCell();
                			undoAllCell.setAttributes2(id, r, TLanguage.getString("TInterpreterUndoAllAction.NAME"), font, foregroundColor, icon);
                			undoAllCell.addMouseListener(new TUndoAllCellListener());
                			myboard.insertCell(undoAllCell);
                			myboard.insertComponent(undoAllCell);
                		}else if (action == TBoardConstants.READ_ACTION_CODE){//Leer
	            		//}else if (text.equals("Leer")){
                			TInterpreterCell readCell = new TInterpreterCell();
                			readCell.setAttributes2(id, r, TLanguage.getString("TInterpreterReadAction.NAME"), font, foregroundColor, icon);
                			readCell.addMouseListener(new TReadCellListener());
                			myboard.insertCell(readCell);
                			myboard.insertComponent(readCell);
                		}else if (action == TBoardConstants.RETURN_ACTION_CODE){//Volver
	            		//}else if (text.equals("Volver")){
                			TInterpreterCell returnCell = new TInterpreterCell();
                			returnCell.setAttributes2(id, r, TLanguage.getString("TInterpreterReturnAction.NAME"), font, foregroundColor, icon);
                   			returnCell.addMouseListener(new TReturnCellListener());
                			myboard.insertCell(returnCell);
                			myboard.insertComponent(returnCell);
                		}else if (action == TBoardConstants.STOP_ACTION_CODE){//Parar
	            		//}else if (text.equals("Parar")){
                			TInterpreterCell stopCell = new TInterpreterCell();
                			stopCell.setAttributes2(id, r, TLanguage.getString("TInterpreterStopAction.NAME"), font, foregroundColor, icon);
                			stopCell.addMouseListener(new TStopCellListener());
                			myboard.insertCell(stopCell);
                			myboard.insertComponent(stopCell);
                		}
	            	}
	            	
	            	else if (componentType.equals("line")){	            		
	            		int startCorner = 0;	            		
	            		
	            		Element attributesLine= component.getChild("attributes");
	            		List attributesList= attributesLine.getChildren("attribute");
	            		Iterator iattributes = attributesList.iterator();
	            		
	            		while (iattributes.hasNext()){
	                    	Element attribute= (Element)iattributes.next();
	                    	
	                    	// linewidth
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("linewidth")){
	                    		linewidth = (Float.valueOf(attribute.getValue().trim()));
	                    
	                    	}
	                    	
	                    	// startCorner
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("startCorner")){
	                    		startCorner = (Integer.valueOf(attribute.getValue().trim()));
	                    
	                    	}
	                    	
	                    	// id
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
	                    		id = attribute.getValue().trim();
	                    		
	                    	}
	                    	
	                    	// bounds
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
	                    		
	                    		
	                    		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
	                    		r.x= (int) x;
	                    		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
	                    		r.y= (int) y;
	                    		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
	                    		r.width= (int) width;
	                    		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
	                    		r.height= (int) height;
	                 
	                    	}
	                    	
	                    	// bordercolor
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bordercolor")){
	                    		String micolor= attribute.getValue().trim();
	                    		micolor = micolor.substring(2, micolor.length());
	                    		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                    		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                    		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                    		borderColor = new Color(red,green,blue);
	                    	}
	                    	
	            		}
	            		TInterpreterLine line = new TInterpreterLine(borderColor,linewidth,r,startCorner);		
	            		myboard.insertLine(line);
	            		myboard.insertComponent(line);
	            	}
	            	
	            	else if (componentType.equals("oval")){	            		
	            		linewidth=(float) 0.0;
	            		r= new Rectangle();
	            		
	            		Element attributesOval= component.getChild("attributes");
	            		List attributesList= attributesOval.getChildren("attribute");
	            		Iterator iattributes = attributesList.iterator();
	            		
	            		while (iattributes.hasNext()){
	                    	Element attribute= (Element)iattributes.next();
	                    	
	                    	// linewidth
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("linewidth")){
	                    		linewidth= (Float.valueOf(attribute.getValue().trim()));
	                    
	                    	}                    	
	                    	
	                    	// id
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
	                    		id= attribute.getValue().trim();
	                    		
	                    	}
	                    	
	                    	// bounds
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
	                    		                    		
	                    		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
	                    		r.x= (int) x;
	                    		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
	                    		r.y= (int) y;
	                    		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
	                    		r.width= (int) width;
	                    		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
	                    		r.height= (int) height;
	                    		
	                    		
	                    	}
	                    	// backgroundColor
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("backgroundColor")){
	                    		String micolor= attribute.getValue().trim();
	                    		
	                    		micolor = micolor.substring(2, micolor.length());
	                    		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                    		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                    		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                    		
	                    		backgroundColor = new Color(red,green,blue);
	                    	}
	                    	
	                    	// bordercolor
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bordercolor")){
	                    		String borderColorRectangle= attribute.getValue().trim();
	                    		
	                    		borderColorRectangle = borderColorRectangle.substring(2, borderColorRectangle.length());
	                    		int red = Integer.parseInt(borderColorRectangle.substring(0,2), 16);
	                    		int green = Integer.parseInt(borderColorRectangle.substring(2,4), 16);
	                    		int blue = Integer.parseInt(borderColorRectangle.substring(4,6), 16);
	                    		
	                    		borderColor = new Color(red,green,blue);
	                    	}
	                    	
	                    	
	            		}         
	            		TInterpreterOval oval = new TInterpreterOval(borderColor,linewidth, r, backgroundColor);
	            		myboard.insertOval(oval);
	            		myboard.insertComponent(oval);
	            	}
	            	
	            	else if (componentType.equals("rectangle")){
	            		linewidth=(float) 0.0;
	            			            		
	            		Element attributesRect= component.getChild("attributes");
	            		List attributesList= attributesRect.getChildren("attribute");
	            		Iterator iattributes = attributesList.iterator();
	            		
	            		while (iattributes.hasNext()){
	                    	Element attribute= (Element)iattributes.next();
	       
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("linewidth")){
	                    		linewidth= (Float.valueOf(attribute.getValue().trim()));
	                    		
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("backgroundColor")){
	                    		String micolor= attribute.getValue().trim();
	                    		
	                    		micolor = micolor.substring(2, micolor.length());
	                    		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                    		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                    		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                    		
	                    		backgroundColor = new Color(red,green,blue);
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bordercolor")){
	                    		String borderColorRectangle= attribute.getValue().trim();
	                    		
	                    		borderColorRectangle = borderColorRectangle.substring(2, borderColorRectangle.length());
	                    		int red = Integer.parseInt(borderColorRectangle.substring(0,2), 16);
	                    		int green = Integer.parseInt(borderColorRectangle.substring(2,4), 16);
	                    		int blue = Integer.parseInt(borderColorRectangle.substring(4,6), 16);
	                    		
	                    		borderColor = new Color(red,green,blue);
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
	                    		id= attribute.getValue().trim();
	                    		
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
	                                  		
	                    		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
	                    		r.x= (int) x;
	                    		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
	                    		r.y= (int) y;
	                    		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
	                    		r.width= (int) width;
	                    		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
	                    		r.height= (int) height;
	                 
	                    	}
	                    	
	            		}
	            		TInterpreterRectangle rectangle = new TInterpreterRectangle();
	            		rectangle.setAttributes(borderColor, r,linewidth, backgroundColor);
	            		myboard.insertRectangle(rectangle);
	            		myboard.insertComponent(rectangle);
	            		
	            	}
	            	
	            	else if (componentType.equals("textArea")){            	
	                	
	            		Element attributesLabel= component.getChild("attributes");
	            		List attributesLabelList= attributesLabel.getChildren("attribute");
	            		Iterator iattributes = attributesLabelList.iterator();
	            		
	            		backgroundColor = null;
	            		foregroundColor = null;
	            		borderColor = null;
	            		
	            		boolean backgroundTransparentTextArea=true;
	            		boolean borderTransparentTextArea = true;
	            		
	            		while (iattributes.hasNext()){
	                    	Element attribute= (Element)iattributes.next();
	                    	
	                    	// linewidth
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("linewidth")){
	                    		linewidth= (Float.valueOf(attribute.getValue().trim()));
	                    		
	                    	}
	                    	
	                    	// text
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("text")){
	                    		text= attribute.getValue().trim();
	                    		
	                    	}
	                    	
	                    	// foregroundColor
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("foregroundColor")){
	                    		String micolor= attribute.getValue().trim();
	                    		
	                    		micolor = micolor.substring(2, micolor.length());
	                    		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                    		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                    		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                    		
	                    		foregroundColor = new Color(red,green,blue);
	                    	}
	                    	
	                    	// id
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
	                    		id= attribute.getValue().trim();
	                    		
	                    	}
	                    	
	                    	// backgroundColor
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("backgroundColor")){
	                    		String micolor= attribute.getValue().trim();
	                    		
	                    		micolor = micolor.substring(2, micolor.length());
	                    		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                    		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                    		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                    		
	                    		backgroundColor = new Color(red,green,blue);
	                    		backgroundTransparentTextArea = false;
	                    		
	                    	}
	                    	
	                    	// font
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("font")){
	                    		int size = Integer.parseInt(attribute.getChild("size").getValue());
	                    		String family = attribute.getChild("family").getValue();
	                    		if ((attribute.getChild("bold") != null)&&(attribute.getChild("italic") != null)){
	                    			font= new Font(family,3, size);
	                    		}
	                    		else if (attribute.getChild("bold") != null){
	                    			font= new Font(family,Font.BOLD, size);
	                    		}
	                    		else if (attribute.getChild("italic") != null){
	                    			font= new Font(family,Font.ITALIC, size);
	                    		}
	                    	}
	                    	
	                    	// bounds
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
	                    		
	                    		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
	                    		r.x= (int) x;
	                    		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
	                    		r.y= (int) y;
	                    		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
	                    		r.width= (int) width;
	                    		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
	                    		r.height= (int) height;
	                    		
	                    	}
	                    	
	                    	// borderColor
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bordercolor")){
	                    		String borderColorRectangle= attribute.getValue().trim();
	                    		
	                    		borderColorRectangle = borderColorRectangle.substring(2, borderColorRectangle.length());
	                    		int red = Integer.parseInt(borderColorRectangle.substring(0,2), 16);
	                    		int green = Integer.parseInt(borderColorRectangle.substring(2,4), 16);
	                    		int blue = Integer.parseInt(borderColorRectangle.substring(4,6), 16);
	                    		
	                    		borderColor = new Color(red,green,blue);
	                    		borderTransparentTextArea = false;
	                    	}
	                    	
	                    	// horizontal alignment
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("horizontalAlignment")){
	                    		ha= Integer.parseInt(attribute.getValue());
	                    		
	                    	
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("verticalAlignment")){
	                    		va= Integer.parseInt(attribute.getValue());
	                    		
	                    	}
	                    	
	                    	
	                    	
	            		}
	            		TInterpreterTextArea textarea = new TInterpreterTextArea();
	            		textarea.setAttributes(id,borderColor,linewidth, backgroundColor, r, text,font,foregroundColor, ha,va, backgroundTransparentTextArea, borderTransparentTextArea);
	            		myboard.insertTextarea(textarea);
	            		myboard.insertComponent(textarea);
	            	}
	            	
	            	
	            	else if (componentType.equals("roundRect")){
	            		Element attributesRoundRect= component.getChild("attributes");
	            		List attributesList= attributesRoundRect.getChildren("attribute");
	            		Iterator iattributes = attributesList.iterator();
	            		while (iattributes.hasNext()){
	                    	Element attribute= (Element)iattributes.next();
	                    	
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("linewidth")){
	                    		linewidth= (Float.valueOf(attribute.getValue().trim()));
	                    		
	                    	}
	                    	
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
	                    		id= attribute.getValue().trim();
	                    		
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
	                    		
	                    		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
	                    		r.x= (int) x;
	                    		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
	                    		r.y= (int) y;
	                    		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
	                    		r.width= (int) width;
	                    		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
	                    		r.height= (int) height;
	                    		
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("bordercolor")){
	                    		String borderColorRectangle= attribute.getValue().trim();
	                    		
	                    		borderColorRectangle = borderColorRectangle.substring(2, borderColorRectangle.length());
	                    		int red = Integer.parseInt(borderColorRectangle.substring(0,2), 16);
	                    		int green = Integer.parseInt(borderColorRectangle.substring(2,4), 16);
	                    		int blue = Integer.parseInt(borderColorRectangle.substring(4,6), 16);
	                    		
	                    		borderColor = new Color(red,green,blue);
	                    	}
	                    	if (((attribute.getAttribute("key").getValue()).trim()).equals("backgroundColor")){
	                    		String micolor= attribute.getValue().trim();
	                    		
	                    		micolor = micolor.substring(2, micolor.length());
	                    		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                    		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                    		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                    		
	                    		backgroundColor = new Color(red,green,blue);
	                    	}
	                    	
	                    	
	            		}
	            		TInterpreterRoundRectangle roundRectangle = new TInterpreterRoundRectangle(borderColor, backgroundColor, linewidth, r);
	            		myboard.insertRoundRectangle(roundRectangle);
	            		myboard.insertComponent(roundRectangle);
	            	}
	            	
	            else if (componentType.equals("label")){           	
	            	
	        		Element attributesLabel= component.getChild("attributes");
	        		List attributesLabelList= attributesLabel.getChildren("attribute");
	        		Iterator iattributes = attributesLabelList.iterator();
	        		
	        		while (iattributes.hasNext()){
	                	Element attribute= (Element)iattributes.next();
	                	
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("linewidth")){
	                		linewidth= (Float.valueOf(attribute.getValue().trim()));
	                		
	                	}
	                	
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("text")){
	                		text= attribute.getValue().trim();
	                		
	                	}
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("font")){
	                		int size = Integer.parseInt(attribute.getChild("size").getValue());
	                		
	                		String family = attribute.getChild("family").getValue();
	                		
	                		if ((attribute.getChild("bold") != null)&&(attribute.getChild("italic") != null)){
	                			font= new Font(family,3, size);
	                		}
	                		else if (attribute.getChild("bold") != null){
	                			font= new Font(family,Font.BOLD, size);
	                		}
	                		else if (attribute.getChild("italic") != null){
	                			font= new Font(family,Font.ITALIC, size);
	                		}
	                		
	                	}
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("bordercolor")){
	                		String borderColorRectangle= attribute.getValue().trim();
	                		
	                		borderColorRectangle = borderColorRectangle.substring(2, borderColorRectangle.length());
	                		int red = Integer.parseInt(borderColorRectangle.substring(0,2), 16);
	                		int green = Integer.parseInt(borderColorRectangle.substring(2,4), 16);
	                		int blue = Integer.parseInt(borderColorRectangle.substring(4,6), 16);
	                		
	                		borderColor = new Color(red,green,blue);
	                	}
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("backgroundColor")){
	                		String micolor= attribute.getValue().trim();
	                		
	                		micolor = micolor.substring(2, micolor.length());
	                		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                		
	                		backgroundColor = new Color(red,green,blue);
	                	}
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("foregroundColor")){
	                		String micolor= attribute.getValue().trim();
	                		
	                		micolor = micolor.substring(2, micolor.length());
	                		int red = Integer.parseInt(micolor.substring(0,2), 16);
	                		int green = Integer.parseInt(micolor.substring(2,4), 16);
	                		int blue = Integer.parseInt(micolor.substring(4,6), 16);
	                		
	                		foregroundColor = new Color(red,green,blue);
	                	}
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("id")){
	                		id= attribute.getValue().trim();
	                		
	                	}
	                	if (((attribute.getAttribute("key").getValue()).trim()).equals("bounds")){
	                		
	                		float x= Float.parseFloat(attribute.getChild("x").getValue().trim());
	                		r.x= (int) x;
	                		float y= Float.parseFloat(attribute.getChild("y").getValue().trim());
	                		r.y= (int) y;
	                		float width= Float.parseFloat(attribute.getChild("width").getValue().trim());
	                		r.width= (int) width;
	                		float height= Float.parseFloat(attribute.getChild("height").getValue().trim());
	                		r.height= (int) height;	                		
	                	}                	
	                	
	        		}
	        		TInterpreterLabel label = new TInterpreterLabel();
	        		label.setAttributes(borderColor,linewidth,backgroundColor, r,text, font, foregroundColor);
	        		myboard.insertLabel(label);
	        		myboard.insertComponent(label);
	            }
	            }
	            project.insertBoard(myboard);

	            if (myboard.getName().equals(getinitialBoardname())){
	            	project.setCellToReturn(myboard.getOrderedCellListNames().get(0).toString());
	            }
	            }

	     }catch (Exception e){
	        e.printStackTrace();
	     }
	     return project;
	  }
	

}