/*
 * File: BoardChangeEvent.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Oct 5, 2006
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

package tico.board.events;

import java.util.EventObject;

/**
 * A semantic event which indicates that a change on a board occured. This
 * event is generated by a <code>TBoard</code> when the change occurs.
 * The event is passed to every every <code>BoardChangeListener</code> object
 * that registered to receive such events using the component's
 * <code>addBoardChangeListener</code> method.
 * 
 * The object that implements the <code>BoardChangeListener</code> interface gets
 * this <code>BoardChangeEvent</code> when the event occurs.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class BoardChangeEvent extends EventObject {
	/**
	 * Constructs an <code>BoardChangeEvent</code> object.
	 * 
	 * @param source The object that originated the event
	 */    
	public BoardChangeEvent(Object source) {
		super(source);
	}
}
