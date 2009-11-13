/*
 * File: TEnvironment.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Antonio Rodriguez
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

package tico.environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.MissingResourceException;

import java.util.Vector;

import tico.components.resources.TFileUtils;
import tico.configuration.TResourceBundle;

public class TEnvironment {

	private static String ENVIRONMENT_DIRECTORY = "environment";

	private static String currentEnvironment = "entorno";

	// Languages map between its names and its file
	private static Map environmentMap = getEnvironment();

	// ResourceBundle with the currentLanguage file
	private static TResourceBundle ENVIRONMENT_BUNDLE = null;

	/**	
	 * Inits the language file loading the file of the specified
	 * <code>language</code> to the <code>LANGUAGE_BUNDLE</code>.
	 * 
	 * @param language
	 *            The current <code>language</code>
	 * @throws IOException
	 */
	public static void initEnvironment(String environment) throws IOException {
		environmentMap = getEnvironment();
		if ((environmentMap == null) || environmentMap.isEmpty())
			throw new FileNotFoundException();

		currentEnvironment = "entorno";
		if ((currentEnvironment == null)
				|| !environmentMap.containsKey(currentEnvironment)) {
			// Set the first language on the map
			Iterator environIterator = environmentMap.entrySet().iterator();
			Map.Entry environEntry = (Map.Entry) environIterator.next();
			currentEnvironment = (String) environEntry.getKey();
		}

		InputStream i = new FileInputStream((File) environmentMap
				.get(currentEnvironment));
		ENVIRONMENT_BUNDLE = new TResourceBundle(i);
	}

	/**
	 * Determines if exists the <code>language</code> file.
	 * 
	 * @param language
	 *            The <code>language</code> file to check
	 * @return <i>true</i> if the <code>language</code> file exists
	 */
	public static boolean environmentExists(String environment) {
		Map environmentMap = getEnvironment();

		return environmentMap.containsKey(environment);
	}

	/**
	 * Generates a <code>map</code> that contains all the language files of
	 * <i>lang</i> directory and its corresponding language names.
	 * 
	 * @return The generated <code>map</code>
	 */
	public static Map getEnvironment() {
		Map environmentMap = new Hashtable();

		File environmentDirectory = new File(ENVIRONMENT_DIRECTORY);
		File[] fileList = environmentDirectory.listFiles();

		for (int i = 0; i < fileList.length; i++) {
			try {
				if (TFileUtils.getExtension(fileList[i]).equals("txt")) {
					TResourceBundle currentEnvironmentBundle = new TResourceBundle(
							new FileInputStream(fileList[i]));
					environmentMap.put(currentEnvironmentBundle
							.getString("entorno"), fileList[i]);
				}
			} catch (IOException e) {
			}
		}

		return environmentMap;
	}

	/**
	 * Returns the string of the specified <code>key</code> in the current
	 * language resource bundle.
	 * 
	 * @param key
	 *            The specified <code>key</code>
	 * @return The string of the specified <code>key</code>
	 */
	public static String getCode(String key) {
		try {
			return ENVIRONMENT_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		} catch (NullPointerException e) {
			return '!' + key + '!';
		}
	}

	public TResourceBundle getEnvironmentBundle() {
		return ENVIRONMENT_BUNDLE;
	}

	public static Vector getAllKeys() {
		Enumeration algo = ENVIRONMENT_BUNDLE.getKeys();
		Vector keys = new Vector();
		int i = 0;
		while (algo.hasMoreElements()) {
			String cadena = algo.nextElement().toString();
			if (!cadena.equals("entorno")) {
				keys.add(i, cadena);
				i++;
			}
		}

		return keys;
	}
}
