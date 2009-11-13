/*
 * File: TRectangle.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Aug 22, 2006
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


package tico.interpreter.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.geom.Rectangle2D;

public class TRectangle {
	
	int ejex;
	int ejeY;
	int alt;
	int anch;
	Color rectangleColor;
	int border;
	
	public TRectangle (int x, int y , int anchura,int altura,Color colour, int Gross)
	{
		ejex=x;
		ejeY=y;
		anch=anchura;
		alt=altura;
		rectangleColor=colour;
		border=Gross;
		
		
	}
	
	public void paint (Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		
		Rectangle2D circle = new Rectangle2D.Double(ejex,ejeY,anch,alt);
		
		g2.setColor(rectangleColor);
		g2.setStroke(new BasicStroke(border));
	  
	    g2.draw(circle);

	}
	    
	    
	     
	

}
