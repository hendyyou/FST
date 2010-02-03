package tico.imageGallery.actions;


import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.JOptionPane;


import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.imageGallery.dataBase.TIGDataBase;
import tico.imageGallery.dialogs.TIGDeleteImagesDialog;
import tico.imageGallery.dialogs.TIGModifyImageDialog;

public class TIGDeleteImageAction extends TIGAbstractAction{

	private TIGDataBase dataBase;
	/**
	 * Constructor for TIGOpenGallery.
	 * 
	 * @param editor The boards' editor
	 */
	public TIGDeleteImageAction(TEditor editor, TIGDataBase dataBase) {
		super(editor);
		this.dataBase = dataBase;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		TIGDataBase.conectDB();
		Vector data = TIGDataBase.imageSearch("*");
		TIGDataBase.closeDB();
		if (data.size() > 0){
			new TIGDeleteImagesDialog(getEditor(), dataBase);
		}
		else{
			JOptionPane.showConfirmDialog(null,
					TLanguage.getString("TIGManageImageAction.EMPTY"),
					TLanguage.getString("TIGManageImageAction.ERROR"),
					JOptionPane.CLOSED_OPTION ,JOptionPane.WARNING_MESSAGE );
		}
	}
}
