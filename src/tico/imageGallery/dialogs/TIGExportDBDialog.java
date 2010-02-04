/*
 * File: TIGExportDBDialog.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Patricia M. Jaray
 * 
 * Date: May 08, 2008
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import tico.components.TButton;
import tico.components.TDialog;
import tico.configuration.TLanguage;
import tico.editor.TEditor;
import tico.imageGallery.components.TIGExportTask;
import tico.imageGallery.dataBase.TIGDataBase;
import tico.imageGallery.dialogs.*;


/*
 * This class load images from a directory and associations from a text file
 * into the Data Base. It copies the new images into the Images directory and
 * creates all the thumbnails of the images. 
 */
public class TIGExportDBDialog extends TDialog {
	
	private GridBagConstraints c;
	
	private TButton exportButton;
	
	private TButton openButton;
	
	private TButton exitButton;
	
	private JTextField directory;
	
	private JTextField text;
	
	private TIGDataBase myDataBase;
	
	private String pathImages = "";
	
	private static File defaultDirectory = null;	
	
	private TEditor myEditor;

	public final static int ONE_SECOND = 1000;

    private Timer timer;
	
    private JProgressBar progressBar;
    
    private TIGExportTask task;

    private JPanel contentPane;
    
    private int numberOfImages;
    
    private boolean stop = false;
	
    private Vector images;
	public Vector myResults;
    
	private TIGThumbnailsDialog thumbnailsDialog;
	
	private JPanel thumbnailsPanel;
	
	public JPanel getThumbnailsPanel() {
		return thumbnailsPanel;
	}

	public void setThumbnailsPanel(JPanel thumbnailsPanel) {
		this.thumbnailsPanel = thumbnailsPanel;
	}

