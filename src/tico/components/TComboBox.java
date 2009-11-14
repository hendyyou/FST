/*
 * File: TComboBox.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Aug 17, 2006
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
package tico.components;

import java.awt.event.*;
import java.awt.Dimension;
import java.util.Vector;
import java.lang.String;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * An implementation of a combo box with the format parameters for Tico
 * applications.
 *
 * @author Pablo Muñoz
 * @version 1.0 Nov 20, 2006
 */
public class TComboBox extends JComboBox implements JComboBox.KeySelectionManager {
	// Default TComboBox size
	protected static final int DEFAUL_HEIGHT = 25;
	protected static final int DEFAULT_WIDTH = 70;
	
	private String searchFor;
	private long lap;
	
	public class CBDocument extends PlainDocument
	{
		public void insertString(int offset, String str, AttributeSet a) throws BadLocationException
		{
			if (str==null) return;
			super.insertString(offset, str, a);
			if(str.length() != 0) fireActionEvent();
		}
	}
	
	
	/**
	 * Creates a <code>TComboBox</code> with a default data model.
	 */
	public TComboBox() {
		super();
		setDefaultSize();
	}

	/**
	 * Creates a <code>TComboBox</code> that takes it's items from an existing
	 * <code>ComboBoxModel</code>.
	 * 
	 * @param dataModel The <code>ComboBoxModel</code> that provides the displayed
	 * list of items
	 */
	public TComboBox(ComboBoxModel dataModel) {
		super(dataModel);
		setDefaultSize();
	}

	/**
	 * Creates a <code>TComboBox</code> that contains the elements in the
	 * specified <code>array</code>.
	 * 
	 * @param items An <code>array</code> of objects to insert into the <code>TComboBox</code>
	 */
	public TComboBox(Object[] items) {
		super(items);
		setDefaultSize();
	}

	/**
	 * Creates a <code>TComboBox</code> that contains the elements in the
	 * specified <code>Vector</code>.
	 * 
	 * @param items An <code>Vector</code> of objects to insert into the <code>TComboBox</code>
	 */
	public TComboBox(Vector items) {
		super(items);
		setDefaultSize();
				
		lap = new java.util.Date().getTime();
		setKeySelectionManager(this);
		JTextField tf;
		if(getEditor() != null) 
		{
			tf = (JTextField)getEditor().getEditorComponent();
			if (tf != null)
			{
				tf.setDocument(new CBDocument());
				addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent evt)
					{				
						long now = new java.util.Date().getTime();
						if ((lap + 1000) < now)
						{				
							JTextField tf = (JTextField)getEditor().getEditorComponent();
							String text = tf.getText();
							ComboBoxModel aModel = getModel();
							String current;
							for(int i = 0; i < aModel.getSize(); i++)
							{
								current = aModel.getElementAt(i).toString();
								String aux = replace(current);
								if(aux.startsWith(replace(text)))
								{
									tf.setText(current);
									setSelectedIndex(i);	
									showPopup();
									tf.setSelectionStart(text.length());
									tf.setSelectionEnd(current.length());
									if (evt.getActionCommand().compareTo("comboBoxEdited") == 0){ 
										tf.setText(current);
										hidePopup();
									}
									break;
								}
							}
						}
					}
				});
			}
		}
	}

	public int selectionForKey(char aKey, ComboBoxModel aModel)
	{
		long now = new java.util.Date().getTime();
		if (searchFor!=null && aKey==KeyEvent.VK_BACK_SPACE &&	searchFor.length()>0)
		{
			searchFor = searchFor.substring(0, searchFor.length() -1);
		}
		else
		{
			if(lap + 1000 < now)
				searchFor = "" + aKey;
			else
				searchFor = searchFor + aKey;
		}
		lap = now;
		String current;
		for(int i = 0; i < aModel.getSize(); i++)
		{
			current = aModel.getElementAt(i).toString().toLowerCase();
			String aux = replace(current);
			if (aux.startsWith(searchFor.toLowerCase())) return i;
		}
		return -1;
	}
	
	/**
	 * Sets the <code>TComboBox</code> default size. To change it this function
	 * must be overriden. 
	 */
	protected void setDefaultSize() {
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAUL_HEIGHT));
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAUL_HEIGHT));
	}
	
	private String replace(String word){
		String result = word.replace(' ', '_').replace(',', '-').replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o').replace('ú', 'u').
		replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U').toLowerCase();
		return result;
	}
}


