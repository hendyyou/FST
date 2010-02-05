/*
 * File: TIGSearchNameDialog.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: Feb 1, 2008
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tico.components.TButton;
import tico.components.TFrame;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.imageGallery.dataBase.TIGDataBase;


/*
 * This class displays the panel that searches images from its name
 */
public class TIGSearchNameDialog extends TFrame{
		
	private JTextField texto;
	
	private TEditor editor;
	
	private TIGDataBase myDataBase;
	
	private Vector result;
	
	private TIGModifyImageDialog mySearchDialog;
	private TIGDeleteImagesDialog mySearchDialogDelete;
	private TIGExportDBDialog mySearchDialogExport;
	
	private TIGImageGalleryDialog myDialog;
	
	
	public TIGSearchNameDialog(){
		
	}
	
	protected JPanel createSearchNamePanelDelete(TEditor editor, TIGDataBase dataBase, TIGDeleteImagesDialog dialog){ 
		
		this.editor = editor;
		this.mySearchDialogDelete = dialog;
		myDataBase = dataBase;
		
		JPanel searchPanel = new JPanel();
		
		searchPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGSearchNameDialog.NAME_SEARCH")));
		JLabel searchLabel = new JLabel(TLanguage.getString("TIGSearchNameDialog.SEARCH"));
		texto = new JTextField(TLanguage.getString("TIGSearchNameDialog.TEXT"),30);
		String text =  TLanguage.getString("TIGSearchNameDialog.TEXT");
		texto.select(0, text.length());
		texto.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				if (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) == 0)
					texto.setText("");
			}
		});		
		texto.addKeyListener(new java.awt.event.KeyAdapter(){
			public void keyReleased(java.awt.event.KeyEvent e){
				System.out.println("Code"+e.getKeyChar());
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						// There are no results for that search
						mySearchDialogDelete.update(result);
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialogDelete.update(result);
				}
			}
		});
		TButton searchButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("**Buscar2** delete nombre");
				result = new Vector();
				if ((texto.getText().compareTo("") != 0) && 
					(texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = TIGDataBase.imageSearch(texto.getText());
					System.out.println("Buscando");
					if (result.size()==0){
						mySearchDialogDelete.update(result);
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialogDelete.update(result);
				}				
			}
		});
		searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				System.out.println("**Buscar3 delete**");
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						mySearchDialogDelete.update(result);
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialogDelete.update(result);
				}
			}
		});
		
		searchButton.setText(TLanguage.getString("TIGSearchNameDialog.SEARCH_BUTTON"));
				
		// Place components
		GridBagConstraints c = new GridBagConstraints();
		searchPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 0);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		searchPanel.add(searchLabel, c);
		
		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 1;
		c.gridy = 0;
		searchPanel.add(texto, c);
		
		c.fill = GridBagConstraints.NONE;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 2;
		c.gridy = 0;
		searchPanel.add(searchButton, c);
		
				
		return searchPanel;
	}
	
	protected JPanel createSearchNamePanelExport(TEditor editor, TIGDataBase dataBase, TIGExportDBDialog dialog){ 
		
		this.editor = editor;
		
		this.mySearchDialogExport = dialog;
		myDataBase = dataBase;
		
		JPanel searchPanel = new JPanel();
		
		searchPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGSearchNameDialog.NAME_SEARCH")));
		JLabel searchLabel = new JLabel(TLanguage.getString("TIGSearchNameDialog.SEARCH"));
		texto = new JTextField(TLanguage.getString("TIGSearchNameDialog.TEXT"),30);
		String text =  TLanguage.getString("TIGSearchNameDialog.TEXT");
		texto.select(0, text.length());
		texto.addMouseListener(new java.awt.event.MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e){
				if (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) == 0)
					texto.setText("");
			}
		});		
		texto.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				System.out.println("**Buscar1** delete busca la 1º vez");
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						// There are no results for that search
						mySearchDialogExport.update(result);
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialogExport.update(result);
				}
			}
		});
		TButton searchButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				result = new Vector();
				if ((texto.getText().compareTo("") != 0) && 
					(texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = TIGDataBase.imageSearch(texto.getText());
					System.out.println("Buscando");
					if (result.size()==0){
						mySearchDialogExport.update(result);
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialogExport.update(result);
				}				
			}
		});
		searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						mySearchDialogExport.update(result);
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialogExport.update(result);
				}
			}
		});
		
		searchButton.setText(TLanguage.getString("TIGSearchNameDialog.SEARCH_BUTTON"));
				
		// Place components
		GridBagConstraints c = new GridBagConstraints();
		searchPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 0);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		searchPanel.add(searchLabel, c);
		
		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 1;
		c.gridy = 0;
		searchPanel.add(texto, c);
		
		c.fill = GridBagConstraints.NONE;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 2;
		c.gridy = 0;
		searchPanel.add(searchButton, c);
		
				
		return searchPanel;
	}
	
	protected JPanel createSearchNamePanel(TEditor editor, TIGDataBase dataBase, TIGModifyImageDialog dialog){ 
		
		this.editor = editor;
		this.mySearchDialog = dialog;
		myDataBase = dataBase;
		
		JPanel searchPanel = new JPanel();
		
		searchPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGSearchNameDialog.NAME_SEARCH")));
		JLabel searchLabel = new JLabel(TLanguage.getString("TIGSearchNameDialog.SEARCH"));
		texto = new JTextField(TLanguage.getString("TIGSearchNameDialog.TEXT"),30);
		String text =  TLanguage.getString("TIGSearchNameDialog.TEXT");
		texto.select(0, text.length());
		texto.addMouseListener(new java.awt.event.MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e){
				if (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) == 0)
					texto.setText("");
			}
		});		
		texto.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				System.out.println("**Buscar1**");
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						// There are no results for that search
						mySearchDialog.update(result);
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialog.update(result);
				}
			}
		});
		TButton searchButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("**Buscar2**");
				result = new Vector();
				if ((texto.getText().compareTo("") != 0) && 
					(texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = TIGDataBase.imageSearch(texto.getText());
					System.out.println("Buscando");
					if (result.size()==0){
						mySearchDialog.update(result);
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialog.update(result);
				}				
			}
		});
		searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				System.out.println("**Buscar3**");
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						mySearchDialog.update(result);
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						mySearchDialog.update(result);
				}
			}
		});
		
		searchButton.setText(TLanguage.getString("TIGSearchNameDialog.SEARCH_BUTTON"));
				
		// Place components
		GridBagConstraints c = new GridBagConstraints();
		searchPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 0);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		searchPanel.add(searchLabel, c);

		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 1;
		c.gridy = 0;
		searchPanel.add(texto, c);
		
		c.fill = GridBagConstraints.NONE;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 2;
		c.gridy = 0;
		searchPanel.add(searchButton, c);
		
				
		return searchPanel;
	}
	
	protected JPanel createSearchNamePanel(TEditor editor, TIGDataBase dataBase, TIGImageGalleryDialog dialog){ 
		
		this.editor = editor;
		this.myDialog = dialog;
		myDataBase = dataBase;
		
		JPanel searchPanel = new JPanel();
		
		searchPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGSearchNameDialog.NAME_SEARCH")));
		JLabel searchLabel = new JLabel(TLanguage.getString("TIGSearchNameDialog.SEARCH"));
		texto = new JTextField(TLanguage.getString("TIGSearchNameDialog.TEXT"),30);
		String text =  TLanguage.getString("TIGSearchNameDialog.TEXT");
		texto.select(0, text.length());
		
		texto.addMouseListener(new java.awt.event.MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e){
				if (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) == 0)
					texto.setText("");
			}
		});		
		/*texto.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				System.out.println("**Buscar4**");
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						myDialog.update(result);
				}
			}
		});*/
		
		TButton searchButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("**Buscar5**");
				System.out.println("Cadena a comparar: " + texto.getText() + " con: " + TLanguage.getString("TIGSearchNameDialog.TEXT"));
				result = new Vector();
				if ((texto.getText().compareTo("") != 0) && 
					(texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					
					result = TIGDataBase.imageSearch(texto.getText());//+"*");
					System.out.println("Resultado: " + result);
					
					if (result.size()==0){
						myDialog.update(result);
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						myDialog.update(result);
				}				
			}
		});
		searchButton.addKeyListener(new java.awt.event.KeyAdapter(){
			@Override
			public void keyReleased(java.awt.event.KeyEvent e){
				System.out.println("**Buscar6**");
				if ((e.getKeyCode() == KeyEvent.VK_ENTER) && (texto.getText().compareTo("") != 0)
					&& (texto.getText().compareTo(TLanguage.getString("TIGSearchNameDialog.TEXT")) != 0)){
					result = new Vector();	
					result = TIGDataBase.imageSearch(texto.getText());	
					if (result.size()==0){
						if (mySearchDialog!= null){
							mySearchDialog.update(result);
						}
						// There are no results for that search
						JOptionPane.showConfirmDialog(null,
								TLanguage.getString("TIGSearchNameDialog.NO_RESULTS"),
								TLanguage.getString("TIGSearchNameDialog.NAME_RESULT"),
								JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
					}
					else
						myDialog.update(result);
				}
			}
		});
		searchButton.setText(TLanguage.getString("TIGSearchNameDialog.SEARCH_BUTTON"));
				
		// Place components
		GridBagConstraints c = new GridBagConstraints();
		searchPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 0);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		searchPanel.add(searchLabel, c);

		c.fill = GridBagConstraints.BOTH;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 1;
		c.gridy = 0;
		searchPanel.add(texto, c);
		
		c.fill = GridBagConstraints.NONE;
		//c.insets = new Insets(10, 10, 10, 10);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 2;
		c.gridy = 0;
		searchPanel.add(searchButton, c);
				
		return searchPanel;
	}
	
	protected Vector returnImages(){
		return result;
	}
	
}