	public TIGExportDBDialog(TEditor editor,TIGDataBase dataBase) {
		super(editor, TLanguage.getString("TIGExportDBDialog.NAME"),true);
		this.myEditor = editor;
		
		myDataBase = dataBase;
		
		TIGDataBase.conectDB();
		numberOfImages = dataBase.numberOfImages();
		
		addWindowListener(new java.awt.event.WindowListener(){
			public void windowClosing(java.awt.event.WindowEvent e){
				dispose();
			}
			
			public void windowActivated(java.awt.event.WindowEvent e){}	  
			public void windowDeactivated(java.awt.event.WindowEvent e){}
			public void windowDeiconified(java.awt.event.WindowEvent e){}
			public void windowIconified(java.awt.event.WindowEvent e){}
			public void windowOpened(java.awt.event.WindowEvent e){}
			public void windowClosed(java.awt.event.WindowEvent e){}

		});
				
		task = new TIGExportTask();
		contentPane = createProgressDialog();
		
		JPanel setDirectory = new JPanel();
		setDirectory.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGExportDBDialog.IMAGES_SEL")));
		
		JPanel buttons = new JPanel();
		//Create components
		
		// Create components
		// First, create the component that search the names of the images
		JPanel searchNamePanel = new JPanel();	
		TIGSearchNameDialog searchNameDialog = new TIGSearchNameDialog();
		searchNamePanel = searchNameDialog.createSearchNamePanelExport(editor,myDataBase,this);
		
		// Second, create the component that search the images from its associations
		JPanel keyWordSearchPanel = new JPanel();
		TIGKeyWordSearchDialog keyWordSearchDialog = new TIGKeyWordSearchDialog(this.myEditor,this.myDataBase);
		images = new Vector();
		myResults= null;
		
		keyWordSearchPanel = keyWordSearchDialog.createKeyWordExportPanel(this);
		
		String keyWord1 = keyWordSearchDialog.KeyWord1();
		
		//All the images in the dataBase are shown 
		//when the window is displayed  
		images = TIGDataBase.imageSearch("*");
				
		// Third, create the component that shows all the images
		thumbnailsPanel = new JPanel();		
		thumbnailsDialog = new TIGThumbnailsDialog(true);
		// Create thumbnails panel with no selection of images
		thumbnailsPanel = thumbnailsDialog.createThumbnailsPanel(images, false);
		
		//First, create the panel that contains the file chooser for the directory
		//that contains the images
			
		directory = new JTextField(40);
		directory.setEditable(false);
		
		openButton = new TButton(new AbstractAction(TLanguage.getString("TIGExportDBDialog.IMAGES")) {
			public void actionPerformed(ActionEvent e) {
				pathImages = createFileChooser();
				if (pathImages.compareTo("") != 0){
					directory.setText(pathImages);
					File myDirectory = new File(pathImages); 
	    			progressBar.setMaximum(numberOfImages);
				}
			}
		});
		
		exitButton = new TButton(new AbstractAction(TLanguage.getString("TIGExportDBDialog.EXIT")) {
			public void actionPerformed(ActionEvent e) {
				stop = true;
				dispose();
			}
		});
		
		//Create the main button
		//aceptButton.addActionListener(new ButtonListener());
		//aceptButton = new TButton(TLanguage.getString("TIGExportDBDialog.DB"));
		//aceptButton.setActionCommand("start");
		//aceptButton.addActionListener(new ButtonListener());
		exportButton = new TButton(new AbstractAction(TLanguage.getString("TIGExportDBDialog.DB")) {
			public void actionPerformed(ActionEvent e) {
				if (directory.getText().compareTo("") == 0){
						JOptionPane.showConfirmDialog(null,
							TLanguage.getString("TIGExportDBDialog.ERROR_IM"),
							TLanguage.getString("TIGExportDBDialog.NAME"),
							JOptionPane.CLOSED_OPTION ,JOptionPane.WARNING_MESSAGE );
				}else if (myResults == null){
					JOptionPane.showConfirmDialog(null,
							TLanguage.getString("TIGSearchImageDialog.MESSAGE_NO_SEARCH"),
							TLanguage.getString("TIGExportDBDialog.NAME"),
							JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
				}else if(myResults.size()==0){
					JOptionPane.showConfirmDialog(null,
							TLanguage.getString("TIGExportDBDialog.MESSAGE_NO_IMAGES_FOUND"),
							TLanguage.getString("TIGExportDBDialog.NAME"),
							JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
				}else{
					pathImages = directory.getText();
					text.setText(TLanguage.getString("TIGLoadDBDialog.PROGRESS_TASK"));
					exportButton.setEnabled(false);
					progressBar.setIndeterminate(false);
					
					progressBar.setValue(0);
					
					task.go(myEditor,myDataBase,pathImages,images);	
					timer.start();
				}
			}
		});		
		
		
		//Place components
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
	
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 10, 5, 10);
		c.gridx = 0;
		c.gridy = 0;
		setDirectory.add(openButton, c);
		
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(5, 10, 5, 10);
		c.gridx = 1;
		c.gridy = 0;
		setDirectory.add(directory, c);
		
		buttons.add(exportButton);
		buttons.add(exitButton);
	
		
		// Place main components
		c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		getContentPane().add(searchNamePanel, c);

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		getContentPane().add(keyWordSearchPanel, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 2;
		getContentPane().add(thumbnailsPanel, c);
		
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 10, 5, 10);
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 3;
		add(setDirectory,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 10, 5, 10);
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 4;
		add(buttons, c);
				
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 5;
		add(contentPane, c);
	
		// Display the dialog
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		setResizable(false);
		pack();

		setLocationRelativeTo(editor);
		setVisible(true);
	
	}
	
	private String createFileChooser(){	
		
		//Displays the file chooser to select the directory where the images are.
		String path = "";
		
		// Open a JFileChooser
		JFileChooser fileChooser = new JFileChooser();
		// Customize JFileChooser
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle(TLanguage.getString("TIGExportDBDialog.IMAGES_DIRECTORY"));
		fileChooser.setCurrentDirectory(defaultDirectory);

		// Checks if the user has chosen a file
		int returnValue = fileChooser.showOpenDialog((Component)null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// Get the chosen file
			File selectedFile = fileChooser.getSelectedFile();
			// Set its directory as next first JFileChooser directory
			defaultDirectory = selectedFile.getParentFile();
			path = selectedFile.getPath();
			path = path + File.separator;
		}
		
		return path;		
	}
	
	private String createFileTextChooser(){	
		
		//Displays the file chooser to select the text file tha contains the associations.
		String path = "";
		
		// Open a JFileChooser
		JFileChooser fileChooser = new JFileChooser();
		// Customize JFileChooser
		fileChooser.setDialogTitle(TLanguage.getString("TIGExportDBDialog.ASSOCIATION_FILE"));
		fileChooser.setCurrentDirectory(defaultDirectory);

		// Checks if the user has chosen a file
		int returnValue = fileChooser.showOpenDialog((Component)null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// Get the chosen file
			File selectedFile = fileChooser.getSelectedFile();
			// Set its directory as next first JFileChooser directory
			defaultDirectory = selectedFile.getParentFile();
			path = selectedFile.getPath();
		}
		
		return path;		
	}
	
	public JPanel createProgressDialog(){
       
		progressBar = new JProgressBar();
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setMaximum(task.getLengthOfTask());
        //progressBar.setIndeterminate(true);

        JPanel panel = new JPanel();
        text = new JTextField("",20);
        text.setEditable(false);
       
        c = new GridBagConstraints();
		setLayout(new GridBagLayout());
	
		c.fill = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 10, 5, 10);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(text, c);
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(5, 10, 5, 10);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(progressBar, c);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(panel, BorderLayout.NORTH);
        //contentPane.add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        contentPane.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), 
				TLanguage.getString("TIGExportDBDialog.PROGRESS")));

        //create a timer
        timer = new Timer(100, new TimerListener());
        
        return contentPane;
		
    }

    //the actionPerformed method in this class
    //is called each time the Timer "goes off"
    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            progressBar.setValue(task.getCurrent());
            if (stop){
            	task.stop();
            }
            if (task.done()) {
                Toolkit.getDefaultToolkit().beep();
                timer.stop();
                exportButton.setEnabled(true);
                progressBar.setValue(progressBar.getMinimum());                
                dispose();
                JOptionPane.showConfirmDialog(null,
						TLanguage.getString("TIGExportDBDialog.EXPORT_COMPLETED"),
						"",
						JOptionPane.CLOSED_OPTION ,JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

	public void update(Vector result){
		thumbnailsPanel = thumbnailsDialog.updateThumbnailsPanel(result,0);
		this.myResults= (Vector) result;
		images= myResults;
	}
	
}