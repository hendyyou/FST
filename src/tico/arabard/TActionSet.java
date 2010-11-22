package tico.arabard;

import java.util.Hashtable;
import java.util.Map;

import javax.swing.Action;

import tico.components.resources.TFileUtils;

/**
 * Map of actions that can be done to an editor
 * @author Adrián Gómez
 * @version 1.0 Nov 22, 2010
 */
public class TActionSet {

	/**
	 * The <code>TEditorPreferencesAction</code> id
	 */
	public final static String EDITOR_PREFERENCES = "editorPreferences";

	/**
	 * The <code>TEditorAbout</code> id
	 */
	public final static String EDITOR_ABOUT = "editorAbout";

	/**
	 * The <code>TEditorExitAction</code> id
	 */
	public final static String EDITOR_EXIT_ACTION = "editorExitAction";

	/**
	 * The <code>TProyectNewAction</code> id
	 */
	public final static String PROJECT_NEW_ACTION = "proyectNewAction";

	/**
	 * The <code>TProyectOpenAction</code> id
	 */
	public final static String PROJECT_OPEN_ACTION = "proyectOpenAction";

	/**
	 * The <code>TProyectSaveAction</code> id
	 */
	public final static String PROJECT_SAVE_ACTION = "proyectSaveAction";

	/**
	 * The <code>TProyectSaveAsAction</code> id
	 */
	public final static String PROJECT_SAVE_AS_ACTION = "proyectSaveAsAction";

	/**
	 * The <code>TProyectImportAction</code> id
	 */
	public final static String PROJECT_IMPORT_ACTION = "proyectImportAction";

	/**
	 * The <code>TProjectInterpretAction</code> id
	 */
	public final static String PROJECT_INTERPRET_ACTION = "projectInterpretAction";

	/**
	 * The <code>TProjectPropertiesAction</code> id
	 */
	public final static String PROJECT_PROPERTIES_ACTION = "projectPropertiesAction";

	/**
	 * The <code>TProjectPrintAction</code> id
	 */
	public final static String PROJECT_PRINT_ACTION = "projectPrintAction";
	
	/**
	 * The <code>TProyectValidationAction</code> id
	 */
	public final static String PROJECT_VALIDATION_ACTION = "projectValidationAction";
	
	/**
	 * The <code>TBoardNewAction</code> id
	 */
	public final static String BOARD_NEW_ACTION = "boardNewAction";

	/**
	 * The <code>TBoardDeleteAction</code> id
	 */
	public final static String BOARD_DELETE_ACTION = "boardDeleteAction";

	/**
	 * The <code>TBoardPropertiesAction</code> id
	 */
	public final static String BOARD_PROPERTIES_ACTION = "boardPropertiesAction";

	/**
	 * The <code>TBoardExportAction</code> id
	 */
	public final static String BOARD_EXPORT_ACTION = "boardExportAction";

	/**
	 * The <code>TBoardInterpretAction</code> id
	 */
	public final static String BOARD_INTERPRET_ACTION = "boardInterpretAction";

	/**
	 * The <code>TBoardExportJPGAction</code> id
	 */
	public final static String BOARD_EXPORT_JPG_ACTION = "boardExportJPGAction";

	/**
	 * The <code>TBoardExportPNGAction</code> id
	 */
	public final static String BOARD_EXPORT_PNG_ACTION = "boardExportPNGAction";
	
	/**
	 * The <code>TBoardValidationAction</code> id
	 */
	public final static String BOARD_VALIDATION_ACTION = "boardValidationAction";
	
	/**
	 * The <code>THorizontalGapAction</code> id
	 */
	public final static String HORIZONTAL_GAP_ACTION = "horizontalGapAction";

	/**
	 * The <code>TVerticalGapAction</code> id
	 */
	public final static String VERTICAL_GAP_ACTION = "verticalGapAction";

	/**
	 * The <code>TAlignBottomAction</code> id
	 */
	public final static String ALIGN_BOTTOM_ACTION = "alignBottomAction";

	/**
	 * The <code>TAlignTopAction</code> id
	 */
	public final static String ALIGN_TOP_ACTION = "alignTopAction";

	/**
	 * The <code>TAlignLeftAction</code> id
	 */
	public final static String ALIGN_LEFT_ACTION = "alignLeftAction";

	/**
	 * The <code>TAlignRightAction</code> id
	 */
	public final static String ALIGN_RIGHT_ACTION = "alignRightAction";

	/**
	 * The <code>TAlignHorizontalAction</code> id
	 */
	public final static String ALIGN_HORIZONTAL_ACTION = "alignHorizontalAction";

