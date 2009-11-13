/*
 * File: TCellDialog.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Mar 6, 2006
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import tico.board.TBoard;
import tico.board.TBoardConstants;
import tico.board.components.TComponent;
import tico.components.TAnotherBorderSelectionPanel;
import tico.components.TAnotherSoundChooser;
import tico.components.TBackgroundSelectionPanel;
import tico.components.TBorderSelectionPanel;
import tico.components.TClickCellActionsPanel;
import tico.components.TFontModelChooser;
import tico.components.TIdTextField;
import tico.components.TImageChooser;
import tico.components.TSendTextChooser;
import tico.components.TSoundChooser;
import tico.components.TTextField;
import tico.configuration.TLanguage;
import tico.editor.TBoardContainer;
import tico.editor.TEditor;
import tico.environment.TEnvironment;

/**
 * Dialog to change <code>TCellDialog</code> attributes.
 * 
 * @author Pablo Mu�oz
 * @version 1.0 Nov 20, 2006
 */
public class TCellDialog extends TComponentDialog {
	
	private static String DEFAULT_TITLE = TLanguage.getString("TCellDialog.TITLE");

	// Tabbed pane which contains all the other cell properties panes
	protected JTabbedPane tabbedPane;

	// Text properties panel
	private JPanel textPropertiesPanel;

	private JPanel textFieldPanel;

	private TTextField textField;

	protected JPanel idFieldPanel;

	protected TIdTextField idTextField;

	private TFontModelChooser fontModel;

	// Component properties panel
	private JPanel componentPropertiesPanel;

	private TBorderSelectionPanel borderSelectionPanel;
	
	private TAnotherBorderSelectionPanel AnotherborderSelectionPanel;

	private TBackgroundSelectionPanel backgroundSelectionPanel;

	private TImageChooser iconChooser;

	private TImageChooser alternativeIconChooser;

	// Actions panel
	private JPanel componentActionsPanel;
	
	private TClickCellActionsPanel clickCellActionPanel;

	private TSoundChooser soundChooser;
	
	private TAnotherSoundChooser browsingSoundChooser;

	private TSendTextChooser sendTextChooser;
	
	private JPanel environmentPanel;
	
	private JScrollPane listScroll;
	
	private JList orderList;
	
	private TEditor myEditor;
	
	/**
	 * Creates a new <code>TCellDialog</code> to edit the <code>cell</code>
	 * properties.
	 * 
	 * @param boardContainer The <code>boardContainer</code> which contains the
	 * cell to be edited
	 * @param cell The <code>cell</code> to be edited
	 */
	public TCellDialog(TBoardContainer boardContainer, TComponent cell) {
		this(boardContainer, DEFAULT_TITLE, cell);
		myEditor = boardContainer.getEditor();	
	}
	
	/**
	 * Creates a new <code>TCellDialog</code> to edit the <code>cell</code>
	 * properties.
	 * 
	 * @param boardContainer The <code>boardContainer</code> which contains the
	 * cell to be edited
	 * @param title The <code>title</code> of the dialog
	 * @param cell The <code>cell</code> to be edited
	 */
	public TCellDialog(TBoardContainer boardContainer, String title,
			TComponent cell) {
		super(boardContainer, title, cell);
		myEditor = boardContainer.getEditor();
	}

