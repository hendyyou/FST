/*
 * File: TInterpreterGridCellView.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
  * Authors: Antonio Rodríguez
 * 
 * Date:	16-May-2006 
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
package tico.interpreter.board;

import org.jgraph.graph.CellViewRenderer;

/**
 * Implementation of a <code>TInterpreterGridCellView</code> view.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TInterpreterGridCellView extends TInterpreterComponentView {
	/**
	 * <code>TGridCellRendered</code> that displays the <code>TGridCell</code> of this view.
	 */
	public static transient TInterpreterCellRenderer renderer = new TInterpreterCellRenderer();

	/**
	 * Creates a new <code>TInterpreterGridCellView</code>.
	 */
	public TInterpreterGridCellView() {
		super();
	}

	/**
	 * Creates a new <code>TInterpreterGridCellView</code> for the specified <code>gridCell</code>.
	 * 
	 * @param gridCell The specified <code>gridCell</code>
	 */
	public TInterpreterGridCellView(Object gridCell) {
		super(gridCell);
	}

	/* (non-Javadoc)
	 * @see org.jgraph.graph.AbstractCellView#getRenderer()
	 */
	public CellViewRenderer getRenderer() {
		return renderer;
	}
}
