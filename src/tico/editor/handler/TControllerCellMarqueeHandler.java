/*
 * File: TCellMarqueeHandler.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: May 23, 2006
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
package tico.editor.handler;

import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

import tico.board.TBoardConstants;
import tico.board.components.TComponent;
import tico.board.components.TControllerCell;
import tico.configuration.TLanguage;
import tico.editor.TBoardContainer;
import tico.editor.TFileHandler;

/**
 * Marquee handler which allows to insert, in a <code>boardContainer</code>
 * board, a new <code>cell</code> of the selected dimension.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TControllerCellMarqueeHandler extends TComponentMarqueeHandler {
	/**
	 * Creates a new <code>TCellMarqueeHandler</code> for the specified
	 * <code>boardContainer</code>.
	 * 
	 * @param boardContainer The specified <code>boardContainer</code>
	 */
	public TControllerCellMarqueeHandler(TBoardContainer boardContainer) {
		super(boardContainer);
	}

	/* (non-Javadoc)
	 * @see tico.editor.handler.TComponentMarqueeHandler#createDefaultComponent(java.awt.geom.Rectangle2D)
	 */
	protected TComponent createDefaultComponent(Rectangle2D bounds) {
		TComponent controllerCell = new TControllerCell();

		controllerCell.getAttributes().applyMap(
				getBoardContainer().getEditor().getCurrentAttributes());
		
		TBoardConstants.setBounds(controllerCell.getAttributes(), bounds);
		
		//Default controller cell is Exit cell
		TBoardConstants.setText(controllerCell.getAttributes(), TLanguage.getString("TInterpreterExitAction.NAME"));
		TBoardConstants.setActionCode(controllerCell.getAttributes(), TBoardConstants.EXIT_ACTION_CODE);
		String currentDirectory = System.getProperty("user.dir");
		String exitFilePath = currentDirectory + File.separator + "controller-icons"+ File.separator + "controller-exit.png";
		ImageIcon exitIcon = null;
		try {
			File f = TFileHandler.importFile(exitFilePath);
			exitIcon = new ImageIcon(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		TBoardConstants.setIcon(controllerCell.getAttributes(), exitIcon);

		return controllerCell;
	}
}
