/*
 * File: TInterpreterOval.java
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
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;

public class TInterpreterOval extends JButton {

	Color borderColorOval;
	int borderSizeOval;
	Color backgroundColorOval;
	int widthOval;
	int heightOval;

  public TInterpreterOval(Color borderColor, float borderSize, Rectangle bounds, Color backgroundColor ) {
    super();
    borderColorOval = borderColor;
    borderSizeOval = (int)borderSize;
    backgroundColorOval = backgroundColor;
    widthOval=bounds.width;
    heightOval=bounds.height;
    this.setLocation(bounds.x, bounds.y);
    this.setSize(bounds.width, bounds.height);
    this.setEnabled(false);
    setContentAreaFilled(false);
    }
  
  protected void paintComponent(Graphics g) {
	  g.setColor(borderColorOval);
	  g.fillOval(0,0,widthOval-1,heightOval-1);
	  super.paintComponent(g);
    }

  protected void paintBorder(Graphics g) {
	  g.setColor(backgroundColorOval);
	  g.fillOval(borderSizeOval, borderSizeOval, widthOval-2*borderSizeOval-1,heightOval-2*borderSizeOval-1);
	 
    }
  }
