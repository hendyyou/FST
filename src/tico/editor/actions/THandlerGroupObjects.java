package tico.editor.actions;

import java.awt.event.ActionEvent;

import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.editor.handler.TGroupObjectsHandler;

public class THandlerGroupObjects extends TEditorAbstractAction{
	
	public THandlerGroupObjects(TEditor editor) {
		super(editor, TLanguage.getString("TGroupObjectsAction.NAME"),
				TResourceManager.getImageIcon("handler-separate-22.png"));
	}
	
	public void actionPerformed(ActionEvent e){
		getEditor().getCurrentBoard().setMarqueeHandler(new TGroupObjectsHandler(getEditor().getCurrentBoardContainer()));
	}

}
