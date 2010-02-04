/*
 * File: TInterpreterCell.java
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
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import tico.interpreter.TInterpreterConstants;

public class TInterpreterCell extends JButton{
	
	private final static int VERTICAL_ICON_MARGIN = 15;
	
	private final static int HORIZONTAL_ICON_MARGIN = 10;
	// Default distance between the icon and the text
	private final static int GAP_ICON_TEXT = 5;
	
	//private String id;
	//private Rectangle bounds;
	//public String text;
	public Font font;
	public Color textColor;
	public int verticalTextPosition;
	public ImageIcon icon;
	public float borderSize;
	public Color borderColor;
	public Color backgroundColor;
	public boolean transparentBackground;
	public boolean transparentBorder;
	public boolean accumulated;
	public String textAreaToSend = null;
	public int timeSending;
	public String textToSend;

	public int altBorderSize;
	public Color altBorderColor = null;
	private ImageIcon alternativeIcon = null;

	public String boardToGo = null;
	public String soundPath = null;
	public String videoPath = null;
	public int xVideo = -1;
	public int yVideo = -1;
	public String command = null;
	
	public Point center = new Point();
		
	public TInterpreterCell (){
		super();
	}
	
	/*public ImageIcon getIcon() {
		return icon;
	}*/

	/*public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}*/

	public float getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}
	
	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public String getCommand(){
		return command;
	}
	
	public boolean isTransparentBorder() {
		return transparentBorder;
	}
	
	public boolean isTransparentBackground() {
		return transparentBackground;
	}
	
	public void setAccumulated(boolean accumulated) {
		this.accumulated = accumulated;
	}
	
	public boolean isAccumulated() {
		return accumulated;
	}
	
	public String getTextAreaToSend() {
		return textAreaToSend;
	}
	
	public void setTextAreaToSend(String textArea) {
		this.textAreaToSend = textArea;
	}
	
	public int getTimeSending() {
		return timeSending;
	}

	public void setTimeSending(int timeSending) {
		this.timeSending = timeSending;
	}

	public String getTextToSend() {
		return textToSend;
	}

	public void setTextToSend(String textToSend) {
		this.textToSend = textToSend;
	}
	
	public int getAlternativeBorderSize() {
		return altBorderSize;
	}
	
	public void setAlternativeBorderSize(int borderSize) {
		this.altBorderSize = borderSize;
	}
	
	public Color getAlternativeBorderColor() {
		return altBorderColor;
	}
	
	public void setAlternativeBorderColor(Color borderColor) {
		this.altBorderColor = borderColor;
	}
	
	public ImageIcon getAlternativeIcon() {
		return alternativeIcon;
	}
	
	public void setAlternativeIcon(ImageIcon icon) {
		this.alternativeIcon = icon;
	}
	
	public ImageIcon getDefaultIcon() {
		return icon;
	}
	
	public void setDefaultIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public String getBoardToGo() {
		return boardToGo;
	}

	public void setBoardToGo(String boardToGo) {
		this.boardToGo = boardToGo;
	}
	
	public String getSoundPath() {
		return soundPath;
	}
	
	public String getVideoPath() {
		return videoPath;
	}
	
	public int getXVideo() {
		return xVideo;
	}

	public void setXVideo(int xVideo) {
		this.xVideo = xVideo;
	}
	
	public int getYVideo() {
		return yVideo;
	}

	public void setYVideo(int yVideo) {
		this.yVideo = yVideo;
	}

	
	public TInterpreterCell setAttributes(String id, Rectangle bounds, String text, Font font, Color textColor, 
			int verticalTextPosition, ImageIcon icon, float borderSize, Color borderColor, Color backgroundColor, 
			boolean transBackground, boolean transBorder, ImageIcon alternativeIcon){
		
		this.setName(id);
		this.setBounds(bounds);
		this.setText(text);
		this.setFont(font);
		this.setForeground(textColor);
		
		// Apply text align properties
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalTextPosition(verticalTextPosition);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		
		if (icon != null) {			
			
			int maxImageWidth;
			int maxImageHeight;
			
			// The image will be centered and will fit all the cell
			maxImageWidth = (int) (bounds.width - 2 * (borderSize + HORIZONTAL_ICON_MARGIN));
			maxImageHeight = (int) (bounds.height - 2 * (borderSize + VERTICAL_ICON_MARGIN));
			
			if ((verticalTextPosition != SwingConstants.CENTER) && !text.equals("")) {
				
				// The image will be set with a margin
				this.setIconTextGap(GAP_ICON_TEXT);

				maxImageWidth = bounds.width - 2
						* (HORIZONTAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT;
				maxImageHeight = bounds.height - 2
						* (VERTICAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT -
						font.getSize();
			}

			if (icon.getIconHeight() > maxImageHeight)
				icon = new ImageIcon(icon.getImage().getScaledInstance(-1,
						maxImageHeight, Image.SCALE_DEFAULT));
			if (icon.getIconWidth() > maxImageWidth)
				icon = new ImageIcon(icon.getImage().getScaledInstance(
						maxImageWidth, -1, Image.SCALE_DEFAULT));
			
			this.setIcon(icon);
				
		}	
		
		this.icon = icon;		
		this.borderSize = borderSize;
		this.borderColor = borderColor;
		this.backgroundColor = backgroundColor;
		this.transparentBackground = transBackground;
		this.transparentBorder = transBorder;
		
		if (transparentBackground){
			this.setFocusPainted(false);
			this.setContentAreaFilled(false);
		}
		else{
			this.setOpaque(true);
			this.setBackground(backgroundColor);
		}

		if (transparentBorder){
			this.setBorderPainted(false);
		}
		else{
			this.setBorder(new LineBorder(borderColor, (int)borderSize));
		}
		
		if (alternativeIcon != null) {
			int textPosition = verticalTextPosition;
			
			int maxAImageWidth;
			int maxAImageHeight;
			
			
			// The image will be centered and will fit all the cell
			maxAImageWidth = bounds.width - 2 * ((int) borderSize + HORIZONTAL_ICON_MARGIN);
			maxAImageHeight = bounds.height - 2 * ((int) borderSize + VERTICAL_ICON_MARGIN);
			
			if ((textPosition != SwingConstants.CENTER) && !text.equals("")) {
				// The image will be set with a margin
				this.setIconTextGap(GAP_ICON_TEXT);

				maxAImageWidth = bounds.width - 2
						* (HORIZONTAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT;
				maxAImageHeight = bounds.height - 2
						* (VERTICAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT -
						font.getSize();
			}

			if (alternativeIcon.getIconHeight() > maxAImageHeight)
				alternativeIcon = new ImageIcon(alternativeIcon.getImage().getScaledInstance(-1,
						maxAImageHeight, Image.SCALE_DEFAULT));
			if (alternativeIcon.getIconWidth() > maxAImageWidth)
				alternativeIcon = new ImageIcon(alternativeIcon.getImage().getScaledInstance(
						maxAImageWidth, -1, Image.SCALE_DEFAULT));
			
			this.alternativeIcon = alternativeIcon;
		}
		
		return this;
	}
	
	/*public TInterpreterCell setAlternativeAttributes (ImageIcon alternativeIcon){

		if (alternativeIcon != null) {
			
			int maxAImageWidth;
			int maxAImageHeight;
			
			// The image will be centered and will fit all the cell
			maxAImageWidth = this.getBounds().width - 2 * ((int) borderSize + HORIZONTAL_ICON_MARGIN);
			maxAImageHeight = this.getBounds().height - 2 * ((int) borderSize + VERTICAL_ICON_MARGIN);
			
			if ((this.verticalTextPosition != SwingConstants.CENTER) && !this.getText().equals("")) {
				// The image will be set with a margin
				this.setIconTextGap(GAP_ICON_TEXT);

				maxAImageWidth = this.getBounds().width - 2
						* (HORIZONTAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT;
				maxAImageHeight = this.getBounds().height - 2
						* (VERTICAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT -
						this.getFont().getSize();
			}

			if (alternativeIcon.getIconHeight() > maxAImageHeight)
				alternativeIcon = new ImageIcon(alternativeIcon.getImage().getScaledInstance(-1,
						maxAImageHeight, Image.SCALE_DEFAULT));
			if (alternativeIcon.getIconWidth() > maxAImageWidth)
				alternativeIcon = new ImageIcon(alternativeIcon.getImage().getScaledInstance(
						maxAImageWidth, -1, Image.SCALE_DEFAULT));
		}
		
		this.alternativeIcon = alternativeIcon;
		return this;
	}*/
	
	public TInterpreterCell setActionsAttributes(String soundPath, String videoPath, String command){
		this.soundPath = soundPath;
		this.videoPath = videoPath;
		this.command = command;
		return this;
	}
	
	/*public TInterpreterCell setAttributes(String command, String id,Color borderColor, float borderSize, Color backgroundColor,  Rectangle bounds, String texto, Font f, Color textColor, ImageIcon icon,ImageIcon alternativeIcon,  int vtp,  TInterpreterConstants tic, boolean trans, boolean transBorder){
		
		this.command = command;
		this.backgroundColor = backgroundColor;
		this.altBorderSize = tic.lineChangeWidth;
		this.altBorderColor = tic.changeColor;
		this.textColor= textColor;
		this.soundPath= tic.soundFile;
		this.verticalTextPosition= vtp;
		this.font=f;
		this.text=texto;
				
		this.borderSize= (int) borderSize;
		this.borderColor= borderColor;
		transparentBackground= trans;
		transparentBorder= transBorder;
		
		if (trans){
			this.setFocusPainted(false);
			this.setContentAreaFilled(false);
		}
		else{
			this.setOpaque(true);
			this.setBackground(backgroundColor);
		}
		this.setForeground(textColor);
		if (transBorder){
			this.setBorderPainted(false);
		}
		else{
			this.setBorder(new LineBorder(borderColor, (int)borderSize));
		}
		this.setName(id);
		this.setText(texto);
		this.setBounds(bounds);
		
		this.setFont(f);
		
		
		
		// Apply text align properties
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalTextPosition(vtp);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		
		// icon
		if (icon != null) {			
			int textPosition = vtp;
			
			int maxImageWidth;
			int maxImageHeight;
			
			// The image will be centered and will fit all the cell
			maxImageWidth = (int) (bounds.width - 2 * (borderSize + HORIZONTAL_ICON_MARGIN));
			maxImageHeight = (int) (bounds.height - 2 * (borderSize + VERTICAL_ICON_MARGIN));
			
			if ((textPosition != SwingConstants.CENTER) && !texto.equals("")) {
				
				// The image will be set with a margin
				this.setIconTextGap(GAP_ICON_TEXT);

				maxImageWidth = bounds.width - 2
						* (HORIZONTAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT;
				maxImageHeight = bounds.height - 2
						* (VERTICAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT -
						f.getSize();
			}

			if (icon.getIconHeight() > maxImageHeight)
				icon = new ImageIcon(icon.getImage().getScaledInstance(-1,
						maxImageHeight, Image.SCALE_DEFAULT));
			if (icon.getIconWidth() > maxImageWidth)
				icon = new ImageIcon(icon.getImage().getScaledInstance(
						maxImageWidth, -1, Image.SCALE_DEFAULT));
			
			
			this.setIcon(icon);
			tic.OriginalIcon = icon;			
			
		}
		// alternativeIcon
		if (alternativeIcon != null) {
			int textPosition = vtp;
			
			int maxAImageWidth;
			int maxAImageHeight;
			
			
			// The image will be centered and will fit all the cell
			maxAImageWidth = bounds.width - 2 * ((int) borderSize + HORIZONTAL_ICON_MARGIN);
			maxAImageHeight = bounds.height - 2 * ((int) borderSize + VERTICAL_ICON_MARGIN);
			
			if ((textPosition != SwingConstants.CENTER) && !texto.equals("")) {
				// The image will be set with a margin
				this.setIconTextGap(GAP_ICON_TEXT);

				maxAImageWidth = bounds.width - 2
						* (HORIZONTAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT;
				maxAImageHeight = bounds.height - 2
						* (VERTICAL_ICON_MARGIN + (int) borderSize) - GAP_ICON_TEXT -
						f.getSize();
			}

			if (alternativeIcon.getIconHeight() > maxAImageHeight)
				alternativeIcon = new ImageIcon(alternativeIcon.getImage().getScaledInstance(-1,
						maxAImageHeight, Image.SCALE_DEFAULT));
			if (alternativeIcon.getIconWidth() > maxAImageWidth)
				alternativeIcon = new ImageIcon(alternativeIcon.getImage().getScaledInstance(
						maxAImageWidth, -1, Image.SCALE_DEFAULT));
			
			tic.AlternativeIcon = alternativeIcon;
		}
		
		this.icon = icon;
		
		return this;
	}*/
	
	public TInterpreterCell setAttributes2(String id, Rectangle bounds, String texto, Font f, Color textColor, ImageIcon icon){
		
		this.textColor = textColor;
		this.font=f;
		
		this.borderSize = TInterpreterConstants.BORDER_SIZE;
		this.borderColor = TInterpreterConstants.BORDER_COLOR;
		
		this.setName(id);
		this.setForeground(textColor);
		this.setText(texto);
		this.setBounds(bounds);
		this.setBackground(TInterpreterConstants.BACKGROUND_COLOR);
		this.setBorder(new LineBorder(TInterpreterConstants.BORDER_COLOR, TInterpreterConstants.BORDER_SIZE));
		this.setFont(f);
		
		// Apply text align properties
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		
		// icon
		if (icon != null) {			
			
			int maxImageWidth;
			int maxImageHeight;
				
			// The image will be set with a margin
			this.setIconTextGap(GAP_ICON_TEXT);

			maxImageWidth = bounds.width - 2
						* (HORIZONTAL_ICON_MARGIN + (int) 1) - GAP_ICON_TEXT;
			maxImageHeight = bounds.height - 2
						* (VERTICAL_ICON_MARGIN + (int) 1) - GAP_ICON_TEXT -
						f.getSize();
			

			if (icon.getIconHeight() > maxImageHeight)
				icon = new ImageIcon(icon.getImage().getScaledInstance(-1,
						maxImageHeight, Image.SCALE_DEFAULT));
			if (icon.getIconWidth() > maxImageWidth)
				icon = new ImageIcon(icon.getImage().getScaledInstance(
						maxImageWidth, -1, Image.SCALE_DEFAULT));
			
			
			this.setIcon(icon);
			
		}
		
		this.icon = icon;
		
		return this;
	}
	
	
}