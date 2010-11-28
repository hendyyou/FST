package tico.editor.actions;

import java.awt.event.ActionEvent;

import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.editor.handler.TSeparateObjectsHandler;

public class THandlerSeparateObjects extends TEditorAbstractAction{

	public THandlerSeparateObjects(TEditor editor) {
		super(editor, TLanguage.getString("TSeparateObjectsAction.NAME"),TResourceManager.getImageIcon("handler-separate-22.png"));
	}
	
	public void actionPerformed(ActionEvent e){
		getEditor().getCurrentBoard().setMarqueeHandler(new TSeparateObjectsHandler(getEditor().getCurrentBoardContainer()));
	}

}
