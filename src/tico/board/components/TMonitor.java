/*
 * File: TLabel.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Mar 6, 2006
 * 
 * Company: Universidad de Zaragoza, CPS, DIIS
 * 
 * License:
 * 		This program is free software: you can redistribute it and/or 
 * 		modify it under the terms of the GNU General Public License 
 * 		as published by the Free Software Foundation, either version 3
 * 		of the License, or (at your option) any later version.
 * 
 * 		This program is distributed in the hope that it will be useful,
 * 		but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 		GNU General Public License for more details.
 * 
 * 		You should have received a copy of the GNU General Public License
 *     	along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

package tico.board.components;

import org.jgraph.graph.AttributeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tico.board.TBoardConstants;
import tico.board.encoding.InvalidFormatException;
import tico.board.encoding.TAttributeEncoder;
import tico.configuration.TLanguage;

/**
 * Defines a <code>TLabel</code> and its attributes.
 *
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TMonitor extends TComponent {
	/**
	 * The <code>TCell</code> type used as attribute in XML encoding and decoding.
	 */
	public static String TYPE = "monitor";
	
	private static int monitorCount = 0;
	
	private static String DEFAULT_TEXT = TLanguage.getString("TMonitor.DEFAULT_TEXT");
	
	/**
	 * Creates a new <code>TLabel</code>.
	 */
	public TMonitor() {
		super();
		setAllowsChildren(false);
		
		TBoardConstants.setSizeable(getAttributes(),true);
		TBoardConstants.setResizeToText(getAttributes(),false);
		TBoardConstants.setText(getAttributes(),DEFAULT_TEXT);
		
	}
	
	/* (non-Javadoc)
	 * @see tico.board.components.TComponent#newId()
	 */
	public String newId() {
    	return "monitor_" + new Integer(monitorCount++);
	}
	
	/* (non-Javadoc)
	 * @see tico.board.components.TComponent#XMLEncode(org.w3c.dom.Document)
	 */
	public Node XMLEncode(Document doc) {
		// Create component node
		Element componentElement = doc.createElement("component");
		componentElement.setAttribute("type", TYPE);

		// Delete default attributes
		AttributeMap map = (AttributeMap)getAttributes().clone();
		map.remove(TBoardConstants.RESIZE_TO_TEXT);
		map.remove(TBoardConstants.SIZEABLE);
		// Append component attributes
		componentElement.appendChild(TAttributeEncoder.XMLEncode(map, doc));
		
		// Return the component node
		return componentElement;
	}
	
	/**
	 * Returns a <code>TLabel</code> object from the data contained in
	 * the XML <code>Element</code>.
	 * 
	 * @param element The XML <code>Element</code> that contains the label data
	 * @return The generated <code>TLabel</code>
	 * @throws InvalidFormatException If <code>Element</code> has an invalid format
	 */
	public static TMonitor XMLDecode(Element element) throws InvalidFormatException {
		if (element.getTagName().equals("component")
				&& element.getAttribute("type").equals(TYPE)) {
			// Create the empty label
			TMonitor label = new TMonitor();
			// Get the attributes
			Node attributesNode = element.getElementsByTagName("attributes")
					.item(0);
			// Apply attributes to the label
			label.getAttributes().applyMap(TAttributeEncoder
					.XMLDecode((Element)attributesNode));
			// Return it
			return label;
		}
		return null;
	}
}
