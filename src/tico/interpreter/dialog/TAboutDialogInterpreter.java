/*
 * File: TAboutDialogIntepreter.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Antonio Rodríguez
 * 
 * Date: May 23, 2007
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

package tico.interpreter.dialog;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import tico.components.TButton;
import tico.components.TDialog;
import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;

import tico.interpreter.*;


	public class TAboutDialogInterpreter extends TDialog {
		
		public TAboutDialogInterpreter (TInterpreter interpreter) {
			super(null, TLanguage.getString("TAboutDialog.TITLE"), false);

			// Create components
			Icon logo = TResourceManager.getImageIcon("tico-logo.png");
			JLabel logoLabel = new JLabel(logo);
			logoLabel.setPreferredSize(new Dimension(350,250));

			JLabel textLabel = createTextLabel();

			TButton acceptButton = new TButton(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			acceptButton.setText("Aceptar");

			// Place components
			GridBagConstraints c = new GridBagConstraints();
			getContentPane().setLayout(new GridBagLayout());

			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(0, 0, 0, 0);
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 0.0;
			c.weighty = 0.0;
			getContentPane().add(logoLabel, c);

			c.insets = new Insets(5, 15, 10, 15);
			c.gridx = 0;
			c.gridy = 1;
			getContentPane().add(textLabel, c);

			c.fill = GridBagConstraints.NONE;
			c.insets = new Insets(10, 10, 10, 10);
			c.gridx = 0;
			c.gridy = 2;
			getContentPane().add(acceptButton, c);

			// Display the dialog
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			setResizable(false);
			pack();

			setLocationRelativeTo(interpreter);
			setVisible(true);
		}

		// Creates the text label
		private JLabel createTextLabel() {
	        JLabel textLabel = new JLabel();
	        textLabel.setHorizontalAlignment(SwingConstants.LEFT);

	        String aboutText = "<html><body><table>";
	        aboutText += "<tr><td colspan=2 align=center><h3>Tico 2.0</h3></td></tr>";
	        aboutText += "<tr><td>"
	                + "Desarrolladores:"
	                
	                + "<tr><td><i>   - Tico V1.0:&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Fernando Negre, David Ramos (2005)</i></td></tr>"
	                + "</td><td><i>   - Editor Tico V2.0:&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Pablo Muñoz (2006)</i></td></tr>"
	                + "</td><td><i>   - Intérprete Tico V2.0:&nbsp &nbsp Antonio Rodríguez (2007)</i></td></tr>"
	                + "</td><td><i>   - Tico V2.1:&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp Beatriz Mateo (2008)</i></td></tr>"
	                
	                + "Director: "
	                + "<tr><td><i>   - Joaquín Ezpeleta (Universidad de Zaragoza)</i></td></tr>"

	                + "Co-Director(Componente de Validación de Aspectos de Interacción): "
	                + "<tr><td><i>   - Sandra Baldassarri (Universidad de Zaragoza)</i></td></tr>"
	                
	                + "Colaboradores: "
	                + "<tr><td><i>   - César Canalis (Colegio Alborada)</i></td></tr>"
	                + "<tr><td><i>   - José Manuel Marcos (Colegio Alborada)</i></td></tr>"
	                + "<tr><td>"
	                
	                +"Entidades: "
	                + "<tr><td><i>   - Universidad de Zaragoza | Instituto de Investigación en Ingeniería de Aragón (I3A)</i></td></tr>"
	                + "<tr><td><i>   - Colegio Público de Educación Especial Alborada. Zaragoza </i></td></tr>";
	    
	        aboutText += "<tr><td>"
	                + TLanguage.getString("TAboutDialog.LICENSE")+": "
	                +"<tr><td><i>   - General Public License, version 2 </i></td></tr>";
	                
	        aboutText += "</table></body></html>";

	        textLabel.setText(aboutText);

	        return textLabel;
	    }
	}