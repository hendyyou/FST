/*
 * File: TInterpreterBoard.java
 * 		This file is part of Tico, an application
 * 		to create and perform interactive communication boards to be
 * 		used by people with severe motor disabilities.
 * 
 * Authors: Isabel González y Carolina Palacio
 * 
 * Date:	Nov, 2009
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import tico.editor.TFileHandler;
import tico.interpreter.components.TInterpreterCell;
import tico.interpreter.components.TInterpreterLabel;
import tico.interpreter.components.TInterpreterLine;
import tico.interpreter.components.TInterpreterOval;
import tico.interpreter.components.TInterpreterRectangle;
import tico.interpreter.components.TInterpreterRoundRectangle;
import tico.interpreter.components.TInterpreterTextArea;
import tico.interpreter.threads.TInterpreterBoardSound;

/**
 * A control that displays a communication board.
 * 
 * A <code>TBoard</code> object doesn't actually contain your data; it simply
 * provides a view of the data. Like any non-trivial <code>Swing</code>
 * component, the board gets data by querying its data model.
 *
 * <code>TBoard</code> displays its data by drawing individual elements. Each
 * element displayed by the board contains exactly one item of data, which is
 * called a component.
 *
 * @author Isabel González y Carolina Palacio
 * @version 1.0 Nov 20, 2009
 */


public class TInterpreterBoard {
	
	private String name;
	private float width;
	private float height;	
	private String soundPath = null;
	private String imagePath = null;
	private Color backgroundColor = null;
	private int imageResizeStyle;
	
	private ArrayList<TInterpreterLine> lineList;
	private ArrayList<TInterpreterOval> ovalList;
	private ArrayList<TInterpreterRectangle> rectangleList;
	private ArrayList<TInterpreterRoundRectangle> roundRectangleList;
	private ArrayList<TInterpreterCell> cellList;
	//private ArrayList<TInterpreterRectangle> gridList;
	private ArrayList<TInterpreterTextArea> textAreaList;
	private ArrayList<TInterpreterLabel> labelList;
	
	private ArrayList<String> orderedCellListNames;
	
	private ArrayList<Object> repaintOrderedComponents;
	
	// Constructor
	public TInterpreterBoard(){
		initializeArrays();
	}
	
	// Getters & Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
	public String getSoundFile() {
		return soundPath;
	}

	public void setSoundFile(String newSoundPath) {
		soundPath = newSoundPath;
	}

	public String getImageFile() {
		return imagePath;
	}