	// Creates the main dialog pane
	protected JPanel setComponentPane(TEditor editor) {
		JPanel componentPane = new JPanel();
		myEditor = editor;

		GridBagConstraints c = new GridBagConstraints();

		componentPane.setLayout(new GridBagLayout());
		
		createTabbedPane();

		createIdField();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 0;
		componentPane.add(idFieldPanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 1;
		componentPane.add(tabbedPane, c);

		return componentPane;
	}

	// Creates the cell id field
	protected void createIdField() {
		idFieldPanel = new JPanel();

		idFieldPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		idFieldPanel.add(new JLabel(TLanguage.getString("TCellDialog.ID")));

		idTextField = new TIdTextField();
		
		idFieldPanel.add(idTextField);

		idTextField.setText(TBoardConstants.getId(getAttributeMap()));
	}

	// Creates the main dialog tabbed pane
	protected void createTabbedPane() {
		tabbedPane = new JTabbedPane();

		// Create properties panels
		createTextPropertiesPanel();
		createComponentPropertiesPanel();
		createActionsPanel();
		createEnvironmentPanel();
		// Add properties panels to the tabbed pane
		tabbedPane.addTab(TLanguage.getString("TCellDialog.TAB_TEXT"),
				textPropertiesPanel);
		tabbedPane.addTab(TLanguage.getString("TCellDialog.TAB_PROPERTIES"),
				componentPropertiesPanel);
		tabbedPane.addTab(TLanguage.getString("TCellDialog.TAB_ACTIONS"),
				componentActionsPanel);
		
		tabbedPane.addTab(TLanguage.getString("TCellDialog.ENVIRONMENT"),environmentPanel);
	}
	public void createEnvironmentPanel()
	{
		environmentPanel =new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		environmentPanel.setLayout(new GridBagLayout());
		createOrderList();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 0;
		environmentPanel.add(listScroll, c);
	}
	
	
	private void createOrderList() {
		Map map = getAttributeMap();
		 listScroll = new JScrollPane();
		// Set minimum scroll pane size
		listScroll.setPreferredSize(new Dimension(300,
				500));
		listScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScroll
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// Create the list
		Vector environmentAction=TEnvironment.getAllKeys();
		
		orderList = new JList(environmentAction);
		orderList.setMinimumSize(new Dimension(300, 500));

		int position=TBoardConstants.getPositionAction(map);
		int tam=environmentAction.size();
		if (position!=-1)
		{		
		if(position<tam){
			orderList.setSelectedIndex(position);
		}
		}
		
		listScroll.setViewportView(orderList);
		
	}

	

	// Creates the text properties panel for the tabbed pane
	private void createTextPropertiesPanel() {
		textPropertiesPanel = new JPanel();

		GridBagConstraints c = new GridBagConstraints();

		textPropertiesPanel.setLayout(new GridBagLayout());

		createTextField();
		createFontModel();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 0;
		textPropertiesPanel.add(textFieldPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		textPropertiesPanel.add(fontModel, c);
	}

	// Creates the cell properties panel for the tabbed pane
	private void createComponentPropertiesPanel() {
		componentPropertiesPanel = new JPanel();

		GridBagConstraints c = new GridBagConstraints();

		componentPropertiesPanel.setLayout(new GridBagLayout());
		createAnotherBorderSelectionPanel();
		createBorderSelectionPanel();
		
		createBackgroundSelectionPanel();
		createIconChooser();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 0;
		componentPropertiesPanel.add(borderSelectionPanel, c);
		
		//add By Toty!
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 1;
		componentPropertiesPanel.add(AnotherborderSelectionPanel, c);


		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 2;
		componentPropertiesPanel.add(backgroundSelectionPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 3;
		componentPropertiesPanel.add(iconChooser, c);
	}

	// Creates the actions panel for the tabbed pane
	private void createActionsPanel() {
		componentActionsPanel = new JPanel();

		GridBagConstraints c = new GridBagConstraints();

		componentActionsPanel.setLayout(new GridBagLayout());

		createFollowingBoardPanel();
		createAlterntativeIconChooser();
		createSoundChooser();
		//createAnotherSoundChooser();
		createSendTextChooser();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 0;
		componentActionsPanel.add(clickCellActionPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 1;
		componentActionsPanel.add(alternativeIconChooser, c);
				
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.gridx = 0;
		c.gridy = 2;
		componentActionsPanel.add(soundChooser, c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 3;
		componentActionsPanel.add(sendTextChooser, c);
	}
	
	
	// Creates the cell text field
	private void createTextField() {
		Map map = getAttributeMap();

		textFieldPanel = new JPanel();

		textFieldPanel.setBorder(new TitledBorder(BorderFactory
				.createEtchedBorder(Color.WHITE, new Color(165, 163, 151)),
				TLanguage.getString("TCellDialog.TEXT_FILED")));

		textField = new TTextField(TBoardConstants.getText(map));
		textField.setColumns(30);

		textFieldPanel.add(textField);
	}
	
	// Creates the font model selection panel
	private void createFontModel() {
		Map map = getAttributeMap();

		fontModel = new TFontModelChooser(TBoardConstants.getFont(map)
				.getName(), TBoardConstants.getForeground(map), TBoardConstants
				.getFont(map).getSize(), TBoardConstants.getFont(map)
				.getStyle());
	}
	
	// Creates the boarder selection panel
	private void createBorderSelectionPanel() {
		Map map = getAttributeMap();

		borderSelectionPanel = new TBorderSelectionPanel();
	
		borderSelectionPanel
				.setBorderColor(TBoardConstants.getBorderColor(map));
		borderSelectionPanel.setBorderSize(Math.max(1, Math
				.round(TBoardConstants.getLineWidth(map))));
		borderSelectionPanel.setSelect(AnotherborderSelectionPanel);
	}
	
	private void createAnotherBorderSelectionPanel() {
		Map map = getAttributeMap();

		AnotherborderSelectionPanel = new TAnotherBorderSelectionPanel();

		AnotherborderSelectionPanel
				.setBorderColor(TBoardConstants.getChangeColor(map));
		
		AnotherborderSelectionPanel.setBorderSize(Math.max(4,Math
				.round(TBoardConstants.getChangeLineWidth(map))));

	}
	
	//Creates another border selection panel.
	

	// Creates the backgrond selection panel
	private void createBackgroundSelectionPanel() {
		Map map = getAttributeMap();

		backgroundSelectionPanel = new TBackgroundSelectionPanel();

		backgroundSelectionPanel.setBackgroundColor(TBoardConstants
				.getBackground(map));
		backgroundSelectionPanel.setGradientColor(TBoardConstants
				.getGradientColor(map));
	}

	// Creates the icon chooser panel
	private void createIconChooser() {
		Map map = getAttributeMap();

		iconChooser = new TImageChooser(TImageChooser.TEXT_POSITION_TYPE, myEditor);

		iconChooser.setIcon((ImageIcon)TBoardConstants.getIcon(map));
		iconChooser.setVerticalTextPosition(TBoardConstants
				.getVerticalTextPosition(map));
	}

	// Creates the alternative icon chooser panel
	private void createAlterntativeIconChooser() {
		Map map = getAttributeMap();

		alternativeIconChooser = new TImageChooser(
				TLanguage.getString("TCellDialog.ALTERNATIVE_IMAGE"),myEditor);
		
		alternativeIconChooser.setIcon((ImageIcon)TBoardConstants
				.getAlternativeIcon(map));
	}

	// Creates the following chooser panel
	private void createFollowingBoardPanel() {
		Map map = getAttributeMap();

		// Get the board list from the editor
		ArrayList boardList = (ArrayList)getBoardContainer().getEditor()
				.getProject().getBoardList().clone();
		// Delete the current board
		boardList.remove(getBoard());

		clickCellActionPanel = new TClickCellActionsPanel(boardList);

		clickCellActionPanel.setFollowingBoard(TBoardConstants
				.getFollowingBoard(map));
		clickCellActionPanel.setAccumulated(TBoardConstants.isAccumulated(map));
	}

	// Creates the sound chooser panel
	private void createSoundChooser() {
		Map map = getAttributeMap();

		soundChooser = new TSoundChooser();

		soundChooser.setSoundFilePath(TBoardConstants.getSoundFile(map));
	}
	
//	private void createAnotherSoundChooser(){
//		Map map=getAttributeMap();
//		browsingSoundChooser =new TAnotherSoundChooser();
//		browsingSoundChooser.setSoundFilePath(TBoardConstants.getBrowsingSoundFile(map));
//	}
	// Creates the send text chooser panel
	private void createSendTextChooser() {
		Map map = getAttributeMap();

		sendTextChooser = new TSendTextChooser(getBoard());

		sendTextChooser.setText(TBoardConstants.getSendText(map));
		sendTextChooser.setTextReceiver(TBoardConstants.getSendTextTarget(map));
		sendTextChooser.setTimer(TBoardConstants.getSendTextTimer(map));
	}

	/* (non-Javadoc)
	 * @see tico.editor.dialogs.TComponentDialog#newComponentsAttributeMap()
	 */
	protected Map newComponentsAttributeMap() {
		// Create used variables
		Map nested = new Hashtable();
		Map attributeMap = new Hashtable();
		Vector removalAttributes = new Vector();

		// Set cell text and format
		TBoardConstants.setText(attributeMap, textField.getText());

		TBoardConstants.setForeground(attributeMap, fontModel.getFontColor());
		TBoardConstants.setFont(attributeMap, new Font(fontModel.getFontFace(),
				fontModel.getFontStyle(), fontModel.getFontSize()));

		// Set cell background and border
		Color color = borderSelectionPanel.getBorderColor();
		if (color != null)
			TBoardConstants.setBorderColor(attributeMap, color);
		else
			removalAttributes.add(TBoardConstants.BORDERCOLOR);
		
		Color color2=AnotherborderSelectionPanel.getBorderColor();
		
		if (color2!=null){
			
			TBoardConstants.setChangeColor(attributeMap,color2);}
		else
			removalAttributes.add(TBoardConstants.CHANGE_COLOR);

		TBoardConstants.setLineWidth(attributeMap, borderSelectionPanel
				.getBorderSize());
		
		TBoardConstants.setChangeLineWidth(attributeMap,AnotherborderSelectionPanel.getBorderSize());
	
		Color background = backgroundSelectionPanel.getBackgroundColor();
		if (background != null)
			TBoardConstants.setBackground(attributeMap, background);
		else
			removalAttributes.add(TBoardConstants.BACKGROUND);

		Color gradient = backgroundSelectionPanel.getGradientColor();
		if (gradient != null)
			TBoardConstants.setGradientColor(attributeMap, gradient);
		else
			removalAttributes.add(TBoardConstants.GRADIENTCOLOR);

		// Set cell static image and other image properties
		ImageIcon icon = iconChooser.getIcon();
		if (icon != null){
			TBoardConstants.setIcon(attributeMap, icon);
		}
		else
			removalAttributes.add(TBoardConstants.ICON);

		TBoardConstants.setVerticalTextPosition(attributeMap, iconChooser
				.getVerticalTextPosition());

		// Set cell alternative image
		ImageIcon alternativeIcon = alternativeIconChooser.getIcon();
		if (alternativeIcon != null)
			TBoardConstants.setAlternativeIcon(attributeMap, alternativeIcon);
		else
			removalAttributes.add(TBoardConstants.ALTERNATIVE_ICON);

		// Set cell sound file
		String soundFile = soundChooser.getSoundFilePath();
		if (soundFile != null)
			TBoardConstants.setSoundFile(attributeMap, soundFile);
		else
			removalAttributes.add(TBoardConstants.SOUND_FILE);
		
		//Set Environment Action
		Object action=orderList.getSelectedValue();
		
		if (action!=null)
		{	int pos=orderList.getSelectedIndex();
			TBoardConstants.setEnvironmentAction(attributeMap,TEnvironment.getCode(action.toString()));
			TBoardConstants.setPositionAction(attributeMap, pos);
		}
		else 
		{	removalAttributes.add(TBoardConstants.ENVIRONMENT_ACTION);
			removalAttributes.add(TBoardConstants.ACTION_POSITION);
		}
		// Set cell send text properties
		TComponent component = sendTextChooser.getTextReceiver();
		if (component != null) {
			TBoardConstants.setSendTextTarget(attributeMap, component);
			TBoardConstants
					.setSendText(attributeMap, sendTextChooser.getText());
			TBoardConstants.setSendTextTimer(attributeMap, sendTextChooser
					.getTimer());
		} else {
			removalAttributes.add(TBoardConstants.SEND_TEXT_TARGET);
			removalAttributes.add(TBoardConstants.SEND_TEXT);
			removalAttributes.add(TBoardConstants.SEND_TEXT_TIMER);
		}

		// Set click action cell attributes
		TBoardConstants.setAccumulated(attributeMap, clickCellActionPanel
				.getAccumulated());
		TBoard followingBoard = clickCellActionPanel.getFollowingBoard();
		if (followingBoard != null)
			TBoardConstants.setFollowingBoard(attributeMap, followingBoard);
		else
			removalAttributes.add(TBoardConstants.FOLLOWING_BOARD);

		// Apply removal attributes
		TBoardConstants.setRemoveAttributes(attributeMap, removalAttributes
				.toArray());

		// Set cell id
		TBoardConstants.setId(attributeMap, idTextField.getText());

		nested.put(getComponent(), attributeMap);

		return nested;
	}

	
}