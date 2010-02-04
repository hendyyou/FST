/*
 * File: TInterpreterRectangle.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Isabel Gonz�lez y Carolina Palacio
 * 
 * Date: Nov, 2009
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
package tico.interpreter.components;

import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class TInterpreterRectangle extends JButton{
	public TInterpreterRectangle (){
		super();
	}

	public TInterpreterRectangle setAttributes(Color borderColor, Rectangle r, float borderSize, Color backgroundColor){
		this.setBounds(r);
		this.setBackground(backgroundColor);
		this.setBorder(new LineBorder(borderColor, (int)borderSize));
		this.setEnabled(false);
		return this;
	}
	
	
}