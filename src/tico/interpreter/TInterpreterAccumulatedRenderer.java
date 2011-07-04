/*
 * File: TInterpreterAccumulatedRenderer.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Antonio Rodríguez
 * 
 * Date: May 23, 2007
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
package tico.interpreter;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.jgraph.graph.CellView;

import tico.board.TBoardConstants;

/**
 * <code>Swing</code> which implements the <code>TCell</code> visualization.
 * 
 * @author Antonio Rodriguez
 */
public class TInterpreterAccumulatedRenderer extends JLabel {

	private final static int VERTICAL_ICON_MARGIN = 15;

	private final static int HORIZONTAL_ICON_MARGIN = 10;

	// Default distnace between the icon and the text
	private final static int GAP_ICON_TEXT = 5;

	private int borderWidth;

	private Color gradientColor;

	public TInterpreterAccumulatedRenderer(CellView view) {
		installAttributes(view);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jgraph.graph.VertexRenderer#installAttributes(org.jgraph.graph.CellView)
	 */
	protected void installAttributes(CellView view) {
		// Get the cell attributes
		Map map = view.getAllAttributes();

		// Apply all the component properties
		// Apply border
		Color borderColor = TBoardConstants.getBorderColor(map);
		if (borderColor != null) {
			borderWidth = Math.max(1, Math.round(TBoardConstants
					.getLineWidth(map)));
			setBorder(BorderFactory.createLineBorder(borderColor, borderWidth));
		} else {
			borderWidth = 0;
			setBorder(null);
		}

		// Apply background and gradient
		Color backgroundColor = TBoardConstants.getBackground(map);
		setBackground(backgroundColor);
		setOpaque((backgroundColor != null));

		gradientColor = TBoardConstants.getGradientColor(map);

		// Apply text font properties
		Font font = TBoardConstants.getFont(map);
		if (font == null)
			font = TBoardConstants.DEFAULTFONT;
		setFont(TBoardConstants.getFont(map));

		Color foregroundColor = TBoardConstants.getForeground(map);
		setForeground((foregroundColor != null) ? foregroundColor
				: TBoardConstants.DEFAULTFOREGROUND);

		// Apply text align properties
		setVerticalAlignment(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
		setVerticalTextPosition(TBoardConstants.getVerticalTextPosition(map));
		setHorizontalTextPosition(SwingConstants.CENTER);

		// Apply text properties
		String text = TBoardConstants.getText(map);
		if (text == null)
			text = "";
		setText(text);

		// Save the icon to be painted in the paint function
		ImageIcon icon = (ImageIcon) TBoardConstants.getIcon(map);

		if (icon != null) {
			int textPosition = TBoardConstants.getVerticalTextPosition(map);
			// modify by toty
			int cellWidth = 100;
			int cellHeight = 100;

			int maxImageWidth;
			int maxImageHeight;

			// The image will be centered and will fit all the cell
			maxImageWidth = cellWidth - 2
					* (borderWidth + HORIZONTAL_ICON_MARGIN);
			maxImageHeight = cellHeight - 2
					* (borderWidth + VERTICAL_ICON_MARGIN);

			if ((textPosition != SwingConstants.CENTER) && !text.equals("")) {
				// The image will be set with a margin
				setIconTextGap(GAP_ICON_TEXT);

				maxImageWidth = cellWidth - 2
						* (HORIZONTAL_ICON_MARGIN + borderWidth)
						- GAP_ICON_TEXT;
				maxImageHeight = cellHeight - 2
						* (VERTICAL_ICON_MARGIN + borderWidth) - GAP_ICON_TEXT
						- getFont().getSize();
			}

			if (icon.getIconHeight() > maxImageHeight)
				icon = new ImageIcon(icon.getImage().getScaledInstance(-1,
						maxImageHeight, Image.SCALE_DEFAULT));
			if (icon.getIconWidth() > maxImageWidth)
				icon = new ImageIcon(icon.getImage().getScaledInstance(
						maxImageWidth, -1, Image.SCALE_DEFAULT));
		}

		setIcon(icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Draw the gradient
		if (gradientColor != null && isOpaque()) {
			setOpaque(false);
			g2.setPaint(new GradientPaint(0, 0, getBackground(), getWidth(),
					getHeight(), gradientColor, true));
			g2.fillRect(0, 0, getWidth(), getHeight());
		}

		super.paint(g);
	}
}