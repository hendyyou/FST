/*
 * File: TInterpreterLabel.java
 * 		This file is part of Tico, an application to create and	perform
 * 		interactive communication boards to be used by people with
 * 		severe motor disabilities.
 * 
 * Authors: Isabel Gonz�lez y Carolina Palacio
 * 
 * Date: Nov, 2009
 * 
 * Company: Universidad de Zaragoza, CPS, DIIS
 * 
 * License:
 * 		This program is free software: you can redistribute it and/or 
 * 		modify it under the terms of the GNU General Public License 
 * 		as published by the Free Software Foundation, either version 3
 * 		of the License, or (at your option) any later version.
 * 
 * 		This program is distributed in the hope that it will be useful,
 * 		but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 		MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * 		GNU General Public License for more details.
 * 
 * 		You should have received a copy of the GNU General Public License
 *     	along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 */

package tico.interpreter.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

public class TInterpreterMonitor extends TInterpreterLabel {
	
	private MonitorThread _monitorThread;
	private String command;
	
	private static ArrayList<MonitorThread> listMonitorThreads = new ArrayList<MonitorThread>();
	
	public static void stopAllThreads() {
		Iterator<MonitorThread> it = listMonitorThreads.iterator();
		while (it.hasNext()) {
			try {
				MonitorThread item = it.next();
				item.stop = true;
				item.p.destroy();
				item.interrupt();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		listMonitorThreads.clear();
	}
	
	public TInterpreterMonitor (){
		super();
	}
	
	public class MonitorThread extends Thread {
		public Process p;
		private String command;
		private TInterpreterMonitor _instanceLabel;
		public InputStream input;
		public boolean stop = false;
		public BufferedReader inputReader;
		
		public MonitorThread(String command, TInterpreterMonitor label) {
			super();
			this.command = command;
			this._instanceLabel = label;
		}
		
		@Override
		public synchronized void start() {
			this.stop = false;
			super.start();
		}
		
		@Override
		public void run() {
			String line;
			String text = "";
			SAXBuilder builder = new SAXBuilder(false);
			Document doc;
			Element rootElement;
			Element textElement;
			Element valueElement;
			Element unitsElement;
			InputSource is;
			try {
				ProcessBuilder pbuilder = new ProcessBuilder(command.split(" ", 0));
				pbuilder.directory(new File(System.getProperty("user.dir")));
				p = pbuilder.start();
				//p = Runtime.getRuntime().exec(command);
				input = p.getInputStream();
				inputReader = new BufferedReader(new InputStreamReader(input));
				boolean finishedCommand = false;
				while (!stop && !finishedCommand) {
					boolean completeLine = false;
					line = "";
					try {
						while (!completeLine) {
							//System.out.println("Reading line...");
							if (inputReader.ready() && !completeLine) {
								char c = (char) inputReader.read();
								if (c == '\n') {
									completeLine = true;
									System.out.println("complete line");
								} else if (c == -1) {
									finishedCommand = true;
									System.out.println("finished command");
									completeLine = true;
								}
								line = line.concat(String.valueOf(c));
							} else {
								Thread.sleep(300);
							}
						}
						System.out.println("Command working");
						try {
							is = new InputSource();
							is.setCharacterStream(new StringReader(line));
							doc = builder.build(is);
							rootElement = doc.getRootElement();
							textElement = rootElement.getChild("text");
							valueElement = rootElement.getChild("value");
							unitsElement = rootElement.getChild("units");
							if (textElement != null) {
								text = textElement.getText();
							}
						} catch (JDOMException e) {
							System.out.println("JDOMException: "+e.toString());
							e.printStackTrace();
						} catch (IOException e) {
							System.out.println("IOException: "+e.toString());
							e.printStackTrace();
						} catch (Exception e) {
							System.out.println("Exception1: "+e.toString());
							e.printStackTrace();
						}
						if (text != null) {
							_instanceLabel.setText(text);
						}
					} catch (Exception e) {
						System.out.println("Exception2: "+e.toString());
						e.printStackTrace();
					}
				}
				inputReader.close();
			} catch (Exception ex) {
				System.out.println("Exception3: "+ex.toString());
				ex.printStackTrace();
			}
		}
	}
	
	private void startThreadMonitor() {
		// Launch thread listener
		if (_monitorThread != null) {
			_monitorThread.stop = true;
			_monitorThread.p.destroy();
			listMonitorThreads.remove(_monitorThread);
			_monitorThread = null;
		}
		_monitorThread = new MonitorThread(this.command,this);
		listMonitorThreads.add(_monitorThread);
		_monitorThread.start();
	}
	
	@Override
	public TInterpreterLabel setAttributes(Color borderColor, float borderSize,
			Color backgroundColor, Color gradientColor, Rectangle r,
			String command, Font font, Color textColor,
			boolean transparentBackground) {
		TInterpreterLabel label = super.setAttributes(borderColor, borderSize,
				backgroundColor, gradientColor, r, command, font, textColor,
				transparentBackground);
		this.command = command;
		startThreadMonitor();
		return label;
	}
	
}
