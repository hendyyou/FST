/*
 * File: TRoundRectMarqueeHandler.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: May 23, 2006
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

package tico.editor.handler;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import org.jgraph.JGraph;

import tico.board.TBoardConstants;
import tico.board.components.TComponent;
import tico.board.components.TRoundRect;
import tico.board.componentview.TRoundRectView;
import tico.editor.TBoardContainer;

/**
 * Marquee handler which allows to insert, in a <code>boardContainer</code>
 * board, a new <code>roundRect</code> of the selected dimension.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TRoundRectMarqueeHandler extends TComponentMarqueeHandler {
	/**
	 * Creates a new <code>TRoundRectMarqueeHandler</code> for the specified
	 * <code>boardContainer</code>.
	 * 
	 * @param boardContainer The specified <code>boardContainer</code>
	 */
	public TRoundRectMarqueeHandler(TBoardContainer boardContainer) {
		super(boardContainer);
	}

	protected TComponent createDefaultComponent(Rectangle2D bounds) {
		TComponent roundRect = new TRoundRect();

		roundRect.getAttributes().applyMap(
				getBoardContainer().getEditor().getCurrentAttributes());

		TBoardConstants.setBounds(roundRect.getAttributes(), bounds);

		return roundRect;
	}

	public void overlay(JGraph graph, Graphics g, boolean clear) {
		if (marqueeBounds != null) {
			int roundRectArc = TRoundRectView.getArcSize((int)marqueeBounds
					.getWidth(), (int)marqueeBounds.getHeight());

			g.drawRoundRect((int)marqueeBounds.getX(), (int)marqueeBounds
					.getY(), (int)marqueeBounds.getWidth(), (int)marqueeBounds
					.getHeight(), roundRectArc, roundRectArc);
		}
	}
}
