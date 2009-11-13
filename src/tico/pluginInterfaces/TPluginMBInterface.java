/*
 * File: TPluginMBInterface.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: May 20, 2008
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

package tico.pluginInterfaces;

import javax.swing.JMenu;
import tico.editor.TEditor;

/**
 * "Home" interface for particular tool. It is used by application core
 * to start up tool when user selects appropriate "tab" on the main
 * application window.
 * @version $Id: Tool.java,v 1.2 2006/02/23 16:44:05 ddimon Exp $
 */
public interface TPluginMBInterface {
    /**
     * This method is called once during application life cycle to allow
     * tool to initialize and show itself.
     * @param rootContainer parent container for tool visual components
     */
    JMenu init(TEditor editor);
}