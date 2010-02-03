/*
 * File: TInterpreterTextArea.java
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
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import tico.interpreter.TInterpreterConstants;


public class TInterpreterTextArea extends JLabel{
	
	public boolean backgroundTextAreaTransparent;
	public boolean borderTextAreaTransparent;	
	
	public TInterpreterTextArea (){
		super();
		
	}
	public TInterpreterTextArea setAttributes(String id,Color borderColor, float borderSize, Color backgroundColor,  Rectangle bounds, String texto, Font f, Color textColor, int ha, int va, boolean backgroundTransparent, boolean borderTransparent){
		
		backgroundTextAreaTransparent = backgroundTransparent;
		borderTextAreaTransparent = borderTransparent;		
		
		this.setFont(f);
		
		if (backgroundTextAreaTransparent){
			this.setBackground(new Color(0,0,0,0));
		}
		else{
			this.setOpaque(true);
			this.setBackground(backgroundColor);
		}
		this.setForeground(textColor);
		if (borderTextAreaTransparent){
			this.setBorder(new LineBorder(Color.white,0));
		}
		else{
			this.setBorder(new LineBorder(borderColor, (int)borderSize));
		}
		
		this.setForeground(textColor);
		
		this.setName(id);
		this.setBounds(bounds);
		
		this.setText(texto);
		this.setHorizontalAlignment(ha);
		this.setVerticalAlignment(va);

		return this;
	}
	
}
