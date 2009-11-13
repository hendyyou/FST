/*
 * File: TInterpreterLabelView.java
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

import org.jgraph.graph.CellViewRenderer;

import tico.board.componentredenerer.TLabelRenderer;

/**
 * Implementation of a <code>TInterpreterLabelView</code> view.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TInterpreterLabelView extends TInterpreterComponentView {
	/**
	 * <code>TTLabelRendered</code> that displays the <code>TLabel</code> of this view.
	 */
	public static transient TLabelRenderer renderer = new TLabelRenderer();
	
	/**
	 * Creates a new <code>TInterpreterLabelView</code>.
	 */
	public TInterpreterLabelView() {
		super();
	}

	/**
	 * Creates a new <code>TInterpreterLabelView</code> for the specified <code>label</code>.
	 * 
	 * @param label The specified <code>label</code>
	 */
	public TInterpreterLabelView(Object label) {
		super(label);
	}

	/* (non-Javadoc)
	 * @see org.jgraph.graph.AbstractCellView#getRenderer()
	 */
	public CellViewRenderer getRenderer() {
		return renderer;
	}
}