	/**
	 * The <code>TAlignVerticalAction</code> id
	 */
	public final static String ALIGN_VERTICAL_ACTION = "alignVerticalAction";

	/**
	 * The <code>TFitWidthAction</code> id
	 */
	public final static String FIT_WIDTH_ACTION = "fitWidthAction";

	/**
	 * The <code>TFitHeightAction</code> id
	 */
	public final static String FIT_HEIGHT_ACTION = "fitHeightAction";

	/**
	 * The <code>TPasteAction</code> id
	 */
	public final static String PASTE_ACTION = "pasteAction";

	/**
	 * The <code>TCopyAction</code> id
	 */
	public final static String COPY_ACTION = "copyAction";

	/**
	 * The <code>TCutAction</code> id
	 */
	public final static String CUT_ACTION = "cutAction";

	/**
	 * The <code>TDeleteAction</code> id
	 */
	public final static String DELETE_ACTION = "deleteAction";

	/**
	 * The <code>TSelectAllAction</code> id
	 */
	public final static String SELECT_ALL_ACTION = "selectAllAction";

	/**
	 * The <code>TUndoAction</code> id
	 */
	public final static String UNDO_ACTION = "undoAction";

	/**
	 * The <code>TRedoAction</code> id
	 */
	public final static String REDO_ACTION = "redoAction";

	/**
	 * The <code>TBackAction</code> id
	 */
	public final static String BACK_ACTION = "backAction";

	/**
	 * The <code>TFrontAction</code> id
	 */
	public final static String FRONT_ACTION = "frontAction";

	/**
	 * The <code>TCellHandlerAction</code> id
	 */
	public final static String CELL_HANDLER = "cellHandlerAction";
	
	/**
	 * The <code>TControllerCellHandlerAction</code> id
	 */
	public final static String CELL_CONTROLLER_HANDLER = "controllerCellHandlerAction";

	/**
	 * The <code>TGridHandlerAction</code> id
	 */
	public final static String GRID_HANDLER = "gridHandlerAction";

	/**
	 * The <code>TLabelHandlerAction</code> id
	 */
	public final static String LABEL_HANDLER = "labelHandlerAction";

	/**
	 * The <code>TLineHandlerAction</code> id
	 */
	public final static String LINE_HANDLER = "lineHandlerAction";

	/**
	 * The <code>TRectangleHandlerAction</code> id
	 */
	public final static String RECTANGLE_HANDLER = "rectangleHandlerAction";

	/**
	 * The <code>TRoundRectHandlerAction</code> id
	 */
	public final static String ROUND_RECT_HANDLER = "roundRectHandlerAction";

	/**
	 * The <code>TSelectionHandlerAction</code> id
	 */
	public final static String SELECTION_HANDLER = "selectionHandlerAction";

	/**
	 * The <code>TOvalHandlerAction</code> id
	 */
	public final static String OVAL_HANDLER = "ovalHandlerAction";

	/**
	 * The <code>TTextAreaHandlerAction</code> id
	 */
	public final static String TEXT_AREA_HANDLER = "textAreaHandlerAction";
	
	/**
	 * The <code>TUsersAdminAction</code> id
	 */
	public final static String USERS_ADMIN_ACTION = "usersAdminAction";
	
	/**
	 * The <code>TAtributtesAdminAction</code> id
	 */
	public final static String LIMITATIONS_ADMIN_ACTION = "limitationsAdminAction";
	
	/**
	 * The <code>TUsersAdminAction</code> id
	 */
	public final static String RULES_ADMIN_ACTION = "rulesAdminAction";
	
	

	// Map which contains the actionName-action pairs
	private Map actionSet;

