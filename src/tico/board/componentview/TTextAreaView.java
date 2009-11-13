/*
 * File: TTextAreaView.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
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
package tico.board.componentview;

import org.jgraph.graph.CellViewRenderer;

import tico.board.componentredenerer.TTextAreaRenderer;

/**
 * Implementation of a <code>TTextAreaView</code> view.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TTextAreaView extends TComponentView {
	/**
	 * <code>TTextAreaRendered</code> that displays the <code>TTextArea</code> of this view.
	 */
    protected static transient TTextAreaRenderer renderer = new TTextAreaRenderer();
	
	/**
	 * Creates a new <code>TTextAreaView</code>.
	 */ 
    public TTextAreaView() {
        super();
    }

	/**
	 * Creates a new <code>TTextAreaView</code> for the specified <code>textArea</code>.
	 * 
	 * @param textArea The specified <code>textArea</code>
	 */   
    public TTextAreaView(Object textArea) {
        super(textArea);
    }
    
    /* (non-Javadoc)
     * @see org.jgraph.graph.AbstractCellView#getRenderer()
     */
    public CellViewRenderer getRenderer() {
        return renderer;
    }
}
