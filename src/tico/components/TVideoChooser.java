/*
 * File: TVideoChooser.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Carolina Palacio
 * 
 * Date: Oct 22, 2009
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

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.sound.sampled.SourceDataLine;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import tico.components.resources.TFileUtils;
import tico.components.resources.TResourceManager;
import tico.components.resources.VideoFilter;
import tico.configuration.TLanguage;
import tico.editor.TFileHandler;

/**
 * Components to a video file.
 * 
 * @author Carolina Palacio
 * @version 4.0 Oct 22, 2009
 */
public class TVideoChooser extends JPanel {
	// TVideoChooser default constructor parameters
	private final static String DEFAULT_TITLE = TLanguage.getString("TVideoChooser.TITLE");

	// Video file properties
	private String videoFilePath;

	// Video file player needed variables
	private boolean stopPlayback;
	private SourceDataLine sourceDataLine;
	
	// Video name panel
	private JPanel videoNamePane;
	// Sound file name text field
	private TTextField videoNameTextField;
	// Play and stop buttons
	//private TButton playVideoButton;
	//private TButton stopVideoButton;
	// Choose and clear buttons panel container
	private JPanel buttonPanel;
	// Clear sound button
	private TButton clearVideoButton;
	// Open image chooser dialog button
	private TButton selectVideoButton;
	// Allows save the directory where you get the last sound file
	private static File defaultDirectory = null;
	
	/*private URL videoURL = null;
	private TVideoPlayer mediaPlayer = null;
	private JDialog videoWindow = null;*/
	
	private URL videoURL;
	//private TVideoPlayer mediaPlayer;
	private JDialog videoWindow;
	/**
	 * Creates a new <code>TSoundChooser</code> with <i>NO_OPTIONS_TYPE</i>
	 * <code>type</code>.
	 */
	public TVideoChooser() {
		this(DEFAULT_TITLE);
	}

