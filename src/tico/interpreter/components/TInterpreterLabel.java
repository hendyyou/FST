/*
 * File: TInterpreterLabel.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Isabel González y Carolina Palacio
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
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class TInterpreterLabel extends JLabel{
	public TInterpreterLabel (){
		super();
	}
	
	public TInterpreterLabel setAttributes(Color borderColor, float borderSize, Color backgroundColor, Rectangle r, String texto, Font f, Color textColor){
		this.setLocation(r.x, r.y);
		
		this.setSize(r.width, r.height);
		this.setForeground(textColor);
		this.setBorder(new LineBorder(borderColor, (int)borderSize));
		this.setHorizontalAlignment(CENTER);
		this.setVerticalAlignment(CENTER);
		this.setFont(f);
		
		this.setText(texto);
		this.setOpaque(true);
		this.setBackground(backgroundColor);
		
		return this;
	}
}
