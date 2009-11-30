/*
 * File: TInterpreter.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Pablo Muñoz
 * 
 * Date: Oct 5, 2006
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

/*Author Antonio*/

package tico.interpreter;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.VertexView;

import tico.board.TBoard;
import tico.board.TBoardModel;
import tico.board.TProject;
import tico.components.*;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.editor.TActionSet;
import tico.interpreter.actions.TInterpreterExitAction;
import tico.interpreter.actions.TInterpreterMouseActions;
import tico.interpreter.board.TInterpreterBoardLayoutCache;
import tico.interpreter.threads.TThreads;


/**
 * The main window of the Tico interpreter application.
 * 
 * @author Antonio Rodriguez
 * @version 1.0 Nov 20, 2006
 */

public class TInterpreter extends JFrame {
	private static String DEFAULT_TITLE = TLanguage.getString("TInterpreter.INTERPRETER_WINDOW_TITLE");

	// Editing project
	private TProject project;
		
	public int BoardChange=0;
	public int BrowseGrid=0;
	public int Grid;
	
	private TBoard InterpreterBoard;	
	public static TThreads TStart;	
	public Robot InterpreterRobot;	
	JMenuBar menuBar = new JMenuBar();	
	// TUNE Should be gotten from setup class
	private Dimension initLocation = new Dimension(0,0);
	private Dimension initSize = new Dimension(1204,800);
	private TInterpreterActionSet actionSet;
	private int ActivateBar=0;
	private int ActivateSelect=1;
	private JRadioButtonMenuItem Barrido;
	private JRadioButtonMenuItem Seleccion;
	private TProject CurrentProyect;
	public static ArrayList AccumulatedList=null;
	public int run=0;
	public TButton Controller;
	public  JPanel ControllerPane;
	public JPanel BackPanel;
	public TPanel interpretArea;
	public JPanel accumulatedArea;
	public static String defaultCursor = null;
	public JPanel Fondo;
    /**
	 * Creates a new <code>TInterpreter</code> main application window.
	 */
	
