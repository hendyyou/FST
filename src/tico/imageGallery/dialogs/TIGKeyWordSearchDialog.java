/*
 * File: TIGKeyWordSearchDialog.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Feb 19, 2008
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

package tico.imageGallery.dialogs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tico.components.TButton;
import tico.components.TComboBox;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.imageGallery.dataBase.TIGDataBase;

/*
 * This class displays a panel for searching images in the Data Base associated and, or 
 * or not to the key words selected. To see more detailed explanation go to the user manual.
 */
public class TIGKeyWordSearchDialog {
	
	protected TComboBox list1;
	
	protected TComboBox list2;
	
	protected TComboBox list3;
	
	protected String keyWord1;
	
	protected String keyWord2;
	
	protected String keyWord3;
	
	protected TComboBox options1;
	
	protected TComboBox options2;
	
	protected int SEARCH_OPTIONS_AND = 1;
	
	protected int SEARCH_OPTIONS_OR = 2;
	
	protected int SEARCH_OPTIONS_NOT = 3;
	
	protected int searchOptions1;
	
	protected int searchOptions2;
	
	protected Vector keyWordList = new Vector();
	
	protected Vector optionsList = new Vector(4);
	
	protected TEditor editor;
	
	protected TIGDataBase dataBase;
	
	public Vector result;
	
	private TIGImageGalleryDialog myDialog;
	
	private TIGModifyImageDialog mySearchDialog;
	private TIGDeleteImagesDialog mySearchDialogDelete;
	private TIGExportDBDialog mySearchDialogExport;
	
	public TIGKeyWordSearchDialog(TEditor editor, TIGDataBase dataBase){
		this.editor = editor;
		this.dataBase = dataBase;		
	}
	
	
	
