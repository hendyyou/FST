/*
 * File: TInterpreterOptionsDialog.java
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

import java.awt.Color;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;

import javax.swing.SpinnerNumberModel;

import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tico.components.TButton;
import tico.components.TDialog;
import tico.components.TImageChooser;

import tico.configuration.TLanguage;
import tico.interpreter.TInterpreter;
import tico.interpreter.TInterpreterConstants;

public class TInterpreterOptionsDialog extends TDialog {

	TInterpreter aux;

	TImageChooser CursorPanel;

	JPanel panelButtons;

	JPanel SpeedPanel;

	JPanel cellPanel;

	public static int value;

	public static int valor;

	JSpinner spinner;

	SpinnerNumberModel model;

	private void createCursorPanel() {
		CursorPanel = new TImageChooser("Imagen de Cursor");
		if (TInterpreterConstants.interpreterCursor != null) {
			ImageIcon algo = new ImageIcon(
					TInterpreterConstants.interpreterCursor);
			CursorPanel.setIcon(algo);
		}

	}

	private void createButtonPanel() {
		panelButtons = new JPanel();
		TButton AcceptButton = new TButton("Aceptar");
		AcceptButton.setFocusable(true);
		TButton CancelButton = new TButton("Cancelar");

		AcceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Accept_ActionPerformed(e);

			}

		});

		CancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cancel_ActionPerformed(e);
			}

		});

		panelButtons.add(AcceptButton);
		panelButtons.add(CancelButton);

	}

	private void createSpeedPanel() {

		SpeedPanel = new JPanel();
		SpeedPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)),
				"Velocidad Cursor en ms"));
		JSlider gros = new JSlider(JSlider.HORIZONTAL, 1000, 20000,
				TInterpreterConstants.interpreterDelay);
		value = gros.getValue();

		gros.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source = (JSlider) e.getSource();

				if (!source.getValueIsAdjusting()) {
					value = (int) source.getValue();

				}
			}

		});
		Dictionary labelTable = new Hashtable();
		labelTable.put(new Integer(1000), new JLabel("1000"));
		labelTable.put(new Integer(10000), new JLabel("10000"));
		labelTable.put(new Integer(20000), new JLabel("20000"));
		gros.setLabelTable(labelTable);
		gros.setMinorTickSpacing(1000);
		gros.setPaintTicks(true);
		gros.setPaintLabels(true);
		SpeedPanel.add(gros);
	}

	private void createNumCellsPanel() {
		cellPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cellPanel.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(
				Color.WHITE, new Color(165, 163, 151)), ""));
		JLabel text = new JLabel("Numero Maximo de Celdas Acumuladas");
		cellPanel.add(text);

		model = new SpinnerNumberModel(
				TInterpreterConstants.interpreterAcumulatedCells, 0, 10, 1);
		spinner = new JSpinner(model);

		cellPanel.add(spinner);

	}

	public TInterpreterOptionsDialog(TInterpreter interprete) {
		// create a non modal dialog.
		super(interprete, TLanguage.getString("TInterpreterOptionDialog.NAME"),
				true);
		aux = interprete;

		JPanel BackPanel = new JPanel();
		createCursorPanel();
		createButtonPanel();
		createSpeedPanel();
		createNumCellsPanel();
		GridBagConstraints c = new GridBagConstraints();
		BackPanel.setLayout(new GridBagLayout());

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 20, 0, 20);
		c.gridx = 0;
		c.gridy = 0;
		BackPanel.add(CursorPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 20, 0, 20);
		c.gridx = 0;
		c.gridy = 1;
		BackPanel.add(cellPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 20, 0, 20);
		c.gridx = 0;
		c.gridy = 2;
		BackPanel.add(SpeedPanel, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 20, 0, 20);
		c.gridx = 0;
		c.gridy = 3;
		BackPanel.add(panelButtons, c);
		// Display it!
		this.setBounds(100, 100, 380, 380);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(true);
		this.setContentPane(BackPanel);
		this.setVisible(true);
	}

	public void Accept_ActionPerformed(ActionEvent e) {

		if (TImageChooser.ruta != null) {
			TInterpreterConstants.interpreterCursor = TImageChooser.ruta;
		} else {
			TInterpreterConstants.interpreterCursor = null;
		}
		TInterpreterConstants.interpreterDelay = value;
		TInterpreterConstants.interpreterAcumulatedCells = model.getNumber()
				.intValue();
		setVisible(false);
	}

	public void Cancel_ActionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
