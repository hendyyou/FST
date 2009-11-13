/*
 * File: TFrame.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Jan 21, 2008
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
package tico.components;

import java.awt.Frame;
import javax.swing.JFrame;

/**
 * An implementation of a dialog with the format parameters for Tico
 * applications.
 *
 * @author Patricia M. Jaray
 * @version 2.1 Jan 21, 2008
 */
public class TFrame extends JFrame {
    /**
     * Creates a non-modal <code>TFrame</code> without a title.
     */
    public TFrame() {
        super("");
    }
    
    /**
     * Creates a non-modal <code>TFrame</code> with a title.
     */
    public TFrame(String title) {
        super(title);
    }
}