	/**
	 * Creates a new <code>TActionSet</code> for the specified <code>editor</code>
	 * with all the possible actions.
	 * 
	 * @param editor The specified <code>editor</code>
	 */
	public TActionSet(TAraboard araboard) {
		actionSet = new Hashtable();
		
		/**
		 * TODO Create action set
		 */
		// Project actions
		/*actionSet.put(PROJECT_NEW_ACTION, new TProjectNewAction(araboard));
		actionSet.put(PROJECT_OPEN_ACTION, new TProjectOpenAction(araboard));
		actionSet.put(PROJECT_SAVE_ACTION, new TProjectSaveAction(araboard));
		actionSet.put(PROJECT_SAVE_AS_ACTION, new TProjectSaveAsAction(araboard));
		actionSet.put(PROJECT_IMPORT_ACTION, new TProjectImportAction(araboard));
		actionSet.put(PROJECT_PROPERTIES_ACTION, new TProjectPropertiesAction(
				araboard));
		actionSet.put(PROJECT_PRINT_ACTION, new TProjectPrintAction(araboard));
		actionSet.put(PROJECT_VALIDATION_ACTION, new TProjectValidationAction(araboard));
		actionSet.put(EDITOR_EXIT_ACTION, new TEditorExitAction(araboard));
		*/
		/*
		// Editor actions
		actionSet.put(EDITOR_PREFERENCES, new TEditorPreferencesAction(editor));
		actionSet.put(EDITOR_ABOUT, new TEditorAboutAction(editor));
		
		

		// Board actions
		actionSet.put(BOARD_NEW_ACTION, new TBoardNewAction(editor));
		actionSet.put(BOARD_DELETE_ACTION, new TBoardDeleteAction(editor));
		actionSet.put(BOARD_PROPERTIES_ACTION, new TBoardPropertiesAction(
				editor));
		actionSet.put(BOARD_EXPORT_ACTION, new TBoardExportAction(editor));
		actionSet.put(BOARD_EXPORT_JPG_ACTION, new TBoardExportImageAction(
				editor, TFileUtils.JPG));
		actionSet.put(BOARD_EXPORT_PNG_ACTION, new TBoardExportImageAction(
				editor, TFileUtils.PNG));
		actionSet.put(BOARD_VALIDATION_ACTION, new TBoardValidationAction(editor));
		
		// Adjust, align and fit actions
		actionSet.put(HORIZONTAL_GAP_ACTION, new TAdjustHorizontalGapAction(
				editor));
		actionSet
				.put(VERTICAL_GAP_ACTION, new TAdjustVerticalGapAction(editor));
		actionSet.put(ALIGN_BOTTOM_ACTION, new TAlignBottomAction(editor));
		actionSet.put(ALIGN_TOP_ACTION, new TAlignTopAction(editor));
		actionSet.put(ALIGN_LEFT_ACTION, new TAlignLeftAction(editor));
		actionSet.put(ALIGN_RIGHT_ACTION, new TAlignRightAction(editor));
		actionSet.put(ALIGN_HORIZONTAL_ACTION,
				new TAlignCenterHorizontalAction(editor));
		actionSet.put(ALIGN_VERTICAL_ACTION, new TAlignCenterVerticalAction(
				editor));
		actionSet.put(FIT_WIDTH_ACTION, new TFitWidthAction(editor));
		actionSet.put(FIT_HEIGHT_ACTION, new TFitHeightAction(editor));
		// Copy-paste actions
		actionSet.put(PASTE_ACTION, new TPasteAction(editor));
		actionSet.put(COPY_ACTION, new TCopyAction(editor));
		actionSet.put(CUT_ACTION, new TCutAction(editor));
		actionSet.put(DELETE_ACTION, new TDeleteAction(editor));
		actionSet.put(SELECT_ALL_ACTION, new TSelectAllAction(editor));
		// Undo manager actions
		actionSet.put(UNDO_ACTION, new TUndoAction(editor));
		actionSet.put(REDO_ACTION, new TRedoAction(editor));
		// Elements order actions
		actionSet.put(BACK_ACTION, new TBackAction(editor));
		actionSet.put(FRONT_ACTION, new TFrontAction(editor));
		// Painter actions
		actionSet.put(CELL_HANDLER, new THandlerCellAction(editor));
		actionSet.put(CELL_CONTROLLER_HANDLER, new THandlerControllerCellAction(editor));
		//actionSet.put(GRID_HANDLER, new THandlerGridAction(editor));
		actionSet.put(LABEL_HANDLER, new THandlerLabelAction(editor));
		actionSet.put(LINE_HANDLER, new THandlerLineAction(editor));
		actionSet.put(RECTANGLE_HANDLER, new THandlerRectangleAction(editor));
		actionSet.put(ROUND_RECT_HANDLER, new THandlerRoundRectAction(editor));
		actionSet.put(SELECTION_HANDLER, new THandlerSelectionAction(editor));
		actionSet.put(OVAL_HANDLER, new THandlerOvalAction(editor));
		actionSet.put(TEXT_AREA_HANDLER, new THandlerTextAreaAction(editor));
		
		// Administration actions
		actionSet.put(USERS_ADMIN_ACTION, new TUsersAdminAction(editor));
		actionSet.put(LIMITATIONS_ADMIN_ACTION, new TLimitationsAdminAction(editor));
		actionSet.put(RULES_ADMIN_ACTION, new TRulesAdminAction(editor));
		*/
	}

	/**
	 * Returns the <code>action</code> with the specified <code>actionId</code>.
	 * 
	 * @param actionId The specified <code>actionId</code>
	 * @return The <code>action</code> with the specified <code>actionId</code>
	 */
	public Action getAction(String actionId) {
		return (Action)actionSet.get(actionId);
	}
}