	/**
	 * Creates a new <code>TVideoChooser</code> with the specified
	 * <code>title</code>.
	 * 
	 * @param title The specified <code>title</code>
	 */
	public TVideoChooser(String title) {
		super();
		// Creates the border of the component
		setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), title));
		// Creates the components
		createVideoNamePane();
		createButtonPanel();
		// Update the components
		updateComponents();

		// Places the components
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 0, 10);
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		add(videoNamePane, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 10, 10, 10);
		c.weightx = 1.0;
		c.weighty = 0.0;
		c.gridx = 0;
		c.gridy = 1;
		add(buttonPanel, c);
	}

	// Creates the video name pane
	private void createVideoNamePane() {
		videoNamePane = new JPanel();

		videoNamePane.setLayout(new FlowLayout());

		JLabel textNameLabel = new JLabel(TLanguage.getString("TVideoChooser.NAME"));

		videoNameTextField = new TTextField();
		videoNameTextField.setColumns(20);
		videoNameTextField.setEditable(false);

		/*playVideoButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				playVideoFile();
			}
		});
		playVideoButton.setIcon(TResourceManager
				.getImageIcon("media-start-16.png"));
		playVideoButton.setMargin(new Insets(2, 2, 2, 2));
		playVideoButton.setToolTipText(TLanguage.getString("TSoundChooser.PLAY_TOOLTIP"));*/

		/*stopVideoButton = new TButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				stopVideoFile();
			}
		});
		stopVideoButton.setIcon(TResourceManager
				.getImageIcon("media-stop-16.png"));
		stopVideoButton.setMargin(new Insets(2, 2, 2, 2));
		stopVideoButton.setEnabled(false);
		stopVideoButton.setToolTipText(TLanguage.getString("TSoundChooser.STOP_TOOLTIP"));*/

		videoNamePane.add(textNameLabel);
		videoNamePane.add(videoNameTextField);
		//videoNamePane.add(playVideoButton);
		//videoNamePane.add(stopVideoButton);
	}

	// Creates the button panel
	private void createButtonPanel() {
		buttonPanel = new JPanel();

		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		selectVideoButton = new TButton(TLanguage.getString("TSoundChooser.BUTTON_SELECT"));
		selectVideoButton.addActionListener(new ChooseVideoButtonListener());

		clearVideoButton = new TButton(new AbstractAction(TLanguage.getString("TSoundChooser.BUTTON_CLEAR")) {
			public void actionPerformed(ActionEvent e) {
				setVideoFilePath(null);
			}
		});
				
		buttonPanel.add(selectVideoButton);
		buttonPanel.add(clearVideoButton);
	}

	/**
	 * Update all the components. Enables or disables the buttons.
	 */
	public void updateComponents() {
		
		if (videoFilePath != null) {
			
			//playVideoButton.setEnabled(true);
			clearVideoButton.setEnabled(true);
			
			videoNameTextField.setText(TFileUtils.getFilename(videoFilePath.toString()) + "." + TFileUtils.getExtension(videoFilePath.toString()));
			videoNameTextField.setCaretPosition(0);
		} else {
			//playVideoButton.setEnabled(false);
			clearVideoButton.setEnabled(false);

			videoNameTextField.setText("");
		}
		
	}

	// Play the selected sound file
	/*private void playVideoFile() {

	      	//stopVideoButton.setEnabled(true);
			playVideoButton.setEnabled(false);
			clearVideoButton.setEnabled(false);
			selectVideoButton.setEnabled(false);
			
			videoWindow = new JDialog();
			videoWindow.setModal(true);
			videoWindow.setFocusable(true);
			videoWindow.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			videoWindow.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we){
					stopVideoFile();
				}  
			});
			//System.err.println("url del video: "+videoURL.toString());
			System.err.println("PATH DEL VIDEO: "+videoFilePath);
			
	        mediaPlayer = new TVideoPlayer(videoFilePath);
	        
	        //System.out.println(videoURL.toString());
	        videoWindow.add(mediaPlayer);
	        
	        /*stopVideoButton = new TButton(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					stopVideoFile();
				}
			});
			stopVideoButton.setIcon(TResourceManager
					.getImageIcon("media-stop-16.png"));
			stopVideoButton.setSize(50,50);
			//stopVideoButton.setMargin(new Insets(2, 2, 2, 2));
			stopVideoButton.setEnabled(true);
			
			videoWindow.add(stopVideoButton);*/
			
	        
	        /*videoWindow.setFocusable(true);
	        videoWindow.setSize(500,500);
	        videoWindow.setVisible(true);
	        
	}*/

	// Stop the current played video file
	/*private void stopVideoFile() {
		//Para la reproducción del video y cierra la ventana
		mediaPlayer.stopVideoPlayer();
		videoWindow.setVisible(false);
		
		//stopVideoButton.setEnabled(false);
		playVideoButton.setEnabled(true);
		clearVideoButton.setEnabled(true);
		selectVideoButton.setEnabled(true);
		
	}*/
	/**
	 * Returns the selected <code>soundFilePath</code>.
	 * 
	 * @return The selected <code>soundFilePath</code>
	 */
	public String getVideoFilePath() {
		
		return videoFilePath;
	}

	/**
	 * Set the <code>soundFilePath</code>.
	 * 
	 * @param soundFilePath The <code>soundFilePath</code> to set
	 */
	public void setVideoFilePath(String videoFilePath) {
		
		this.videoFilePath = videoFilePath;
		
		updateComponents();
	}
	
	// Action listener for the selectVideoButton
	private class ChooseVideoButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Open a JFileChooser
			JFileChooser fileChooser = new JFileChooser();
			// Customize JFileChooser
			fileChooser.setDialogTitle(TLanguage.getString("TVideoChooser.CHOOSE_VIDEO"));
			fileChooser.setCurrentDirectory(defaultDirectory);
			fileChooser.addChoosableFileFilter(new VideoFilter());
			fileChooser.setAcceptAllFileFilterUsed(false);

			// Checks if the user has chosen a file
			int returnValue = fileChooser.showOpenDialog((Component)null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				// Get the chosen file
				File selectedFile = fileChooser.getSelectedFile();
				// Set its directory as next first JFileChooser directory
				defaultDirectory = selectedFile.getParentFile();
					
				try {
					// Import the file to the application directory
					selectedFile = TFileHandler.importFile(selectedFile);
					setVideoFilePath(selectedFile.getAbsolutePath());
					//videoFilePath = "file:/"+selectedFile.getAbsolutePath();
					
					//videoFilePath = fileChooser.getSelectedFile().toURI().toString();
						
					// TUNE Find a method to keep clean the application
					// directory
				} catch (Exception ex) {
					// If the import fails show an error message
					JOptionPane.showMessageDialog(null,
							TLanguage.getString("TVideoChooser.OPEN_FILE_ERROR"),
							TLanguage.getString("ERROR") + "!",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	// Video play thread which plays the selected video file
}
