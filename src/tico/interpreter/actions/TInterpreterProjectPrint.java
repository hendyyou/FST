/*
 * File: TInterpreterProjectPrint.java
 * 		This file is part of Tico, an application to create and	perfom
 * 		interactive comunication boards to be used by people with
 * 		severe motor disabilities.
 * 
  * Authors: Antonio Rodríguez
 * 
 * Date:	May-2006 
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

package tico.interpreter.actions;



import java.awt.event.ActionEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import tico.components.resources.TResourceManager;
import tico.configuration.TLanguage;
import tico.interpreter.TInterpreter;

/**
 * Action wich prints the current editor project. Each board of the editor
 * is printed in a different page.
 * 
 * @author Antonio Rodriguez
 * @version 1.0 Nov 20, 2006
 */
public class TInterpreterProjectPrint extends TInterpreterAbstractAction{

	/**
	 * Constructor for TProjectPrintAction.
	 * 
	 * @param interpreter The boards' interpreter
	 */
	public TInterpreterProjectPrint(TInterpreter interpreter) {
		super(interpreter, TLanguage.getString("TProjectPrintAction.NAME"),
				TResourceManager.getImageIcon("archive-print-22.png"));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// Get the print manager
		
			PrinterJob job = PrinterJob.getPrinterJob();
			PageFormat format = job.defaultPage();
//			 El tamaño de la pagina esta dado por:
//			 tamaño papel en pulgadas por 72
//			 ej. carta 8.5 * 11 = 612 * 792
			Paper papel = new Paper();
			papel.setSize(1265,799);
			
			format.setPaper(papel);
			format.setOrientation(PageFormat.LANDSCAPE);
			job.setPrintable((Printable) interpreter.interpretArea, format);
			
			try{
			job.print();
			}
			catch (PrinterException e1) {
			e1.printStackTrace();
			}
			
			

}
}