	public void setImageFile(String newImagePath) {
		imagePath = newImagePath;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getImageResizeStyle() {
		return imageResizeStyle;
	}
	
	public void setImageResizeStyle(int newImageResizeStyle) {
		imageResizeStyle = newImageResizeStyle;
	}	
	
	public TInterpreterTextArea getTextAreaByName(String nameTextArea){
		int i=0;
		while (i < textAreaList.size()){			
			if ((textAreaList.get(i).getName()).equals(nameTextArea)){
				return(textAreaList.get(i));
			}
			i++;
		}
		return null;	
	}
	
	public TInterpreterCell getCellByName(String nameCell){
		int i=0;
		while (i < cellList.size()){			
			if ((cellList.get(i).getName()).equals(nameCell)){
				return(cellList.get(i));
			}
			i++;
		}
		return null;
	}

	public ArrayList<TInterpreterLine> getLineList() {
		return lineList;
	}

	public void setLineList(ArrayList<TInterpreterLine> newLineList) {
		lineList = newLineList;
	}

	public ArrayList<TInterpreterLabel> getLabelList() {
		return labelList;
	}

	public void setLabelList(ArrayList<TInterpreterLabel> newLabelList) {
		labelList = newLabelList;
	}
	
	public ArrayList<Object> getRepaintOrderedComponents() {
		return repaintOrderedComponents;
	}

	public void setRepaintOrderedComponents(ArrayList<Object> newObjectList) {
		repaintOrderedComponents = newObjectList;
	}

	public ArrayList<TInterpreterOval> getOvalList() {
		return ovalList;
	}

	public void setOvalList(ArrayList<TInterpreterOval> newOvalList) {
		ovalList = newOvalList;
	}
	
	public ArrayList<TInterpreterRectangle> getRectangleList() {
		return rectangleList;
	}

	public void setRectangleList(ArrayList<TInterpreterRectangle> newRectangleList) {
		rectangleList = newRectangleList;
	}
	
	public ArrayList<TInterpreterRoundRectangle> getRoundRectangleList() {
		return roundRectangleList;
	}

	public void setRoundRectangleList(ArrayList<TInterpreterRoundRectangle> newRoundRectangleList) {
		roundRectangleList = newRoundRectangleList;
	}
	
	public ArrayList<TInterpreterCell> getCellList() {
		return cellList;
	}

	public void setCellList(ArrayList<TInterpreterCell> newCellList) {
		cellList = newCellList;
	}
	
	/*public ArrayList<TInterpreterRectangle> getGridList() {
		return gridList;
	}

	public void setGridList(ArrayList<TInterpreterRectangle> newGridList) {
		gridList = newGridList;
	}	*/
	
	public ArrayList<String> getOrderedCellListNames() {
		return orderedCellListNames;
	}

	public void setOrderedCellListNames(ArrayList<String> newOrderedCellListNames) {
		orderedCellListNames = newOrderedCellListNames;
	}
	
	public void loadBoard(JPanel interpreterArea){
		TInterpreter.setCurrentBoard(this);
		
		//load board components
/*		for (int i=0; i < lineList.size(); i++){
			interpreterArea.add(lineList.get(i));
		}
		for (int i=0; i < labelList.size(); i++){
			interpreterArea.add(labelList.get(i));
		}
		for (int i=0; i < ovalList.size(); i++){
			interpreterArea.add(ovalList.get(i));
		}
		for (int i=0; i < rectangleList.size(); i++){
			interpreterArea.add(rectangleList.get(i));
		}
		for (int i=0; i < roundRectangleList.size(); i++){
			interpreterArea.add(roundRectangleList.get(i));
		}
		for (int i=0; i < cellList.size(); i++){
			interpreterArea.add(cellList.get(i));
		}
		for (int i=0; i < textAreaList.size(); i++){
			interpreterArea.add(textAreaList.get(i));
		}*/
		for (int i=repaintOrderedComponents.size()-1; i >=0 ; i--){
			interpreterArea.add((Component) repaintOrderedComponents.get(i));
		}
		
		
		// load board attributes
		Dimension boardSize= new Dimension((int)width, (int)height);
		interpreterArea.setPreferredSize(boardSize);
		
		if (backgroundColor!= null){
			interpreterArea.setBackground(backgroundColor);
		}
		
		if (this.imagePath!= null){
			ImageIcon imageFile = new ImageIcon(TFileHandler.getCurrentDirectoryPath()+File.separator+imagePath);
			
			JLabel backgroundImageContainer= new JLabel();
			backgroundImageContainer.setSize((int)boardSize.getWidth()-2,(int)boardSize.getHeight()-2);
			backgroundImageContainer.setHorizontalAlignment(SwingConstants.CENTER);
			backgroundImageContainer.setVerticalAlignment(SwingConstants.CENTER);
			
			//Image bigger than interpretArea
			if (imageFile.getIconHeight() >= this.getHeight() && imageFile.getIconWidth() >= this.getWidth()){
				imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)boardSize.getWidth()-2,(int)boardSize.getHeight()-2,Image.SCALE_DEFAULT));
			}
			
