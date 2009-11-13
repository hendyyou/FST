/*
 * File: TInterpreterComponentView.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
  * Authors: Antonio Rodríguez
 * 
 * Date:	16-May-2006 
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

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import org.jgraph.graph.CellHandle;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.VertexView;

import tico.board.TBoardConstants;

/**
 * Default implementation of a <code>TComponent</code> view.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TInterpreterComponentView extends VertexView {
	/**
	 * Creates a new <code>TInterpreterComponentView</code>.
	 */
	public TInterpreterComponentView() {
		super();

	}

	/**
	 * Creates a new <code>TInterpreterComponentView</code> for the specified <code>component</code>.
	 * 
	 * @param component The specified <code>component</code>
	 */
	public TInterpreterComponentView(Object component) {
		super(component);
	}

	/* (non-Javadoc)
	 * @see org.jgraph.graph.AbstractCellView#getRenderer()
	 */
	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	
	/* (non-Javadoc)
	 * @see org.jgraph.graph.CellView#getHandle(org.jgraph.graph.GraphContext)
	 */
	public CellHandle getHandle(GraphContext context) {
		return null;
	}

	
}
