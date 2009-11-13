/*
 * File: TAlignCenterHorizontalAction.java
 * 		This file is part of Tico, an application
 * 		to create and perfom interactive comunication boards to be
 * 		used by people with severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Jan 30, 2006
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
package tico.editor.actions;

import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;

import org.jgraph.graph.AttributeMap;

import tico.board.TBoardConstants;
import tico.board.components.TComponent;
import tico.board.components.TGrid;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.editor.TEditor;

/**
 * Action wich align the horizontal central edge of all the editor's current
 * selected components.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TAlignCenterHorizontalAction extends TEditorAbstractAction {
	/**
	 * Constructor of the TAlignCenterHorizontalAction.
	 * 
	 * @param editor The boards' editor
	 */
	public TAlignCenterHorizontalAction(TEditor editor) {
		super(editor, TLanguage.getString("TAlignCenterHorizontalAction.NAME"),
				TResourceManager.getImageIcon("align-horizontal-center-22.png"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Map nested = new Hashtable();
		Map attributeMap;
		
		int selectedCellsCount;
		
		TComponent referenceCell, currentCell;
		Rectangle2D referenceBounds, currentBounds, newBounds;

		Object[] selectedCells = getEditor().getCurrentBoard().getSelectionCells();
		for (int i = 0; i < selectedCells.length; i++)
			if (selectedCells[i] instanceof TGrid)
				getEditor().getCurrentBoard().removeSelectionCell(selectedCells[i]);
		
		selectedCellsCount = getEditor().getCurrentBoard().getSelectionCount();
		
		// I don't have to modify any component
		if (selectedCellsCount < 2) return;
		
		// Get the reference values
		referenceCell = (TComponent)getEditor().getCurrentBoard().getSelectionCells()[0];
		referenceBounds = (Rectangle2D)TBoardConstants.getBounds(referenceCell.getAttributes());
		
		// Apply them to all the other selected cells
		for (int i = 1; i < selectedCellsCount; i++) {
			attributeMap = new AttributeMap();
			
			currentCell = (TComponent)getEditor().getCurrentBoard().getSelectionCells()[i];
			currentBounds = (Rectangle2D)TBoardConstants.getBounds(currentCell.getAttributes());
			newBounds = new Rectangle2D.Double();
			newBounds.setFrameFromCenter(currentBounds.getCenterX(),
					referenceBounds.getCenterY(),currentBounds.getX(),
					referenceBounds.getCenterY() - (currentBounds.getHeight() / 2));
			
			TBoardConstants.setBounds(attributeMap,newBounds);
			nested.put(currentCell,attributeMap);
		}
		
		getEditor().getCurrentBoard().getGraphLayoutCache().edit(nested,null,null,null);
	}
}
