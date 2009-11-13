/*
 * File: ProjectChangeEvent.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Oct 5, 2006
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
package tico.board.events;

import java.util.EventObject;

import tico.board.TBoard;

/**
 * A semantic event which indicates that a change on a project occured. This
 * event is generated by a <code>TProject</code> when the change occurs.
 * The event is passed to every every <code>ProjectChangeListener</code> object
 * that registered to receive such events using the component's
 * <code>addProjectChangeListener</code> method.
 * 
 * The object that implements the <code>ProjectChangeListener</code> interface gets
 * this <code>ProjectChangeEvent</code> when the event occurs.
 * 
 * @author Pablo Mu�oz
 * @version 1.0 Nov 20, 2006
 */
public class ProjectChangeEvent extends EventObject {
	/**
	 * A board has been modified.
	 */
	public static int BOARD_MODIFIED = 0;
	
	/**
	 * A board has been added.
	 */
	public static int BOARD_ADDED = 1;
	
	/**
	 * A board has been removed.
	 */
	public static int BOARD_REMOVED = 2;

	/**
	 * Change in the <code>TProject</code> name. This implies that no
	 * <code>TBoard</code> suffers a change, so the <code>changeBoard</code>
	 * will be <i>null</i>.
	 */
	public static int NAME_CHANGED = 3;
	
	// The board suffered the change. If the change is NAME_CHANGED, the board
	// will be null
	private TBoard changedBoard;
	// The change
	private int change;
	
	/**
	 * Constructs an <code>ProjectChangeEvent</code> object.
	 * 
	 * @param source The object that originated the event
	 * @param changedBoard The <code>board</code> received the change
	 * @param change The <code>change</code>. This value must be NAME_CHANGED,
	 * BOARD_REMOVED, BOARD_ADDED or BOARD_MODIFIED
	 */ 	
	public ProjectChangeEvent(Object source, TBoard changedBoard, int change) {
		super(source);

		this.change = change;
		this.changedBoard = changedBoard;
	}
	
	/**
	 * Returns the changed board. If the change is NAME_CHANGED the board
	 * is <i>null</i>.
	 * 
	 * @return The changed board.
	 */
	public TBoard getChangedBoard() {
		return changedBoard;
	}
	
	/**
	 * Returns the change.
	 * 
	 * @return The change. This value can be NAME_CHANGED, BOARD_REMOVED,
	 * BOARD_ADDED or BOARD_MODIFIED
	 */
	public int getChange() {
		return change;
	}
}
