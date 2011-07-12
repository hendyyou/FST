/*
 * File: TLabelView.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Mar 6, 2006
 * 
 * Company: Universidad de Zaragoza, CPS, DIIS
 * 
 * License:
 * 		This program is free software: you can redistribute it and/or 
 * 		modify it under the terms of the GNU General Public License 
 * 		as published by the Free Software Foundation, either version 3
 * 		of the License, or (at your option) any later version.
 * 
 * 		This program is distributed in the hope that it will be useful,
 * 		but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 		GNU General Public License for more details.
 * 
 * 		You should have received a copy of the GNU General Public License
 *     	along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

package tico.board.componentview;

import org.jgraph.graph.CellViewRenderer;

import tico.board.componentredenerer.TLabelRenderer;
import tico.board.componentredenerer.TMonitorRenderer;

/**
 * Implementation of a <code>TMonitorView</code> view.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TMonitorView extends TComponentView {
	/**
	 * <code>TTLabelRendered</code> that displays the <code>TLabel</code> of this view.
	 */
	public static transient TMonitorRenderer renderer = new TMonitorRenderer();
	
	/**
	 * Creates a new <code>TLabelView</code>.
	 */
	public TMonitorView() {
		super();
	}

	/**
	 * Creates a new <code>TMonitorView</code> for the specified <code>monitor</code>.
	 * 
	 * @param monitor The specified <code>monitor</code>
	 */
	public TMonitorView(Object monitor) {
		super(monitor);
	}

	/* (non-Javadoc)
	 * @see org.jgraph.graph.AbstractCellView#getRenderer()
	 */
	public CellViewRenderer getRenderer() {
		return renderer;
	}
}
