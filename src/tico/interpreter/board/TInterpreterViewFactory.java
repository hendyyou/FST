/*
 * File: TInterpreterViewFactory.java
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

import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.VertexView;

import tico.board.components.TCell;
import tico.board.components.TGridCell;
import tico.board.components.TLabel;
import tico.board.components.TLine;
import tico.board.components.TOval;
import tico.board.components.TRectangle;
import tico.board.components.TRoundRect;
import tico.board.components.TTextArea;

/**
 * Implementation of a cell view factory that returns the default view for
 * each component type.
 *
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TInterpreterViewFactory extends DefaultCellViewFactory {
	/**
	 * Constructs a <code>TInterpreterComponentView</code> view for the specified
	 * <code>component</code>.
	 * 
	 * @param component The specified <code>component</code>
	 * @return The constructed <code>TInterpreterComponentView</code>
	 */
	protected VertexView createVertexView(Object component) {
				
		if (component instanceof TCell)
			return new TInterpreterCellView(component);
		if (component instanceof TGridCell)
			return new TInterpreterGridCellView(component);
		if (component instanceof TOval)
			return new TInterpreterOvalView(component);
		if (component instanceof TRectangle)
			return new TInterpreterRectangleView(component);
		if (component instanceof TRoundRect)
			return new TInterpreterRoundRectView(component);
		if (component instanceof TTextArea)
			return new TInterpreterTextAreaView(component);
		if (component instanceof TLine)
			return new TInterpreterLineView(component);
		if (component instanceof TLabel)
			return new TInterpreterLabelView(component);
		return super.createVertexView(component);
	}
}
