/*
 * File: TInterpreterRoundRectangle.java
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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class TInterpreterRoundRectangle extends JButton {

	Color borderColorRoundRectangle;
	Color backgroundColorRoundRectangle;
	int borderSizeRoundRectangle;
	int widthRoundRectangle;
	int heightRoundRectangle;
  
  public TInterpreterRoundRectangle(Color borderColor, Color backgroundColor, float borderSize, Rectangle bounds) {
	  super();
	  borderColorRoundRectangle = borderColor;
	  backgroundColorRoundRectangle = backgroundColor;
	  borderSizeRoundRectangle = (int)borderSize;
	  widthRoundRectangle = bounds.width;
	  heightRoundRectangle = bounds.height;
	  this.setLocation(bounds.x, bounds.y);
	  this.setSize(bounds.width, bounds.height);
	  this.setEnabled(false);
	  setContentAreaFilled(false);
	}
  
  protected void paintComponent(Graphics g) {
    Graphics2D graphics2 = (Graphics2D) g;
    graphics2.setColor(borderColorRoundRectangle);
    RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, widthRoundRectangle-1, heightRoundRectangle-1, borderSizeRoundRectangle+30, borderSizeRoundRectangle+30);
    graphics2.fill(roundedRectangle); 
    }

  protected void paintBorder(Graphics g) {
	  Graphics2D graphics2 = (Graphics2D) g;
	  graphics2.setColor(backgroundColorRoundRectangle);
	  RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(borderSizeRoundRectangle, borderSizeRoundRectangle, widthRoundRectangle-2*borderSizeRoundRectangle-1, heightRoundRectangle-2*borderSizeRoundRectangle-1, 30, 30);
	  graphics2.fill(roundedRectangle); 
    }
  }