	protected JPanel createKeyWordPanel(TIGImageGalleryDialog dialog){ 
		
		this.myDialog = dialog;
		
		JPanel keyWordPanel = new JPanel();
		
		keyWordPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGKeyWordSearchDialog.CATEGORY_SEARCH")));
		
		keyWordList = dataBase.getKeyWords();
		//keyWord1 = (String) keyWordList.elementAt(0);
		
		list1 = new TComboBox(keyWordList);
		list1.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				System.out.println("list1");
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
					keyWord1 = tf.getText();
				}
			}
		});
		list1.setEditable(true);
		list1.setPreferredSize(new Dimension (125, 25));
						
		list2 = new TComboBox(keyWordList);
		keyWord2 = "";
		list2.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				System.out.println("list2");
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					JTextField tf = (JTextField)list2.getEditor().getEditorComponent();
					keyWord2 = tf.getText();
				}
			}
		});
		list2.setEditable(true);
		list2.setPreferredSize(new Dimension (125, 25));
		
		list3 = new TComboBox(keyWordList);
		keyWord3 = "";
		list3.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				System.out.println("list3");
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
					keyWord3 = tf.getText();
				}
			}
		});
		list3.setEditable(true);
		list3.setPreferredSize(new Dimension (125, 25));
		
		optionsList.addElement("");
		optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.AND"));
		optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.OR"));
		optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.NOT"));
		
		options1 = new TComboBox(optionsList);
		options1.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				System.out.println("options1");
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					
					System.out.println("Entrooo");
					switch (options1.getSelectedIndex())
					{
						case 1: searchOptions1 = SEARCH_OPTIONS_AND;
								
								break;
						case 2: searchOptions1 = SEARCH_OPTIONS_OR;
								
								break;
						case 3: searchOptions1 = SEARCH_OPTIONS_NOT;
								
								break;
					}
				}
				else{
					if (options1.getSelectedIndex()== 0){
						list2.setEnabled(false);
						options2.setEnabled(false);
						list3.setEnabled(false);
					}
					else if (options1.getSelectedIndex()== 1){
						list2.setEnabled(true);
						options2.setEnabled(true);
					}
					else if (options1.getSelectedIndex()== 2){
						list2.setEnabled(true);
						options2.setEnabled(true);
					}
					else if (options1.getSelectedIndex()== 3){
						list2.setEnabled(true);
						options2.setEnabled(true);
					}
					
				}
			}
		});
		options1.setEditable(true);
		options1.setPreferredSize(new Dimension (50, 25));
		
		options2 = new TComboBox(optionsList);
		options2.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				System.out.println("options2");
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					switch (options2.getSelectedIndex())
					{
						case 1: searchOptions2 = SEARCH_OPTIONS_AND;
								break;
						case 2: searchOptions2 = SEARCH_OPTIONS_OR;
								break;
						case 3: searchOptions2 = SEARCH_OPTIONS_NOT;
					}
				}
				else{
					if (options2.getSelectedIndex()== 0){
						list3.setEnabled(false);
					}
					else if (options2.getSelectedIndex()== 1){
						list3.setEnabled(true);
					}
					else if (options2.getSelectedIndex()== 2){
						list3.setEnabled(true);
					}
					else if (options2.getSelectedIndex()== 3){
						list3.setEnabled(true);
					}
					
				}
			}
		});
		options2.setEditable(true);
		options2.setPreferredSize(new Dimension (50, 25));
		
		//Create button
		TButton searchButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Busco SQL");
				result = new Vector();
				keyWord1 = KeyWord1();
				keyWord2 = KeyWord2();
				keyWord3 = KeyWord3();;
				searchOptions1 = searchOptions1();
				searchOptions2 = searchOptions2();
				result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
				if (result.size()==0){
					// There are no results for that search
					
					JOptionPane.showConfirmDialog(null,
							TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
							TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
							JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
				}
				else{
					myDialog.update(result);
				}
			}
		});
		searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)){
					result = new Vector();
					keyWord1 = KeyWord1();
					keyWord2 = KeyWord2();
					keyWord3 = KeyWord3();;
					searchOptions1 = searchOptions1();
					searchOptions2 = searchOptions2();
					result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
					if (result.size()==0){
						// There are no results for that search
						System.out.println("No encontrado categorias3");
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
								TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						myDialog.update(result);
				}
			}
		});
		searchButton.setText(TLanguage.getString("TIGKeyWordSearchDialog.SEARCH"));
		list2.setEnabled(false);
		list3.setEnabled(false);
		options2.setEnabled(false);
		
		// Place components
		GridBagConstraints c = new GridBagConstraints();
		keyWordPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		keyWordPanel.add(list1, c);
			
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 1;
		c.gridy = 0;
		keyWordPanel.add(options1, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 2;
		c.gridy = 0;
		keyWordPanel.add(list2, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 3;
		c.gridy = 0;
		keyWordPanel.add(options2, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 4;
		c.gridy = 0;
		keyWordPanel.add(list3, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 5;
		c.gridy = 0;
		keyWordPanel.add(searchButton, c);
		
		return keyWordPanel;
	}
	
	protected JPanel createKeyWordDeletePanel(TIGDeleteImagesDialog dialog){ 
			
			this.mySearchDialogDelete = dialog;
			
			JPanel keyWordPanel = new JPanel();
			
			keyWordPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
					Color.WHITE, new Color(165, 163, 151)), 
					TLanguage.getString("TIGKeyWordSearchDialog.CATEGORY_SEARCH")));
			
			keyWordList = dataBase.getKeyWords();
			//keyWord1 = (String) keyWordList.elementAt(0);
			
			list1 = new TComboBox(keyWordList);
			list1.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
						keyWord1 = tf.getText();
					}
				}
			});
			list1.setEditable(true);
			list1.setPreferredSize(new Dimension (125, 25));
							
			list2 = new TComboBox(keyWordList);
			keyWord2 = "";
			list2.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						JTextField tf = (JTextField)list2.getEditor().getEditorComponent();
						keyWord2 = tf.getText();
					}
				}
			});
			list2.setEditable(true);
			list2.setPreferredSize(new Dimension (125, 25));
			
			list3 = new TComboBox(keyWordList);
			keyWord3 = "";
			list3.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
						keyWord3 = tf.getText();
					}
				}
			});
			list3.setEditable(true);
			list3.setPreferredSize(new Dimension (125, 25));
			
			optionsList.addElement("");
			optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.AND"));
			optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.OR"));
			optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.NOT"));
			
			options1 = new TComboBox(optionsList);
			options1.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						switch (options1.getSelectedIndex())
						{
							case 0: searchOptions1 = 0;
									break;
							case 1: searchOptions1 = SEARCH_OPTIONS_AND;
									break;
							case 2: searchOptions1 = SEARCH_OPTIONS_OR;
									break;
							case 3: searchOptions1 = SEARCH_OPTIONS_NOT;
						}
					}
					else{
						if (options1.getSelectedIndex()== 0){
							list2.setEnabled(false);
							options2.setEnabled(false);
							list3.setEnabled(false);
						}
						else if (options1.getSelectedIndex()== 1){
							list2.setEnabled(true);
							options2.setEnabled(true);
						}
						else if (options1.getSelectedIndex()== 2){
							list2.setEnabled(true);
							options2.setEnabled(true);
						}
						else if (options1.getSelectedIndex()== 3){
							list2.setEnabled(true);
							options2.setEnabled(true);
						}
						
					}
				}
			});
			options1.setEditable(true);
			options1.setPreferredSize(new Dimension (50, 25));
			
			options2 = new TComboBox(optionsList);
			options2.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						switch (options2.getSelectedIndex())
						{
							case 0: searchOptions2 = 0;
									break;
							case 1: searchOptions2 = SEARCH_OPTIONS_AND;
									break;
							case 2: searchOptions2 = SEARCH_OPTIONS_OR;
									break;
							case 3: searchOptions2 = SEARCH_OPTIONS_NOT;
						}
					}
					else{
						if (options2.getSelectedIndex()== 0){
							list3.setEnabled(false);
						}
						else if (options2.getSelectedIndex()== 1){
							list3.setEnabled(true);
						}
						else if (options2.getSelectedIndex()== 2){
							list3.setEnabled(true);
						}
						else if (options2.getSelectedIndex()== 3){
							list3.setEnabled(true);
						}
						
					}
				}
			});
			options2.setEditable(true);
			options2.setPreferredSize(new Dimension (50, 25));
			
			//Create button
			TButton searchButton = new TButton(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					result = new Vector();
					keyWord1 = KeyWord1();
					keyWord2 = KeyWord2();
					keyWord3 = KeyWord3();;
					searchOptions1 = searchOptions1();
					searchOptions2 = searchOptions2();
					result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
					if (result.size()==0){
						System.out.println("No encontrado categorias2");
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
								TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
								JOptionPane.CLOSED_OPTION);
					}
					else
						mySearchDialogDelete.update(result);
				}
			});
			searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
				@Override
				public void keyReleased(java.awt.event.KeyEvent e){
					if ((e.getKeyCode() == KeyEvent.VK_ENTER)){
						result = new Vector();
						keyWord1 = KeyWord1();
						keyWord2 = KeyWord2();
						keyWord3 = KeyWord3();;
						searchOptions1 = searchOptions1();
						searchOptions2 = searchOptions2();
						result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
						if (result.size()==0){
							System.out.println("No encontrado categorias");
							// There are no results for that search
							JOptionPane.showConfirmDialog(null,
									TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
									TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
									JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else
							mySearchDialogDelete.update(result);
					}
				}
			});
			searchButton.setText(TLanguage.getString("TIGKeyWordSearchDialog.SEARCH"));
			list2.setEnabled(false);
			list3.setEnabled(false);
			options2.setEnabled(false);
			
			// Place components
			GridBagConstraints c = new GridBagConstraints();
			keyWordPanel.setLayout(new GridBagLayout());
	
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 0;
			c.gridy = 0;
			keyWordPanel.add(list1, c);
				
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 1;
			c.gridy = 0;
			keyWordPanel.add(options1, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 2;
			c.gridy = 0;
			keyWordPanel.add(list2, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 3;
			c.gridy = 0;
			keyWordPanel.add(options2, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 4;
			c.gridy = 0;
			keyWordPanel.add(list3, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 5;
			c.gridy = 0;
			keyWordPanel.add(searchButton, c);
			
			return keyWordPanel;
		}
		
	protected JPanel createKeyWordExportPanel(TIGExportDBDialog dialog){ 
		
		this.mySearchDialogExport = dialog;
		
		JPanel keyWordPanel = new JPanel();
		
		keyWordPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGKeyWordSearchDialog.CATEGORY_SEARCH")));
		
		keyWordList = dataBase.getKeyWords();
		//keyWord1 = (String) keyWordList.elementAt(0);
		
		list1 = new TComboBox(keyWordList);
		list1.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
					keyWord1 = tf.getText();
				}
			}
		});
		list1.setEditable(true);
		list1.setPreferredSize(new Dimension (125, 25));
						
		list2 = new TComboBox(keyWordList);
		keyWord2 = "";
		list2.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					JTextField tf = (JTextField)list2.getEditor().getEditorComponent();
					keyWord2 = tf.getText();
				}
			}
		});
		list2.setEditable(true);
		list2.setPreferredSize(new Dimension (125, 25));
		
		list3 = new TComboBox(keyWordList);
		keyWord3 = "";
		list3.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
					keyWord3 = tf.getText();
				}
			}
		});
		list3.setEditable(true);
		list3.setPreferredSize(new Dimension (125, 25));
		
		optionsList.addElement("");
		optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.AND"));
		optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.OR"));
		optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.NOT"));
		
		options1 = new TComboBox(optionsList);
		options1.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					switch (options1.getSelectedIndex())
					{
						case 0: searchOptions1 = 0;
								break;
						case 1: searchOptions1 = SEARCH_OPTIONS_AND;
								break;
						case 2: searchOptions1 = SEARCH_OPTIONS_OR;
								break;
						case 3: searchOptions1 = SEARCH_OPTIONS_NOT;
					}
				}
				else{
					if (options1.getSelectedIndex()== 0){
						list2.setEnabled(false);
						options2.setEnabled(false);
						list3.setEnabled(false);
					}
					else if (options1.getSelectedIndex()== 1){
						list2.setEnabled(true);
						options2.setEnabled(true);
					}
					else if (options1.getSelectedIndex()== 2){
						list2.setEnabled(true);
						options2.setEnabled(true);
					}
					else if (options1.getSelectedIndex()== 3){
						list2.setEnabled(true);
						options2.setEnabled(true);
					}
					
				}
			}
		});
		options1.setEditable(true);
		options1.setPreferredSize(new Dimension (50, 25));
		
		options2 = new TComboBox(optionsList);
		options2.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e){
				if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
				{
					switch (options2.getSelectedIndex())
					{
						case 0: searchOptions2 = 0;
								break;
						case 1: searchOptions2 = SEARCH_OPTIONS_AND;
								break;
						case 2: searchOptions2 = SEARCH_OPTIONS_OR;
								break;
						case 3: searchOptions2 = SEARCH_OPTIONS_NOT;
					}
				}
				else{
					if (options2.getSelectedIndex()== 0){
						list3.setEnabled(false);
					}
					else if (options2.getSelectedIndex()== 1){
						list3.setEnabled(true);
					}
					else if (options2.getSelectedIndex()== 2){
						list3.setEnabled(true);
					}
					else if (options2.getSelectedIndex()== 3){
						list3.setEnabled(true);
					}
					
				}
			}
		});
		options2.setEditable(true);
		options2.setPreferredSize(new Dimension (50, 25));
		
		//Create button
		TButton searchButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				result = new Vector();
				keyWord1 = KeyWord1();
				keyWord2 = KeyWord2();
				keyWord3 = KeyWord3();;
				searchOptions1 = searchOptions1();
				searchOptions2 = searchOptions2();
				result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
				if (result.size()==0){
					System.out.println("No encontrado categorias2");
					// There are no results for that search
					JOptionPane.showConfirmDialog(null,
							TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
							TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
							JOptionPane.CLOSED_OPTION);
				}
				else
					mySearchDialogDelete.update(result);
			}
		});
		searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)){
					result = new Vector();
					keyWord1 = KeyWord1();
					keyWord2 = KeyWord2();
					keyWord3 = KeyWord3();;
					searchOptions1 = searchOptions1();
					searchOptions2 = searchOptions2();
					result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
					if (result.size()==0){
						System.out.println("No encontrado categorias");
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
								TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialogDelete.update(result);
				}
			}
		});
		searchButton.setText(TLanguage.getString("TIGKeyWordSearchDialog.SEARCH"));
		list2.setEnabled(false);
		list3.setEnabled(false);
		options2.setEnabled(false);
		
		// Place components
		GridBagConstraints c = new GridBagConstraints();
		keyWordPanel.setLayout(new GridBagLayout());
	
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 5, 10, 5);
		c.gridx = 0;
		c.gridy = 0;
		keyWordPanel.add(list1, c);
			
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 5, 10, 5);
		c.gridx = 1;
		c.gridy = 0;
		keyWordPanel.add(options1, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 5, 10, 5);
		c.gridx = 2;
		c.gridy = 0;
		keyWordPanel.add(list2, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 5, 10, 5);
		c.gridx = 3;
		c.gridy = 0;
		keyWordPanel.add(options2, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 5, 10, 5);
		c.gridx = 4;
		c.gridy = 0;
		keyWordPanel.add(list3, c);
		
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(10, 5, 10, 5);
		c.gridx = 5;
		c.gridy = 0;
		keyWordPanel.add(searchButton, c);
		
		return keyWordPanel;
	}
		
	protected JPanel createKeyWordPanel(TIGModifyImageDialog dialog){ 
			
			this.mySearchDialog = dialog;
			
			JPanel keyWordPanel = new JPanel();
			
			keyWordPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
					Color.WHITE, new Color(165, 163, 151)), 
					TLanguage.getString("TIGKeyWordSearchDialog.CATEGORY_SEARCH")));
			
			keyWordList = dataBase.getKeyWords();
			//keyWord1 = (String) keyWordList.elementAt(0);
			
			list1 = new TComboBox(keyWordList);
			list1.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
						keyWord1 = tf.getText();
					}
				}
			});
			list1.setEditable(true);
			list1.setPreferredSize(new Dimension (125, 25));
							
			list2 = new TComboBox(keyWordList);
			keyWord2 = "";
			list2.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						JTextField tf = (JTextField)list2.getEditor().getEditorComponent();
						keyWord2 = tf.getText();
					}
				}
			});
			list2.setEditable(true);
			list2.setPreferredSize(new Dimension (125, 25));
			
			list3 = new TComboBox(keyWordList);
			keyWord3 = "";
			list3.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
						keyWord3 = tf.getText();
					}
				}
			});
			list3.setEditable(true);
			list3.setPreferredSize(new Dimension (125, 25));
			
			optionsList.addElement("");
			optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.AND"));
			optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.OR"));
			optionsList.addElement(TLanguage.getString("TIGKeyWordSearchDialog.NOT"));
			
			options1 = new TComboBox(optionsList);
			options1.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						switch (options1.getSelectedIndex())
						{
							case 0: searchOptions1 = 0;
									break;
							case 1: searchOptions1 = SEARCH_OPTIONS_AND;
									break;
							case 2: searchOptions1 = SEARCH_OPTIONS_OR;
									break;
							case 3: searchOptions1 = SEARCH_OPTIONS_NOT;
						}
					}else{
						if (options1.getSelectedIndex()== 0){
							list2.setEnabled(false);
							options2.setEnabled(false);
							list3.setEnabled(false);
						}
						else if (options1.getSelectedIndex()== 1){
							list2.setEnabled(true);
							options2.setEnabled(true);
						}
						else if (options1.getSelectedIndex()== 2){
							list2.setEnabled(true);
							options2.setEnabled(true);
						}
						else if (options1.getSelectedIndex()== 3){
							list2.setEnabled(true);
							options2.setEnabled(true);
						}
						
					}
				}
			});
			options1.setEditable(true);
			options1.setPreferredSize(new Dimension (50, 25));
			
			options2 = new TComboBox(optionsList);
			options2.addActionListener(new AbstractAction() {
				public void actionPerformed(ActionEvent e){
					if (e.getActionCommand().compareTo("comboBoxEdited") == 0) 
					{
						switch (options2.getSelectedIndex())
						{
							case 0: searchOptions2 = 0;
									break;
							case 1: searchOptions2 = SEARCH_OPTIONS_AND;
									break;
							case 2: searchOptions2 = SEARCH_OPTIONS_OR;
									break;
							case 3: searchOptions2 = SEARCH_OPTIONS_NOT;
						}
					}else{
						if (options2.getSelectedIndex()== 0){
							list3.setEnabled(false);
						}
						else if (options2.getSelectedIndex()== 1){
							list3.setEnabled(true);
						}
						else if (options2.getSelectedIndex()== 2){
							list3.setEnabled(true);
						}
						else if (options2.getSelectedIndex()== 3){
							list3.setEnabled(true);
						}
						
					}
				}
			});
			options2.setEditable(true);
			options2.setPreferredSize(new Dimension (50, 25));
			
			//Create button
			TButton searchButton = new TButton(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					result = new Vector();
					keyWord1 = KeyWord1();
					keyWord2 = KeyWord2();
					keyWord3 = KeyWord3();;
					searchOptions1 = searchOptions1();
					searchOptions2 = searchOptions2();
					result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
					if (result.size()==0){
						System.out.println("No encontrado categorias2");
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
								TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
								JOptionPane.CLOSED_OPTION);
					}
					else
						mySearchDialog.update(result);
				}
			});
			searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
				@Override
				public void keyReleased(java.awt.event.KeyEvent e){
					if ((e.getKeyCode() == KeyEvent.VK_ENTER)){
						result = new Vector();
						keyWord1 = KeyWord1();
						keyWord2 = KeyWord2();
						keyWord3 = KeyWord3();;
						searchOptions1 = searchOptions1();
						searchOptions2 = searchOptions2();
						result = dataBase.search(keyWord1,searchOptions1,keyWord2,searchOptions2,keyWord3);
						if (result.size()==0){
							System.out.println("No encontrado categorias");
							// There are no results for that search
							JOptionPane.showConfirmDialog(null,
									TLanguage.getString("TIGKeyWordSearchDialog.NO_RESULTS"),
									TLanguage.getString("TIGKeyWordSearchDialog.NAME"),
									JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
						}
						else
							mySearchDialog.update(result);
					}
				}
			});
			searchButton.setText(TLanguage.getString("TIGKeyWordSearchDialog.SEARCH"));
			list2.setEnabled(false);
			list3.setEnabled(false);
			options2.setEnabled(false);
			
			// Place components
			GridBagConstraints c = new GridBagConstraints();
			keyWordPanel.setLayout(new GridBagLayout());
	
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 0;
			c.gridy = 0;
			keyWordPanel.add(list1, c);
				
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 1;
			c.gridy = 0;
			keyWordPanel.add(options1, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 2;
			c.gridy = 0;
			keyWordPanel.add(list2, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 3;
			c.gridy = 0;
			keyWordPanel.add(options2, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 4;
			c.gridy = 0;
			keyWordPanel.add(list3, c);
			
			c.fill = GridBagConstraints.CENTER;
			c.insets = new Insets(10, 5, 10, 5);
			c.gridx = 5;
			c.gridy = 0;
			keyWordPanel.add(searchButton, c);
			
			return keyWordPanel;
	}
	
	public String KeyWord1(){
		JTextField tf = (JTextField)list1.getEditor().getEditorComponent();
		keyWord1 = tf.getText();
		return keyWord1;
	}
	
	public String KeyWord2(){
		JTextField tf = (JTextField)list2.getEditor().getEditorComponent();
		keyWord2 = tf.getText();
		return keyWord2;
	}	
	
	public String KeyWord3(){
		JTextField tf = (JTextField)list3.getEditor().getEditorComponent();
		keyWord3 = tf.getText();
		return keyWord3;
	}	
	
	public int searchOptions1(){
		switch (options1.getSelectedIndex())
		{
			case 0: searchOptions1 = 0;
					break;
			case 1: searchOptions1 = SEARCH_OPTIONS_AND;
					break;
			case 2: searchOptions1 = SEARCH_OPTIONS_OR;
					break;
			case 3: searchOptions1 = SEARCH_OPTIONS_NOT;
		}		
		return searchOptions1;
	}	
	
	public int searchOptions2(){
		switch (options2.getSelectedIndex())
		{
			case 0: searchOptions2 = 0;
					break;
			case 1: searchOptions2 = SEARCH_OPTIONS_AND;
					break;
			case 2: searchOptions2 = SEARCH_OPTIONS_OR;
					break;
			case 3: searchOptions2 = SEARCH_OPTIONS_NOT;
		}		
		return searchOptions2;
	}	
}
