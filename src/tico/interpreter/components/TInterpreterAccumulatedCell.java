/*
 * File: TInterpreterAccumulatedCell.java
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

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import tico.interpreter.TInterpreterConstants;

public class TInterpreterAccumulatedCell extends JButton{
	
	private final static int VERTICAL_ICON_MARGIN = 10;
	
	private final static int HORIZONTAL_ICON_MARGIN = 10;
	// Default distance between the icon and the text
	private final static int GAP_ICON_TEXT = 5;
	
	private String sound = null;
	
	public TInterpreterAccumulatedCell (){
		super();
	}
	
	public TInterpreterAccumulatedCell setAttributes(TInterpreterCell cell){
		this.setForeground(cell.getForeground());
		this.setBorder(new LineBorder(cell.getBorderColor(), (int)cell.getBorderSize()));
		this.sound = cell.getSoundPath();
		this.setSize(90, 90);
		//this.setPreferredSize(new Dimension(85,85));
		this.setContentAreaFilled(false);
		
		this.setText(cell.getText());
		
		this.setOpaque(true);
		this.setBackground(cell.getBackground());
		
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalTextPosition(cell.getVerticalTextPosition());
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		
		// icon
		if (cell.getIcon() != null) {			
			int textPosition = cell.getVerticalTextPosition();
			
			int maxImageWidth;
			int maxImageHeight;
			ImageIcon icon = (ImageIcon) cell.getIcon();
			
			// The image will be centered and will fit all the cell
			maxImageWidth = (int) (this.getWidth() - 2 * (cell.getBorderSize() + HORIZONTAL_ICON_MARGIN));
			maxImageHeight = (int) (this.getHeight() - 2 * (cell.getBorderSize() + VERTICAL_ICON_MARGIN));
			
			if ((textPosition != SwingConstants.CENTER) && !cell.getText().equals("")) {
				
				// The image will be set with a margin
				this.setIconTextGap(GAP_ICON_TEXT);

				maxImageWidth = this.getWidth() - 2
						* (HORIZONTAL_ICON_MARGIN + (int) cell.getBorderSize()) - GAP_ICON_TEXT;
				maxImageHeight = this.getHeight() - 2
						* (VERTICAL_ICON_MARGIN + (int) cell.getBorderSize()) - GAP_ICON_TEXT -
						cell.getFont().getSize();
			}

			if (cell.getIcon().getIconHeight() > maxImageHeight){
				icon = new ImageIcon(cell.getDefaultIcon().getImage().getScaledInstance(-1,
						maxImageHeight, Image.SCALE_SMOOTH));
			}
			if (cell.getIcon().getIconWidth() > maxImageWidth){
				icon = new ImageIcon(cell.getDefaultIcon().getImage().getScaledInstance(
						maxImageWidth, -1, Image.SCALE_SMOOTH));
			}
			
			this.setIcon(icon);
		}
		return this;
	}

	/**
	 * Gets the sound of the accumulated cell.
	 * 
	 * @return the sound of the <code>TInterpreterAccumulatedCell</code>
	 */
	public String getSound() {
		return sound;
	}
	
	
}
