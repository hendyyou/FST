package tico.arabard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;

import tico.board.TBoardConstants;
import tico.board.TProject;
import tico.components.TToolBarContainer;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.editor.TActionSet;
import tico.editor.TEditorMenuBar;
import tico.araboard.toolbars.TFileToolBar;

/**
 * The main window of the TICO Araboard application
 * @author Adrián Gómez
 * @version 1.0 Nov 22, 2010
 *
 */
public class TAraboard extends JFrame{
	
	private static String DEFAULT_TITLE = TLanguage.getString("TAraboard.EDITOR_WINDOW_TITLE");
	
	//Editing project
	private TProject project;
	
	private boolean modified = false;
	
	private File projectFile = null;

	// Default size and location of the window
	private Dimension initLocation = new Dimension(100, 100);
	
	private Dimension initSize = new Dimension(800, 600);

	
	//Actions defined for board management
	private TActionSet actionSet;

	// Tool bars
	private TToolBarContainer toolBarContainer;
	
	//private TEditionToolBar editionToolBar;
	
	//private TAlignToolBar alignToolBar;
	
	private TFileToolBar fileToolBar;
	
	//private TFormatToolBar formatToolBar;
	
	//private THandlersToolBar handlersToolBar;
	
	//private TTextToolBar textToolBar;
	
	/**
	 * Creates a new TArabord main application window.
	 */
	public TAraboard() {
		this(null);
	}
	
	
	/**
	 * Creates a new TAraboard main application window with
	 * specified initial project
	 * @param project The specified initial project
	 */
	public TAraboard(TProject project){
		super(DEFAULT_TITLE);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setWindowAttributes();
		
		this.getContentPane().setLayout(new BorderLayout());
		
		this.createActions();
		this.createToolBars();
		this.createMenu();
		this.createBoardEditor();
		this.crateWindowListener();
		
		this.updateProjectButtons();
		
		if(project != null)
			this.setProject(project);
		
		this.setVisible(true);
		TBoardConstants.araboard = this;
	}

	protected void setWindowAttributes() {
		// TODO Defining icon image
		//this.setIconImage(TResourceManager.getImage("araboard-icon-24.png"));
		this.setLocation(this.initLocation.width, this.initLocation.height);
		this.setSize(initSize);
	}
	
	private void createToolBars() {
		
		this.toolBarContainer = new TToolBarContainer();
		
		// TODO Create Toolbars
		this.fileToolBar = new TFileToolBar(this);
		//this.editionToolBar = new TEditionToolBar(this);
		//this.handlersToolBar = new THandlersToolBar(this);
		//this.formatToolBar = new TFormatToolBar(this);
		//this.textToolBar = new TTextToolBar(this);
		//this.alignToolBar = new TAlignToolBar(this);

		//this.toolBarContainer.addToolBar(this.fileToolBar);
		//this.toolBarContainer.addToolBar(this.editionToolBar);
		//this.toolBarContainer.addToolBar(this.handlersToolBar);
		//this.toolBarContainer.addToolBar(this.formatToolBar);
		//this.toolBarContainer.addToolBar(this.textToolBar);
		//this.toolBarContainer.addToolBar(this.alignToolBar);

		this.getContentPane().add(this.toolBarContainer, BorderLayout.NORTH);
	}
	
	private void createMenu() {
		
		// TODO Create Menu bar
		//this.setJMenuBar(new TEditorMenuBar(this, toolBarContainer));
	}
	
	private void createActions() {
		// TODO Create action set
		//this.actionSet = new TActionSet(this);
	}
	
	private void createBoardEditor() {
		// TODO Create board editor
		
	}
	
	private void crateWindowListener() {
		// TODO Create listeners
		
	}

	private void setProject(TProject project2) {
		// TODO Create set project
		
	}


	private void updateProjectButtons() {
		// TODO Create update project buttons
		
	}
	
	/**
	 * @return The editor's actionSet
	 */
	public TActionSet getActionSet() {
		return actionSet;
	}


}
