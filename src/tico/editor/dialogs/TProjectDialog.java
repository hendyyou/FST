/*
 * File: TProjectDialog.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Sep 4, 2006
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
package tico.editor.dialogs;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import tico.board.TProject;
import tico.components.TInitialBoardSelectionPanel;
import tico.configuration.TLanguage;
import tico.editor.TEditor;

/**
 * Dialog to change <code>TBoard</code> properties.
 * 
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TProjectDialog extends TPropertiesDialog {

	private TProject currentProject;
	
	private TInitialBoardSelectionPanel initialBoardPanel;

	/**
	 * Creates a new <code>TProjectDialog</code> to edit the editor's
	 * <code>project</code> properties.
	 * 
	 * @param editor The <code>editor</code> which contains the project
	 * properties to be edited
	 */
	public TProjectDialog(TEditor editor) {
		super(editor);
		setTitle(TLanguage.getString("TProjectDialog.TITLE"));

		currentProject = editor.getProject();
		createTabbedPane();

		setVisible(true);
	}

	// Create the main dialog tabbed pane
	private void createTabbedPane() {
		getPropertiesPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab(TLanguage.getString("TProjectDialog.TAB_PROPERTIES"),
				createPropertiesPanel());

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 1;
		getPropertiesPane().add(tabbedPane, c);
	}

	// Create the project properties panel
	private JPanel createPropertiesPanel() {
		JPanel preferencesPanel = new JPanel();

		GridBagConstraints c = new GridBagConstraints();
		preferencesPanel.setLayout(new GridBagLayout());

		createInitialBoardPanel();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		preferencesPanel.add(initialBoardPanel, c);

		return preferencesPanel;
	}
	
	// Creates the initial board panel
	private void createInitialBoardPanel() {
		ArrayList boardList = currentProject.getBoardList();
		
		initialBoardPanel = new TInitialBoardSelectionPanel(boardList);
		
		initialBoardPanel.setInitialBoard(currentProject.getInitialBoard());
	}

	/* (non-Javadoc)
	 * @see tico.editor.dialogs.TPropertiesDialog#applyValues()
	 */
	protected boolean applyValues() {
		if (!currentProject.getInitialBoard().equals(initialBoardPanel.getInitialBoard()))
			currentProject.setInitialBoard(initialBoardPanel.getInitialBoard());
		return true;
	}
}
