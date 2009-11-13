/*
 * File: TToolBarLabel.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Aug 17, 2006
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

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * An implementation of a label for a tool bar with the format parameters for Tico
 * applications.
 *
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TToolBarLabel extends JLabel {

	/**
	 * Creates a <code>TToolBarLabel</code> with an <code>icon</code>.
	 * 
	 * @param icon The <code>icon</code> image to display on the button
	 */
	public TToolBarLabel(Icon icon) {
		super(icon);
		setPreferredSize(new Dimension(icon.getIconHeight() + 10,
				icon.getIconWidth() + 10));
	}
}