	public TInterpreter() {	
		
		super(DEFAULT_TITLE);
		initSize=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		AccumulatedList=new ArrayList();
		try {
			InterpreterRobot=new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		/*DEfinimos el tama�o de la ventana.*/
		setWindowAtributes();

		createActions();
		  
		createMenu();
		createInterpreterArea();
	    createWindowListener();
	    
	    updateMenuButtons();
	    
		if (project != null)
			setProject(project);
		
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Creates a new <code>TInterpreter</code> main application window with the
	 * specified initial <code>project</code>.
	 * 
	 * @param project The specified initial <code>project</code>
	 */	
	
	public TInterpreter(TProject project) {	
		this();
		setProject(project);
		
		this.setTitle(TInterpreter.DEFAULT_TITLE + " - " + project.getName());
	
		CurrentProyect=project;
	}
	
	/**
	 * Creates a new <code>TInterpreter</code> main application window with the
	 * specified initial <code>board</code>.
	 * 
	 * @param board The specified initial <code>board</code>
	 */		
	
	public TInterpreter(TBoard board) {
		this();
		
		TProject project = new TProject(board.getBoardName());
		CurrentProyect=project;
		project.addBoard(board);
		setProject(project);
	}
	
	public TProject getIntepreterProject() {
		return CurrentProyect;
	}

	// Sets the window attributes
	protected void setWindowAtributes() {
		setIconImage(TResourceManager.getImage("interpreter-icon-24.png"));
		setLocation(initLocation.width, initLocation.height);
		setSize(initSize.width, initSize.height);
		
	}

	// Creates the editor actions
	private void createActions() {
		actionSet = new TInterpreterActionSet(this);
	}

	
	// Creates the editor main menu
	private void createMenu() {
		
		JMenu menu = new JMenu(TLanguage.getString("TInterpreter.FILE_MENU"));
		menu.setMnemonic(KeyEvent.VK_A);

		// Create the menu items
		TMenuItem menuItem;
		/*Creamos Menu para elegir Proyecto*/
		menuItem = new TMenuItem(actionSet
				.getAction(TInterpreterActionSet.PROJECT_OPEN_ACTION));
		menuItem.setMnemonic(KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		
//		menuItem = new TMenuItem(actionSet
//				.getAction(TInterpreterActionSet.INTERPRETER_PRINT_ACTION));
//		menuItem.setMnemonic(KeyEvent.VK_P);
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
//				ActionEvent.CTRL_MASK));
//		menu.add(menuItem);
		
		
		menu.add(new JSeparator());
		menuItem = new TMenuItem(actionSet
				.getAction(TInterpreterActionSet.INTERPRETER_EXIT_ACTION));
		menuItem.setMnemonic(KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		/*Menu para mostrar quienes somos*/
		
		
		// Add the menu
		menuBar.add(menu);
		JMenu menuHelp = new JMenu(TLanguage.getString("TEditorMenuBar.HELP_MENU"));
		
		menuHelp.setMnemonic(KeyEvent.VK_Y);
		menuItem = new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_ABOUT));
		menuItem.setMnemonic(KeyEvent.VK_F1);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
				ActionEvent.CTRL_MASK));
		menuHelp.add(menuItem);
		
		JMenu  menu2 = new JMenu(TLanguage.getString("TInterpreterMenuBar.ACTION_MENU"));
		menuItem = new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_RUN));
		
		menuItem.setMnemonic(KeyEvent.VK_F5);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
				ActionEvent.CTRL_MASK));
		menu2.add(menuItem);
		
		menuItem = new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_STOP));
		
		menuItem.setMnemonic((char)KeyEvent.VK_ESCAPE);
		menuItem.setAccelerator(KeyStroke.getKeyStroke((char) KeyEvent.VK_ESCAPE));
		menu2.add(menuItem);
		
		//MenuItem to Read the Cells of the Accumulated Panel
		
		TMenuItem menuItem2 = new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_READ));
		menuItem2.setMnemonic(KeyEvent.VK_F7);
		menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7,
				ActionEvent.CTRL_MASK));
		menu2.add(menuItem2);
		
		
		menuItem2 = new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_UNDO));
		menuItem2.setMnemonic(KeyEvent.VK_F7);
		menuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				ActionEvent.CTRL_MASK));
		menu2.add(menuItem2);
		
		
		
		
		JMenu tipoBarrido = new JMenu(TLanguage.getString("TInterpreterMenuBar.MODE"));
		
		//Initial Situation JRadioBUtton;
		Barrido = new JRadioButtonMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_BARRIDO));
		Seleccion = new JRadioButtonMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_DIRECT_SELECTION));
		Barrido.setSelected(false);
		Seleccion.setSelected(true);
		
		ActivateSelect=1;
		ActivateBar=0;
		
		
		
		tipoBarrido.add(Barrido);
        tipoBarrido.add(Seleccion);
        menu2.add(tipoBarrido);
        
		JMenu menuView = new JMenu(TLanguage.getString("TInterpreterMenuBar.VIEW_MENU"));
		menuItem= new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_OPTIONS));
		menuView.add(menuItem);
		menuItem = new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_LANGUAJES));
		menuView.add(menuItem);
		menuBar.add(menuView);
		menuBar.add(menu2);
		menuBar.add(createValidationMenu());
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
		
	}
	
	public int getActivateSelect (){
		
		return this.ActivateSelect;
	}
	public int getActivateBar () {
		
		return this.ActivateBar;
	}
	
	public void setActivateSelect (int value){
		this.ActivateSelect=value;
	}
	
	public void setActivateBar (int value){
		
		this.ActivateBar=value;
	}
	
	public void setRadioBar (boolean value){
		Barrido.setSelected(value);
	}
	
	public void setRadioSelect (boolean value){
		
		Seleccion.setSelected(value);
	}
	public TInterpreter getInterpreter() {
		return this;
	}
	
	// Creates de board interpreting area
	private void createInterpreterArea() {
		
		interpretArea = new TPanel();
		interpretArea.setLayout(new FlowLayout(FlowLayout.LEADING));
	
		accumulatedArea = new JPanel();
		 Fondo= new JPanel(new BorderLayout());
		
		Fondo.add(accumulatedArea,BorderLayout.CENTER);
		accumulatedArea.setLayout(new FlowLayout(FlowLayout.LEFT));
		accumulatedArea.setBackground(Color.WHITE);
		JScrollPane accumulatedScrollPane = new JScrollPane(
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		accumulatedScrollPane.setViewportView(Fondo);
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				new JScrollPane(interpretArea), Fondo);
		
		splitPane.setResizeWeight(0.5);
		splitPane.setEnabled(false);
		splitPane.setDividerSize(10);
		splitPane.setDividerLocation(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height-157);
		
	    ImageIcon imageController=TResourceManager.getImageIcon("jump22.png");
	    
	    	
	    ControllerPane= new JPanel();
		Controller= new TButton(actionSet.getAction(TInterpreterActionSet.CONTROLLER_BUTTON_ACTION));
		
		//Install Features to Controller.
		
		Controller.setVerticalTextPosition(SwingConstants.BOTTOM);
		Controller.setHorizontalTextPosition(SwingConstants.CENTER);
		Controller.setFocusPainted(false);
		Controller.setIcon(imageController);
	   		
		Controller.setBackground(Color.green);
		
		Controller.addMouseListener(new TInterpreterMouseActions(Controller));
		
		ControllerPane.add(Controller);
		ControllerPane.setBackground(Color.cyan);
		
		Fondo.add(ControllerPane,BorderLayout.EAST);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(splitPane, BorderLayout.CENTER);
				
	}
	
	// Creates the window listener
	private void createWindowListener() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new TInterpreterExitAction(getInterpreter()).actionPerformed(null);
			}
		});
	}
	
	/**
	 * Returns the current <code>project</code>.
	 * 
	 * @return The current <code>project</code>
	 */
	public TProject getProject() {
		return project;
	}

	/**
	 * Sets a new <code>project</code> to begin its interpretation
	 * 
	 * @param project The new <code>project</code>
	 */
	public void setProject(TProject project) {
		if (getProject() != null)
			deleteProject();
		this.setTitle(TInterpreter.DEFAULT_TITLE + " - " + project.getName());
		TBoard board = project.getInitialBoard();
		this.project = project;
		
		changeBoard(board);
		updateMenuButtons();
	}
	/*
	 * Return the current Board of the Interpreter
	 * 
	 */
	public TBoard getBoard (){
		
		return this.InterpreterBoard;
	}
	
	public void changeBoard(TBoard board) {
		
		interpretArea.removeAll();
		InterpreterBoard=board;
		TBoardModel boardModel = (TBoardModel)board.getModel();		
		TBoard nBoard = new TBoard(boardModel);
		GraphLayoutCache boardLayoutCache = new TInterpreterBoardLayoutCache();
		boardLayoutCache.setModel(boardModel);
		nBoard.setGraphLayoutCache(boardLayoutCache);
		nBoard.setSelectionEnabled(false);
		nBoard.setMarqueeHandler(new TInterpreterMarqueeHandler(this));
		nBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		interpretArea.add(nBoard);
		interpretArea.updateUI();
	}
	
	
	
	/*Procedimiento mediante el cual acumulamos elementos en el panel accumulated Area*/
	
	public void addAccumulated(VertexView view) {
		
		
		
		if (AccumulatedList.size() < TInterpreterConstants.interpreterAcumulatedCells)
		{
		accumulatedArea.add(new TInterpreterAccumulatedRenderer(view));
		accumulatedArea.updateUI();
		}
	}	
	/**
	 * Deletes from the application the current <code>project</code>.
	 */	
	public void deleteProject() {
		project = null;
		updateMenuButtons();
	}

	/**
	 * Returns the editor's <code>actionSet</code>.
	 * 
	 * @return The editor's <code>actionSet</code>
	 */
	public TInterpreterActionSet getActionSet() {
		return actionSet;
	}
	
	/**
	 * Change Interpreter's Cursor into a predefinied image 
	 * 
	 */
	
	public void TIntepreterChangeCursor(){			
		
		
		if (TInterpreterConstants.interpreterCursor==null)
		{
		ImageIcon ii=TResourceManager.getImageIcon("flecha2.png");
	
		if (ii==null){
			System.out.println("Error en el cursor");
			ii=TResourceManager.getImageIcon("flecha2.png");
			}
		Image imageCursor=ii.getImage();
		Cursor customCursor=getToolkit().createCustomCursor(imageCursor,new Point(),"MyCursor");
		this.setCursor(customCursor);
		}
		else 
		{
			ImageIcon ii= new ImageIcon(TInterpreterConstants.interpreterCursor);
			
			if (ii==null){
				System.out.println("Error en el cursor");
				ii=TResourceManager.getImageIcon("flecha2.png");
			}
			
			Image imageCursor=ii.getImage();
			Cursor customCursor=getToolkit().createCustomCursor(imageCursor,new Point(),"MyCursor");
			this.setCursor(customCursor);
		}
		
		
  	}
	
	/*
	 * Restore Default Cursor. 
	 * 
	 */
	
	public void TInterpreterRestoreCursor(){
				
		Cursor cursorBar = new Cursor(Cursor.DEFAULT_CURSOR);
		this.setCursor(cursorBar);
	}
	
	
	private JMenu createValidationMenu() {
		// Create the menu
		JMenu menu = new JMenu(TLanguage.getString("TInterpreter.VALIDATION_MENU"));
		menu.setMnemonic(KeyEvent.VK_L);		

		// Create submenu
		TMenuItem menuItem = new TMenuItem(actionSet.getAction(TInterpreterActionSet.INTERPRETER_VALIDATION));
		
		menu.add(menuItem);
		add(menu);
		
		return menu;
	}
	
	public void updateMenuButtons() {
		boolean projectExists = (project != null);

		// Action handlers
		actionSet.getAction(TInterpreterActionSet.INTERPRETER_VALIDATION).setEnabled(
				projectExists);
	}
}