			//Image smaller than interpretArea
			else if (imageFile.getIconHeight() < this.getHeight() && imageFile.getIconWidth() < this.getWidth()){
				if (getImageResizeStyle()==0){  //Adjust						
					imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)boardSize.getWidth()-2,(int)boardSize.getHeight()-2,Image.SCALE_DEFAULT));
				}
				if (getImageResizeStyle()==1){  //Ampliada al tamaño del tablero
					//Si el tablero es más ancho que alto
					if (this.getWidth()> this.getHeight()){ //ajustamos a la altura
						
						float factor = (float) (boardSize.getHeight()/imageFile.getIconHeight());
						
						imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)((float)imageFile.getIconWidth()*factor-2),(int)boardSize.getHeight()-2,Image.SCALE_DEFAULT));
						
					}	
					if (this.getWidth()< this.getHeight()){ //ajustamos a la anchura
						
						float factor = (float) (boardSize.getWidth()/imageFile.getIconWidth());
						imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)boardSize.getWidth()-2,(int)((float)imageFile.getIconHeight()*factor-2),Image.SCALE_DEFAULT));
						
					}
				}				
			}
			
			//Imagen más alta que el tablero
			//Calculamos factor de escala con las alturas y lo aplicamos tb a las anchuras!
			else if (imageFile.getIconHeight() > this.getHeight() && imageFile.getIconWidth() < this.getWidth()){
				
				float factor = (float) (boardSize.getHeight()/imageFile.getIconHeight());
				
				if (getImageResizeStyle()==1 || getImageResizeStyle()==2){  //Ajustada/ampliada al tamaño del tablero							
					imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)((float)imageFile.getIconWidth()*factor-2),(int)boardSize.getHeight()-2,Image.SCALE_DEFAULT));
				}
				if (getImageResizeStyle()==0){
					imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)boardSize.getWidth()-2,(int)boardSize.getHeight()-2,Image.SCALE_DEFAULT));
				}
				
			}	
			
			//Imagen más ancha que el tablero
			else if (imageFile.getIconHeight() < this.getHeight() && imageFile.getIconWidth() > this.getWidth()){
				
				float factor = (float) (boardSize.getWidth()/imageFile.getIconWidth());
				
				if (getImageResizeStyle()==1 || getImageResizeStyle()==2){  //Ajustada/ampliada al tamaño del tablero							
					imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)boardSize.getWidth()-2,(int)((float)imageFile.getIconHeight()*factor-2),Image.SCALE_DEFAULT));
				}
				if (getImageResizeStyle()==0){
					imageFile = new ImageIcon(imageFile.getImage().getScaledInstance((int)boardSize.getWidth()-2,(int)boardSize.getHeight()-2,Image.SCALE_DEFAULT));
				}
				
			}	
			
			backgroundImageContainer.setIcon((Icon) (imageFile));
			backgroundImageContainer.setVisible(true); 
			backgroundImageContainer.setLocation(1, 1);
			interpreterArea.add(backgroundImageContainer);			
		}
		//Devolvemos el foco al area de interpretación cada vez que cargamos un panel
		//para poder capturar el ESC
		TInterpreterConstants.interpreter.interpretArea.requestFocusInWindow();
				
		if (this.soundPath!= null){			
			TInterpreterBoardSound boardSound = new TInterpreterBoardSound(this.soundPath);
			SwingUtilities.invokeLater(boardSound);
		}
	}

	public void initializeArrays(){
		lineList = new ArrayList<TInterpreterLine>();
		labelList = new ArrayList<TInterpreterLabel>();
		ovalList = new ArrayList<TInterpreterOval>();
		rectangleList = new ArrayList<TInterpreterRectangle>();
		roundRectangleList = new ArrayList<TInterpreterRoundRectangle>();
		cellList = new ArrayList<TInterpreterCell>();
		//gridList = new ArrayList();
		textAreaList = new ArrayList<TInterpreterTextArea>();
		orderedCellListNames = new ArrayList<String>();
		repaintOrderedComponents = new ArrayList<Object>();
	}
	
	// Insert a line in the list
	public void insertLine(TInterpreterLine line){
		lineList.add(line);
	}
	
	// Insert a label in the list
	public void insertLabel(TInterpreterLabel label){
		labelList.add(label);
	}
	
	// Insert a oval in the list
	public void insertOval(TInterpreterOval oval){
		ovalList.add(oval);
	}
	
	// Insert a rectangle in the list
	public void insertRectangle(TInterpreterRectangle rectangle){
		rectangleList.add(rectangle);
	}
	
	// Insert a round rectangle in the list
	public void insertRoundRectangle(TInterpreterRoundRectangle roundRectangle){
		roundRectangleList.add(roundRectangle);
	}
	
	// Insert a cell in the list
	public void insertCell(TInterpreterCell cell){
		cellList.add(cell);
	}
	
	// Insert a text area in the list
	public void insertTextarea(TInterpreterTextArea textArea){
		textAreaList.add(textArea);
	}
	
	public void insertOrderedCellName(String cellName){
		orderedCellListNames.add(cellName);
	}
	
	public void insertComponent(Object component){
		repaintOrderedComponents.add(component);
	}
}
