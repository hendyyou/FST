package tico.araboard.toolbars;

import tico.arabard.TAraboard;
import tico.components.TToolBar;
import tico.components.TToolBarButton;
import tico.configuration.TLanguage;
import tico.editor.TActionSet;

/**
 * Tool bar with the file action buttons
 * @author Adrián Gómez
 * @version 1.0 Nov 22, 2010
 */
public class TFileToolBar extends TToolBar {

	// File buttons
	private TToolBarButton newButton;
	private TToolBarButton openButton;
	private TToolBarButton saveButton;
	private TToolBarButton saveAsButton;
	// Print button
	private TToolBarButton printButton;
	
	/**
	 * Creates a new <code>TFileToolBar</code>.
	 * 
	 * @param editor The <code>editor</code> actions receiver
	 */	
	public TFileToolBar(TAraboard araboard) {
		super(TLanguage.getString("TFileToolBar.NAME"));

		/*newButton = new TToolBarButton(araboard.getActionSet().getAction(
				TActionSet.PROJECT_NEW_ACTION));
		openButton = new TToolBarButton(araboard.getActionSet().getAction(
				TActionSet.PROJECT_OPEN_ACTION));
		saveButton = new TToolBarButton(araboard.getActionSet().getAction(
				TActionSet.PROJECT_SAVE_ACTION));
		saveAsButton = new TToolBarButton(araboard.getActionSet().getAction(
				TActionSet.PROJECT_SAVE_AS_ACTION));
		printButton = new TToolBarButton(araboard.getActionSet().getAction(
				TActionSet.PROJECT_PRINT_ACTION));*/

		add(newButton);
		addSeparator();
		add(openButton);
		add(saveButton);
		add(saveAsButton);
		addSeparator();
		add(printButton);
	}
}
