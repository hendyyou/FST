/*
 * File: TSetup.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Feb 19, 2006
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
package tico.configuration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Static class that manages the application configuration. It allows to
 * write and read from a XML file (<i>conf/tico.conf</i>).
 *
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TSetup {
	private static String CONFIGURATION_FILE_PATH = "conf" + File.separator
			+ "tico.conf";

	private static File CONFIGURATION_FILE = new File(CONFIGURATION_FILE_PATH);

	private static String language = "Español";

	/**
	 * Loads the configuration file <i>conf/tico.conf</i>
	 * 
	 * @throws ParserConfigurationException If there are syntactic error
	 * in the XML document
	 * @throws SAXException If there are problems transforming the
	 * text to a XML document
	 * @throws IOException If there are file problems
	 */
	public static void load() throws ParserConfigurationException,
			SAXException, IOException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

		Document doc = docBuilder.parse(CONFIGURATION_FILE);

		Element preferencesElement = doc.getDocumentElement();
		if (preferencesElement.getTagName().equals("preferences")) {
			NodeList languageNodeList = preferencesElement.getElementsByTagName("language");
			if (languageNodeList.getLength() > 0) {
				String language = languageNodeList.item(0).getChildNodes().item(0).getNodeValue();
				if (language != null)
					if (TLanguage.languageExists(language))
					setLanguage(language);
			}
		}
	}
	
	/**
	 * Saves the configuration file <i>conf/tico.conf</i>
	 * 
	 * @throws ParserConfigurationException If there are syntactic error
	 * in the XML document
	 * @throws IOException If there are file problems
	 * @throws TransformerException If there are problems transforming the
	 * XML document to text
	 */
	public static void save() throws ParserConfigurationException, IOException,
			TransformerException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		Document doc = domBuilder.newDocument();

		// Create properties node
		Element projectElement = doc.createElement("preferences");
		// Create language node
		Element languageElement = doc.createElement("language");
		languageElement.appendChild(doc.createTextNode(getLanguage()));
		// Append language node
		projectElement.appendChild(languageElement);
		// Append preferences node
		doc.appendChild(projectElement);

		// Write the file
		BufferedWriter bufferWriter = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(CONFIGURATION_FILE), "UTF-8"));

		// Use a Transformer for output
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(bufferWriter);
		transformer.transform(source, result);
		bufferWriter.close();
	}
	
	/**
	 * Returns the application <code>language</code>.
	 * 
	 * @return The application <code>language</code>
	 */
	public static String getLanguage() {
		return language;
	}

	/**
	 * Sets the application <code>language</code>.
	 * 
	 * @param language The application <code>language</code> to set
	 */
	public static void setLanguage(String language) {
		TSetup.language = language;
	}
}
