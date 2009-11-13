/*
 * File: TInterpreterDialog.java
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

package tico.interpreter.dialog;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import tico.components.TLanguageComboBox;
import tico.configuration.TLanguage;
import tico.configuration.TSetup;
import tico.editor.TEditor;
import tico.editor.dialogs.*;
import tico.interpreter.*;



public class TInterpreterDialog extends TProyectInterpreterDialog {

		private JPanel languageChooserPanel;

		private TLanguageComboBox languageComboBox;

		/**
		 * Creates a new <code>TEditorDialog</code> to edit the
		 * <code>editor</code> preferences.
		 * 
		 * @param editor
		 *            The <code>editor</code> to edit
		 */
		public TInterpreterDialog (TInterpreter interpreter) {
			super(interpreter);
			setTitle(TLanguage.getString("TEditorDialog.TITLE"));

			createTabbedPane();

			setVisible(true);
		}

		// Create the main dialog tabbed pane
		private void createTabbedPane() {
			getPropertiesPane().setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();

			JTabbedPane tabbedPane = new JTabbedPane();
			/*Creates one Tab of preferences*/
			tabbedPane.addTab(TLanguage.getString("TEditorDialog.TAB_PREFERENCES"),
					createPreferencesPanel());
			
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(0, 5, 5, 5);
			c.gridx = 0;
			c.gridy = 1;
			getPropertiesPane().add(tabbedPane, c);
		}

		// Create the editor preferences panel
		private JPanel createPreferencesPanel() {
			JPanel preferencesPanel = new JPanel();

			GridBagConstraints c = new GridBagConstraints();
			preferencesPanel.setLayout(new GridBagLayout());

			createLanguageChooser();

			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(5, 10, 10, 10);
			c.gridx = 0;
			c.gridy = 0;
			preferencesPanel.add(languageChooserPanel, c);

			return preferencesPanel;
		}

		private void createLanguageChooser() {
			languageChooserPanel = new JPanel();

			languageChooserPanel.setBorder(new TitledBorder(BorderFactory
					.createEtchedBorder(Color.black, new Color(165, 163, 151)),
					TLanguage.getString("TEditorDialog.LANGUAGE")));

			JLabel languageLabel = new JLabel(TLanguage
					.getString("TEditorDialog.LANGUAGE"));
			languageComboBox = new TLanguageComboBox(TSetup.getLanguage());

			GridBagConstraints c = new GridBagConstraints();
			languageChooserPanel.setLayout(new GridBagLayout());

			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(5, 15, 10, 0);
			c.gridx = 0;
			c.gridy = 0;
			languageChooserPanel.add(languageLabel, c);

			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(5, 15, 10, 15);
			c.gridx = 1;
			c.gridy = 0;
			languageChooserPanel.add(languageComboBox, c);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see tico.board.dialogs.TPropertiesDialog#applyValues()
		 * Set actual language
		 */
		protected boolean applyValues() {
			if (!TSetup.getLanguage().equals(languageComboBox.getLanguage())) {
				TSetup.setLanguage(languageComboBox.getLanguage());
				
				JOptionPane.showMessageDialog(null,
						TLanguage.getString("TEditorDialog.LANGUAGE_CHANGE_ADVICE"),
						TLanguage.getString("TEditorDialog.LANGUAGE_CHANGE"),
						JOptionPane.INFORMATION_MESSAGE);
			}

			return true;
		}
	}


